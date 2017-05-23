package com.sxdtdx.aitou.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxdtdx.aitou.R;
import com.sxdtdx.aitou.presenter.PublishVotePresenter;
import com.sxdtdx.aitou.utils.HelpUtils;
import com.sxdtdx.aitou.view.SuperListView;
import com.sxdtdx.aitou.view.activity.AddOptionsActivity;
import com.sxdtdx.aitou.view.activity.HomeActivity;
import com.sxdtdx.aitou.view.interfaces.IPublishVote;
import com.sxdtdx.aitou.view.utils.CommonAdapter;
import com.sxdtdx.aitou.view.utils.ViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

import static android.app.Activity.RESULT_OK;
import static com.sxdtdx.aitou.view.activity.HomeActivity.INDEX_TAB_ZERO;

public class PublishVoteFragment extends Fragment implements IPublishVote, View.OnClickListener {

    private static final int REQUEST_CODE_ADD_OPTION = 1;
    private static final int SELECT_ORIGINAL_PIC = 0x11;
    private View mView;
    private EditText mPublishTitle;
    private EditText mPublishDetails;
    private ImageView mPublishCover;
    private Button mPublishBtn;
    private PublishVotePresenter mPublishVotePresenter;
    List<String> mOptions = new ArrayList<>();
    private SuperListView mOptionsView;
    private OptionsAdapter mOptionsAdapter;
    private Button mAddBtn;
    private File mCoverFile = null;
    private TextView mAddCoverTxt;
    private ImageView mCover;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_vote_publish, container, false);
        initView();
        mPublishVotePresenter = new PublishVotePresenter(this);
        return mView;
    }

    @Override
    public void initView() {
        mPublishTitle = (EditText) mView.findViewById(R.id.et_publish_theme);
        mPublishCover = (ImageView) mView.findViewById(R.id.iv_publish_cover);
        mAddCoverTxt = (TextView) mView.findViewById(R.id.tv_add_cover);
        mCover = (ImageView) mView.findViewById(R.id.iv_cover);
        mPublishDetails = (EditText) mView.findViewById(R.id.et_publish_details);
        mPublishBtn = (Button) mView.findViewById(R.id.btn_publish);
        mAddBtn = (Button) mView.findViewById(R.id.btn_add_options);
        mOptionsView = (SuperListView) mView.findViewById(R.id.rl_publish_candidate);
        mOptionsAdapter = new OptionsAdapter(getActivity());
        mOptionsView.setAdapter(mOptionsAdapter);
        mOptionsView.setFocusable(false);
        mAddBtn.setOnClickListener(this);
        mPublishBtn.setOnClickListener(this);
        mPublishCover.setOnClickListener(this);
    }

    @Override
    public void addOptions() {
        startActivityForResult(new Intent(getActivity(), AddOptionsActivity.class), REQUEST_CODE_ADD_OPTION);
    }

    @Override
    public void refreshOptionList() {
        mOptionsAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteOption(int index) {
        mOptions.remove(index);
        refreshOptionList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADD_OPTION:
                    mOptions.add(data.getStringExtra("option"));
                    refreshOptionList();
                    break;
                case SELECT_ORIGINAL_PIC:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();
                        mCoverFile = new File(picturePath);
                        Glide.with(this).load(mCoverFile).into(mCover);
                        mPublishCover.setVisibility(View.GONE);
                        mAddCoverTxt.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    }

    @Override
    public void publishVote() {
        String title = mPublishTitle.getText().toString();
        String content = mPublishDetails.getText().toString();
        if (TextUtils.isEmpty(title)) {
            HelpUtils.showToast(R.string.action_edit_title);
        } else if (TextUtils.isEmpty(content)) {
            HelpUtils.showToast(R.string.action_edit_details);
        } else if (mOptions.isEmpty()) {
            HelpUtils.showToast(R.string.action_edit_option);
        } else if (mCoverFile == null) {
            HelpUtils.showToast(R.string.action_add_cover);
        } else {
            BmobUser currentUser = BmobUser.getCurrentUser();
            String userName = currentUser.getUsername();
            String phone = currentUser.getMobilePhoneNumber();
            mPublishVotePresenter.doPublic(title, content, mCoverFile, mOptions, phone, userName);
        }
    }

    @Override
    public void publishSuccess() {
        HelpUtils.showToast(R.string.success_publish);
        mPublishTitle.setText("");
        mPublishDetails.setText("");
        mCover.setImageDrawable(null);
        mCoverFile = null;
        mPublishCover.setVisibility(View.VISIBLE);
        mAddCoverTxt.setVisibility(View.VISIBLE);
        mOptions.clear();
        mOptionsAdapter.notifyDataSetChanged();
        HomeActivity activity = (HomeActivity) getActivity();
        activity.setTabSelection(INDEX_TAB_ZERO);
    }

    @Override
    public void publishFailed() {
        HelpUtils.showToast(R.string.error_publish);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_options:
                addOptions();
                break;
            case R.id.btn_publish:
                publishVote();
                break;
            case R.id.iv_publish_cover:
                selectPicFromGallery();
                break;
        }
    }


    @Override
    public void selectPicFromGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_ORIGINAL_PIC);
    }

    private class OptionsAdapter extends CommonAdapter<String> {

        OptionsAdapter(Context context) {
            super(context, R.layout.item_create_option, mOptions);
        }

        @Override
        public void convert(final ViewHolder viewHolder, String s) {
            viewHolder.setText(R.id.option_text, s);
            viewHolder.getView(R.id.delete_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteOption(viewHolder.getPosition());
                }
            });
        }
    }
}

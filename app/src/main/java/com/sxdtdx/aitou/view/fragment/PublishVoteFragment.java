package com.sxdtdx.aitou.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sxdtdx.aitou.R;
import com.sxdtdx.aitou.presenter.PublishVotePresenter;
import com.sxdtdx.aitou.utils.HelpUtils;
import com.sxdtdx.aitou.view.interfaces.IPublishVote;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class PublishVoteFragment extends Fragment implements IPublishVote {

    private View mView;
    private EditText mPublishTitle;
    private EditText mPublishDetails;
    private ImageView mPublishCover;
    private Button mPublishBtn;
    private PublishVotePresenter mPublishVotePresenter;
    List<String> mOptions;

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
        mPublishDetails = (EditText) mView.findViewById(R.id.et_publish_details);
        mPublishBtn = (Button) mView.findViewById(R.id.btn_publish);
    }

    @Override
    public void addOptions() {

    }

    @Override
    public void deleteOption(int index) {

    }

    @Override
    public void publishVote() {
        String title = mPublishTitle.getText().toString();
        String content = mPublishDetails.getText().toString();
        if (TextUtils.isEmpty(title)) {
            HelpUtils.showToast(R.string.action_edit_title);
        } else if (TextUtils.isEmpty(content)) {
            HelpUtils.showToast(R.string.action_edit_details);
        } else {
            BmobUser currentUser = BmobUser.getCurrentUser();
            String userName = currentUser.getUsername();
            String phone = currentUser.getMobilePhoneNumber();
            File cover = null;
            mPublishVotePresenter.doPublic(title, content, cover, mOptions, phone, userName);
        }
    }

    @Override
    public void publishSuccess() {
        HelpUtils.showToast(R.string.success_publish);
    }

    @Override
    public void publishFailed() {
        HelpUtils.showToast(R.string.success_publish);

    }
}

package com.sxdtdx.aitou.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxdtdx.aitou.R;
import com.sxdtdx.aitou.model.bean.Option;
import com.sxdtdx.aitou.model.bean.VoteDetails;
import com.sxdtdx.aitou.model.bean.Votes;
import com.sxdtdx.aitou.presenter.VoteDetailPresenter;
import com.sxdtdx.aitou.utils.HelpUtils;
import com.sxdtdx.aitou.utils.ThreadUtils;
import com.sxdtdx.aitou.view.SuperListView;
import com.sxdtdx.aitou.view.interfaces.IVoteDetail;
import com.sxdtdx.aitou.view.utils.CommonAdapter;
import com.sxdtdx.aitou.view.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.sxdtdx.aitou.view.fragment.VoteListFragment.EXTRA_VOTE_ID;

public class VoteDetailsActivity extends AppCompatActivity implements IVoteDetail {

    private static final String TAG = "VoteDetailsActivity";
    private TextView mVoteTitle;
    private TextView mVoteTime;
    private TextView mVoteContent;
    private ImageView mVoteCover;
    private TextView mVotedPCount;
    private SuperListView mVoteList;
    private Button voteBtn;
    private VoteDetailPresenter mVoteDetailPresenter;
    private List<Option> mOptions = new ArrayList<>();
    private ColumnAdapter mAdapter;
    private String mVotedId;
    private String mVoteObjectId;
    private List<String> VoteOptionNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_details);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initView();
        mVoteDetailPresenter = new VoteDetailPresenter(this);
        mVotedId = getIntent().getStringExtra(EXTRA_VOTE_ID);
        getVote(mVotedId);
    }

    @Override
    public void initView() {
        TextView title = (TextView) findViewById(R.id.title_text);
        title.setText(R.string.title_details);
        mVoteTitle = (TextView) findViewById(R.id.tv_vote_details_title);
        mVoteTime = (TextView) findViewById(R.id.tv_vote_details_time);
        mVoteContent = (TextView) findViewById(R.id.tv_vote_details_content);
        mVoteCover = (ImageView) findViewById(R.id.iv_vote_details_cover);
        mVotedPCount = (TextView) findViewById(R.id.tv_vote_details_voted_count);
        mVoteList = (SuperListView) findViewById(R.id.slv_vote_details_options);
        voteBtn = (Button) findViewById(R.id.btn_vote);
        voteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VoteOptionNames.size() > 1) {
                    HelpUtils.showToast(R.string.toast_chose_limit);
                    return;
                }
                doVote(BmobUser.getCurrentUser().getObjectId(), mVoteObjectId, VoteOptionNames.get(0));
            }
        });
        mAdapter = new ColumnAdapter(this);
        mVoteList.setAdapter(mAdapter);
    }

    @Override
    public void getVote(String subjectId) {
        mVoteDetailPresenter.getVoteDetails(subjectId);
    }

    @Override
    public void initData(final Votes voteBean) {
        mVoteObjectId = voteBean.getObjectId();
        mVoteTitle.setText(voteBean.getTitle());
        mVoteTime.setText(voteBean.getTime());
        mVoteContent.setText(voteBean.getDescribe());
        Glide.with(this).load(voteBean.getCover()).into(mVoteCover);

        final List<String> optionNames = voteBean.getOptions();

        BmobQuery<VoteDetails> query = new BmobQuery<>();
        query.addWhereEqualTo("voteId", mVotedId);
        query.findObjects(new FindListener<VoteDetails>() {
            @Override
            public void done(List<VoteDetails> list, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "list: " + list);
                    for (String name : optionNames) {
                        final Option option = new Option();
                        option.setName(name);

                        int count = 0;
                        boolean isSelected = false;
                        for (int i = 0; i < list.size(); i++) {
                            if (name.equals(list.get(i).getOptionName())) {
                                count = count + 1;
                                if (BmobUser.getCurrentUser().getObjectId().equals(list.get(i).getUserId())) {
                                    isSelected = true;
                                }
                            }
                        }
                        option.setVotedCount(count);
                        option.setSelected(isSelected);
                        mOptions.add(option);
                    }
                    Log.d(TAG, "options: " + mOptions);
                    ThreadUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                            voteBtn.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    Log.d(TAG, "Exception: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void doVote(String userId, String subjectId, String optionName) {
        mVoteDetailPresenter.doVote(userId, subjectId, optionName);
    }

    @Override
    public void refreshBtn() {
        voteBtn.setEnabled(false);
        voteBtn.setText(R.string.voted);
        HelpUtils.showToast(R.string.vote_voted);
    }

    private class ColumnAdapter extends CommonAdapter<Option> {

        ColumnAdapter(Context context) {
            super(context, R.layout.item_vote_detail_option, mOptions);
        }

        @Override
        public void convert(ViewHolder viewHolder, final Option option) {
            viewHolder.setText(R.id.option_count, String.valueOf(option.getVotedCount()));
            viewHolder.setText(R.id.option_name, option.getName());
            CheckBox checkBox = viewHolder.getView(R.id.checkbox_option);
            checkBox.setChecked(option.isSelected());
            if (option.isSelected()) {
                voteBtn.setEnabled(false);
                voteBtn.setText(R.string.voted);
                checkBox.setClickable(false);
            }
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        VoteOptionNames.add(option.getName());
                    } else {
                        VoteOptionNames.remove(option.getName());
                    }
                }
            });
        }
    }

}

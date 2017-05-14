package com.sxdtdx.aitou.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxdtdx.aitou.R;
import com.sxdtdx.aitou.model.bean.Option;
import com.sxdtdx.aitou.model.bean.Votes;
import com.sxdtdx.aitou.presenter.VoteDetailPresenter;
import com.sxdtdx.aitou.utils.HelpUtils;
import com.sxdtdx.aitou.view.SuperListView;
import com.sxdtdx.aitou.view.interfaces.IVoteDetail;
import com.sxdtdx.aitou.view.utils.CommonAdapter;
import com.sxdtdx.aitou.view.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

import static com.sxdtdx.aitou.view.fragment.VoteListFragment.EXTRA_VOTE_ID;

public class VoteDetailsActivity extends AppCompatActivity implements IVoteDetail{

    private TextView mVoteTitle;
    private TextView mVoteTime;
    private TextView mVoteContent;
    private ImageView mVoteCover;
    private TextView mVotedPCount;
    private SuperListView mVoteList;
    private Button voteBtn;
    private VoteDetailPresenter mVoteDetailPresenter;
    private List<Option> mOptions = new ArrayList<>();;
    private ColumnAdapter mAdapter;

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
        getVote(getIntent().getStringExtra(EXTRA_VOTE_ID));
    }

    @Override
    public void initView() {
        TextView title = (TextView) findViewById(R.id.title_text);
        title.setText("详情");
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
                doVote(BmobUser.getCurrentUser().getObjectId(), "","");
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
    public void initData(Votes voteBean) {
        mVoteTitle.setText(voteBean.getTitle());
        mVoteTime.setText(voteBean.getTime());
        mVoteContent.setText(voteBean.getDescribe());
        Glide.with(this).load(voteBean.getCover()).into(mVoteCover);
        for (String name : voteBean.getOptions()) {
            Option option = new Option();
            option.setName(name);
            mOptions.add(option);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void doVote(String userId, String subjectId, String optionName) {
        mVoteDetailPresenter.doVote(userId, subjectId, optionName);
    }

    @Override
    public void refreshBtn() {
        voteBtn.setEnabled(false);
        voteBtn.setText("已投票");
        HelpUtils.showToast("投票成功");
    }

    class ColumnAdapter extends CommonAdapter<Option> {

        public ColumnAdapter(Context context) {
            super(context, R.layout.item_vote_detail_option, mOptions);
        }

        @Override
        public void convert(ViewHolder viewHolder, final Option option) {
            viewHolder.setText(R.id.option_text, option.getName());
            CheckBox checkBox = viewHolder.getView(R.id.checkbox_option);
            checkBox.setSelected(option.isSelected());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    option.setSelected(b);
                }
            });
        }

    }

}

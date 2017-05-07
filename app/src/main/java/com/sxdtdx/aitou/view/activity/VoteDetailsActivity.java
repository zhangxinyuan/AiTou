package com.sxdtdx.aitou.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxdtdx.aitou.R;
import com.sxdtdx.aitou.model.bean.PublicVote;
import com.sxdtdx.aitou.presenter.VoteDetailPresenter;
import com.sxdtdx.aitou.view.SuperListView;
import com.sxdtdx.aitou.view.interfaces.IVoteDetail;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_details);
        initView();
        mVoteDetailPresenter = new VoteDetailPresenter(this);
        getVote(getIntent().getStringExtra(EXTRA_VOTE_ID));
    }

    @Override
    public void initView() {
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
    }

    @Override
    public void getVote(String subjectId) {
        mVoteDetailPresenter.getVoteDetails(subjectId);
    }

    @Override
    public void initData(PublicVote voteBean) {
        mVoteTitle.setText(voteBean.getTitle());
        mVoteTime.setText(voteBean.getTime());
        mVoteContent.setText(voteBean.getDescribe());
        Glide.with(this).load(voteBean.getCover()).into(mVoteCover);

    }

    @Override
    public void doVote(String userId, String subjectId, String optionName) {
        mVoteDetailPresenter.doVote(userId, subjectId, optionName);
    }

    @Override
    public void refreshBtn() {

    }
}

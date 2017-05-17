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
import android.widget.ListAdapter;
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
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;

import static com.sxdtdx.aitou.view.fragment.VoteListFragment.EXTRA_VOTE_ID;

public class VoteDetailsActivity extends AppCompatActivity implements IVoteDetail{

    private static final String TAG = "VoteDetailsActivity";
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
    private String mVotedId;

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
            final Option option = new Option();
            option.setName(name);
            BmobQuery<VoteDetails> query1 = new BmobQuery<VoteDetails>();
            query1.addWhereEqualTo("voteId", mVotedId);
            BmobQuery<VoteDetails> query2 = new BmobQuery<VoteDetails>();
            query2.addWhereEqualTo("optionName", name);

            List<BmobQuery<VoteDetails>> queryList = new ArrayList<BmobQuery<VoteDetails>>();
            queryList.add(query1);
            queryList.add(query2);

            BmobQuery<VoteDetails> query = new BmobQuery<VoteDetails>();
            query.and(queryList);
            query.count(VoteDetails.class, new CountListener() {
                @Override
                public void done(Integer integer, BmobException e) {
                    if (e == null) {
                        Log.d(TAG, "COUNT: " + integer);
                        option.setVoteds(integer);
                        mOptions.add(option);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Log.d(TAG, "BmobException: " + e.getMessage());
                    }
                }
            });
        }

        BmobQuery<VoteDetails> query1 = new BmobQuery<VoteDetails>();
        query1.addWhereEqualTo("voteId", mVotedId);
        BmobQuery<VoteDetails> query2 = new BmobQuery<VoteDetails>();
        query2.addWhereEqualTo("userId", BmobUser.getCurrentUser().getMobilePhoneNumber());

        List<BmobQuery<VoteDetails>> queryList = new ArrayList<BmobQuery<VoteDetails>>();
        queryList.add(query1);
        queryList.add(query2);

        BmobQuery<VoteDetails> query = new BmobQuery<VoteDetails>();
        query.and(queryList);
        query.findObjects(new FindListener<VoteDetails>() {
            @Override
            public void done(List<VoteDetails> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.isEmpty()) {
                        ThreadUtils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                voteBtn.setEnabled(false);
                                voteBtn.setText("已投票");
                            }
                        });
                    }
                } else {
                    Log.d(TAG, "BmobException: " + e.getMessage());
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

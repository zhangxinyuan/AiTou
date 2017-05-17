package com.sxdtdx.aitou.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxdtdx.aitou.R;
import com.sxdtdx.aitou.model.bean.Votes;
import com.sxdtdx.aitou.model.interfaces.OnItemClickListener;
import com.sxdtdx.aitou.presenter.VoteListPresenter;
import com.sxdtdx.aitou.view.interfaces.IVoteList;

import java.util.ArrayList;
import java.util.List;

public class VotesActivity extends AppCompatActivity implements IVoteList {
    public static final String EXTRA_VOTE_ID = "vote_id";
    private List<Votes> mVotes = new ArrayList<>();
    private VoteListPresenter mVoteListPresenter;
    private VoteListAdapter mVoteListAdapter;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_list);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initView();
        mVoteListPresenter = new VoteListPresenter(this);
        requestData();
    }

    @Override
    public void initView() {
        TextView title = (TextView) findViewById(R.id.title_text);
        title.setText("我的发布");
        RecyclerView mVoteList = (RecyclerView) findViewById(R.id.rl_vote_list);
        mVoteList.setLayoutManager(new LinearLayoutManager(this));
        mVoteListAdapter = new VoteListAdapter();
        mVoteList.setAdapter(mVoteListAdapter);
        mVoteListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                turnToDetailsPage(mVotes.get(position).getObjectId());
            }
        });
    }

    @Override
    public void requestData() {
        mVoteListPresenter.requestPersonalData();
    }

    @Override
    public void refreshLis(List<Votes> votes) {
        mVotes = votes;
        mVoteListAdapter.notifyDataSetChanged();
    }

    @Override
    public void turnToDetailsPage(String voteId) {
        Intent intent = new Intent(VotesActivity.this, VoteDetailsActivity.class);
        intent.putExtra(EXTRA_VOTE_ID, voteId);
        startActivity(intent);
    }

    private class VoteListAdapter extends RecyclerView.Adapter<VoteListAdapter.MyHolder> {
        private OnItemClickListener onItemClickListener;

        @Override
        public VoteListAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            VoteListAdapter.MyHolder holder = new VoteListAdapter.MyHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_fragment_vote_list,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final VoteListAdapter.MyHolder holder, final int position) {

            Votes vote = mVotes.get(position);
            Glide.with(context).load(vote.getCover()).into(holder.mVoteCover);
            holder.mVoteTitle.setText(vote.getTitle());
            holder.mVoteTime.setText(vote.getTime());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        int pos = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(view, pos);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mVotes == null ? 0 : mVotes.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder{

            private final ImageView mVoteCover;
            private final TextView mVoteTitle;
            private final TextView mVoteTime;

            public MyHolder(View itemRootView) {
                super(itemRootView);
                mVoteCover = (ImageView) itemRootView.findViewById(R.id.ll_vote_list_cover);
                mVoteTitle = (TextView) itemRootView.findViewById(R.id.tv_vote_list_title);
                mVoteTime = (TextView) itemRootView.findViewById(R.id.tv_vote_list_time);
            }
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }
    }
}

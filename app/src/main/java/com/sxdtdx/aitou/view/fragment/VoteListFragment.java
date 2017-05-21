package com.sxdtdx.aitou.view.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
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
import com.sxdtdx.aitou.view.activity.VoteDetailsActivity;
import com.sxdtdx.aitou.view.interfaces.IVoteList;

import java.util.ArrayList;
import java.util.List;

public class VoteListFragment extends Fragment implements IVoteList{

    public static final String EXTRA_VOTE_ID = "vote_id";

    private View mView;
    private List<Votes> mVotes = new ArrayList<>();
    private VoteListPresenter mVoteListPresenter;
    private VoteListAdapter mVoteListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_vote_list, container, false);
        initView();
        mVoteListPresenter = new VoteListPresenter(this);
        requestData();
        return mView;
    }

    @Override
    public void initView() {
        final RecyclerView mVoteList = (RecyclerView) mView.findViewById(R.id.rl_vote_list);
        mVoteList.setLayoutManager(new LinearLayoutManager(getActivity()));
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            requestData();
        }
    }

    @Override
    public void requestData() {
        mVoteListPresenter.requestData();
    }

    @Override
    public void refreshLis(List<Votes> votes) {
        mVotes = votes;
        mVoteListAdapter.notifyDataSetChanged();
    }

    @Override
    public void turnToDetailsPage(String voteId) {
        Intent intent = new Intent(getActivity(), VoteDetailsActivity.class);
        intent.putExtra(EXTRA_VOTE_ID, voteId);
        startActivity(intent);
    }


    private class VoteListAdapter extends RecyclerView.Adapter<VoteListAdapter.MyHolder> {
        private OnItemClickListener onItemClickListener;

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_fragment_vote_list,parent,false));
        }

        @Override
        public void onBindViewHolder(final MyHolder holder, final int position) {

            Votes vote = mVotes.get(position);
            Glide.with(getActivity()).load(vote.getCover()).into(holder.mVoteCover);
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

        class MyHolder extends RecyclerView.ViewHolder{

            private final ImageView mVoteCover;
            private final TextView mVoteTitle;
            private final TextView mVoteTime;

            MyHolder(View itemRootView) {
                super(itemRootView);
                mVoteCover = (ImageView) itemRootView.findViewById(R.id.ll_vote_list_cover);
                mVoteTitle = (TextView) itemRootView.findViewById(R.id.tv_vote_list_title);
                mVoteTime = (TextView) itemRootView.findViewById(R.id.tv_vote_list_time);
            }
        }

        void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }
    }
}

package com.sxdtdx.aitou.presenter;

import com.sxdtdx.aitou.model.bean.PublicVote;
import com.sxdtdx.aitou.model.bizs.VoteBiz;
import com.sxdtdx.aitou.model.interfaces.CallBack;
import com.sxdtdx.aitou.view.interfaces.IVoteList;

import java.util.List;

/**
 * Created by zxy on 17-5-7.
 */

public class VoteListPresenter {
    private IVoteList iVoteList;
    private final VoteBiz mVoteBiz;

    public VoteListPresenter(IVoteList iVoteList) {
        this.iVoteList = iVoteList;
        mVoteBiz = new VoteBiz();
    }

    public void requestData() {

        mVoteBiz.getVoteDataList(new CallBack<List<PublicVote>>() {
            @Override
            public void onSuccess(List<PublicVote> result) {
                iVoteList.refreshLis(result);
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }
}

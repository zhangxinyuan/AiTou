package com.sxdtdx.aitou.presenter;

import com.sxdtdx.aitou.model.bean.Votes;
import com.sxdtdx.aitou.model.bizs.VoteBiz;
import com.sxdtdx.aitou.model.interfaces.CallBack;
import com.sxdtdx.aitou.view.interfaces.IVoteDetail;

/**
 * Created by xinyuan on 17-5-7.
 */

public class VoteDetailPresenter {
    private IVoteDetail iVoteDetail;
    private final VoteBiz voteBiz;

    public VoteDetailPresenter(IVoteDetail iVoteDetail) {
        this.iVoteDetail = iVoteDetail;
        voteBiz = new VoteBiz();
    }

    public void getVoteDetails(String subjectId) {
        voteBiz.getVoteDetails(subjectId, new CallBack<Votes>() {
            @Override
            public void onSuccess(Votes result) {
                iVoteDetail.initData(result);
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    public void doVote(String userId, String subjectId, String optionName) {
        voteBiz.doVote(userId, subjectId, optionName, new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                iVoteDetail.refreshBtn();
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }
}

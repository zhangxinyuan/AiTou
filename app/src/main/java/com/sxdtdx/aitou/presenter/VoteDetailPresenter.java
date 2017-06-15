package com.sxdtdx.aitou.presenter;

import com.sxdtdx.aitou.model.bean.VoteInfo;
import com.sxdtdx.aitou.model.bizs.VoteBiz;
import com.sxdtdx.aitou.model.interfaces.CallBack;
import com.sxdtdx.aitou.model.interfaces.IVoteBiz;
import com.sxdtdx.aitou.view.interfaces.IVoteDetail;

/**
 * Created by xinyuan on 17-5-7.
 */

public class VoteDetailPresenter {
    private IVoteDetail iVoteDetail;
    private IVoteBiz voteBiz;

    public VoteDetailPresenter(IVoteDetail iVoteDetail) {
        this.iVoteDetail = iVoteDetail;
        voteBiz = new VoteBiz();
    }

    public void getVoteDetails(String subjectId) {
        voteBiz.getVoteDetails(subjectId, new CallBack<VoteInfo>() {
            @Override
            public void onSuccess(final VoteInfo result) {
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

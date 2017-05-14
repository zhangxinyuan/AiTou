package com.sxdtdx.aitou.presenter;

import com.sxdtdx.aitou.model.bean.Votes;
import com.sxdtdx.aitou.model.bizs.VoteBiz;
import com.sxdtdx.aitou.model.interfaces.CallBack;
import com.sxdtdx.aitou.view.interfaces.IVoteList;

import java.util.List;

import cn.bmob.v3.BmobUser;

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

        mVoteBiz.getVoteDataList(new CallBack<List<Votes>>() {
            @Override
            public void onSuccess(List<Votes> result) {
                iVoteList.refreshLis(result);
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    public void requestPersonalData() {

        mVoteBiz.getVoteDataList(BmobUser.getCurrentUser().getMobilePhoneNumber(), new CallBack<List<Votes>>() {
            @Override
            public void onSuccess(List<Votes> result) {
                iVoteList.refreshLis(result);
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }
}

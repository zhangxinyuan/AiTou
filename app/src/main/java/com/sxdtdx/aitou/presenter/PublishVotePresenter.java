package com.sxdtdx.aitou.presenter;

import com.sxdtdx.aitou.model.bizs.VoteBiz;
import com.sxdtdx.aitou.model.interfaces.CallBack;
import com.sxdtdx.aitou.utils.HelpUtils;
import com.sxdtdx.aitou.view.interfaces.IPublishVote;

import java.io.File;
import java.util.List;

/**
 * Created by zhangxinyuan on 2017/5/4.
 */

public class PublishVotePresenter {

    private IPublishVote mIPublishVote;
    private final VoteBiz mVoteBiz;

    public PublishVotePresenter(IPublishVote iPublishVote) {
        mVoteBiz = new VoteBiz();
        this.mIPublishVote = iPublishVote;
    }

    public void doPublic(String subject, String content, File cover, List<String> options, String userId, String
            userName) {

        mVoteBiz.publicVote(subject, content, cover, options, HelpUtils.getCurrentDate(), userId, userName, new
                CallBack<String>() {

            @Override
            public void onSuccess(String result) {
                mIPublishVote.publishSuccess();
            }

            @Override
            public void onFailed(String error) {
                mIPublishVote.publishFailed();
            }
        });
    }
}

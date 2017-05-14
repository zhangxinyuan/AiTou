package com.sxdtdx.aitou.view.interfaces;

import com.sxdtdx.aitou.model.bean.Votes;

/**
 * Created by xinyuan on 17-5-7.
 */

public interface IVoteDetail {
    void initView();
    void getVote(String subjectId);
    void initData(Votes voteBean);
    void doVote(String userId, String subjectId,String optionName);
    void refreshBtn();
}

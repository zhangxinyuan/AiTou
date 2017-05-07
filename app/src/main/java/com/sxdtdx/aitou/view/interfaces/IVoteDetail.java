package com.sxdtdx.aitou.view.interfaces;

import com.sxdtdx.aitou.model.bean.PublicVote;

/**
 * Created by xinyuan on 17-5-7.
 */

public interface IVoteDetail {
    void initView();
    void getVote(String subjectId);
    void initData(PublicVote voteBean);
    void doVote(String userId, String subjectId,String optionName);
    void refreshBtn();
}

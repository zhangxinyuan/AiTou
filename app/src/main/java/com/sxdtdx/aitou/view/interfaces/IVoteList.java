package com.sxdtdx.aitou.view.interfaces;

import com.sxdtdx.aitou.model.bean.PublicVote;

import java.util.List;

/**
 * Created by xinyuan on 17-5-7.
 */

public interface IVoteList {
    void initView();
    void requestData();
    void refreshLis(List<PublicVote> votes);
    void turnToDetailsPage(String objectId);
}

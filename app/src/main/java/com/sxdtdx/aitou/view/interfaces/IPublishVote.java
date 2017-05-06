package com.sxdtdx.aitou.view.interfaces;

/**
 * Created by zhangxinyuan on 2017/5/4.
 */

public interface IPublishVote {
    void initView();
    void addOptions();
    void deleteOption(int index);
    void publishVote();
    void publishSuccess();
    void publishFailed();
}

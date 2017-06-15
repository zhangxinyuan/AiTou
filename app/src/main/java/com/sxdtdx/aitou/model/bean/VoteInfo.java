package com.sxdtdx.aitou.model.bean;

import java.util.List;

/**
 * Created by zhangxinyuan on 2017/6/15.
 */

public class VoteInfo {
    private Votes voteMsg;
    private List<Option> optionsMsg;

    public Votes getVoteMsg() {
        return voteMsg;
    }

    public void setVoteMsg(Votes voteMsg) {
        this.voteMsg = voteMsg;
    }

    public List<Option> getOptionsMsg() {
        return optionsMsg;
    }

    public void setOptionsMsg(List<Option> optionsMsg) {
        this.optionsMsg = optionsMsg;
    }
}

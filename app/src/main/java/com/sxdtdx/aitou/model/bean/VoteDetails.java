package com.sxdtdx.aitou.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xinyuan on 17-5-7.
 */

public class VoteDetails extends BmobObject{
    private String voteId;
    private String optionName;
    private String userId;

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

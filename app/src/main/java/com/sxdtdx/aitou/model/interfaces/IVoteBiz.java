package com.sxdtdx.aitou.model.interfaces;

import java.io.File;
import java.util.List;

/**
 * Created by zhangxinyuan on 2017/5/5.
 */

public interface IVoteBiz {
    void publicVote(String subject, String content, File cover, List<String> options, String date, String userId, String userName, CallBack<String> callBack);
}

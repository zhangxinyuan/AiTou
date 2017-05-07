package com.sxdtdx.aitou.model.interfaces;

import com.sxdtdx.aitou.model.bean.PublicVote;

import java.io.File;
import java.util.List;

/**
 * Created by zhangxinyuan on 2017/5/5.
 */

public interface IVoteBiz {
    void publicVote(String subject, String content, File cover, List<String> options, String date, String userId, String userName, CallBack<String> callBack);
    void getVoteDataList( CallBack<List<PublicVote>> callBack);
    void getVoteDataList(String userId,  CallBack<List<PublicVote>> callBack);
    void getVoteDetails(String subjectId, CallBack<PublicVote> callBack);

    void doVote(String userId, String subjectId, String optionName, CallBack<String> callBack);
}

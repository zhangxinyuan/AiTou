package com.sxdtdx.aitou.model.interfaces;

import com.sxdtdx.aitou.model.bean.Votes;

import java.io.File;
import java.util.List;

/**
 * Created by zhangxinyuan on 2017/5/5.
 */

public interface IVoteBiz {
    void publicVote(String subject, String content, File cover, List<String> options, String date, String userId, String userName, CallBack<String> callBack);
    void getVoteDataList( CallBack<List<Votes>> callBack);
    void getPersonalPublishVoteList(String userId, CallBack<List<Votes>> callBack);
    void getPersonalVotedList(String userId, CallBack<List<Votes>> callBack);
    void getVoteDetails(String subjectId, CallBack<Votes> callBack);
    void doVote(String userId, String subjectId, String optionName, CallBack<String> callBack);
}

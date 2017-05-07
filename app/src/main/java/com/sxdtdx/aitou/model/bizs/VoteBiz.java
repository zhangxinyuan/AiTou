package com.sxdtdx.aitou.model.bizs;

import com.sxdtdx.aitou.model.bean.PublicVote;
import com.sxdtdx.aitou.model.bean.VoteDetails;
import com.sxdtdx.aitou.model.interfaces.CallBack;
import com.sxdtdx.aitou.model.interfaces.IVoteBiz;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by zhangxinyuan on 2017/5/5.
 */

public class VoteBiz implements IVoteBiz {

    @Override
    public void publicVote(String subject, String content, File cover, List<String> options, String date, String
            userId, String userName, final CallBack<String> callBack) {
        PublicVote publicVote = new PublicVote();
        publicVote.setUserName(userName);
        publicVote.setPhone(userId);
        publicVote.setTitle(subject);
        publicVote.setDescribe(content);
        publicVote.setCover(cover);
        publicVote.setOptions(options);
        publicVote.setTime(date);
        publicVote.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    if (callBack != null) {
                        callBack.onFailed("发布出错");
                    }
                } else {
                    if (callBack != null) {
                        callBack.onSuccess("发布成功");
                    }
                }
            }
        });
    }

    @Override
    public void getVoteDataList(final CallBack<List<PublicVote>> callBack) {

        BmobQuery<PublicVote> query = new BmobQuery<>();
        query.findObjects(new FindListener<PublicVote>() {
            @Override
            public void done(List<PublicVote> list, BmobException e) {
                if (e == null) {
                    if (callBack != null) {
                        callBack.onFailed(e.getMessage());
                    }
                } else {
                    if (callBack != null) {
                        callBack.onSuccess(list);
                    }
                }
            }
        });
    }

    @Override
    public void getVoteDataList(String userId, CallBack<List<PublicVote>> callBack) {

    }

    @Override
    public void getVoteDetails(String subjectId, final CallBack<PublicVote> callBack) {
        BmobQuery<PublicVote> query = new BmobQuery<>();
        query.getObject(subjectId, new QueryListener<PublicVote>() {
            @Override
            public void done(PublicVote publicVote, BmobException e) {
                if (e == null) {
                    if (callBack != null) {
                        callBack.onSuccess(publicVote);
                    }

                } else {
                    if (callBack != null) {
                        callBack.onFailed("出错啦");
                    }
                }

            }
        });
    }

    @Override
    public void doVote(String userId, String subjectId, String optionName, final CallBack<String> callBack) {
        VoteDetails voteDetails = new VoteDetails();
        voteDetails.setUserId(userId);
        voteDetails.setOptionName(optionName);
        voteDetails.setVoteId(subjectId);
        voteDetails.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e != null) {
                    if (callBack != null) {
                        callBack.onSuccess("success");
                    }
                } else {
                    if (callBack != null) {
                        callBack.onFailed("error");
                    }
                }
            }
        });
    }

}

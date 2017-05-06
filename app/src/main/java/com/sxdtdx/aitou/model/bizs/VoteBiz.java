package com.sxdtdx.aitou.model.bizs;

import com.sxdtdx.aitou.model.bean.PublicVote;
import com.sxdtdx.aitou.model.interfaces.CallBack;
import com.sxdtdx.aitou.model.interfaces.IVoteBiz;

import java.io.File;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
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
}

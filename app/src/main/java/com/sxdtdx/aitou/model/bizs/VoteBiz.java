package com.sxdtdx.aitou.model.bizs;

import android.util.Log;

import com.sxdtdx.aitou.model.bean.Option;
import com.sxdtdx.aitou.model.bean.VoteDetails;
import com.sxdtdx.aitou.model.bean.VoteInfo;
import com.sxdtdx.aitou.model.bean.Votes;
import com.sxdtdx.aitou.model.interfaces.CallBack;
import com.sxdtdx.aitou.model.interfaces.IVoteBiz;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by zhangxinyuan on 2017/5/5.
 */

public class VoteBiz implements IVoteBiz {

    private static final String TAG = "VoteBiz";

    @Override
    public void publicVote(final String subject, final String content, final File cover, final List<String> options, final String date, final String
            userId, final String userName, final CallBack<String> callBack) {
        final BmobFile coverFile = new BmobFile(cover);
        coverFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    String fileUrl = coverFile.getFileUrl();
                    Votes votes = new Votes();
                    votes.setUserName(userName);
                    votes.setPhone(userId);
                    votes.setTitle(subject);
                    votes.setDescribe(content);
                    votes.setCover(fileUrl);
                    votes.setOptions(options);
                    votes.setTime(date);
                    votes.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                if (callBack != null) {
                                    callBack.onSuccess("发布成功");
                                }

                            } else {
                                if (callBack != null) {
                                    callBack.onFailed("发布出错");
                                }
                            }
                        }
                    });
                } else {
                    Log.e("error", e.getMessage());
                    if (callBack != null) {
                        callBack.onFailed("发布出错");
                    }
                }
            }
        });
    }

    @Override
    public void getVoteDataList(final CallBack<List<Votes>> callBack) {

        BmobQuery<Votes> query = new BmobQuery<Votes>();
        query.order("-createdAt");
        query.findObjects(new FindListener<Votes>() {
           @Override
           public void done(List<Votes> list, BmobException e) {
               if (e == null) {
                   if (callBack != null) {
                       callBack.onSuccess(list);
                   }
               } else {
                   Log.e("error: " , e.getMessage());
                   if (callBack != null) {
                       callBack.onFailed(e.getMessage());
                   }
               }
           }
       });
    }

    @Override
    public void getPersonalPublishVoteList(String userPhone, final CallBack<List<Votes>> callBack) {
        BmobQuery<Votes> query = new BmobQuery<Votes>();
        query.addWhereEqualTo("phone", userPhone);
        query.order("-createdAt");
        query.setLimit(500);
        query.findObjects(new FindListener<Votes>() {
            @Override
            public void done(List<Votes> list, BmobException e) {
                if (e == null) {
                    if (callBack != null) {
                        callBack.onSuccess(list);
                    }
                } else {
                    Log.e("error: " , e.getMessage());
                    if (callBack != null) {
                        callBack.onFailed(e.getMessage());
                    }
                }
            }
        });
    }

    @Override
    public void getPersonalVotedList(String userId, final CallBack<List<Votes>> callBack) {
        final BmobQuery<VoteDetails> query = new BmobQuery<VoteDetails>();
        query.addWhereEqualTo("userId", userId);
        query.setLimit(500);
        query.findObjects(new FindListener<VoteDetails>() {
            @Override
            public void done(List<VoteDetails> list, BmobException e) {
                if (e == null) {
                    List<String> voteId = new ArrayList<String>();
                    for (VoteDetails voteDetail: list) {
                        voteId.add(voteDetail.getVoteId());
                    }
                    BmobQuery<Votes> query2 = new BmobQuery<Votes>();
                    query2.addWhereContainedIn("objectId", voteId);
                    query2.order("-createdAt");
                    query2.setLimit(500);
                    query2.findObjects(new FindListener<Votes>() {
                        @Override
                        public void done(List<Votes> list, BmobException e) {
                            if (e == null) {
                                if (callBack != null) {
                                    callBack.onSuccess(list);
                                }
                            } else {
                                Log.e("error: " , e.getMessage());
                                if (callBack != null) {
                                    callBack.onFailed(e.getMessage());
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getVoteDetails(final String subjectId, final CallBack<VoteInfo> callBack) {
        BmobQuery<Votes> query = new BmobQuery<>();
        query.getObject(subjectId, new QueryListener<Votes>() {
            @Override
            public void done(final Votes votes, BmobException e) {
                if (e == null) {
                    getVoteCountInfo(votes, subjectId, callBack);
                } else {
                    if (callBack != null) {
                        callBack.onFailed("出错啦");
                    }
                }
            }
        });
    }

    private void getVoteCountInfo(final Votes votes, String subjectId, final CallBack<VoteInfo> callBack) {
        final List<String> optionNames = votes.getOptions();
        BmobQuery<VoteDetails> query = new BmobQuery<>();
        query.addWhereEqualTo("voteId", subjectId);
        query.findObjects(new FindListener<VoteDetails>() {
            @Override
            public void done(List<VoteDetails> list, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "list: " + list);
                    List<Option> options = new ArrayList<>();
                    for (String name : optionNames) {
                        final Option option = new Option();
                        option.setName(name);

                        int count = 0;
                        boolean isSelected = false;
                        for (int i = 0; i < list.size(); i++) {
                            if (name.equals(list.get(i).getOptionName())) {
                                count = count + 1;
                                if (BmobUser.getCurrentUser().getObjectId().equals(list.get(i).getUserId())) {
                                    isSelected = true;
                                }
                            }
                        }
                        option.setVotedCount(count);
                        option.setSelected(isSelected);
                        options.add(option);
                    }
                    VoteInfo voteInfo = new VoteInfo();
                    voteInfo.setVoteMsg(votes);
                    voteInfo.setOptionsMsg(options);
                    Log.d(TAG, "options: " + options);
                    if (callBack != null) {
                        callBack.onSuccess(voteInfo);
                    }
                } else {
                    Log.d(TAG, "Exception: " + e.getMessage());
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
        voteDetails.setVoteId(subjectId);
        voteDetails.setUserId(userId);
        voteDetails.setOptionName(optionName);
        voteDetails.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
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

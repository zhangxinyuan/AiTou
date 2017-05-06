package com.sxdtdx.aitou.model.bizs;

import com.sxdtdx.aitou.MyApplication;
import com.sxdtdx.aitou.R;
import com.sxdtdx.aitou.model.interfaces.CallBack;
import com.sxdtdx.aitou.model.interfaces.ILoginAndRegisterBiz;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by lenovo on 2017/4/26.
 */

public class LoginAndRegisterBiz implements ILoginAndRegisterBiz {

    @Override
    public void goLogin(String phone, String passWord, final CallBack<String> callBack) {
        BmobUser user = new BmobUser();
        user.setMobilePhoneNumber(phone);
        user.setPassword(passWord);
        user.login(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    if (callBack != null) {
                        callBack.onSuccess(MyApplication.getContext().getString(R.string.success_login));
                    }
                } else {
                    if (callBack != null) {
                        callBack.onFailed(MyApplication.getContext().getString(R.string.error_login_error));
                    }
                }
            }
        });
    }

    @Override
    public void goRegister(String pickName, String phone, String passWord, final CallBack<BmobUser> callBack) {
        BmobUser user = new BmobUser();
        user.setUsername(pickName);
        user.setMobilePhoneNumber(phone);
        user.setPassword(passWord);
        user.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    if (callBack != null) {
                        callBack.onSuccess(bmobUser);
                    }
                } else {
                    if (callBack != null) {
                        callBack.onFailed(e.getMessage());
                    }
                }
            }
        });
    }
}

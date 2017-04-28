package com.sxdtdx.aitou.presenter;

import com.sxdtdx.aitou.model.bean.User;
import com.sxdtdx.aitou.model.biz.LoginAndRegisterBiz;
import com.sxdtdx.aitou.model.biz.interfaces.CallBack;
import com.sxdtdx.aitou.view.interfaces.ILogin;

/**
 * Created by lenovo on 2017/4/26.
 */

public class LoginPresenter {

    private ILogin mILogin;
    private final LoginAndRegisterBiz mLoginAndRegisterBiz;

    public LoginPresenter(ILogin iLogin) {
        this.mILogin = iLogin;
        mLoginAndRegisterBiz = new LoginAndRegisterBiz();
    }

    public void attemptLogin(String account, String passWord) {
        mLoginAndRegisterBiz.goLogin(account, passWord, new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                mILogin.turnToMain();
            }

            @Override
            public void onFailed(String error) {
                mILogin.loginFailed();
            }
        });
    }
}

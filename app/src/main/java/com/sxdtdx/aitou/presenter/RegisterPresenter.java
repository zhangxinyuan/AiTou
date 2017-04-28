package com.sxdtdx.aitou.presenter;

import com.sxdtdx.aitou.model.bean.User;
import com.sxdtdx.aitou.model.biz.LoginAndRegisterBiz;
import com.sxdtdx.aitou.model.biz.interfaces.CallBack;
import com.sxdtdx.aitou.view.interfaces.IRegister;

/**
 * Created by lenovo on 2017/4/26.
 */

public class RegisterPresenter{

    private IRegister mIRegister;
    private final LoginAndRegisterBiz mLoginAndRegisterBiz;

    public RegisterPresenter(IRegister iRegister) {
        this.mIRegister = iRegister;
        mLoginAndRegisterBiz = new LoginAndRegisterBiz();
    }

    public void attemptRegister(String pickName, String account, String passWord) {
        mLoginAndRegisterBiz.goRegister(pickName, account, passWord, new CallBack<User>() {
            @Override
            public void onSuccess(User result) {
                mIRegister.registerSuccess();
            }

            @Override
            public void onFailed(String error) {
                mIRegister.registerFailed(error);
            }
        });
    }
}

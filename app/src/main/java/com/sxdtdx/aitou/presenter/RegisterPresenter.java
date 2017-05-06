package com.sxdtdx.aitou.presenter;

import com.sxdtdx.aitou.model.bizs.LoginAndRegisterBiz;
import com.sxdtdx.aitou.model.interfaces.CallBack;
import com.sxdtdx.aitou.view.interfaces.IRegister;

import cn.bmob.v3.BmobUser;

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
        mLoginAndRegisterBiz.goRegister(pickName, account, passWord, new CallBack<BmobUser>() {
            @Override
            public void onSuccess(BmobUser result) {
                mIRegister.registerSuccess();
            }

            @Override
            public void onFailed(String error) {
                mIRegister.registerFailed(error);
            }
        });
    }
}

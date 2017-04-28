package com.sxdtdx.aitou.view.interfaces;

/**
 * Created by lenovo on 2017/4/26.
 */

public interface ILogin {
    void initView();
    void attemptLogin();
    void loginFailed();
    void turnToMain();
    void turnToRegister();
}

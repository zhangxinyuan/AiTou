package com.sxdtdx.aitou.view.interfaces;

/**
 * Created by lenovo on 2017/4/26.
 */

public interface IRegister {
    void initView();

    void attemptRegister();

    void registerSuccess();

    void registerFailed(String errorMsg);
}

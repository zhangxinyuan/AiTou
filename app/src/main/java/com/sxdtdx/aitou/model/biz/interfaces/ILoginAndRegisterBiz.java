package com.sxdtdx.aitou.model.biz.interfaces;

import com.sxdtdx.aitou.model.bean.User;

/**
 * Created by lenovo on 2017/4/26.
 */

public interface ILoginAndRegisterBiz {
    void goLogin(String account, String passWord, CallBack<String> callBack);
    void goRegister(String pickName, String account, String passWord, CallBack<User> callBack);
}

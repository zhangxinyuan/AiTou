package com.sxdtdx.aitou.model.interfaces;

import cn.bmob.v3.BmobUser;

/**
 * Created by lenovo on 2017/4/26.
 */

public interface ILoginAndRegisterBiz {
    void goLogin(String account, String passWord, CallBack<String> callBack);
    void goRegister(String pickName, String account, String passWord, CallBack<BmobUser> callBack);
}

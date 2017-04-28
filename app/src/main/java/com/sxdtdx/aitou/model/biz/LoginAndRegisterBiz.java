package com.sxdtdx.aitou.model.biz;

import com.sxdtdx.aitou.MyApplication;
import com.sxdtdx.aitou.R;
import com.sxdtdx.aitou.model.bean.User;
import com.sxdtdx.aitou.model.biz.interfaces.CallBack;
import com.sxdtdx.aitou.model.biz.interfaces.ILoginAndRegisterBiz;
import com.sxdtdx.aitou.model.db.UserTable;

/**
 * Created by lenovo on 2017/4/26.
 */

public class LoginAndRegisterBiz implements ILoginAndRegisterBiz{

    @Override
    public void goLogin(String account, String passWord, CallBack<String> callBack) {
//        if (UserTable.query(account, passWord)) {
//            callBack.onSuccess(MyApplication.getContext().getString(R.string.error_login_success));
//        } else {
//            callBack.onFailed(MyApplication.getContext().getString(R.string.error_login_error));
//        }
        callBack.onSuccess("ok");
    }

    @Override
    public void goRegister(String pickName, String account, String passWord, CallBack<User> callBack) {
        if (UserTable.queryAccount(account)) {
            callBack.onFailed(MyApplication.getContext().getString(R.string.error_account_error));
        } else {
            User user = new User();
            user.setPickName(pickName);
            user.setPhone(account);
            user.setPsw(passWord);
            UserTable.insert(user);
            callBack.onSuccess(user);
        }
    }
}

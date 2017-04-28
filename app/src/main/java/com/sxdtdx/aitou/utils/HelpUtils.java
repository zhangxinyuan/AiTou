package com.sxdtdx.aitou.utils;

import android.widget.Toast;

import com.sxdtdx.aitou.MyApplication;

/**
 * Created by lenovo on 2017/4/26.
 */

public class HelpUtils {
    public static void showToast(int strId) {
        Toast.makeText(MyApplication.getContext(), strId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String str) {
        Toast.makeText(MyApplication.getContext(), str, Toast.LENGTH_SHORT).show();
    }

    public static boolean isPhoneValid(String phone) {
        return phone.matches("[1][358]\\d{9}");
    }

    public static boolean isPasswordValid(String password) {
        return password.length() == 8;
    }
}

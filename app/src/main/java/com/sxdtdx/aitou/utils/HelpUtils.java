package com.sxdtdx.aitou.utils;

import android.widget.Toast;

import com.sxdtdx.aitou.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


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

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return sdf.format(new Date());
    }
}

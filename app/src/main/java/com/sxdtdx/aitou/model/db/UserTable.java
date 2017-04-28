package com.sxdtdx.aitou.model.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sxdtdx.aitou.model.bean.User;

/**
 * Created by lenovo on 2017/4/26.
 */

public class UserTable {
    private static final String TABLE_NAME_USER = "user";
    private static final String COLUMN_NAME_USER_ID = "id";
    private static final String COLUMN_NAME_USER_NAME = "name";
    private static final String COLUMN_NAME_USER_PHONE = "phone";
    private static final String COLUMN_NAME_USER_PASSWORD = "password";


    static String getCreateTableSql() {
        return "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME_USER +
                " (" +
                COLUMN_NAME_USER_ID +
                " TEXT PRIMARY KEY," +
                COLUMN_NAME_USER_NAME +
                " TEXT," +
                COLUMN_NAME_USER_PHONE +
                " TEXT," +
                COLUMN_NAME_USER_PASSWORD +
                " TEXT)";
    }

    public static synchronized boolean insert(User user) {
        SQLiteDatabase db = DbHelper.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_USER_NAME, user.getPickName());
        values.put(COLUMN_NAME_USER_PHONE, user.getPhone());
        values.put(COLUMN_NAME_USER_PASSWORD, user.getPsw());
        long result = db.insert(TABLE_NAME_USER, null, values);
        return result != -1;
    }

    public static synchronized boolean query(String account, String passWord) {
        SQLiteDatabase db = DbHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_USER, null, COLUMN_NAME_USER_PHONE + " = ? AND " +
                COLUMN_NAME_USER_PASSWORD + " = ? ", new String[]{account, passWord}, null, null, null);
        if (cursor == null || cursor.isClosed()) {
            return false;
        }
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public static synchronized boolean queryAccount(String account) {
        SQLiteDatabase db = DbHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_USER, null, COLUMN_NAME_USER_PHONE + "=?", new String[]{account}, null, null, null);
        if (cursor == null || cursor.isClosed()) {
            return false;
        }
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public static synchronized User getUserMsg(String account, String passWord) {
        SQLiteDatabase db = DbHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_USER, null, COLUMN_NAME_USER_PHONE + "=? AND " +
                COLUMN_NAME_USER_PASSWORD + "=?", new String[]{account, passWord}, null, null, null);
        if (cursor == null || cursor.isClosed()) {
            return null;
        }
        User user = new User();
        user.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER_PHONE)));
        user.setPickName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER_NAME)));
        user.setPsw(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER_PASSWORD)));
        cursor.close();
        return user;
    }

}

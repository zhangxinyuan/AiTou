package com.sxdtdx.aitou.model.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sxdtdx.aitou.MyApplication;

/**
 * Created by lenovo on 2017/4/26.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "aitou.db";

    static synchronized DbHelper getInstance() {
        return InstanceHolder.mDbHelper;
    }

    public DbHelper() {
        super(MyApplication.getInstance().getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UserTable.getCreateTableSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private static class InstanceHolder {
        private static final DbHelper mDbHelper = new DbHelper();
    }
}

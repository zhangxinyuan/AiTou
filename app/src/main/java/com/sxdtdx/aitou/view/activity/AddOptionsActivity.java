package com.sxdtdx.aitou.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.sxdtdx.aitou.R;
import com.sxdtdx.aitou.presenter.ActivityManager;
import com.sxdtdx.aitou.utils.HelpUtils;
import com.sxdtdx.aitou.view.interfaces.IAddOption;

public class AddOptionsActivity extends AppCompatActivity implements IAddOption, View.OnClickListener{

    private EditText mOptionEdit;
    private TextView mAddBtn;
    private TextView mCancleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_options);
        this.setFinishOnTouchOutside(false);
        ActivityManager.addActivity(this);
        initView();
    }

    @Override
    public void initView() {
        mOptionEdit = (EditText) findViewById(R.id.option_et);
        mAddBtn = (TextView) findViewById(R.id.add_btn);
        mCancleBtn = (TextView) findViewById(R.id.cancel_btn);
        mAddBtn.setOnClickListener(this);
        mCancleBtn.setOnClickListener(this);
    }

    @Override
    public void toFinish(int code, Intent intent) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mOptionEdit.getWindowToken(), 0);
        setResult(code, intent);
        finish();
        this.overridePendingTransition(R.anim.alpha_out, 0);
    }

    @Override
    public void doAdd() {
        if(TextUtils.isEmpty(mOptionEdit.getText().toString())) {
            HelpUtils.showToast(R.string.action_edit_option);
        } else {
            Intent intent = new Intent();
            intent.putExtra("option", mOptionEdit.getText().toString());
            toFinish(RESULT_OK, intent);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_btn:
                doAdd();
                break;
            case R.id.cancel_btn:
                toFinish(RESULT_CANCELED, null);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            toFinish(RESULT_CANCELED, null);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }
}

package com.sxdtdx.aitou.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.sxdtdx.aitou.R;
import com.sxdtdx.aitou.presenter.RegisterPresenter;
import com.sxdtdx.aitou.utils.HelpUtils;
import com.sxdtdx.aitou.view.interfaces.IRegister;

public class RegisterActivity extends AppCompatActivity implements IRegister, View.OnClickListener {

    private EditText mRegisterName;
    private EditText mRegisterPhone;
    private EditText mRegisterPassWord;
    private EditText mRegisterPassWordConfirm;
    private CheckBox mRegisterAgreement;
    private Button mRegisterButton;
    private RegisterPresenter mRegisterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mRegisterPresenter = new RegisterPresenter(this);
        initView();
    }

    @Override
    public void initView() {
        mRegisterName = (EditText) findViewById(R.id.register_name);
        mRegisterPhone = (EditText) findViewById(R.id.register_phone);
        mRegisterPassWord = (EditText) findViewById(R.id.register_password);
        mRegisterPassWordConfirm = (EditText) findViewById(R.id.register_confirm_password);
        mRegisterAgreement = (CheckBox) findViewById(R.id.register_agreement);
        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(this);
        mRegisterAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mRegisterButton.setClickable(true);
                } else {
                    mRegisterButton.setClickable(false);
                }
            }
        });
    }

    @Override
    public void attemptRegister() {
        // Reset errors.
        mRegisterName.setError(null);
        mRegisterPhone.setError(null);
        mRegisterPassWord.setError(null);
        mRegisterPassWordConfirm.setError(null);

        String pickName = mRegisterPhone.getText().toString();
        String account = mRegisterPhone.getText().toString();
        String password = mRegisterPassWord.getText().toString();
        String passwordConfirm = mRegisterPassWordConfirm.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(passwordConfirm)) {
            mRegisterPassWordConfirm.setError(getString(R.string.error_null_password));
            focusView = mRegisterPassWordConfirm;
            cancel = true;
        }
        if (!HelpUtils.isPasswordValid(passwordConfirm)) {
            mRegisterPassWordConfirm.setError(getString(R.string.error_invalid_password));
            focusView = mRegisterPassWordConfirm;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            mRegisterPassWord.setError(getString(R.string.error_null_password));
            focusView = mRegisterPassWord;
            cancel = true;
        }
        if (!HelpUtils.isPasswordValid(password)) {
            mRegisterPassWord.setError(getString(R.string.error_invalid_password));
            focusView = mRegisterPassWord;
            cancel = true;
        }

        if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConfirm) && !password.equals(passwordConfirm)) {
            mRegisterPassWordConfirm.setError(getString(R.string.error_confirm_password));
            focusView = mRegisterPassWordConfirm;
            cancel = true;
        }

        if (TextUtils.isEmpty(account)) {
            mRegisterPhone.setError(getString(R.string.error_field_required));
            focusView = mRegisterPhone;
            cancel = true;
        } else if (!HelpUtils.isPhoneValid(account)) {
            mRegisterPhone.setError(getString(R.string.error_invalid_email));
            focusView = mRegisterPhone;
            cancel = true;
        }

        if (TextUtils.isEmpty(pickName)) {
            mRegisterName.setError(getString(R.string.error_invalid_pickName));
            focusView = mRegisterName;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mRegisterPresenter.attemptRegister(pickName, account, password);
        }
    }

    @Override
    public void registerSuccess() {
        HelpUtils.showToast(R.string.success_register);
        finish();
    }

    @Override
    public void registerFailed(String errorMsg) {
        HelpUtils.showToast(errorMsg);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register_button) {
            attemptRegister();
        }
    }
}

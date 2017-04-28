package com.sxdtdx.aitou.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sxdtdx.aitou.R;
import com.sxdtdx.aitou.presenter.LoginPresenter;
import com.sxdtdx.aitou.utils.HelpUtils;
import com.sxdtdx.aitou.view.interfaces.ILogin;

import static com.sxdtdx.aitou.utils.HelpUtils.isPhoneValid;

/**
 * user login
 */
public class LoginActivity extends AppCompatActivity implements ILogin, OnClickListener {

    private EditText mUserView;
    private EditText mPasswordView;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginPresenter = new LoginPresenter(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserView.setText(null);
        mPasswordView.setText(null);
    }

    @Override
    public void initView() {
        mUserView = (EditText) findViewById(R.id.user);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mUserSignInButton = (Button) findViewById(R.id.user_sign_in_button);
        mUserSignInButton.setOnClickListener(this);

        TextView mUserSignUpView = (TextView) findViewById(R.id.user_sign_up_tv);
        mUserSignUpView.setText(Html.fromHtml(getString(R.string.action_sign_up)));
        mUserSignUpView.setOnClickListener(this);
    }

    @Override
    public void attemptLogin() {
        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String phone = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !HelpUtils.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid phone number.
        if (TextUtils.isEmpty(phone)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        } else if (!isPhoneValid(phone)) {
            mUserView.setError(getString(R.string.error_invalid_email));
            focusView = mUserView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //login
            mLoginPresenter.attemptLogin(phone, password);
        }
    }

    @Override
    public void turnToMain() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void loginFailed() {
        HelpUtils.showToast(R.string.error_login_error);
    }

    @Override
    public void turnToRegister() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_sign_in_button:
                attemptLogin();
                break;
            case R.id.user_sign_up_tv:
                turnToRegister();
                break;
        }
    }

}


package com.liujian.myqq.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.liujian.myqq.R;
import com.liujian.myqq.task.HttpAsyncTask;
import com.liujian.myqq.utils.TextUtils;
import com.liujian.myqq.utils.ToastUtils;

import java.util.HashMap;

/**
 * Created by liujian on 15/10/6.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, HttpAsyncTask.HttpAsyncTaskListener {

    @ViewInject(R.id.al_edt_account)
    private EditText edtAccount;
    @ViewInject(R.id.al_edt_password)
    private EditText edtPassword;
    @ViewInject(R.id.al_btn_login)
    private Button btnLogin;
    @ViewInject(R.id.al_ivw_head)
    private ImageView ivwHead;
    @ViewInject(R.id.al_tvw_new_user)
    private TextView tvwNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_login, mUIHelper.getContainer(), true);
        ViewUtils.inject(this, v);
        initAction();
        mUIHelper.setTitleBarVisible(false);
    }

    private void initAction() {
        tvwNewUser.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.al_tvw_new_user:
                newUserClickAction();
                break;
            case R.id.al_tvw_cant_login:
                newUserClickAction();
                break;
            case R.id.al_btn_login:
                loginClickAction();
                break;
        }
    }

    private void loginClickAction() {
        if (TextUtils.empty(edtAccount.getText().toString())) {
            ToastUtils.showShortToast(getApplicationContext(), R.string.please_input_account);
            return;
        }
        if (TextUtils.empty(edtPassword.getText().toString())) {
            ToastUtils.showShortToast(getApplicationContext(), R.string.please_input_password);
            return;
        }
        startLogin();
    }

    private void startLogin() {
        mUIHelper.showWaitingMask();
        HashMap<String, Object> taskParam = new HashMap<String, Object>();
//        new HttpAsyncTask().execute(taskParam, "", this, "");
    }

    private void newUserClickAction() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void notifyData(int commdID, String respData, Object object) {

    }

    @Override
    public void notifyError(int commdID, HttpAsyncTask.ErrorMessage errMsg) {

    }
}

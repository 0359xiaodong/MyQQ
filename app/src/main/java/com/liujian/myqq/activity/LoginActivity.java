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
import com.liujian.myqq.data.ParserCommonRsp;
import com.liujian.myqq.data.User;
import com.liujian.myqq.globel.GlobeConfig;
import com.liujian.myqq.globel.HttpRequestCode;
import com.liujian.myqq.globel.IRequestUrl;
import com.liujian.myqq.task.HttpAsyncTask;
import com.liujian.myqq.utils.DeviceUtils;
import com.liujian.myqq.utils.SecurityUtil;
import com.liujian.myqq.utils.TextUtils;
import com.liujian.myqq.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by liujian on 15/10/6.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, HttpAsyncTask.HttpAsyncTaskListener {

    @ViewInject(R.id.fl_edt_account)
    private EditText edtAccount;
    @ViewInject(R.id.fl_edt_password)
    private EditText edtPassword;
    @ViewInject(R.id.fl_btn_login)
    private Button btnLogin;
    @ViewInject(R.id.fl_ivw_head)
    private ImageView ivwHead;
    @ViewInject(R.id.fl_tvw_new_user)
    private TextView tvwNewUser;

    private String mAccount;
    private String mPassword;

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
        DeviceUtils.hideSoftInputKeyBoard(this);
        switch (v.getId()) {
            case R.id.fl_tvw_new_user:
                newUserClickAction();
                break;
            case R.id.fl_tvw_cant_login:
                newUserClickAction();
                break;
            case R.id.fl_btn_login:
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
        mAccount = edtAccount.getText().toString().trim();
        mPassword = SecurityUtil.getMD5(edtPassword.getText().toString());
        HashMap<String, Object> taskParam = new HashMap<String, Object>();
        taskParam.put("account", mAccount);
        taskParam.put("password", mPassword);
        new HttpAsyncTask().execute(taskParam, HttpRequestCode.CODE_USER_LOGIN, this, IRequestUrl.LOGIN);
    }

    private void newUserClickAction() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void notifyData(int commdID, ParserCommonRsp respData, Object object) {
        mUIHelper.hindWaitingMask();
        switch (commdID) {
            case HttpRequestCode.CODE_USER_LOGIN:
                if (respData.status == HttpRequestCode.HTTP_RESULT_SUCCESS) {
                    try {
                        GlobeConfig.globeUser.parseData(new JSONObject(respData.jsonResponse));
                        Intent intent = new Intent(this, QQMainActivity.class);
                        ToastUtils.showShortToast(getApplicationContext(), R.string.login_success);
                        startActivity(intent);
                        this.finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void notifyError(int commdID, HttpAsyncTask.ErrorMessage errMsg) {
        mUIHelper.hindWaitingMask();
    }
}

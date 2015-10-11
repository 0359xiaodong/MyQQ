package com.liujian.myqq.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.liujian.myqq.R;
import com.liujian.myqq.data.ParserCommonRsp;
import com.liujian.myqq.data.User;
import com.liujian.myqq.globel.Constant;
import com.liujian.myqq.globel.GlobeConfig;
import com.liujian.myqq.globel.HttpRequestCode;
import com.liujian.myqq.globel.IRequestUrl;
import com.liujian.myqq.interfaces.MyTextWatcher;
import com.liujian.myqq.task.HttpAsyncTask;
import com.liujian.myqq.utils.DeviceUtils;
import com.liujian.myqq.utils.SecurityUtil;
import com.liujian.myqq.utils.SharePreferenceUtil;
import com.liujian.myqq.utils.TextUtils;
import com.liujian.myqq.utils.ToastUtils;
import com.liujian.myqq.view.DelayTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by liujian on 15/10/6.
 */
public class SetNickActivity extends BaseActivity {

    @ViewInject(R.id.fs_btn_done)
    private Button btnDone;
    @ViewInject(R.id.fs_edt_nick)
    private EditText edtNick;
    @ViewInject(R.id.fs_edt_password)
    private EditText edtPassword;

    private String nick;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_setnick, mUIHelper.getContainer(), true);
        ViewUtils.inject(this, v);
        mUIHelper.setLeftStringLeftDrawable(R.drawable.icon_back_white);
        mUIHelper.setLeftString(R.string.back);
        mUIHelper.setTitle(R.string.set_nick);
        mUIHelper.tvwLeftTitle.setOnClickListener(this);

        initAction();
    }

    private void initAction() {
        btnDone.setOnClickListener(this);
        edtNick.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.MyAfterTextChangedListener() {
            @Override
            public void afterTextChanged(String text) {
                btnDone.setEnabled(TextUtils.verifyText(edtNick, 1, 30) && TextUtils.verifyText(edtPassword, 6, 16));
            }
        }));
        edtPassword.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.MyAfterTextChangedListener() {
            @Override
            public void afterTextChanged(String text) {
                btnDone.setEnabled(TextUtils.verifyText(edtNick, 1, 30) && TextUtils.verifyText(edtPassword, 6, 16));
            }
        }));
    }

    public void setNickAndPass() {
        mUIHelper.showWaitingMask();
        HashMap<String, Object> taskParam = new HashMap<>();
        taskParam.put("phone", GlobeConfig.globeUser.phone);
        taskParam.put("nick", nick);
        taskParam.put("password", SecurityUtil.getMD5(password));
        new HttpAsyncTask().execute(taskParam, HttpRequestCode.CODE_SET_NICK, this, IRequestUrl.SET_NICK);
    }

    @Override
    public void onClick(View v) {
        DeviceUtils.hideSoftInputKeyBoard(this);
        switch (v.getId()) {
            case R.id.title_tvw_left:
                this.finish();
                break;
            case R.id.fs_btn_done:
                nick = edtNick.getText().toString();
                password = edtPassword.getText().toString().trim();
                setNickAndPass();
                break;
        }
    }

    @Override
    public void notifyData(int commdID, ParserCommonRsp respData, Object object) {
        mUIHelper.hindWaitingMask();
        switch (commdID) {
            case HttpRequestCode.CODE_SET_NICK:
                if (respData.status == HttpRequestCode.HTTP_RESULT_SUCCESS) {
                    User user = new User();
                    try {
                        user.parseData(new JSONObject(respData.jsonResponse));
                        GlobeConfig.globeUser.parseUser(user);
                        Intent intent = new Intent(this, QQMainActivity.class);
                        startActivity(intent);
                        saveUserInfo();
                        ToastUtils.showShortToast(getApplicationContext(), "注册成功,您的QQ号是" + GlobeConfig.globeUser.qq + ",请牢记您的QQ号码.");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtils.showShortToast(getApplicationContext(), respData.jsonResponse);
                }
                break;
        }
    }

    public void saveUserInfo() {
        SharePreferenceUtil.getInstance().setObjectValue(getApplicationContext(), Constant.KEY_USER_INFO, GlobeConfig.globeUser);
    }

    @Override
    public void notifyError(int commdID, HttpAsyncTask.ErrorMessage errMsg) {
        mUIHelper.hindWaitingMask();
        ToastUtils.showShortToast(getApplicationContext(), errMsg.errorMsg);
    }
}

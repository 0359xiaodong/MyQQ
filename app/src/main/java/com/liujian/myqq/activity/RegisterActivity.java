package com.liujian.myqq.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.liujian.myqq.R;
import com.liujian.myqq.data.ParserCommonRsp;
import com.liujian.myqq.globel.GlobeConfig;
import com.liujian.myqq.globel.HttpRequestCode;
import com.liujian.myqq.globel.IRequestUrl;
import com.liujian.myqq.interfaces.MyTextWatcher;
import com.liujian.myqq.task.HttpAsyncTask;
import com.liujian.myqq.utils.TextUtils;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by liujian on 15/10/6.
 */
public class RegisterActivity extends BaseActivity {

    @ViewInject(R.id.fr_btn_next)
    private Button btnNext;
    @ViewInject(R.id.fr_tvw_rules)
    private TextView tvwRules;
    @ViewInject(R.id.fr_edt_phone)
    private TextView edtPhone;

    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_register, mUIHelper.getContainer(), true);
        ViewUtils.inject(this, v);
        mUIHelper.setLeftStringLeftDrawable(R.drawable.icon_back_white);
        mUIHelper.setLeftString(R.string.back);
        mUIHelper.setTitle(R.string.input_phone_number);
        mUIHelper.tvwLeftTitle.setOnClickListener(this);
        initAction();
    }

    private void initAction() {
        btnNext.setOnClickListener(this);
        tvwRules.setOnClickListener(this);
        edtPhone.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.MyAfterTextChangedListener() {
            @Override
            public void afterTextChanged(String text) {
                btnNext.setEnabled(TextUtils.verifyText(text, 11, 15));
            }
        }));
    }

    public void startRegister() {
        mUIHelper.showWaitingMask();
        HashMap<String, Object> taskParam = new HashMap<>();
        taskParam.put("phone", phone);
        new HttpAsyncTask().execute(taskParam, HttpRequestCode.CODE_USER_REGISTER, this, IRequestUrl.REGISTER);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tvw_left:
                this.finish();
                break;
            case R.id.fr_btn_next:
                phone = edtPhone.getText().toString();
                startRegister();
                break;
            case R.id.fr_tvw_rules:
                break;
        }
    }

    @Override
    public void notifyData(int commdID, ParserCommonRsp respData, Object object) {
        mUIHelper.hindWaitingMask();
        switch (commdID) {
            case HttpRequestCode.CODE_USER_REGISTER:
                if (respData.status == HttpRequestCode.HTTP_RESULT_SUCCESS) {
                    GlobeConfig.globeUser.phone = phone;
                    Intent intent = new Intent(this, VerifyActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void notifyError(int commdID, HttpAsyncTask.ErrorMessage errMsg) {
        mUIHelper.hindWaitingMask();
    }
}

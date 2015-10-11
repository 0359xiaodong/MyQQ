package com.liujian.myqq.activity;

import android.content.Intent;
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
import com.liujian.myqq.utils.DeviceUtils;
import com.liujian.myqq.utils.TextUtils;
import com.liujian.myqq.utils.ToastUtils;
import com.liujian.myqq.view.DelayTextView;

import java.util.HashMap;

/**
 * Created by liujian on 15/10/6.
 */
public class VerifyActivity extends BaseActivity {

    @ViewInject(R.id.fv_btn_next)
    private Button btnNext;
    @ViewInject(R.id.fv_tvw_rules)
    private TextView tvwRules;
    @ViewInject(R.id.fv_edt_code)
    private TextView edtCode;
    @ViewInject(R.id.fv_tvw_resend)
    private DelayTextView tvwResend;
    @ViewInject(R.id.fv_tvw_tip)
    private TextView tvwTip;

    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_verify, mUIHelper.getContainer(), true);
        ViewUtils.inject(this, v);
        mUIHelper.setLeftStringLeftDrawable(R.drawable.icon_back_white);
        mUIHelper.setLeftString(R.string.back);
        mUIHelper.setTitle(R.string.input_code_number);
        mUIHelper.tvwLeftTitle.setOnClickListener(this);

        tvwTip.setText(getString(R.string.we_have_send_your_phone_text, GlobeConfig.globeUser.phone));
        tvwResend.hasSendMessage();
        initAction();
    }

    private void initAction() {
        btnNext.setOnClickListener(this);
        tvwRules.setOnClickListener(this);
        tvwResend.setOnClickListener(this);
        edtCode.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.MyAfterTextChangedListener() {
            @Override
            public void afterTextChanged(String text) {
                btnNext.setEnabled(TextUtils.verifyText(text, 6));
            }
        }));
    }

    public void startRegister() {
        mUIHelper.showWaitingMask();
        HashMap<String, Object> taskParam = new HashMap<>();
        taskParam.put("phone", GlobeConfig.globeUser.phone);
        new HttpAsyncTask().execute(taskParam, HttpRequestCode.CODE_USER_REGISTER, this, IRequestUrl.REGISTER);
    }

    public void startVerify() {
        mUIHelper.showWaitingMask();
        HashMap<String, Object> taskParam = new HashMap<>();
        taskParam.put("phone", GlobeConfig.globeUser.phone);
        taskParam.put("code", code);
        new HttpAsyncTask().execute(taskParam, HttpRequestCode.CODE_VERIFY_REGISTER, this, IRequestUrl.VERIFY_REGISTER);
    }

    @Override
    public void onClick(View v) {
        DeviceUtils.hideSoftInputKeyBoard(this);
        switch (v.getId()) {
            case R.id.title_tvw_left:
                this.finish();
                break;
            case R.id.fv_btn_next:
                code = edtCode.getText().toString();
                startVerify();
                break;
            case R.id.fr_tvw_rules:
                break;
            case R.id.fv_tvw_resend:
                startRegister();
                break;
        }
    }

    @Override
    public void notifyData(int commdID, ParserCommonRsp respData, Object object) {
        mUIHelper.hindWaitingMask();
        switch (commdID) {
            case HttpRequestCode.CODE_VERIFY_REGISTER:
                if (respData.status == HttpRequestCode.HTTP_RESULT_SUCCESS) {
                    Intent intent = new Intent(this, SetNickActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtils.showShortToast(getApplicationContext(), respData.jsonResponse);
                }
                break;
            case HttpRequestCode.CODE_USER_REGISTER:
                if (respData.status == HttpRequestCode.HTTP_RESULT_SUCCESS) {
                    tvwResend.hasSendMessage();
                }
                break;
        }
    }

    @Override
    public void notifyError(int commdID, HttpAsyncTask.ErrorMessage errMsg) {
        mUIHelper.hindWaitingMask();
    }
}

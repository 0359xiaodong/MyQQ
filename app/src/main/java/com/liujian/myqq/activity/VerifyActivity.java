package com.liujian.myqq.activity;

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
import com.liujian.myqq.utils.ToastUtils;

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
    private TextView tvwResend;
    @ViewInject(R.id.fv_tvw_tip)
    private TextView tvwTip;

    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_verify, mUIHelper.getContainer(), true);
        ViewUtils.inject(this, v);
        mUIHelper.setLeftStringLeftDrawable(R.drawable.icon_back_white);
        mUIHelper.setLeftString(R.string.input_phone_number);
        mUIHelper.setTitle(R.string.input_code_number);

        tvwTip.setText(getString(R.string.we_have_send_your_phone_text, GlobeConfig.globeUser.phone));
        initAction();
    }

    private void initAction() {
        btnNext.setOnClickListener(this);
        tvwRules.setOnClickListener(this);
        edtCode.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.MyAfterTextChangedListener() {
            @Override
            public void afterTextChanged(String text) {
                btnNext.setEnabled(TextUtils.verifyText(text, 6));
            }
        }));
    }

    public void startVerify() {
        mUIHelper.showWaitingMask();
        HashMap<String, Object> taskParam = new HashMap<>();
        taskParam.put("phone", GlobeConfig.globeUser.phone);
        taskParam.put("code", GlobeConfig.globeUser.phone);
        new HttpAsyncTask().execute(taskParam, HttpRequestCode.CODE_USER_REGISTER, this, IRequestUrl.REGISTER);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fr_btn_next:
                code = edtCode.getText().toString();
                startVerify();
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

                } else {
                    ToastUtils.showShortToast(getApplicationContext(), respData.jsonResponse);
                }
                break;
        }
    }

    @Override
    public void notifyError(int commdID, HttpAsyncTask.ErrorMessage errMsg) {
        mUIHelper.hindWaitingMask();
    }
}

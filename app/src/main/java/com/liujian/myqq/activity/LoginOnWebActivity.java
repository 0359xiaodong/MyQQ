package com.liujian.myqq.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.liujian.myqq.R;
import com.liujian.myqq.data.ParserCommonRsp;
import com.liujian.myqq.data.ScanResult;
import com.liujian.myqq.globel.Constant;
import com.liujian.myqq.globel.HttpRequestCode;
import com.liujian.myqq.globel.IRequestUrl;
import com.liujian.myqq.task.HttpAsyncTask;
import com.liujian.myqq.utils.ToastUtils;

import java.util.HashMap;

/**
 * Created by sh.liujian on 2015-10-13.
 */
public class LoginOnWebActivity extends BaseActivity {

    public static final int STATUS_LOGIN = 1;

    @ViewInject(R.id.flw_btn_login)
    private Button btnLogin;
    @ViewInject(R.id.flw_tvw_close)
    private TextView tvwClose;
    @ViewInject(R.id.flw_tvw_cannt_login)
    private TextView tvwCanntLogin;

    private ScanResult scanResult = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_login_web, mUIHelper.getContainer(), true);
        ViewUtils.inject(this, v);

        if(getIntent().hasExtra(Constant.KEY_SCAN_INFO)) {
            scanResult = (ScanResult) getIntent().getSerializableExtra(Constant.KEY_SCAN_INFO);
        }

        mUIHelper.setTitleBarVisible(false);
        initAction();
    }

    private void initAction() {
        btnLogin.setOnClickListener(this);
        tvwClose.setOnClickListener(this);
        tvwCanntLogin.setOnClickListener(this);
    }

    @Override
    public void notifyData(int commdID, ParserCommonRsp respData, Object object) {
        switch (commdID) {
            case HttpRequestCode.CODE_SET_SCAN:
                if (respData.status == HttpRequestCode.HTTP_RESULT_SUCCESS) {
                    ToastUtils.showShortToast(getApplicationContext(), R.string.login_success);
                    this.finish();
                }
                break;
        }
    }

    @Override
    public void notifyError(int commdID, HttpAsyncTask.ErrorMessage errMsg) {

    }

    private void loginOnWeb() {
        HashMap<String, Object> taskParms = new HashMap<>();
        taskParms.put("qrtoken", scanResult.content);
        taskParms.put("status", STATUS_LOGIN);
        taskParms.put("type", Constant.DATA_TYPE_LOGIN);
        new HttpAsyncTask().execute(taskParms, HttpRequestCode.CODE_SET_SCAN, this, IRequestUrl.SET_SCAN_INFO);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flw_btn_login:
                if (scanResult != null) {
                    loginOnWeb();
                }
                break;
            case R.id.flw_tvw_close:
                this.finish();
                break;
            case R.id.flw_tvw_cannt_login:
                ToastUtils.showShortToast(getApplicationContext(), "人丑我也没办法");
                break;
        }
    }
}

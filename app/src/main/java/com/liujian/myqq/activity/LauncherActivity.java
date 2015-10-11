package com.liujian.myqq.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.liujian.myqq.R;

/**
 * Created by liujian on 15/10/6.
 */
public class LauncherActivity extends Activity implements View.OnClickListener {

    @ViewInject(R.id.fl_btn_login)
    private Button btnLogin;
    @ViewInject(R.id.fl_btn_register)
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_launcher);
        ViewUtils.inject(this);
        initAction();
    }

    private void initAction() {
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.fl_btn_login:
                intent = new Intent(this, LoginActivity.class);
                this.finish();
                break;
            case R.id.fl_btn_register:
                intent = new Intent(this, RegisterActivity.class);
                break;
        }
        startActivity(intent);
    }
}

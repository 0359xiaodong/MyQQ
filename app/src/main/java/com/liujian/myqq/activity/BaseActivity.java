package com.liujian.myqq.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.liujian.myqq.R;
import com.liujian.myqq.task.HttpAsyncTask;
import com.liujian.myqq.view.UIHelper;

/**
 * Created by liujian on 15/10/8.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener, HttpAsyncTask.HttpAsyncTaskListener{

    protected LayoutInflater inflater;
    protected UIHelper mUIHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_fragment);
        inflater = getLayoutInflater();
        mUIHelper = new UIHelper(getApplicationContext());
        mUIHelper.initView(getWindow().getDecorView());
    }
}

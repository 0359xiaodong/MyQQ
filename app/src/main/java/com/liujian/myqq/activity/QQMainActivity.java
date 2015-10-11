package com.liujian.myqq.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.liujian.myqq.R;
import com.liujian.myqq.data.ParserCommonRsp;
import com.liujian.myqq.task.HttpAsyncTask;
import com.liujian.myqq.view.TabTextView;

/**
 * Created by liujian on 15/10/6.
 */
public class QQMainActivity extends BaseActivity {

    private final int INDEX_MESSAGE = 0, INDEX_LINKER = 1, INDEX_NEWS = 2;

    private int currentIndex = -1;

    @ViewInject(R.id.am_tvw_message)
    private TabTextView tvwMessage;
    @ViewInject(R.id.am_tvw_linker)
    private TabTextView tvwLinker;
    @ViewInject(R.id.am_tvw_news)
    private TabTextView tvwNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_main, mUIHelper.getContainer(), true);
        ViewUtils.inject(this, v);
        mUIHelper.setLeftStringVisible(View.GONE);
        mUIHelper.setLeftImgVisible(View.GONE);
        mUIHelper.setTitle(R.string.message);
        mUIHelper.ivwLeftTitle.setOnClickListener(this);
        mUIHelper.setLeftImgResouce(R.drawable.icon_head);

        initAction();
        setCurrentIndex(INDEX_MESSAGE);
    }

    private void initAction() {
        tvwNews.setOnClickListener(this);
        tvwLinker.setOnClickListener(this);
        tvwMessage.setOnClickListener(this);
    }

    private void setCurrentIndex(int index) {
        if (currentIndex == index) return;
        clearIndexView();
        switch (index) {
            case INDEX_MESSAGE:
                tvwMessage.setSelect();
                break;
            case INDEX_LINKER:
                tvwLinker.setSelect();
                break;
            case INDEX_NEWS:
                tvwNews.setSelect();
                break;
        }
        currentIndex = index;
    }

    private void clearIndexView() {
        tvwNews.clearSelect();
        tvwLinker.clearSelect();
        tvwMessage.clearSelect();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.am_tvw_message:
                setCurrentIndex(INDEX_MESSAGE);
                break;
            case R.id.am_tvw_linker:
                setCurrentIndex(INDEX_LINKER);
                break;
            case R.id.am_tvw_news:
                setCurrentIndex(INDEX_NEWS);
                break;
        }
    }

    @Override
    public void notifyData(int commdID, ParserCommonRsp respData, Object object) {

    }

    @Override
    public void notifyError(int commdID, HttpAsyncTask.ErrorMessage errMsg) {

    }
}

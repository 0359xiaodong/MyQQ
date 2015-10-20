package com.liujian.myqq.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.liujian.myqq.R;
import com.liujian.myqq.adapters.ContactAdapter;
import com.liujian.myqq.data.Contact;
import com.liujian.myqq.data.ParserCommonRsp;
import com.liujian.myqq.task.HttpAsyncTask;
import com.liujian.myqq.utils.LJLog;
import com.liujian.myqq.view.TabTextView;
import com.liujian.myqq.view.listviews.SwipeListView;
import com.liujian.myqq.view.listviews.SwipeListViewListener;

import java.util.ArrayList;

/**
 * Created by liujian on 15/10/6.
 */
public class QQMainActivity extends BaseActivity {

    private final int INDEX_MESSAGE = 0, INDEX_LINKER = 1, INDEX_NEWS = 2;

    private int currentIndex = -1;

    private PopupWindow mPopupWindow;

    @ViewInject(R.id.am_tvw_message)
    private TabTextView tvwMessage;
    @ViewInject(R.id.am_tvw_linker)
    private TabTextView tvwLinker;
    @ViewInject(R.id.am_tvw_news)
    private TabTextView tvwNews;
    @ViewInject(R.id.am_lvw_contacts)
    private SwipeListView slvwContasts;

    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_main, mUIHelper.getContainer(), true);
        ViewUtils.inject(this, v);

        mUIHelper.setLeftStringVisible(View.GONE);
        mUIHelper.setLeftImgVisible(View.VISIBLE);
        mUIHelper.setLeftImgResouce(R.drawable.icon_head);
        mUIHelper.setRightImgResouce(R.drawable.icon_more);
        mUIHelper.setRightImgVisible(true);
        mUIHelper.ivwLeftTitle.setOnClickListener(this);
        mUIHelper.ivwRightTitle.setOnClickListener(this);

        initAction();
        initPopWindow();
        initData();
        setCurrentIndex(INDEX_MESSAGE);
    }

    private void initAction() {
        tvwNews.setOnClickListener(this);
        tvwLinker.setOnClickListener(this);
        tvwMessage.setOnClickListener(this);
    }

    private void initData() {
        final ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("尘埃", "啦啦啦啦啦啦啦...", "昨天", 3, R.drawable.head_1));
        contacts.add(new Contact("风中尘埃", "啦啦啦啦啦啦啦...", "前天", 1, R.drawable.head_2));
        contacts.add(new Contact("尘埃化土", "啦啦啦啦啦啦啦...", "08:10", 0, R.drawable.head_3));
        contacts.add(new Contact("测试啦啦", "啦啦啦啦啦啦啦...", "09:09", 8, R.drawable.head_4));
        contacts.add(new Contact("我的电脑", "啦啦啦啦啦啦啦...", "昨天", 12, R.drawable.head_5));
        contactAdapter = new ContactAdapter(getApplicationContext(), contacts);
        slvwContasts.setAdapter(contactAdapter);
        slvwContasts.setSwipeListViewListener(new SwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {

            }

            @Override
            public void onClosed(int position, boolean fromRight) {

            }

            @Override
            public void onListChanged() {

            }

            @Override
            public void onMove(int position, float x) {

            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {

            }

            @Override
            public void onStartClose(int position, boolean right) {

            }

            @Override
            public void onClickFrontView(int position) {
                Intent intent = new Intent(QQMainActivity.this, ContactActivity.class);
                intent.putExtra("contact", contacts.get(position));
                startActivity(intent);
            }

            @Override
            public void onClickBackView(int position) {

            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {

            }

            @Override
            public int onChangeSwipeMode(int position) {
                return 0;
            }

            @Override
            public void onChoiceChanged(int position, boolean selected) {

            }

            @Override
            public void onChoiceStarted() {

            }

            @Override
            public void onChoiceEnded() {

            }

            @Override
            public void onFirstListItem() {

            }

            @Override
            public void onLastListItem() {

            }
        });
    }

    private void setCurrentIndex(int index) {
        if (currentIndex == index) return;
        clearIndexView();
        switch (index) {
            case INDEX_MESSAGE:
                tvwMessage.setSelect();
                mUIHelper.setTitle(R.string.message);
                mUIHelper.setRightImgVisible(true);
                mUIHelper.setRightStringVisible(View.GONE);
                break;
            case INDEX_LINKER:
                tvwLinker.setSelect();
                mUIHelper.setTitle(R.string.linker);
                mUIHelper.setRightImgVisible(false);
                mUIHelper.setRightStringVisible(View.VISIBLE);
                mUIHelper.setRightString(R.string.add);
                break;
            case INDEX_NEWS:
                tvwNews.setSelect();
                mUIHelper.setTitle(R.string.news);
                mUIHelper.setRightImgVisible(false);
                mUIHelper.setRightStringVisible(View.VISIBLE);
                mUIHelper.setRightString(R.string.more);
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
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
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
            case R.id.title_ivw_right:
                showPop(v, 0, 0);
                break;
            case R.id.mp_tvw_scan:
                Intent intent = new Intent(this, ScanActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initPopWindow() {
        View popView = inflater.inflate(R.layout.main_popup, null);
        popView.findViewById(R.id.mp_tvw_scan).setOnClickListener(this);
        popView.findViewById(R.id.mp_tvw_add_friend).setOnClickListener(this);
        popView.findViewById(R.id.mp_tvw_create_chat).setOnClickListener(this);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                LJLog.d("OnDismiss ...");
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1; //0.0-1.0
                getWindow().setAttributes(lp);
            }
        });
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
    }


    public void showPop(View parent, int x, int y) {
        //设置popwindow显示位置
        mPopupWindow.showAsDropDown(parent, x, y);
        //获取popwindow焦点
        mPopupWindow.setFocusable(true);
        //设置popwindow如果点击外面区域，便关闭。
        mPopupWindow.setOutsideTouchable(true);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.8f; //0.0-1.0
        getWindow().setAttributes(lp);
        mPopupWindow.update();
    }

    @Override
    public void notifyData(int commdID, ParserCommonRsp respData, Object object) {

    }

    @Override
    public void notifyError(int commdID, HttpAsyncTask.ErrorMessage errMsg) {

    }
}

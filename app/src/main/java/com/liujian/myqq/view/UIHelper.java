package com.liujian.myqq.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liujian.myqq.R;

/**
 * Created by liujian on 15/10/8.
 */
public class UIHelper {

    Context mContext;

    public TextView tvwLeftTitle;
    public TextView tvwRightTitle;
    public TextView tvwTitle;
    public TextView tvwTitleTip;

    public ImageView ivwLeftTitle;
    public ImageView ivwRightTitle;

    public ViewGroup titleBar;
    public ViewGroup shadeMask;
    public ViewGroup mainContainer;

    public UIHelper(Context context) {
        mContext = context;
    }

    public void initView(View view) {
        tvwLeftTitle = (TextView) view.findViewById(R.id.title_tvw_left);
        tvwRightTitle = (TextView) view.findViewById(R.id.title_tvw_right);
        tvwTitle = (TextView) view.findViewById(R.id.title_tvw_title);
        tvwTitleTip = (TextView) view.findViewById(R.id.title_tvw_tip);

        ivwLeftTitle = (ImageView) view.findViewById(R.id.title_ivw_left);
        ivwRightTitle = (ImageView) view.findViewById(R.id.title_ivw_right);

        titleBar = (ViewGroup) view.findViewById(R.id.title_bar);
        mainContainer = (ViewGroup) view.findViewById(R.id.fragment_main);
        shadeMask = (ViewGroup) view.findViewById(R.id.shade_mask);
    }

    public ViewGroup getContainer() {
        return mainContainer;
    }

    public void setTitleBarVisible(boolean visible) {
        if (visible) {
            titleBar.setVisibility(View.VISIBLE);
        } else {
            titleBar.setVisibility(View.GONE);
        }
    }

    public void setTitle(String title) {
        tvwTitle.setText(title);
    }

    public void setTitleTip(String tip) {
        tvwTitleTip.setText(tip);
    }


    public void setTitle(int resid) {
        tvwTitle.setText(resid);
    }

    public void setTitleTip(int resid) {
        tvwTitleTip.setText(resid);
    }

    public void setLeftImgVisible(boolean visible) {
        if (visible) {
            ivwLeftTitle.setVisibility(View.VISIBLE);
        } else {
            ivwLeftTitle.setVisibility(View.GONE);
        }
    }

    public void setRightImgVisible(boolean visible) {
        if (visible) {
            ivwRightTitle.setVisibility(View.VISIBLE);
        } else {
            ivwRightTitle.setVisibility(View.GONE);
        }
    }

    public void setLeftImgResouce(int resid) {
        ivwLeftTitle.setImageResource(resid);
    }

    public void setRightImgResouce(int resid) {
        ivwRightTitle.setImageResource(resid);
    }

    public void setLeftString(String text) {
        tvwLeftTitle.setText(text);
    }

    public void setLeftString(int resid) {
        tvwLeftTitle.setText(resid);
    }

    public void setLeftStringLeftDrawable(int resid) {
        Drawable drawable = mContext.getResources().getDrawable(resid);
        drawable.setBounds(0, 0, 60, 60);
        tvwLeftTitle.setCompoundDrawables(drawable, null, null, null);
    }

    public void setRightString(String text) {
        tvwRightTitle.setText(text);
    }

    public void setRightString(int resid) {
        tvwRightTitle.setText(resid);
    }

    public void showWaitingMask() {
        shadeMask.setVisibility(View.VISIBLE);
    }

    public void hindWaitingMask() {
        shadeMask.setVisibility(View.GONE);
    }
}

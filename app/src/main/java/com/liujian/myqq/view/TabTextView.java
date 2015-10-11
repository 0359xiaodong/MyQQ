package com.liujian.myqq.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.liujian.myqq.R;

/**
 * Created by sh.liujian on 2015-10-8.
 */
public class TabTextView extends TextView {

    private static final int Left = 0, Top = 1, Right = 2, Bottom = 3;

    private Drawable mSeletedDrawable;
    private Drawable mUnSeletedDrawable;

    private int mDrawableSize = 80;

    private int mSeletedRes;
    private int mUnSeletedRes;

    private int mSeletedColor;
    private int mUnSeletedColor;

    private int defaultColor = Color.BLACK;

    private int direction;

    public TabTextView(Context context) {
        this(context, null);
    }

    public TabTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public TabTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabTextView, defStyleAttr, 0);
        mSeletedRes = array.getResourceId(R.styleable.TabTextView_selected_drawable, 0);
        mUnSeletedRes = array.getResourceId(R.styleable.TabTextView_unselected_drawable, 0);
        mSeletedColor = array.getColor(R.styleable.TabTextView_selected_color, defaultColor);
        mUnSeletedColor = array.getColor(R.styleable.TabTextView_unselected_color, defaultColor);
        direction = array.getInt(R.styleable.TabTextView_drawable_direction, 0);
        mDrawableSize = array.getDimensionPixelSize(R.styleable.TabTextView_drawable_size, mDrawableSize);
        array.recycle();
        init();
    }

    public void setSelect() {
        setTextColor(mSeletedColor);
        setDrawable(direction, mSeletedDrawable);
    }

    public void clearSelect() {
        setTextColor(mUnSeletedColor);
        setDrawable(direction, mUnSeletedDrawable);
    }

    private void setDrawable(int direct, Drawable drawable) {
        switch (direct) {
            case Top:
                setCompoundDrawables(null, drawable, null, null);
                break;
            case Left:
                setCompoundDrawables(drawable, null, null, null);
                break;
            case Right:
                setCompoundDrawables(null, null, drawable, null);
                break;
            case Bottom:
                setCompoundDrawables(null, null, null, drawable);
                break;
        }
    }

    private void init() {
        // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片,获取图片的顺序是左上右下（0,1,2,3,）
        if (mUnSeletedRes != 0) {
            mUnSeletedDrawable = getResources().getDrawable(mUnSeletedRes);
            mUnSeletedDrawable.setBounds(0, 0, mDrawableSize, mDrawableSize); //Icon Size
        }
        if (mSeletedRes != 0) {
            mSeletedDrawable = getResources().getDrawable(mSeletedRes);
            mSeletedDrawable.setBounds(0, 0, mDrawableSize, mDrawableSize); //Icon Size
        }
        clearSelect();
    }
}

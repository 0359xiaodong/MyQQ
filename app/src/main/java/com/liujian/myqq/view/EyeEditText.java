package com.liujian.myqq.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.liujian.myqq.R;

/**
 * Created by sh.liujian on 2015-10-8.
 */
public class EyeEditText extends EditText implements TextWatcher, View.OnFocusChangeListener {

    //EditText右侧的删除按钮
    private Drawable mEyeDrawable;
    private boolean hasFocus;

    int inputTypt;

    public EyeEditText(Context context) {
        this(context, null);
    }

    public EyeEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public EyeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (hasFocus) {
            setEyeIconVisible(s.length() > 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            if (getCompoundDrawables()[2] != null) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                Rect rect = getCompoundDrawables()[2].getBounds();
                int height = rect.height();
                int distance = (getHeight() - height) / 2;
                boolean isInnerWidth = x > (getWidth() - getTotalPaddingRight()) && x < (getWidth() - getPaddingRight());
                boolean isInnerHeight = y > distance && y < (distance + height);
                if (isInnerWidth && isInnerHeight) {
                    this.setInputType(InputType.TYPE_NULL);
                }
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                this.setInputType(inputTypt);
                this.setSelection(getText().length());
            }
        }
        return super.onTouchEvent(event);
    }

    private void init() {
        // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片,获取图片的顺序是左上右下（0,1,2,3,）
        mEyeDrawable = getCompoundDrawables()[2];
        if (mEyeDrawable == null) {
            mEyeDrawable = getResources().getDrawable(
                    R.drawable.icon_eye);
        }
        inputTypt = getInputType();
        mEyeDrawable.setBounds(0, 0, 70, 70); //Icon Size

        // 默认设置隐藏图标
        setEyeIconVisible(false);
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
        // 设置输入框焦点发生改变的监听
        setOnFocusChangeListener(this);
    }

    protected void setEyeIconVisible(boolean visible) {
        Drawable right = visible ? mEyeDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (this.hasFocus) {
            setEyeIconVisible(getText().length() > 0);
        } else {
            setEyeIconVisible(false);
            setInputType(inputTypt);
        }
    }
}

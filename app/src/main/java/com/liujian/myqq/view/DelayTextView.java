package com.liujian.myqq.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.liujian.myqq.R;


/**
 * Created by sh.liujian on 2015-10-10.
 */
public class DelayTextView extends TextView implements View.OnClickListener {

    private static final int DELAY_COUNT = 10086;

    private int count;
    private int stringId;
    private int color;
    private int orignColor;
    private String orignString;

    OnClickListener mOnClickListener;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DELAY_COUNT:
                    if (count > 0) {
                        setText(getContext().getString(stringId, count + ""));
                        sendEmptyMessageDelayed(DELAY_COUNT, 1000);
                        count--;
                    } else {
                        setText(orignString);
                        setTextColor(orignColor);
                        setEnabled(true);
                    }
                    break;
            }
        }
    };

    public DelayTextView(Context context) {
        this(context, null);
    }

    public DelayTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public DelayTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.DelayTextView, defStyleAttr, 0);
        count = array.getInt(R.styleable.DelayTextView_count_time, 60);
        stringId = array.getResourceId(R.styleable.DelayTextView_count_text, R.string.re_send_couting);
        color = array.getColor(R.styleable.DelayTextView_count_color, Color.argb(255, 192, 192, 192));
        array.recycle();
        super.setOnClickListener(this);
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        mOnClickListener = listener;
    }

    public void hasSendMessage() {
        setEnabled(false);
        orignString = getText().toString();
        orignColor = getCurrentTextColor();
        setText(getContext().getString(stringId, count + ""));
        setTextColor(color);
        handler.sendEmptyMessageDelayed(DELAY_COUNT, 1000);
    }

    public void removeSendMessage() {
        setEnabled(true);
        setText(orignString);
        setTextColor(orignColor);
        handler.removeMessages(DELAY_COUNT);
    }


    @Override
    public void onClick(View v) {
//        hasSendMessage();
        if (mOnClickListener != null) {
            mOnClickListener.onClick(v);
        }
    }
}

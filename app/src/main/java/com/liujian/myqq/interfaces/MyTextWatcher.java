package com.liujian.myqq.interfaces;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by liujian on 15/10/9.
 */
public class MyTextWatcher implements TextWatcher {



    public static interface MyAfterTextChangedListener {
        void afterTextChanged(String text);
    }

    private MyAfterTextChangedListener myAfterTextChangedListener;

    public MyTextWatcher(MyAfterTextChangedListener listener) {
        myAfterTextChangedListener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (myAfterTextChangedListener != null) {
            myAfterTextChangedListener.afterTextChanged(s.toString());
        }
    }
}

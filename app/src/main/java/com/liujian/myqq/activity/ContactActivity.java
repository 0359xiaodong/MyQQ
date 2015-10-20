package com.liujian.myqq.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.liujian.myqq.R;
import com.liujian.myqq.data.Contact;
import com.liujian.myqq.data.ParserCommonRsp;
import com.liujian.myqq.interfaces.MyTextWatcher;
import com.liujian.myqq.task.HttpAsyncTask;
import com.liujian.myqq.utils.TextUtils;

/**
 * Created by sh.liujian on 2015-10-20.
 */
public class ContactActivity extends BaseActivity {

    Contact contact = null;

    @ViewInject(R.id.fc_ivw_emoji)
    private ImageView ivwEmoji;
    @ViewInject(R.id.fc_ivw_more)
    private ImageView ivwMore;
    @ViewInject(R.id.fc_ivw_speak)
    private ImageView ivwSpeak;
    @ViewInject(R.id.fc_edt_input)
    private EditText edtInput;
    @ViewInject(R.id.fc_tvw_send)
    private TextView tvwSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_contact, mUIHelper.getContainer(), true);
        ViewUtils.inject(this, v);

        if (getIntent().hasExtra("contact")) {
            contact = (Contact) getIntent().getSerializableExtra("contact");
        } else {
            throw new RuntimeException("no contact set");
        }

        mUIHelper.setTitleTipVisible(View.VISIBLE);
        mUIHelper.setLeftStringLeftDrawable(R.drawable.icon_back_white);
        mUIHelper.setRightImgVisible(View.VISIBLE);
        mUIHelper.setRightImgResouce(R.drawable.icon_user_white);
        mUIHelper.ivwRightTitle.setOnClickListener(this);
        initAction();
        initData();
    }

    private void initAction() {
        mUIHelper.tvwLeftTitle.setOnClickListener(this);
        mUIHelper.ivwRightTitle.setOnClickListener(this);
        ivwSpeak.setOnClickListener(this);
        ivwEmoji.setOnClickListener(this);
        ivwMore.setOnClickListener(this);
        tvwSend.setOnClickListener(this);
        edtInput.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.MyAfterTextChangedListener() {
            @Override
            public void afterTextChanged(String text) {
                if (TextUtils.empty(text)) {
                    ivwSpeak.setVisibility(View.VISIBLE);
                    tvwSend.setVisibility(View.GONE);
                } else {
                    ivwSpeak.setVisibility(View.GONE);
                    tvwSend.setVisibility(View.VISIBLE);
                }
            }
        }));
    }

    private void initData() {
        mUIHelper.setTitle(contact.name);
        mUIHelper.setTitleTip("iPhone6s Plus在线 - WiFi");
        mUIHelper.setLeftString(R.string.back);
    }


    @Override
    public void notifyData(int commdID, ParserCommonRsp respData, Object object) {

    }

    @Override
    public void notifyError(int commdID, HttpAsyncTask.ErrorMessage errMsg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tvw_left:
                this.finish();
                break;
        }
    }
}

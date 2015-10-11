package com.liujian.myqq.data;

import com.liujian.myqq.interfaces.Parseable;
import com.liujian.myqq.utils.TextUtils;

import org.json.JSONObject;

/**
 * Created by liujian on 15/10/9.
 */
public class User implements Parseable {

    public String name;
    public String phone;
    public String nick;
    public String img;
    public String email;
    public String qq;
    public String token;

    @Override
    public void parseData(JSONObject data) {
        name = data.optString("name");
        phone = data.optString("phone");
        nick = data.optString("nick");
        img = data.optString("img");
        email = data.optString("email");
        qq = data.optString("qq");
        token = data.optString("token");
    }

    public void parseUser(User user) {
        if (user == null) return;
        name = user.name;
        nick = user.nick;
        img = user.img;
        email = user.email;
        qq = user.qq;
        token = user.token;
        phone = user.phone;
    }

    public boolean isLogin() {
        return !TextUtils.empty(token);
    }
}

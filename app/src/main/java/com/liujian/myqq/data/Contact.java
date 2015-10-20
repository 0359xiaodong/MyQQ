package com.liujian.myqq.data;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sh.liujian on 2015-10-20.
 */
public class Contact implements Serializable {

    public int contactType;

    public int contacterID;
    public String name;
    public String lastTalk;
    public String imageUrl;
    public int messageCount;
    public String lastTime;

    public Contact(String name, String lastTalk, String lastTime, int messageCount, int imageUrl) {
        this.name = name;
        this.lastTalk = lastTalk;
        this.lastTime = lastTime;
        this.messageCount = messageCount;
        this.imageUrl = "" + imageUrl;
    }

    public Contact(JSONObject data) {
        contactType = data.optInt("contactType");
        name = data.optString("name");
        lastTalk = data.optString("lastTalk");
        imageUrl = data.optString("imageUrl");
        messageCount = data.optInt("messageCount");
        lastTime = data.optString("lastTime");
        contacterID = data.optInt("ID");
    }
}

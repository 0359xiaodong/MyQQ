package com.liujian.myqq.data;

import com.liujian.myqq.interfaces.Parseable;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by liujian on 15/10/9.
 */
public class ParserCommonRsp implements Parseable {

    public int status;
    public int respondType;
    public String jsonResponse;

    @Override
    public void parseData(JSONObject data) {
        status = data.optInt("status");
        respondType = data.optInt("datatype");
        jsonResponse = data.optString("content");
    }
}

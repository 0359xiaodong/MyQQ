package com.liujian.myqq.interfaces;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by liujian on 15/10/9.
 */
public interface Parseable extends Serializable {
    void parseData(JSONObject data);
}

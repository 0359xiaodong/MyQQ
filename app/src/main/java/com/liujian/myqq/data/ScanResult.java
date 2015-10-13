package com.liujian.myqq.data;

import com.liujian.myqq.utils.LJLog;

import java.io.Serializable;

/**
 * Created by sh.liujian on 2015-10-12.
 */
public class ScanResult implements Serializable {

    public int resultType;
    public String content;

    public ScanResult(String scanText) {
        String[] temp = scanText.split("\\|");
        for (String s : temp) {
            LJLog.e(" " + s);
        }
        resultType = Integer.valueOf(temp[0].split(":")[1]);
        content = temp[1].split(":")[1];
    }
}

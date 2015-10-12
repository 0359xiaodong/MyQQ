package com.liujian.myqq.data;

/**
 * Created by sh.liujian on 2015-10-12.
 */
public class ScanResult {

    public int resultType;
    public String content;

    public ScanResult(String scanText) {
        String[] temp = scanText.split("|");
        resultType = Integer.valueOf(temp[0].split(":")[0]);
        content = temp[2].split(":")[2];
    }
}

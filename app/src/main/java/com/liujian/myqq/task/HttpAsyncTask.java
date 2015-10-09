package com.liujian.myqq.task;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.liujian.myqq.data.ParserCommonRsp;
import com.liujian.myqq.globel.GlobeConfig;
import com.liujian.myqq.globel.HttpRequestCode;
import com.liujian.myqq.utils.LJLog;
import com.liujian.myqq.utils.SecurityUtil;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author junzhang
 */
public class HttpAsyncTask extends AsyncTask<Object, Object, JSONObject> {
    public static int requestNum = 0;
    public static int requestFailNum = 0;

    public static final String TAG = HttpAsyncTask.class.getSimpleName();
    private HttpAsyncTaskListener mListener;
    private int commdID;
    private String requestUrl;
    private String url = GlobeConfig.BASE_URL;

    @Override
    protected JSONObject doInBackground(Object... params) {
        HashMap<String, Object> taskParam = (HashMap<String, Object>) params[0];
        commdID = (Integer) params[1];
        mListener = (HttpAsyncTaskListener) params[2];
        requestUrl = (String) params[3];
        url += requestUrl;
        String responseString = "";
        try {
            requestNum++;
            LJLog.d("HTTP请求 --" + requestNum + "-- url = " + url);
            taskParam.put("verify", SecurityUtil.getMD5(SecurityUtil.KEY));
            taskParam.put("from", "Android");

            for (Entry<String, Object> s : taskParam.entrySet()) {
                LJLog.d("    parama -- " + requestNum + " -->" + s.getKey() + " : " + s.getValue());
            }

            responseString = HttpManager.getInstance().sendPostRequest(taskParam, url);
            LJLog.d("HTTP返回--" + requestNum + " -- " + responseString);
            if (TextUtils.isEmpty(responseString.trim())) {
                return null;
            }
            return new JSONObject(responseString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        if (mListener == null) {
            return;
        }
        try {
            if (result != null) {
                ParserCommonRsp parserCommonRsp = new ParserCommonRsp();
                parserCommonRsp.parseData(result);
                LJLog.d("HTTP成功，请求 " + requestNum + " 次，失败 " + requestFailNum + " 次，失败率：" + getPercent(requestFailNum, requestNum) + "  请求url：" + url);
                if (parserCommonRsp.status == HttpRequestCode.HTTP_TOKENT_INVALID) {
                }
                mListener.notifyData(commdID, parserCommonRsp, null);
            } else {
                ErrorMessage errMsg = new ErrorMessage();
                errMsg.errorCode = -1;
                mListener.notifyError(commdID, errMsg);
                requestFailNum++;
                LJLog.d("HTTP失败，请求 " + requestNum + " 次，失败 " + requestFailNum + " 次，失败率：" + getPercent(requestFailNum, requestNum) + "  请求url：" + url);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessage errMsg = new ErrorMessage();
            errMsg.errorCode = -1;
            mListener.notifyError(commdID, errMsg);
            requestFailNum++;
            LJLog.d("HTTP失败，请求 " + requestNum + " 次，失败 " + requestFailNum + " 次，失败率：" + getPercent(requestFailNum, requestNum) + "  请求url：" + url);
        }

    }

    public class ErrorMessage {
        public int errorCode;
        public String errorMsg;
    }

    public interface HttpAsyncTaskListener {
        // parameter object : resvered for extend
        public void notifyData(int commdID, ParserCommonRsp respData, Object object);

        public void notifyError(int commdID, ErrorMessage errMsg);
    }

    private static String getPercent(int x, int total) {
        double baiy = x * 1.0;
        double baiz = total * 1.0;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);
        String string = nf.format(baiy / baiz);

        return string;
    }

}

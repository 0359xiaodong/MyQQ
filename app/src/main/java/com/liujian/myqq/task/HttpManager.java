package com.liujian.myqq.task;

import android.os.Build;

import com.liujian.myqq.utils.LJLog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpManager {

    private static final int REQUEST_TIMEOUT = 8000;

    private static final int CONNECTION_TIMEOUT = 8000;

    private static final String HTTP_HEADER_CONTENT_TYPE_KEY = "Content-Type";
    private static final String HTTP_HEADER_CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded";

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static HttpManager mInstance = null;

    private HttpManager() {
    }

    public static HttpManager getInstance() {
        if (mInstance == null) {
            mInstance = new HttpManager();
        }
        return mInstance;
    }


    /**
     * Prior to Android 2.2 (Froyo), this class had some frustrating bugs. In
     * particular, calling close() on a readable InputStream could poison the
     * connection pool. Work around this by disabling connection pooling:
     */
    private void disableConnectionReuseIfNecessary() {
        // Work around pre-Froyo bugs in HTTP connection reuse.
        if (Build.VERSION.SDK_INT < 8) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    public String sendPostRequest(Map<String, Object> params, String url)
            throws Exception {
        return sendPostRequest(params, url, DEFAULT_CHARSET, 3);
    }

    public String sendPostRequest(Map<String, Object> params, String url, String ecoding, int repeatCount) throws Exception {
        if (repeatCount == 0) {
            throw new Exception();
        }
        String responseData = null;
        try {
            HttpURLConnection connection = getHttpURLConnection("POST", url, params, REQUEST_TIMEOUT, CONNECTION_TIMEOUT);
            if (connection.getResponseCode() == 200) {
                responseData = read(connection);
                LJLog.d("Http_Manager For 6.0 URL -> " + url.toString()
                        + "\n responseData: -> " + responseData);
                return responseData;
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseData = sendPostRequest(params, url, ecoding, repeatCount - 1);
        }
        return responseData;
    }


    public String sendGETRequest(Map<String, Object> params, boolean isPolling, boolean isNeedToken, String url) throws Exception {
        return sendGETRequest(params, url, DEFAULT_CHARSET, 3);
    }

    private String sendGETRequest(Map<String, Object> params, String url,
                                  String ecoding, int repeatCount) throws Exception {
        if (repeatCount == 0) {
            throw new Exception();
        }
        String responseData = null;
        try {
            HttpURLConnection connection = getHttpURLConnection("GET", url, params, REQUEST_TIMEOUT, REQUEST_TIMEOUT);
            if (connection.getResponseCode() == 200) {
                responseData = read(connection);
                LJLog.d("Http_Manager For 6.0 URL -> " + url.toString()
                        + "\n responseData: -> " + responseData);
                return responseData;
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseData = sendGETRequest(params, url, ecoding, repeatCount - 1);
        }
        return responseData;
    }

    /**
     * @param type       http类型：get 或 post
     * @param url        接口地址
     * @param params     接口请求参数
     * @param conTimeOut 连接超时时间
     * @param socTimeOut socket超时时间
     * @return
     */
    private HttpURLConnection getHttpURLConnection(String type, String url, Map<String, Object> params, int conTimeOut, int socTimeOut) throws Exception {
        LJLog.d("getHttpURLConnection type        = " + type);
        LJLog.d("getHttpURLConnection url         = " + url);
        LJLog.d("getHttpURLConnection conTimeOut  = " + conTimeOut);
        LJLog.d("getHttpURLConnection socTimeOut  = " + socTimeOut);

        disableConnectionReuseIfNecessary();
        if (null != params && "GET".equalsIgnoreCase(type)) {
            url = initGetUrl(url, params);
            LJLog.d("getHttpsURLConnection For 6.0 url    = " + url);
            params = null;
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection(java.net.Proxy.NO_PROXY);
            connection.setRequestMethod(type); // 默认就是GET
            if ("POST".equalsIgnoreCase(type)) {
                connection.setDoInput(true);
                connection.setDoOutput(true);
                if (null != params) {
                    LJLog.d(encodeParams(params));
                    LJLog.d("fullurl -- " + url + "?" + encodeParams(params));
                    connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE_KEY, HTTP_HEADER_CONTENT_TYPE_VALUE);
                    connection.getOutputStream().write(encodeParams(params).getBytes());
                    connection.getOutputStream().flush();
                    connection.getOutputStream().close();
                }
            }
            conTimeOut = (1 > conTimeOut) ? REQUEST_TIMEOUT : conTimeOut;
            socTimeOut = (1 > socTimeOut) ? CONNECTION_TIMEOUT : socTimeOut;

            connection.setConnectTimeout(conTimeOut);
            connection.setReadTimeout(socTimeOut);

            connection.setDefaultUseCaches(false);
            connection.connect();
            int statusCode = connection.getResponseCode();
            LJLog.d("getHttpURLConnection For 6.0 statusCode = " + statusCode);
            return connection;
        } catch (Exception e) {
            throw e;
        }
    }

    private InputStream getInputStream(HttpURLConnection httpURLConnection) throws Exception {
        InputStream inputStream = null;
        try {
            int statusCode = httpURLConnection.getResponseCode();
            if (statusCode < 200 || 300 < statusCode) {
                inputStream = httpURLConnection.getErrorStream();
                LJLog.d("httpURLConnection.getContentLength() = " + httpURLConnection.getContentLength());
                if (null == inputStream || httpURLConnection.getContentLength() < 1)
                    throw new Exception();
            } else {
                inputStream = httpURLConnection.getInputStream();
            }
            String contentEncoding = httpURLConnection.getContentEncoding();
        } catch (IOException e) {
            throw new Exception();
        }
        return inputStream;
    }

    private String read(HttpURLConnection httpURLConnection) throws Exception {
        String result = "";
        try {
            InputStream inputStream = null;
            int statusCode = httpURLConnection.getResponseCode();
            if (statusCode < 200 || 300 < statusCode) {
                inputStream = httpURLConnection.getErrorStream();
                LJLog.d("httpURLConnection.getContentLength() = " + httpURLConnection.getContentLength());
                if (null == inputStream || httpURLConnection.getContentLength() < 1)
                    throw new Exception("");
            } else {
                inputStream = httpURLConnection.getInputStream();
            }
            ByteArrayOutputStream content = new ByteArrayOutputStream();
            int readBytes = 0;
            byte[] sBuffer = new byte[1024];
            while ((readBytes = inputStream.read(sBuffer)) != -1) {
                content.write(sBuffer, 0, readBytes);
            }
            String contentType = httpURLConnection.getContentType();
            String charset = ((contentType.indexOf("charset=") != -1) && !((charset = contentType.substring(contentType.indexOf("charset=") + 8)).length() == 0)) ? charset : "UTF-8";
            LJLog.d("charset=" + charset);
            result = new String(content.toByteArray(), charset).trim();
            LJLog.d("read = " + result);
        } catch (IOException e) {
            throw e;
        }
        return result;
    }

    /**
     * 拼接接口参数
     *
     * @param url 接口地址
     * @return
     */
    private String initGetUrl(String url, Map<String, Object> params) {
        if (url.contains("?")) {
            if (url.endsWith("?"))
                return url + encodeParams(params);
            return url + "&" + encodeParams(params);
        } else {
            return url + "?" + encodeParams(params);
        }
    }

    private String encodeParams(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            sb.append(key);
            sb.append("=");
            try {
                sb.append(URLEncoder.encode(params.get(key).toString(), DEFAULT_CHARSET));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}

package com.liujian.myqq.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.liujian.myqq.R;
import com.liujian.myqq.data.ParserCommonRsp;
import com.liujian.myqq.data.ScanResult;
import com.liujian.myqq.globel.Constant;
import com.liujian.myqq.globel.HttpRequestCode;
import com.liujian.myqq.globel.IRequestUrl;
import com.liujian.myqq.task.HttpAsyncTask;
import com.liujian.myqq.utils.SoundUtil;
import com.liujian.qr.camera.CameraManager;
import com.liujian.qr.decoding.CaptureActivityHandler;
import com.liujian.qr.decoding.InactivityTimer;
import com.liujian.qr.view.ViewfinderView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by sh.liujian on 2015-10-12.
 */
public class ScanActivity extends BaseActivity implements SurfaceHolder.Callback {

    @ViewInject(R.id.viewfinder_view)
    private ViewfinderView viewfinderView;

    private static final long VIBRATE_DURATION = 500L;

    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;

    private ScanResult scanResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_scan, mUIHelper.getContainer(), true);
        ViewUtils.inject(this, v);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

        mUIHelper.setTitle(R.string.scan_code);
        mUIHelper.setLeftStringLeftDrawable(R.drawable.icon_back_white);
        mUIHelper.setLeftString(R.string.back);
        mUIHelper.tvwLeftTitle.setOnClickListener(this);

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        vibrate = true;
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void playBeepSoundAndVibrate() {
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (resultString.equals("")) {
            Toast.makeText(ScanActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
        } else {
//            Intent resultIntent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putString("result", resultString);
//            bundle.putParcelable("bitmap", barcode);
//            resultIntent.putExtras(bundle);
//            this.setResult(RESULT_OK, resultIntent);
        }
        SoundUtil.playSoundId(R.raw.qrcode_completed);
        try {
            scanResult = new ScanResult(resultString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (scanResult != null) {
            analyScanResult(scanResult);
        }
    }

    private void analyScanResult(ScanResult scanResult) {
        switch (scanResult.resultType) {
            case Constant.DATA_TYPE_LOGIN:
                loginOnWeb();
                break;
            case Constant.DATA_TYPE_USER:
                break;
            case Constant.DATA_TYPE_HTML:
                break;
        }
    }

    private void loginOnWeb() {
        HashMap<String, Object> taskParams = new HashMap<>();
        taskParams.put("type", Constant.DATA_TYPE_LOGIN);
        taskParams.put("qrtoken", scanResult.content);
        new HttpAsyncTask().execute(taskParams, HttpRequestCode.CODE_SET_SCAN, this, IRequestUrl.SET_SCAN_INFO);
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    @Override
    public void notifyData(int commdID, ParserCommonRsp respData, Object object) {
        switch (commdID) {
            case HttpRequestCode.CODE_SET_SCAN:
                if (respData.status == HttpRequestCode.HTTP_RESULT_SUCCESS) {
                    Intent intent = new Intent(this, LoginOnWebActivity.class);
                    intent.putExtra(Constant.KEY_SCAN_INFO, scanResult);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void notifyError(int commdID, HttpAsyncTask.ErrorMessage errMsg) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tvw_left:
                this.finish();
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }
}

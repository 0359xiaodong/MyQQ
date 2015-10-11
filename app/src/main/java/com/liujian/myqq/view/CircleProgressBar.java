package com.liujian.myqq.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.liujian.myqq.R;
import com.liujian.myqq.utils.LJLog;

/**
 * Created by sh.liujian on 2015-10-10.
 */
public class CircleProgressBar extends View {

    public final int TYPE_IMAGE = 1, TYPE_CIRCLE = 0;

    Bitmap mImage;

    private int mProgress = 0;
    private int mSecondaryProgress;
    private int mMax = 100;

    private int mImgResId;
    private int mProgressType;

    private int mTextResId;
    private int mTextSize = 45;
    private int mTextColor;
    private int mBackColor;
    private int mProgressColor;
    private int mProgressPadding;

    private int mWidth;
    private int mHeight;

    private int mRadius;

    private Paint paintBack;
    private Paint paintProgress;
    private Paint paintText;
    private Paint paintWhite = new Paint();

    Handler handler = new Handler(getContext().getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 10086) {
                if (mProgress < mMax) {
                    setProgress(mProgress + 1);
                    sendEmptyMessageDelayed(10086, 50);
                } else {
                    setProgress(0);
                    sendEmptyMessageDelayed(10086, 50);
                }
            }
        }
    };

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressBar, defStyleAttr, 0);

        int defaultColor = Color.argb(255, 255, 255, 255);

        mProgressType = array.getInt(R.styleable.CircleProgressBar_circle_progress_type, TYPE_CIRCLE);
        mTextSize = (int) array.getDimensionPixelSize(R.styleable.CircleProgressBar_circle_progress_textSize, mTextSize);
        mTextColor = array.getColor(R.styleable.CircleProgressBar_circle_progress_textColor, defaultColor);
        mBackColor = array.getColor(R.styleable.CircleProgressBar_circle_progress_backColor, defaultColor);
        mProgressColor = array.getColor(R.styleable.CircleProgressBar_circle_progress_progressColor, defaultColor);
        mImgResId = array.getResourceId(R.styleable.CircleProgressBar_circle_progress_image, 0);
        mProgressPadding = array.getDimensionPixelSize(R.styleable.CircleProgressBar_circle_progress_progressPadding, 0);
        mMax = array.getInt(R.styleable.CircleProgressBar_circle_progress_progressMax, mMax);
        if (mMax < 1) {
            mMax = 1;
        }
        mProgress = array.getInt(R.styleable.CircleProgressBar_circle_progress_progressCurrent, mProgress);

        if (mImgResId != 0) {
            mImage = BitmapFactory.decodeResource(context.getResources(), mImgResId);
        }

        mTextResId = array.getResourceId(R.styleable.CircleProgressBar_circle_progress_text, 0);

        paintBack = new Paint();
        paintBack.setColor(mBackColor);
        paintBack.setAntiAlias(true);

        paintProgress = new Paint();
        paintProgress.setColor(mProgressColor);
        paintProgress.setAntiAlias(true);

        paintText = new Paint();
        paintText.setColor(mTextColor);
        paintText.setAntiAlias(true);

        paintWhite.setColor(Color.WHITE);
        paintWhite.setAntiAlias(true);

        handler.sendEmptyMessageDelayed(10086, 500);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取系统进度条的宽度 这个宽度也是自定义进度条的宽度 所以在这里直接赋值
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        /** 设置宽度 */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mWidth = specSize;
        } else {
            // 由图片决定的宽
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            if (specMode == MeasureSpec.AT_MOST) {
                mWidth = Math.min(desireByImg, specSize);
            } else {
                mWidth = desireByImg;
            }
        }

        /** 设置高度 */
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mHeight = specSize;
        } else {
            int desire = getPaddingTop() + getPaddingBottom() + mImage.getHeight();
            if (specMode == MeasureSpec.AT_MOST) {
                mHeight = Math.min(desire, specSize);
            } else {
                mHeight = desire;
            }
        }
        setMeasuredDimension(mWidth, mHeight);
        mRadius = mWidth / 2;
    }

    private void drawCircleProgress(Canvas canvas) {
        paintProgress.setStyle(Paint.Style.STROKE);
        paintProgress.setStrokeWidth(2);
        Paint dark = new Paint();
        dark.setColor(Color.BLACK);
        canvas.drawArc(new RectF(1, 1, mWidth - 1, mHeight - 1), 0, 360, false, paintProgress);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius - mProgressPadding, paintBack);
        paintProgress.setStrokeWidth(mProgressPadding);
        canvas.drawArc(new RectF((mProgressPadding + 1) / 2, (mProgressPadding + 1) / 2, mWidth - (mProgressPadding + 1) / 2, mHeight - (mProgressPadding + 1) / 2), -90, (int) (mProgress * 360.0 / mMax), false, paintProgress);
        paintText.setTextSize(mTextSize);
        paintText.setTextAlign(Paint.Align.CENTER);
        String text = getContext().getString(mTextResId, 100 * mProgress / mMax);
        Paint.FontMetricsInt fontMetricsInt = paintText.getFontMetricsInt();
        canvas.drawText(text, mWidth / 2, mHeight / 2 - (fontMetricsInt.bottom + fontMetricsInt.top) / 2, paintText);
    }

    private void drawImageProgress(Canvas canvas) {
        mImage = resizeBitmap(mImage, mWidth, mHeight, false);
//        LJLog.d("Width " + mImage.getWidth() + " height " + mImage.getHeight());
        Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas newCanvas = new Canvas(target);
        paintProgress.setColor(mProgressColor);
        paintProgress.setAntiAlias(true);
        newCanvas.drawBitmap(mImage, 0, 0, paintProgress);
//        Rect rect = new Rect(0, (int) (mHeight * 1.0 * mProgress / mMax), mWidth, (int) (mHeight * 1.0 * mProgress / mMax) + mProgressPadding);  滚动式
//        Rect rect = new Rect(0, 0, mWidth, (int) (mHeight * 1.0 * mProgress / mMax)); //顶部式
        Rect rect = new Rect(0, mHeight - (int) (mHeight * 1.0 * mProgress / mMax), mWidth, mHeight); //顶部式
        paintProgress.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        newCanvas.drawRect(rect, paintProgress);
//        drawCanvasBorder(canvas);
        canvas.drawBitmap(target, 0, 0, null);
        paintProgress.reset();
    }

    private void drawCanvasBorder(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        float[] points = {0, 0,
                mWidth, 0,
                mWidth - 1, 0,
                mWidth - 1, mHeight - 1,
                mWidth - 1, mHeight - 1,
                0, mHeight - 1,
                0, mHeight - 1,
                0, 0
        };
        canvas.drawLines(points, paint);
    }

    private Bitmap resizeBitmap(Bitmap bitmap, int width, int height, boolean zoom) {
        Matrix matrix = new Matrix();
        float scaleX = width / (bitmap.getWidth() * 1.0f);
        float scaleY = height / (bitmap.getHeight() * 1.0f);
        if (zoom) {
            matrix.postScale(scaleX, scaleY); //长和宽放大缩小的比例
        } else {
            matrix.postScale(Math.min(scaleX, scaleY), Math.min(scaleX, scaleY)); //长和宽放大缩小的比例
        }
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    private float angle2radian(int angle) {
        return (float) (angle * Math.PI / 360);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        LJLog.d("Progress running...");
        switch (mProgressType) {
            case TYPE_CIRCLE:
                drawCircleProgress(canvas);
                break;
            case TYPE_IMAGE:
                drawImageProgress(canvas);
                break;
            default:
                drawCircleProgress(canvas);
                break;
        }
    }

    public void setMax(int max) {
        if (max <= 0) {
            return;
        }
        mMax = max;
        invalidate();
    }

    public void setProgress(int progress) {
        if (progress < 0 ) {
            progress = 0;
        }
        if (progress > mMax) {
            progress = mMax;
        }
        mProgress = progress;
        invalidate();
    }
}

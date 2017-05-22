package com.zhy.autolayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhy.autolayout.utils.AutoLayoutHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhy on 15/6/30.
 */
public class AutoLinearLayout extends LinearLayout
{
    private int WIDTH;
    private int HEIGHT;
    private int PAINT_ALPHA = 0;

    private int mPressedColor;
    private Paint mPaint;
    private int mShapeType;
    private int mRadius;

    private int mRoundRadius;
    private int mRippleColor;
    private int mRippleDuration;
    private int mRippleRadius;
    private float pointX, pointY;

    private Paint mRipplePaint;
    private RectF mRectF;
    private Path mPath;
    private Timer mTimer;
    private TimerTask mTask;
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_DRAW_COMPLETE){
                invalidate();
            }
        }
    };

    //private final static int HALF_ALPHA = 127;
    private  int RIPPLR_ALPHE = 0;
    private  int MSG_DRAW_COMPLETE = 101;

    private long mClickTime = 0;

    private AutoLayoutHelper mHelper = new AutoLayoutHelper(this);
    private int mRippleDurationTemp;

    public AutoLinearLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context,attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (!isInEditMode())
            mHelper.adjustChildren();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new LayoutParams(getContext(), attrs);
    }


    public static class LayoutParams extends LinearLayout.LayoutParams
            implements AutoLayoutHelper.AutoLayoutParams
    {
        private AutoLayoutInfo mAutoLayoutInfo;

        public LayoutParams(Context c, AttributeSet attrs)
        {
            super(c, attrs);
            mAutoLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(c, attrs);
        }

        @Override
        public AutoLayoutInfo getAutoLayoutInfo()
        {
            return mAutoLayoutInfo;
        }


        public LayoutParams(int width, int height)
        {
            super(width, height);
        }


        public LayoutParams(ViewGroup.LayoutParams source)
        {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source)
        {
            super(source);
        }

    }

    private void init(final Context context, final AttributeSet attrs) {
        if (isInEditMode())
            return;
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, com.zhy.autolayout.R.styleable.UIButton);
        mPressedColor = typedArray.getColor(com.zhy.autolayout.R.styleable.UIButton_color_pressed, getResources().getColor(com.zhy.autolayout.R.color.color_pressed_def));
        PAINT_ALPHA = typedArray.getInteger(com.zhy.autolayout.R.styleable.UIButton_alpha_pressed, PAINT_ALPHA);
        mShapeType = typedArray.getInt(com.zhy.autolayout.R.styleable.UIButton_shape_type, 1);
        mRadius = typedArray.getDimensionPixelSize(com.zhy.autolayout.R.styleable.UIButton_radius, getResources().getDimensionPixelSize(com.zhy.autolayout.R.dimen.ui_radius));
        mRippleColor = typedArray.getColor(
                com.zhy.autolayout.R.styleable.UIButton_ripple_color, getResources().getColor(com.zhy.autolayout.R.color.color_pressed_def)
        );
        RIPPLR_ALPHE = typedArray.getInteger(
                com.zhy.autolayout.R.styleable.UIButton_ripple_alpha, RIPPLR_ALPHE
        );
        mRippleDuration = typedArray.getInteger(
                com.zhy.autolayout.R.styleable.UIButton_ripple_duration, 800
        );
        mRippleDurationTemp = mRippleDuration;
        mShapeType = typedArray.getInt(com.zhy.autolayout.R.styleable.UIButton_shape_type, 1);
        mRoundRadius = typedArray.getDimensionPixelSize(com.zhy.autolayout.R.styleable.UIButton_radius,
                getResources().getDimensionPixelSize(com.zhy.autolayout.R.dimen.ui_radius));
        typedArray.recycle();
        if (RIPPLR_ALPHE!=0) {
            mRipplePaint = new Paint();
            mRipplePaint.setColor(mRippleColor);
            mRipplePaint.setAlpha(0);
            mRipplePaint.setStyle(Paint.Style.FILL);
            mRipplePaint.setAntiAlias(true);
            this.setWillNotDraw(false);
            mPath = new Path();
            mRectF = new RectF();
            pointY = pointX = -1;
        }
        if (PAINT_ALPHA!=0){
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mPressedColor);
            this.setWillNotDraw(false);
            mPaint.setAlpha(0);
            mPaint.setAntiAlias(true);
//            this.setDrawingCacheEnabled(true);
//        this.setClickable(true);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        WIDTH = w;
        HEIGHT = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRipplePaint == null||RIPPLR_ALPHE == 0) {

        } else {
            drawFillCircle(canvas);
        }

        if (mPaint == null||PAINT_ALPHA==0) return;
        if (mShapeType == 0) {
            canvas.drawCircle(WIDTH/2, HEIGHT/2, WIDTH/2.1038f, mPaint);
        } else {
            RectF rectF = new RectF();
            rectF.set(0, 0, WIDTH, HEIGHT);
            canvas.drawRoundRect(rectF, mRadius, mRadius, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mRipplePaint!=null) {
                    pointX = event.getX();
                    pointY = event.getY();
                    mRipplePaint.setAlpha(RIPPLR_ALPHE);
                    mClickTime = System.currentTimeMillis();
                    mRippleDuration = mRippleDurationTemp;
                    onStartDrawRipple();
                }
                if (mPaint!=null) {
                    mPaint.setAlpha(PAINT_ALPHA);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mRipplePaint!=null) {
                    long l = System.currentTimeMillis() - mClickTime;
                    if (l < mRippleDuration) {
                        mRippleDuration = 300;
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mRipplePaint.setAlpha(0);
                                onCompleteDrawRipple();
                                invalidate();
                            }
                        }, 300);
                    } else {
                        mRipplePaint.setAlpha(0);
                        onCompleteDrawRipple();
                    }
                }

                if (mPaint!=null) {
                    mPaint.setAlpha(0);
                }
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    /** Draw ripple effect*/
    private void drawFillCircle(Canvas canvas){
        if (canvas != null && pointX >= 0 && pointY >= 0){
            int rbX = canvas.getWidth();
            int rbY = canvas.getHeight();
            float x_max =  Math.max(pointX, Math.abs(rbX - pointX));
            float y_max =  Math.max(pointY, Math.abs(rbY - pointY));
            float longDis = (float) Math.sqrt(x_max*x_max+y_max*y_max);
//            if (mRippleRadius > longDis) {
//                onCompleteDrawRipple();
//                return;
//            }
            final float drawSpeed = longDis / mRippleDuration * 35;
            mRippleRadius += drawSpeed;

            canvas.save();
//            canvas.translate(0, 0);//保持原点
            mPath.reset();
            canvas.clipPath(mPath);
            if (mShapeType == 0){
                mPath.addCircle(rbX / 2, rbY / 2, WIDTH/2, Path.Direction.CCW);
            }else {
                mRectF.set(0, 0, WIDTH, HEIGHT);
                mPath.addRoundRect(mRectF, mRoundRadius, mRoundRadius, Path.Direction.CCW);
            }
            canvas.clipPath(mPath, Region.Op.REPLACE);
            canvas.drawCircle(pointX, pointY, mRippleRadius, mRipplePaint);
            canvas.restore();
        }
    }

    /** Start draw ripple effect*/
    private void onStartDrawRipple(){
        onCompleteDrawRipple();
        mTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(MSG_DRAW_COMPLETE);
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask, 0, 30);
    }

    /** Stop draw ripple effect*/
    private void onCompleteDrawRipple(){
        mHandler.removeMessages(MSG_DRAW_COMPLETE);
        if (mTimer != null){
            if (mTask != null){
                mTask.cancel();
            }
            mTimer.cancel();
        }
        mRippleRadius = 0;
    }
}

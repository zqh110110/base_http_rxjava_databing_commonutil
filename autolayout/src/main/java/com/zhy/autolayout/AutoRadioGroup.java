package com.zhy.autolayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * Created by zhy on 15/6/30.
 */
public class AutoRadioGroup extends RadioGroup
{
    private int WIDTH;
    private int HEIGHT;
    private int PAINT_ALPHA = 0;

    private int mPressedColor;
    private Paint mPaint;
    private int mShapeType;
    private int mRadius;

    private AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoRadioGroup(Context context, AttributeSet attrs)
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
    public RadioGroup.LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new AutoRadioGroup.LayoutParams(getContext(), attrs);
    }


    public static class LayoutParams extends RadioGroup.LayoutParams
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
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UIButton);
        mPressedColor = typedArray.getColor(R.styleable.UIButton_color_pressed, getResources().getColor(R.color.color_pressed_def));
        PAINT_ALPHA = typedArray.getInteger(R.styleable.UIButton_alpha_pressed, PAINT_ALPHA);
        mShapeType = typedArray.getInt(R.styleable.UIButton_shape_type, 1);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.UIButton_radius, getResources().getDimensionPixelSize(R.dimen.ui_radius));
        typedArray.recycle();
        if (PAINT_ALPHA==0) return;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mPressedColor);
        this.setWillNotDraw(false);
        mPaint.setAlpha(0);
        mPaint.setAntiAlias(true);
        this.setDrawingCacheEnabled(true);
//        this.setClickable(true);
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
        if(PAINT_ALPHA!=0) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPaint.setAlpha(PAINT_ALPHA);
                    invalidate();
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    mPaint.setAlpha(0);
                    invalidate();
                    break;
            }
        }
        return super.onTouchEvent(event);
    }
}

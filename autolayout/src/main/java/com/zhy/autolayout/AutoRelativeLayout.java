/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhy.autolayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zhy.autolayout.utils.AutoLayoutHelper;

public class AutoRelativeLayout extends RelativeLayout
{
    private int WIDTH;
    private int HEIGHT;
    private int PAINT_ALPHA = 0;

    private int mPressedColor;
    private Paint mPaint;
    private int mShapeType;
    private int mRadius;

    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoRelativeLayout(Context context)
    {
        super(context);
    }

    public AutoRelativeLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public AutoRelativeLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context,attrs);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (!isInEditMode())
            mHelper.adjustChildren();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
    }


    public static class LayoutParams extends RelativeLayout.LayoutParams
            implements AutoLayoutHelper.AutoLayoutParams
    {
        private AutoLayoutInfo mAutoLayoutInfo;

        public LayoutParams(Context c, AttributeSet attrs)
        {
            super(c, attrs);
            mAutoLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(c, attrs);
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

        @Override
        public AutoLayoutInfo getAutoLayoutInfo()
        {
            return mAutoLayoutInfo;
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

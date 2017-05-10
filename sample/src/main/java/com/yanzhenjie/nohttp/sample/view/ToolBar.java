package com.yanzhenjie.nohttp.sample.view;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ToolBar extends RelativeLayout {


    public LayoutInflater mInflater;
    public LinearLayout.LayoutParams layoutParamsFF = null;
    public LinearLayout.LayoutParams layoutParamsFW = null;
    public LinearLayout.LayoutParams layoutParamsWF = null;
    public LinearLayout.LayoutParams layoutParamsWW = null;
    public RelativeLayout ab_base = null;
    protected LinearLayout titleLayout = null;
    protected LinearLayout titleTextLayout = null;
    private LinearLayout.LayoutParams titleTextLayoutParams = null;
    private LinearLayout.LayoutParams rightViewLayoutParams = null;
    private LinearLayout.LayoutParams iconHeightLL;
    private LinearLayout.LayoutParams iconHeightLL2;
    protected TextView titleTextBtn = null;
    protected LinearLayout rightLayout = null;
    public int diaplayWidth = 320;
    public int diaplayHeight = 480;
    private WindowManager mWindowManager = null;
    private LinearLayout leftLayout;
    private Context context;

    public static float iconWidthRate = 0.134f;
    public static float iconHeightRate = 0.134f;
    public static int text_color_id = android.R.color.white;

    public ToolBar(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    protected void init() {
        mInflater = LayoutInflater.from(context);
        mWindowManager = (WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = mWindowManager.getDefaultDisplay();
        diaplayWidth = display.getWidth();
        diaplayHeight = display.getHeight();
        iconHeightLL = new LinearLayout.LayoutParams((int) (iconWidthRate * diaplayWidth), (int) (iconHeightRate * diaplayWidth));
        iconHeightLL2 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                (int) (iconHeightRate * diaplayWidth));

        initActionBar();
    }

    private void initActionBar() {
        layoutParamsFF = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        layoutParamsFW = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsWF = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        layoutParamsWW = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titleTextLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        titleTextLayoutParams.gravity = Gravity.CENTER_VERTICAL;
        rightViewLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        rightViewLayoutParams.gravity = Gravity.CENTER_VERTICAL;

        ab_base = this;
        ab_base.setClickable(true);

        titleLayout = new LinearLayout(context);
//        titleLayout.setMinimumHeight((int) (iconHeightRate * diaplayWidth));
        titleLayout.setOrientation(LinearLayout.HORIZONTAL);
        titleLayout.setGravity(Gravity.CENTER);
        titleLayout.setPadding(0, 0, 0, 0);

        titleTextLayout = new LinearLayout(context);
        titleTextLayout.setOrientation(LinearLayout.HORIZONTAL);
        titleTextLayout.setGravity(Gravity.CENTER);
        titleTextLayout.setPadding(0, 0, 0, 0);

//		titleTextBtn = new Button(this);
        titleTextBtn = new AlwaysMarqueeTextView(context);
        titleTextBtn.setTextColor(getResources().getColor(text_color_id));
        titleTextBtn.setTextSize(17);
//		titleTextBtn.getPaint().setFakeBoldText(true);
        titleTextBtn.setPadding(5, 0, 5, 0);
        titleTextBtn.setGravity(Gravity.CENTER);
//		titleTextBtn.setShadowLayer(3, 2, 2,
//				getResources().getColor(android.R.color.darker_gray));
        titleTextBtn.setBackgroundDrawable(null);
        titleTextBtn.setSingleLine(true);
        titleTextBtn.setFocusable(true);
        titleTextBtn.setFocusableInTouchMode(true);
        titleTextBtn.setHorizontallyScrolling(true);
        titleTextBtn.setEllipsize(TruncateAt.MARQUEE);
        titleTextLayout.addView(titleTextBtn, layoutParamsWF);

        // 加左边的按钮
        leftLayout = new LinearLayout(context);
        leftLayout.setOrientation(LinearLayout.HORIZONTAL);
        leftLayout.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
        leftLayout.setPadding(0, 0, 0, 0);
        leftLayout.setHorizontalGravity(Gravity.LEFT);
        leftLayout.setGravity(Gravity.CENTER_VERTICAL);
//        leftLayout.setVisibility(View.GONE);
        titleLayout.addView(leftLayout, rightViewLayoutParams);

        titleLayout.addView(titleTextLayout, titleTextLayoutParams);

        LayoutParams layoutParamsFW2 = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        ab_base.addView(titleLayout, layoutParamsFW2);

        rightLayout = new LinearLayout(context);
        rightLayout.setOrientation(LinearLayout.HORIZONTAL);
        rightLayout.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
        rightLayout.setPadding(0, 0, 0, 0);
        rightLayout.setHorizontalGravity(Gravity.RIGHT);
        rightLayout.setGravity(Gravity.CENTER_VERTICAL);
//        rightLayout.setVisibility(View.GONE);
        titleLayout.addView(rightLayout, rightViewLayoutParams);

    }

    public void setTitleText(CharSequence text) {
        titleTextBtn.setText(text);
        setTitleLayoutGravity(Gravity.CENTER, Gravity.CENTER);
    }

    public void setTitleText(int resId) {
        titleTextBtn.setText(resId);
        setTitleLayoutGravity(Gravity.CENTER, Gravity.CENTER);
    }

    public void setTitleTextColor(int color) {
        titleTextBtn.setTextColor(color);
    }

    public void addRightView(View rightView) {
        rightLayout.setVisibility(View.VISIBLE);
        rightLayout.addView(rightView, iconHeightLL);
    }

    public void addRightView2(View rightView) {
        rightLayout.setVisibility(View.VISIBLE);
        rightLayout.addView(rightView, iconHeightLL2);
    }

    public View addRightButtonView(int resId) {
        rightLayout.setVisibility(View.VISIBLE);
        ImageView btn = new ImageView(context);
        btn.setBackgroundResource(resId);
        rightLayout.addView(btn, iconHeightLL);
        return btn;
    }

    public Button addRightButtonTextView(String tx) {
        rightLayout.setVisibility(View.VISIBLE);
        Button btn = new Button(context);
        iconHeightLL2.rightMargin = 10;
        rightLayout.addView(btn, iconHeightLL2);
        btn.setText(tx);
        btn.setGravity(Gravity.CENTER);
        btn.setTextColor(getResources().getColor(text_color_id));
        return btn;
    }

    public View addRightTextView(String text) {
        rightLayout.setVisibility(View.VISIBLE);
        TextView tv_index = new TextView(context);
        tv_index.setTextColor(getResources().getColor(text_color_id));
        tv_index.setTextSize(16);
        tv_index.setText(text);
        layoutParamsWF.rightMargin = 15;
        rightLayout.setVisibility(View.VISIBLE);
        rightLayout.addView(tv_index, layoutParamsWF);
        return tv_index;
    }

    public ImageView addImgRightView(int imgdrawable) {
        ImageView iv = new ImageView(context);
        iv.setBackgroundResource(imgdrawable);
        rightLayout.setVisibility(View.VISIBLE);
        rightLayout.addView(iv, iconHeightLL);
        return iv;
    }

    public  void addTitleView(View view){
        titleLayout.addView(view, titleTextLayoutParams);
    }

    public void addLeftView(View leftView) {
        leftLayout.setVisibility(View.VISIBLE);
        leftLayout.addView(leftView, iconHeightLL);
    }

    public void clearLeftView() {
        leftLayout.removeAllViews();
    }

    public View addRightView(int resId) {
        rightLayout.setVisibility(View.VISIBLE);
        View view = mInflater.inflate(resId, null);
        rightLayout.addView(view, iconHeightLL);
        return view;
    }

    public void clearRightView() {
        rightLayout.removeAllViews();
    }

    public void dialogOpen(String title, String msg,
                           DialogInterface.OnClickListener mOkOnClickListener) {
        Builder builder = new Builder(context);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setPositiveButton("确定", mOkOnClickListener);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void dialogOpenNotCancel(String title, String msg,
                                    DialogInterface.OnClickListener mOkOnClickListener) {
        Builder builder = new Builder(context);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setPositiveButton("确定", mOkOnClickListener);
        builder.create().show();
    }

    public void dialogOpen(String title, String msg) {
        Builder builder = new Builder(context);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.create().show();
    }

    public LinearLayout getTitleLayout() {
        return titleLayout;
    }

    public void setTitleLayout(LinearLayout titleLayout) {
        this.titleLayout = titleLayout;
    }

    public void setTitleLayoutBackground(int res) {
        titleLayout.setBackgroundResource(res);
    }

    public void setTitleTextMargin(int left, int top, int right, int bottom) {
        titleTextLayoutParams.setMargins(left, top, right, bottom);
    }

    public void setTitleLayoutBackgroundColor(int color) {
        titleLayout.setBackgroundColor(color);
    }

    public void setTitleTextSize(int titleTextSize) {
        this.titleTextBtn.setTextSize(titleTextSize);
    }

    public void setTitleLayoutGravity(int gravity1, int gravity2) {
        measureView(this.rightLayout);
        measureView(this.leftLayout);
        int leftWidth = this.leftLayout.getMeasuredWidth();
        int rightWidth = this.rightLayout.getMeasuredWidth();
        this.titleTextLayoutParams.rightMargin = 0;
        this.titleTextLayoutParams.leftMargin = 0;
        if ((gravity1 == Gravity.CENTER_HORIZONTAL || gravity1 == Gravity.CENTER)) {
            if (leftWidth == 0 && rightWidth == 0) {
                this.titleTextLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            } else {
                if (gravity2 == Gravity.RIGHT) {
                    if (rightWidth == 0) {
                    } else {
                        this.titleTextBtn.setPadding(rightWidth / 3 * 2, 0, 0,
                                0);
                    }
                    this.titleTextBtn.setGravity(Gravity.CENTER);
                    this.rightLayout.setHorizontalGravity(Gravity.RIGHT);
                }
                if (gravity2 == Gravity.CENTER
                        || gravity2 == Gravity.CENTER_HORIZONTAL) {
                    this.titleTextLayout.setGravity(Gravity.CENTER);
                    this.rightLayout.setHorizontalGravity(Gravity.LEFT);
                    this.titleTextBtn.setGravity(Gravity.CENTER);
                    int offset = leftWidth - rightWidth;
                    if (offset > 0) {
                        this.titleTextLayoutParams.rightMargin = offset;
                    } else {
                        this.titleTextLayoutParams.leftMargin = Math
                                .abs(offset);
                    }
                }
            }
        } else if (gravity1 == Gravity.LEFT && gravity2 == Gravity.RIGHT) {
            this.titleTextLayout.setGravity(Gravity.LEFT);
            this.rightLayout.setHorizontalGravity(Gravity.RIGHT);
        } else if (gravity1 == Gravity.RIGHT && gravity2 == Gravity.RIGHT) {
            this.titleTextLayout.setGravity(Gravity.RIGHT);
            this.rightLayout.setHorizontalGravity(Gravity.RIGHT);
        } else if (gravity1 == Gravity.LEFT && gravity2 == Gravity.LEFT) {
            this.titleTextLayout.setGravity(Gravity.LEFT);
            this.rightLayout.setHorizontalGravity(Gravity.LEFT);
        }

    }

    public TextView getTitleTextButton() {
        return titleTextBtn;
    }

    public void setTitleTextBold(boolean bold) {
        TextPaint paint = titleTextBtn.getPaint();
        if (bold) {
            paint.setFakeBoldText(true);
        } else {
            paint.setFakeBoldText(false);
        }

    }

    public void setTitleTextBackgroundResource(int resId) {
        titleTextBtn.setBackgroundResource(resId);
    }

    public void setTitleLayoutBackgroundDrawable(Drawable d) {
        titleLayout.setBackgroundDrawable(d);
    }

    public void setTitleTextBackgroundDrawable(Drawable drawable) {
        titleTextBtn.setBackgroundDrawable(drawable);
    }

    public static View measureView(View v) {
        int w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
        return v;
    }

    public static class AlwaysMarqueeTextView extends android.support.v7.widget.AppCompatTextView {

        public AlwaysMarqueeTextView(Context context) {
            super(context);
        }

        public AlwaysMarqueeTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public AlwaysMarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        public boolean isFocused() {
            return true;
        }
    }
}

package com.smcb.simulatedstock.base;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smcb.simulatedstock.R;
import com.zhy.autolayout.config.AutoLayoutConifg;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseActivity extends AppCompatActivity implements IResult {

    private String TAG = "AbActivity";

    public static final String SHAREPATH = "app_share";

    protected Application mApplication;

    public String mProgressMessage = "请稍后...";

    public LayoutInflater mInflater;

    public android.app.ProgressDialog mProgressDialog;

    public SharedPreferences mSharedPreferences = null;

    public LinearLayout.LayoutParams layoutParamsFF = null;

    public LinearLayout.LayoutParams layoutParamsFW = null;

    public LinearLayout.LayoutParams layoutParamsWF = null;

    public LinearLayout.LayoutParams layoutParamsWW = null;

    protected ImageView logoView = null;

    protected ImageView logoLineView = null;

    public RelativeLayout ab_base = null;

    protected LinearLayout titleLayout = null;

    protected LinearLayout titleTextLayout = null;

    private LinearLayout.LayoutParams titleTextLayoutParams = null;

    private LinearLayout.LayoutParams rightViewLayoutParams = null;
    private LinearLayout.LayoutParams iconHeightLL;
    private LinearLayout.LayoutParams iconHeightLL2;
    private static final int titleLayoutID = R.id.titleLayoutID;
    public RelativeLayout contentLayout = null;
    protected TextView titleTextBtn = null;
    protected LinearLayout rightLayout = null;
    public int diaplayWidth = 320;
    public int diaplayHeight = 480;
    private WindowManager mWindowManager = null;
    protected Handler mhandler = new Handler();
    private LinearLayout leftLayout;

    public static float iconWidthRate = 0.134f;
    public static float iconHeightRate = 0.134f;
    public static int text_color_id = android.R.color.white;
    public static int top_bar_bg = R.color.colorPrimary;
    private boolean mIsShow;
    public final static AtomicInteger c = new AtomicInteger(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mInflater = LayoutInflater.from(this);
        mWindowManager = getWindowManager();
        Display display = mWindowManager.getDefaultDisplay();
        diaplayWidth = display.getWidth();
        diaplayHeight = display.getHeight();
        mApplication = getApplication();

        iconHeightLL = new LinearLayout.LayoutParams((int) (iconWidthRate * diaplayWidth), (int) (iconHeightRate * diaplayWidth));
        iconHeightLL2 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                (int) (iconHeightRate * diaplayWidth));
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(top_bar_bg));
//        StatusBarCompat.translucentStatusBar(this);
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
                ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        rightViewLayoutParams.gravity = Gravity.CENTER_VERTICAL;

        ab_base = new RelativeLayout(this);
        ab_base.setClickable(true);
        ab_base.setFitsSystemWindows(true);

        titleLayout = new LinearLayout(this);
        titleLayout.setMinimumHeight((int) (iconHeightRate * diaplayWidth));
        titleLayout.setId(titleLayoutID);
        titleLayout.setOrientation(LinearLayout.HORIZONTAL);
        titleLayout.setGravity(Gravity.CENTER);
        titleLayout.setPadding(0, 0, 0, 0);
        titleLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));

        contentLayout = new RelativeLayout(this);
        contentLayout.setPadding(0, 0, 0, 0);
        contentLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));

        titleTextLayout = new LinearLayout(this);
        titleTextLayout.setOrientation(LinearLayout.HORIZONTAL);
        titleTextLayout.setGravity(Gravity.CENTER);
        titleTextLayout.setPadding(0, 0, 0, 0);

//		titleTextBtn = new Button(this);
        titleTextBtn = new AlwaysMarqueeTextView(this);
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
        leftLayout = new LinearLayout(this);
        leftLayout.setOrientation(LinearLayout.HORIZONTAL);
        leftLayout.setGravity(Gravity.LEFT);
        leftLayout.setPadding(0, 0, 0, 0);
        leftLayout.setHorizontalGravity(Gravity.LEFT);
        leftLayout.setGravity(Gravity.CENTER_VERTICAL);
        leftLayout.setVisibility(View.GONE);
        titleLayout.addView(leftLayout, rightViewLayoutParams);

        logoView = new ImageView(this);
        logoView.setVisibility(View.GONE);
        logoLineView = new ImageView(this);
        logoLineView.setVisibility(View.GONE);

        titleLayout.addView(logoView, iconHeightLL);
        titleLayout.addView(logoLineView, layoutParamsWW);
        titleLayout.addView(titleTextLayout, titleTextLayoutParams);

        RelativeLayout.LayoutParams layoutParamsFW2 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                (int) (iconHeightRate * diaplayWidth));

        ab_base.addView(titleLayout, layoutParamsFW2);

        RelativeLayout.LayoutParams layoutParamsFW1 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsFW1.addRule(RelativeLayout.BELOW, titleLayoutID);
        ab_base.addView(contentLayout, layoutParamsFW1);

        rightLayout = new LinearLayout(this);
        rightLayout.setOrientation(LinearLayout.HORIZONTAL);
        rightLayout.setGravity(Gravity.RIGHT);
        rightLayout.setPadding(0, 0, 0, 0);
        rightLayout.setHorizontalGravity(Gravity.RIGHT);
        rightLayout.setGravity(Gravity.CENTER_VERTICAL);
        rightLayout.setVisibility(View.GONE);
        titleLayout.addView(rightLayout, rightViewLayoutParams);

        mSharedPreferences = getSharedPreferences(SHAREPATH,
                Context.MODE_PRIVATE);
        logoView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        super.setContentView(ab_base, layoutParamsFF);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsShow = true;
        if (ab_base != null) {
            loadDefaultStyle();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsShow = false;
    }

    private static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    public void loadDefaultStyle() {
        this.setTitleLayoutBackground(top_bar_bg);
        this.setTitleLayoutGravity(Gravity.CENTER, Gravity.CENTER);
        this.setTitleTextColor(getResources().getColor(text_color_id));
    }

    public void showToast(final String text) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, "" + text, Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }

    public void showToast(int resId) {
        Toast.makeText(this, "" + this.getResources().getText(resId),
                Toast.LENGTH_SHORT).show();
    }

    public void setTitleText(String text) {
        titleTextBtn.setText(text);
        setTitleLayoutGravity(Gravity.CENTER, Gravity.CENTER);
    }

    public void setTitleText(int resId) {
        titleTextBtn.setText(resId);
        setTitleLayoutGravity(Gravity.CENTER, Gravity.CENTER);
    }

    public void setLogo(Drawable drawable) {
        logoView.setVisibility(View.VISIBLE);
        logoView.setBackgroundDrawable(drawable);
    }

    public void setLogo(int resId) {
        logoView.setVisibility(View.VISIBLE);
        logoView.setBackgroundResource(resId);
    }

    public void setLogoLine(int resId) {
        logoLineView.setVisibility(View.VISIBLE);
        logoLineView.setBackgroundResource(resId);
    }

    public void setLogoLine(Drawable drawable) {
        logoLineView.setVisibility(View.VISIBLE);
        logoLineView.setBackgroundDrawable(drawable);
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
        ImageView btn = new ImageView(this);
        btn.setBackgroundResource(resId);
        rightLayout.addView(btn, iconHeightLL);
        return btn;
    }

    public Button addRightButtonTextView(String tx) {
        rightLayout.setVisibility(View.VISIBLE);
        Button btn = new Button(this);
        iconHeightLL2.rightMargin = 10;
        rightLayout.addView(btn, iconHeightLL2);
        btn.setText(tx);
        btn.setGravity(Gravity.CENTER);
        btn.setTextColor(getResources().getColor(text_color_id));
        return btn;
    }

    public View addRightTextView(String text) {
        rightLayout.setVisibility(View.VISIBLE);
        TextView tv_index = new TextView(this);
        tv_index.setTextColor(getResources().getColor(text_color_id));
        tv_index.setTextSize(16);
        tv_index.setText(text);
        layoutParamsWF.rightMargin = 15;
        rightLayout.setVisibility(View.VISIBLE);
        rightLayout.addView(tv_index, layoutParamsWF);
        return tv_index;
    }

    public ImageView addImgRightView(int imgdrawable) {
        ImageView iv = new ImageView(this);
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

    public void setAbContentView(View contentView) {
        initActionBar();
        contentLayout.removeAllViews();
        contentLayout.addView(contentView, layoutParamsFF);
    }

    public void setAbContentView(int resId) {
        initActionBar();
        contentLayout.removeAllViews();
        contentLayout.addView(mInflater.inflate(resId, null), layoutParamsFF);
    }

    public void setLogoBackOnClickListener(View.OnClickListener mOnClickListener) {
        logoView.setOnClickListener(mOnClickListener);
    }

    public void dialogOpen(String title, String msg,
                           DialogInterface.OnClickListener mOkOnClickListener) {
        Builder builder = new Builder(this);
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
        Builder builder = new Builder(this);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setPositiveButton("确定", mOkOnClickListener);
        builder.create().show();
    }

    public void dialogOpen(String title, String msg) {
        Builder builder = new Builder(this);
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
        measureView(this.logoView);
        measureView(this.leftLayout);
        int leftWidth = this.logoView.getMeasuredWidth()
                + this.leftLayout.getMeasuredWidth();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
//		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
//		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void defaultStartActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void finish() {
        super.finish();
//		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public void defaultFinish() {
        super.finish();
    }

    public void defaultStartActivity(Intent intent) {
        super.startActivity(intent);
    }

    public Application getMyApplication() {
        return mApplication;
    }

    public void setApplication(Application application) {
        this.mApplication = application;
    }

    /**
     * 通过Class跳转界面
     **/
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Action跳转界面
     **/
    protected void startActivity(String action) {
        startActivity(action, null);
    }

    /**
     * 含有Bundle通过Action跳转界面
     **/
    protected void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public static View measureView(View v) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
        return v;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onResult(int requestId,int response,Object o){

    }

    @Override
    public boolean isShow() {
        return this.mIsShow;
    }

    @Override
    public int getId() {
        return c.incrementAndGet();
    }
}

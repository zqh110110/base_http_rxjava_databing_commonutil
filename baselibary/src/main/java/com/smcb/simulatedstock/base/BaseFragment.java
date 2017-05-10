package com.smcb.simulatedstock.base;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smcb.simulatedstock.R;
import com.zhy.autolayout.config.AutoLayoutConifg;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class BaseFragment extends Fragment implements IResult {

	public static final int NONE = -1;

	/** 全局的LayoutInflater对象，已经完成初始化. */
	public LayoutInflater mInflater;

	/** 全局的Application对象，已经完成初始化. */
	public Application mApplication = null;

	/** 全局的SharedPreferences对象，已经完成初始化. */
	public SharedPreferences abSharedPreferences = null;

	/**
	 * LinearLayout.LayoutParams，已经初始化为FILL_PARENT, FILL_PARENT
	 */
	public LayoutParams layoutParamsFF = null;

	/**
	 * LinearLayout.LayoutParams，已经初始化为FILL_PARENT, WRAP_CONTENT
	 */
	public LayoutParams layoutParamsFW = null;

	/**
	 * LinearLayout.LayoutParams，已经初始化为WRAP_CONTENT, FILL_PARENT
	 */
	public LayoutParams layoutParamsWF = null;

	/**
	 * LinearLayout.LayoutParams，已经初始化为WRAP_CONTENT, WRAP_CONTENT
	 */
	public LayoutParams layoutParamsWW = null;

	/** 左侧的Logo图标View. */
	protected ImageView logoView = null;

	/** 左侧的Logo图标右边的分割线View. */
	protected ImageView logoLineView = null;

	/** 总布 */
	public RelativeLayout ab_base = null;

	/** 标题栏布 */
	protected LinearLayout titleLayout = null;

	/** 标题布局. */
	protected LinearLayout titleTextLayout = null;

	/** 标题文本的对齐参 */
	private LayoutParams titleTextLayoutParams = null;

	/** 右边布局的的对齐参数. */
	private LayoutParams rightViewLayoutParams = null;
	private LayoutParams iconHeightLL;
	/** 标题栏布D. */
	private static final int titleLayoutID = R.id.titleLayoutID;

	/** 主内容布 */
	protected RelativeLayout contentLayout = null;

	/** 显示标题文字的View. */
	protected TextView titleTextBtn = null;

	/** 右边的View，可以自定义显示 */
	protected LinearLayout rightLayout = null;

	/** The diaplay width. */
	public int diaplayWidth = 320;

	/** The diaplay height. */
	public int diaplayHeight = 480;

	/** The m window manager. */
	private WindowManager mWindowManager = null;

	/** 标题栏明标 */
	public static final String TITLE_TRANSPARENT_FLAG = "TITLE_TRANSPARENT_FLAG";

	/** 标题栏 */
	public static final int TITLE_TRANSPARENT = 0;

	/** 标题栏不透明. */
	public static final int TITLE_NOTRANSPARENT = 1;
	public static final String SHAREPATH = "app_share";

	private final static AtomicInteger c = new AtomicInteger(0);

	protected FragmentActivity act;
	public boolean hasActionBar = true;

	private LinearLayout contentView;

	private LinearLayout leftLayout;

	private static final String IMAGE_CACHE_DIR = "images";
	public static final String EXTRA_IMAGE = "extra_image";
//	public SimpleImageLoader mImageLoader;

	private float iconWidthRate=BaseActivity.iconWidthRate;
	private float iconHeightRate=BaseActivity.iconHeightRate;
	private int text_color_id=BaseActivity.text_color_id;
	private int top_bar_bg=BaseActivity.top_bar_bg;

	public Handler mHandler=new Handler();
	public LayoutParams iconHeightLL2;

	public boolean shouldPadding=false;

	public View rootView;
    private IReqData mIReqData;
	private int mVisiable = NONE;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		act =  getActivity();
		mInflater = LayoutInflater.from(act);
		mWindowManager = act.getWindowManager();
		mApplication =  act.getApplication();
		Display display = mWindowManager.getDefaultDisplay();
		diaplayWidth = display.getWidth();
		diaplayHeight = display.getHeight();
		final int longest = (diaplayHeight > diaplayWidth ? diaplayHeight : diaplayWidth) / 2;
//		DiskLruBasedCache.ImageCacheParams cacheParams =
//				new DiskLruBasedCache.ImageCacheParams(act, IMAGE_CACHE_DIR);
//		cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory
//		// The ImageFetcher takes care of loading images into our ImageView children asynchronously
//		mImageLoader = new SimpleImageLoader(act, cacheParams);//, R.drawable.empty_photo);
//		//mImageFetcher.setFadeInImage(false);
//		mImageLoader.setMaxImageSize(longest);

		iconHeightLL = new LayoutParams((int)(iconWidthRate*diaplayWidth),(int) (iconHeightRate*diaplayWidth));
		iconHeightLL2 = new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				(int) (iconHeightRate*diaplayWidth));

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public boolean isHasActionBar(){
		return hasActionBar;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		if (isHasActionBar()) {
			if (contentView == null) {
				initActionBar();
				contentView = new LinearLayout(act);
				contentView.setClickable(true);
				contentLayout.setClickable(true);
				contentView.setLayoutParams(new LayoutParams(
						ViewGroup.LayoutParams.FILL_PARENT,
						ViewGroup.LayoutParams.FILL_PARENT));
				contentView.addView(ab_base, layoutParamsFF);

				addChildView(contentLayout);
			} else {
				ViewGroup parent = ((ViewGroup) contentView.getParent());
				if (parent !=null) {
					parent.removeView(contentView);
				}
			}
			return contentView;
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public void addChildView(ViewGroup contentLayout) {

	}

	public void initActionBar() {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		layoutParamsFF = new LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT);
		layoutParamsFW = new LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParamsWF = new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.FILL_PARENT);
		layoutParamsWW = new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		titleTextLayoutParams = new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1);
		titleTextLayoutParams.gravity = Gravity.CENTER_VERTICAL;
		rightViewLayoutParams = new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 0);
		rightViewLayoutParams.gravity = Gravity.CENTER_VERTICAL;
		ab_base = new RelativeLayout(act);
		ab_base.setClickable(true);
		ab_base.setFitsSystemWindows(true);
//		ab_base.setBackgroundColor(getResources().getColor(R.color.black));

		titleLayout = new LinearLayout(act);
		titleLayout.setMinimumHeight((int) (iconHeightRate * diaplayWidth));
		titleLayout.setId(titleLayoutID);
		titleLayout.setOrientation(LinearLayout.HORIZONTAL);
		titleLayout.setGravity(Gravity.CENTER_VERTICAL);
		titleLayout.setPadding(0, 0, 0, 0);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && act instanceof BaseActivity) {
			titleLayout.setFitsSystemWindows(true);
		}
		contentLayout = new RelativeLayout(act);
		contentLayout.setPadding(0, 0, 0, 0);

		titleTextLayout = new LinearLayout(act);
		titleTextLayout.setOrientation(LinearLayout.HORIZONTAL);
		titleTextLayout.setGravity(Gravity.CENTER);
		titleTextLayout.setPadding(0, 0, 0, 0);

//		titleTextBtn = new Button(act);
		titleTextBtn = new AlwaysMarqueeTextView(act);
		titleTextBtn.setTextColor(getResources().getColor(text_color_id));
//		titleTextBtn.setShadowLayer(3, 2, 2,
//				getResources().getColor(android.R.color.darker_gray));
//		titleTextBtn.getPaint().setFakeBoldText(true);
		titleTextBtn.setTextSize(17);
		titleTextBtn.setPadding(5, 0, 5, 0);
		titleTextBtn.setGravity(Gravity.CENTER);
		titleTextBtn.setBackgroundDrawable(null);
		titleTextBtn.setSingleLine(true);
		titleTextBtn.setFocusable(true);
		titleTextBtn.setFocusableInTouchMode(true);
		titleTextBtn.setHorizontallyScrolling(true);
		titleTextBtn.setEllipsize(TruncateAt.MARQUEE);

		titleTextLayout.addView(titleTextBtn, layoutParamsWF);

		// 加左边的按钮
		leftLayout = new LinearLayout(act);
		leftLayout.setOrientation(LinearLayout.HORIZONTAL);
		leftLayout.setGravity(Gravity.LEFT);
		leftLayout.setPadding(0, 0, 0, 0);
		leftLayout.setHorizontalGravity(Gravity.LEFT);
		leftLayout.setGravity(Gravity.CENTER_VERTICAL);
		leftLayout.setVisibility(View.GONE);
		titleLayout.addView(leftLayout, rightViewLayoutParams);

		logoView = new ImageView(act);
		logoView.setVisibility(View.GONE);
		logoLineView = new ImageView(act);
		logoLineView.setVisibility(View.GONE);

		titleLayout.addView(logoView, iconHeightLL);
		titleLayout.addView(logoLineView, layoutParamsWW);
		titleLayout.addView(titleTextLayout, titleTextLayoutParams);

		Intent intent = act.getIntent();
		int titleTransparentFlag = intent.getIntExtra(TITLE_TRANSPARENT_FLAG,
				TITLE_NOTRANSPARENT);
		RelativeLayout.LayoutParams layoutParamsFW2 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				(int) (iconHeightRate*diaplayWidth));
		if (titleTransparentFlag == TITLE_TRANSPARENT) {
			ab_base.addView(contentLayout, layoutParamsFW);
			layoutParamsFW2.addRule(RelativeLayout.ALIGN_PARENT_TOP,
					RelativeLayout.TRUE);
			ab_base.addView(titleLayout, layoutParamsFW2);
		} else {
			ab_base.addView(titleLayout, layoutParamsFW2);
			RelativeLayout.LayoutParams layoutParamsFW1 = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			layoutParamsFW1.addRule(RelativeLayout.BELOW, titleLayoutID);
			ab_base.addView(contentLayout, layoutParamsFW1);
		}

		// 加右边的按钮
		rightLayout = new LinearLayout(act);
		rightLayout.setOrientation(LinearLayout.HORIZONTAL);
		rightLayout.setGravity(Gravity.RIGHT);
		rightLayout.setPadding(0, 0, 0, 0);
		rightLayout.setHorizontalGravity(Gravity.RIGHT);
		rightLayout.setGravity(Gravity.CENTER_VERTICAL);
		rightLayout.setVisibility(View.GONE);
		titleLayout.addView(rightLayout, rightViewLayoutParams);

		logoView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				back();
			}
		});

	}

	@Override
	public void onResume() {
		super.onResume();
		if (ab_base != null) {
			loadDefaultStyle();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT&&Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP&& act instanceof BaseActivity) {
			if (shouldPadding) {
				View view = getView();
				if (view != null) {
					DisplayMetrics metrics = getResources().getDisplayMetrics();
					int STATUS_BAR_HEIGHT = (int) Math.ceil( 25 * metrics.density);
					view.setPadding(0, STATUS_BAR_HEIGHT, 0, 0);
				}
			}
		}
	}

	public void loadDefaultStyle() {
		this.setTitleLayoutBackground(top_bar_bg);
		this.setTitleLayoutGravity(Gravity.CENTER, Gravity.CENTER);
		this.setTitleTextColor(getResources().getColor(
				text_color_id));
	}

	/**
	 *
	 */
	protected void back() {
		getFragmentManager().popBackStack();
	}

	/**
	 * 描述：设置标题文本颜
	 *
	 * @param
	 */
	public void setTitleTextColor(int color) {
		titleTextBtn.setTextColor(color);
	}

	/**
	 * 描述：设置标题文
	 *
	 * @param text
	 *            文本
	 */
	public void setTitleText(String text) {
		titleTextBtn.setText(text);
		setTitleLayoutGravity(Gravity.CENTER, Gravity.CENTER);
	}

	/**
	 * 描述：设置标题文
	 *
	 * @param resId
	 *            文本的资源ID
	 */
	public void setTitleText(int resId) {
		titleTextBtn.setText(resId);
		setTitleLayoutGravity(Gravity.CENTER, Gravity.CENTER);
	}

	/**
	 * 描述：设置Logo的背景图.
	 *
	 * @param drawable
	 *            Logo资源Drawable
	 */
	public void setLogo(Drawable drawable) {
		logoView.setVisibility(View.VISIBLE);
		logoView.setBackgroundDrawable(drawable);
	}

	/**
	 * 描述：设置Logo的背景资
	 *
	 * @param resId
	 *            Logo资源ID
	 */
	public void setLogo(int resId) {
		logoView.setVisibility(View.VISIBLE);
		logoView.setBackgroundResource(resId);
	}

	/**
	 * 描述：设置Logo分隔线的背景资源.
	 *
	 * @param resId
	 *            Logo资源ID
	 */
	public void setLogoLine(int resId) {
		logoLineView.setVisibility(View.VISIBLE);
		logoLineView.setBackgroundResource(resId);
	}

	/**
	 * 描述：设置Logo分隔线的背景
	 *
	 * @param drawable
	 *            Logo资源Drawable
	 */
	public void setLogoLine(Drawable drawable) {
		logoLineView.setVisibility(View.VISIBLE);
		logoLineView.setBackgroundDrawable(drawable);
	}

	/**
	 * 描述：把指定的View填加到标题栏右边.
	 *
	 * @param rightView
	 *            指定的View
	 */
	public void addRightView(View rightView) {
		rightLayout.setVisibility(View.VISIBLE);
		rightLayout.addView(rightView, iconHeightLL);
	}

	public void addRightTextView(String text) {
		TextView tv_index = new TextView(act);
		tv_index.setTextColor(getResources().getColor(text_color_id));
		tv_index.setTextSize(22);
		tv_index.setText(text);
		layoutParamsWF.rightMargin = 15;
		rightLayout.setVisibility(View.VISIBLE);
		rightLayout.addView(tv_index, layoutParamsWF);
	}

	public void showToast(final String text) {
		act.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(act, "" + text, Toast.LENGTH_SHORT)
						.show();
			}
		});

	}

	public void addLeftView(View leftView) {
		leftLayout.setVisibility(View.VISIBLE);
		leftLayout.addView(leftView, iconHeightLL);
	}
	public void addLeftView2(View leftView) {
		leftLayout.setVisibility(View.VISIBLE);
		leftLayout.addView(leftView, iconHeightLL2);
	}
	public void addLeftViewNoLL(View leftView) {
		leftLayout.setVisibility(View.VISIBLE);
		leftLayout.addView(leftView);
	}

	/**
	 * 描述：把指定资源ID表示的View填加到标题栏右边.
	 *
	 * @param resId
	 *            指定的View的资源ID
	 */
	public View addRightView(int resId) {
		rightLayout.setVisibility(View.VISIBLE);
		View view=mInflater.inflate(resId, null);
		rightLayout.addView(view, iconHeightLL);
		return view;
	}

	public View addRightButtonView(int resId) {
		rightLayout.setVisibility(View.VISIBLE);
		ImageView btn = new ImageView(act);
		LayoutParams searchIconHeightLL = new LayoutParams((int)(0.0612 * diaplayWidth), (int)(0.0639 * diaplayWidth));
		searchIconHeightLL.rightMargin = (int)(0.097 * diaplayWidth);
		// btn.setLayoutParams(new LinearLayout.LayoutParams(20, 20));
		// btn.setScaleType(ScaleType.FIT_XY);

		btn.setBackgroundResource(resId);
		rightLayout.addView(btn, searchIconHeightLL);
		return btn;
	}

	/**
	 * 描述：清除标题栏右边的View.
	 */
	public void clearRightView() {
		rightLayout.removeAllViews();
	}

	public void clearLeftView() {
		leftLayout.removeAllViews();
	}

	/**
	 * 描述：用指定的View填充主界
	 *
	 * @param contentView
	 *            指定的View
	 */
	public void setAbContentView(View contentView) {
		contentLayout.removeAllViews();
		contentLayout.addView(contentView, layoutParamsFF);
	}

	public void setAbContentView(View contentView, ViewGroup.LayoutParams p) {
		contentLayout.removeAllViews();
		contentLayout.addView(contentView, p);
	}

	/**
	 * 描述：用指定资源ID表示的View填充主界
	 *
	 * @param resId
	 *            指定的View的资源ID
	 */
	public void setAbContentView(int resId) {
		contentLayout.removeAllViews();
		contentLayout.addView(mInflater.inflate(resId, null), layoutParamsFF);
	}

	/**
	 * 描述：设置Logo按钮的返回事
	 *
	 * @param mOnClickListener
	 *            指定的返回事
	 */
	public void setLogoBackOnClickListener(View.OnClickListener mOnClickListener) {
		logoView.setOnClickListener(mOnClickListener);
	}

	/**
	 * 描述：对话框dialog （确认，取消
	 *
	 * @param title
	 *            对话框标题内
	 * @param msg
	 *            对话框提示内
	 * @param mOkOnClickListener
	 *            点击确认按钮的事件监
	 */
	public void dialogOpen(String title, String msg,
						   DialogInterface.OnClickListener mOkOnClickListener) {
		Builder builder = new Builder(act);
		builder.setMessage(msg);
		builder.setTitle(title);
		builder.setPositiveButton("确认", mOkOnClickListener);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	/**
	 * 描述：对话框dialog （无按钮
	 *
	 * @param title
	 *            对话框标题内
	 * @param msg
	 *            对话框提示内
	 */
	public void dialogOpen(String title, String msg) {
		Builder builder = new Builder(act);
		builder.setMessage(msg);
		builder.setTitle(title);
		builder.create().show();
	}

	/**
	 * Gets the title layout.
	 *
	 * @return the title layout
	 */
	public LinearLayout getTitleLayout() {
		return titleLayout;
	}

	/**
	 * Sets the title layout.
	 *
	 * @param titleLayout
	 *            the new title layout
	 */
	public void setTitleLayout(LinearLayout titleLayout) {
		this.titleLayout = titleLayout;
	}

	/**
	 * 描述：标题栏的背景图.
	 *
	 * @param res
	 *            背景图资源ID
	 */
	public void setTitleLayoutBackground(int res) {
		titleLayout.setBackgroundResource(res);
	}

	/**
	 * 描述：标题文字的对齐.
	 *
	 * @param left
	 *            the left
	 * @param top
	 *            the top
	 * @param right
	 *            the right
	 * @param bottom
	 *            the bottom
	 */
	public void setTitleTextMargin(int left, int top, int right, int bottom) {
		titleTextLayoutParams.setMargins(left, top, right, bottom);
	}

	/**
	 * 描述：标题栏的背景图.
	 *
	 * @param color
	 *            背景颜色
	 */
	public void setTitleLayoutBackgroundColor(int color) {
		titleLayout.setBackgroundColor(color);
	}

	/**
	 * 描述：标题文字字
	 *
	 * @param titleTextSize
	 *            文字字号
	 */
	public void setTitleTextSize(int titleTextSize) {
		this.titleTextBtn.setTextSize(titleTextSize);
	}

	/**
	 * 描述：设置标题文字对齐方 根据右边的具体情况判定方向： ）中间靠Gravity.CENTER,Gravity.CENTER
	 * ）左边居右边居右Gravity.LEFT,Gravity.RIGHT
	 * ）左边居中，右边居右Gravity.CENTER,Gravity.RIGHT
	 * ）左边居右，右边居右Gravity.RIGHT,Gravity.RIGHT 必须在addRightView(view)方法后设
	 *
	 * @param gravity1
	 *            标题对齐方式
	 * @param gravity2
	 *            右边布局对齐方式
	 */
	public void setTitleLayoutGravity(int gravity1, int gravity2) {
		measureView2(this.rightLayout);
		measureView2(this.logoView);
		measureView2(this.leftLayout);
		int leftWidth = this.logoView.getMeasuredWidth()
				+ this.leftLayout.getMeasuredWidth();
		int rightWidth = this.rightLayout.getMeasuredWidth();
		// Log.d(TAG, "测量布局的宽度："+leftWidth+","+rightWidth);
		this.titleTextLayoutParams.rightMargin = 0;
		this.titleTextLayoutParams.leftMargin = 0;
		// 全部中间
		if ((gravity1 == Gravity.CENTER_HORIZONTAL || gravity1 == Gravity.CENTER)) {
			if (leftWidth == 0 && rightWidth == 0) {
				this.titleTextLayout.setGravity(Gravity.CENTER);
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
					this.titleTextLayout.setGravity(Gravity.CENTER_HORIZONTAL);
					this.rightLayout.setHorizontalGravity(Gravity.RIGHT);
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
			// 左右
		} else if (gravity1 == Gravity.LEFT && gravity2 == Gravity.RIGHT) {
			this.titleTextLayout.setGravity(Gravity.LEFT);
			this.rightLayout.setHorizontalGravity(Gravity.RIGHT);
			// 全部右靠
		} else if (gravity1 == Gravity.RIGHT && gravity2 == Gravity.RIGHT) {
			this.titleTextLayout.setGravity(Gravity.RIGHT);
			this.rightLayout.setHorizontalGravity(Gravity.RIGHT);
		} else if (gravity1 == Gravity.LEFT && gravity2 == Gravity.LEFT) {
			this.titleTextLayout.setGravity(Gravity.LEFT);
			this.rightLayout.setHorizontalGravity(Gravity.LEFT);
		}

	}

	/**
	 * 描述：获取标示标题文本的Button.
	 *
	 * @return the title Button view
	 */
	public TextView getTitleTextButton() {
		return titleTextBtn;
	}

	/**
	 * 描述：设置标题字体粗
	 *
	 * @param bold
	 *            the new title text bold
	 */
	public void setTitleTextBold(boolean bold) {
		TextPaint paint = titleTextBtn.getPaint();
		if (bold) {
			// 粗体
			paint.setFakeBoldText(true);
		} else {
			paint.setFakeBoldText(false);
		}

	}

	/**
	 * 描述：设置标题背
	 *
	 * @param resId
	 *            the new title text background resource
	 */
	public void setTitleTextBackgroundResource(int resId) {
		titleTextBtn.setBackgroundResource(resId);
	}

	/**
	 * 描述：设置标题背
	 *
	 * @param d
	 *            背景
	 */
	public void setTitleLayoutBackgroundDrawable(Drawable d) {
		titleLayout.setBackgroundDrawable(d);
	}

	/**
	 * 描述：设置标题背
	 *
	 * @param drawable
	 *            the new title text background drawable
	 */
	public void setTitleTextBackgroundDrawable(Drawable drawable) {
		titleTextBtn.setBackgroundDrawable(drawable);
	}

	public static View measureView2(View v) {
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		v.measure(w, h);
		return v;
	}

	public void gotoFragment(int id,BaseFragment f,boolean addBackStack)
	{
		FragmentTransaction ft = act.getSupportFragmentManager().beginTransaction();
//		if(tabIndex>currentModeTabIndex)
//		{
//			ft.setCustomAnimations(R.anim.push_left_in,
//					R.anim.push_left_out, R.anim.push_right_in,
//					R.anim.push_right_out);
//		}
//		else
//		{
//			ft.setCustomAnimations(R.anim.push_right_in,
//					R.anim.push_right_out, R.anim.push_left_in,
//					R.anim.push_left_out);
//		}
		if (id==android.R.id.content)
		{
			f.shouldPadding=true;
		}
		ft.replace(id, f);
		if(addBackStack) {
			ft.addToBackStack(null);
		}
		ft.commitAllowingStateLoss();
	}

	public long getID() {
		return BaseActivity.c.incrementAndGet();
	}

	public void onFragmentResult(int  requestid,int responeid,Object o){

	}

	public void refresh() {

	}

	public boolean canBack()
	{
		return true;
	}


	public static BaseFragment getInstance() {
		return null;
	}


	public void notify(Object o) {

	}

	@Override
	public void onResult(int requestId, int response, Object o) {

	}

	@Override
	public boolean isShow() {
		if (mVisiable!=NONE) {
			return mVisiable==View.VISIBLE?true:false;
		}
		return isVisible()&&isResumed();
	}

	public void setShow(int v) {
		mVisiable = v;
	}
}

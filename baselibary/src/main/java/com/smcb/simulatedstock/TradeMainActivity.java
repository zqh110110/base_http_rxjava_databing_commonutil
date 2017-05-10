package com.smcb.simulatedstock;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.smcb.simulatedstock.base.BaseActivity;
import com.smcb.simulatedstock.base.BaseFragment;
import com.smcb.simulatedstock.base.IChangePage;
import com.smcb.simulatedstock.base.IReqData;
import com.smcb.simulatedstock.base.PollingListenner;
import com.smcb.simulatedstock.base.SPUtils;
import com.smcb.simulatedstock.library.indicatorview.IndicatorView;
import com.smcb.simulatedstock.library.log.KLog;
import com.smcb.simulatedstock.page.BuyStockFragment;
import com.smcb.simulatedstock.page.CancelBillFragment;
import com.smcb.simulatedstock.page.SellStockFragment;
import com.smcb.simulatedstock.page.StockPositionFragment;
import com.smcb.simulatedstock.page.TradeQueryFragment;
import com.smcb.simulatedstock.utils.Constant;
import com.smcb.tl.entity.FundEntity;

import java.util.ArrayList;
import java.util.List;


public class TradeMainActivity extends BaseActivity implements IChangePage {

    public static final String USER_ACCOUNT = "user_account";
    public static final String USER_PWD = "user_pwd";
    public static final String TITLEBAR_SHOW = "TITLEBAR_SHOW";

    private ViewPager vp;
    private IndicatorView tabView;
    private List<BaseFragment> fragments = new ArrayList<>();
    private View rightView;
    private int pageIndex = 0;

    private View.OnClickListener mGotoHQPageListener;
    private PollingListenner mPollingListenner;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.trade_main);
        Intent it = getIntent();
        if (it!=null) {
            String user_account = it.getStringExtra(USER_ACCOUNT);
            String user_pwd = it.getStringExtra(USER_PWD);
            boolean titlebar_show = it.getBooleanExtra(TITLEBAR_SHOW,true);
            if (titleLayout != null) {
                titleLayout.setVisibility(titlebar_show?View.VISIBLE:View.GONE);
            }
            if (user_account !=null) {
                setAccountAndPossword(this,user_account,user_pwd);
            }
        }
        setTitleText("模拟炒股");
        initViews();
    }

    private void initViews() {
        tabView = (IndicatorView) findViewById(R.id.tabView);
        vp = (ViewPager) findViewById(R.id.vp);
        tabView.setViewPager(vp);
        fragments.add(StockPositionFragment.getInstance());
        fragments.add(BuyStockFragment.getInstance());
        fragments.add(SellStockFragment.getInstance());
        fragments.add(CancelBillFragment.getInstance());
        fragments.add(TradeQueryFragment.getInstance());
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        tabView.setOnIndicatorChangedListener(new IndicatorView.OnIndicatorChangedListener() {
            @Override
            public void onIndicatorChanged(int oldSelectedIndex, int newSelectedIndex) {
                pageIndex = newSelectedIndex;
                vp.setCurrentItem(newSelectedIndex);
            }
        });
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndex = position;
                tabView.setIndexWithViewPager(position);
//                if (pageIndex == 4) {
//                    rightView.setVisibility(View.GONE);
//                } else {
//                    rightView.setVisibility(View.VISIBLE);
//                }
//                setTitleLayoutGravity(Gravity.CENTER,Gravity.CENTER);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragments.get(pageIndex).refresh();
                    }
                },300);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rightView =mInflater.inflate(R.layout.refresh_view,null);
        addRightView(rightView);
        rightView.setVisibility(View.GONE);
        rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragments.get(pageIndex).refresh();
            }
        });
        mPollingListenner = new PollingListenner() {
            @Override
            public void polling() {
                try {
                    BaseFragment f = fragments.get(pageIndex);
                    if (f != null && f.isShow()) {
                        fragments.get(pageIndex).refresh();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        TradeMainUtil.getInstance().addPollingListener(mPollingListenner);
        TradeMainUtil.getInstance().setmChangePageListener(this);
    }

    public void setGoToHQPageListener(View.OnClickListener listener) {
        TradeMainUtil.getInstance().setGoToHQPageListener(listener);
    }

    public void setAccountAndPossword(Context context,String account, String pwd) {
        TradeMainUtil.getInstance().setAccountAndPossword(context,account,pwd);
    }

    public void changePage(int index, Object o) {
        pageIndex = index;
        vp.setCurrentItem(index);
        fragments.get(index).notify(o);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragments.get(pageIndex).refresh();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (count > 0) {
                getSupportFragmentManager().popBackStack();
                return true;
            }
            if (!fragments.get(pageIndex).canBack()) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TradeMainUtil.getInstance().removePollingLinstener(mPollingListenner);
    }
}

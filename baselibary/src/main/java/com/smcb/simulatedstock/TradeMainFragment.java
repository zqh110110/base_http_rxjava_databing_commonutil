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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

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


public class TradeMainFragment extends BaseFragment implements IChangePage {

    private ViewPager vp;
    private IndicatorView tabView;
    private List<BaseFragment> fragments = new ArrayList<>();
    private View rightView;
    private int pageIndex = 0;
    private PollingListenner mPollingListenner;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void addChildView(ViewGroup contentLayout) {
        rootView = mInflater.inflate(R.layout.trade_main,null);
        contentLayout.addView(rootView,layoutParamsFF);
        setTitleText("模拟炒股");
        setTitleLayoutGravity(Gravity.CENTER,Gravity.CENTER);
    }

    private void initViews() {
        tabView = (IndicatorView) rootView.findViewById(R.id.tabView);
        vp = (ViewPager) rootView.findViewById(R.id.vp);
        tabView.setViewPager(vp);
        fragments.add(StockPositionFragment.getInstance());
        fragments.add(BuyStockFragment.getInstance());
        fragments.add(SellStockFragment.getInstance());
        fragments.add(CancelBillFragment.getInstance());
        fragments.add(TradeQueryFragment.getInstance());
        vp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
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
                },500);
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

        TradeMainUtil.getInstance().setmChangePageListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPollingListenner == null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    initViews();
                }
            },50);

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
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fragments.get(pageIndex).refresh();
            }
        },300);
    }

    public void changeVisiable(int v) {
        int size = fragments.size();
        for (int i=0;i<size;i++) {
            fragments.get(i).setShow(v);
        }
    }

    @Override
    public void changePage(int index, Object o) {
        pageIndex = index;
        vp.setCurrentItem(index);
        fragments.get(index).notify(o);
    }

    public void setGoToHQPageListener(View.OnClickListener listener) {
        TradeMainUtil.getInstance().setGoToHQPageListener(listener);
    }

    public void setAccountAndPossword(Context context,String account, String pwd) {
        TradeMainUtil.getInstance().setAccountAndPossword(context,account,pwd);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        TradeMainUtil.getInstance().removePollingLinstener(mPollingListenner);
    }
}

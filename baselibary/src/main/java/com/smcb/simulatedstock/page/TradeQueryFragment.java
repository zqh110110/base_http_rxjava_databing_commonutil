package com.smcb.simulatedstock.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.smcb.simulatedstock.R;
import com.smcb.simulatedstock.base.BaseFragment;
import com.smcb.simulatedstock.base.IReqData;
import com.smcb.simulatedstock.utils.Constant;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * Created by Administrator on 2017/3/10.
 */

public class TradeQueryFragment extends BaseFragment implements View.OnClickListener {

    // Content View Elements

    private com.zhy.autolayout.AutoLinearLayout mMenu1;
    private com.zhy.autolayout.AutoLinearLayout mMenu2;
    private com.zhy.autolayout.AutoLinearLayout mMenu3;
    private com.zhy.autolayout.AutoLinearLayout mMenu4;
    private AutoLinearLayout mMenu5;

    // End Of Content View Elements

    private void bindViews() {
        mMenu1 = (com.zhy.autolayout.AutoLinearLayout) rootView.findViewById(R.id.menu1);
        mMenu2 = (com.zhy.autolayout.AutoLinearLayout) rootView.findViewById(R.id.menu2);
        mMenu3 = (com.zhy.autolayout.AutoLinearLayout) rootView.findViewById(R.id.menu3);
        mMenu4 = (com.zhy.autolayout.AutoLinearLayout) rootView.findViewById(R.id.menu4);
        mMenu5 = (com.zhy.autolayout.AutoLinearLayout) rootView.findViewById(R.id.menu5);

        mMenu1.setOnClickListener(this);
        mMenu2.setOnClickListener(this);
        mMenu3.setOnClickListener(this);
        mMenu4.setOnClickListener(this);
        mMenu5.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = mInflater.inflate(R.layout.trade_query_fragment,null);
            bindViews();
        } else {
            ViewGroup p = (ViewGroup) rootView.getParent();
            if (p != null) {
                p.removeView(rootView);
            }
        }
        return rootView;
    }

    public static  BaseFragment getInstance() {
        return new TradeQueryFragment();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.menu1) {
            Intent it = new Intent();
            it.setClass(act,TadyCompletedActivity.class);
            startActivity(it);
//            gotoFragment(android.R.id.content, new TadyCompletedFragment().setReqDataUtil(getReqDataUtil()), true);
        } else if (id == R.id.menu2) {
            Intent it = new Intent();
            it.setClass(act,TadyEntrustedActivity.class);
            startActivity(it);
//            gotoFragment(android.R.id.content, new TadyEntrustedFragment().setReqDataUtil(getReqDataUtil()), true);
        } else if (id == R.id.menu3) {
            Intent it = new Intent();
            it.setClass(act,HistoryCompletedActivity.class);
            startActivity(it);
//                gotoFragment(android.R.id.content,new HistoryCompletedFragment().setReqDataUtil(getReqDataUtil()),true);
        } else if (id == R.id.menu4) {
            Intent it = new Intent();
            it.setClass(act,HistoryEntrustedActivity.class);
            startActivity(it);
//                gotoFragment(android.R.id.content,new HistoryEntrustedFragment().setReqDataUtil(getReqDataUtil()),true);
        } else if (id == R.id.menu5) {
            Intent it = new Intent();
            it.setClass(act,RunningAccountActivity.class);
            startActivity(it);
//                gotoFragment(android.R.id.content,new RunningAccountFragment().setReqDataUtil(getReqDataUtil()),true);
        }
    }
}

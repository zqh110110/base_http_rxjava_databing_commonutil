package com.smcb.simulatedstock.page;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.smcb.simulatedstock.R;
import com.smcb.simulatedstock.TradeMainUtil;
import com.smcb.simulatedstock.base.BaseFragment;
import com.smcb.simulatedstock.base.IReqData;
import com.smcb.simulatedstock.library.commonadapter.CommonAdapter;
import com.smcb.simulatedstock.utils.DataUtil;
import com.smcb.simulatedstock.utils.TradeServiceUtil;
import com.smcb.tl.entity.HisOrderEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/3/13.
 */

public class TadyEntrustedFragment extends BaseFragment {
    private IReqData activity;

    private View mEmpty;
    private View leftView;
    private View rightView;
    private ListView mListView;
    private List<Object> mBillStocks = new ArrayList<>();
    private CommonAdapter<Object> mBillStockCommonAdapter;

    @Override
    public void addChildView(ViewGroup contentLayout) {
        rootView = mInflater.inflate(R.layout.tady_entrusted_fragment,null);
        contentLayout.addView(rootView,layoutParamsFF);
        setTitleText("当日委托");
        bindViews();
        initViews();
        refresh();
    }

    private void bindViews() {
        activity = (IReqData) TradeMainUtil.getInstance();
        mEmpty =  rootView.findViewById(R.id.empty);
        mListView = (ListView) rootView.findViewById(R.id.listView);
        leftView=mInflater.inflate(R.layout.back_view,null);
        addLeftView(leftView);
        leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        rightView =mInflater.inflate(R.layout.refresh_view,null);
        addRightView(rightView);
        rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
    }

    private void initViews() {
        mBillStockCommonAdapter = DataUtil.initBillStocks(TradeServiceUtil.M_ENTRUST_SEARCH, mListView, mBillStocks, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void refresh() {
        activity.sendMsg(TadyEntrustedFragment.this, TradeServiceUtil.M_ENTRUST_SEARCH,null);
    }

    @Override
    public void onFragmentResult(int requestid, int responeid, Object o) {
        if (requestid == TradeServiceUtil.M_ENTRUST_SEARCH) {
            List<HisOrderEntity> data = (List<HisOrderEntity>) o;
            if (data != null && data.size() >0) {
                mEmpty.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);
                mBillStocks.clear();
                mBillStocks.addAll(data);
                mBillStockCommonAdapter.notifyDataSetChanged();
            } else {
                mEmpty.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
            }
        }
    }
}

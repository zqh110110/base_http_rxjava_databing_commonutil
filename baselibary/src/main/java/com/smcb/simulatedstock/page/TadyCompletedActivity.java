package com.smcb.simulatedstock.page;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.smcb.simulatedstock.R;
import com.smcb.simulatedstock.TradeMainUtil;
import com.smcb.simulatedstock.base.BaseActivity;
import com.smcb.simulatedstock.base.BaseFragment;
import com.smcb.simulatedstock.base.IReqData;
import com.smcb.simulatedstock.library.commonadapter.CommonAdapter;
import com.smcb.simulatedstock.utils.DataUtil;
import com.smcb.simulatedstock.utils.TradeServiceUtil;
import com.smcb.tl.entity.HisTradeEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/3/13.
 */

public class TadyCompletedActivity extends BaseActivity {

    private IReqData activity;
    private View mEmpty;

    private View leftView;
    private View rightView;
    private ListView mListView;
    private List<Object> mBillStocks = new ArrayList<>();
    private CommonAdapter<Object> mBillStockCommonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.tady_completed_fragment);
        setTitleText("当日成交");
        bindViews();
        initViews();
        refresh();
    }

    private void initViews() {
        mBillStockCommonAdapter = DataUtil.initBillStocks(TradeServiceUtil.M_DEAL_SEARCH, mListView, mBillStocks, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void bindViews() {
        activity = TradeMainUtil.getInstance();
        mEmpty =  findViewById(R.id.empty);
        mListView = (ListView) findViewById(R.id.listView);
        leftView=mInflater.inflate(R.layout.back_view,null);
        addLeftView(leftView);
        leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

    public void refresh() {
        activity.sendMsg(this, TradeServiceUtil.M_DEAL_SEARCH,null);
    }

    public void onResult(int requestid, int responeid, Object o) {
        if (requestid == TradeServiceUtil.M_DEAL_SEARCH) {
            List<HisTradeEntity> data = (List<HisTradeEntity>) o;
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

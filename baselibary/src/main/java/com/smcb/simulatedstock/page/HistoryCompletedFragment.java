package com.smcb.simulatedstock.page;

import android.app.DatePickerDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.ListView;

import com.smcb.simulatedstock.R;
import com.smcb.simulatedstock.TradeMainUtil;
import com.smcb.simulatedstock.base.BaseFragment;
import com.smcb.simulatedstock.base.IReqData;
import com.smcb.simulatedstock.library.commonadapter.CommonAdapter;
import com.smcb.simulatedstock.utils.DataUtil;
import com.smcb.simulatedstock.utils.TradeServiceUtil;
import com.smcb.tl.entity.HisTradeEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/3/13.
 */

public class HistoryCompletedFragment extends BaseFragment {

    private IReqData activity;
    private View mEmpty;

    // Content View Elements

    private TextView mStartTime;
    private TextView mEndTime;
    private View leftView;
    private View rightView;
    private ListView mListView;
    private List<Object> mBillStocks = new ArrayList<>();
    private CommonAdapter<Object> mBillStockCommonAdapter;
    private View mStartTimeC;
    private View mEndTimeC;

    // End Of Content View Elements

    private void bindViews() {
        activity = (IReqData) TradeMainUtil.getInstance();
        mEmpty =  rootView.findViewById(R.id.empty);
        mListView = (ListView) rootView.findViewById(R.id.listView);
        mStartTime = (TextView) rootView.findViewById(R.id.startTime);
        mEndTime = (TextView) rootView.findViewById(R.id.endTime);
        mStartTimeC = rootView.findViewById(R.id.startTimeC);
        mEndTimeC = rootView.findViewById(R.id.endTimeC);

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar instance = Calendar.getInstance();
        int mYear = instance.get(Calendar.YEAR);
        int mMonth = instance.get(Calendar.MONTH);
        int mDay = instance.get(Calendar.DAY_OF_MONTH)-7;
        instance.set(mYear,mMonth,mDay);
        String date = sdf.format(instance.getTime());
        mStartTime.setText(date);
        instance.set(mYear,mMonth,mDay+7);
        date = sdf.format(instance.getTime());
        mEndTime.setText(date);

        mStartTimeC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar ca = Calendar.getInstance();
                int mYear = ca.get(Calendar.YEAR);
                int mMonth = ca.get(Calendar.MONTH);
                int mDay = ca.get(Calendar.DAY_OF_MONTH)-7;
                new DatePickerDialog(act,new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int M, int d) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        Calendar instance = Calendar.getInstance();
                        instance.set(y,M,d);
                        String date = sdf.format(instance.getTime());
                        mStartTime.setText(date);
                        refresh();
                    }
                }, mYear, mMonth, mDay).show();
            }
        });
        mEndTimeC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar ca = Calendar.getInstance();
                int mYear = ca.get(Calendar.YEAR);
                int mMonth = ca.get(Calendar.MONTH);
                int mDay = ca.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(act,new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int M, int d) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        Calendar instance = Calendar.getInstance();
                        instance.set(y,M,d);
                        String date = sdf.format(instance.getTime());
                        mEndTime.setText(date);
                        refresh();
                    }
                }, mYear, mMonth, mDay).show();
            }
        });
    }

    @Override
    public void addChildView(ViewGroup contentLayout) {
        rootView = mInflater.inflate(R.layout.history_completed_fragment,null);
        contentLayout.addView(rootView,layoutParamsFF);
        setTitleText("历史成交");
        bindViews();
        initViews();
        refresh();
    }

    private void initViews() {
        mBillStockCommonAdapter = DataUtil.initBillStocks(TradeServiceUtil.M_DEAL_HIS, mListView, mBillStocks, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void refresh() {
        reqListData(mStartTime.getText().toString(),mEndTime.getText().toString());
    }

    private void reqListData(String startTime,String endTime) {
        Map<String, String> args = new HashMap<>();
        args.put("startTime",startTime);
        args.put("endTime",endTime);
        activity.sendMsg(this, TradeServiceUtil.M_DEAL_HIS,args);
    }

    @Override
    public void onFragmentResult(int requestid, int responeid, Object o) {
        if (requestid == TradeServiceUtil.M_DEAL_HIS) {
            if (o instanceof String) {
                showToast((String) o);
                return;
            }
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

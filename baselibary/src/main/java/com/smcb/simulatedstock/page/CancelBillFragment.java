package com.smcb.simulatedstock.page;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smcb.simulatedstock.R;
import com.smcb.simulatedstock.TradeMainUtil;
import com.smcb.simulatedstock.base.BaseFragment;
import com.smcb.simulatedstock.base.IReqData;
import com.smcb.simulatedstock.library.commonadapter.CommonAdapter;
import com.smcb.simulatedstock.utils.DataUtil;
import com.smcb.simulatedstock.utils.TradeServiceUtil;
import com.smcb.tl.entity.HisOrderEntity;
import com.zhy.autolayout.widget.UIButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/3/10.
 */

public class CancelBillFragment extends BaseFragment {


    // Content View Elements

    private com.zhy.autolayout.AutoLinearLayout mStockCantainer;
    private com.zhy.autolayout.AutoLinearLayout mStockCantainer2;
    private View mEmpty;
    private Dialog mDialog;
    private ListView mListView;
    private List<Object> mBillStocks = new ArrayList<>();
    private CommonAdapter<Object> mBillStockCommonAdapter;

    // End Of Content View Elements

    private void bindViews() {

        mStockCantainer2 = (com.zhy.autolayout.AutoLinearLayout) rootView.findViewById(R.id.stockCantainer2);
        mEmpty =  rootView.findViewById(R.id.empty);
        mListView = (ListView) rootView.findViewById(R.id.listView);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = mInflater.inflate(R.layout.cancel_bill_fragment,null);
            bindViews();
            initViews();
        } else {
            ViewGroup p = (ViewGroup) rootView.getParent();
            if (p != null) {
                p.removeView(rootView);
            }
        }
        return rootView;
    }

    private void initViews() {
        mBillStockCommonAdapter = DataUtil.initBillStocks(TradeServiceUtil.M_ENTRUST_SEARCH, mListView, mBillStocks, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HisOrderEntity item = (HisOrderEntity) view.getTag();
                if (DataUtil.canCancelBill(act, item.getWTTP())) {
                    if (mDialog == null) {
                        mDialog = new Dialog(act, R.style.trade_pop);
                    }
                    View view1 = mInflater.inflate(R.layout.cancel_bill_dialog, null);
                    TextView mTitle = (TextView) view1.findViewById(R.id.title);
                    TextView mName = (TextView) view1.findViewById(R.id.name);
                    TextView mControl = (TextView) view1.findViewById(R.id.control);
                    TextView mCodeTv = (TextView) view1.findViewById(R.id.code);
                    TextView mCount = (TextView) view1.findViewById(R.id.count);
                    TextView mPriceTv = (TextView) view1.findViewById(R.id.price);
                    TextView mPriceTx = (TextView) view1.findViewById(R.id.priceTx);
                    TextView mCountTx = (TextView) view1.findViewById(R.id.countTx);
                    UIButton mCancel = (UIButton) view1.findViewById(R.id.cancel);
                    UIButton mSure = (UIButton) view1.findViewById(R.id.sure);

                    if ("1".equals(item.getBSFG())) {//买入
                        mControl.setText("撤买入单");
                    } else {
                        mControl.setText("撤卖出单");
                    }
                    mName.setText(item.getZQMC());
                    mCodeTv.setText(item.getZQDM());
                    mCount.setText(DataUtil.str2Int(item.getWTSL()) - DataUtil.str2Int(item.getCJSL()) + "");
                    mPriceTv.setText(item.getWTJG());
                    mCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mDialog != null) {
                                mDialog.dismiss();
                            }
                        }
                    });
                    mSure.setTag(item);
                    mSure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            HisOrderEntity item = (HisOrderEntity) view.getTag();
                            if (mDialog != null) {
                                mDialog.dismiss();
                            }
                            Map<String, String> args = new HashMap<>();
                            args.put("buyOrSell", item.getBSFG());
                            args.put("code", item.getWTBH());
                            TradeMainUtil.getInstance().sendMsg(CancelBillFragment.this, TradeServiceUtil.M_CANCEL_BILL, args);
                        }
                    });

                    mDialog.setContentView(view1);
                    mDialog.show();
                }
            }
        });
    }

    @Override
    public void refresh() {
        TradeMainUtil.getInstance().sendMsg(this, TradeServiceUtil.M_ENTRUST_SEARCH,null);
    }

    @Override
    public void onResult(int requestid, int responeid, Object o) {
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

        if (TradeServiceUtil.M_CANCEL_BILL == requestid) {
            String info = (String) o;
            if (info !=null) {
                Map<String,String> map = new Gson().fromJson(info, new TypeToken<Map<String, String>>() {
                }.getType());
                showToast(map.get("ERMT"));
            }
        }
    }

    public static  BaseFragment getInstance() {
        return new CancelBillFragment();
    }
}

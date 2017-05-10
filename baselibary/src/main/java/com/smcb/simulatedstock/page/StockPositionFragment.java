package com.smcb.simulatedstock.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.smcb.simulatedstock.R;
import com.smcb.simulatedstock.TradeMainUtil;
import com.smcb.simulatedstock.base.BaseFragment;
import com.smcb.simulatedstock.base.IReqData;
import com.smcb.simulatedstock.library.commonadapter.CommonAdapter;
import com.smcb.simulatedstock.utils.Constant;
import com.smcb.simulatedstock.utils.DataUtil;
import com.smcb.simulatedstock.utils.TradeServiceUtil;
import com.smcb.tl.entity.FundEntity;
import com.smcb.tl.entity.HolderEntity;
import com.smcb.tl.entity.ShareEntity;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/3/10.
 */

public class StockPositionFragment extends BaseFragment {

    public int INDEX = 0;

    // Content View Elements

    private TextView mZzc;
    private TextView mFdyk;
    private TextView mZsz;
    private TextView mKq;
    private TextView mKy;
    private View mEmpty;
    private ListView mListView;

    public static ArrayList<ShareEntity> mShareEntityData = new ArrayList<>();
    private ArrayList<HolderEntity> mHolderEntities;
    private CommonAdapter<ShareEntity> mShareEntityCommonAdapter;
    private TextView mFdyktx;
    private TextView mKqtx;


    // End Of Content View Elements

    private void bindViews() {

        mZzc = (TextView) rootView.findViewById(R.id.zzc);
        mFdyk = (TextView) rootView.findViewById(R.id.fdyk);
        mFdyktx = (TextView) rootView.findViewById(R.id.fdyktx);
        mZsz = (TextView) rootView.findViewById(R.id.zsz);
        mKq = (TextView) rootView.findViewById(R.id.kq);
        mKqtx = (TextView) rootView.findViewById(R.id.kqtx);
        mKy = (TextView) rootView.findViewById(R.id.ky);
        mEmpty = rootView.findViewById(R.id.empty);
        mListView = (ListView) rootView.findViewById(R.id.listView);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = mInflater.inflate(R.layout.stock_position_fragment,null);
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
        mShareEntityData.clear();
        mShareEntityCommonAdapter = DataUtil.initShareStocks(mListView, mShareEntityData,null,
        new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转买入页面
                ShareEntity item = (ShareEntity) view.getTag();
                TradeMainUtil.getInstance().changePage(1,item);
            }
        },new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转卖出页面
                ShareEntity item = (ShareEntity) view.getTag();
                TradeMainUtil.getInstance().changePage(2,item);
            }
        },TradeMainUtil.getInstance().getGoToPageListener()/*new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转行情页面
                ShareEntity item = (ShareEntity) view.getTag();

            }
        }*/);

    }

    @Override
    public void refresh() {
        TradeMainUtil.getInstance().sendMsg(this, TradeServiceUtil.M_CAPTIAL_SEARCH,null);
        TradeMainUtil.getInstance().sendMsg(this, TradeServiceUtil.M_HOLD_SEARCH,null);
        if (mHolderEntities == null) {
            TradeMainUtil.getInstance().sendMsg(this, TradeServiceUtil.M_HOLDER_SEARCH, null);
        }
    }

    @Override
    public void onResult(int requestid, int responeid, Object o) {
        if (requestid == TradeServiceUtil.M_CAPTIAL_SEARCH) {
            FundEntity data = (FundEntity) o;
            TradeMainUtil.getInstance().setFundEntity(data);
            mZsz.setText(DataUtil.parseDouble(data.getZSZ(),2));
            mZzc.setText(DataUtil.parseDouble(data.getZCZZ(),2));
            double fdyk = DataUtil.str2Double(data.getZCZZ()) - Constant.YSZJ;
            mFdyk.setText(DataUtil.parseDouble(fdyk,2));
            if (fdyk>0) {
                mFdyk.setTextColor(act.getResources().getColor(R.color.up_tx_color));
                mFdyktx.setTextColor(act.getResources().getColor(R.color.up_tx_color));
            } else if (fdyk==0) {
                mFdyk.setTextColor(act.getResources().getColor(R.color.black));
                mFdyktx.setTextColor(act.getResources().getColor(R.color.black));
            } else {
                mFdyk.setTextColor(act.getResources().getColor(R.color.down_tx_color));
                mFdyktx.setTextColor(act.getResources().getColor(R.color.down_tx_color));
            }
            mKy.setText(DataUtil.parseDouble(data.getKYZJ(),2));
        }
        if (requestid == TradeServiceUtil.M_HOLD_SEARCH) {
            ArrayList<ShareEntity> data = (ArrayList<ShareEntity>) o;
            if (data != null && data.size() >0) {
                mEmpty.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);
                mShareEntityData.clear();
                mShareEntityData.addAll(data);
                mShareEntityCommonAdapter.notifyDataSetChanged();
                int size = mShareEntityData.size();
                double yk = 0;
                for (int i=0;i<size;i++) {
                    yk+=DataUtil.str2Double(DataUtil.parseDouble(mShareEntityData.get(i).getFDYK(), 3));
                }
                mKq.setText(DataUtil.parseDouble(yk,2));
                if (yk>0) {
                    mKq.setTextColor(act.getResources().getColor(R.color.up_tx_color));
                    mKqtx.setTextColor(act.getResources().getColor(R.color.up_tx_color));
                } else if (yk==0) {
                    mKq.setTextColor(act.getResources().getColor(R.color.black));
                    mKqtx.setTextColor(act.getResources().getColor(R.color.black));
                } else {
                    mKq.setTextColor(act.getResources().getColor(R.color.down_tx_color));
                    mKqtx.setTextColor(act.getResources().getColor(R.color.down_tx_color));
                }
            } else {
                mKq.setText("0.00");
                mEmpty.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
            }
        }
        if (requestid == TradeServiceUtil.M_HOLDER_SEARCH) {
            mHolderEntities = (ArrayList<HolderEntity>) o;
        }
    }

    public static  BaseFragment getInstance() {
        return new StockPositionFragment();
    }
}

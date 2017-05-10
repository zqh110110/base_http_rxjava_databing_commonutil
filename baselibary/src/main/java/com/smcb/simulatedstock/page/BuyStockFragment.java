package com.smcb.simulatedstock.page;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smcb.simulatedstock.R;
import com.smcb.simulatedstock.TradeMainUtil;
import com.smcb.simulatedstock.adapter.StockItem;
import com.smcb.simulatedstock.base.BaseFragment;
import com.smcb.simulatedstock.base.IReqData;
import com.smcb.simulatedstock.data.DbManager;
import com.smcb.simulatedstock.data.entity.HQResult;
import com.smcb.simulatedstock.data.entity.MarkEntity;
import com.smcb.simulatedstock.data.gen.MarkEntityDao;
import com.smcb.simulatedstock.library.commonadapter.CommonAdapter;
import com.smcb.simulatedstock.library.commonadapter.item.AdapterItem;
import com.smcb.simulatedstock.library.log.KLog;
import com.smcb.simulatedstock.utils.DataCacheUtils;
import com.smcb.simulatedstock.utils.DataUtil;
import com.smcb.simulatedstock.utils.TradeServiceUtil;
import com.smcb.tl.entity.ShareEntity;
import com.zhy.autolayout.widget.UIButton;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/3/10.
 */

public class BuyStockFragment extends BaseFragment {

    private static final String MARKENTITY_CACHE = "MarkEntity_Cache";
    // Content View Elements
    public  int INDEX = 1;

    private com.zhy.autolayout.AutoLinearLayout mStockNameCantainer;
    private EditText mStockCode;
    private com.zhy.autolayout.AutoLinearLayout mPriceCantainer;
    private View mPriceD;
    private View mLine1;
    private EditText mPrice;
    private View mLine2;
    private View mPriceP;
    private TextView mDprice;
    private TextView mZprice;
    private com.zhy.autolayout.AutoLinearLayout mStockCountCantainer;
    private View mCountD;
    private View mCountP;
    private View mLine4;
    private EditText mStockCount;
    private View mLine5;
    private TextView mCanCount;
    private com.zhy.autolayout.AutoLinearLayout mSelectCount;
    private com.zhy.autolayout.widget.UIButton mCommit;
    private LinearLayout mBuyCantainer;
    private LinearLayout mSellCantainer;
    private com.zhy.autolayout.AutoLinearLayout mEmpty;
    private TextView mStockName;
    private ListView mListView;
    private ListView mStockListView;


    private List<MarkEntity> mStockList = new ArrayList<>();

    private List<String> mHodlers = new ArrayList<>();

    public static final int BUY = 1;
    public static final int SELL = 2;

    public int type = BUY;//1买0卖
    private CommonAdapter<MarkEntity> mCommonAdapter;
    private String mCode;
    private String mStid;
    private int mCount;
    private boolean isTextChange = false;
    private ArrayList<ShareEntity> mShareEntitys = new ArrayList<>();
    private boolean mPriceHasSet = false;
    private Dialog mBuyStockDialog;
    private HQResult hqResult;
    private CommonAdapter<ShareEntity> mShareEntityCommonAdapter;


    // End Of Content View Elements

    private void bindViews() {

        mStockNameCantainer = (com.zhy.autolayout.AutoLinearLayout) rootView.findViewById(R.id.stockNameCantainer);
        mStockCode = (EditText) rootView.findViewById(R.id.stockCode);
        mStockName = (TextView) rootView.findViewById(R.id.stockName);
        mPriceCantainer = (com.zhy.autolayout.AutoLinearLayout) rootView.findViewById(R.id.priceCantainer);
        mPriceD =  rootView.findViewById(R.id.priceD);
        mLine1 = (View) rootView.findViewById(R.id.line1);
        mPrice = (EditText) rootView.findViewById(R.id.price);
        mLine2 = (View) rootView.findViewById(R.id.line2);
        mPriceP =  rootView.findViewById(R.id.priceP);
        mDprice = (TextView) rootView.findViewById(R.id.dprice);
        mZprice = (TextView) rootView.findViewById(R.id.zprice);
        mStockCountCantainer = (com.zhy.autolayout.AutoLinearLayout) rootView.findViewById(R.id.stockCountCantainer);
        mCountD =  rootView.findViewById(R.id.countD);
        mCountP =  rootView.findViewById(R.id.countP);
        mLine4 = (View) rootView.findViewById(R.id.line4);
        mStockCount = (EditText) rootView.findViewById(R.id.stockCount);
        mLine5 = (View) rootView.findViewById(R.id.line5);
        mCanCount = (TextView) rootView.findViewById(R.id.canCount);
        mSelectCount = (com.zhy.autolayout.AutoLinearLayout) rootView.findViewById(R.id.selectCount);
        mCommit = (com.zhy.autolayout.widget.UIButton) rootView.findViewById(R.id.commit);
        mBuyCantainer = (LinearLayout) rootView.findViewById(R.id.buyCantainer);
        mSellCantainer = (LinearLayout) rootView.findViewById(R.id.sellCantainer);
        mEmpty = (com.zhy.autolayout.AutoLinearLayout) rootView.findViewById(R.id.empty);
        mListView = (ListView) rootView.findViewById(R.id.listView2);
        mStockListView = (ListView) rootView.findViewById(R.id.listView);
    }

    private void init() {
        if (type == SELL) {
//            mLine1.setBackgroundColor(getResources().getColor(R.color.cornflowerblue));
//            mLine2.setBackgroundColor(getResources().getColor(R.color.cornflowerblue));
//            mLine4.setBackgroundColor(getResources().getColor(R.color.cornflowerblue));
//            mLine5.setBackgroundColor(getResources().getColor(R.color.cornflowerblue));
            mCommit.setBackgroundResource(R.drawable.border_round_down_bg);
            mPrice.setHint("卖出价格");
            mCanCount.setText("可卖--股");
            mStockCount.setHint("卖出量");
//            mStockNameCantainer.setBackgroundResource(R.drawable.input_border2);
//            mStockCountCantainer.setBackgroundResource(R.drawable.input_border2);
//            mPriceCantainer.setBackgroundResource(R.drawable.input_border2);
//            mPriceD.setBackgroundResource(R.mipmap.jian_btn_bg2);
//            mPriceP.setBackgroundResource(R.mipmap.jia_btn_bg2);
//            mCountD.setBackgroundResource(R.mipmap.jian_btn_bg2);
//            mCountP.setBackgroundResource(R.mipmap.jia_btn_bg2);
            mCommit.setText("卖出");
        }
        mStockList.clear();
        mCommonAdapter = new CommonAdapter<MarkEntity>(mStockList, 1) {
            @NonNull
            @Override
            public AdapterItem createItem(Object o) {
                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MarkEntity markEntity = (MarkEntity) view.getTag();
                        List<MarkEntity> object = (List<MarkEntity>) DataCacheUtils.getAppConfig(act).readObject(MARKENTITY_CACHE);
                        boolean has = false;
                        if (object == null) {
                            object = new ArrayList<>();
                        } else {
                            int size = object.size();
                            for (int i = 0;i<size;i++) {
                                MarkEntity markEntity1 = object.get(i);
                                if (markEntity1.getCODE().equals(markEntity.getCODE())&&markEntity1.getNAME().equals(markEntity.getNAME())) {
                                    has = true;
                                    break;
                                }
                            }
                        }
                        if (!has) {
                            object.add(markEntity);
                            DataCacheUtils.getAppConfig(act).saveObject((Serializable) object,MARKENTITY_CACHE);
                        }
                        mStockName.setText(markEntity.getNAME());
                        mStockCode.setText(markEntity.getCODE());
                        mCode = markEntity.getCODE();
                        mStid = DataUtil.getSetCode(0,markEntity.getSTID())+"";
                        mStockList.clear();
                        mStockCount.setText("100");
                        mPriceHasSet = false;
                        showPopWin();
                        refresh();
                    }
                };
                return new StockItem(mStockCode, mStockName, mListView,onClickListener);
            }
        };
        mListView.setAdapter(mCommonAdapter);

        mStockCode.addTextChangedListener(new TextWatcher() {

            private String oldStr;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                oldStr = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isTextChange) {
                    return;
                }

                if ("".equals(oldStr)&&charSequence.toString().equals("")||charSequence.toString().equals(oldStr)) {//没有变化
                    return;
                }

                MarkEntityDao mDao = DbManager.getDaoSession(act).getMarkEntityDao();
                Query<MarkEntity> query = null;
                if (type == BUY) {
                    QueryBuilder<MarkEntity> queryBuilder = mDao.queryBuilder();
                    query = queryBuilder
                            .whereOr(
                             queryBuilder.and(MarkEntityDao.Properties.CODE.like(charSequence + "%"),MarkEntityDao.Properties.CODE.like( "600%"))
                            ,queryBuilder.and(MarkEntityDao.Properties.CODE.like(charSequence + "%"),MarkEntityDao.Properties.CODE.like( "601%"))
                            ,queryBuilder.and(MarkEntityDao.Properties.CODE.like(charSequence + "%"),MarkEntityDao.Properties.CODE.like( "000%"))
                            ,queryBuilder.and(MarkEntityDao.Properties.CODE.like(charSequence + "%"),MarkEntityDao.Properties.CODE.like( "002%"))
                            ,queryBuilder.and(MarkEntityDao.Properties.CODE.like(charSequence + "%"),MarkEntityDao.Properties.CODE.like( "300%")))
                            .build();
                } else {
                    QueryBuilder<MarkEntity> queryBuilder = mDao.queryBuilder();
                    query = queryBuilder.where(queryBuilder.and(MarkEntityDao.Properties.CODE.like(charSequence + "%"),MarkEntityDao.Properties.NAME.in(mHodlers))).build();
                }

                if ("".equals(charSequence.toString())&&type == BUY) {
                    mStockList.clear();
                    List<MarkEntity> object = (List<MarkEntity>) DataCacheUtils.getAppConfig(act).readObject(MARKENTITY_CACHE);
                    if (object != null) {
                        mStockList.addAll(object);
                    }
                    showPopWin();
                    return ;
                }
                List<MarkEntity> list = query.list();
                if (list!=null&&list.size()>0) {
                    List<MarkEntity> temp = new ArrayList<MarkEntity>();
                    int size = list.size();
                    for (int a=0;a<size;a++) {
                        MarkEntity entity = list.get(a);
                        if (("0".equals(entity.getSTID())||"1".equals(entity.getSTID()))&&!DataUtil.isIndex(DataUtil.str2Int(entity.getSTID()),entity.getCODE())) {
                            temp.add(entity);
                        }
                    }
                    mStockList.clear();
                    mStockList.addAll(temp);
                } else {
                    mStockList.clear();
                }
                showPopWin();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mStockCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(mStockCode.getText().toString())&&type == BUY) {
                    mStockList.clear();
                    List<MarkEntity> object = (List<MarkEntity>) DataCacheUtils.getAppConfig(act).readObject(MARKENTITY_CACHE);
                    if (object != null) {
                        mStockList.addAll(object);
                    }
                    showPopWin();
                } else if ("".equals(mStockCode.getText().toString())&&type == SELL) {
                    mStockList.clear();
                    MarkEntityDao mDao = DbManager.getDaoSession(act).getMarkEntityDao();
                    Query<MarkEntity> query = mDao.queryBuilder().where(MarkEntityDao.Properties.NAME.in(mHodlers)).build();
                    List<MarkEntity> object = query.list();
                    if (object != null) {
                        mStockList.addAll(object);
                    }
                    showPopWin();
                }
            }
        });

        mStockCode.requestFocus();
        mPrice.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_CLASS_NUMBER);
        //设置字符过滤
        mPrice.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if(source.equals(".") && dest.toString().length() == 0){
                    return "0.";
                }
                if (dest.toString().equals("0")&&dest.toString().length()==1&&source.equals("0")) {
                    return "";
                }
                if(dest.toString().contains(".")){
                    int index = dest.toString().indexOf(".");
                    int mlength = dest.toString().substring(index).length();
                    if(mlength == 3){
                        return "";
                    }
                }
                return null;
            }
        }});
        mPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i0, int i1, int i2) {
                if (hqResult!=null) {
                    double price = DataUtil.str2Double(hqResult.getNOW());
                    if (!charSequence.toString().equals("")) {
                        double temp = DataUtil.str2Double(charSequence.toString());
                        if (temp != -1) {
                            price = temp;
                        }
                    }
                    reqBSNum();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mPriceD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePriceDP(false,mPrice);
            }
        });
        mPriceP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePriceDP(true,mPrice);
            }
        });
        mCountD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePriceDP(false,mStockCount);
            }
        });
        mCountP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePriceDP(true,mStockCount);
            }
        });

        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStockCode.getText().toString().equals("")) {
                    showToast("请填入股票数量");
                    return;
                }
                if (mStockCount.getText().toString().equals("")||mStockCount.getText().toString().equals("0")) {
                    showToast("请填入股票数量");
                    return;
                }
                if (mPrice.getText().toString().equals("")||mPrice.getText().toString().equals("0")) {
                    showToast("请填入股票价格");
                    return;
                }
                if (mBuyStockDialog == null) {
                    mBuyStockDialog = new AlertDialog.Builder(act, R.style.trade_pop).create();
                }
                View view1 = mInflater.inflate(R.layout.buy_stock_dialog, null);
                TextView mTitle = (TextView) view1.findViewById(R.id.title);
                TextView mName = (TextView) view1.findViewById(R.id.name);
                TextView mCodeTv = (TextView) view1.findViewById(R.id.code);
                TextView mCount = (TextView) view1.findViewById(R.id.count);
                TextView mPriceTv = (TextView) view1.findViewById(R.id.price);
                TextView mPriceTx = (TextView) view1.findViewById(R.id.priceTx);
                TextView mCountTx = (TextView) view1.findViewById(R.id.countTx);
                UIButton mCancel = (UIButton) view1.findViewById(R.id.cancel);
                UIButton mSure = (UIButton) view1.findViewById(R.id.sure);

                if (type == BUY) {
                    mTitle.setText("买入信息确认");
                } else {
                    mTitle.setText("卖出信息确认");
                    mPriceTx.setText("卖出价格：");
                    mCountTx.setText("卖出数量：");
                }
                mName.setText(mStockName.getText());
                mCodeTv.setText(mCode);
                mCount.setText(mStockCount.getText());
                mPriceTv.setText(mPrice.getText().toString());
                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mBuyStockDialog!=null) {
                            mBuyStockDialog.dismiss();
                        }
                    }
                });
                mSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mBuyStockDialog!=null) {
                            mBuyStockDialog.dismiss();
                        }
                        Map<String, String> args = new HashMap<>();
                        args.put("code", mCode);
                        args.put("setcode",mStid);
                        args.put("price",mPrice.getText().toString());
                        args.put("buyOrSell",type+"");
                        args.put("count",mStockCount.getText().toString());
                        TradeMainUtil.getInstance().sendMsg(BuyStockFragment.this,TradeServiceUtil.M_COMMIT_SEARCH,args);
                    }
                });
                mBuyStockDialog.show();
                mBuyStockDialog.setContentView(view1);

            }
        });

        int childCount = mSelectCount.getChildCount();
        for (int i= 0;i<childCount;i++) {
            mSelectCount.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    double rate = DataUtil.str2Double(view.getContentDescription().toString());
                    int v = (int) (mCount * rate/100)*100;
                    mStockCount.setText(v+"");
                }
            });
        }

        mShareEntityCommonAdapter = DataUtil.initShareStocks(mStockListView, mShareEntitys, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareEntity item = (ShareEntity) view.getTag();
                isTextChange = true;
                mStockName.setText(item.getZQMC());
                mStockCode.setText(item.getZQDM());
                mCode = item.getZQDM();
                mStid = DataUtil.getSetCode(0,item.getZQDM())+"";
                mStockCount.setText("100");
                isTextChange = false;
                mPriceHasSet = false;
                refresh();
            }
        },null,null,null);

    }

    @Override
    public void notify(final Object o) {
        if (o instanceof ShareEntity) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ShareEntity item = (ShareEntity) o;
                    isTextChange = true;
                    mStockName.setText(item.getZQMC());
                    mStockCode.setText(item.getZQDM());
                    mCode = item.getZQDM();
                    mStid = DataUtil.getSetCode(0,item.getZQDM())+"";
                    mStockCount.setText("100");
                    isTextChange = false;
                    mPriceHasSet = false;
                    refresh();
                }
            },500);
        }
    }

    private void reqBSNum() {
        Map<String, String> args = new HashMap<>();
        args.put("code", mCode);
        args.put("setcode",mStid);
        args.put("price",mPrice.getText().toString());
        args.put("buyOrSell",type+"");
        TradeMainUtil.getInstance().sendMsg(BuyStockFragment.this, TradeServiceUtil.M_BS_NUM,args);
    }

    private void changePriceDP(boolean plus,EditText et) {
        String priceStr = et.getText().toString();
        if ("".equals(priceStr)) {
            priceStr = "0";
        }
        double price = DataUtil.str2Double(priceStr);
        double dvalue = plus ? 0.01 : -0.01;
        if (et == mStockCount) {
            dvalue *= 10000;
        }
        double newP = price +dvalue;
        if (newP>= 0) {
            if (et == mStockCount) {
                int v = (int) (newP / 100);
                et.setText((v*100) + "");
            } else {
                et.setText(DataUtil.rahToStr(newP, 2) + "");
                reqBSNum();
//                if (TradeMainUtil.getInstance().mFundEntity!=null) {
//                    if (type == BUY) {
//                        mCount = (int) (DataUtil.str2Double(TradeMainUtil.getInstance().mFundEntity.getKYZJ()) / newP);
//                        mCanCount.setText(Html.fromHtml("可买<font color='red'>" + mCount + "</font>股"));
//                    }
//                }
            }
        }
    }

    private void showPopWin() {
        if (mStockList!=null&&mStockList.size()>0) {
            mListView.setVisibility(View.VISIBLE);
        } else {
            mListView.setVisibility(View.GONE);
        }
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = mInflater.inflate(R.layout.buystock_fragment,null);
            bindViews();
            init();

        } else {
            ViewGroup p = (ViewGroup) rootView.getParent();
            if (p != null) {
                p.removeView(rootView);
            }
        }
//        DataUtil.initShareStocks(mInflater,StockPositionFragment.mShareEntityData,mEmpty,mStockCantainer,null);
        return rootView;
    }

    public static BaseFragment getInstance() {
        return new BuyStockFragment();
    }

    @Override
    public void refresh() {
        if (mCode != null) {
            Map<String, String> args = new HashMap<>();
            args.put("code", mCode);
            args.put("stid",mStid);
            TradeMainUtil.getInstance().sendMsg(this, TradeServiceUtil.M_STOCK_INFO, args);
        }
        TradeMainUtil.getInstance().sendMsg(this, TradeServiceUtil.M_HOLD_SEARCH,null);
    }

    @Override
    public void onResult(int requestid, int responeid, Object o) {
        if (o == null) {
            KLog.d("返回对象为null");
            return;
        }
        if (requestid == TradeServiceUtil.M_STOCK_INFO) {
            ArrayList<HQResult> data = (ArrayList<HQResult>) o;
            hqResult = data.get(0);
            final double price = DataUtil.str2Double(hqResult.getNOW());

            if (!mPriceHasSet) {
                mPrice.setText(DataUtil.rahToStr(price, 2));
                mPriceHasSet = true;
            }

            String dt = "跌停 <font color='#17a400'>"+DataUtil.rahToStr(DataUtil.GetTPPrice(hqResult.getNAME(),Integer.parseInt(hqResult.getSTID()),hqResult.getCODE(),2,DataUtil.str2Double(hqResult.getCLOS()),false),2)+"</font>";
            String zt = "涨停 <font color='#e71008'>"+DataUtil.rahToStr(DataUtil.GetTPPrice(hqResult.getNAME(),Integer.parseInt(hqResult.getSTID()),hqResult.getCODE(),2,DataUtil.str2Double(hqResult.getCLOS()),true),2)+"</font>";
            mDprice.setText(Html.fromHtml(dt));
            mZprice.setText(Html.fromHtml(zt));

            ((TextView)mBuyCantainer.getChildAt(0).findViewById(R.id.ptx)).setText("买一");
            ((TextView)mBuyCantainer.getChildAt(0).findViewById(R.id.p)).setText(DataUtil.rahToStr(hqResult.getBYP1(),2,hqResult.getCLOS()));
            ((TextView)mBuyCantainer.getChildAt(0).findViewById(R.id.v)).setText(hqResult.getBYV1());
            ((TextView)mBuyCantainer.getChildAt(1).findViewById(R.id.ptx)).setText("买二");
            ((TextView)mBuyCantainer.getChildAt(1).findViewById(R.id.p)).setText(DataUtil.rahToStr(hqResult.getBYP2(),2,hqResult.getCLOS()));
            ((TextView)mBuyCantainer.getChildAt(1).findViewById(R.id.v)).setText(hqResult.getBYV2());
            ((TextView)mBuyCantainer.getChildAt(2).findViewById(R.id.ptx)).setText("买三");
            ((TextView)mBuyCantainer.getChildAt(2).findViewById(R.id.p)).setText(DataUtil.rahToStr(hqResult.getBYP3(),2,hqResult.getCLOS()));
            ((TextView)mBuyCantainer.getChildAt(2).findViewById(R.id.v)).setText(hqResult.getBYV3());
            ((TextView)mBuyCantainer.getChildAt(3).findViewById(R.id.ptx)).setText("买四");
            ((TextView)mBuyCantainer.getChildAt(3).findViewById(R.id.p)).setText(DataUtil.rahToStr(hqResult.getBYP4(),2,hqResult.getCLOS()));
            ((TextView)mBuyCantainer.getChildAt(3).findViewById(R.id.v)).setText(hqResult.getBYV4());
            ((TextView)mBuyCantainer.getChildAt(4).findViewById(R.id.ptx)).setText("买五");
            ((TextView)mBuyCantainer.getChildAt(4).findViewById(R.id.p)).setText(DataUtil.rahToStr(hqResult.getBYP5(),2,hqResult.getCLOS()));
            ((TextView)mBuyCantainer.getChildAt(4).findViewById(R.id.v)).setText(hqResult.getBYV5());

            ((TextView)mSellCantainer.getChildAt(4).findViewById(R.id.ptx)).setText("卖一");
            ((TextView)mSellCantainer.getChildAt(4).findViewById(R.id.p)).setText(DataUtil.rahToStr(hqResult.getBYP1(),2,hqResult.getCLOS()));
            ((TextView)mSellCantainer.getChildAt(4).findViewById(R.id.v)).setText(hqResult.getBYV1());
            ((TextView)mSellCantainer.getChildAt(3).findViewById(R.id.ptx)).setText("卖二");
            ((TextView)mSellCantainer.getChildAt(3).findViewById(R.id.p)).setText(DataUtil.rahToStr(hqResult.getBYP2(),2,hqResult.getCLOS()));
            ((TextView)mSellCantainer.getChildAt(3).findViewById(R.id.v)).setText(hqResult.getBYV2());
            ((TextView)mSellCantainer.getChildAt(2).findViewById(R.id.ptx)).setText("卖三");
            ((TextView)mSellCantainer.getChildAt(2).findViewById(R.id.p)).setText(DataUtil.rahToStr(hqResult.getBYP3(),2,hqResult.getCLOS()));
            ((TextView)mSellCantainer.getChildAt(2).findViewById(R.id.v)).setText(hqResult.getBYV3());
            ((TextView)mSellCantainer.getChildAt(1).findViewById(R.id.ptx)).setText("卖四");
            ((TextView)mSellCantainer.getChildAt(1).findViewById(R.id.p)).setText(DataUtil.rahToStr(hqResult.getBYP4(),2,hqResult.getCLOS()));
            ((TextView)mSellCantainer.getChildAt(1).findViewById(R.id.v)).setText(hqResult.getBYV4());
            ((TextView)mSellCantainer.getChildAt(0).findViewById(R.id.ptx)).setText("卖五");
            ((TextView)mSellCantainer.getChildAt(0).findViewById(R.id.p)).setText(DataUtil.rahToStr(hqResult.getBYP5(),2,hqResult.getCLOS()));
            ((TextView)mSellCantainer.getChildAt(0).findViewById(R.id.v)).setText(hqResult.getBYV5());

            for (int i=0;i<5;i++) {
                mBuyCantainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView tv = (TextView) view.findViewById(R.id.p);
                        mPrice.setText(tv.getText().toString());
//                        double price = DataUtil.str2Double(tv.getText().toString());
//                        if (TradeMainUtil.getInstance().mFundEntity!=null) {
//                            if (type == BUY) {
//                                mCount = (int) (DataUtil.str2Double(TradeMainUtil.getInstance().mFundEntity.getKYZJ()) / price);
//                                mCanCount.setText(Html.fromHtml("可买<font color='red'>" + mCount + "</font>股"));
//                            }
//                        }
                    }
                });
                mSellCantainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView tv = (TextView) view.findViewById(R.id.p);
                        mPrice.setText(tv.getText().toString());
//                        double price = DataUtil.str2Double(tv.getText().toString());
//                        if (TradeMainUtil.getInstance().mFundEntity!=null) {
//                            if (type == BUY) {
//                                mCount = (int) (DataUtil.str2Double(TradeMainUtil.getInstance().mFundEntity.getKYZJ()) / price);
//                                mCanCount.setText(Html.fromHtml("可买<font color='red'>" + mCount + "</font>股"));
//                            }
//                        }
                    }
                });
            }

        }
        if (requestid == TradeServiceUtil.M_HOLD_SEARCH) {
            ArrayList<ShareEntity> data = (ArrayList<ShareEntity>) o;
            if (data != null && data.size() >0) {
                mEmpty.setVisibility(View.GONE);
                mStockListView.setVisibility(View.VISIBLE);
                int size = data.size();
                mHodlers.clear();
                for (int i = 0; i < size; i++) {
                    mHodlers.add(data.get(i).getZQMC());
                }
                mShareEntitys.clear();
                mShareEntitys.addAll(data);
                mShareEntityCommonAdapter.notifyDataSetChanged();
            } else {
                mEmpty.setVisibility(View.VISIBLE);
                mStockListView.setVisibility(View.GONE);
            }
        }
        if (requestid == TradeServiceUtil.M_COMMIT_SEARCH) {
            String info = (String) o;
            if (info !=null) {
                Map<String,String> map = new Gson().fromJson(info, new TypeToken<Map<String, String>>() {
                }.getType());
                showToast(map.get("ERMT"));
            }
        }
        if (requestid == TradeServiceUtil.M_BS_NUM) {
            String info = (String) o;
            if (info !=null) {
                Map<String,String> map = new Gson().fromJson(info, new TypeToken<Map<String, String>>() {
                }.getType());
                mCount = DataUtil.str2Int(map.get("JYSL"));
                if (type == BUY) {
                    mCanCount.setText(Html.fromHtml("可买<font color='#e71008'>"+DataUtil.val2w(mCount+"")+"</font>股"));
                } else {
                    mCanCount.setText(Html.fromHtml("可卖<font color='#17a400'>"+DataUtil.val2w(mCount+"")+"</font>股"));
                }
            }
        }
}

//    @Override
//    public boolean canBack() {
//        if(mListView.getVisibility() == View.VISIBLE) {
//            mListView.setVisibility(View.GONE);
//            return false;
//        } else {
//            return true;
//        }
//    }
}

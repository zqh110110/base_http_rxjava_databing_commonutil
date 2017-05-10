package com.smcb.simulatedstock;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.util.ArrayMap;
import android.view.View;

import com.smcb.simulatedstock.base.BaseFragment;
import com.smcb.simulatedstock.base.IChangePage;
import com.smcb.simulatedstock.base.IReqData;
import com.smcb.simulatedstock.base.IResult;
import com.smcb.simulatedstock.base.PollingListenner;
import com.smcb.simulatedstock.base.SPUtils;
import com.smcb.simulatedstock.library.log.KLog;
import com.smcb.simulatedstock.utils.Constant;
import com.smcb.simulatedstock.utils.TradeServiceUtil;
import com.smcb.tl.entity.FundEntity;

import java.util.ArrayList;


public class TradeMainUtil implements IReqData {

    private static TradeMainUtil instance;
    private Context context;
    private FundEntity mFundEntity;
    private ArrayMap<Integer,IResult> mCallBackFragments = new ArrayMap<>();
    private ArrayList<PollingListenner> mPollingListenners = new ArrayList<>();
    private IChangePage mChangePageListener;

    private TradeMainUtil(){}

    public static TradeMainUtil getInstance() {
        if (instance == null) {
            instance = new TradeMainUtil();
        }
        return instance;
    }

    public void addPollingListener(PollingListenner listenner) {
        this.mPollingListenners.add(listenner);
    }

    public void removePollingLinstener(PollingListenner listenner) {
        this.mPollingListenners.remove(listenner);
    }

    public IChangePage getmChangePageListener() {
        return mChangePageListener;
    }

    public void setmChangePageListener(IChangePage mChangePageListener) {
        this.mChangePageListener = mChangePageListener;
    }

    private Handler mMessenger = new Handler()
    {
        @Override
        public void handleMessage(Message msgFromServer)
        {
            if (msgFromServer.arg1>=0) {
                IResult object = mCallBackFragments.get(msgFromServer.arg1);

                if (object!=null&&object.isShow()) {
                    object.onResult(msgFromServer.what, 0, msgFromServer.obj);
                    mCallBackFragments.remove(msgFromServer.what);
                }
            } else {

            }
        }
    };
    private Runnable mPollingRun;
    private Handler mhandler = new Handler();
    private View.OnClickListener mGotoHQPageListener;
    /**
     * 开启服务
     * @param flg 是否需要初始化行情服务
     */
    public void create(Context context,boolean flg) {
        this.context = context;
        bindServiceInvoked(flg);
        init();
    }
    public void create(Context context) {
        create(context,false);
    }

    public Context getContext() {
        return context;
    }

    private void init() {
        mPollingRun = new Runnable() {

            @Override
            public void run() {
                int size = mPollingListenners.size();
                for (int i = 0;i<size;i++) {
                    mPollingListenners.get(i).polling();
                }
                mhandler.postDelayed(mPollingRun,Constant.DELAY_TIME);
            }
        };
        mhandler.postDelayed(mPollingRun,Constant.DELAY_TIME);
    }

    @Override
    public void changePage(int index, Object o) {
        if (mChangePageListener != null) {
            mChangePageListener.changePage(index,o);
        }
    }

    @Override
    public void setGoToHQPageListener(View.OnClickListener listener) {
        mGotoHQPageListener = listener;
    }

    @Override
    public View.OnClickListener getGoToPageListener() {
        return mGotoHQPageListener;
    }

    @Override
    public void setAccountAndPossword(Context context,String account, String pwd) {
        SPUtils.getInstance(context).setValue(TradeServiceUtil.TRADE_ACCOUNT_NAME,account);
        SPUtils.getInstance(context).setValue(TradeServiceUtil.TRADE_POSSWORD_PWD,pwd);

        sendMsg(null,TradeServiceUtil.M_LOGIN,null);
    }

    public FundEntity getFundEntity(){
        return mFundEntity;
    }
    public void setFundEntity(FundEntity entity){
        mFundEntity = entity;
    }
    @Override
    public void sendMsg(IResult f,int msgCode,Object args) {
        Message message = new Message();//Message.obtain(null, msgCode);
        message.what = msgCode;
        if (f != null) {
            message.arg1 = f.getId();
            mCallBackFragments.put(message.arg1, f);
        } else {
            message.arg1 = -1;
        }
        message.obj = args;
        TradeServiceUtil.getInstance().sendMessage(mMessenger,message);
    }

    private void bindServiceInvoked(boolean flg)
    {
        TradeServiceUtil.getInstance().start(flg);
    }

    public void destroy()
    {
        TradeServiceUtil.getInstance().stop();
        if (mhandler!=null) {
            mPollingListenners.clear();
            mhandler.removeCallbacks(mPollingRun);
        }

    }

}

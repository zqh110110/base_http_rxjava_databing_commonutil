//package com.smcb.simulatedstock.service;
//
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.net.wifi.WifiInfo;
//import android.net.wifi.WifiManager;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Message;
//import android.os.Messenger;
//import android.os.RemoteException;
//import android.text.TextUtils;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonSyntaxException;
//import com.google.gson.internal.LinkedTreeMap;
//import com.google.gson.reflect.TypeToken;
//import com.sc.utils.SocketUtil;
//import com.smcb.simulatedstock.base.LogUtils;
//import com.smcb.simulatedstock.base.SPUtils;
//import com.smcb.simulatedstock.bean.BillEntity;
//import com.smcb.simulatedstock.bean.CodeEntity;
//import com.smcb.simulatedstock.data.SocUtils;
//import com.smcb.simulatedstock.data.entity.HQResult;
//import com.smcb.simulatedstock.data.entity.MarkEntity;
//import com.smcb.simulatedstock.data.entity.MarkGoup;
//import com.smcb.simulatedstock.library.log.KLog;
//import com.smcb.simulatedstock.utils.Constant;
//import com.smcb.simulatedstock.utils.DataCacheUtils;
//import com.smcb.simulatedstock.utils.DataUtil;
//import com.smcb.simulatedstock.utils.FieldType;
//import com.smcb.simulatedstock.utils.HqUtils;
//import com.smcb.tl.core.EncryptUtil;
//import com.smcb.tl.entity.ClientInfo;
//import com.smcb.tl.entity.ClientReq;
//import com.smcb.tl.entity.FundEntity;
//import com.smcb.tl.entity.HisOrderEntity;
//import com.smcb.tl.entity.HisTradeEntity;
//import com.smcb.tl.entity.HolderEntity;
//import com.smcb.tl.entity.ShareEntity;
//import com.smcb.tl.entity.SocServer;
//import com.smcb.tl.utils.RSAUtils;
//import com.smcb.tl.utils.ReqConst;
//import com.smcb.tl.utils.SocketReqCallBack;
//import com.smcb.tl.utils.ThreadPool;
//import com.smcb.tl.utils.TradeSocUtil;
//import com.smcb.tl.utils.TradeUtils;
//
//import java.io.FileNotFoundException;
//import java.lang.reflect.Type;
//import java.security.NoSuchAlgorithmException;
//import java.security.PublicKey;
//import java.security.spec.InvalidKeySpecException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import static com.smcb.tl.utils.ReqConst.STOCK_BUYSELL;
//
///**
// * Created by Administrator on 2017/3/13.
// */
//
//public class TradeService extends Service {
//
//    public static final String TRADE_ACCOUNT_NAME = "trade_account_name";
//    public static final String TRADE_POSSWORD_PWD = "trade_possword_pwd";
//    /**
//     * 下载证书
//     */
//    public static final int M_GET_CERT = 0;
//    /**
//     * 登录
//     */
//    public static final int M_LOGIN = 1;
//    /**
//     * 查询资金
//     */
//    public static final int M_CAPTIAL_SEARCH = 2;
//    /**
//     * 查询股份
//     */
//    public static final int M_HOLD_SEARCH = 3;
//    /**
//     * 查询股东
//     */
//    public static final int M_HOLDER_SEARCH = 4;
//    /**
//     * 委托下单
//     */
//    public static final int M_COMMIT_SEARCH = 5;//委托下单
//    /**
//     * 最大交易量
//     */
//    public static final int M_BS_NUM = 6;
//    /**
//     * 委托撤单
//     */
//    public static final int M_CANCEL_BILL = 7;
//    /**
//     * 当日成交查询
//     */
//    public static final int M_DEAL_SEARCH = 8;
//    /**
//     * 当日委托查询
//     */
//    public static final int M_ENTRUST_SEARCH = 9;
//    /**
//     * 历史成交查询
//     */
//    public static final int M_DEAL_HIS = 10;
//    /**
//     * 历史委托查询
//     */
//    public static final int M_ENTRUST_HIS = 11;
//    /**
//     * 交割单查询
//     */
//    public static final int M_DO_SEARCH = 12;
//    /**
//     * 股票五档查询
//     */
//    public static final int M_STOCK_INFO = 13;
//    /**
//     * 用户信息缓存信息
//     */
//    private static final String CLIENTINFO_CACHE = "CLIENTINFO_CACHE";
//
//
//    private Messenger mMessenger = new Messenger(new Handler()
//    {
//        @Override
//        public void handleMessage(Message msgfromClient)
//        {
//            Message msgToClient = Message.obtain(msgfromClient);//返回给客户端的消息
//            switch (msgfromClient.what)
//            {
//                //msg 客户端传来的消息
//                case M_LOGIN:
//                    userLogin(msgToClient);
//                    break;
//                case M_CAPTIAL_SEARCH:
//                    reqData(msgToClient,ReqConst.MONEYQUERY_M_NREQ);
//                    break;
//                case M_HOLD_SEARCH:
//                    reqData(msgToClient,ReqConst.HOLDINGQUERY_M_NREQ);
//                    break;
//                case M_HOLDER_SEARCH:
//                    reqData(msgToClient,ReqConst.SHAREHOLDER_QUERY);
//                    break;
//                case M_COMMIT_SEARCH:
//                    Map<String,String> args = (Map<String, String>) msgToClient.obj;
//                    bsStock(msgToClient,args.get("code"), DataUtil.str2Int(args.get("setcode")), args.get("price"), args.get("buyOrSell"), args.get("count"), "3");
//                    break;
//                case M_BS_NUM:
//                    args = (Map<String, String>) msgToClient.obj;
//                    reqMaxNum(msgToClient,args.get("code"), DataUtil.str2Int(args.get("setcode")), args.get("price"), args.get("buyOrSell"));
//                    break;
//                case M_CANCEL_BILL:
//                    args = (Map<String, String>) msgToClient.obj;
//                    reqCancel(msgToClient,args.get("buyOrSell"), args.get("code"));
//                    break;
//                case M_DEAL_SEARCH:
//                    reqDeal(msgToClient,"1000");
//                    break;
//                case M_ENTRUST_SEARCH:
//                    reqEntrust(msgToClient,"1000");
//                    break;
//                case M_DEAL_HIS:
//                    args = (Map<String, String>) msgToClient.obj;
//                    reqDealHis(msgToClient,ReqConst.HISTRADEQUERY_M_NREQ,"0",args.get("startTime"),args.get("endTime"),"200");
//                    break;
//                case M_ENTRUST_HIS:
//                    args = (Map<String, String>) msgToClient.obj;
//                    reqDealHis(msgToClient,ReqConst.HISORDERQUERY_M_NREQ,"0",args.get("startTime"),args.get("endTime"),"200");
//                    break;
//                case M_DO_SEARCH:
//                    args = (Map<String, String>) msgToClient.obj;
//                    reqDelivery(msgToClient,args.get("startTime"),args.get("endTime"),"200");
//                    break;
//                case M_STOCK_INFO:
//                    reqStockInfo(msgToClient);
//                    break;
//
//            }
//
//            super.handleMessage(msgfromClient);
//        }
//    });
//
//    private Gson gson = new Gson();
//    private static ClientInfo clientInfo;
//    private ArrayList<HolderEntity> holderEntities;
//    private ArrayList<ShareEntity> holdEntities;
//    private String userName;
//    private String userPwd;
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        initHQ();
//        initSocket();
//        return mMessenger.getBinder();
//    }
//
//    private void setAccoutPwd() {
//        userName = SPUtils.getInstance(this).getStringValue(TRADE_ACCOUNT_NAME);
//        userPwd = SPUtils.getInstance(this).getStringValue(TradeService.TRADE_POSSWORD_PWD);
//        if (null == userName || "".equals(userName)) {
//            KLog.d("==================账号密码未设置==================================");
//        }
//    }
//
//    @Override
//    public boolean onUnbind(Intent intent) {
//        SocketUtil.getInstance().stopSocket();
//        TradeSocUtil.getInstance().stopSocket();
//        //清除账号密码
//        SPUtils.getInstance(getApplicationContext()).setValue(TradeService.TRADE_ACCOUNT_NAME,"");
//        SPUtils.getInstance(getApplicationContext()).setValue(TradeService.TRADE_POSSWORD_PWD,"");
//
//        KLog.d("tradeservice onUnbind !!");
//        return super.onUnbind(intent);
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        return START_STICKY;
//    }
//
//    @Override
//    public void onDestroy() {
//        SocketUtil.getInstance().stopSocket();
//        TradeSocUtil.getInstance().stopSocket();
//        KLog.d("tradeservice onDestroy !!");
//        super.onDestroy();
//    }
//
//    /**
//     * 拉取码表
//     */
//    private void initHQ() {
////        com.sc.entity.SocServer entity = new com.sc.entity.SocServer();
////        entity.setIp(Constant.HQ_SERVER_IP);
////        entity.setPort(Constant.HQ_SERVER_PORT);
////        entity.setName("代理1");
////        List<com.sc.entity.SocServer> serverList = new ArrayList<>();
////        serverList.add(entity);
////        SocketUtil.getInstance().runSocket(serverList, new com.sc.utils.SocketReqCallBack() {
////            @Override
////            public void onSuccess(String info) {
////                SocUtils.getInstance().reqTab(getApplicationContext());
////            }
////        });
//    }
//
//    private void initSocket() {
//        SocServer entity = new SocServer();
//        entity.setIp(Constant.SERVER_IP);
//        entity.setPort(Constant.SERVER_PORT);
//        entity.setName("交易服务器");
//        List<SocServer> serverList = new ArrayList<>();
//        serverList.add(entity);
//        TradeSocUtil.getInstance().runSocket(serverList, new SocketReqCallBack() {
//            @Override
//            public void onSuccess(final String info) {
//                initPubKey();
//                KLog.d(info);
//            }
//        });
//    }
//
//    private void initPubKey() {
//        String publicKey = EncryptUtil.readKeyFile(this, ReqConst.TRADE_PUBLIC_KEY);
//        if (TextUtils.isEmpty(publicKey)) {
//            ThreadPool.execute(new Runnable() {
//                @Override
//                public void run() {
//                    TradeSocUtil.getInstance().reqCert(new SocketReqCallBack() {
//                        @Override
//                        public void onSuccess(String resultstr) {
//                            KLog.d("cert value = "+resultstr);
//                            String certstr = resultstr.substring(resultstr.indexOf("-----BEGIN CERTIFICATE-----"), resultstr.length());
//                            EncryptUtil.writeKey(certstr.getBytes(), getApplicationContext(), ReqConst.TRADE_PUBLIC_KEY);
//                            initPubKey();
//                        }
//                    });
//                }
//            });
//        } else {
//            try {
//                PublicKey pKey = RSAUtils.getPublicKey(this.openFileInput(ReqConst.TRADE_PUBLIC_KEY));
//                userLogin(null);
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            } catch (InvalidKeySpecException e) {
//                e.printStackTrace();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    /**
//     * @function 用户登录校验
//     * @param msgToClient
//     */
//    private void userLogin(final Message msgToClient) {
//        ClientReq req = buildREQ();
//        setAccoutPwd();
//        req.setKHH(userName);
//        req.setJYMM(userPwd);
//        req.setZHLB("1");
//        req.setCVER("3");
//        req.setGVER("1");
//        req.setHDEX("");
//        req.setSECK(TradeUtils.AES_PWD);
//        final String jsonStr = gson.toJson(req);
//        ThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                TradeSocUtil.getInstance().reqData(ReqConst.CLIENT_VALIDATE, jsonStr, new SocketReqCallBack() {
//                    @Override
//                    public void onSuccess(final String info) {
//                        try {
//                            LinkedTreeMap<String, Object> map = gson.fromJson(info, new TypeToken<LinkedTreeMap<String, Object>>() {
//                            }.getType());
//                            ArrayList<ClientInfo> clientInfos = gson.fromJson(gson.toJson(map.get("list")), new TypeToken<ArrayList<ClientInfo>>() {
//                            }.getType());
//                            ClientInfo user = clientInfos.get(0);
//                            if (user != null) {
//                                clientInfo = user;
//                                DataCacheUtils.getAppConfig(getApplicationContext()).saveObject(clientInfo,CLIENTINFO_CACHE);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        if (msgToClient != null) {
//                            sendInfo(msgToClient,info);
//                        } else {
//                            KLog.json(gson.toJson(clientInfo));
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * 历史成交/委托
//     * @param s 起始位置
//     * @param s1 开始时间
//     * @param s2 结束时间
//     * @param s3 请求数量
//     */
//    private void reqDealHis(final Message msgToClient, final int reqID, String s, String s1, String s2, String s3) {
//        if (null == clientInfo) {
//            KLog.d("请选登陆！");
//            Message msg = Message.obtain(null, M_LOGIN);
//            msg.arg1 = -1;
//            userLogin(msg);
//            return;
//        }
//        ClientReq req = buildREQ();
//        req.setZJZH(clientInfo.getZJZH());
//        req.setJYMM(userPwd);
//        req.setQLEN(s3);
//        req.setBEGD(s1);
//        req.setENDD(s2);
//        req.setMPS(s);
//        final String jsonStr = gson.toJson(req);
//        ThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                TradeSocUtil.getInstance().reqData(reqID, jsonStr, new SocketReqCallBack() {
//                    @Override
//                    public void onSuccess(final String info) {
//
//                        LinkedTreeMap<String, Object> map = gson.fromJson(info, new TypeToken<LinkedTreeMap<String, Object>>() {
//                        }.getType());
//                        if (map !=null) {
//                            if (map.get("list")!=null) {
//                                sendInfo(msgToClient, gson.toJson(map.get("list")));
//                            }else {
//                                if ("0".equals(map.get("ERMS"))) {
//                                    sendInfo(msgToClient, "[]");
//                                } else {
//                                    sendInfo(msgToClient, map.get("ERMT"));
//                                }
//                            }
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * 对账单
//     * @param s 开始时间
//     * @param s1 结束时间
//     * @param s2 请求数量
//     */
//    private void reqDelivery(final Message msgToClient, String s, String s1, String s2) {
//        if (null == clientInfo) {
//            KLog.d("请选登陆！");
//            Message msg = Message.obtain(null, M_LOGIN);
//            msg.arg1 = -1;
//            userLogin(msg);
//            return;
//        }
//        ClientReq req = buildREQ();
//        req.setZJZH(clientInfo.getZJZH());
//        req.setJYMM(userPwd);
//        req.setQLEN(s2);
//        req.setBEGD(s);
//        req.setENDD(s1);
//        req.setBZ(ReqConst.BZ);
//        final String jsonStr = gson.toJson(req);
//        ThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                TradeSocUtil.getInstance().reqData(ReqConst.BILLINGINQUIRIESQUERY_M_NREQ, jsonStr, new SocketReqCallBack() {
//                    @Override
//                    public void onSuccess(final String info) {
//                        LinkedTreeMap<String, Object> map = gson.fromJson(info, new TypeToken<LinkedTreeMap<String, Object>>() {
//                        }.getType());
//                        if (map !=null) {
//                            if (map.get("list")!=null) {
//                                sendInfo(msgToClient, gson.toJson(map.get("list")));
//                            }else {
//                                sendInfo(msgToClient, "[]");
//                            }
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * 当日委托
//     *
//     * @param s 数量
//     */
//    private void reqEntrust(final Message msgToClient, String s) {
//        if (null == clientInfo) {
//            KLog.d("请选登陆！");
//            Message msg = Message.obtain(null, M_LOGIN);
//            msg.arg1 = -1;
//            userLogin(msg);
//            return;
//        }
//        ClientReq req = buildREQ();
//        req.setZJZH(clientInfo.getZJZH());
//        req.setJYMM(userPwd);
//        req.setQLEN(s);
//        req.setCZQR("0");
//        final String jsonStr = gson.toJson(req);
//        ThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                TradeSocUtil.getInstance().reqData(ReqConst.DAY_ORDER_QUERY, jsonStr, new SocketReqCallBack() {
//                    @Override
//                    public void onSuccess(final String info) {
//                        LinkedTreeMap<String, Object> map = gson.fromJson(info, new TypeToken<LinkedTreeMap<String, Object>>() {
//                        }.getType());
//                        if (map !=null) {
//                            if (map.get("list")!=null) {
//                                sendInfo(msgToClient, gson.toJson(map.get("list")));
//                            }else {
//                                sendInfo(msgToClient, "[]");
//                            }
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * 当日成交
//     *
//     * @param s 数量
//     */
//    private void reqDeal(final Message msgToClient, String s) {
//        if (null == clientInfo) {
//            KLog.d("请选登陆！");
//            Message msg = Message.obtain(null, M_LOGIN);
//            msg.arg1 = -1;
//            userLogin(msg);
//            return;
//        }
//        ClientReq req = buildREQ();
//        req.setZJZH(clientInfo.getZJZH());
//        req.setJYMM(userPwd);
//        req.setQLEN(s);
//        final String jsonStr = gson.toJson(req);
//        ThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                TradeSocUtil.getInstance().reqData(ReqConst.DAY_TRADE_QUERY, jsonStr, new SocketReqCallBack() {
//                    @Override
//                    public void onSuccess(final String info) {
//
//                        LinkedTreeMap<String, Object> map = gson.fromJson(info, new TypeToken<LinkedTreeMap<String, Object>>() {
//                        }.getType());
//                        if (map !=null) {
//                            if (map.get("list")!=null) {
//                                sendInfo(msgToClient, gson.toJson(map.get("list")));
//                            } else {
//                                sendInfo(msgToClient, "[]");
//                            }
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * 请求撤单
//     *
//     * @param s  bs标记  1，买  2，卖
//     * @param s1 委托编号
//     */
//    private void reqCancel(final Message msgToClient, String s, String s1) {
//        if (null == clientInfo) {
//            KLog.d("请选登陆！");
//            Message msg = Message.obtain(null, M_LOGIN);
//            msg.arg1 = -1;
//            userLogin(msg);
//            return;
//        }
//        ClientReq req = buildREQ();
//        req.setZJZH(clientInfo.getZJZH());
//        req.setJYMM(userPwd);
//        req.setZQDM(s);
//        req.setWTBH(s1);
//        final String jsonStr = gson.toJson(req);
//        ThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                TradeSocUtil.getInstance().reqData(ReqConst.STOCK_BUYSELL_CANCEL, jsonStr, new SocketReqCallBack() {
//                    @Override
//                    public void onSuccess(final String info) {
//
//                        sendInfo(msgToClient,info);
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * 取最大交易量
//     *
//     * @param s  股票代码
//     * @param i  市场setcdoe
//     * @param s1 价格
//     * @param s2 bs标记  1，买  2，卖
//     */
//    private void reqMaxNum(final Message msgToClient, String s, int i, String s1, String s2) {
//        if (null == clientInfo) {
//            KLog.d("请选登陆！");
//            Message msg = Message.obtain(null, M_LOGIN);
//            msg.arg1 = -1;
//            userLogin(msg);
//            return;
//        }
//        HolderEntity holder = getHoler(holderEntities, i, s);
//        ClientReq req = buildREQ();
//        req.setZJZH(clientInfo.getZJZH());
//        req.setJYMM(userPwd);
//        req.setGDDM(holder.getGDDM());
//        req.setJYSM(holder.getJYSM());
//        req.setZQDM(s);
//        req.setWTJG(s1);
//        req.setBSFG(s2);
//        final String jsonStr = gson.toJson(req);
//        ThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                TradeSocUtil.getInstance().reqData(ReqConst.TRADEMAXNM_M_NREQ, jsonStr, new SocketReqCallBack() {
//                    @Override
//                    public void onSuccess(final String info) {
//
//                        sendInfo(msgToClient,info);
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * \
//     *
//     * @param s  股票代码
//     * @param i  市场setcdoe
//     * @param s1 价格
//     * @param s2 bs标记  1，买  2，卖
//     * @param s3 数量
//     * @param s4 委托属性   "3"新股申购
//     */
//    private void bsStock(final Message msgToClient,String s, int i, String s1, String s2, String s3, String s4) {
//        if (null == clientInfo) {
//            KLog.d("请选登陆！");
//            Message msg = Message.obtain(null, M_LOGIN);
//            msg.arg1 = -1;
//            userLogin(msg);
//            return;
//        }
//        HolderEntity holder = getHoler(holderEntities, i, s);
//        ClientReq req = buildREQ();
//        req.setZJZH(clientInfo.getZJZH());
//        req.setJYMM(userPwd);
//        req.setZQDM(s);
//        req.setWTJG(s1);
//        req.setBSFG(s2);
//        req.setZHLB("1");
//        req.setGDDM(holder.getGDDM());
//        req.setJYSM(holder.getJYSM());
//        req.setJYDW(ReqConst.WJYD);
//        req.setWTSL(s3);
//        req.setBZ(ReqConst.BZ);
//        final String jsonStr = gson.toJson(req);
//        ThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                TradeSocUtil.getInstance().reqData(STOCK_BUYSELL, jsonStr, new SocketReqCallBack() {
//                    @Override
//                    public void onSuccess(final String info) {
//
//                        sendInfo(msgToClient,info);
//                    }
//                });
//            }
//        });
//    }
//
//    private void reqData(final Message msgToClient,final int reqid) {
//        if (null == clientInfo) {
//            KLog.d("请选登陆！");
//            Message msg = Message.obtain(null, M_LOGIN);
//            msg.arg1 = -1;
//            userLogin(msg);
//            return;
//        }
//        ClientReq req = buildREQ();
//        req.setZJZH(clientInfo.getZJZH());
//        req.setJYMM(userPwd);
//        req.setBZ(ReqConst.BZ);
//        final String jsonStr = gson.toJson(req);
//        ThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                TradeSocUtil.getInstance().reqData(reqid, jsonStr, new SocketReqCallBack() {
//                    @Override
//                    public void onSuccess(final String info) {
//                        switch (reqid) {
//                            case ReqConst.SHAREHOLDER_QUERY:
//                                //股东信息缓存一下
//                                LinkedTreeMap<String, Object> map = gson.fromJson(info, new TypeToken<LinkedTreeMap<String, Object>>() {
//                                }.getType());
//                                holderEntities = gson.fromJson(gson.toJson(map.get("list")), new TypeToken<ArrayList<HolderEntity>>() {
//                                }.getType());
//                                sendInfo(msgToClient,holderEntities);
//                                break;
//                            case ReqConst.HOLDINGQUERY_M_NREQ:
//                                map = gson.fromJson(info, new TypeToken<LinkedTreeMap<String, Object>>() {
//                                }.getType());
//                                holdEntities = gson.fromJson(gson.toJson(map.get("list")), new TypeToken<ArrayList<ShareEntity>>() {
//                                }.getType());
//                                sendInfo(msgToClient,holdEntities);
//                                break;
//                            case ReqConst.MONEYQUERY_M_NREQ :
//                                sendInfo(msgToClient,info);
//                                break;
//                        }
//
//
//                    }
//                });
//            }
//        });
//    }
//
//    private ClientReq buildREQ() {
//        ClientReq req = new ClientReq();
//        req.setCLTP(ReqConst.CLTP);
//        req.setMAC(getLocalMac(this));
//        req.setDISK(android.os.Build.MODEL);
//        req.setYYB(ReqConst.YYB);
//        if (null != clientInfo && !TextUtils.isEmpty(clientInfo.getSEID()))
//            req.setSEID(clientInfo.getSEID());
//        return req;
//    }
//
//    public static String getLocalMac(Context context) {
//        try {
//            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//            WifiInfo info = wifi.getConnectionInfo();
//            return info.getMacAddress();
//        } catch (Exception e) {
//            return "";
//        }
//    }
//
//    /**
//     * 股东查询
//     *
//     * @param holders
//     * @param codestr
//     * @return
//     */
//    public static HolderEntity getHoler(ArrayList<HolderEntity> holders, int setcode, String codestr) {
//        HolderEntity bean = new HolderEntity();
//        if (null == holders || null == codestr || "".equals(codestr))
//            return bean;
//        for (HolderEntity entity : holders) {
//            int hsetcode = 0;
//            int zhlb = 0;//0 普通 1基金
//            int zfbz = 0;//0副 1主
//            try {
//                hsetcode = Integer.parseInt(entity.getJYSM());
//                zhlb = Integer.parseInt(entity.getZHLB());
//                zfbz = Integer.parseInt(entity.getZFBZ());
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//            if (hsetcode == 1 && setcode == 1 && zhlb == 0 && zfbz == 1) {
//                // 上海
//                bean = entity;
//                break;
//            }
//            if (hsetcode == 2 && setcode == 0 && zhlb == 0 && zfbz == 1) {
//                // 深圳
//                bean = entity;
//                break;
//            }
//        }
//        return bean;
//    }
//
//    private void reqStockInfo(final Message msgToClient) {
//        Map<String,String> args = (Map<String, String>) msgToClient.obj;
//        List<CodeEntity> list = new ArrayList<>();
//        list.add(new CodeEntity(args.get("code"),args.get("stid")));
//        String[] params = {FieldType.enFieldName, FieldType.enFieldOpen, FieldType.enFieldHigh, FieldType.enFieldLow, FieldType.enFieldPrice, FieldType.enFieldVolume,
//                FieldType.enFieldAmount, FieldType.enFieldSetCode, FieldType.enFieldClose, FieldType.enFieldBuyPrc1, FieldType.enFieldBuyPrc2, FieldType.enFieldBuyPrc3, FieldType.enFieldBuyPrc4, FieldType.enFieldBuyPrc5,
//                FieldType.enFieldBuyVol1, FieldType.enFieldBuyVol2, FieldType.enFieldBuyVol3, FieldType.enFieldBuyVol4, FieldType.enFieldBuyVol5
//                , FieldType.enFieldSellPrc1, FieldType.enFieldSellPrc2, FieldType.enFieldSellPrc3, FieldType.enFieldSellPrc4, FieldType.enFieldSellPrc5
//                , FieldType.enFieldSellVol1, FieldType.enFieldSellVol2, FieldType.enFieldSellVol3, FieldType.enFieldSellVol4, FieldType.enFieldSellVol5, FieldType.enFieldTime, FieldType.enFieldZGB, FieldType.enFieldLTGB,FieldType.enFieldDigit,FieldType.enFieldSetCode};
//        HqUtils.reqCOMB(list, params, new com.sc.utils.SocketReqCallBack() {
//            @Override
//            public void onSuccess(String info) {
//                try {
//                    LinkedTreeMap<String, Object> map = gson.fromJson(info, new TypeToken<LinkedTreeMap<String, Object>>() {
//                    }.getType());
//                    if (Double.parseDouble(map.get("ERMS").toString()) != 0) {
//                        KLog.d("股票信息请求失败！");
//                        return;
//                    }
//                    ArrayList<HQResult> markGoups = gson.fromJson(gson.toJson(map.get("LIST")), new TypeToken<ArrayList<HQResult>>() {
//                    }.getType());
//                    sendInfo(msgToClient,markGoups);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    /**
//     * 回传信息
//     * @param msgToClient
//     * @param info
//     */
//    private void sendInfo(Message msgToClient,Object info) {
//        if (msgToClient == null) {
//            return ;
//        }
//
//        Object cls = null;
//        if (info instanceof String) {
//
//            KLog.json((String) info);
//
//            switch (msgToClient.what) {
//                //发送客户端的消息
//                case M_LOGIN:
//                    if (clientInfo == null) {
//                        if (((String) info).contains("-35")) {
//                            clientInfo = (ClientInfo) DataCacheUtils.getAppConfig(getApplicationContext()).readObject(CLIENTINFO_CACHE);
//                            KLog.d("use clientinfo cache ! "+clientInfo);
//                        }
//                    }
//                    break;
//                case M_CAPTIAL_SEARCH:
//                    cls = FundEntity.class;
//                    break;
//                case M_HOLD_SEARCH:
//
//                    break;
//                case M_HOLDER_SEARCH:
//
//                    break;
//                case M_COMMIT_SEARCH:
//
//                    break;
//                case M_BS_NUM:
//
//                    break;
//                case M_CANCEL_BILL:
//
//                    break;
//                case M_DEAL_SEARCH:
//                    cls = new TypeToken<ArrayList<HisTradeEntity>>(){}.getType();
//                    break;
//                case M_ENTRUST_SEARCH:
//                    cls = new TypeToken<ArrayList<HisOrderEntity>>(){}.getType();
//                    break;
//                case M_DEAL_HIS:
//                    cls = new TypeToken<ArrayList<HisTradeEntity>>(){}.getType();
//                    break;
//                case M_ENTRUST_HIS:
//                    cls = new TypeToken<ArrayList<HisOrderEntity>>(){}.getType();
//                    break;
//                case M_DO_SEARCH:
//                    cls = new TypeToken<ArrayList<com.smcb.tl.entity.BillEntity>>(){}.getType();
//                    break;
//
//            }
//        } else {
//            KLog.json(gson.toJson(info));
//        }
//
//        try {
//            if (cls != null) {
//                try {
//                    if (cls instanceof Class) {
//                        info = gson.fromJson((String) info, (Class) cls);
//                    } else {
//                        info = gson.fromJson((String) info, (Type) cls);
//                    }
//                } catch (JsonSyntaxException e) {
//                    e.printStackTrace();
//                }
//            }
//            msgToClient.obj = info;
//            msgToClient.replyTo.send(msgToClient);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//}

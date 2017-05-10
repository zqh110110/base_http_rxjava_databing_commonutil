package com.smcb.simulatedstock.utils;

import com.google.gson.Gson;
import com.sc.utils.ThreadPool;
import com.sc.entity.ReqEntity;
import com.sc.utils.ReqConst;
import com.sc.utils.SocketReqCallBack;
import com.sc.utils.SocketUtil;
import com.smcb.simulatedstock.bean.CodeEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/7.
 */

public class HqUtils {
    public static void reqFST(final ReqEntity reqEntity, final SocketReqCallBack callback) {
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Map map = new HashMap<>();
                map.put("CODE", reqEntity.getCode());
                map.put("STID", reqEntity.getSetcode());
                SocketUtil.getInstance().reqData(ReqConst.LINE_CHART, new Gson().toJson(map), callback);
            }
        });
    }

    public static void reqFXT(final ReqEntity reqEntity, final SocketReqCallBack callback) {
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Map map = new HashMap<>();
                map.put("CODE", reqEntity.getCode());
                map.put("STID", reqEntity.getSetcode());
                map.put("NUM", reqEntity.getWantNum());
                map.put("TYPE", reqEntity.getKtype());
                map.put("STRT", reqEntity.getStart());
                map.put("FQ", reqEntity.getFq());
                SocketUtil.getInstance().reqData(ReqConst.KLINE_CHART, new Gson().toJson(map), callback);
            }
        });
    }

    public static void reqTICK(final ReqEntity reqEntity, final SocketReqCallBack callback) {
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Map map = new HashMap<>();
                map.put("CODE", reqEntity.getCode());
                map.put("STID", reqEntity.getSetcode());
                map.put("NUM", reqEntity.getWantNum());
                map.put("STRT", reqEntity.getStart());
                SocketUtil.getInstance().reqData(ReqConst.TICK_REQ, new Gson().toJson(map), callback);
            }
        });
    }

    public static void reqCOMB(final List<CodeEntity> codes, final String[] wantIDS, final SocketReqCallBack callback) {
        if (codes == null) return;
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Map map = new HashMap<>();
                map.put("WANT", codes.size());
                map.put("CDHS", codes);
                map.put("FLDN", wantIDS.length);
                map.put("FLDS", wantIDS);
                SocketUtil.getInstance().reqData(ReqConst.COMBHQ, new Gson().toJson(map), callback);
            }
        });
    }

    public static void reqHQLB(final ReqEntity reqEntity, final SocketReqCallBack callback) {
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Map map = new HashMap<>();
                map.put("CONT", reqEntity.getWantNum());
                map.put("FILD", reqEntity.getFiled());
                map.put("STID", String.valueOf(reqEntity.getSetdomain()));
                map.put("STRT", reqEntity.getStart());
                map.put("SORT", reqEntity.getSortType());
                map.put("PUSH", "0");
                SocketUtil.getInstance().reqData(ReqConst.HQ_LIST_REQ, new Gson().toJson(map), callback);
            }
        });
    }
}

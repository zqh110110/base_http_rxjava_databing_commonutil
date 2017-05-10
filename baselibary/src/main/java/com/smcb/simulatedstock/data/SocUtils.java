package com.smcb.simulatedstock.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.sc.utils.ReqConst;
import com.sc.utils.SocketReqCallBack;
import com.sc.utils.SocketUtil;
import com.smcb.simulatedstock.base.DateFormatUtils;
import com.smcb.simulatedstock.base.LogUtils;
import com.smcb.simulatedstock.base.PinYin4j;
import com.smcb.simulatedstock.base.SPUtils;
import com.smcb.simulatedstock.data.entity.MarkEntity;
import com.smcb.simulatedstock.data.entity.MarkGoup;
import com.smcb.simulatedstock.data.gen.MarkEntityDao;
import com.smcb.simulatedstock.utils.DataUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/7.
 */
public class SocUtils {
    private static SocUtils ourInstance = null;
    private Gson gson = new Gson();

    public static SocUtils getInstance() {
        if (null == ourInstance) {
            ourInstance = new SocUtils();
        }
        return ourInstance;
    }

    private final String MARK_TAG = "MARK_TAG";

    /**
     * 码表
     */
    public void reqTab(Context context) {
        String markTag = SPUtils.getInstance(context).getStringValue(MARK_TAG);
        String nowDate = DateFormatUtils.date2Str(new Date(), DateFormatUtils.FORMAT_DATE);
        final MarkEntityDao mDao = DbManager.getDaoSession(context).getMarkEntityDao();
        try {
            if (mDao.count() > 0 && markTag.equals(nowDate)) return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        SPUtils.getInstance(context).setValue(MARK_TAG, nowDate);
        Map reqMap = new HashMap<>();
        reqMap.put("MARK", "-1");
        SocketUtil.getInstance().reqData(ReqConst.MARKET_REQ, gson.toJson(reqMap), new SocketReqCallBack() {
            @Override
            public void onSuccess(String info) {
                LogUtils.d("MARK jsonstr=" + info);
                try {
                    LinkedTreeMap<String, Object> map = gson.fromJson(info, new TypeToken<LinkedTreeMap<String, Object>>() {
                    }.getType());
                    if (Double.parseDouble(map.get("ERMS").toString()) != 0) {
                        LogUtils.d("码表同步失败！");
                        return;
                    }
                    ArrayList<MarkGoup> markGoups = gson.fromJson(gson.toJson(map.get("list")), new TypeToken<ArrayList<MarkGoup>>() {
                    }.getType());
                    try {
                        mDao.deleteAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < markGoups.size(); i++) {
                        MarkGoup groupEntity = markGoups.get(i);
                        String mark = groupEntity.getMARK();
                        List<MarkEntity> markEntities = groupEntity.getList();
                        saveStocks(mDao, markEntities, mark);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveStocks(MarkEntityDao mDao, List<MarkEntity> markEntities, String mark) {
        List<MarkEntity> saveList = new ArrayList<>();
        for (MarkEntity entity : markEntities) {
            String name = DataUtil.trimAsicc(entity.getNAME());
            entity.setNAME(name);
            String pyTag = PinYin4j.converterToFirstSpell(name);
            String pyAll = PinYin4j.converterToSpell(name);
            entity.setSTID(mark);
            entity.setPYTAG(pyTag);
            entity.setPYALL(pyAll);
            boolean isab = DataUtil.isAbStock(Integer.parseInt(mark), entity.getCODE());
            if (isab) {
                saveList.add(entity);
            }
//            LogUtils.d("save stock==" + name + "-" + pyTag + "-" + pyAll);
//            mDao.insertOrReplace(entity);
        }
        mDao.insertOrReplaceInTx(saveList);
        LogUtils.d("save stock over....");
    }
}

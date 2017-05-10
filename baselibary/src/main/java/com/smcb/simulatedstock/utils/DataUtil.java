package com.smcb.simulatedstock.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.smcb.simulatedstock.R;
import com.smcb.simulatedstock.bean.BillEntity;
import com.smcb.simulatedstock.library.commonadapter.CommonAdapter;
import com.smcb.simulatedstock.library.commonadapter.item.AdapterItem;
import com.smcb.tl.entity.HisOrderEntity;
import com.smcb.tl.entity.HisTradeEntity;
import com.smcb.tl.entity.ShareEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.SECOND;

public class DataUtil {
    private static int Const_Count = 10000;

    public DataUtil() {
    }

    public static float roundAndHalf(float var0) {
        BigDecimal var1 = new BigDecimal((double) var0);
        var0 = var1.setScale(2, 4).floatValue();
        return var0;
    }

    public static String rahToStr(String var0, int var2) {
        return rahToStr(str2Double(var0), var2);
    }

    public static Spanned rahToStr(String var0, int var2, String campare) {
        double v = str2Double(var0);
        double v1 = str2Double(campare);
        if (v == v1) {
            return Html.fromHtml("<font color='#333333'>" + rahToStr(str2Double(var0), var2) + "</font>");
        } else if (v > v1) {
            return Html.fromHtml("<font color='#e71008'>" + rahToStr(str2Double(var0), var2) + "</font>");
        } else {
            return Html.fromHtml("<font color='#17a400'>" + rahToStr(str2Double(var0), var2) + "</font>");
        }
    }

    public static String rahToStr(double var0, int var2) {
        if (!Double.isNaN(var0) && var0 != -1.0D / 0.0 && var0 != 1.0D / 0.0) {
            BigDecimal var3 = new BigDecimal(var0);
            var0 = (double) var3.setScale(var2, 4).floatValue();
            if (var2 == 2) {
                DecimalFormat var6 = new DecimalFormat("0.00");
                return var6.format(var0);
            } else if (var2 == 0) {
                return String.valueOf((int) var0);
            } else {
                StringBuffer var4 = new StringBuffer("0.");

                for (int var5 = 0; var5 < var2; ++var5) {
                    var4.append("0");
                }

                DecimalFormat var7 = new DecimalFormat(var4.toString());
                return var7.format(var0);
            }
        } else {
            return "";
        }
    }

    public static String rahToStr2(double var0, int var2) {
        if (!Double.isNaN(var0) && var0 != -1.0D / 0.0 && var0 != 1.0D / 0.0) {
            BigDecimal var3 = new BigDecimal(var0);
            var0 = var3.setScale(var2, 4).doubleValue();
            if (var2 == 2) {
                DecimalFormat var6 = new DecimalFormat("0.00");
                return var6.format(var0);
            } else if (var2 == 0) {
                return String.valueOf((int) var0);
            } else {
                StringBuffer var4 = new StringBuffer("0.");

                for (int var5 = 0; var5 < var2; ++var5) {
                    var4.append("0");
                }

                DecimalFormat var7 = new DecimalFormat(var4.toString());
                return var7.format(var0);
            }
        } else {
            return "";
        }
    }

    public static String rahToStr(float var0) {
        if (!Float.isNaN(var0) && var0 != -1.0F / 0.0 && var0 != 1.0F / 0.0) {
            BigDecimal var1 = new BigDecimal((double) var0);
            var0 = var1.setScale(2, 4).floatValue();
            DecimalFormat var2 = new DecimalFormat("0.00");
            String var3 = var2.format((double) var0);
            if (var3.endsWith(".00")) {
                var3 = var3.substring(0, var3.indexOf(".00"));
            }

            return var3;
        } else {
            return "";
        }
    }

    public static String rahToStr(double var0) {
        try {
            if (!Double.isNaN(var0) && var0 != -1.0D / 0.0 && var0 != 1.0D / 0.0) {
                BigDecimal var2 = new BigDecimal(var0);
                var0 = var2.setScale(2, 4).doubleValue();
                DecimalFormat var3 = new DecimalFormat("0.00");
                String var4 = var3.format(var0);
                if (var4.endsWith(".00")) {
                    var4 = var4.substring(0, var4.indexOf(".00"));
                }

                return var4;
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return "";
    }

    public static double roundAndHalf(double var0, int var2) {
        if (var0 != 0.0D) {
            BigDecimal var3 = new BigDecimal(var0);
            var0 = var3.setScale(var2, 4).doubleValue();
        }

        return var0;
    }

    public static String roundAndHalfToStr(double var0) {
        NumberFormat var2 = NumberFormat.getInstance();
        var2.setGroupingUsed(false);
        String var3 = var2.format(var0);
        return var3;
    }

    public static float doubleTofloat(double var0, int var2) {
        BigDecimal var3 = new BigDecimal(var0);
        float var4 = var3.setScale(var2, 4).floatValue();
        return var4;
    }

    public static synchronized int getConstCount() {
        if (Const_Count >= 20000) {
            Const_Count = 10000;
        }

        return Const_Count++;
    }

    public static int getTimeNum(String[][] var0) {
        int var1 = 0;
        SimpleDateFormat var2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (int var3 = 0; var3 < var0.length; ++var3) {
            String[] var4 = var0[var3];
            String var5 = var4[0];
            String var6 = var4[1];

            try {
                Date var7 = var2.parse(var5);
                Date var8 = var2.parse(var6);
                int var9 = (int) ((var8.getTime() - var7.getTime()) / 60000L);
                var1 += var9;
            } catch (ParseException var10) {
                var10.printStackTrace();
            }
        }

        return var1;
    }

    public static int getArrayTimeNum(String[] var0) {
        int var1 = 0;
        SimpleDateFormat var2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String var3 = var0[0];
        String var4 = var0[1];

        try {
            Date var5 = var2.parse(var3);
            Date var6 = var2.parse(var4);
            int var7 = (int) ((var6.getTime() - var5.getTime()) / 60000L);
            var1 += var7;
        } catch (ParseException var8) {
            var8.printStackTrace();
        }

        return var1;
    }

    public static String formatVolStr(int var0, int var1) {
        StringBuffer var2 = new StringBuffer();
        String var3 = "";
        double var4 = (double) (var0 / 100);
        if (var4 >= 1.0E8D) {
            var4 /= 1.0E8D;
            var3 = "亿";
        } else if (var4 >= 10000.0D) {
            var4 /= 10000.0D;
            var3 = "万";
        }

        var4 = roundAndHalf(var4, var1);
        if (var4 % 1.0D == 0.0D) {
            var2.append((int) var4);
        } else {
            var2.append(var4);
        }

        var2.append(var3);
        return var2.toString();
    }

    public static String formatVolStr2(int var0, int var1) {
        StringBuffer var2 = new StringBuffer();
        String var3 = "";
        double var4 = (double) var0;
        if (var4 >= 1.0E8D) {
            var4 /= 1.0E8D;
            var3 = "亿";
        } else if (var4 >= 10000.0D) {
            var4 /= 10000.0D;
            var3 = "万";
        }

        var4 = roundAndHalf(var4, var1);
        if (var4 % 1.0D == 0.0D) {
            var2.append((int) var4);
        } else {
            var2.append(var4);
        }

        var2.append(var3);
        return var2.toString();
    }

    public static String formatVolStr3(double var0, int var2) {
        StringBuffer var3 = new StringBuffer();
        String var4 = "";
        if (var0 >= 1.0E8D) {
            var0 /= 1.0E8D;
            var4 = "亿";
        } else if (var0 >= 10000.0D) {
            var0 /= 10000.0D;
            var4 = "万";
        }

        var0 = roundAndHalf(var0, var2);
        if (var0 % 1.0D == 0.0D) {
            var3.append((int) var0);
        } else {
            var3.append(var0);
        }

        var3.append(var4);
        return var3.toString();
    }

    public static String fomVol(String var0, int var1) {
        StringBuffer var2 = new StringBuffer();
        String var3 = "";
        double var4 = (double) str2Int(var0);
        if (var4 >= 1.0E8D) {
            var4 /= 1.0E8D;
            var3 = "亿";
        } else if (var4 >= 10000.0D) {
            var4 /= 10000.0D;
            var3 = "万";
        }

        var4 = roundAndHalf(var4, var1);
        if (var4 % 1.0D == 0.0D) {
            var2.append((int) var4);
        } else {
            var2.append(var4);
        }

        var2.append(var3);
        return var2.toString();
    }

    public static String fomVol(String var0) {
        StringBuffer var1 = new StringBuffer();
        String var2 = "";
        double var3 = str2Double(var0);
        if (var3 >= 1.0E8D) {
            var3 /= 1.0E8D;
            var2 = "亿";
        } else if (var3 >= 10000.0D) {
            var3 /= 10000.0D;
            var2 = "万";
        }

        var3 = roundAndHalf(var3, 2);
        if (var3 % 1.0D == 0.0D) {
            var1.append((int) var3);
        } else {
            var1.append(var3);
        }

        var1.append(var2);
        return var1.toString();
    }

    public static String amount2Str(float var0, int var1) {
        StringBuffer var2 = new StringBuffer();
        String var3 = "";
        double var4 = (double) var0;
        if (var0 >= 1.0E8F) {
            var4 = (double) var0 / 1.0E8D;
            var3 = "亿";
        } else if (var0 >= 10000.0F) {
            var4 = (double) var0 / 10000.0D;
            var3 = "万";
        }

        var4 = roundAndHalf(var4, var1);
        if (var4 % 1.0D == 0.0D) {
            var2.append((int) var4);
        } else {
            var2.append(var4);
        }

        var2.append(var3);
        return var2.toString();
    }

    public static String amount2Str(double var0, int var2) {
        StringBuffer var3 = new StringBuffer();
        String var4 = "";
        String var5 = "";
        if (var0 < 0.0D) {
            var5 = "-";
            var0 = Math.abs(var0);
        }

        if (var0 >= 1.0E8D) {
            var0 /= 1.0E8D;
            var4 = "亿";
        } else if (var0 >= 10000.0D) {
            var0 /= 10000.0D;
            var4 = "万";
        }

        var0 = roundAndHalf(var0, var2);
        var3.append(var5);
        if (var0 % 1.0D == 0.0D) {
            var3.append((int) var0);
        } else {
            var3.append(var0);
        }

        var3.append(var4);
        return var3.toString();
    }

    public static String amount2Str2(double var0, int var2) {
        StringBuffer var3 = new StringBuffer();
        String var4 = "";
        String var5 = "";
        if (var0 < 0.0D) {
            var5 = "-";
            var0 = Math.abs(var0);
        }

        if (var0 >= 1.0E8D) {
            var0 /= 1.0E8D;
            var4 = "亿";
        } else if (var0 >= 10000.0D) {
            var0 /= 10000.0D;
            var4 = "万";
        }

        String var6 = rahToStr2(var0, var2);
        var3.append(var5);
        var3.append(var6);
        var3.append(var4);
        return var3.toString();
    }

    public static String amount2StrW(double var0, int var2) {
        StringBuffer var3 = new StringBuffer();
        String var4 = "";
        String var5 = "";
        if (var0 < 0.0D) {
            var5 = "-";
            var0 = Math.abs(var0);
        }

        if (var0 >= 10000.0D) {
            var0 /= 10000.0D;
            var4 = "亿";
        } else if (var0 >= 1.0D) {
            var0 /= 1.0D;
            var4 = "万";
        }

        String var6 = rahToStr2(var0, var2);
        var3.append(var5);
        var3.append(var6);
        var3.append(var4);
        return var3.toString();
    }

    public static String amount2Str3(double var0, int var2, int var3) {
        StringBuffer var4 = new StringBuffer();
        String var5 = "";
        String var6 = "";
        if (var0 < 0.0D) {
            var6 = "-";
            var0 = Math.abs(var0);
        }

        double var7 = 10000.0D;
        double var9 = 10000.0D;
        if (var3 == 9) {
            var7 = 1.0E9D;
        } else if (var3 == 8) {
            var7 = 1.0E8D;
        } else if (var3 == 7) {
            var7 = 1.0E7D;
        } else if (var3 == 6) {
            var7 = 1000000.0D;
        }

        if (var9 > 0.0D && var0 >= var7) {
            var0 /= var9;
            var5 = "万";
        }

        String var11 = rahToStr2(var0, var2);
        var4.append(var6);
        var4.append(var11);
        var4.append(var5);
        return var4.toString();
    }

    public static String minuteToTime(int var0) {
        StringBuffer var1 = new StringBuffer();
        int var3 = var0 / 60;
        int var4 = var0 % 60;
        if (var3 < 10) {
            var1.append("0");
        }

        var1.append(var3);
        var1.append(":");
        if (var4 < 10) {
            var1.append("0");
        }

        var1.append(var4);
        return var1.toString();
    }

    public static String tradetimetostr(String var0) {
        try {
            if (var0.length() == 5) {
                var0 = "0" + var0;
            }

            String var1 = var0.substring(0, 2);
            String var2 = var0.substring(2, 4);
            String var3 = var0.substring(4, var0.length());
            return var1 + ":" + var2 + ":" + var3;
        } catch (Exception var4) {
            return "";
        }
    }

    public static String tradeDatetostr(String var0) {
        try {
            String var1 = var0.substring(4, 6);
            String var2 = var0.substring(6, var0.length());
            return var1 + "-" + var2;
        } catch (Exception var3) {
            return "";
        }
    }

    public static int getNowTimetoMin() {
        Date var0 = new Date();
        int var1 = var0.getHours() * 60 + var0.getMinutes();
        return var1;
    }

    public static String formatDate(int var0, String var1, int var2) {
        StringBuffer var3 = new StringBuffer();

        try {
            String var4 = minuteToTime(var2);
            if (var0 >= 1 && var0 <= 5) {
                if (var1.length() > 4) {
                    var1 = var1.substring(var1.length() - 4, var1.length());
                }

                if (var1.length() == 4) {
                    var3.append(var1.substring(0, 2));
                    var3.append("/");
                    var3.append(var1.substring(2, var1.length()));
                    var3.append(" ");
                } else if (var1.length() == 3) {
                    var3.append("0");
                    var3.append(var1.substring(0, 1));
                    var3.append("/");
                    var3.append(var1.substring(1, var1.length()));
                    var3.append(" ");
                }

                var3.append(var4);
            } else {
                String var5 = var1.substring(0, 4);
                String var6 = var1.substring(4, 6);
                String var7 = var1.substring(6, var1.length());
                var3.append(var5);
                var3.append("/");
                var3.append(var6);
                var3.append("/");
                var3.append(var7);
            }
        } catch (Exception var8) {
            Log.d("DataUtils", "时间解析数据问题...");
        }

        return var3.toString();
    }

    public static String getWeekOfDateStr(Date var0) {
        String[] var1 = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar var2 = Calendar.getInstance();
        var2.setTime(var0);
        int var3 = var2.get(DAY_OF_WEEK) - 1;
        if (var3 < 0) {
            var3 = 0;
        }

        return var1[var3];
    }

    public static int getWeekOfDate(Date var0) {
        if (var0 == null) {
            var0 = new Date();
        }

        int[] var1 = new int[]{7, 1, 2, 3, 4, 5, 6};
        Calendar var2 = Calendar.getInstance();
        var2.setTime(var0);
        int var3 = var2.get(DAY_OF_WEEK) - 1;
        if (var3 < 0) {
            var3 = 0;
        }

        return var1[var3];
    }

    public static boolean checktime(String[][] var0) {
        if (null == var0) {
            return false;
        } else {
            boolean var1 = false;
            Date var2 = new Date();

            for (int var3 = 0; var3 < var0.length; ++var3) {
                String[] var4 = var0[var3];
                String var5 = var4[0];
                String var6 = var4[1];

                try {
                    SimpleDateFormat var7 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    SimpleDateFormat var8 = new SimpleDateFormat("HH:mm:ss");
                    Date var9 = var7.parse(var5);
                    Date var10 = var7.parse(var6);
                    int var11 = var10.getDay() - var9.getDay();
                    var9 = var8.parse(var8.format(var9));
                    var10 = var8.parse(var8.format(var10));
                    Date var12 = var8.parse(var8.format(var2));
                    Date var13 = var8.parse("00:00:00");
                    Date var14 = var8.parse("04:00:00");
                    Calendar var15;
                    if (var12.after(var13) && var12.before(var14)) {
                        var15 = Calendar.getInstance();
                        var15.setTime(var12);
                        var15.add(DATE, var11);
                        var12 = var15.getTime();
                    }

                    var15 = Calendar.getInstance();
                    var15.setTime(var10);
                    var15.add(DATE, var11);
                    var10 = var15.getTime();
                    if (var12.after(var9) && var12.before(var10)) {
                        var1 = true;
                        break;
                    }
                } catch (ParseException var16) {
                    var16.printStackTrace();
                }
            }

            return var1;
        }
    }


    public static void writeFile(String var0, String var1) {
        try {
            File var2 = Environment.getExternalStorageDirectory();
            String var3 = var2.getPath();
            File var4 = new File(var3 + var1);
            if (!var4.exists()) {
                var4.createNewFile();
            }

            FileOutputStream var5 = new FileOutputStream(var3 + var1);
            byte[] var6 = var0.getBytes();
            var5.write(var6);
            var5.close();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public static void writeFile(String var0) {
        try {
            File var1 = Environment.getExternalStorageDirectory();
            String var2 = var1.getPath();
            File var3 = new File(var2 + "/test.txt");
            if (!var3.exists()) {
                var3.createNewFile();
            }

            FileOutputStream var4 = new FileOutputStream(var2 + "/test.txt");
            byte[] var5 = var0.getBytes();
            var4.write(var5);
            var4.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public static void writeFile(byte[] var0) {
        try {
            File var1 = Environment.getExternalStorageDirectory();
            String var2 = var1.getPath();
            File var3 = new File(var2 + "/testbyte.txt");
            if (!var3.exists()) {
                var3.createNewFile();
            }

            FileOutputStream var4 = new FileOutputStream(var2 + "/testbyte.txt");
            var4.write(var0);
            var4.close();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    public static String int2ip(int var0) {
        StringBuilder var1 = new StringBuilder();
        var1.append(var0 & 255).append(".");
        var1.append(var0 >> 8 & 255).append(".");
        var1.append(var0 >> 16 & 255).append(".");
        var1.append(var0 >> 24 & 255);
        return var1.toString();
    }

    public static String getLocalIpAddress(Context var0) {
        try {
            WifiManager var1 = (WifiManager) var0.getSystemService(Context.WIFI_SERVICE);
            WifiInfo var2 = var1.getConnectionInfo();
            int var3 = var2.getIpAddress();
            return int2ip(var3);
        } catch (Exception var4) {
            return " 获取IP出错!!!!请保证是WIFI,或者请重新打开网络!\n" + var4.getMessage();
        }
    }

    public static String getLocalMacAddr(Context var0) {
        try {
            WifiManager var1 = (WifiManager) var0.getSystemService(Context.WIFI_SERVICE);
            WifiInfo var2 = var1.getConnectionInfo();
            return var2.getMacAddress();
        } catch (Exception var3) {
            return "";
        }
    }

    public static String getLocalMac(Context var0) {
        return "";
    }

    public static String getDevicesInfo() {
        return Build.MODEL;
    }

    public static String readKeyFile(Context var0, String var1) {
        String var2 = "";

        int var4;
        try {
            for (FileInputStream var3 = var0.openFileInput(var1); (var4 = var3.read()) != -1; var2 = var2 + Character.toString((char) var4)) {
                ;
            }
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        return var2;
    }

    public static void writeKey(byte[] var0, Context var1, String var2) {
        try {
            FileOutputStream var3 = var1.openFileOutput(var2, 0);
            var3.write(var0);
            var3.close();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static String getRandomString(int var0) {
        String var1 = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random var2 = new Random();
        StringBuffer var3 = new StringBuffer();

        for (int var4 = 0; var4 < var0; ++var4) {
            int var5 = var2.nextInt(var1.length());
            var3.append(var1.charAt(var5));
        }

        return var3.toString();
    }

    public static void writeFileTest(String var0) {
        try {
            File var1 = Environment.getExternalStorageDirectory();
            String var2 = var1.getPath();
            File var3 = new File(var2 + "/1.txt");
            if (!var3.exists()) {
                var3.createNewFile();
            }

            FileOutputStream var4 = new FileOutputStream(var2 + "/1.txt");
            var4.write(var0.getBytes());
            var4.close();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public static String getSpecifiedDayAfter(String var0) {
        Calendar var1 = Calendar.getInstance();
        Date var2 = null;

        try {
            var2 = (new SimpleDateFormat("yyyy-MM-dd")).parse(var0);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        var1.setTime(var2);
        int var3 = var1.get(DATE);
        var1.set(DATE, var3 + 1);
        String var4 = (new SimpleDateFormat("yyyy-MM-dd")).format(var1.getTime());
        return var4;
    }

    public static String getSpecifiedDayBefore(String var0) {
        Calendar var1 = Calendar.getInstance();
        Date var2 = null;

        try {
            var2 = (new SimpleDateFormat("yyyy-MM-dd")).parse(var0);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        var1.setTime(var2);
        int var3 = var1.get(DATE);
        var1.set(DATE, var3 - 1);
        String var4 = (new SimpleDateFormat("yyyy-MM-dd")).format(var1.getTime());
        return var4;
    }

    public static int charToInt(String var0) {
        int var1 = 0;

        try {
            var1 = Integer.valueOf(var0).intValue();
        } catch (Exception var5) {
            try {
                char[] var3 = var0.toCharArray();
                var1 = var3[0];
                if (var1 > 55) {
                    var1 -= 55;
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

        return var1;
    }

    public static String intToChar(int var0) {
        String var1 = var0 + "";

        try {
            if (var0 < 10) {
                var1 = String.valueOf(var0);
            } else {
                int var2 = 65 + var0 - 10;
                char var3 = (char) var2;
                var1 = String.valueOf(var3);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return var1;
    }

    public static String getFormatTime4(long var0) {
        String var2 = "";
        SimpleDateFormat var3 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat var4 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

        try {
            Calendar var5 = Calendar.getInstance();
            var5.set(HOUR_OF_DAY, 0);
            var5.set(SECOND, 0);
            var5.set(MINUTE, 0);
            var5.set(MILLISECOND, 0);
            if (var0 > 0L) {
                long var6 = var5.getTimeInMillis();
                var5.add(DATE, -1);
                long var8 = var5.getTimeInMillis();
                var5.add(DATE, -7);
                long var10 = var5.getTimeInMillis();
                if (var0 >= var6) {
                    var2 = var3.format(Long.valueOf(var0));
                } else if (var0 >= var8 && var0 < var6) {
                    var2 = "昨天 " + var3.format(Long.valueOf(var0));
                } else if (var0 >= var10 && var0 < var8) {
                    var5.setTime(new Date(var0));
                    int var12 = var5.get(DAY_OF_WEEK);
                    String var13 = "";
                    if (2 == var12) {
                        var13 = "星期一";
                    } else if (3 == var12) {
                        var13 = "星期二";
                    } else if (4 == var12) {
                        var13 = "星期三";
                    } else if (5 == var12) {
                        var13 = "星期四";
                    } else if (6 == var12) {
                        var13 = "星期五";
                    } else if (7 == var12) {
                        var13 = "星期六";
                    } else if (1 == var12) {
                        var13 = "星期日";
                    }

                    var2 = var13 + " " + var3.format(Long.valueOf(var0));
                } else {
                    var2 = var4.format(Long.valueOf(var0));
                }
            }
        } catch (Exception var14) {
            var14.printStackTrace();
        }

        return var2;
    }

    public static String getFormatTime3(long var0) {
        String var2 = "";
        SimpleDateFormat var3 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat var4 = new SimpleDateFormat("yyyy/MM/dd");

        try {
            Calendar var5 = Calendar.getInstance();
            var5.setTime(new Date());
            var5.set(HOUR_OF_DAY, 0);
            var5.set(SECOND, 0);
            var5.set(MINUTE, 0);
            var5.set(MILLISECOND, 0);
            if (var0 > 0L) {
                long var6 = var5.getTimeInMillis();
                var5.add(DATE, -1);
                long var8 = var5.getTimeInMillis();
                var5.add(DATE, -7);
                long var10 = var5.getTimeInMillis();
                if (var0 >= var6) {
                    var2 = var3.format(Long.valueOf(var0));
                } else if (var0 >= var8 && var0 < var6) {
                    var2 = "昨天";
                } else if (var0 >= var10 && var0 < var8) {
                    var5.setTime(new Date(var0));
                    int var12 = var5.get(DAY_OF_WEEK);
                    String var13 = "";
                    if (2 == var12) {
                        var13 = "星期一";
                    } else if (3 == var12) {
                        var13 = "星期二";
                    } else if (4 == var12) {
                        var13 = "星期三";
                    } else if (5 == var12) {
                        var13 = "星期四";
                    } else if (6 == var12) {
                        var13 = "星期五";
                    } else if (7 == var12) {
                        var13 = "星期六";
                    } else if (1 == var12) {
                        var13 = "星期日";
                    }

                    var2 = var13;
                } else {
                    var2 = var4.format(Long.valueOf(var0));
                }
            }
        } catch (Exception var14) {
            var14.printStackTrace();
        }

        return var2;
    }


    public static String convertUnicode(String var0) {
        var0 = var0 == null ? "" : var0;
        StringBuffer var2 = new StringBuffer(1000);
        var2.setLength(0);

        for (int var4 = 0; var4 < var0.length(); ++var4) {
            char var3 = var0.charAt(var4);
            var2.append("\\u");
            int var5 = var3 >>> 8;
            String var1 = Integer.toHexString(var5);
            if (var1.length() == 1) {
                var2.append("0");
            }

            var2.append(var1);
            var5 = var3 & 255;
            var1 = Integer.toHexString(var5);
            if (var1.length() == 1) {
                var2.append("0");
            }

            var2.append(var1);
        }

        return new String(var2);
    }

    public static double div(double var0, double var2) {
        return div(var0, var2, 3);
    }

    public static double div(double var0, double var2, int var4) {
        if (var4 < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal var5 = new BigDecimal(Double.toString(var0));
            BigDecimal var6 = new BigDecimal(Double.toString(var2));
            return var5.divide(var6, var4, 4).doubleValue();
        }
    }

    public static double round(double var0, int var2) {
        if (var2 < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal var3 = new BigDecimal(Double.toString(var0));
            BigDecimal var4 = new BigDecimal("1");
            return var3.divide(var4, var2, 4).doubleValue();
        }
    }

    public static double sub(double var0, double var2) {
        BigDecimal var4 = new BigDecimal(var0);
        BigDecimal var5 = new BigDecimal(var2);
        return var4.subtract(var5).doubleValue();
    }

    public static boolean checktime(String var0, String var1, String var2) {
        boolean var3 = false;
        SimpleDateFormat var4 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat var5 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date var6 = var4.parse(var0);
            Date var7 = var4.parse(var1);
            Date var8 = var4.parse(var4.format(var5.parse(var2)));
            if (var8.after(var6) && var8.before(var7)) {
                var3 = true;
            }
        } catch (Exception var9) {
            ;
        }

        return var3;
    }

    public static String getUUID(Context var0) {
        String var1 = "";
        return var1;
    }

    public static boolean checkDate(String var0, String var1) {
        boolean var2 = false;
        SimpleDateFormat var3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat var4 = new SimpleDateFormat("yyyy-MM-dd");

        try {
            String var5 = var4.format(var3.parse(var0));
            String var6 = var4.format(var3.parse(var1));
            if (var5.equals(var6)) {
                return true;
            }
        } catch (Exception var7) {
            ;
        }

        return var2;
    }

    public static Bitmap readBitMap(Context var0, int var1) {
        BitmapFactory.Options var2 = new BitmapFactory.Options();
        var2.inPreferredConfig = Bitmap.Config.RGB_565;
        var2.inPurgeable = true;
        var2.inInputShareable = true;
        InputStream var3 = var0.getResources().openRawResource(var1);
        return BitmapFactory.decodeStream(var3, (Rect) null, var2);
    }

    public static String setPrefix(String var0) {
        if (var0.startsWith("SH", 0)) {
            var0 = var0.replace("SH", "01");
        } else if (var0.startsWith("SZ", 0)) {
            var0 = var0.replace("SZ", "00");
        }

        return var0;
    }

    public static String filterNull(String var0) {
        return var0 == null ? "" : var0;
    }

    public static int getArrayIndex(int var0, int var1, int var2) {
        var0 = var0 > var2 ? var2 : var0;
        var0 = var0 < var1 ? var1 : var0;
        return var0;
    }

    public static boolean inTime(long var0, long var2) {
        long var4 = (new Date()).getTime();
        return var4 > var0 && var4 < var2;
    }

    public static void mapToBean(Map var0, Object var1) {
        Set var2 = var0.keySet();
        Iterator var3 = var2.iterator();

        while (var3.hasNext()) {
            Object var4 = var3.next();
            Object var5 = var0.get(var4);
            setMethod(var4, var5, var1);
        }

    }

    public static void setMethod(Object var0, Object var1, Object var2) {
        try {
            Class var3 = Class.forName(var2.getClass().getName());
            String var4 = (String) var0;
            var4 = var4.trim();
            if (!var4.substring(0, 1).equals(var4.substring(0, 1).toUpperCase())) {
                var4 = var4.substring(0, 1).toUpperCase() + var4.substring(1);
            }

            if (!String.valueOf(var0).startsWith("set")) {
                var4 = "set" + var4;
            }

            Class[] var5 = new Class[]{Class.forName("java.lang.String")};
            Method var6 = var3.getMethod(var4, var5);
            var6.invoke(var2, new Object[]{var1});
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public static String getChartData(String var0, Context var1) {
        StringBuffer var2 = new StringBuffer();

        try {
            InputStreamReader var3 = new InputStreamReader(var1.getResources().getAssets().open(var0), "gb2312");
            BufferedReader var4 = new BufferedReader(var3);
            String var5 = "";

            while ((var5 = var4.readLine()) != null) {
                var2.append(var5);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return var2.toString();
    }

    public static String parseDouble(String var0, int var1) {
        try {
            double var2 = Double.parseDouble(var0);
            return var2 != -1.0D ? rahToStr(var2, var1) : "--";
        } catch (Exception var4) {
            return "--";
        }
    }

    public static String parseDouble(double var0, int var2) {
        try {
            return var0 != -1.0D ? rahToStr(var0, var2) : "--";
        } catch (Exception var4) {
            return "--";
        }
    }

    public static double str2Double(String var0) {
        try {
            return Double.parseDouble(var0);
        } catch (Exception var2) {
            return -1.0D;
        }
    }

    public static int str2Int(String var0) {
        try {
            return Integer.parseInt(var0);
        } catch (Exception var2) {
            return -1;
        }
    }

    public static String val2w(String number) {
        String result = "--";
        try {
            double nVal = Double.parseDouble(number);
            if (nVal >= 10000) {
                nVal = nVal / 10000;
                result = rahToStr2(nVal, 2) + "万";
            } else {
                result = rahToStr2(nVal, 0);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String val2w(String number, int var) {
        String result = "--";
        try {
            double nVal = Double.parseDouble(number);
            if (nVal >= 10000) {
                nVal = nVal / 10000;
                result = rahToStr2(nVal, var) + "万";
            } else if (nVal >= 100000000) {
                nVal = nVal / 100000000;
                result = rahToStr2(nVal, var) + "亿";
            } else {
                result = rahToStr2(nVal, var);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isIndex(int setcode, String code) {
        if (setcode != Const.shSetcode && setcode != Const.szSetcode)
            return false;
        long l = 0;
        try {
            l = Long.parseLong(code);
        } catch (Exception e) {
            return false;
        }
        if (setcode == Const.shSetcode) {
            if (l > 999000l || l < 30l) {
                return true;
            }
            String str = code.substring(0, 3);
            if (str.equals("777") || str.equals("778") || str.equals("779") || str.equals("000"))
                return true;
        } else {
            String str = code.substring(0, 3);
            if (str.equals("399"))
                return true;
        }
        if (l >= 880000l && l < 885000l)
            return true;
        return l >= 990000l && l < 994000l;
    }

    public static int getSetCode(int setcode, String code) {
        int setCode = getStockType(setcode, code);
        if (setCode == 9) {
            setCode = 1;
        } else {
            setCode = 0;
        }
        return setCode;
    }

    /**
     * @param setcode
     * @param code
     * @return
     * @function 获取证券类型
     */
    public static int getStockType(int setcode, String code) {
        int type = 0;
        try {
            switch (setcode) {
                case Const.bhSetcode:
                    type = Const.CODE_BH;
                    break;
                case Const.szSetcode:
                    type = Const.CODE_SZOTHER;
                    if (code.substring(0, 3).equals("003")) {
                        return Const.CODE_SZSPEC;
                    }
                    if (code.substring(0, 3).equals("300")) {
                        return Const.CODE_SZ300CY;
                    }
                    String str = code.substring(0, 1);
                    if (str.equals("0")) {
                        String str1 = code.substring(1, 2);
                        if (str1.equals("0")) {
                            if (code.substring(2, 3).equals("2")) {
                                type = Const.CODE_SZCY;
                            } else {
                                type = Const.CODE_SZAG;
                            }
                        } else if (str1.equals("3")) {
                        } else if (str1.equals("8")) {
                            type = Const.CODE_SZQZ;
                        }
                    } else if (str.equals("1")) {
                        String str1 = code.substring(1, 2);
                        if (str1.equals("0")) {
                            type = Const.CODE_SZGZ;
                        } else if (str1.equals("1")) {
                            type = Const.CODE_SZZQ;
                        } else if (str1.equals("2")) {
                            type = Const.CODE_SZKZHZQ;
                        } else if (str1.equals("3")) {
                            type = Const.CODE_SZGZHG;
                        } else if (str1.equals("5")) {
                            type = Const.CODE_KFJJ;
                        } else if (str1.equals("6")) {
                            type = Const.CODE_KFJJ;
                        } else if (str1.equals("7")) {

                        } else if (str1.equals("8")) {
                            type = Const.CODE_SZJJ;
                        }
                    } else if (str.equals("2")) {
                        type = Const.CODE_SZBG;
                    } else if (str.equals("3")) {
                        if (code.substring(1, 2).equals("8")) {
                            type = Const.CODE_SZQZ;
                        }
                    } else if (str.equals("4")) {
                        String str1 = code.substring(1, 2);
                        if (str1.equals("0") || str1.equals("2") || str1.equals("3"))
                            type = Const.CODE_SB;
                    }
                    break;
                case Const.shSetcode:
                    type = Const.CODE_SHOTHER;
                    String strsh = code.substring(0, 1);
                    if (strsh.equals("0")) {
                        if (Long.parseLong(code) > 999l)
                            type = Const.CODE_SHGZ;
                    } else if (strsh.equals("1")) {
                        if (code.substring(1, 2).equals("2")) {
                            type = Const.CODE_SHZQ;
                        } else {
                            type = Const.CODE_SHKZHZQ;
                        }
                    } else if (strsh.equals("2")) {
                        type = Const.CODE_SHGZHG;
                    } else if (strsh.equals("5")) {
                        if (code.substring(1, 2).equals("8")) {
                            type = Const.CODE_SHQZ;
                        } else {
                            type = Const.CODE_SHJJ;
                        }
                    } else if (strsh.equals("6")) {
                        type = Const.CODE_SHAG;
                    } else if (strsh.equals("7")) {
                        if (code.substring(1, 2).equals("0")) {
                            type = Const.CODE_SHQZ;
                        }
                        if (code.substring(1, 2).equals("5") || code.substring(1, 2).equals("7")) {
                            type = Const.CODE_SHGZ;
                        }
                    } else if (strsh.equals("9")) {
                        if (Long.parseLong(code) < 909000L)
                            type = Const.CODE_SHBG;
                    }
                    break;
            }
        } catch (Exception ex) {

        }
        return type;
    }

    /**
     * @param name    股票名称
     * @param setcode
     * @param code    股票代码
     * @param tpflag  精度
     * @param Close   收盘价格
     * @param bUp     是否涨/true=涨停 false=跌停
     * @return
     * @function 涨跌停函数算法
     */
    public static double GetTPPrice(String name, int setcode, String code, int tpflag, double Close, boolean bUp) {
        double fRet = 100.0, fStep = 100.0, fUnit = 100.0;
        if (tpflag == 3)
            fUnit = 1000.0;
        int Type = getStockType(setcode, code);
        if (name.length() > 2 && (name.substring(0, 1).equals("S") || name.substring(0, 3).equals("*ST"))) {
            if (bUp)
                fStep = (int) (Close * 0.05 * fUnit + 0.5) / fUnit;
            else
                fStep = (int) (Close * 0.05 * fUnit + 0.05) / fUnit;
            if (bUp)
                fRet = (int) ((Close + fStep) * fUnit + 0.5) / fUnit;
            else
                fRet = (int) (Close * 0.95 * fUnit + 0.5) / fUnit;
        } else if (name.length() > 2 && name.substring(0, 2).equals("PT")) {
            if (bUp)
                fStep = (int) (Close * 0.05 * fUnit + 0.5) / fUnit;
            else
                fStep = (int) (Close * 0.05 * fUnit + 0.05) / fUnit;
            if (bUp)
                fRet = (int) ((Close + fStep) * fUnit + 0.5) / fUnit;
            else
                fRet = 0.0;
        } else if (Type == Const.CODE_SB) {// 三板
            if (bUp)
                fStep = (int) (Close * 0.05 * fUnit + 0.5) / fUnit;
            else
                fStep = (int) (Close * 0.05 * fUnit + 0.05) / fUnit;
            if (bUp)
                fRet = (int) ((Close + fStep) * fUnit + 0.5) / fUnit;
            else
                fRet = (int) (Close * 0.95 * fUnit + 0.5) / fUnit;
        } else if (Type == Const.CODE_SZQZ || Type == Const.CODE_SZGZ || Type == Const.CODE_SZZQ || Type == Const.CODE_SZKZHZQ || Type == Const.CODE_SZGZHG || Type == Const.CODE_SHQZ
                || Type == Const.CODE_SHGZ || Type == Const.CODE_SHZQ || Type == Const.CODE_SHKZHZQ || Type == Const.CODE_SHGZHG)// 国债等
        {
            fRet = 0.0;
        } else // 其它
        {
            if (bUp)
                fStep = (int) (Close * 0.10 * fUnit + 0.5) / fUnit;
            else
                fStep = (int) (Close * 0.10 * fUnit + 0.05) / fUnit;
            if (bUp)
                fRet = (int) ((Close + fStep) * fUnit + 0.5) / fUnit;
            else
                fRet = (int) (Close * 0.90 * fUnit + 0.5) / fUnit;
        }
        return fRet;
    }

    public static boolean isAbStock(int setcode, String code) {
        if (setcode != Const.shSetcode && setcode != Const.szSetcode)
            return false;
        int stocktype = getStockType(setcode, code);
        if (stocktype == Const.CODE_SZAG || stocktype == Const.CODE_SHAG || stocktype == Const.CODE_SZCY || stocktype == Const.CODE_SZ300CY || stocktype == Const.CODE_SZBG || stocktype == Const.CODE_SHBG) {
            return true;
        }
        return false;
    }


    public static CommonAdapter<Object> initBillStocks(final int reqId, final ListView lv, List<Object> mShareEntityData, final View.OnClickListener listener) {
        CommonAdapter<Object> commonAdapter = new CommonAdapter<Object>(mShareEntityData, 1) {
            @NonNull
            @Override
            public AdapterItem createItem(Object o) {
                return new AdapterItem() {

                    public TextView mState;
                    public TextView mStateTx;
                    public TextView mCount2;
                    public TextView mCount;
                    public TextView mPrice2;
                    public TextView mPrice;
                    public TextView mTime;
                    public TextView mName;
                    public ImageView mBsicon;
                    public View view;


                    @Override
                    public int getLayoutResId() {
                        return R.layout.cancel_bill_item;
                    }

                    @Override
                    public void bindViews(View contentView) {
                        this.view = contentView;
                        mBsicon = (ImageView) contentView.findViewById(R.id.bsicon);
                        mName = (TextView) contentView.findViewById(R.id.name);
                        mTime = (TextView) contentView.findViewById(R.id.time);
                        mPrice = (TextView) contentView.findViewById(R.id.price);
                        mPrice2 = (TextView) contentView.findViewById(R.id.price2);
                        mCount = (TextView) contentView.findViewById(R.id.count);
                        mCount2 = (TextView) contentView.findViewById(R.id.count2);
                        mStateTx = (TextView) contentView.findViewById(R.id.stateTx);
                        mState = (TextView) contentView.findViewById(R.id.state);
                    }

                    @Override
                    public void setViews() {
                        view.setOnClickListener(listener);
                    }

                    @Override
                    public void handleData(Object o, int i) {
                        String bsfg = "";
                        if (reqId == TradeServiceUtil.M_DEAL_SEARCH || reqId == TradeServiceUtil.M_DEAL_HIS) {
                            HisTradeEntity item = (HisTradeEntity) o;
                            mPrice2.setVisibility(View.GONE);
                            mState.setVisibility(View.GONE);
                            mCount2.setVisibility(View.GONE);
                            mName.setText(item.getZQMC());
                            mTime.setText(item.getCJTI());
                            mCount.setText(item.getCJSL());
                            mPrice.setText(DataUtil.val2w(item.getCJJG(), 2));
                            mStateTx.setText(DataUtil.val2w(item.getCJJE(), 2));
                            bsfg = item.getBSFG();
                        } else {
                            HisOrderEntity item = (HisOrderEntity) o;
                            mName.setText(item.getZQMC());
                            mTime.setText(item.getWTTI());
                            mPrice.setText(DataUtil.val2w(item.getWTJG(), 3));
                            mPrice2.setText(DataUtil.val2w(item.getCJJG(), 3));
                            mCount.setText(item.getWTSL());
                            mCount2.setText(item.getCJSL());
                            mState.setText(getWTTP(lv.getContext(), item.getWTTP()));
                            bsfg = item.getBSFG();
                            if ("1".equals(bsfg)) {//买入
                                mStateTx.setText("买入");
                            } else {
                                mStateTx.setText("卖出");
                            }
                        }
                        view.setTag(o);
                        if ("1".equals(bsfg)) {//买入
                            mBsicon.setImageResource(R.mipmap.buy_icon);
//                            mName.setTextColor(lv.getResources().getColor(R.color.up_tx_color));
//                            mTime.setTextColor(lv.getResources().getColor(R.color.up_tx_color));
//                            mPrice.setTextColor(lv.getResources().getColor(R.color.up_tx_color));
//                            mPrice2.setTextColor(lv.getResources().getColor(R.color.up_tx_color));
//                            mCount.setTextColor(lv.getResources().getColor(R.color.up_tx_color));
//                            mCount2.setTextColor(lv.getResources().getColor(R.color.up_tx_color));
//                            mStateTx.setTextColor(lv.getResources().getColor(R.color.up_tx_color));
//                            mState.setTextColor(lv.getResources().getColor(R.color.up_tx_color));
                        } else {
                            mBsicon.setImageResource(R.mipmap.sell_icon);
//                            mName.setTextColor(lv.getResources().getColor(R.color.down_tx_color));
//                            mTime.setTextColor(lv.getResources().getColor(R.color.down_tx_color));
//                            mPrice.setTextColor(lv.getResources().getColor(R.color.down_tx_color));
//                            mPrice2.setTextColor(lv.getResources().getColor(R.color.down_tx_color));
//                            mCount.setTextColor(lv.getResources().getColor(R.color.down_tx_color));
//                            mCount2.setTextColor(lv.getResources().getColor(R.color.down_tx_color));
//                            mStateTx.setTextColor(lv.getResources().getColor(R.color.down_tx_color));
//                            mState.setTextColor(lv.getResources().getColor(R.color.down_tx_color));
                        }
                    }
                };
            }
        };
        lv.setAdapter(commonAdapter);
        return commonAdapter;
    }

    public static CommonAdapter<ShareEntity> initShareStocks(final ListView lv, final ArrayList<ShareEntity> mShareEntityData, final View.OnClickListener listener, final View.OnClickListener menu1listener, final View.OnClickListener menu2listener, final View.OnClickListener menu3listener) {
        CommonAdapter<ShareEntity> commonAdapter = new CommonAdapter<ShareEntity>(mShareEntityData, 1) {

            public String mCodeAndName = "";

            @NonNull
            @Override
            public AdapterItem createItem(Object o) {
                return new AdapterItem() {

                    public View mMenu;
                    public View mMenu1;
                    public View mMenu2;
                    public View mMenu3;
                    public TextView mYk;
                    public TextView mDQJG;
                    public TextView mCBJ;
                    public TextView mKMSL;
                    public TextView mZQSL;
                    public TextView mFDYK;
                    public TextView mZXSZ;
                    public TextView mZQMC;
                    public View view;

                    @Override
                    public int getLayoutResId() {
                        return R.layout.stock_trade_list_item;
                    }

                    @Override
                    public void bindViews(View contentView) {
                        mZQMC = (TextView) contentView.findViewById(R.id.ZQMC);
                        mZXSZ = (TextView) contentView.findViewById(R.id.ZXSZ);
                        mFDYK = (TextView) contentView.findViewById(R.id.FDYK);
                        mZQSL = (TextView) contentView.findViewById(R.id.ZQSL);
                        mKMSL = (TextView) contentView.findViewById(R.id.KMSL);
                        mCBJ = (TextView) contentView.findViewById(R.id.CBJ);
                        mDQJG = (TextView) contentView.findViewById(R.id.DQJG);
                        mYk = (TextView) contentView.findViewById(R.id.yk);
                        mMenu = contentView.findViewById(R.id.menu);
                        mMenu1 = contentView.findViewById(R.id.menu_item1);
                        mMenu2 = contentView.findViewById(R.id.menu_item2);
                        mMenu3 = contentView.findViewById(R.id.menu_item3);
                        this.view = contentView;
                    }

                    @Override
                    public void setViews() {
                        if (listener == null && menu1listener != null) {
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ShareEntity item = (ShareEntity) view.getTag();
                                    int visiable = mMenu.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;

                                    if (mMenu.getVisibility() == View.GONE) {
                                        mCodeAndName = item.getZQDM() + item.getZQMC();
                                    } else {
                                        mCodeAndName = "";
                                    }

                                    notifyDataSetChanged();
                                }
                            });
                        } else {
                            view.setOnClickListener(listener);
                        }
                        mMenu1.setOnClickListener(menu1listener);
                        mMenu2.setOnClickListener(menu2listener);
                        mMenu3.setOnClickListener(menu3listener);
                    }

                    @Override
                    public void handleData(Object o, int i) {
                        ShareEntity item = (ShareEntity) o;
                        String cn = item.getZQDM() + item.getZQMC();
                        if (!mCodeAndName.equals("") && cn.equals(mCodeAndName)) {
                            mMenu.setVisibility(View.VISIBLE);
                        } else {
                            mMenu.setVisibility(View.GONE);
                        }

                        mZQMC.setText(item.getZQMC());
                        mZXSZ.setText(DataUtil.parseDouble(item.getZXSZ(), 3));
                        mFDYK.setText(DataUtil.parseDouble(item.getFDYK(), 3));
                        mZQSL.setText(DataUtil.parseDouble(item.getZQSL(), 3));
                        mKMSL.setText(DataUtil.parseDouble(item.getKMSL(), 3));
                        mCBJ.setText(DataUtil.parseDouble(item.getCBJ(), 3));
                        mDQJG.setText(DataUtil.parseDouble(item.getDQJG(), 3));
                        double fdyk = (DataUtil.str2Double(item.getDQJG()) - DataUtil.str2Double(item.getCBJ())) / DataUtil.str2Double(item.getDQJG()) * 100;
                        mYk.setText(DataUtil.parseDouble(fdyk, 3) + "%");
                        if (fdyk > 0) {
//                            mZQMC.setTextColor(lv.getResources().getColor(R.color.up_tx_color));
//                            mZXSZ.setTextColor(lv.getResources().getColor(R.color.up_tx_color));
                            mFDYK.setTextColor(lv.getResources().getColor(R.color.up_tx_color));
//                            mZQSL.setTextColor(lv.getResources().getColor(R.color.up_tx_color));
//                            mKMSL.setTextColor(lv.getResources().getColor(R.color.up_tx_color));
//                            mCBJ.setTextColor(lv.getResources().getColor(R.color.up_tx_color));
//                            mDQJG.setTextColor(lv.getResources().getColor(R.color.up_tx_color));
                            mYk.setTextColor(lv.getResources().getColor(R.color.up_tx_color));
                        } else if (fdyk == 0) {
//                            mZQMC.setTextColor(lv.getResources().getColor(R.color.gray));
//                            mZXSZ.setTextColor(lv.getResources().getColor(R.color.gray));
                            mFDYK.setTextColor(lv.getResources().getColor(R.color.gray));
//                            mZQSL.setTextColor(lv.getResources().getColor(R.color.gray));
//                            mKMSL.setTextColor(lv.getResources().getColor(R.color.gray));
//                            mCBJ.setTextColor(lv.getResources().getColor(R.color.gray));
//                            mDQJG.setTextColor(lv.getResources().getColor(R.color.gray));
                            mYk.setTextColor(lv.getResources().getColor(R.color.gray));
                        } else {
//                            mZQMC.setTextColor(lv.getResources().getColor(R.color.down_tx_color));
//                            mZXSZ.setTextColor(lv.getResources().getColor(R.color.down_tx_color));
                            mFDYK.setTextColor(lv.getResources().getColor(R.color.down_tx_color));
//                            mZQSL.setTextColor(lv.getResources().getColor(R.color.down_tx_color));
//                            mKMSL.setTextColor(lv.getResources().getColor(R.color.down_tx_color));
//                            mCBJ.setTextColor(lv.getResources().getColor(R.color.down_tx_color));
//                            mDQJG.setTextColor(lv.getResources().getColor(R.color.down_tx_color));
                            mYk.setTextColor(lv.getResources().getColor(R.color.down_tx_color));
                        }
                        view.setTag(item);
                        mMenu1.setTag(item);
                        mMenu2.setTag(item);
                        mMenu3.setTag(item);

                    }
                };
            }
        };
        lv.setAdapter(commonAdapter);
        return commonAdapter;
    }

    /**
     * 获取委托状态 0 未报 可以 1 待报 不可以 2 已报 可以 3 已报待撤 不可以 4 部成待撤 不可以 5 部撤 不可以 6 已撤 不可以 7
     * 部成 可以 8 已成 不可以 9 废单 不可以 W 待确认 不可以
     *
     * @param context
     * @param wtStatus
     * @return
     */
    public static String getWTTP(Context context, String wtStatus) {
        String status = "";
        Resources resource = context.getResources();
        if ("0".equals(wtStatus)) {
            status = resource.getString(R.string.trade_order_status_0);
        } else if ("1".equals(wtStatus)) {
            status = resource.getString(R.string.trade_order_status_1);
        } else if ("2".equals(wtStatus)) {
            status = resource.getString(R.string.trade_order_status_2);
        } else if ("3".equals(wtStatus)) {
            status = resource.getString(R.string.trade_order_status_3);
        } else if ("4".equals(wtStatus)) {
            status = resource.getString(R.string.trade_order_status_4);
        } else if ("5".equals(wtStatus)) {
            status = resource.getString(R.string.trade_order_status_5);
        } else if ("6".equals(wtStatus)) {
            status = resource.getString(R.string.trade_order_status_6);
        } else if ("7".equals(wtStatus)) {
            status = resource.getString(R.string.trade_order_status_7);
        } else if ("8".equals(wtStatus)) {
            status = resource.getString(R.string.trade_order_status_8);
        } else if ("9".equals(wtStatus)) {
            status = resource.getString(R.string.trade_order_status_9);
        } else if ("w".equals(wtStatus)) {
            status = resource.getString(R.string.trade_order_status_10);
        }
        return status;
    }

    public static boolean canCancelBill(Context context, String wtStatus) {
        String status = "";
        Resources resource = context.getResources();
        if ("0".equals(wtStatus)) {
            return true;
        } else if ("1".equals(wtStatus)) {

        } else if ("2".equals(wtStatus)) {
            return true;
        } else if ("3".equals(wtStatus)) {

        } else if ("4".equals(wtStatus)) {

        } else if ("5".equals(wtStatus)) {

        } else if ("6".equals(wtStatus)) {

        } else if ("7".equals(wtStatus)) {
            return true;
        } else if ("8".equals(wtStatus)) {

        } else if ("9".equals(wtStatus)) {

        } else if ("w".equals(wtStatus)) {

        }
        return false;
    }


    public static String trimAsicc(String str) {
        if (null == str || str.equals("")) return "";
        return str.replaceAll((char) 12288 + "", "").trim();
    }
}

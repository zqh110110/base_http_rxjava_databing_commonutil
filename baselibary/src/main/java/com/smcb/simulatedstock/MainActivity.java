package com.smcb.simulatedstock;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.smcb.simulatedstock.base.BaseActivity;
import com.smcb.simulatedstock.utils.Constant;

/**
 * Created by Administrator on 2017/3/22.
 */

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //调起交易页面（fragment方式）
//        TradeMainFragment f = new TradeMainFragment();
//        //设置账户密码
//        f.setAccountAndPossword(getApplicationContext(),Constant.USERNAME,Constant.USERPWD);
//        getSupportFragmentManager().beginTransaction().add(android.R.id.content,f).commitAllowingStateLoss();

//        //-----------------------------------------------------------------------------------------------------------
//        //调起交易页面（activiy方式）
//        Intent it = new Intent();
//        it.setClass(this,TradeMainActivity.class);
//        //设置账户密码
//        it.putExtra(TradeMainActivity.USER_ACCOUNT,Constant.USERNAME);
//        it.putExtra(TradeMainActivity.USER_PWD,Constant.USERPWD);
//        startActivity(it);
//        finish();

        //-----------------------------------------------------------------------------------------------------------
        //调起交易页面（activiy方式）
        Intent it = new Intent();
        it.setClass(this,TradeMainActivity.class);
        //设置账户密码
        it.putExtra(TradeMainActivity.USER_ACCOUNT,Constant.USERNAME);
        it.putExtra(TradeMainActivity.USER_PWD,Constant.USERPWD);
        startActivity(it);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (count > 0) {
                getSupportFragmentManager().popBackStack();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}

package com.smcb.simulatedstock.page;

import com.smcb.simulatedstock.base.BaseFragment;

/**
 * Created by Administrator on 2017/3/10.
 */

public class SellStockFragment extends BaseFragment {
    public static BaseFragment getInstance() {
        BuyStockFragment fragment = new BuyStockFragment();
        fragment.INDEX = 2;
        fragment.type = BuyStockFragment.SELL;
        return fragment;
    }
}

package com.smcb.simulatedstock.adapter;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.smcb.simulatedstock.R;
import com.smcb.simulatedstock.data.entity.MarkEntity;
import com.smcb.simulatedstock.library.commonadapter.item.AdapterItem;


/**
 * Created by Administrator on 2017/3/15.
 */

public class StockItem implements AdapterItem<MarkEntity> {

    private TextView code;
    private TextView name;
    private View view;

    private EditText mStockCode;
    private TextView mStockName;
    private ListView mlistView;

    private View.OnClickListener listener;

    public StockItem(EditText code,TextView name,ListView listView,View.OnClickListener listener) {
        this.mStockCode = code;
        this.mStockName = name;
        this.mlistView = listView;
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.stock_pop_item;
    }

    @Override
    public void bindViews(View view) {
        code = (TextView) view.findViewById(R.id.code);
        name = (TextView) view.findViewById(R.id.name);
        this.view = view;
    }

    @Override
    public void setViews() {
        view.setOnClickListener(listener);
    }

    @Override
    public void handleData(MarkEntity markEntity, int i) {
        code.setText(markEntity.getCODE());
        name.setText(markEntity.getNAME());
        view.setTag(markEntity);
    }
}

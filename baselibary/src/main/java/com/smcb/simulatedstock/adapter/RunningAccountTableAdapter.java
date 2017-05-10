package com.smcb.simulatedstock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smcb.simulatedstock.R;
import com.smcb.simulatedstock.library.fixtabheader.adapters.BaseTableAdapter;
import com.smcb.simulatedstock.utils.DataUtil;
import com.smcb.tl.entity.BillEntity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import static com.smcb.simulatedstock.utils.DataUtil.val2w;

public class RunningAccountTableAdapter extends BaseTableAdapter {
	private final Context context;
	private final LayoutInflater inflater;

	private String[] titles = {"交易日期","证券代码","成交价格","成交数量","业务名称","发生金额","剩余金额","股东代码","币种","备注"};

	private List<BillEntity> mList;

	public RunningAccountTableAdapter(Context context, List<BillEntity> list) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.mList = list;
	}

	public Context getContext() {
		return context;
	}

	public LayoutInflater getInflater() {
		return inflater;
	}

	@Override
	public View getView(int row, int column, View converView, ViewGroup parent) {
		if (converView == null) {
			converView = inflater.inflate(getLayoutResource(row, column), parent, false);
			AutoUtils.auto(converView);
		}
		row+=1;
		column+=1;
		if (row == 0) {
			TextView tx1 = (TextView) converView.findViewById(android.R.id.text1);
			tx1.setText(titles[column]);
		} else {
			TextView tx1 = (TextView) converView.findViewById(android.R.id.text1);
			TextView tx2 = (TextView) converView.findViewById(android.R.id.text2);

			BillEntity item = mList.get(row);
			switch (column) {
				case 0:
					tx1.setText(item.getJYDA());
					tx2.setVisibility(View.GONE);
					break;
				case 1:
					tx1.setText(item.getZQDM());
					tx2.setText(item.getZQMC());
					tx2.setVisibility(View.VISIBLE);
					break;
				case 2:
					tx1.setText(DataUtil.parseDouble(item.getCJJG(),2));
					tx2.setVisibility(View.GONE);
					break;
				case 3:
					tx1.setText(item.getFSSL());
					tx2.setVisibility(View.GONE);
					break;
				case 4:
					tx1.setText(item.getYWMC());
					tx2.setVisibility(View.GONE);
					break;
				case 5:
					tx1.setText(val2w(item.getFSJE(),2));
					tx2.setVisibility(View.GONE);
					break;
				case 6:
					tx1.setText(val2w(item.getBCYE(),2));
					tx2.setVisibility(View.GONE);
					break;
				case 7:
					tx1.setText(item.getGDDM());
					tx2.setVisibility(View.GONE);
					break;
				case 8:
					tx1.setText("人民币");
					tx2.setVisibility(View.GONE);
					break;
				case 9:
					tx1.setText(item.getBEIZ());
					tx2.setVisibility(View.GONE);
					break;
			}
		}
		return converView;
	}

	@Override
	public int getRowCount() {
		return mList.size()-1;
	}

	@Override
	public int getColumnCount() {
		return 9;
	}

	@Override
	public int getWidth(int column) {
		return AutoUtils.getPercentWidthSize(270);
	}

	@Override
	public int getHeight(int row) {
		if (row == -1) {
			return AutoUtils.getPercentWidthSize(100);
		} else {
			return AutoUtils.getPercentWidthSize(150);
		}
	}


	public int getLayoutResource(int row, int column) {
		final int layoutResource;
		switch (getItemViewType(row, column)) {
			case 0:
				layoutResource = R.layout.item_table1_header;
				break;
			case 1:
				layoutResource = R.layout.item_table1;
				break;
			default:
				throw new RuntimeException("wtf?");
		}
		return layoutResource;
	}

	@Override
	public int getItemViewType(int row, int column) {
		if (row < 0) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}
}

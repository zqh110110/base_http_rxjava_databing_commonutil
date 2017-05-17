package com.mcxtzhang.commonadapter.databinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mcxtzhang.commonadapter.BR;

import java.util.ArrayList;
import java.util.List;


/**
 * 介绍：普通Adapter
 * 泛型没有特殊需求可以不传
 * 泛型D:是Bean类型，如果有就传。
 * 泛型B:是对应的xml Layout的Binding类
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 16/09/25.
 */

public class BaseBindingLvGvAdapter<D, B extends ViewDataBinding> extends BaseAdapter {
    protected Context mContext;
    protected int mLayoutId;
    protected List<D> mDatas;
    protected LayoutInflater mInfalter;
    //用于设置Item的事件Presenter
    protected Object ItemPresenter;

    public BaseBindingLvGvAdapter(Context mContext, List mDatas, int mLayoutId) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        this.mDatas = mDatas;
        this.mInfalter = LayoutInflater.from(mContext);
    }

    public BaseBindingLvGvAdapter(Context mContext, List mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mInfalter = LayoutInflater.from(mContext);
    }

    public BaseBindingLvGvVH<B> onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseBindingLvGvVH<B> holder = new BaseBindingLvGvVH<B>((B) DataBindingUtil.inflate(mInfalter, mLayoutId, parent, false));
        onCreateViewHolder(holder);
        return holder;
    }

    /**
     * 如果需要给Vh设置监听器啥的 可以在这里
     *
     * @param holder
     */
    public void onCreateViewHolder(BaseBindingLvGvVH<B> holder) {

    }

    /**
     * 子类除了绑定数据，还要设置监听器等其他操作。
     * 可以重写这个方法，不要删掉super.onBindViewHolder(holder, position);
     *
     * @param holder
     * @param position
     */
    public void onBindViewHolder(BaseBindingLvGvVH<B> holder, int position) {
        holder.getBinding().setVariable(BR.data, mDatas.get(position));
        holder.getBinding().setVariable(BR.itemP, ItemPresenter);
        holder.getBinding().executePendingBindings();
    }

    public int getItemCount() {
        return null != mDatas ? mDatas.size() : 0;
    }

    public Object getItemPresenter() {
        return ItemPresenter;
    }

    /**
     * 用于设置Item的事件Presenter
     *
     * @param itemPresenter
     * @return
     */
    public BaseBindingLvGvAdapter setItemPresenter(Object itemPresenter) {
        ItemPresenter = itemPresenter;
        return this;
    }

    /**
     * 刷新数据，初始化数据
     *
     * @param list
     */
    public void setDatas(List<D> list) {
        if (this.mDatas != null) {
            if (null != list) {
                List<D> temp = new ArrayList<D>();
                temp.addAll(list);
                this.mDatas.clear();
                this.mDatas.addAll(temp);
            } else {
                this.mDatas.clear();
            }
        } else {
            this.mDatas = list;
        }
        notifyDataSetChanged();
    }

    /**
     * 删除一条数据
     * 会自动定向刷新
     *
     * @param i
     */
    public void remove(int i) {
        if (null != mDatas && mDatas.size() > i && i > -1) {
            mDatas.remove(i);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加一条数据 至队尾
     * 会自动定向刷新
     *
     * @param data
     */
    public void add(D data) {
        if (data != null && mDatas != null) {
            mDatas.add(data);
            notifyDataSetChanged();
        }
    }

    /**
     * 在指定位置添加一条数据
     * 会自动定向刷新
     * <p>
     * 如果指定位置越界，则添加在队尾
     *
     * @param position
     * @param data
     */
    public void add(int position, D data) {
        if (data != null && mDatas != null) {
            if (mDatas.size() > position && position > -1) {
                mDatas.add(position, data);
            } else {
                add(data);
            }
            notifyDataSetChanged();
        }
    }


    /**
     * 加载更多数据
     *
     * @param list
     */
    public void addDatas(List<D> list) {
        if (null != list) {
            List<D> temp = new ArrayList<D>();
            temp.addAll(list);
            if (this.mDatas != null) {
                this.mDatas.addAll(temp);
            } else {
                this.mDatas = temp;
            }
            notifyDataSetChanged();
        }

    }


    public List<D> getDatas() {
        return mDatas;
    }

    @Override
    public int getCount() {
        return getItemCount();
    }

    @Override
    public Object getItem(int position) {
        return null != mDatas ? mDatas.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            BaseBindingLvGvVH<B> bBaseBindingVH = onCreateViewHolder(parent, getItemViewType(position));
            convertView = bBaseBindingVH.getBinding().getRoot();
            convertView.setTag(bBaseBindingVH);
        }
        BaseBindingLvGvVH<B> bBaseBindingVH = (BaseBindingLvGvVH<B>) convertView.getTag();
        onBindViewHolder(bBaseBindingVH,position);
        return convertView;
    }

}

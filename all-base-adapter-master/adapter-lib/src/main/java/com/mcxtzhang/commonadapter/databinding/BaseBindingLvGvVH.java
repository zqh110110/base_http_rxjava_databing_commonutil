package com.mcxtzhang.commonadapter.databinding;

import android.databinding.ViewDataBinding;

/**
 * 介绍：使用DataBinding ，告别ViewHolder
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 16/09/25.
 */

public class BaseBindingLvGvVH<T extends ViewDataBinding>  {
    protected final T mBinding;

    public BaseBindingLvGvVH(T t) {
        mBinding = t;
    }

    public T getBinding() {
        return mBinding;
    }
}

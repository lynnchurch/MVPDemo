package me.lynnchurch.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.autolayout.utils.AutoUtils;

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {
    private View mItemView;

    public BaseHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        AutoUtils.autoSize(itemView);//适配
    }

    public View getItemView() {
        return mItemView;
    }

    /**
     * 设置数据
     *
     * @param
     */
    public abstract void setData(T data);
}

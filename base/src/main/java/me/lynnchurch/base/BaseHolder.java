package me.lynnchurch.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.autolayout.utils.AutoUtils;

import me.lynnchurch.base.utils.ButterKnifeUtil;

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {
    private View mItemView;

    public BaseHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        AutoUtils.autoSize(itemView);
        ButterKnifeUtil.bindTarget(this, itemView);
    }

    public View getItemView() {
        return mItemView;
    }

    public abstract void setData(T data);
}

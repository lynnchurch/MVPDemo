package me.lynnchurch.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class DefaultAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>> {
    protected List<T> mData;
    protected OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private BaseHolder<T> mHolder;

    public DefaultAdapter(List<T> data) {
        super();
        this.mData = data;
    }

    /**
     * 创建Hodler
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BaseHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        mHolder = getHolder(view);
        return mHolder;
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseHolder<T> holder, final int position) {
        holder.setData(mData.get(position));
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, mData.get(position), position);
                }
            }
        });
    }

    /**
     * 数据的个数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }


    public List<T> getData() {
        return mData;
    }

    /**
     * 获得item的数据
     *
     * @param position
     * @return
     */
    public T getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    /**
     * 子类实现提供holder
     *
     * @param v
     * @return
     */
    public abstract BaseHolder<T> getHolder(View v);

    /**
     * 提供Item的布局
     *
     * @return
     */
    public abstract int getLayoutId();


    public interface OnRecyclerViewItemClickListener<T> {
        void onItemClick(View view, T data, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}

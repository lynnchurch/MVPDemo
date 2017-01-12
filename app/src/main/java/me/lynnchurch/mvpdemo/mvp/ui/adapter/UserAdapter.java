package me.lynnchurch.mvpdemo.mvp.ui.adapter;

import android.view.View;

import java.util.List;

import me.lynnchurch.base.BaseHolder;
import me.lynnchurch.base.DefaultAdapter;
import me.lynnchurch.mvpdemo.R;
import me.lynnchurch.mvpdemo.mvp.model.bean.User;
import me.lynnchurch.mvpdemo.mvp.ui.holder.UserItemHolder;

public class UserAdapter extends DefaultAdapter<User> {
    public UserAdapter(List<User> data) {
        super(data);
    }

    @Override
    public BaseHolder<User> getHolder(View v) {
        return new UserItemHolder(v);
    }

    @Override
    public int getLayoutId() {
        return R.layout.recycle_list;
    }
}

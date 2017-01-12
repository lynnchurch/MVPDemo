package me.lynnchurch.base.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ButterKnifeUtil {
    public static Unbinder bindTarget(Object target, Object source) {
        if (source instanceof Activity) {
            return ButterKnife.bind(target, (Activity) source);
        } else if (source instanceof View) {
            return ButterKnife.bind(target, (View) source);
        } else if (source instanceof Dialog) {
            return ButterKnife.bind(target, (Dialog) source);
        } else {
            return Unbinder.EMPTY;
        }
    }
}

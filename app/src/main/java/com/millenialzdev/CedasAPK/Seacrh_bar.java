package com.millenialzdev.CedasAPK;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class Seacrh_bar extends LinearLayout {
    public Seacrh_bar(Context context) {
        super(context);
    }

    public Seacrh_bar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setGravity(Gravity.CENTER);
    }

    public Seacrh_bar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Seacrh_bar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}

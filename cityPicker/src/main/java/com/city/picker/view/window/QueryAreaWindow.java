package com.city.picker.view.window;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.city.picker.R;

public class QueryAreaWindow  extends PopupWindow {

    private Context mContext;

    public QueryAreaWindow(Context context) {
        super(context);
        this.mContext = context;

        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View contentView = LayoutInflater.from(context).inflate(R.layout.popup_query_area,
                null, false);
        setContentView(contentView);
    }
}

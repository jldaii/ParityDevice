package com.city.picker.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.city.picker.R;
import com.parity.datapersistence.dbBean.AreaMsgDbBean;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class HotCityGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<AreaMsgDbBean> mHotCities;


    public HotCityGridAdapter(Context context,List<AreaMsgDbBean> hotCities) {
        this.mContext = context;
        this.mHotCities = hotCities;
    }



    /**
     * 获取app的版本号
     *
     * @return 返回当前的版本号
     */
     private int getVersionCode() {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getCount() {
        return mHotCities == null ? 0 : mHotCities.size();
    }

    @Override
    public AreaMsgDbBean getItem(int position) {
        return mHotCities == null ? null : mHotCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        HotCityViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.cp_item_hot_city_gridview, parent, false);
            holder = new HotCityViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_hot_city_name);
            view.setTag(holder);
        }else{
            holder = (HotCityViewHolder) view.getTag();
        }
        holder.name.setText(mHotCities.get(position).getPlaceName());
        return view;
    }

    public static class HotCityViewHolder{
        TextView name;
    }
}

package com.city.picker.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.city.picker.R;
import com.parity.datapersistence.dbBean.AreaMsgDbBean;


import java.util.List;


public class ResultListAdapter extends BaseAdapter {
    private Context mContext;
    private List<AreaMsgDbBean> mCities;
    private String mKeyWord;

    public ResultListAdapter(Context context, List<AreaMsgDbBean> cities) {
        this.mCities = cities;
        this.mContext = context;
    }

    public void changeData(List<AreaMsgDbBean> list,String keyWord){
//        if (mCities == null){
//            mCities = list;
//        }else{
//            mCities.clear();
//            mCities.addAll(list);
//        }
        mCities = list;
        mKeyWord= keyWord;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public AreaMsgDbBean getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ResultViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.cp_item_search_result_listview, parent, false);
            holder = new ResultViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_item_result_listview_name);
            view.setTag(holder);
        }else{
            holder = (ResultViewHolder) view.getTag();
        }
        /**
         * 替换字体颜色
         */
        String newText = mCities.get(position).getPlaceName().replace(mKeyWord,String.format("<font color='#FF0000'>%s</font>",mKeyWord));
        holder.name.setText(Html.fromHtml(newText));
        return view;
    }

    public static class ResultViewHolder{
        TextView name;
    }
}

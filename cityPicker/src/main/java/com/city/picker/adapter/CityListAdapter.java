package com.city.picker.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.city.picker.R;
import com.city.picker.view.activity.CityPickerActivity;
import com.corelibs.utils.MyShareUtil;
import com.corelibs.utils.PinyinUtils;
import com.corelibs.views.WrapHeightGridView;
import com.parity.datapersistence.constant.DataConstants;
import com.parity.datapersistence.dbBean.AreaMsgDbBean;
import com.parity.datapersistence.model.LocateState;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CityListAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_COUNT = 2;

    private Context mContext;
    private LayoutInflater inflater;
    private List<AreaMsgDbBean> mCities;
    private List<AreaMsgDbBean> mHotCitys;
    private HashMap<String, Integer> letterIndexes;
    private String[] sections;
    private OnCityClickListener onCityClickListener;
    private int locateState = LocateState.LOCATING;
    private String locatedCity;
    private  boolean ifin=true;
    private String mType;

    public CityListAdapter(Context mContext, List<AreaMsgDbBean> mCities,List<AreaMsgDbBean> hotCitys,String type) {
        this.mContext = mContext;
        this.mCities = mCities;
        this.mHotCitys = hotCitys;
        this.mType = type;
        this.inflater = LayoutInflater.from(mContext);
        if (mCities == null){
            mCities = new ArrayList<>();
        }
        mCities.add(0, new AreaMsgDbBean("热门", "0"));
        int size = mCities.size();
        letterIndexes = new HashMap<>();
        sections = new String[size];
        for (int index = 0; index < size; index++){
            //当前城市拼音首字母
            String currentLetter = PinyinUtils.getFirstLetter(mCities.get(index).getPlaceNamePinYin());
            //上个首字母，如果不存在设为""
            String previousLetter = index >= 1 ? PinyinUtils.getFirstLetter(mCities.get(index - 1).getPlaceNamePinYin()) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)){
                letterIndexes.put(currentLetter, index);
                sections[index] = currentLetter;
            }
        }
    }

    /**
     * 更新定位状态
     * @param state
     */
    public void updateLocateState(int state, String city){
        this.locateState = state;
        this.locatedCity = city;
        notifyDataSetChanged();
    }

    /**
     * 获取字母索引的位置
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter){
        Integer integer = letterIndexes.get(letter);
        return integer == null ? -1 : integer;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position < VIEW_TYPE_COUNT - 1 ? position : VIEW_TYPE_COUNT - 1;
    }

    @Override
    public int getCount() {
        return mCities == null ? 0: mCities.size();
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
    public View getView(final int position, View view, ViewGroup parent) {
        CityViewHolder holder;
        int viewType = getItemViewType(position);
        switch (viewType){
            case 0:     //热门
                view = inflater.inflate(R.layout.cp_view_hot_city, parent, false);
                WrapHeightGridView gridView = (WrapHeightGridView) view.findViewById(R.id.gridview_hot_city);
                TextView tvHot = view.findViewById(R.id.txt_hot);
                if (TextUtils.equals(DataConstants.AreaDataConstant.TYPE_FROM,mType)){
                    tvHot.setText("热门省市");
                }else {
                    tvHot.setText("热门国家");
                }
                final HotCityGridAdapter hotCityGridAdapter = new HotCityGridAdapter(mContext,mHotCitys);
                gridView.setAdapter(hotCityGridAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (onCityClickListener != null){
                            onCityClickListener.onCityClick(hotCityGridAdapter.getItem(position).getPlaceName());
                        }
                    }
                });
                break;
            case 1:     //所有
                if (view == null){
                    view = inflater.inflate(R.layout.cp_item_city_listview, parent, false);
                    holder = new CityViewHolder();
                    holder.letter = (TextView) view.findViewById(R.id.tv_item_city_listview_letter);
                    holder.name = (TextView) view.findViewById(R.id.tv_item_city_listview_name);
                    view.setTag(holder);
                }else{
                    holder = (CityViewHolder) view.getTag();
                }
                if (CityPickerActivity.HIDE_ALL_CITY) {
                    view.setVisibility(View.GONE);
                }
                if (position >= 1){
                    final String city = mCities.get(position).getPlaceName();
                    holder.name.setText(city);
                    String currentLetter = PinyinUtils.getFirstLetter(mCities.get(position).getPlaceNamePinYin());
                    String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(mCities.get(position - 1).getPlaceNamePinYin()) : "";
                    if (!TextUtils.equals(currentLetter, previousLetter)){
                        holder.letter.setVisibility(View.VISIBLE);
                        holder.letter.setText(currentLetter);
                    }else{
                        holder.letter.setVisibility(View.GONE);
                    }
                    holder.name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onCityClickListener != null){
                                onCityClickListener.onCityClick(city);
                            }
                        }
                    });
                }
                break;
        }
        return view;
    }

    public List<String> getifinCities(Context context, String mycity){
        List<String> cityList=new ArrayList<String>();

        String cityString = MyShareUtil.getSharedString(context, "cityList");
        try {
            JSONArray jsonArray=new JSONArray(cityString);
            for (int i = 0; i < jsonArray.length(); i++) {
                cityList.add(jsonArray.get(i).toString());
                if (jsonArray.get(i).toString().contains(mycity)){
                    ifin = true;
                    break;
                }else {
                    ifin = false;
                }
            }
            return cityList;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static class CityViewHolder{
        TextView letter;
        TextView name;
    }

    public void setOnCityClickListener(OnCityClickListener listener){
        this.onCityClickListener = listener;
    }

    public interface OnCityClickListener{
        void onCityClick(String name);
        void onLocateClick();
    }
}

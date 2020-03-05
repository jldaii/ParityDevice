package com.city.picker.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.city.picker.R;
import com.city.picker.adapter.CityListAdapter;
import com.city.picker.adapter.ResultListAdapter;
import com.city.picker.view.CheckPermissionsListener;
import com.city.picker.view.widget.SideLetterBar;
import com.corelibs.utils.StatusBarUtil;
import com.corelibs.widget.NavBar;
import com.parity.datapersistence.constant.DataConstants;

import com.parity.datapersistence.dbBean.AreaMsgDbBean;
import com.parity.datapersistence.model.LocateState;
import com.parity.datapersistence.requestBean.AreaDataRequestBean;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * 城市选择
 */
public class CityPickerActivity extends CheckPermissionsActivity implements View.OnClickListener, CheckPermissionsListener {
    public static final String KEY_PICKED_CITY = "picked_city";
    public static final String KEY_PICKED_TYPE = "picked_type";

    private LinearLayout mSearchLayout;
    private LinearLayout mLeftLayout;
    private ListView mListView;
    private ListView mResultListView;
    private SideLetterBar mLetterBar;
    private EditText searchBox;
    private ImageView clearBtn;
    private TextView cancelBtn;
    private ViewGroup emptyView;
    // 标题栏
    private NavBar navBar;

    private EditText edtQueryArea;

    private ImageView imgDelInput;

    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private List<AreaMsgDbBean> mAllCities;
    private List<AreaMsgDbBean> mResultCities;
//    private DBManager dbManager;

    public static final Boolean HIDE_ALL_CITY = false;

//    private AMapLocationClient mLocationClient;

    private AreaDataRequestBean mAreaDataRequestBean;

    private String mType;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cp_activity_city_list);
        setTranslucentStatusBar();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        StatusBarUtil.StatusBarLightMode(this);
//        setStatusBar(this.getResources().getColor(R.color.businesstop));
        initData();
        initView();
        //请求权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
//            mLocationClient.startLocation();
        }else {
            requestPermissions(this, neededPermissions, this);
        }
    }


    /**
     *
     */
    private void initData() {

        getDataFormIntent();
        /**
         * 这里要去做区分了
         * 根据 出发地  目的地进行区分查询数据
         * 给到列表填充显示的数据 应该就区分开
         * CityListAdapter 包含了热门 所以 在给它 出发或者目的地的时候 一并要给出热门的出发地或者目的地
         */
        if (TextUtils.equals(DataConstants.AreaDataConstant.TYPE_FROM,mType)){
            mCityAdapter = new CityListAdapter(this, mAreaDataRequestBean.getFrom(),mAreaDataRequestBean.getHot_from());
        }else {
            mCityAdapter = new CityListAdapter(this, mAreaDataRequestBean.getTo(),mAreaDataRequestBean.getHot_to());
        }
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                backWithData(name);
            }

            @Override
            public void onLocateClick() {
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
                requestPermissions(CityPickerActivity.this, neededPermissions, CityPickerActivity.this);
            }
        });

        mResultAdapter = new ResultListAdapter(this, null);
    }

    private void getDataFormIntent() {
        Intent data = getIntent();
        mType = data.getStringExtra("type");
        mAreaDataRequestBean = (AreaDataRequestBean) data.getSerializableExtra("bean");
    }

    private void initView() {
        navBar = findViewById(R.id.nav);

        navBar.setTitle(this.getString(R.string.txt_International_price_comparator));
        navBar.showBack();
        edtQueryArea = (EditText) findViewById(R.id.edt_query_area);
        mListView = (ListView) findViewById(R.id.listview_all_city);
        mListView.setAdapter(mCityAdapter);
        mSearchLayout = (LinearLayout) findViewById(R.id.search_layout);
        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        imgDelInput = (ImageView) findViewById(R.id.img_del_input);

        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                if (position<0){
                    position = 0;
                }
                mListView.setSelection(position);
            }
        });
        imgDelInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtQueryArea.setText("");
            }
        });
        searchBox = (EditText) findViewById(R.id.et_search);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
//                    mResultCities = dbManager.searchCity(keyword);
                    if (mResultCities == null || mResultCities.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(mResultCities,keyword);
                    }
                }
            }
        });

        edtQueryArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyWord = s.toString();
                if (TextUtils.isEmpty(keyWord)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                    imgDelInput.setVisibility(View.INVISIBLE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    imgDelInput.setVisibility(View.VISIBLE);
                    if (mAreaDataRequestBean == null) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        if (TextUtils.equals(mType, DataConstants.AreaDataConstant.TYPE_FROM)){
                            if (null!=mAreaDataRequestBean.getFrom() && !mAreaDataRequestBean.getFrom().isEmpty()){
                                List<AreaMsgDbBean> fromList = new ArrayList<>();
                                for (AreaMsgDbBean dbBean : mAreaDataRequestBean.getFrom()) {
                                    if (dbBean.getPlaceName().contains(keyWord)){
                                        fromList.add(dbBean);
                                    }
                                }
                                if (!fromList.isEmpty()){
                                    mResultAdapter.changeData(fromList,keyWord);
                                }else {
                                    emptyView.setVisibility(View.VISIBLE);
                                }
                            }
                        }else {
                            if (null!=mAreaDataRequestBean.getTo() && !mAreaDataRequestBean.getTo().isEmpty()){
                                List<AreaMsgDbBean> toList = new ArrayList<>();
                                for (AreaMsgDbBean dbBean : mAreaDataRequestBean.getTo()) {
                                    if (dbBean.getPlaceName().contains(keyWord)){
                                        toList.add(dbBean);
                                    }
                                }
                                if (!toList.isEmpty()){
                                    mResultAdapter.changeData(toList,keyWord);
                                }else {
                                    emptyView.setVisibility(View.VISIBLE);
                                }

                            }
                        }
                    }
                }
            }
        });

        emptyView = (ViewGroup) findViewById(R.id.empty_view);
        mResultListView = (ListView) findViewById(R.id.listview_search_result);
        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                backWithData(mResultAdapter.getItem(position).getPlaceName());
            }
        });

        clearBtn = (ImageView) findViewById(R.id.iv_search_clear);
        cancelBtn = (TextView) findViewById(R.id.tv_search_cancel);

        clearBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        if (HIDE_ALL_CITY) {
            mSearchLayout.setVisibility(View.GONE);
            mLetterBar.setVisibility(View.GONE);
        }
    }

    private void backWithData(String city){
        Intent data = new Intent();
        data.putExtra(KEY_PICKED_CITY, city);
        data.putExtra(KEY_PICKED_TYPE, mType);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_search_clear) {
            searchBox.setText("");
            clearBtn.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
//            mResultListView.setVisibility(View.GONE);
            mResultCities = null;
        } else if (i == R.id.tv_search_cancel) {
            finish();
        }
    }

    @Override
    public void onGranted() {
//        mLocationClient.startLocation();
    }

    @Override
    public void onDenied(List<String> permissions) {
//        Toast.makeText(this, "权限被禁用，请到设置里打开", Toast.LENGTH_SHORT).show();
        mCityAdapter.updateLocateState(LocateState.FAILED, null);
    }

    /**
     * 设置全屏模式，并将状态栏设置为透明，支持4.4及以上系统
     */
    public void setTranslucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

            setFullScreen();
        }
    }

    /**
     * 设置全屏模式
     */
    public void setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }
}

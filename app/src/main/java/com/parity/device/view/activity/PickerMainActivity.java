package com.parity.device.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.city.picker.view.activity.CityPickerActivity;
import com.corelibs.base.BaseActivity;
import com.corelibs.utils.ToastMgr;
import com.corelibs.widget.NavBar;
import com.parity.datapersistence.constant.DataConstants;
import com.parity.datapersistence.requestBean.AreaDataRequestBean;
import com.parity.device.R;
import com.parity.device.presenter.PickerMainPresenter;
import com.parity.device.view.interfaces.PickerMainView;

import butterknife.BindView;
import butterknife.OnClick;

public class PickerMainActivity extends BaseActivity<PickerMainView, PickerMainPresenter> implements PickerMainView {


    @BindView(R.id.nav)
    NavBar nav;
    @BindView(R.id.edt_place_from)
    TextView edtPlaceFrom;
    @BindView(R.id.liny_from)
    LinearLayout linyFrom;
    @BindView(R.id.edt_place_to)
    TextView edtPlaceTo;
    @BindView(R.id.liny_to)
    LinearLayout linyTo;
    @BindView(R.id.rb_goods)
    RadioButton rbGoods;
    @BindView(R.id.rb_file)
    RadioButton rbFile;
    @BindView(R.id.rg_goods_type)
    RadioGroup rgGoodsType;
    @BindView(R.id.txt_kg)
    TextView txtKg;
    @BindView(R.id.edt_goods_weight)
    EditText edtGoodsWeight;
    @BindView(R.id.btn_to_query)
    Button btnToQuery;

    AreaDataRequestBean mAreaDataRequestBean;

    private static final int REQUEST_CODE_PICK_CITY = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picker_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initNav();
    }

    private void initNav() {
        nav.setTitle(this.getString(R.string.txt_International_price_comparator));
        nav.showBack();
    }

    @Override
    protected PickerMainPresenter createPresenter() {
        return new PickerMainPresenter();
    }


    // 返回值处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK){
            if (data != null){
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                String type = data.getStringExtra(CityPickerActivity.KEY_PICKED_TYPE);

                if (TextUtils.equals(DataConstants.AreaDataConstant.TYPE_FROM,type)){
                    edtPlaceFrom.setText(city);
                }else {
                    edtPlaceTo.setText(city);
                }

            }
        }

    }



    @OnClick({R.id.liny_from, R.id.liny_to, R.id.rb_goods, R.id.rb_file, R.id.btn_to_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 出发地

            case R.id.liny_from:
               if (mAreaDataRequestBean == null){
                   presenter.getAreaDatas(DataConstants.AreaDataConstant.TYPE_FROM);
               }else {
                   toAreaPicker(DataConstants.AreaDataConstant.TYPE_FROM,mAreaDataRequestBean);
               }
                break;
                // 目的地
            case R.id.liny_to:
                if (mAreaDataRequestBean == null){
                    presenter.getAreaDatas(DataConstants.AreaDataConstant.TYPE_TO);
                }else {
                    toAreaPicker(DataConstants.AreaDataConstant.TYPE_TO,mAreaDataRequestBean);
                }
                break;
                // 包裹
            case R.id.rb_goods:
                break;
                // 文件
            case R.id.rb_file:
                break;

                // 查询
            case R.id.btn_to_query:
                if (TextUtils.isEmpty(edtGoodsWeight.getText().toString())){
                    ToastMgr.show("请输入重量");
                    return;
                }
                break;
        }
    }

    /**
     * 去选择地区的界面
     * 要传入类型
     * from 去选择出发地
     * to 去选择目的地
     * @param type
     */
    @Override
    public void toAreaPicker(String type, AreaDataRequestBean areaDataRequestBean){
        mAreaDataRequestBean = areaDataRequestBean;
        Intent intent = new Intent();
        intent.putExtra("bean",areaDataRequestBean);
        intent.putExtra("type",type);
        intent.setClass(this, CityPickerActivity.class);
        startActivityForResult(intent,REQUEST_CODE_PICK_CITY);
    }
}

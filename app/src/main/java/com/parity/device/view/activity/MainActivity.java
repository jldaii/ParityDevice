package com.parity.device.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.city.picker.view.activity.CityPickerActivity;
import com.corelibs.utils.ToastMgr;
import com.google.gson.Gson;
import com.parity.datapersistence.dao.AreaMsgDao;
import com.parity.datapersistence.dbBean.AreaMsgDbBean;
import com.parity.datapersistence.requestBean.Te;
import com.parity.device.R;
import com.city.picker.json.AreaJsonMsg;
import com.corelibs.utils.PinyinUtils;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Button btnData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        btnData = findViewById(R.id.btn_data);

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                        // 这里取把数据存储到数据库里面去
                        // 然后再跳转到对应界面进行处理
                        Te te = new Gson().fromJson(AreaJsonMsg.json,Te.class);
                        if (null!=te && null!=te.getObj()){
                            if (null!=te.getObj().getFrom() && !te.getObj().getFrom().isEmpty()){
                                for (AreaMsgDbBean areaMsgDbBean : te.getObj().getFrom()) {
                                    // 获取拼音
                                    areaMsgDbBean.setPlaceNamePinYin(PinyinUtils.hzToPinYin(areaMsgDbBean.getPlaceName()));
                                    AreaMsgDao.getInstance(MainActivity.this).add(areaMsgDbBean);
                                }
                            }
                        }

                        if (null!=te && null!=te.getObj()){
                            if (null!=te.getObj().getHot_from() && !te.getObj().getHot_from().isEmpty()){
                                for (AreaMsgDbBean areaMsgDbBean : te.getObj().getHot_from()) {
                                    // 获取拼音
                                    areaMsgDbBean.setPlaceNamePinYin(PinyinUtils.hzToPinYin(areaMsgDbBean.getPlaceName()));
                                    AreaMsgDao.getInstance(MainActivity.this).add(areaMsgDbBean);
                                }
                            }
                        }

                        if (null!=te && null!=te.getObj()){
                            if (null!=te.getObj().getHot_to() && !te.getObj().getHot_to().isEmpty()){
                                for (AreaMsgDbBean areaMsgDbBean : te.getObj().getHot_to()) {
                                    // 获取拼音
                                    areaMsgDbBean.setPlaceNamePinYin(PinyinUtils.hzToPinYin(areaMsgDbBean.getPlaceName()));
                                    AreaMsgDao.getInstance(MainActivity.this).add(areaMsgDbBean);
                                }
                            }
                        }

                        if (null!=te && null!=te.getObj()){
                            if (null!=te.getObj().getTo() && !te.getObj().getTo().isEmpty()){
                                for (AreaMsgDbBean areaMsgDbBean : te.getObj().getTo()) {
                                    // 获取拼音
                                    areaMsgDbBean.setPlaceNamePinYin(PinyinUtils.hzToPinYin(areaMsgDbBean.getPlaceName()));
                                    AreaMsgDao.getInstance(MainActivity.this).add(areaMsgDbBean);
                                }
                            }
                        }

                        emitter.onNext(1);

                    }
                }).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        ToastMgr.show(String.valueOf(integer));
                        startActivity(new Intent(MainActivity.this, CityPickerActivity.class));


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });
    }


}

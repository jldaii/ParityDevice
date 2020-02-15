package com.parity.device.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.city.picker.json.AreaJsonMsg;
import com.corelibs.base.BaseActivity;
import com.corelibs.utils.PinyinUtils;
import com.corelibs.widget.NavBar;
import com.google.gson.Gson;
import com.parity.datapersistence.dao.AreaMsgDao;
import com.parity.datapersistence.dbBean.AreaMsgDbBean;
import com.parity.datapersistence.requestBean.Te;
import com.parity.device.App;
import com.parity.device.R;
import com.parity.device.presenter.InternationalInformationPresenter;
import com.parity.device.view.interfaces.InternationalInformationView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class InternationalInformationActivity extends BaseActivity<InternationalInformationView, InternationalInformationPresenter> implements InternationalInformationView {

    @BindView(R.id.nav)
    NavBar nav;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.rl_service)
    RelativeLayout rlService;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.rl_International_price_comparator)
    RelativeLayout rlInternationalPriceComparator;
    @BindView(R.id.iv6)
    ImageView iv6;
    @BindView(R.id.rl_robot)
    RelativeLayout rlRobot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_international_information;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initNav();
    }

    private void initNav() {
        nav.setTitle("国际资讯台");
        nav.showBack();
    }

    @Override
    protected InternationalInformationPresenter createPresenter() {
        return null;
    }

    @OnClick({R.id.rl_service, R.id.rl_International_price_comparator, R.id.rl_robot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_service:

                break;
            case R.id.rl_International_price_comparator:
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
                                    AreaMsgDao.getInstance(App.getInstance()).add(areaMsgDbBean);
                                }
                            }
                        }

                        if (null!=te && null!=te.getObj()){
                            if (null!=te.getObj().getHot_from() && !te.getObj().getHot_from().isEmpty()){
                                for (AreaMsgDbBean areaMsgDbBean : te.getObj().getHot_from()) {
                                    // 获取拼音
                                    areaMsgDbBean.setPlaceNamePinYin(PinyinUtils.hzToPinYin(areaMsgDbBean.getPlaceName()));
                                    AreaMsgDao.getInstance(App.getInstance()).add(areaMsgDbBean);
                                }
                            }
                        }

                        if (null!=te && null!=te.getObj()){
                            if (null!=te.getObj().getHot_to() && !te.getObj().getHot_to().isEmpty()){
                                for (AreaMsgDbBean areaMsgDbBean : te.getObj().getHot_to()) {
                                    // 获取拼音
                                    areaMsgDbBean.setPlaceNamePinYin(PinyinUtils.hzToPinYin(areaMsgDbBean.getPlaceName()));
                                    AreaMsgDao.getInstance(App.getInstance()).add(areaMsgDbBean);
                                }
                            }
                        }

                        if (null!=te && null!=te.getObj()){
                            if (null!=te.getObj().getTo() && !te.getObj().getTo().isEmpty()){
                                for (AreaMsgDbBean areaMsgDbBean : te.getObj().getTo()) {
                                    // 获取拼音
                                    areaMsgDbBean.setPlaceNamePinYin(PinyinUtils.hzToPinYin(areaMsgDbBean.getPlaceName()));
                                    AreaMsgDao.getInstance(App.getInstance()).add(areaMsgDbBean);
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
                        startActivity(new Intent(InternationalInformationActivity.this,PickerMainActivity.class));


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

                break;
            case R.id.rl_robot:
                break;
        }
    }
}

package com.parity.device.presenter;


import com.corelibs.base.BasePresenter;
import com.parity.datapersistence.dao.AreaMsgDao;
import com.parity.datapersistence.requestBean.AreaDataRequestBean;
import com.parity.device.App;
import com.parity.device.view.interfaces.PickerMainView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PickerMainPresenter  extends BasePresenter<PickerMainView> {
    @Override
    public void onStart() {

    }

    public void getAreaDatas(String type) {
        view.showLoading();
        Observable.create(new ObservableOnSubscribe<AreaDataRequestBean>() {
            @Override
            public void subscribe(ObservableEmitter<AreaDataRequestBean> emitter) throws Exception {

                AreaDataRequestBean bean = AreaMsgDao.getInstance(App.getInstance()).getAllAndDistinguish();
                emitter.onNext(bean);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<AreaDataRequestBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(AreaDataRequestBean areaDataRequestBean) {
                view.hideLoading();
                view.toAreaPicker(type,areaDataRequestBean);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}

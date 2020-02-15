package com.parity.device.presenter;

import android.annotation.SuppressLint;

import com.corelibs.api.ResponseTransformer;
import com.corelibs.pagination.presenter.ListPagePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.parity.device.model.api.MainApi;
import com.parity.device.model.bean.BaseData;
import com.parity.device.model.bean.Wxarticle;
import com.parity.device.view.interfaces.MainView;

import java.util.List;

public class MainPresenter extends ListPagePresenter<MainView> {

    private MainApi api;

    @Override
    protected void onViewAttach() {
        api = getApi(MainApi.class);
    }

    @Override
    public void onStart() {
        search(true);
    }

    @SuppressLint("CheckResult")
    public void search(final boolean reload) {
        if (!doPagination(reload)) return;
        if (reload) view.showLoading();

        api.getWxArticle()
                .compose(new ResponseTransformer<>())
                .compose(bindToLifeCycle())
                .subscribe(new ResponseSubscriber<BaseData<List<Wxarticle>>>() {
                    @Override
                    public void success(BaseData<List<Wxarticle>> listBaseData) {
                        listBaseData.data.get(0).toString();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });



    }
}

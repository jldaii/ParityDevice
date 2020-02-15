package com.parity.device.view.activity;



import android.graphics.Paint;
import android.os.Bundle;


import com.corelibs.base.BaseActivity;

import com.corelibs.widget.NavBar;

import com.parity.device.R;

import com.parity.device.model.entity.Repository;
import com.parity.device.presenter.MainPresenter;
import com.parity.device.view.interfaces.MainView;


import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {

    @BindView(R.id.nav)
    NavBar nav;



    private Paint paint;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setTitle("main");

        presenter.search(true);

    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onLoadingCompleted() {
        hideLoading();
    }

    @Override
    public void onAllPageLoaded() {

    }

    @Override
    public void renderResult(List<Repository> repositories, boolean reload) {

    }
}

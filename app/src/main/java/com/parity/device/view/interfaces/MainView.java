package com.parity.device.view.interfaces;

import com.corelibs.base.BasePaginationView;
import com.parity.device.model.entity.Repository;


import java.util.List;

public interface MainView extends BasePaginationView {
    void renderResult(List<Repository> repositories, boolean reload);

}

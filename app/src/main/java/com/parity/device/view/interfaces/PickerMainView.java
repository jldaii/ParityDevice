package com.parity.device.view.interfaces;

import com.corelibs.base.BaseView;
import com.parity.datapersistence.requestBean.AreaDataRequestBean;

public interface PickerMainView extends BaseView {


    void toAreaPicker(String type, AreaDataRequestBean areaDataRequestBean);
}

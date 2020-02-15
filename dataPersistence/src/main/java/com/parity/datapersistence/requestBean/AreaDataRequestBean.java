package com.parity.datapersistence.requestBean;

import com.parity.datapersistence.dbBean.AreaMsgDbBean;

import java.io.Serializable;
import java.util.List;

public class AreaDataRequestBean implements Serializable {
    private List<AreaMsgDbBean> hot_from;
    private List<AreaMsgDbBean> hot_to;
    private List<AreaMsgDbBean> from;
    private List<AreaMsgDbBean> to;

    public List<AreaMsgDbBean> getHot_from() {
        return hot_from;
    }

    public void setHot_from(List<AreaMsgDbBean> hot_from) {
        this.hot_from = hot_from;
    }

    public List<AreaMsgDbBean> getHot_to() {
        return hot_to;
    }

    public void setHot_to(List<AreaMsgDbBean> hot_to) {
        this.hot_to = hot_to;
    }

    public List<AreaMsgDbBean> getFrom() {
        return from;
    }

    public void setFrom(List<AreaMsgDbBean> from) {
        this.from = from;
    }

    public List<AreaMsgDbBean> getTo() {
        return to;
    }

    public void setTo(List<AreaMsgDbBean> to) {
        this.to = to;
    }
}

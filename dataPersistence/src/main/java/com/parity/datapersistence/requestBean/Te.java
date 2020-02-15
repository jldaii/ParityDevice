package com.parity.datapersistence.requestBean;


public class Te {


    /**
     * requestId : null
     * success : true
     * business : null
     * errorCode : null
     * errorMessage : null
     * params : null
     * date : null
     * version : null
     * obj : {"hot_from":[{"id":4,"type":"hot_from","placeName":"广东省","status":"1","remark":null,"createTime":null,"modifyTime":null},{"id":5,"type":"hot_from","placeName":"浙江省","status":"1","remark":null,"createTime":null,"modifyTime":null}],"hot_to":[{"id":14,"type":"hot_to","placeName":"新加坡","status":"1","remark":null,"createTime":null,"modifyTime":null},{"id":12,"type":"hot_to","placeName":"日本","status":"1","remark":null,"createTime":null,"modifyTime":null},{"id":13,"type":"hot_to","placeName":"韩国","status":"1","remark":null,"createTime":null,"modifyTime":null}],"from":[{"id":1,"type":"from","placeName":"广东省","status":"1","remark":null,"createTime":null,"modifyTime":null},{"id":2,"type":"from","placeName":"浙江省","status":"1","remark":null,"createTime":null,"modifyTime":null},{"id":3,"type":"from","placeName":"海南省","status":"1","remark":null,"createTime":null,"modifyTime":null}],"to":[{"id":6,"type":"to","placeName":"德国","status":"1","remark":null,"createTime":null,"modifyTime":null},{"id":7,"type":"to","placeName":"法国","status":"1","remark":null,"createTime":null,"modifyTime":null},{"id":8,"type":"to","placeName":"英国","status":"1","remark":null,"createTime":null,"modifyTime":null},{"id":9,"type":"to","placeName":"日本","status":"1","remark":null,"createTime":null,"modifyTime":null},{"id":10,"type":"to","placeName":"新家坡","status":"1","remark":null,"createTime":null,"modifyTime":null},{"id":11,"type":"to","placeName":"韩国","status":"1","remark":null,"createTime":null,"modifyTime":null}]}
     */

    private String requestId;
    private boolean success;
    private String business;
    private String errorCode;
    private String errorMessage;
    private String params;
    private String date;
    private String version;
    private AreaDataRequestBean obj;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public AreaDataRequestBean getObj() {
        return obj;
    }

    public void setObj(AreaDataRequestBean obj) {
        this.obj = obj;
    }

}

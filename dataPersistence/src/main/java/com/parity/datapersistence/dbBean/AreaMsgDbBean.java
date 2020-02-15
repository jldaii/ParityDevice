package com.parity.datapersistence.dbBean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "AreaMsgDbBean")
public class AreaMsgDbBean implements Serializable {

    public AreaMsgDbBean() {
    }

    /**
     * 声明主键
     */
    @DatabaseField(generatedId = true)
    private int index;


    /**
     *  "id": 10
     * type : to
     * placeName : 新家坡
     * status : 1
     * remark : null
     * createTime : null
     * modifyTime : null
     */


    @DatabaseField(canBeNull = false,uniqueCombo = true)
    private String id;

    /**
     * hot_to 热门目的地
     * hot_from 热门出发地
     *
     * to 目的地
     * from 出发地
     */
    @DatabaseField(canBeNull = false,uniqueCombo = true)
    private String type;

    @DatabaseField(canBeNull = false,uniqueCombo = true)
    private String placeName;

    // 拼音 非后台返回数据
    @DatabaseField
    private String placeNamePinYin;

    @DatabaseField
    private String status;

    @DatabaseField
    private String remark;

    @DatabaseField
    private String createTime;

    @DatabaseField
    private String modifyTime;

    public AreaMsgDbBean(String name, String pinyin) {
        placeName = name;
        placeNamePinYin = pinyin;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceNamePinYin() {
        return placeNamePinYin;
    }

    public void setPlaceNamePinYin(String placeNamePinYin) {
        this.placeNamePinYin = placeNamePinYin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}

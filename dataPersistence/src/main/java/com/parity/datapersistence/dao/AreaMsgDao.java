package com.parity.datapersistence.dao;

import android.content.Context;
import android.text.TextUtils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.parity.datapersistence.constant.DataConstants;
import com.parity.datapersistence.dbBean.AreaMsgDbBean;
import com.parity.datapersistence.helper.DatabaseHelper;
import com.parity.datapersistence.requestBean.AreaDataRequestBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AreaMsgDao {
    private Context context;
    private Dao<AreaMsgDbBean,Integer> areaMsgDao;
    private DatabaseHelper helper;

    private static AreaMsgDao mInstange;


    private AreaMsgDao(Context context){
        this.context = context;
        helper = DatabaseHelper.getInstance(context);

        try {
            areaMsgDao = helper.getDao(AreaMsgDbBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized static AreaMsgDao getInstance(Context context){
        if (null == mInstange){
            synchronized (AreaMsgDao.class) {
                if (null == mInstange){
                    mInstange = new AreaMsgDao(context);
                }
            }
        }
        return mInstange;
    }

    public void add(AreaMsgDbBean bean){
        try {
            areaMsgDao.createIfNotExists(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updata(AreaMsgDbBean bean){
        try {
            areaMsgDao.createOrUpdate(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AreaMsgDbBean> getAll(){
        List<AreaMsgDbBean> list = new ArrayList<>();
        try {
            list = areaMsgDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (null!=list && !list.isEmpty()){
            Collections.sort(list, new CityComparator());
        }
        return list;
    }

    /**
     * 获取分类以后的组装数据
     * @return
     */
    public AreaDataRequestBean getAllAndDistinguish(){
        AreaDataRequestBean areaDataRequestBean = new AreaDataRequestBean();
        List<AreaMsgDbBean> hot_from = new ArrayList<>();
        List<AreaMsgDbBean> hot_to = new ArrayList<>();
        List<AreaMsgDbBean> from = new ArrayList<>();
        List<AreaMsgDbBean> to = new ArrayList<>();
        List<AreaMsgDbBean> allDatas = getAll();
        if (null!=allDatas && !allDatas.isEmpty()){
            for (AreaMsgDbBean dbBean : allDatas) {
                if (TextUtils.equals(DataConstants.AreaDataConstant.TYPE_HOT_FROM,dbBean.getType())){
                    hot_from.add(dbBean);
                    continue;
                }
                if (TextUtils.equals(DataConstants.AreaDataConstant.TYPE_HOT_TO,dbBean.getType())){
                    hot_to.add(dbBean);
                    continue;
                }
                if (TextUtils.equals(DataConstants.AreaDataConstant.TYPE_FROM,dbBean.getType())){
                    from.add(dbBean);
                    continue;
                }
                if (TextUtils.equals(DataConstants.AreaDataConstant.TYPE_TO,dbBean.getType())){
                    to.add(dbBean);
                    continue;
                }
            }
            Collections.sort(hot_from, new CityComparator());
            Collections.sort(hot_to, new CityComparator());
            Collections.sort(from, new CityComparator());
            Collections.sort(to, new CityComparator());

            areaDataRequestBean.setHot_from(hot_from);
            areaDataRequestBean.setHot_to(hot_to);
            areaDataRequestBean.setFrom(from);
            areaDataRequestBean.setTo(to);
        }
        return areaDataRequestBean;
    }

    /**
     * 获取全部热门出发地
     * @return
     */
    public List<AreaMsgDbBean> getAllHotFrom(Context context){
        QueryBuilder builder = getInstance(context).getAreaMsgDao().queryBuilder().distinct();
        Where where = null;
        try {
            where = builder.where().eq("type",DataConstants.AreaDataConstant.TYPE_HOT_FROM);
            where.prepare();
            return builder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 获取全部热门目的地
     * @return
     */
    public List<AreaMsgDbBean> getAllHotTo(){

        QueryBuilder builder = getInstance(context).getAreaMsgDao().queryBuilder().distinct();
        Where where = null;
        try {
            where = builder.where().eq("type",DataConstants.AreaDataConstant.TYPE_HOT_TO);
            where.prepare();
            return builder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 获取全部出发地（非热门）
     * @return
     */
    public List<AreaMsgDbBean> getAllFrom(){

        QueryBuilder builder = getInstance(context).getAreaMsgDao().queryBuilder().distinct();
        Where where = null;
        try {
            where = builder.where().eq("type",DataConstants.AreaDataConstant.TYPE_FROM);
            where.prepare();
            return builder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 获取全部目的地（非热门）
     * @return
     */
    public List<AreaMsgDbBean> getAllTo(){

        QueryBuilder builder = getInstance(context).getAreaMsgDao().queryBuilder().distinct();
        Where where = null;
        try {
            where = builder.where().eq("type",DataConstants.AreaDataConstant.TYPE_TO);
            where.prepare();
            return builder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Dao<AreaMsgDbBean, Integer> getAreaMsgDao() {
        return areaMsgDao;
    }

    public void setAreaMsgDao(Dao<AreaMsgDbBean, Integer> areaMsgDao) {
        this.areaMsgDao = areaMsgDao;
    }

    /**
     * sort by a-z
     */
    private class CityComparator implements Comparator<AreaMsgDbBean> {
        @Override
        public int compare(AreaMsgDbBean lhs, AreaMsgDbBean rhs) {
            String a = lhs.getPlaceNamePinYin().substring(0, 1);
            String b = rhs.getPlaceNamePinYin().substring(0, 1);
            return a.compareTo(b);
        }
    }

}

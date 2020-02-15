package com.parity.device.model.api;


import com.parity.device.constants.CommonUrls;
import com.parity.device.model.bean.BaseData;
import com.parity.device.model.bean.Wxarticle;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MainApi {
    /**
     * 登录
     */

    @GET(CommonUrls.get_wxarticle)
    Observable<BaseData<List<Wxarticle>>> getWxArticle();
}

package com.luteng.qiubai.Utils;

import com.luteng.qiubai.entity.TotalEntity;
import com.luteng.qiubai.entity.VideoEntity;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by John on 2015/12/29.
 */
public interface QsbkService {
    //变量占用符
    @GET("article/list/{type}")
    Call<TotalEntity> getTotalData(@Path("type")String type,@Query("page")int page);
    //此处为Get请求，如果是Post，则为@Field
    @GET("article/list/{type}")
    Call<VideoEntity> getVideoData(@Path("type")String type,@Query("page")int page);
}

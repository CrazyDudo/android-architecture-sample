package com.example.android_architecture_sample.data.network;


import com.example.android_architecture_sample.data.network.model.JpushBean;

import java.util.HashMap;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface JpushApi {

    //https://api.jpush.cn/v3/push
    @Headers({"Content-Type:application/json", "Authorization:Basic NTAxODJlM2RkOTdhM2UyMDI3NGJlMDcxOmM1NWZkMmZlMzVjNDQ5NWMyMWVkM2QyNg=="})
    @POST("push")
    Observable<JpushBean> pushMessage(@Body() HashMap<String, Object> body);

}

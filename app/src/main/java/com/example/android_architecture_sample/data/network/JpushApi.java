package com.example.android_architecture_sample.data.network;


import com.example.android_architecture_sample.data.network.model.JpushBean;

import java.util.HashMap;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface JpushApi {

    //https://api.jpush.cn/v3/push
    @Headers({"Content-Type:application/json", "Authorization:Basic M2UyOTI1OWRiOWM0NDhhNmI4YjNjMTc3OmY5NTA5NTQ4YWRjYWM2ZjhmNDE2MWE3OA=="})
    @POST("push")
    Observable<JpushBean> pushMessage(@Body() HashMap<String, Object> body);

}

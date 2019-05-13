package com.example.android_architecture_sample.data.network;



import com.example.android_architecture_sample.AppApplication;
import com.example.android_architecture_sample.data.config.Constant;
import com.example.android_architecture_sample.utils.NetWorkUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManage {
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetWorkUtil.isNetWorkAvailable(AppApplication.getContext())) {
                int maxAge = 60;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };


    public static ApiManage apiManage;

    private static File httpCacheDirectory = new File(AppApplication.getContext().getCacheDir(), "switchCache");
    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);
    private static OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)
            .build();

    public ContactsDataApi contactsDataApi;
    public JpushApi jpushApi;

    public static ApiManage getInstance() {
        if (apiManage == null) {
            synchronized (ApiManage.class) {
                if (apiManage == null) {
                    apiManage = new ApiManage();
                }
            }
        }
        return apiManage;
    }

    public ContactsDataApi getDataService() {
        if (contactsDataApi == null) {
            synchronized (ApiManage.class) {
                if (contactsDataApi == null) {
                    contactsDataApi = new Retrofit.Builder()
                            .baseUrl(Constant.BASE_URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(ContactsDataApi.class);
                }
            }
        }

        return contactsDataApi;
    }

    public JpushApi getJpushService() {
        if (jpushApi == null) {
            synchronized (ApiManage.class) {
                if (jpushApi == null) {
                    jpushApi = new Retrofit.Builder()
                            .baseUrl(Constant.JPUSH_BASE_URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(JpushApi.class);
                }
            }
        }

        return jpushApi;
    }


}

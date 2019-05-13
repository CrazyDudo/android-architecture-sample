package com.example.android_architecture_sample.data.network;




import com.example.android_architecture_sample.data.network.model.ContactsBean;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface ContactsDataApi {

    @GET("contacts_mock_short.json")
    Observable<List<ContactsBean>> getContactsData();
}

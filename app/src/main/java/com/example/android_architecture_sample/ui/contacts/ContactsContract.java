package com.example.android_architecture_sample.ui.contacts;


import com.example.android_architecture_sample.data.network.model.ContactsBean;

import java.util.List;

public interface ContactsContract {

    interface IPresenter{
        void requestData();
        void sortData();
    }

    interface IView{
        void onLoading();
        void onRequestSuccess(List<ContactsBean> contactsBeans);
        void onError();
    }
}

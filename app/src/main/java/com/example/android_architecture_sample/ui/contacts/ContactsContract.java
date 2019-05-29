package com.example.android_architecture_sample.ui.contacts;


import com.example.android_architecture_sample.data.network.model.ContactsBean;
import com.example.android_architecture_sample.ui.base.BasePresenter;
import com.example.android_architecture_sample.ui.base.BaseView;

import java.util.List;

public interface ContactsContract {

    interface Presenter extends BasePresenter {
        void requestData();
        void sortData();
    }

    interface View extends BaseView<Presenter> {
        void onLoading();
        void onRequestSuccess(List<ContactsBean> contactsBeans);
        void onError();
    }
}

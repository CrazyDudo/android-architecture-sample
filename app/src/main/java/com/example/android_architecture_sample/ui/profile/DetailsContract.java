package com.example.android_architecture_sample.ui.profile;


import com.example.android_architecture_sample.data.network.model.ContactsBean;
import com.example.android_architecture_sample.ui.base.BasePresenter;
import com.example.android_architecture_sample.ui.base.BaseView;

public interface DetailsContract {

    interface Presenter extends BasePresenter {
        void sendPushNotification(ContactsBean contactsBean);
    }

    interface View  extends BaseView<Presenter> {
        void onSendSuccess();
        void onSendFailed();
    }
}

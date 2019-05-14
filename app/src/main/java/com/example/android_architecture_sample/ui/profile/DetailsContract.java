package com.example.android_architecture_sample.ui.profile;


import com.example.android_architecture_sample.data.network.model.ContactsBean;

public interface DetailsContract {

    interface IPresenter{
        void sendPushNotification(ContactsBean contactsBean);
    }

    interface IView{
        void onSendSuccess();
        void onSendFailed();
    }
}

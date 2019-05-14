package com.example.android_architecture_sample.ui.profile;


import com.example.android_architecture_sample.data.network.model.ContactsBean;
import com.example.android_architecture_sample.ui.base.BasePresenter;
import com.example.android_architecture_sample.ui.base.BaseView;

public interface DetailsContract {

    interface IPresenter extends BasePresenter<IView> {
        void sendPushNotification(ContactsBean contactsBean);
    }

    interface IView extends BaseView<IPresenter> {
        void onSendSuccess();
        void onSendFailed();
    }
}

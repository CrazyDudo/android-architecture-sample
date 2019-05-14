package com.example.android_architecture_sample.ui.contacts;


import android.util.Log;

import com.example.android_architecture_sample.data.network.ApiManager;
import com.example.android_architecture_sample.data.network.model.ContactsBean;

import java.util.Collections;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ContactsPresenter implements ContactsContract.IPresenter {
    private ContactsContract.IView view;
    private static final String TAG = "ContactsPresenter";

//    public ContactsPresenter(ContactsContract.IView view) {
//        this.view = view;
//    }

    @Override
    public void requestData() {
        doRequestData();
    }


    @Override
    public void sortData() {

    }


    private void doRequestData() {
        view.onLoading();
        ApiManager.getInstance()
                .getDataService()
                .getContactsData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ContactsBean>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError();
                        Log.d(TAG, "onError: " + e.getMessage());

                    }

                    @Override
                    public void onNext(List<ContactsBean> contactsBeans) {

                        view.onRequestSuccess(contactsBeans);
                        Log.d(TAG, "onNext: " + contactsBeans.get(0).getEmail());

                        //sort
                        Collections.sort(contactsBeans);
                        Collections.reverse(contactsBeans);

                    }

                });


    }


    @Override
    public void takeView(ContactsContract.IView view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        this.view=null;
    }
}

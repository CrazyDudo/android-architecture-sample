package com.example.android_architecture_sample.di.module;

import com.example.android_architecture_sample.ui.contacts.ContactsPresenter;
import com.example.android_architecture_sample.ui.profile.DetailsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    public ActivityModule() {
    }

    @Provides
    ContactsPresenter provideContactsPersenter() {

        return new ContactsPresenter();
    }

    @Provides
    DetailsPresenter provideDetailsPersenter() {
        return new DetailsPresenter();
    }
}

package com.example.android_architecture_sample.di.component;

import com.example.android_architecture_sample.di.module.ActivityModule;
import com.example.android_architecture_sample.ui.contacts.ContactsListActivity;
import com.example.android_architecture_sample.ui.profile.DetailsActivity;

import dagger.Component;

@Component(modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(ContactsListActivity activity);
    void inject(DetailsActivity activity);
}

package com.example.android_architecture_sample.ui.contacts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android_architecture_sample.R;
import com.example.android_architecture_sample.data.network.model.ContactsBean;
import com.example.android_architecture_sample.di.component.DaggerActivityComponent;
import com.example.android_architecture_sample.di.module.ActivityModule;
import com.example.android_architecture_sample.ui.profile.DetailsActivity;
import com.example.android_architecture_sample.ui.widget.ToolBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsListActivity extends AppCompatActivity implements ContactsContract.IView {
    private static final String TAG = "ContactsListActivity";

    @Inject
    ContactsPresenter presenter;

    @BindView(R.id.lv_contact_list)
    ListView lvContactList;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.tb)
    ToolBar tb;
    private ContactsListAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        ButterKnife.bind(this);
         DaggerActivityComponent.builder()
                .activityModule(new ActivityModule())
                .build()
                .inject(this);
        initView();
        initData();
    }

    private void initView() {
        presenter.takeView(this);
        initToolBar();
        initSwipeLayout();
    }

    private void initToolBar() {
        tb.showBackBtn(false);
        tb.showTitle(true);
        tb.showLeftlab(false);
        tb.setBackOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initSwipeLayout() {
        swipeLayout.setColorSchemeResources(R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryDark
        );
        swipeLayout.setSize(SwipeRefreshLayout.LARGE);
        ;
        swipeLayout.setProgressViewEndTarget(true, 100);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(1);
                    }
                }).start();
            }
        });
    }


    private void initData() {

        presenter.requestData();
    }


    /**
     * set adapter for list view
     *
     * @param mListData
     */
    private void initListView(final List<ContactsBean> mListData) {
        adapter = new ContactsListAdapter(this, R.layout.contacts_list_item, mListData);
        lvContactList.setDivider(null);
        lvContactList.setAdapter(adapter);
        lvContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContactsListActivity.this, DetailsActivity.class);
                intent.putExtra("contact_info", mListData.get(position));
                startActivity(intent);

            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    swipeLayout.setRefreshing(false);
//                    adapter.notifyDataSetChanged();
//                    swipeLayout.setEnabled(false);
                    Toast.makeText(ContactsListActivity.this, "refreshed", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onLoading() {
        Log.d(TAG, "onLoading: ");
        progressDialog = ProgressDialog.show(this, "Loading...", "");

    }

    @Override
    public void onRequestSuccess(List<ContactsBean> contactsBeans) {
        progressDialog.dismiss();
        initListView(contactsBeans);
        Log.d(TAG, "onRequestSuccess: ");
    }

    @Override
    public void onError() {
        progressDialog.dismiss();
        Toast.makeText(this, "network error", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onError: ");
    }

}

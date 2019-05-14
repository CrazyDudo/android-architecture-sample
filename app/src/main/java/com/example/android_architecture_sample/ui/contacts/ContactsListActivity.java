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
import com.example.android_architecture_sample.data.network.ApiManager;
import com.example.android_architecture_sample.data.network.model.ContactsBean;
import com.example.android_architecture_sample.ui.profile.DetailsActivity;
import com.example.android_architecture_sample.ui.widget.ToolBar;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ContactsListActivity extends AppCompatActivity {

    private static final String TAG = "ContactsListActivity";
    ProgressDialog mProgressDialog;
    @BindView(R.id.lv_contact_list)
    ListView lvContactList;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.tb)
    ToolBar tb;
    private ContactsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
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
        doRequestData();
    }

    private void doRequestData() {
        mProgressDialog = ProgressDialog.show(this, "Loading...", "Please wait...", true, false);
        ApiManager.getInstance()
                .getDataService()
                .getContactsData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ContactsBean>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        mProgressDialog.dismiss();
                        Toast.makeText(ContactsListActivity.this, "network connect error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<ContactsBean> contactsBeans) {
                        mProgressDialog.dismiss();
                        Log.d(TAG, "onNext: " + contactsBeans.get(0).getEmail());

                        //sort
                        Collections.sort(contactsBeans);
                        Collections.reverse(contactsBeans);
                        initListView(contactsBeans);
                    }

                });
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
}

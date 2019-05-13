package com.example.android_architecture_sample.ui.profile;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.android_architecture_sample.R;
import com.example.android_architecture_sample.data.network.ApiManage;
import com.example.android_architecture_sample.data.network.model.ContactsBean;
import com.example.android_architecture_sample.data.network.model.JpushBean;
import com.example.android_architecture_sample.ui.widget.GlideCircleWithBorder;
import com.example.android_architecture_sample.ui.widget.ToolBar;


import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = "DetailsActivity";
    @BindView(R.id.iv_detail_avatar)
    ImageView ivDetailAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.btn_message)
    Button btnMessage;
    @BindView(R.id.tb)
    ToolBar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        initToolBar();
    }

    private void initToolBar() {
        tb.showTitle(false);
        tb.showLeftlab(true);
        tb.setBackOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        ContactsBean contactsBean = getIntent().getExtras().getParcelable("contact_info");

        Glide.with(this).load(contactsBean.getAvatar()).apply(new RequestOptions()
                .error(this.getResources().getDrawable(R.mipmap.default_img))
                .placeholder(R.mipmap.default_img).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                .fallback(R.mipmap.default_img)
                .transform(new GlideCircleWithBorder(this, 1, Color.parseColor("#BCBCBC"))))
                .into(ivDetailAvatar);

        tvName.setText(contactsBean.getFirst_name() + " " + contactsBean.getLast_name());
        tvPhone.setText(contactsBean.getPhone());
        tvGender.setText(contactsBean.getGender());
        tvEmail.setText(contactsBean.getEmail());
        tvAddress.setText(contactsBean.getAddress());
    }

    @OnClick({R.id.tv_name, R.id.btn_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_message:
                sendMessage();
                break;
        }
    }

    private void sendMessage() {
        HashMap<String, Object> body = new HashMap<>();
        HashMap<String, String> notification = new HashMap();
        notification.put("alert", "Hi,this is a test message");
        body.put("platform", "all");
        body.put("audience", "all");
        body.put("notification", notification);

        ApiManage.getInstance()
                .getJpushService()
                .pushMessage(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JpushBean>() {

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(DetailsActivity.this, "network connect error,try again later", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(JpushBean jpushBean) {

                        Log.d(TAG, "onNext: " + jpushBean.getMsg_id());
                        Toast.makeText(DetailsActivity.this, "Message send", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}

package com.example.android_architecture_sample.ui.profile;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.android_architecture_sample.R;
import com.example.android_architecture_sample.data.network.model.ContactsBean;
import com.example.android_architecture_sample.di.component.DaggerActivityComponent;
import com.example.android_architecture_sample.di.module.ActivityModule;
import com.example.android_architecture_sample.ui.widget.GlideCircleWithBorder;
import com.example.android_architecture_sample.ui.widget.ToolBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity implements DetailsContract.IView {
    private static final String TAG = "DetailsActivity";

    @Inject
    DetailsPresenter presenter;

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
    private ContactsBean contactsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
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
        contactsBean = getIntent().getExtras().getParcelable("contact_info");

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
                presenter.sendPushNotification(contactsBean);
                break;
        }
    }


    @Override
    public void onSendSuccess() {
        Toast.makeText(this, "Send Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendFailed() {
        Toast.makeText(this, "Send Failed", Toast.LENGTH_SHORT).show();

    }
}

package com.example.android_architecture_sample.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android_architecture_sample.R;


public class ToolBar extends FrameLayout {


    private RelativeLayout rlBack;
    private TextView tvTitle;
    private TextView tvLeft;
    public ToolBar(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ToolBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ToolBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_toolbar, this);
        rlBack = findViewById(R.id.rl_left);
        tvTitle = findViewById(R.id.tv_title);
        tvLeft = findViewById(R.id.tv_left);
    }

    public void showBackBtn(boolean enable) {
        if (enable) {
            rlBack.setVisibility(View.VISIBLE);
        } else {
            rlBack.setVisibility(View.GONE);
        }
    }

    public void showLeftlab(boolean enable) {
        if (enable) {
            tvLeft.setVisibility(View.VISIBLE);
        } else {
            tvLeft.setVisibility(View.GONE);
        }
    }

    public void showTitle(boolean enable) {
        if (enable) {
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }


    public void setBackOnclickListener(OnClickListener onclickListener) {
        rlBack.setOnClickListener(onclickListener);
    }
}

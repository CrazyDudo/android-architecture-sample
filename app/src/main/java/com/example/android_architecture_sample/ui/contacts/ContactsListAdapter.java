package com.example.android_architecture_sample.ui.contacts;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.android_architecture_sample.R;
import com.example.android_architecture_sample.data.network.model.ContactsBean;
import com.example.android_architecture_sample.ui.widget.GlideCircleWithBorder;
import com.example.android_architecture_sample.utils.TimeUtil;


import java.util.List;

@SuppressWarnings("NullableProblems")
public class ContactsListAdapter extends ArrayAdapter<ContactsBean> {

    private Context mContext;
    private int resourceId;
    private final List<ContactsBean> datas;

    public ContactsListAdapter(Context context, int textViewResourceId, List<ContactsBean> data) {
        super(context, textViewResourceId, data);
        mContext = context;
        datas = data;
        resourceId = textViewResourceId;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ContactsBean contactsBean = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.tvName = convertView.findViewById(R.id.tv_name);
            viewHolder.tvDate = convertView.findViewById(R.id.tv_date);
            viewHolder.ivAvatar = convertView.findViewById(R.id.iv_avatar);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvName.setText(contactsBean.getFirst_name() + " " + contactsBean.getLast_name());
        //date in first item 2016/1/30 15:36:0
        viewHolder.tvDate.setText(TimeUtil.getIntervalByTimeStamp(contactsBean.getDate()));
//
        Glide.with(mContext).load(contactsBean.getAvatar())
                .apply(new RequestOptions()
                .error(mContext.getResources().getDrawable(R.mipmap.default_img))
                .placeholder(R.mipmap.default_img).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                .fallback(R.mipmap.default_img)
                .transform(new GlideCircleWithBorder(mContext, 1, Color.parseColor("#BCBCBC"))))
                .into(viewHolder.ivAvatar);
        return convertView;
    }


    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public ContactsBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView tvName;
        TextView tvDate;
        ImageView ivAvatar;
    }

}

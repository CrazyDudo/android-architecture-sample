package com.example.android_architecture_sample.data.network.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactsBean  implements Comparable<ContactsBean>,Parcelable {


    /**
     * id : 1
     * first_name : Joan
     * last_name : Jordan
     * email : jjordan0@networksolutions.com
     * gender : Female
     * phone : 62-(504)864-6551
     * address : 0 Shopko Pass
     * job : Professor
     * avatar : https://robohash.org/ablaborumquia.bmp?size=50x50&set=set1
     * date : 1454139360
     */

    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String gender;
    private String phone;
    private String address;
    private String job;
    private String avatar;
    private String date;

    protected ContactsBean(Parcel in) {
        id = in.readInt();
        first_name = in.readString();
        last_name = in.readString();
        email = in.readString();
        gender = in.readString();
        phone = in.readString();
        address = in.readString();
        job = in.readString();
        avatar = in.readString();
        date = in.readString();
    }

    public static final Creator<ContactsBean> CREATOR = new Creator<ContactsBean>() {
        @Override
        public ContactsBean createFromParcel(Parcel in) {
            return new ContactsBean(in);
        }

        @Override
        public ContactsBean[] newArray(int size) {
            return new ContactsBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int compareTo(ContactsBean o) {

        return getDate().compareTo(o.getDate());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(email);
        dest.writeString(gender);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(job);
        dest.writeString(avatar);
        dest.writeString(date);
    }
}

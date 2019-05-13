package com.example.android_architecture_sample.utils;

public class TimeUtil {

    public static String getIntervalByTimeStamp(String timeStr) {
        String temp = "";
        try {
            long now = System.currentTimeMillis() / 1000;
            long publish = Long.parseLong(timeStr);
            long diff = now - publish;
            long months = diff / (60 * 60 * 24*30);
            long days = diff / (60 * 60 * 24);
            long hours = (diff - days * (60 * 60 * 24)) / (60 * 60);
            long minutes = (diff - days * (60 * 60 * 24) - hours * (60 * 60)) / 60;
            if (months > 0) {
                temp = months + " months ago";
            } else if (days > 0) {
                temp = days + " days ago";
            } else if (hours > 0) {
                temp = hours + " hours ago";
            } else {
                temp = minutes + " min ago";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }
}

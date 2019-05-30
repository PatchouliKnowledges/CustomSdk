package com.cyd.bean;

import android.graphics.drawable.Drawable;

public class Appinfo {

    private String appName;
    private String versionName;
    private long versionCode;
    private String packageName;
    private Drawable icon;
    private Drawable banner;
    private Drawable logo;

    public Drawable getLogo() {
        return logo;
    }

    public void setLogo(Drawable logo) {
        this.logo = logo;
    }

    public Drawable getBanner() {
        return banner;
    }

    public void setBanner(Drawable banner) {
        this.banner = banner;
    }

    public long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }


    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}

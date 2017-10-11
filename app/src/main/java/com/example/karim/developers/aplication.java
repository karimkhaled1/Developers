package com.example.karim.developers;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

public class aplication {
    private String packageName;
    private String url;
    private int downloads;
    private String uid;

    public aplication(String packageName, String url,String uid) {
        this.packageName = packageName;
        this.url = url;
        this.uid=uid;
        downloads=0;
    }
    public aplication() {
    }

    public String getPackageName() {
        return packageName;
    }

    public String getUrl() {
        return url;
    }

    public int getDownloads() {
        return downloads;
    }

    public String getUid() {
        return uid;
    }

}

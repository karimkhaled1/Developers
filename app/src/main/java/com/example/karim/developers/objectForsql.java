package com.example.karim.developers;

/**
 * Created by karim on 4/10/2017.
 */

public class objectForsql {
    String Package;
    String uid;
    String downloaded;

    public String getDownloaded() {
        return downloaded;
    }

    public String getPackage() {
        return Package;
    }

    public String getUid() {
        return uid;
    }

    public objectForsql(String aPackage, String uid,String downloaded) {

        Package = aPackage;
        this.uid = uid;
        this.downloaded=downloaded;
    }
}

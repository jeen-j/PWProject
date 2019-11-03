package com.creative.jeen.mypassword;

public class ItemData {

    private String mSiteName;
    private String mUrl;
    private String mUserID;
    private String mPassword;
    private String mNote;


    public ItemData(String siteName, String url, String userID, String password, String note) {
        mSiteName = siteName;
        mUrl = url;
        mUserID = userID;
        mPassword = password;
        mNote = note;
    }


    public String getSiteName() {
        return mSiteName;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getUserID() {
        return mUserID;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getNote() {
        return mNote;
    }


}

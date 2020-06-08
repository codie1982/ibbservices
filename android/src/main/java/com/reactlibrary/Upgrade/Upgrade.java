package com.reactlibrary.Upgrade;

import android.app.Activity;
import android.content.Context;

import com.reactlibrary.Models.Version;

public class Upgrade {

    private Version version;
    private Context context;
    private Activity activity;

    public Version getVersion() {
        return version;
    }

    public Context getContext() {
        return context;
    }

    public Activity getActivity() {
        return activity;
    }

    public Long getCurrentVersion() {
        return currentVersion;
    }

    private Long currentVersion;
    private String currentVersionString;

    //constractor
    public Upgrade() {
    }
    //constractor
    public Upgrade(Context context) {
        this.context = context;
    }
    //constractor
    public Upgrade(Context context ,Version version) {
        this.version = version;
        this.context = context;
    }

    protected void setActivity(Activity activity){
        this.activity = activity;
    }
    protected void setCurrentVersion(Long currentVersion){
        this.currentVersion = currentVersion;
    }
    protected void setCurrentVersionString(String currentVersionString){
        this.currentVersionString = currentVersionString;
    }

    public String getCurrentVersionString(){
        return currentVersionString;
    }

    public Long getVersionType() {
        return getVersion().versionData.versionDetail.versionType;
    }


}

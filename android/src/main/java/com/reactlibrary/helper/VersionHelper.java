package com.reactlibrary.Helper;

import android.content.Context;
import android.os.Build;

public class VersionHelper {

    private Context pcontext;
    int published_version;
    int current_version;
    private static VersionHelper nVerisonHelper = new VersionHelper();

    public static synchronized VersionHelper getInstance() {
        return nVerisonHelper;
    }


    public Boolean checkVersion(Long current_version, Long published_version) {
        if (current_version < published_version) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkVersion(Double current_version,Double published_version) {
        if (current_version<published_version) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkVersion(String _current_version, String _published_version) {
        current_version = Integer.parseInt(_current_version);
        published_version = Integer.parseInt(_published_version);
        if (current_version < published_version) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkVersion(Context context, String _published_version) {
        pcontext = context;

        published_version = Integer.parseInt(_published_version);

        if (0 > published_version) {
            return true;
        } else {
            return false;
        }
    }

    public Long getCurrentVersion(ApplicationHelper applicationHelper){
        Long current_version=0L;
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                current_version = applicationHelper.getPackageInfo().getLongVersionCode();
                System.out.println("Uygulama Versiyonu :" + applicationHelper.getPackageInfo().getLongVersionCode());
            }else{
                current_version =new Long( applicationHelper.getPackageInfo().versionCode);
                System.out.println("Uygulama Versiyonu :" + applicationHelper.getPackageInfo().versionCode);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            current_version = -1L;
        }
        return current_version;
    }

}

package com.reactlibrary.Module;

import android.content.Context;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.reactlibrary.Helper.VersionHelper;

import java.util.UUID;

public class IBBInfo {
    //aplikasyon, Cihaz,Kullanıcı bilgilerini birleştirip özel id ile db ye yazılacak hale dönüştürmemiz gerekiyor
    //İlk kayıt datası ve update datası için iki ayrı kısımda ilgilenmemiz gerekli
    private String connectionID;
    private DeviceInfo deviceInfo;

    Context context;
    private ApplicationInfo applicationInfo;
    public IBBInfo(Context context) {
        this.context = context;
        this.connectionID = UUID.randomUUID().toString();
        deviceInfo = new DeviceInfo(this.context);
    }

    public String getConnectionID() {
        return connectionID;
    }

    public String getDeviceID() {
        return deviceInfo.getDevice_id();
    }

    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }

    public void setApplicationInfo(ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
    }
    public Boolean checkVersion(Long current_version,Long published_version) {
        VersionHelper versionHelper = new VersionHelper();
        if(versionHelper.checkVersion(current_version,published_version)){
            return true;
        }else {
            return false;
        }
    }

    public WritableMap result() {
        WritableMap map = Arguments.createMap();

        map.putString("deviceID",getDeviceID());

        map.putString("application_market",applicationInfo.getApplication_market());

        map.putString("application_name",applicationInfo.getApplication_name());

        map.putString("category",applicationInfo.getCategory());

        map.putString("description",applicationInfo.getDescription());

        map.putString("download_link",applicationInfo.getDownload_link());

        map.putString("logo",applicationInfo.getLogo());

        map.putString("package_name",applicationInfo.getPackage_name());

        map.putString("published",applicationInfo.getPublished());

        map.putString("short_title",applicationInfo.getShort_title());

        map.putString("title",applicationInfo.getTitle());

        map.putString("type",applicationInfo.getType());

        //map.putString("update_time",applicationInfo.getUpdate_time().toString());

        map.putInt("published_version",applicationInfo.getPublished_version().intValue());

        return map;
    }


}

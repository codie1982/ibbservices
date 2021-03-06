package com.reactlibrary.Module;

import android.content.Context;
import android.provider.Settings.*;

public class DeviceInfo {
    //Cihaz bilgilerini almamız Gerekiyor

   private String device_id;
   private String OS;
   private String OSVersion;
   private String device_model;

   private Context context;
    public DeviceInfo(Context context) {
        this.context = context;
        this.device_id = Secure.getString(this.context.getContentResolver(),
                Secure.ANDROID_ID);
    }

    public String getDevice_id() {
        return device_id;
    }

    public String getOS() {
        return "";
    }

    public String getOSVersion() {
        return OSVersion;
    }

    public String getDevice_model() {
        return device_model;
    }
}

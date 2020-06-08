package com.reactlibrary.Helper;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;


public class PermissionHelper2 {

    public static Boolean hasPermission(Context context,String... permissions){
        if(permissions != null){
            for(String permission:permissions){
                if(ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }

        }
        return true;
    }
}

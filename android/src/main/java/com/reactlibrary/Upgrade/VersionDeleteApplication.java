package com.reactlibrary.Upgrade;

import android.content.Context;
import android.content.Intent;

import com.reactlibrary.Activity.VersionDeleteActivity;
import com.reactlibrary.Models.Version;

public class VersionDeleteApplication extends Upgrade implements IUpgrade {

    public VersionDeleteApplication() {
    }

    public VersionDeleteApplication(Context context, Version version) {
        super(context, version);
    }

    @Override
    public void startProcess() {
/*

        DOWNLOADURL = download_url;
        DIRNAME = application_name;
        FILENAME = application_name + ".apk";
*/

        Intent modalIntent = new Intent(context, VersionDeleteActivity.class);
        modalIntent.setFlags(modalIntent.FLAG_ACTIVITY_NEW_TASK);

/*         modalIntent.putExtra("dirname", DIRNAME);
        modalIntent.putExtra("filename", FILENAME);
        modalIntent.putExtra("deviceid", deviceID);
        modalIntent.putExtra("published_version", published_version);
        modalIntent.putExtra("package_name", package_name);*/
        context.startActivity(modalIntent);

    }
}

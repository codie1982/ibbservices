package com.reactlibrary.Upgrade;

import android.content.Context;
import android.content.Intent;

import com.reactlibrary.Activity.DownloadActivity;
import com.reactlibrary.Activity.PauseActivity;
import com.reactlibrary.Models.Version;

import static com.reactlibrary.Constain.VersionConstains.CRITICAL;

public class PauseApplication extends Upgrade implements IUpgrade {

    public PauseApplication() {
    }

    public PauseApplication(Context context, Version version) {
        super(context, version);
    }

    @Override
    public void startProcess() {
        System.out.println(nVersion.versionData.versionDetail.upload_url + "Important için Download Linki");
/*
        DOWNLOADURL = download_url;
        DIRNAME = application_name;
        FILENAME = application_name + ".apk";
*/

        Intent modalIntent = new Intent(context, PauseActivity.class);
        modalIntent.setFlags(modalIntent.FLAG_ACTIVITY_NEW_TASK);

/*         modalIntent.putExtra("dirname", DIRNAME);
        modalIntent.putExtra("filename", FILENAME);
        modalIntent.putExtra("deviceid", deviceID);
        modalIntent.putExtra("published_version", published_version);
        modalIntent.putExtra("package_name", package_name);*/
        context.startActivity(modalIntent);
    }
}

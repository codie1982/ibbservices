package com.reactlibrary.Upgrade;

import android.content.Context;
import android.content.Intent;

import com.reactlibrary.Activity.DownloadActivity;
import com.reactlibrary.Models.Version;

import static com.reactlibrary.Constain.VersionConstains.CRITICAL;
import static com.reactlibrary.Constain.VersionConstains.LOWIMPORTANT;

public class lowImportantUpgrade extends Upgrade implements IUpgrade {

    public lowImportantUpgrade() {
    }

    public lowImportantUpgrade(Context context, Version version) {
        super(context, version);
    }

    @Override
    public void startProcess() {
        String upload_url = nVersion.versionData.versionDetail.upload_url;
/*

        DOWNLOADURL = download_url;
        DIRNAME = application_name;
        FILENAME = application_name + ".apk";
*/

        Intent modalIntent = new Intent(context, DownloadActivity.class);
        modalIntent.setFlags(modalIntent.FLAG_ACTIVITY_NEW_TASK);
        modalIntent.putExtra("upgrade_type",LOWIMPORTANT );

/*         modalIntent.putExtra("dirname", DIRNAME);
        modalIntent.putExtra("filename", FILENAME);
        modalIntent.putExtra("deviceid", deviceID);
        modalIntent.putExtra("published_version", published_version);
        modalIntent.putExtra("package_name", package_name);*/
        context.startActivity(modalIntent);

    }
}

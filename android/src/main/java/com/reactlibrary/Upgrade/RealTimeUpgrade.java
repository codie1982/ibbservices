package com.reactlibrary.Upgrade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.reactlibrary.Activity.DownloadActivity;
import com.reactlibrary.Models.Version;

import static com.reactlibrary.Constain.VersionConstains.CRITICAL;
import static com.reactlibrary.Constain.VersionConstains.IMPORTANT;
import static com.reactlibrary.Constain.VersionConstains.REALTIME;

public class RealTimeUpgrade extends Upgrade implements IUpgrade {

    public RealTimeUpgrade() {
    }

    public RealTimeUpgrade(Context context, Version version) {
        super(context, version);
    }

    @Override
    public void startProcess() {
        Version nVersion  = getVersion();
        Context context = getContext();
        Activity activity = getActivity();
        String upload_url = nVersion.versionData.versionDetail.upload_url;

        Intent intent = new Intent(context, DownloadActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("upgrade_type",REALTIME );


        intent.putExtra("publisher",nVersion.applicationData.publisher);
        intent.putExtra("download_count",nVersion.applicationData.downloadCount);
        intent.putExtra("downloadLogoUrl",nVersion.applicationData.downloadLogoUrl);
        intent.putExtra("application_name",nVersion.applicationData.applicationName);
        intent.putExtra("application_title",nVersion.applicationData.applicationTitle);
        intent.putExtra("application_short_title",nVersion.applicationData.applicationShortTitle);
        intent.putExtra("application_description",nVersion.applicationData.applicationDescription);
        intent.putExtra("category",nVersion.applicationData.category);

        intent.putExtra("upgrade_type", IMPORTANT);
        intent.putExtra("download_url", nVersion.versionData.versionDetail.upload_url);
        intent.putExtra("id", nVersion.versionData.id);
        intent.putExtra("version_notes", nVersion.versionData.versionDetail.versionNotes);
        intent.putExtra("public_time", nVersion.versionData.versionDetail.publicTime);
        intent.putExtra("version_title", nVersion.versionData.versionDetail.versionTitle);

        intent.putExtra("current_version", getCurrentVersion());
        intent.putExtra("current_version_string", getCurrentVersionString());
        intent.putExtra("version_string", nVersion.versionData.versionDetail.versionString);
        intent.putExtra("publish_version", nVersion.versionData.versionDetail.versionNumber);


        intent.putExtra("title", "Gerçek Zamanlı Güncelleme");
        intent.putExtra("title_description", "Uygulama için hayati öneme sahip güncellemedir. Uygulama güncellemesi otomatik başlar, güncelleme olmadan uygulamanın devam etmesine izin verilmez.");

        intent.putExtra("color", "#007bff");
        context.startActivity(intent);
    }
}

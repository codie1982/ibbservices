package com.reactlibrary.Upgrade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.reactlibrary.Activity.DownloadActivity;
import com.reactlibrary.Models.Version;
import com.reactlibrary.R;

import static com.reactlibrary.Constain.StatusConstains.VERSION_DELETE;
import static com.reactlibrary.Constain.StatusConstains.VERSION_REGULAR;
import static com.reactlibrary.Constain.VersionConstains.*;

public class ImportantUpgrade extends Upgrade implements IUpgrade {

    public ImportantUpgrade() {
    }

    public ImportantUpgrade(Context context, Version version) {
        super(context, version);
    }

    @Override
    public void startProcess() {
        Version nVersion = getVersion();
        Context context = getContext();
        Activity activity = getActivity();
        String upload_url = nVersion.versionData.versionDetail.upload_url;


        Intent intent = new Intent(context, DownloadActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra("upgrade_type", IMPORTANT);

        intent.putExtra("publisher", nVersion.applicationData.publisher);
        intent.putExtra("download_count", nVersion.applicationData.downloadCount);
        intent.putExtra("downloadLogoUrl", nVersion.applicationData.downloadLogoUrl);
        intent.putExtra("application_name", nVersion.applicationData.applicationName);
        intent.putExtra("application_title", nVersion.applicationData.applicationTitle);
        intent.putExtra("application_short_title", nVersion.applicationData.applicationShortTitle);
        intent.putExtra("application_description", nVersion.applicationData.applicationDescription);
        intent.putExtra("category", nVersion.applicationData.category);

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

        if (nVersion.status.equals(VERSION_REGULAR)) {
            intent.putExtra("title", "Önemli Güncelleme");
            intent.putExtra("title_description", "Önemli Güncelleme Uygulamanızda gerekli olan ve uygulama için geliştirilmiş yeni alanları barındırır. Önemli güncelleme her uygulama açılışında gösterilir, ancak güncelleştirme olmasada uygulamanın kullanımına izin verilir.");
            intent.putExtra("color", getContext().getResources().getColor(R.color.color_important));
            context.startActivity(intent);
        } else if (nVersion.status.equals(VERSION_DELETE)) {
            intent.putExtra("title", "Mevcut Versiyon Kaldırılmıştır.");
            intent.putExtra("title_description", "Cihazınızda bulunan uygulamanın mevcut versiyonu kaldırılmıştır. " +
                    "Bu Versiyon ile devam etmeniz mümkün değildir. Size bu aplikasyonun bir önceki versiyonu önerilmektedir." +
                    "Cihazınıza önerilen versiyonu kurum devam etmeniz gerekmektedir.");
            intent.putExtra("color", getContext().getResources().getColor(R.color.color_critical));
            context.startActivity(intent);
            activity.finish();
        } else {
            intent.putExtra("title", "Önemli Güncelleme");
            intent.putExtra("title_description", "Önemli Güncelleme Uygulamanızda gerekli olan ve uygulama için geliştirilmiş yeni alanları barındırır. Önemli güncelleme her uygulama açılışında gösterilir, ancak güncelleştirme olmasada uygulamanın kullanımına izin verilir.");
            intent.putExtra("color", getContext().getResources().getColor(R.color.color_important));
            context.startActivity(intent);
        }


    }
}

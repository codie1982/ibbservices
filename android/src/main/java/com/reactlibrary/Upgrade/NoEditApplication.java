package com.reactlibrary.Upgrade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.reactlibrary.Activity.DownloadActivity;
import com.reactlibrary.Activity.NoApplicationActivity;
import com.reactlibrary.Activity.NoEditActivity;
import com.reactlibrary.Models.Version;

import static com.reactlibrary.Constain.VersionConstains.CRITICAL;

public class NoEditApplication extends Upgrade implements IUpgrade {

    public NoEditApplication() {
    }

    public NoEditApplication(Context context, Version version) {
        super(context, version);
    }

    @Override
    public void startProcess() {
        Version nVersion  = getVersion();
        Context context = getContext();
        Activity activity = getActivity();

        Intent modalIntent = new Intent(context, NoApplicationActivity.class);
        modalIntent.setFlags(modalIntent.FLAG_ACTIVITY_NEW_TASK);

        modalIntent.putExtra("status", "application_noedit");
        modalIntent.putExtra("message", "Bu Uygulama henüz düzenlenmemiş ve kullanıma hazır değil." +
                "Lütfen daha sonra tekrar deneyin.");
        context.startActivity(modalIntent);
        activity.finish();
    }
}

package com.reactlibrary.Upgrade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.reactlibrary.Activity.DownloadActivity;
import com.reactlibrary.Activity.NoApplicationActivity;
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
        Version nVersion  = getVersion();
        Context context = getContext();
        Activity activity = getActivity();

        Intent modalIntent = new Intent(context, NoApplicationActivity.class);
        modalIntent.setFlags(modalIntent.FLAG_ACTIVITY_NEW_TASK);

        modalIntent.putExtra("status", "application_pause");
        modalIntent.putExtra("message", "Bu Uyuglama IBB Tarafından geçici bir süreliğine durdurulmuştur.");
        context.startActivity(modalIntent);
        activity.finish();
    }
}

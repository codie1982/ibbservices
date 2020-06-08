package com.reactlibrary.Upgrade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.reactlibrary.Activity.DownloadActivity;
import com.reactlibrary.Models.Version;

import static com.reactlibrary.Constain.VersionConstains.REALTIME;

public class NoTypicalUpgrade extends Upgrade implements IUpgrade {

    public NoTypicalUpgrade() {
    }

    public NoTypicalUpgrade(Context context, Version version) {
        super(context, version);
    }

    @Override
    public void startProcess() {
        Version nVersion  = getVersion();
        Context context = getContext();
        Activity activity = getActivity();
        Intent modalIntent = new Intent(context, DownloadActivity.class);
        modalIntent.setFlags(modalIntent.FLAG_ACTIVITY_NEW_TASK);
        modalIntent.putExtra("upgrade_type",-1L );
        context.startActivity(modalIntent);

    }
}

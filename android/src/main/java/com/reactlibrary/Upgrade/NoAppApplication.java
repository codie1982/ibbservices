package com.reactlibrary.Upgrade;

import android.content.Context;
import android.content.Intent;

import com.reactlibrary.Activity.NoApplicationActivity;

public class NoAppApplication extends Upgrade implements IUpgrade {

    public NoAppApplication() {
    }

    public NoAppApplication(Context context) {
        super(context);
    }

    @Override
    public void startProcess() {
        Intent modalIntent = new Intent(context, NoApplicationActivity.class);
        modalIntent.setFlags(modalIntent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(modalIntent);

    }
}

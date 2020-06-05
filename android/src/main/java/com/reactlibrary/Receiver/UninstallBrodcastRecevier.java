package com.reactlibrary.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class UninstallBrodcastRecevier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getData().getEncodedSchemeSpecificPart();
        System.out.println("Dosya Uninstall Oluyor" + packageName);
        Toast.makeText(context, "Kullanıcı UNINSTALL : " + packageName, Toast.LENGTH_LONG).show();
    }
}

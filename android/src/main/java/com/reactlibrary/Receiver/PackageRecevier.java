package com.reactlibrary.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import static com.reactlibrary.Constain.Constains.SETUP;

public class PackageRecevier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences sharedPreferences =context.getSharedPreferences(context.getPackageName(),context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SETUP,false);
        editor.commit();

        String packageName = intent.getData().getEncodedSchemeSpecificPart();
        System.out.println("PAKET HAREKETLERI : " + packageName);
        Toast.makeText(context, "PAKET HAREKETLERI : " + packageName, Toast.LENGTH_LONG).show();
        /*
        *  if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
        String packageName = intent.getDataString();
        Log.i("Installed:", packageName + "package name of the program");
    }
    Log.d(TAG, "Action: " + intent.getAction());
    Uri data = intent.getData();
    Log.d(TAG, "The DATA: " + data);
    * */
    }
}

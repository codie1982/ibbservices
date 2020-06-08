package com.reactlibrary.Upgrade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.reactlibrary.Activity.NoApplicationActivity;
import com.reactlibrary.Models.Version;

import static com.reactlibrary.Constain.StatusConstains.*;

public class NoApplication extends Upgrade implements IUpgrade {

    public NoApplication() {
    }

    public NoApplication(Context context) {
        super(context);
    }

    public NoApplication(Context context, Version version) {
        super(context, version);
    }


    @Override
    public void startProcess() {
        Version nVersion = getVersion();
        Context context = getContext();
        Activity activity = getActivity();
        Intent modalIntent = new Intent(context, NoApplicationActivity.class);
        modalIntent.setFlags(modalIntent.FLAG_ACTIVITY_NEW_TASK);
        //System.out.println(nVersion.status);
        if (nVersion.status.equals(NO_APPLICATION_DATA)) {
            modalIntent.putExtra("status", "no_application_data");
            modalIntent.putExtra("message", "Bu uygulamanın Genel Bilgileri Oluşturulmamış veya hatalı oluşturulmuş");
        } else if (nVersion.status.equals(NO_VERSION)) {
            modalIntent.putExtra("status", "no_version");
            modalIntent.putExtra("message", "Uygulamanın versiyon bilgisi oluşturulmamış");
        } else if (nVersion.status.equals(APPLICATION_ERROR)) {
            modalIntent.putExtra("status", "application_error");
            modalIntent.putExtra("message", "Uygulamanın Genel bilgileri Hatalı");
        } else if (nVersion.status.equals(NO_PACKAGE)) {
            modalIntent.putExtra("status", "no_package");
            modalIntent.putExtra("message", "Bu uygulama için oluşturulan paket ya IBB tarafından kullanımdan kaldırılmış yada hiş oluşturulmamış");
        }else if (nVersion.status.equals(APPLICATION_DELETE)) {
            modalIntent.putExtra("status", "application_delete");
            modalIntent.putExtra("message", "Bu Uygulama IBB tarafından Kullanımdan Kaldırılmış");
        }else if (nVersion.status.equals(NO_APPLICATION)) {
            modalIntent.putExtra("status", "no_application");
            modalIntent.putExtra("message", "Böyle bir uygulama IBB tarafından desteklenmiyor.");
        }
        context.startActivity(modalIntent);
        activity.finish();

    }
}

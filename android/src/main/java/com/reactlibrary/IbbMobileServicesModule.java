package com.reactlibrary;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
/*
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
*/
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.reactlibrary.Activity.DownloadActivity;
import com.reactlibrary.Api.ManagerAll;
import com.reactlibrary.Models.Version;
import com.reactlibrary.Module.IBBInfo;
import com.reactlibrary.Upgrade.IntegrationManager;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;
import static com.reactlibrary.Constain.ApplicationConstains.*;

public class IbbMobileServicesModule extends ReactContextBaseJavaModule {

    private String applicationID;
    private IBBInfo ibbInfo;

    private Socket socket;
    {
        try {
            socket = IO.socket("http://10.4.240.65:5000");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private final ReactApplicationContext reactContext;
    SharedPreferences sharedPreferences;
    private final int STORAGE_PERMISSION_CODE = 100;

    private Version version;
    Intent modalIntent;
    private long lastDownload = -1L;
    public IbbMobileServicesModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "IbbMobileServices";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("DURATION_SHORT_KEY", Toast.LENGTH_SHORT);
        constants.put("DURATION_LONG_KEY", Toast.LENGTH_LONG);
        constants.put("AUTO_UPGRADE", AUTO_UPGRADE);
        constants.put("CHECK_VERSION", CHECK_VERSION);

        return constants;
    }

    //Başlangıç methodunda ilgili dataları alıp react içine gönderiyoruz.Promis üzerinden göndermek gerekli
    @ReactMethod
    public void init(String applicationID, final Promise promise) {

        final String finalApplicationID = applicationID;



        sharedPreferences = getCurrentActivity().getSharedPreferences(reactContext.getPackageName(), reactContext.MODE_PRIVATE);
        String package_string = "android" + "." + reactContext.getPackageName();

        socket.connect();
        ibbInfo = new IBBInfo(reactContext);
        String data[] = new String[]{applicationID, reactContext.getPackageName(), ibbInfo.getDeviceID(), ibbInfo.getConnectionID()};
        socket.emit("application_info", data);

        LifecycleEventListener lifecycleEventListener = new LifecycleEventListener() {
            @Override
            public void onHostResume() {

            }

            @Override
            public void onHostPause() {

            }

            @Override
            public void onHostDestroy() {

                socket.disconnect();
                System.out.println("Uygulama Kapanıyor Kapanıyor");
            }
        };
        getReactApplicationContext().addLifecycleEventListener(lifecycleEventListener);

        if (CHECK_VERSION) {
            final Call<Version> Application_lastest_version = ManagerAll.getInstance().getVersionInfo(applicationID, package_string);
            Application_lastest_version.enqueue(new retrofit2.Callback<Version>() {
                @Override
                public void onResponse(Call<Version> call, Response<Version> response) {
                    System.out.println(response.raw().request().url());
                    if (response.body() != null) {
                        version = response.body();
                        try {
                            IntegrationManager integrationManager = new IntegrationManager(reactContext, version);
                            integrationManager.setCurrnet_activity(getCurrentActivity());
                            integrationManager.setState();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Aplikasyon Bilgisi Yok");
                        IntegrationManager integrationManager = new IntegrationManager(reactContext, null);
                        integrationManager.noAppProcess();
                    }
                }

                @Override
                public void onFailure(Call<Version> call, Throwable t) {
                    System.out.println("Aplikasyon Akredite Olmamış");
                    System.out.println(t.getMessage());
                    IntegrationManager integrationManager = new IntegrationManager(reactContext, version);
                    integrationManager.noAppProcess();
                }
            });
        }
    }



    @ReactMethod
    public void update_application() {

    }

    @ReactMethod
    public void get_build_version() {

    }

    @ReactMethod
    public void get_package_name() {

    }
}

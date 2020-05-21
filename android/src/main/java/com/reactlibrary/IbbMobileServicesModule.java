package com.reactlibrary;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.reactlibrary.activity.DownloadActivity;
import com.reactlibrary.fragment.FirstFragment;
import com.reactlibrary.helper.VersionHelper;
import com.reactlibrary.module.ApplicationInfo;
import com.reactlibrary.module.IBBInfo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.reactlibrary.constain.Constains.CURRENT_VERSION;
import static com.reactlibrary.constain.Constains.DEVICEID;
import static com.reactlibrary.constain.Constains.PACKAGE_NAME;
import static com.reactlibrary.constain.Constains.REGEDIT;

public class IbbMobileServicesModule extends ReactContextBaseJavaModule  {

    private final ReactApplicationContext reactContext;

    private final int STORAGE_PERMISSION_CODE = 100;

    private static final int IMAGE_PICKER_REQUEST = 1;
    private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
    private static final String E_PICKER_CANCELLED = "E_PICKER_CANCELLED";
    private static final String E_FAILED_TO_SHOW_PICKER = "E_FAILED_TO_SHOW_PICKER";
    private static final String E_NO_IMAGE_DATA_FOUND = "E_NO_IMAGE_DATA_FOUND";

    private  String DOWNLOADURL = "";
    private  String DIRNAME = "";
    private  String FILENAME = "";

    Intent modalIntent;
    private long lastDownload=-1L;

    public IbbMobileServicesModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }


    @Override
    public String getName() {
        return "IbbMobileServices";
    }

    @ReactMethod
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
        // TODO: Implement some actually useful functionality
        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    }


    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("DURATION_SHORT_KEY", Toast.LENGTH_SHORT);
        constants.put("DURATION_LONG_KEY", Toast.LENGTH_LONG);
        return constants;
    }

    //Başlangıç methodunda ilgili dataları alıp react içine gönderiyoruz.Promis üzerinden göndermek gerekli
    @ReactMethod
    public void init(String package_name,final Promise promise){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        try {

            db.collection("applications")
                    .whereEqualTo(PACKAGE_NAME,package_name)
                    .limit(1)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            final IBBInfo IBB = new IBBInfo(reactContext);
                            if(!queryDocumentSnapshots.isEmpty()){
                                for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                                    ApplicationInfo applicationInfo = new ApplicationInfo(document);
                                    IBB.setApplicationInfo(applicationInfo);
                                }
                                promise.resolve(IBB.result());
                            }else {
                                promise.reject("ERROR","Herhangi bir bulunmamaktadır");
                            }
                        }
                    }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("onFailure");

                }
            });
        }catch (Exception e){
            promise.reject("ERROR",e.getMessage());
        }
    }

    //Cihaz durumuna göre kayıtlı son versiyon bilgisi getirilir.
    @ReactMethod
    public void current_data(final String deviceID, final String package_name,final Double published_version, final Promise promise){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        try{
            final CollectionReference regEdit = db.collection(REGEDIT);
                    regEdit
                    .whereEqualTo(DEVICEID,deviceID)
                    .whereEqualTo(PACKAGE_NAME,package_name)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(queryDocumentSnapshots.isEmpty()){
                               //Herhangi bir kayıt yok ise

                                Map<String,Object> appData = new HashMap<>();

                                appData.put(DEVICEID,deviceID);
                                appData.put(PACKAGE_NAME, package_name);
                                appData.put(CURRENT_VERSION,published_version);
                                //Bu kısımda Applikasyon ile ilgili daha fazla verileri de kayıt edebiliriz
                                regEdit.document().set(appData);
                                WritableMap map = Arguments.createMap();

                                map.putString(DEVICEID, deviceID);
                                map.putString(PACKAGE_NAME, package_name);
                                map.putDouble(CURRENT_VERSION, published_version);
                                promise.resolve(map);
                            }else{
                                //Cihaz ve uygulama kayıtlı ise
                                WritableMap map = Arguments.createMap();
                                for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                                    map.putInt("current_version", document.getLong(CURRENT_VERSION).intValue());
                                }
                              promise.resolve(map);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    promise.reject("ERROR",e.getMessage());
                }
            });


        }catch (Exception e){
            promise.reject("ERROR",e.getMessage());
        }
    }

    @ReactMethod
    public void check_version(Double current_version,Double published_version,Callback resultCallback){

        VersionHelper vh = new VersionHelper();
        if(vh.checkVersion(current_version,published_version)){
            resultCallback.invoke(true);
        }else {
            resultCallback.invoke(false);
        }
    }

    @ReactMethod
    public void check_permission(final Promise promise){
        try{
            if(Build.VERSION.SDK_INT >=23){
                if(ContextCompat.checkSelfPermission(getCurrentActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    PermissionAwareActivity activity = (PermissionAwareActivity) getCurrentActivity();
                    if (activity == null) {
                        // Handle null case
                    }
                    if(ActivityCompat.shouldShowRequestPermissionRationale(getCurrentActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        System.out.println("Tekrar Sor");
                        promise.resolve(false);
                    }else{
                        activity.requestPermissions(
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                STORAGE_PERMISSION_CODE,
                                new PermissionListener() {
                                    @Override
                                    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
                                        switch (requestCode){
                                            case STORAGE_PERMISSION_CODE:
                                                for(String permission : permissions){
                                                    for(int i = 0 ; i< permissions.length;i++){
                                                        if(permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) || permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE) ){
                                                            if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                                                                promise.resolve(true);
                                                            }else {
                                                                promise.resolve(false);
                                                            }
                                                        }
                                                    }
                                                }
                                                break;
                                            default:
                                                return  true;
                                        }
                                        return true;
                                    }
                                }
                        );
                    }
                }else {
                    promise.resolve(true);
                }

            /*PermissionHelper permissionHelper = new PermissionHelper();
            if(permissionHelper.hasPermission(reactContext,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE})){
                System.out.println("İzinler verilmiştir.");
            }else {
                System.out.println("İzinler verilmemiş.");
            }*/

            }else {
                System.out.println("SDK ile alınıyor");
                promise.resolve(true);
            }
        }catch (Exception e){
            promise.reject("ERROR",e.getMessage());
        }

    }

    @ReactMethod
    public void update_application(String download_url,String application_name){

        DOWNLOADURL = download_url;
        DIRNAME = application_name;
        FILENAME = application_name+".apk";

        modalIntent = new Intent(reactContext, DownloadActivity.class);
        modalIntent.setFlags(modalIntent.FLAG_ACTIVITY_NEW_TASK);
        modalIntent.putExtra("downloadurl",DOWNLOADURL);
        modalIntent.putExtra("dirname",DIRNAME);
        modalIntent.putExtra("filename",FILENAME);
        reactContext.startActivityForResult(modalIntent,100,null);
    }




}

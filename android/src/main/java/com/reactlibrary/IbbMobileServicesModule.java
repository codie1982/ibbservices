package com.reactlibrary;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.react.bridge.Callback;
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
import com.reactlibrary.Activity.DownloadActivity;
import com.reactlibrary.Api.ManagerAll;
import com.reactlibrary.Models.Version;
import com.reactlibrary.Upgrade.IntegrationManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

import static com.reactlibrary.Constain.ApplicationConstains.*;

public class IbbMobileServicesModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    SharedPreferences sharedPreferences;
    private final static Boolean setupapplication = false;

    private final int STORAGE_PERMISSION_CODE = 100;

    private static final int IMAGE_PICKER_REQUEST = 1;
    private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
    private static final String E_PICKER_CANCELLED = "E_PICKER_CANCELLED";
    private static final String E_FAILED_TO_SHOW_PICKER = "E_FAILED_TO_SHOW_PICKER";
    private static final String E_NO_IMAGE_DATA_FOUND = "E_NO_IMAGE_DATA_FOUND";

    private String DOWNLOADURL = "";
    private String DIRNAME = "";
    private String FILENAME = "";

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
        sharedPreferences = getCurrentActivity().getSharedPreferences(reactContext.getPackageName(), reactContext.MODE_PRIVATE);
        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        String package_string = "android" + "." + reactContext.getPackageName();
     /*   final Call<Cosmos> cosmosCall = ManagerAll.getInstance().getCosmosInfo();
        cosmosCall.enqueue(new retrofit2.Callback<Cosmos>() {
            @Override
            public void onResponse(Call<Cosmos> call, Response<Cosmos> response) {
                System.out.println("Başarılı");
                Cosmos cosmos = response.body();
                System.out.println("Uygulama Sayısı "+cosmos.snapshots);
            }
            @Override
            public void onFailure(Call<Cosmos> call, Throwable t) {
                System.out.println("Başarısız");
                System.out.println(t.getMessage());
            }
        });*/
        if (CHECK_VERSION) {
            final Call<Version> Application_lastest_version = ManagerAll.getInstance().getVersionInfo(applicationID, package_string);
            Application_lastest_version.enqueue(new retrofit2.Callback<Version>() {
                @Override
                public void onResponse(Call<Version> call, Response<Version> response) {
                    System.out.println(response.raw().request().url());
                    if (response.body() != null) {
                        version = response.body();
                        try {
                            IntegrationManager integrationManager = new IntegrationManager(reactContext,version);
                            integrationManager.setState();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Aplikasyon Bilgisi Yok");
                        IntegrationManager integrationManager = new IntegrationManager(reactContext,null);
                        integrationManager.noApp();
                    }
                }
                @Override
                public void onFailure(Call<Version> call, Throwable t) {
                    System.out.println("Aplikasyon Akredite Olmamış");
                    System.out.println(t.getMessage());
                    IntegrationManager integrationManager = new IntegrationManager(reactContext,version);
                    integrationManager.noApp();
                }
            });
        }
    }
    @ReactMethod
    public void check_version() {

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

    //Firebase Kodları
    /*
        //Başlangıç methodunda ilgili dataları alıp react içine gönderiyoruz.Promis üzerinden göndermek gerekli
        @ReactMethod
        public void init2(String applicationID,final Promise promise){
            sharedPreferences = getCurrentActivity().getSharedPreferences(reactContext.getPackageName(), reactContext.MODE_PRIVATE);
            FirebaseFirestore db = FirebaseFirestore.getInstance();

           try {
                db.collection("cosmos")
                        .whereEqualTo(PACKAGE_NAME,applicationID)
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
                        System.out.println(e.getMessage());
                    }
                });
            }catch (Exception e){
                promise.reject("ERROR",e.getMessage());
            }
        }

        //Cihaz durumuna göre kayıtlı son versiyon bilgisi getirilir.
        @ReactMethod
        public void current_data(final String deviceID, final String package_name,
                                 final Double published_version, final Promise promise){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            final CollectionReference regEdit = db.collection(REGEDIT);

            try{
                //Burada cookie var ise db ye kayıt yapmayacaz
                //cookie yok ise kayıt yapacaz
                final Boolean setupApplication = sharedPreferences.getBoolean(SETUP,false);
                        if(!setupApplication){
                            System.out.println("ilk kurulum");
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(SETUP,true);
                            editor.commit();
                        }
                             System.out.println("Normal Başlangıç");
                              regEdit
                                    .whereEqualTo(DEVICEID,deviceID)
                                    .whereEqualTo(PACKAGE_NAME,package_name)
                                    .limit(1)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                           String documentID = "";
                                            final WritableMap reactData = Arguments.createMap();
                                            if(queryDocumentSnapshots.isEmpty()){
                                                Map<String,Object> registerData = new HashMap<>();
                                                registerData.put(DEVICEID,deviceID);
                                                registerData.put(PACKAGE_NAME, package_name);
                                                registerData.put(CURRENT_VERSION,published_version);
                                                regEdit.document().set(registerData);
                                                reactData.putDouble(CURRENT_VERSION, published_version);
                                            }else{
                                                for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                                                    documentID = document.getId();
                                                    break;
                                                }
                                                //Aplikasyon cihaza yeni kuruluyorsa ve daha önceden cihaz ile ilgili bir kayıt var ise o kaydı güncelle
                                                if(!setupApplication){
                                                    Map<String,Object> appData = new HashMap<>();
                                                    appData.put(DEVICEID,deviceID);
                                                    appData.put(PACKAGE_NAME, package_name);
                                                    appData.put(CURRENT_VERSION,published_version);

                                                    final String finalDocumentID = documentID;
                                                    regEdit.document(documentID).update(appData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            regEdit.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                                    for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                                                                       if(document.getId() == finalDocumentID){
                                                                           reactData.putString(DEVICEID, document.getString("deviceid"));
                                                                           reactData.putString(PACKAGE_NAME, document.getString("package_name"));
                                                                           reactData.putDouble(CURRENT_VERSION, document.getLong("current_version"));
                                                                           promise.resolve(reactData);
                                                                       }
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    });
                                                }else{
                                                    regEdit.document(documentID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if(task.isSuccessful()){
                                                            DocumentSnapshot document = task.getResult();
                                                            reactData.putString(DEVICEID, document.getString("deviceid"));
                                                            reactData.putString(PACKAGE_NAME, document.getString("package_name"));
                                                            reactData.putDouble(CURRENT_VERSION, document.getLong("current_version"));
                                                            promise.resolve(reactData);
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                        }
                                    });
            }catch (Exception e){
                promise.reject("ERROR",e.getMessage());
            }
        }
    */
    @ReactMethod
    public void check_version(Double current_version, Double published_version, Callback resultCallback) {

       /* VersionHelper vh = new VersionHelper();
        if (vh.checkVersion(current_version, published_version)) {
            resultCallback.invoke(true);
        } else {
            resultCallback.invoke(false);
        }*/
    }

    @ReactMethod
    public void check_permission(final Promise promise) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                if (ContextCompat.checkSelfPermission(getCurrentActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    PermissionAwareActivity activity = (PermissionAwareActivity) getCurrentActivity();
                    if (activity == null) {
                        // Handle null case
                    }
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getCurrentActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        System.out.println("Tekrar Sor");
                        promise.resolve(false);
                    } else {
                        activity.requestPermissions(
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                STORAGE_PERMISSION_CODE,
                                new PermissionListener() {
                                    @Override
                                    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
                                        switch (requestCode) {
                                            case STORAGE_PERMISSION_CODE:
                                                for (String permission : permissions) {
                                                    for (int i = 0; i < permissions.length; i++) {
                                                        if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) || permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                                                promise.resolve(true);
                                                            } else {
                                                                promise.resolve(false);
                                                            }
                                                        }
                                                    }
                                                }
                                                break;
                                            default:
                                                return true;
                                        }
                                        return true;
                                    }
                                }
                        );
                    }
                } else {
                    promise.resolve(true);
                }

            /*PermissionHelper permissionHelper = new PermissionHelper();
            if(permissionHelper.hasPermission(reactContext,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE})){
                System.out.println("İzinler verilmiştir.");
            }else {
                System.out.println("İzinler verilmemiş.");
            }*/

            } else {
                System.out.println("SDK ile alınıyor");
                promise.resolve(true);
            }
        } catch (Exception e) {
            promise.reject("ERROR", e.getMessage());
        }

    }

    @ReactMethod
    public void update_application(String download_url,
                                   String application_name,
                                   String deviceID,
                                   Double published_version,
                                   String package_name) {

        DOWNLOADURL = download_url;
        DIRNAME = application_name;
        FILENAME = application_name + ".apk";

        modalIntent = new Intent(reactContext, DownloadActivity.class);
        modalIntent.setFlags(modalIntent.FLAG_ACTIVITY_NEW_TASK);
        modalIntent.putExtra("downloadurl", DOWNLOADURL);
        modalIntent.putExtra("dirname", DIRNAME);
        modalIntent.putExtra("filename", FILENAME);
        modalIntent.putExtra("deviceid", deviceID);
        modalIntent.putExtra("published_version", published_version);
        modalIntent.putExtra("package_name", package_name);
        reactContext.startActivityForResult(modalIntent, 100, null);
    }


}

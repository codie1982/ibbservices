package com.reactlibrary.Services;

import androidx.annotation.NonNull;

//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.Timestamp;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.Query;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
import com.reactlibrary.Module.ApplicationInfo;

import java.util.HashMap;
import java.util.Map;

import static com.reactlibrary.Constain.Constains.*;

public class ApplicationServices {
    //Application services ile DBye kayıt işlemlerini ve DB sorgulama işlemlerini yapmamız gerekiyor.
  //  private FirebaseFirestore db;
    private String deviceID;
    private String packageName;

    public Long getCurrent_version() {
        return current_version;
    }

    public void setCurrent_version(Long current_version) {
        this.current_version = current_version;
    }

    private Long current_version;
    public ApplicationInfo applicationInfo;
/*
    public ApplicationServices(String packageName,String deviceID) {
        db = FirebaseFirestore.getInstance();
        this.packageName = packageName; // paket ismi ile o uygulamanın bilgilerini almamız gerekiyor.
        this.deviceID = deviceID;
        getApplicationData();
        //Aplikasyon bilgilerini okumak
    }

    public void isRegister(final String deviceID, final  String packageName){
        final CollectionReference regEdit = db.collection(REGEDIT);
        Query query = regEdit
                .whereEqualTo(DEVICEID,this.deviceID)
                .whereEqualTo(PACKAGE_NAME,packageName)
                .limit(1);
        query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty()){
                    //ilgili kayıt var ise
                    Map<String,Object> appData = new HashMap<>();

                    appData.put(DEVICEID,deviceID);
                    appData.put(PACKAGE_NAME, packageName);
                    appData.put(CURRENT_VERSION,getApplicationInfo().getCurrent_version());
                    //Bu kısımda Applikasyon ile ilgili daha fazla verileri de kayıt edebiliriz
                    regEdit.document().set(appData);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Collection Yok");
            }
        });

    }

    public void currentVersion(final String deviceID, final  String packageName){
        final Long[] current_version = {null};
        Task<QuerySnapshot> CR = db.collection(REGEDIT)
                .whereEqualTo(PACKAGE_NAME,packageName)
                .whereEqualTo(DEVICEID,deviceID)
                .limit(1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            for(QueryDocumentSnapshot document :queryDocumentSnapshots){
                                current_version[0] = document.getLong(CURRENT_VERSION);
                                System.out.println(current_version[0]);
                                setCurrent_version(current_version[0]);
                            }
                        }

                    }
                });


    }
    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }
    public void setApplicationInfo(ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
    }
    private void getApplicationData() {
        //Aplikasyon bilgileri (Son veriler)
        CollectionReference collectionReference  = db.collection("applications");
        Query query = collectionReference.whereEqualTo(PACKAGE_NAME,packageName).limit(1);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    for(QueryDocumentSnapshot document : queryDocumentSnapshots){

                        String documentID = document.getId();
                        String application_market = document.getString("application_market");
                        String application_name = document.getString("application_name");
                        String category = document.getString("category");
                        String description = document.getString("description");
                        String download_link = document.getString("download_link");
                        String logo = document.getString("logo");
                        String package_name = document.getString("package_name");
                        String published = document.getString("published");
                        String short_title = document.getString("short_title");
                        String title = document.getString("title");
                        String type = document.getString("type");
                        Timestamp update_time = document.getTimestamp("update_time");
                        Long published_version = document.getLong("version");

                        ApplicationInfo appInfo = new ApplicationInfo();
                        appInfo.setApplication_market(application_market);
                        appInfo.setApplication_name(application_name);
                        appInfo.setCategory(category);
                        appInfo.setDescription(description);
                        appInfo.setDownload_link(download_link);
                        appInfo.setLogo(logo);
                        appInfo.setPackage_name(package_name);
                        appInfo.setPublished(published);
                        appInfo.setShort_title(short_title);
                        appInfo.setTitle(title);
                        appInfo.setType(type);
                        appInfo.setUpdate_time(update_time);
                        appInfo.setPublished_version(published_version);
                        setApplicationInfo(appInfo);

                        break;

                    }

                }else {
                    //Uygulama bilgilerine erişemez ise herhangi bir sorgulama yapılmasına gerek olmaz
                    System.out.println("Uygulama Bilgilerine erişilemiyor");
                }
            }
        });
    }
*/

}

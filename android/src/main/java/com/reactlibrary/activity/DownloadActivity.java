package com.reactlibrary.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.react.ReactActivity;
import com.reactlibrary.Fragment.CriticalDownloadFragment;
import com.reactlibrary.Fragment.ImportantDownloadFragment;
import com.reactlibrary.Fragment.LowImportantDownloadFragment;
import com.reactlibrary.Fragment.NoTypcalDownloadFragment;
import com.reactlibrary.Fragment.RealTimeDownloadFragment;
import com.reactlibrary.Fragment.WarningDownloadFragment;
import com.reactlibrary.R;
import com.reactlibrary.Fragment.DownloadFragment;
import static com.reactlibrary.Constain.VersionConstains.*;

public class DownloadActivity extends ReactActivity {

    private Long UPGRADE_TYPE =0L;
    public static long lastDownload=-1L;
    private  String DIRNAME;
    private  String FILENAME;
    private  String DOWNLOADURL;

    String DEVICEID;
    String PACKAGE_NAME;
    Double PUBLISHED_VERSION;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

       Intent intent =  getIntent();
        UPGRADE_TYPE = intent.getLongExtra("upgrade_type",0);

//        lastDownload = intent.getLongExtra("lastDownload",0);
//        DIRNAME = intent.getStringExtra("dirname");
//        FILENAME = intent.getStringExtra("filename");
//        DOWNLOADURL = intent.getStringExtra("downloadurl");
//        DEVICEID = intent.getStringExtra("deviceid");
//        PACKAGE_NAME = intent.getStringExtra("package_name");
//        PUBLISHED_VERSION = intent.getDoubleExtra("published_version",0);

        if (UPGRADE_TYPE == CRITICAL) {
            CriticalDownloadFragment criticalDownloadFragment = new CriticalDownloadFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frameDownload,criticalDownloadFragment,"criticalDownload");
            transaction.commit();
        }else  if(UPGRADE_TYPE == IMPORTANT){
            ImportantDownloadFragment importantDownloadFragment = new ImportantDownloadFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frameDownload,importantDownloadFragment,"importantDownload");
            transaction.commit();
        }else  if(UPGRADE_TYPE == WARNING){
            WarningDownloadFragment warningDownloadFragment = new WarningDownloadFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frameDownload,warningDownloadFragment,"warningDownload");
            transaction.commit();
        }else  if(UPGRADE_TYPE == LOWIMPORTANT){
            LowImportantDownloadFragment lowImportantDownloadFragment = new LowImportantDownloadFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frameDownload,lowImportantDownloadFragment,"lowimportantDownload");
            transaction.commit();
        }else  if(UPGRADE_TYPE == REALTIME){
            RealTimeDownloadFragment realTimeDownloadFragment = new RealTimeDownloadFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frameDownload,realTimeDownloadFragment,"realTimeDownload");
            transaction.commit();
        }else{
            NoTypcalDownloadFragment noTypcalDownloadFragment = new NoTypcalDownloadFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frameDownload,noTypcalDownloadFragment,"realTimeDownload");
            transaction.commit();
        }
    }

    public Bundle getMyData() {
        Bundle bundle = new Bundle();
        bundle.putString("downloadurl",DOWNLOADURL);
        bundle.putString("dirname",DIRNAME);
        bundle.putString("filename",FILENAME);
        bundle.putString("deviceid",DEVICEID);
        bundle.putString("package_name",PACKAGE_NAME);
        bundle.putDouble("published_version",PUBLISHED_VERSION);
        return bundle;
    }
}

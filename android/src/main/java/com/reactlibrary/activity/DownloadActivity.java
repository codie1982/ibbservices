package com.reactlibrary.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.facebook.react.ReactActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.reactlibrary.R;
import com.reactlibrary.fragment.FirstFragment;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class DownloadActivity extends ReactActivity {

    public static long lastDownload=-1L;
    private  String DIRNAME;
    private  String FILENAME;
    private  String DOWNLOADURL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_modal);

       Intent intent =  getIntent();
       lastDownload = intent.getLongExtra("lastDownload",0);
       DIRNAME = intent.getStringExtra("dirname");
       FILENAME = intent.getStringExtra("filename");
       DOWNLOADURL = intent.getStringExtra("downloadurl");

    }

    public Bundle getMyData() {
        Bundle bundle = new Bundle();
        bundle.putString("downloadurl",DOWNLOADURL);
        bundle.putString("dirname",DIRNAME);
        bundle.putString("filename",FILENAME);
        return bundle;
    }
}

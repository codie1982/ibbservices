package com.reactlibrary.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.react.ReactActivity;
import com.reactlibrary.R;
import com.reactlibrary.Fragment.DownloadFragment;

//İzinleri Burada almamız gerekiyor
public class DownloadActivity extends ReactActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        intent = getIntent();

        DownloadFragment downloadFragment = new DownloadFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameDownload, downloadFragment, "downloadfragment");
        transaction.commit();
    }



    public Bundle getMyData() {
        Bundle bundle = new Bundle();
        bundle.putLong("upgrade_type", intent.getLongExtra("upgrade_type", 0));
        bundle.putString("id", intent.getStringExtra("id"));
        bundle.putString("download_url", intent.getStringExtra("download_url"));
        bundle.putString("title", intent.getStringExtra("title"));
        bundle.putString("title_description", intent.getStringExtra("title_description"));
        bundle.putInt("color", intent.getIntExtra("color",0));
        bundle.putString("version_notes", intent.getStringExtra("version_notes"));
        bundle.putString("public_time", intent.getStringExtra("public_time"));
        bundle.putString("version_title", intent.getStringExtra("version_title"));

        bundle.putString("version_string", intent.getStringExtra("version_string"));
        bundle.putString("publish_version", intent.getStringExtra("publish_version"));


        bundle.putLong("current_version", intent.getLongExtra("current_version",0L));
        bundle.putString("current_version_string", intent.getStringExtra("current_version_string"));



        bundle.putString("publisher", intent.getStringExtra("publisher"));
        bundle.putLong("download_count", intent.getLongExtra("download_count", 0));
        bundle.putString("downloadLogoUrl", intent.getStringExtra("downloadLogoUrl"));
        bundle.putString("application_name", intent.getStringExtra("application_name"));
        bundle.putString("application_title", intent.getStringExtra("application_title"));
        bundle.putString("application_short_title", intent.getStringExtra("application_short_title"));
        bundle.putString("application_description", intent.getStringExtra("application_description"));
        bundle.putString("category", intent.getStringExtra("category"));

        return bundle;
    }
}

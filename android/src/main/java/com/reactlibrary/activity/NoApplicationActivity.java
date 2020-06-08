package com.reactlibrary.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.reactlibrary.Fragment.DownloadFragment;
import com.reactlibrary.Fragment.noApplicationFragment;
import com.reactlibrary.R;

public class NoApplicationActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_application);

        intent = getIntent();

        noApplicationFragment noApplicationFragment = new noApplicationFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.noApplicationFrame, noApplicationFragment, "noApplicationFragment");
        transaction.commit();
    }


    public Bundle getNoApplicationdata() {
        Bundle bundle = new Bundle();
        bundle.putString("status", intent.getStringExtra("status"));
        bundle.putString("message", intent.getStringExtra("message"));
        return bundle;
    }
}
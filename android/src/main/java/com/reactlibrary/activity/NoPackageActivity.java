package com.reactlibrary.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.reactlibrary.R;

public class NoPackageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_package);
    }
}
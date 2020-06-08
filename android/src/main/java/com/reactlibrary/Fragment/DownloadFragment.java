package com.reactlibrary.Fragment;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.reactlibrary.GameFragment;
import com.reactlibrary.Helper.PermissionHelper;
import com.reactlibrary.R;
import com.reactlibrary.Activity.DownloadActivity;

import java.io.File;
import java.text.DecimalFormat;

import com.squareup.picasso.Picasso;

import static com.reactlibrary.Constain.VersionConstains.*;

public class DownloadFragment extends PermissionHelper {
    DownloadActivity downloadActivity;


    String DIRNAME;
    String FILENAME;
    Long LASTDOWNLOAD;
    String DOWNLOADURL;


    TextView txtApplicationTitle;
    TextView txtApplicationShortTitle;
    TextView txtPublisher;
    TextView txtVersionNotes;
    TextView txtVersionTitle;
    TextView txtVersionTitleDescription;
    TextView txtVersionString;
    TextView txtCurrentVersionString;
    ImageView imgApplicationLogo;
    ProgressBar pgsApplicationDownloading;
    TextView txtOneway;
    Button btnVersionStart;
    static Context mcontext;

    private String id;
    private String category;
    private String application_description;
    private String application_short_title;
    private String application_title;
    private String application_name;
    private String downloadLogoUrl;
    private long download_count;
    private String publisher;
    private int typeColor;
    private String title;
    private String download_url;
    private String version_notes;
    private String public_time;
    private String version_title;
    private String publish_version_string;
    private long upgrade_type;
    private String title_description;
    private String current_version_string;
    private long current_version;
    private long publish_version;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        downloadActivity = (DownloadActivity) getActivity();
        assert downloadActivity != null;

        imgApplicationLogo = view.findViewById(R.id.imgApplicationLogo);
        txtApplicationTitle = view.findViewById(R.id.txtApplicationTitle);
        txtApplicationShortTitle = view.findViewById(R.id.txtApplicationShortTitle);
        txtPublisher = view.findViewById(R.id.txtPublisher);
        txtVersionNotes = view.findViewById(R.id.txtVersionNotes);
        txtVersionTitle = view.findViewById(R.id.txtVersionTitle);
        txtVersionTitleDescription = view.findViewById(R.id.txtVersionTitleDescription);
        txtVersionString = view.findViewById(R.id.txtVersionString);
        txtCurrentVersionString = view.findViewById(R.id.txtCurrentVersionString);
        txtOneway = view.findViewById(R.id.txtOneway);
        btnVersionStart = view.findViewById(R.id.btnVersionStart);

        pgsApplicationDownloading = view.findViewById(R.id.pgsApplicationDownloading);

        Bundle mydataBundle = downloadActivity.getMyData();
        upgrade_type = mydataBundle.getLong("upgrade_type", 0L);
        id = mydataBundle.getString("id");
        download_url = mydataBundle.getString("download_url");
        title = mydataBundle.getString("title");
        title_description = mydataBundle.getString("title_description");
        typeColor = mydataBundle.getInt("color",0);
        publisher = mydataBundle.getString("publisher");
        download_count = mydataBundle.getLong("download_count", 0L);
        downloadLogoUrl = mydataBundle.getString("downloadLogoUrl");
        application_name = mydataBundle.getString("application_name");
        application_title = mydataBundle.getString("application_title");
        application_short_title = mydataBundle.getString("application_short_title");
        application_description = mydataBundle.getString("application_description");
        category = mydataBundle.getString("category");


        publish_version = mydataBundle.getLong("publish_version");
        publish_version_string = mydataBundle.getString("version_string");

        current_version = mydataBundle.getLong("current_version");
        current_version_string = mydataBundle.getString("current_version_string");

        version_notes = mydataBundle.getString("version_notes");
        public_time = mydataBundle.getString("public_time");
        version_title = mydataBundle.getString("version_title");

        //TODO:Bu kısımlarda daha esnek bir yapıda oluşturma yapmamız gerekli
        DIRNAME = id;
        FILENAME = id + ".apk";
        DOWNLOADURL = download_url;
        pgsApplicationDownloading.setProgress(0);

        mcontext = getContext();
        mcontext.registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pgsApplicationDownloading.getProgressDrawable().setColorFilter(
                typeColor, android.graphics.PorterDuff.Mode.SRC_IN);

        txtVersionTitle.setTextColor(typeColor);
        btnVersionStart.setBackgroundColor(typeColor);

        if (upgrade_type == CRITICAL) {

            btnVersionStart.setEnabled(true);

            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    ||
                    ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                startDownload();
                Toast.makeText(getContext(), "Güncellemeye Otomatik Başladı", Toast.LENGTH_SHORT).show();

            } else {
                String[] istenilenIzinler = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                this.izinIste(istenilenIzinler, 100);
            }
            if(current_version < publish_version){
                txtVersionString.setText(publish_version_string );
                txtCurrentVersionString.setText(current_version_string);
                txtCurrentVersionString.setTextColor(getResources().getColor(R.color.color_critical));
                txtVersionString.setTextColor(getResources().getColor(R.color.color_realtime));
                txtOneway.setText("--->");
            }else{
                txtVersionString.setText(current_version_string );
                txtCurrentVersionString.setText(publish_version_string);

                txtCurrentVersionString.setTextColor(getResources().getColor(R.color.color_critical));
                txtVersionString.setTextColor(getResources().getColor(R.color.color_realtime));
                txtOneway.setText("<---");
            }

            GameFragment gameFragment = new GameFragment();
            FragmentManager manager = getParentFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.gameFrameLayout, gameFragment, "gamefragment");
            transaction.commit();
            transaction.show(gameFragment);

        } else if (upgrade_type == IMPORTANT) {

            if(current_version < publish_version){
                txtVersionString.setText(publish_version_string );
                txtCurrentVersionString.setText(current_version_string);
                txtCurrentVersionString.setTextColor(getResources().getColor(R.color.color_important));
                txtVersionString.setTextColor(getResources().getColor(R.color.color_critical));
                txtOneway.setText("--->");
            }else{
                txtVersionString.setText(current_version_string );
                txtCurrentVersionString.setText(publish_version_string);

                txtCurrentVersionString.setTextColor(getResources().getColor(R.color.color_important));
                txtVersionString.setTextColor(getResources().getColor(R.color.color_critical));
                txtOneway.setText("<---");
            }


            btnVersionStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            ||
                            ContextCompat.checkSelfPermission(getActivity(),
                                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        startDownload();
                        Toast.makeText(getContext(), "Güncellemeye Başladı", Toast.LENGTH_SHORT).show();
                    } else {
                        String[] istenilenIzinler = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                        izinIste(istenilenIzinler, 300);
                    }
                }
            });
            if(current_version < publish_version){
                txtVersionString.setText(publish_version_string );
                txtCurrentVersionString.setText(current_version_string);
                txtCurrentVersionString.setTextColor(getResources().getColor(R.color.color_lowimportant));
                txtVersionString.setTextColor(getResources().getColor(R.color.color_critical));
                txtOneway.setText("--->");
            }else{
                txtVersionString.setText(current_version_string );
                txtCurrentVersionString.setText(publish_version_string);

                txtCurrentVersionString.setTextColor(getResources().getColor(R.color.color_lowimportant));
                txtVersionString.setTextColor(getResources().getColor(R.color.color_critical));
                txtOneway.setText("<---");
            }

            GameFragment gameFragment = new GameFragment();
            FragmentManager manager = getParentFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.gameFrameLayout, gameFragment, "gamefragment");
            transaction.commit();
            transaction.show(gameFragment);
        } else if (upgrade_type == LOWIMPORTANT) {



            btnVersionStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            ||
                            ContextCompat.checkSelfPermission(getActivity(),
                                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        startDownload();
                        Toast.makeText(getContext(), "Güncellemeye Başladı", Toast.LENGTH_SHORT).show();
                    } else {
                        String[] istenilenIzinler = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                        izinIste(istenilenIzinler, 300);
                    }
                }
            });
            if(current_version < publish_version){
                txtVersionString.setText(publish_version_string );
                txtCurrentVersionString.setText(current_version_string);
                txtCurrentVersionString.setTextColor(getResources().getColor(R.color.color_lowimportant));
                txtVersionString.setTextColor(getResources().getColor(R.color.color_critical));
                txtOneway.setText("--->");
            }else{
                txtVersionString.setText(current_version_string );
                txtCurrentVersionString.setText(publish_version_string);

                txtCurrentVersionString.setTextColor(getResources().getColor(R.color.color_lowimportant));
                txtVersionString.setTextColor(getResources().getColor(R.color.color_critical));
                txtOneway.setText("<---");
            }

            GameFragment gameFragment = new GameFragment();
            FragmentManager manager = getParentFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.gameFrameLayout, gameFragment, "gamefragment");
            transaction.commit();
            transaction.show(gameFragment);
        } else if (upgrade_type == REALTIME) {


            btnVersionStart.setEnabled(true);
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    ||
                    ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                startDownload();
                Toast.makeText(getContext(), "Güncellemeye Otomatik Başladı", Toast.LENGTH_SHORT).show();

            } else {
                String[] istenilenIzinler = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                this.izinIste(istenilenIzinler, 200);
            }
            if(current_version < publish_version){
                txtVersionString.setText(publish_version_string );
                txtCurrentVersionString.setText(current_version_string);
                txtCurrentVersionString.setTextColor(getResources().getColor(R.color.color_realtime));
                txtVersionString.setTextColor(getResources().getColor(R.color.color_critical));
                txtOneway.setText("--->");
            }else{
                txtVersionString.setText(current_version_string );
                txtCurrentVersionString.setText(publish_version_string);

                txtCurrentVersionString.setTextColor(getResources().getColor(R.color.color_realtime));
                txtVersionString.setTextColor(getResources().getColor(R.color.color_critical));
                txtOneway.setText("<---");
            }
            GameFragment gameFragment = new GameFragment();
            FragmentManager manager = getParentFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.gameFrameLayout, gameFragment, "gamefragment");
            transaction.commit();
            transaction.show(gameFragment);
        }

        Picasso.get().load(downloadLogoUrl).into(imgApplicationLogo);
        txtApplicationTitle.setText(application_title);
        txtApplicationShortTitle.setText(application_short_title);
        txtPublisher.setText(publisher);
        txtVersionNotes.setText(version_notes);

        txtVersionTitle.setText(title);
        txtVersionTitleDescription.setText(title_description);






    }

    private void setVersionlayout(Long publish_version,Long  current_version){


    }

    @Override
    public void izinVerildi(int requestCode) {
        if (requestCode == 100) {
            startDownload();
            Toast.makeText(getContext(), "Yazma ve Okuma için İzin verildi", Toast.LENGTH_SHORT);
        } else if (requestCode == 200) {
            startDownload();
            Toast.makeText(getContext(), "Yazma ve Okuma için İzin verildi", Toast.LENGTH_SHORT);

        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public void startDownload() {
        if (Build.VERSION.SDK_INT >= 24) {
            File folder = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), DIRNAME);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString(), DIRNAME + "/" + FILENAME);
            if (file.isFile()) {
                file.delete();
            }

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DOWNLOADURL))
                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setRequiresCharging(false)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                    .setDestinationInExternalFilesDir(downloadActivity,
                            Environment.DIRECTORY_DOCUMENTS + "/" + DIRNAME, FILENAME);

            final DownloadManager downloadManager = (DownloadManager) downloadActivity.getSystemService(downloadActivity.DOWNLOAD_SERVICE);
            LASTDOWNLOAD = downloadManager.enqueue(request);


            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean downloading = true;
                    while (downloading) {
                        //System.out.println(downloading);
                        DownloadManager.Query q = new DownloadManager.Query();
                        q.setFilterById(LASTDOWNLOAD);
                        Cursor cursor = downloadManager.query(q);
                        cursor.moveToFirst();

                        double bytes_downloaded = cursor.getDouble(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        double bytes_total = cursor.getDouble(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                        if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                            downloading = false;
                        }
                        final double dl_progress = (double) ((bytes_downloaded * 100l) / bytes_total) + 0.00001;

                        downloadActivity.runOnUiThread(new Runnable() {
                            DecimalFormat precision = new DecimalFormat("0.00");
                            @Override
                            public void run() {
                                pgsApplicationDownloading.setProgress((int) dl_progress);

                            }
                        });
                        statusMessage(cursor);
                        System.out.println(statusMessage(cursor));
                        cursor.close();
                    }
                }
            }).start();
        }
    }

    private String statusMessage(Cursor c) {
        String msg = "???";

        switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            case DownloadManager.STATUS_FAILED:
                pgsApplicationDownloading.setIndeterminate(true);
                msg = "Download failed!";
                break;

            case DownloadManager.STATUS_PAUSED:
                pgsApplicationDownloading.setIndeterminate(true);

                msg = "Download paused!";
                break;

            case DownloadManager.STATUS_PENDING:
                pgsApplicationDownloading.setIndeterminate(true);
                msg = "Download pending!";
                break;

            case DownloadManager.STATUS_RUNNING:
                pgsApplicationDownloading.setIndeterminate(false);
                msg = "Download in progress!";
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                pgsApplicationDownloading.setIndeterminate(false);

                msg = "Download complete!";
                break;
            default:
                msg = "Download is nowhere in sight";
                break;
        }
        return (msg);
    }

    public BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            if (LASTDOWNLOAD == id) {
                setupApplication(context, intent);
                getActivity().finish();
                System.out.println("İndirme Tamamlandı");
                Toast.makeText(context, "INDIRME TAMAMLANDI", Toast.LENGTH_SHORT).show();
            }
        }

        private void uninstallApplication(String package_name) {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + package_name));
            getContext().startActivity(intent);
        }

        public void setupApplication(Context context, Intent intent) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri contentUri = FileProvider.getUriForFile(context, "com.ibbtestaplication.provider",
                        new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString(),
                                DIRNAME + "/" + FILENAME));
                Intent install = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                install.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                install.setDataAndType(contentUri, "application/vnd.android.package-archive");
                context.startActivity(install);
                context.unregisterReceiver(this);
            } else {
                if (Build.VERSION.SDK_INT >= 23) {
                    Uri uri = Uri.fromFile(new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString(), DIRNAME + "/" + FILENAME));
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setDataAndType(uri, "application/vnd.android.package-archive");
                    context.startActivity(install);
                    context.unregisterReceiver(this);
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}

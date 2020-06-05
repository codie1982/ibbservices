package com.reactlibrary.Fragment;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.reactlibrary.R;
import com.reactlibrary.Activity.DownloadActivity;

import java.io.File;
import java.text.DecimalFormat;

import com.reactlibrary.Receiver.PackageRecevier;
public class DownloadFragment extends Fragment {
    DownloadActivity downloadActivity;
    TextView txtProgress;

    String DIRNAME;
    String FILENAME;
    Long LASTDOWNLOAD;
    String DOWNLOADURL;

    String DEVICEID;
    String PACKAGE_NAME;
    Double PUBLISHED_VERSION;

    ProgressBar progressBar;
    static Context mcontext ;

    SharedPreferences sharedPreferences;

    BroadcastReceiver packageRecevier;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {


        View view = inflater.inflate(R.layout.fragment_download, container, false);

        downloadActivity = (DownloadActivity) getActivity();

        assert downloadActivity != null;
        Bundle mydataBundle = downloadActivity.getMyData();
        DIRNAME = mydataBundle.getString("dirname");
        FILENAME = mydataBundle.getString("filename");
        DOWNLOADURL = mydataBundle.getString("downloadurl");

        DEVICEID = mydataBundle.getString("deviceid");
        PACKAGE_NAME = mydataBundle.getString("package_name");
        PUBLISHED_VERSION = mydataBundle.getDouble("published_version",0);

        progressBar = view.findViewById(R.id.pgsApplicationDownloading);
        txtProgress = view.findViewById(R.id.txtProgresstext);
        progressBar.setProgress(0);
        txtProgress.setText("% 0.00");

        IntentFilter intentFilter = new IntentFilter();
        //intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        //intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        //intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        intentFilter.addAction(Intent.ACTION_MY_PACKAGE_REPLACED);
        intentFilter.addDataScheme("package");

        mcontext = getContext();
       // mcontext.registerReceiver(onAppInstalled,intentFilter);p
        packageRecevier = new PackageRecevier();
        mcontext.registerReceiver(packageRecevier,intentFilter);
        mcontext.registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        return view;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startDownload();
        //setupApplication(mcontext);

       /* view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });*/
    }

    public void startDownload(){
        if(Build.VERSION.SDK_INT >=24){
            File folder = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), DIRNAME);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString(), DIRNAME+"/"+FILENAME);
            if(file.isFile()){
                file.delete();
            }

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DOWNLOADURL))
                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setRequiresCharging(false)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                    .setDestinationInExternalFilesDir(downloadActivity,
                            Environment.DIRECTORY_DOCUMENTS+"/"+DIRNAME,FILENAME);

            final DownloadManager downloadManager=(DownloadManager) downloadActivity.getSystemService(downloadActivity.DOWNLOAD_SERVICE);
            LASTDOWNLOAD=downloadManager.enqueue(request);


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
                                //System.out.println("ilerleme - " + dl_progress);
                                txtProgress.setText("% "+precision.format(dl_progress));
                                progressBar.setProgress((int) dl_progress);

                            }
                        });
                        statusMessage(cursor);
                        //System.out.println(statusMessage(cursor));
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
                msg = "Download failed!";
                break;

            case DownloadManager.STATUS_PAUSED:
                msg = "Download paused!";
                break;

            case DownloadManager.STATUS_PENDING:
                msg = "Download pending!";
                break;

            case DownloadManager.STATUS_RUNNING:
                msg = "Download in progress!";
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                msg = "Download complete!";
                break;
            default:
                msg = "Download is nowhere in sight";
                break;
        }
        return (msg);
    }


    public BroadcastReceiver onDownloadComplete=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            long id=intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);

            if(LASTDOWNLOAD==id){

                setupApplication(context,intent);
                getActivity().finish();
                System.out.println("İndirme Tamamlandı");
                Toast.makeText(context,"INDIRME TAMAMLANDI",Toast.LENGTH_SHORT).show();

            }
        }

        private void uninstallApplication(String package_name) {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:"+package_name));
            getContext().startActivity(intent);
        }
        public void setupApplication(Context context,Intent intent){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                Uri contentUri = FileProvider.getUriForFile(context,   "com.ibbtestaplication.provider",
                        new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString(), DIRNAME +"/"+ FILENAME));
                Intent install = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                install.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                install.setDataAndType(contentUri,"application/vnd.android.package-archive");
                context.startActivity(install);
                context.unregisterReceiver(this);
            }else{
                if(Build.VERSION.SDK_INT >=23){
                    Uri uri = Uri.fromFile(new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString(), DIRNAME+"/"+FILENAME));
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setDataAndType(uri, "application/vnd.android.package-archive");
                    context.startActivity(install);
                    context.unregisterReceiver(this);
                }
            }
        }
    };

    public void setupApplication(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(context,   "com.ibbtestaplication.provider",
                    new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString(), DIRNAME +"/"+ FILENAME));
            Intent install = new Intent(Intent.ACTION_INSTALL_PACKAGE);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
            install.setDataAndType(contentUri,"application/vnd.android.package-archive");
            context.startActivity(install);
        }else{
            if(Build.VERSION.SDK_INT >=23){
                Uri uri = Uri.fromFile(new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString(), DIRNAME+"/"+FILENAME));
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setDataAndType(uri, "application/vnd.android.package-archive");
                context.startActivity(install);
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getContext().unregisterReceiver(packageRecevier);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

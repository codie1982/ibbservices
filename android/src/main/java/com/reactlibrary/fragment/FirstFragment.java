package com.reactlibrary.fragment;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import androidx.navigation.fragment.NavHostFragment;

import com.reactlibrary.R;
import com.reactlibrary.activity.DownloadActivity;

import java.io.File;
import java.text.DecimalFormat;

public class FirstFragment extends Fragment {
    DownloadActivity downloadActivity;
    TextView txtProgress;

    String DIRNAME;
    String FILENAME;
    Long LASTDOWNLOAD;
    String DOWNLOADURL;

    ProgressBar progressBar;
    static Context mcontext ;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_first, container, false);

        downloadActivity = (DownloadActivity) getActivity();

        assert downloadActivity != null;
        Bundle mydataBundle = downloadActivity.getMyData();
        DIRNAME = mydataBundle.getString("dirname");
        FILENAME = mydataBundle.getString("filename");
        DOWNLOADURL = mydataBundle.getString("downloadurl");

        progressBar = view.findViewById(R.id.pgsApplicationDownloading);
        txtProgress = view.findViewById(R.id.txtProgresstext);
        progressBar.setProgress(0);
        txtProgress.setText("% 0.00");

        mcontext = getContext();
        mcontext.registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startDownload();

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
                        final double dl_progress = (double) ((bytes_downloaded * 100l) / bytes_total);

                        final FirstFragment firstFragment = new FirstFragment();
                        downloadActivity.runOnUiThread(new Runnable() {
                            DecimalFormat precision = new DecimalFormat("0.00");
                            @Override
                            public void run() {
                                System.out.println("ilerleme - " + dl_progress);
                                txtProgress.setText("% "+precision.format(dl_progress));
                                progressBar.setProgress((int) dl_progress);
                            }
                        });
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
                System.out.println("Download Bitti");
                setupApplication(context,intent);
                getActivity().finish();
                Toast.makeText(mcontext, "İndirme Tamamlandı", Toast.LENGTH_SHORT).show();
            }
        }


        public void setupApplication(Context context, Intent intent){

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

}

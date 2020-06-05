package com.reactlibrary.Helper;

import android.content.Context;

public class DownloadHelper {
Context context;

    public DownloadHelper(Context context) {
        this.context = context;
    }
/*
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    private void beginDownload(String url, String dirname, String filename) {
        File folder = new File(this.context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), dirname);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(this.context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString(), dirname+"/"+filename);
        if(file.isFile()){
            file.delete();
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setDestinationInExternalFilesDir(getActivity(),Environment.DIRECTORY_DOCUMENTS+"/"+dirname,filename);

        final DownloadManager downloadManager=(DownloadManager) getActivity().getSystemService(getActivity().DOWNLOAD_SERVICE);
        lastDownload=downloadManager.enqueue(request);

        new Thread(new Runnable() {

            @Override
            public void run() {
                boolean downloading = true;
                while (downloading) {
                    //System.out.println(downloading);
                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(lastDownload);
                    Cursor cursor = downloadManager.query(q);
                    cursor.moveToFirst();
                    double bytes_downloaded = cursor.getDouble(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    double bytes_total = cursor.getDouble(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false;
                    }
                    final double dl_progress = (double) ((bytes_downloaded * 100l) / bytes_total);
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mProgressBar.setProgress((int) dl_progress);
                        }
                    });
                    Log.d("EE", statusMessage(cursor));
                    cursor.close();
                }


            }
        }).start();
    }
*/
}

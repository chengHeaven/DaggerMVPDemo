package com.heavencheng.daggermvpdemo.util;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.heavencheng.daggermvpdemo.R;

import java.io.File;
import java.io.IOException;

/**
 * @author Heaven
 */
public class DownloadUtil {

    //下载器
    private DownloadManager downloadManager;

    private Context mContext;
    //下载的ID
    private long downloadId;
    private String mName;
    private String mPath;
    private static final String TAG = "DownloadUtil";

    private ProgressDialog mProgress;


    private final QueryRunnable mQueryProgressRunnable = new QueryRunnable();

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1001) {
                mProgress.setProgress(msg.arg1);
                mProgress.setMax(msg.arg2);


            }
        }
    };


    public DownloadUtil(Context context, String url, String version) {
        this.mContext = context;
        this.mName = "cx_" + version + ".apk";
        downloadApk(url, mName, version);


    }

    //下载apk
    private void downloadApk(String url, String name, String version) {
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(false);
        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle("菜信商圈");
        request.setDescription("新版本下载中...");
        request.setVisibleInDownloadsUi(true);

        //设置下载的路径
        File file = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), name);
        request.setDestinationUri(Uri.fromFile(file));
        mPath = file.getAbsolutePath();
        //获取DownloadManager
        if (downloadManager == null) {
            downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        }
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        if (downloadManager != null) {
            downloadId = downloadManager.enqueue(request);

            startQuery();
        }

        //注册广播接收者，监听下载状态
        mContext.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }


    //更新下载进度
    private void startQuery() {
        if (downloadId != 0) {

            mHandler.post(mQueryProgressRunnable);
        }
    }

    //查询下载进度
    private class QueryRunnable implements Runnable {
        @Override
        public void run() {
            if (mProgress == null){
                mProgress = new ProgressDialog(mContext);
                WindowManager.LayoutParams params = mProgress.getWindow().getAttributes();
                params.gravity = Gravity.CENTER;

//                mProgress.setProgressStyle(R.style.myDialog);
                mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                mProgress.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.download_progress));
                mProgress.setMessage("下载进度：");

                mProgress.setProgressNumberFormat("");
                if (!mProgress.isShowing()){
                    mProgress.show();
                }

            }
            queryState();
            mHandler.postDelayed(mQueryProgressRunnable,10);
        }
    }


    //查询下载进度
    private void queryState() {
        // 通过ID向下载管理查询下载情况，返回一个cursor
        Cursor c = downloadManager.query(new DownloadManager.Query().setFilterById(downloadId));
        if (c == null) {
            Toast.makeText(mContext, "下载失败",Toast.LENGTH_SHORT).show();
        } else { // 以下是从游标中进行信息提取
            if (!c.moveToFirst()) {
                Toast.makeText(mContext,"下载失败",Toast.LENGTH_SHORT).show();

                if(!c.isClosed()) {
                    c.close();
                }
                return;
            }

            int mDownload_so_far = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            int mDownload_all = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            Message msg=Message.obtain();
            if(mDownload_all>0) {
                msg.what = 1001;
                msg.arg1=mDownload_so_far;
                msg.arg2=mDownload_all;
                mHandler.sendMessage(msg);
            }
            if(!c.isClosed()){
                c.close();
            }
        }
    }



    //广播监听下载的各个状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };

    //检查下载状态
    private void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    Log.e(TAG, "STATUS_PAUSED: ");
                    break;
                //下载延迟
                case DownloadManager.STATUS_PENDING:
                    Log.e(TAG, "STATUS_PENDING: ");
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    Log.e(TAG, "STATUS_RUNNING: ");
                    break;
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    Log.e(TAG, "STATUS_SUCCESSFUL: ");


                    mHandler.removeCallbacks(mQueryProgressRunnable);
                    //下载完成安装APK

                    installApk();
                    cursor.close();

                    mHandler.removeCallbacks(mQueryProgressRunnable);
                    if (mProgress.isShowing()){
                        mProgress.dismiss();
                    }
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    Log.e(TAG, "STATUS_FAILED: ");



                    Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                    cursor.close();
                    mContext.unregisterReceiver(receiver);

                    mHandler.removeCallbacks(mQueryProgressRunnable);
                    if (mProgress.isShowing()){
                        mProgress.dismiss();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void installApk() {
        setPermission(mPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file = (new File(mPath));
        //Android 7.0以上要使用FileProvider
        if (Build.VERSION.SDK_INT >= 24) {
            //File file = (new File(mPath));
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(mContext, "com.caixin.merchant.fileprovider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            //intent.setDataAndType(Uri.fromFile(new File(Environment.DIRECTORY_DOWNLOADS, mName)), "application/vnd.android.package-archive");
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        mContext.startActivity(intent);
    }

    /**
     * 修改文件权限
     */
    private void setPermission(String absolutePath) {
        String command = "chmod " + "777" + " " + absolutePath;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

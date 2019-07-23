package com.zggk.bridge;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.view.Gravity;
import android.widget.Toast;
import com.bumptech.glide.request.RequestOptions;
import com.mob.MobSDK;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zggk.bridge.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/**
 * Created by 张成昆 on 2019-2-18.
 */

public class CustomApp extends Application {
    public static CustomApp app;// 应用实例
    public static SPUtils spUtils;// 本地存储工具类
    public static String BASEURL = "http://106.37.229.146:7101/GDWQGZ/";// 本地存储工具类
    public SharedPreferences sp;// 本地存储类
    public static final int SHOW_TOAST_TIMES = 500;// 土司通知显示时长
    public RequestOptions options;
    public final String APP_FILE_SAVE_PATH = "/mnt/sdcard/zggkiroad/";
    public static String TPDZ= "com.zggk.bridge.fileprovider";
    public static final String DOWNLOAD_PATH = "/data/data/com.zggk.gxwq/download";
    public static final String appFileName = "gxwq.apk";  //版本
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        ZXingLibrary.initDisplayOpinion(this);
        sp = getSharedPreferences("my_db_test_bridge", Context.MODE_PRIVATE);
        spUtils = new SPUtils();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10*1000L, TimeUnit.MILLISECONDS)
                .readTimeout(10*1000L,TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("OkHttpUtils",true))
                .build();
        OkHttpUtils.initClient(okHttpClient);
        options=new RequestOptions()
                .placeholder(R.drawable.morentu)
                .error(R.drawable.morentu)
                .fallback(R.drawable.morentu);
        MobSDK.init(this);
    }
    /**
     * 自定义显示位置的Toast
     *
     * @param location 显示位置
     * @param time     显示时间
     */
    public void customToast(int location, int time, int textId) {
        customToast(location, time, getResources().getString(textId));
    }

    /**
     * 自定义显示位置的Toast
     *
     * @param textId 显示文本
     */
    public void customToast(int textId) {
        customToast(Gravity.CENTER, SHOW_TOAST_TIMES, getResources().getString(textId));
    }

    /**
     * 自定义显示位置的Toast
     *
     * @param textId 显示文本
     */
    public void customToast(String textId) {
        if (textId == null)
            textId = "";
        customToast(Gravity.CENTER, SHOW_TOAST_TIMES, textId);
    }

    /**
     * 自定义的Toast
     *
     * @param location 显示位置 Gravity
     * @param duration 显示时间
     * @param showTxt  文本消息
     */
    public void customToast(int location, int duration, String showTxt) {
//        Toast toast = new Toast(this);
//        toast.setDuration(duration);
//        toast.setGravity(location, 0, 0);
//        View toastView = View.inflate(app, R.layout.custom_toast_layout, null);
//        TextView toastTv = (TextView) toastView.findViewById(R.id.toast_tv);
//        toastTv.setText(showTxt);
//        toast.setView(toastView);
//        toast.show();
        Toast.makeText(this,showTxt,duration).show();
    }
}
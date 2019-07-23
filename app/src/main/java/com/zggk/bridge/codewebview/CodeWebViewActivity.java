package com.zggk.bridge.codewebview;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.logic.ImgFileListActivity;
import com.google.gson.Gson;
import com.zggk.bridge.Bean.AddINTbean;
import com.zggk.bridge.Bean.Pljson;
import com.zggk.bridge.Bean.WebJson;
import com.zggk.bridge.CustomApp;
import com.zggk.bridge.R;
import com.zggk.bridge.add.AddActivity;
import com.zggk.bridge.jd.JdActivity;
import com.zggk.bridge.mvp.MVPBaseActivity;
import com.zggk.bridge.p.PActivity;
import com.zggk.bridge.photo.MinePopupWindow;
import com.zggk.bridge.utils.Utils;
import com.zggk.bridge.webview.WebViewActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CodeWebViewActivity extends MVPBaseActivity<CodeWebViewContract.View, CodeWebViewPresenter> implements CodeWebViewContract.View {
    @Bind(R.id.web)
    WebView web;
    @Bind(R.id.no_data_image)
    ImageView noDataImage;
    @Bind(R.id.no_data_txt)
    TextView noDataTxt;
    @Bind(R.id.web_loading_error_layout)
    LinearLayout webLoadingErrorLayout;
    @Bind(R.id.web_loading_pb)
    ProgressBar webLoadingPb;
    @Bind(R.id.RelativeLayout1)
    RelativeLayout RelativeLayout1;
    @Bind(R.id.et)
    EditText et;
    @Bind(R.id.lay)
    LinearLayout lay;
    @Bind(R.id.send)
    TextView send;
    private String URL="";//首页
    private Handler mHandler = new Handler();// 页面控制
    private boolean loadError = false;//是否加载失败了
    private String TYPE ="1";
    private MinePopupWindow minePopupWindow;
    private final int CHOOSE_PICTURE_CODE = 11;
    private final int CAMERA_CODE = 22;
    private String cameraPath;
    private int childViewPosition;
    private String CODE;
    private Gson gson=new Gson();
    public void setCameraPath(String cameraPath) {
        this.cameraPath = cameraPath;
    }
    private String UserId, Content, ToFrom, CJID;
    public void setChildViewPosition(int childViewPosition) {
        this.childViewPosition = childViewPosition;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        intview();
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserId = CustomApp.spUtils.getString("UserID");
                Content = et.getText().toString();
                Pljson pljson = new Pljson();
                pljson.setCJID(CJID);
                pljson.setContent(et.getText().toString());
                pljson.setToFrom("");
                pljson.setUserId(UserId);
                String Pljson = gson.toJson(pljson);
                Log.e("测试: ",Pljson );
                mPresenter.addPL(Pljson);
            }
        });
    }

    private void intview() {
        URL =getIntent().getStringExtra("URL");
        Log.e("intview: ", URL);
        webLoadingErrorLayout.setVisibility(View.GONE);
        if (Utils.isNull(URL)) {
            webLoadingErrorLayout.setVisibility(View.VISIBLE);
        }
        // webview setting
        WebSettings settings = web.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // WebView自动加载图片
            settings.setLoadsImagesAutomatically(true);
        } else {
            // WebView先不要自动加载图片，等页面finish后再发起图片加载
            settings.setLoadsImagesAutomatically(false);
        }
        // 设置此属性，可任意比例缩放
        settings.setUseWideViewPort(false);
        settings.setLoadWithOverviewMode(true);
        // 页面不能支持缩放
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setDisplayZoomControls(false);
        // 设置支持js
        settings.setJavaScriptEnabled(true);
        // 设置 缓存模式
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);

        // 设置滚动条
        web.setVerticalScrollBarEnabled(true);
        web.setVerticalScrollbarOverlay(true);
        web.setHorizontalScrollBarEnabled(false);
        web.setHorizontalScrollbarOverlay(false);
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                final int mProgress = newProgress;
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        if (mProgress >= 100) {
                            webLoadingPb.setVisibility(View.GONE);
                        } else {
                            webLoadingPb.setProgress(mProgress);
                        }
                    }
                });
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public Bitmap getDefaultVideoPoster() {
                try {
                    return BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.morentu);
                } catch (Exception e) {
                    return super.getDefaultVideoPoster();
                }

            }
        });

        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                if (url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://")) {
                    web.loadUrl(url);
                    return true;
                } else {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        return false;
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                        return true;
                    }
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (loadError) {
                    web.setVisibility(View.GONE);
                    webLoadingErrorLayout.setVisibility(View.VISIBLE);
                } else {
                    // 恢复图片加载
                    if (!web.getSettings().getLoadsImagesAutomatically()) {
                        web.getSettings().setLoadsImagesAutomatically(true);
                    }
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                loadError = true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // handler.cancel(); 默认的处理方式，WebView变成空白页
                handler.proceed();// 接受所有证书
                loadError = true;
            }
        });
        web.loadUrl(URL);
        web.addJavascriptInterface(new JsInterface(), "Android");
    }

    public class JsInterface {
        @JavascriptInterface
        public void showToast(String type) {
            Log.e("showToast", type);
            if (type.equals("11")) {
                finish();
            } else if (type.equals("25")) {
                getWindow().getDecorView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            lay.setVisibility(View.VISIBLE);
                            et.setVisibility(View.VISIBLE);
                            et.requestFocus();
                            imm.showSoftInput(et, 0);
                        }
                    }
                }, 100);
            }else {
                TYPE = type;
            }
        }
        @JavascriptInterface
        public void showToast(String type,String b) {
            Log.e("showToast", type);
            if (type.equals("4")){
                Intent intent = new Intent(CodeWebViewActivity.this, JdActivity.class);
                intent.putExtra("CODE",b);
                startActivityForResult(intent, 1);
            }else if (type.equals("5")){
                Intent intent = new Intent(CodeWebViewActivity.this, AddActivity.class);
                intent.putExtra("CODE",b);
                startActivityForResult(intent, 1);
            }else if (type.equals("20")) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + b);
                intent.setData(data);
                startActivity(intent);
            } else if (type.equals("23")) {
                CODE = b;
                if (minePopupWindow == null) {
                    minePopupWindow = new MinePopupWindow(CodeWebViewActivity.this, itemOnClick);
                }
                minePopupWindow.showAtLocation(RelativeLayout1, Gravity.BOTTOM, 0, 0);
            }  else if (type.equals("27")) {
                Intent intent = new Intent(CodeWebViewActivity.this, PActivity.class);
                intent.putExtra("CODE", b);
                startActivityForResult(intent, 1);
            }
        }
        @JavascriptInterface
        public void showToast(String type, String UserId, String CJI) {
            if (type.equals("26")) {
                Log.e("showToast", type);
                Log.e("showToast", CJI);
                CJID = CJI;
                getWindow().getDecorView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            lay.setVisibility(View.VISIBLE);
                            et.setVisibility(View.VISIBLE);
                            et.requestFocus();
                            imm.showSoftInput(et, 0);
                        }
                    }
                }, 100);
            }
        }
    }

    private View.OnClickListener itemOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mine_camera_btn:
                    if (minePopupWindow.isShowing()) {
                        minePopupWindow.dismiss();
                    }
                    getPicFromCamera();
                    break;
                case R.id.mine_photo_btn:
                    if (minePopupWindow.isShowing()) {
                        minePopupWindow.dismiss();
                    }
                    getPicFromPhoto();
                    break;
                case R.id.mine_cancel_btn:
                    if (minePopupWindow.isShowing()) {
                        minePopupWindow.dismiss();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    /**
     * 照相获取
     */
    public void getPicFromCamera() {
        String path = CustomApp.app.APP_FILE_SAVE_PATH
                + System.currentTimeMillis() + ".jpg";
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            // 下面这句指定调用相机拍照后的照片存储的路径
            Uri imageUri = FileProvider.getUriForFile(this, CustomApp.TPDZ, file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        setCameraPath(path);
        setChildViewPosition(childViewPosition);
        startActivityForResult(intent, CAMERA_CODE);
    }

    public void getPicFromPhoto() {
        Intent intent = new Intent(this, ImgFileListActivity.class);
        intent.putExtra("position", childViewPosition);
        intent.putExtra("isLimitedNumber", true);
        intent.putExtra("maxsize", 1);
        startActivityForResult(intent, CHOOSE_PICTURE_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == 2){
            if (requestCode == 1) {
                web.loadUrl("javascript:callH5('Android OK !!!')");
            }
        }else if (requestCode == CHOOSE_PICTURE_CODE){
            if (data != null) {
                ArrayList<String> listSelectPic = data.getStringArrayListExtra("filelist");
                int position = data.getIntExtra("position", -1);
                if (position != -1 && listSelectPic != null && listSelectPic.size() > 0) {
                    String pathStr = listSelectPic.get(0);
                    final String savePathStr = pathStr.substring(0, pathStr.lastIndexOf("/"));
                    Luban.with(this)
                            .load(listSelectPic)                                  // 传人要压缩的图片列表
                            .ignoreBy(100)                                  // 忽略不压缩图片的大小
                            .setTargetDir(savePathStr)                     // 设置压缩后文件存储位置
                            .setCompressListener(new OnCompressListener() { //设置回调
                                @Override
                                public void onStart() {
                                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                }

                                @Override
                                public void onSuccess(File file) {
                                    // TODO 压缩成功后调用，返回压缩后的图片文件
                                    String imgPath = file.getPath();
                                    WebJson WebJson = new WebJson();
                                    WebJson.setBridgeGuid(CODE);
                                    List<String> piclist =  new ArrayList<>();
                                    String strBlob = Utils.bmpToBase64String(imgPath);
                                    piclist.add(strBlob);
                                    WebJson.setPicList(piclist);
                                    String tijiaodates = gson.toJson(WebJson);
                                    mPresenter.testinfo(tijiaodates);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过程出现问题时调用
                                    Log.i("图片压缩失败", "");
                                }
                            }).launch();    //启动压缩
                }
            }
        }else if (requestCode == CAMERA_CODE){
            if (resultCode == -1) {
                if (!Utils.isNull(cameraPath) && childViewPosition != -1) {
                    Utils.dealBitmapRotate(cameraPath);
                    Luban.with(this)
                            .load(cameraPath)                                  // 传人要压缩的图片列表
                            .ignoreBy(100)                                  // 忽略不压缩图片的大小
                            .setTargetDir(cameraPath.substring(0, cameraPath.lastIndexOf("/")))                        // 设置压缩后文件存储位置
                            .setCompressListener(new OnCompressListener() { //设置回调
                                @Override
                                public void onStart() {
                                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                }

                                @Override
                                public void onSuccess(File file) {
                                    // TODO 压缩成功后调用，返回压缩后的图片文件
                                    String imgPath = file.getPath();
                                    WebJson WebJson = new WebJson();
                                    WebJson.setBridgeGuid(CODE);
                                    List<String>  piclist =  new ArrayList<>();
                                    String strBlob = Utils.bmpToBase64String(imgPath);
                                    piclist.add(strBlob);
                                    WebJson.setPicList(piclist);
                                    String tijiaodates = gson.toJson(WebJson);
                                    mPresenter.testinfo(tijiaodates);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过程出现问题时调用
                                    Log.i("图片压缩失败", "");
                                }
                            }).launch();    //启动压缩
                }
            }
        }
    }
    @Override
    public void getData(AddINTbean videoVos2) {
        if (videoVos2.getSTATE().equals("1")){
            web.loadUrl("javascript:callH5('Android OK !!!')");
        }
    }

    @Override
    public void getData3(AddINTbean videoVos2) {
        if (videoVos2.getSTATE().equals("1")) {
            web.loadUrl("javascript:callH52(" + "'" + CJID + "'" + ")");
            et.setText("");
            et.setVisibility(View.GONE);
            lay.setVisibility(View.GONE);
            View view = CodeWebViewActivity.this.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) CodeWebViewActivity.this.getSystemService(CodeWebViewActivity.this.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void onRequestError(String msg) {

    }

    @Override
    public void onRequestEnd() {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) ) {
            if (TYPE.equals("1")){
                finish();
                return true;
            }else if (TYPE.equals("2")){
                web.goBack();
                return true;
            }


        }
        return super.onKeyDown(keyCode, event);
    }
}
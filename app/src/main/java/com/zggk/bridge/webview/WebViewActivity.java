package com.zggk.bridge.webview;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
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
import android.widget.Toast;

import com.example.logic.ImgFileListActivity;
import com.google.gson.Gson;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zggk.bridge.Bean.AddINTbean;
import com.zggk.bridge.Bean.Pljson;
import com.zggk.bridge.Bean.WebJson;
import com.zggk.bridge.CommBtnListener;
import com.zggk.bridge.CommNotificationDialog;
import com.zggk.bridge.CustomApp;
import com.zggk.bridge.LoginActivity;
import com.zggk.bridge.R;
import com.zggk.bridge.SecondActivity;
import com.zggk.bridge.SoftKeyBoardListener;
import com.zggk.bridge.add.AddActivity;
import com.zggk.bridge.codewebview.CodeWebViewActivity;
import com.zggk.bridge.jd.JdActivity;
import com.zggk.bridge.mvp.MVPBaseActivity;
import com.zggk.bridge.p.PActivity;
import com.zggk.bridge.photo.MinePopupWindow;
import com.zggk.bridge.update.UpdateManager;
import com.zggk.bridge.utils.DonwloadSaveImg;
import com.zggk.bridge.utils.PermissionUtils;
import com.zggk.bridge.utils.Utils;

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

public class WebViewActivity extends MVPBaseActivity<WebViewContract.View, WebViewPresenter> implements WebViewContract.View {
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
    private String types = "1";
    private WebView vLoadWb;// 加载页面容器
    private ProgressBar vLoadingPb;// 加载进度
    private LinearLayout vNodateLly;//没有传入数据的布局 加载失败布局
    /* *******************页面变量声明*********************** */
    private Handler mHandler = new Handler();// 页面控制
    private boolean loadError = false;//是否加载失败了
    private String urlHome = "";//首页
    private PermissionUtils permissionUtils;
    private ArrayList<String> permissionsOfVersionUpdate;
    private int REQUEST_CODE = 6;
    private Intent intent;
    private CommNotificationDialog logoutWarmDialog;
    private MinePopupWindow minePopupWindow;
    private final int CHOOSE_PICTURE_CODE = 11;
    private final int CAMERA_CODE = 22;
    private String cameraPath;
    private int childViewPosition;
    private String CODE;
    private Gson gson = new Gson();
    private int ISPHOTO;
    private String USERID;
    private String TAG = "测试";
    private String UserId, Content, ToFrom, CJID;

    public void setCameraPath(String cameraPath) {
        this.cameraPath = cameraPath;
    }

    public void setChildViewPosition(int childViewPosition) {
        this.childViewPosition = childViewPosition;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//        Utils.setStatusBarColor(this, R.color.white);
        setContentView(R.layout.activity_home);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ButterKnife.bind(this);
        urlHome = "http://106.37.229.146:7102/index.html?gydwlist=" + CustomApp.spUtils.getString("UserID") + "|" + CustomApp.spUtils.getString("UserName") + "|" + CustomApp.spUtils.getString("UnitNo") + "|" + CustomApp.spUtils.getString("UnitName") + "|" + CustomApp.spUtils.getString("UserType") + "|" + CustomApp.spUtils.getString("Post") + "|" + CustomApp.spUtils.getString("SpType");
        Log.e("onCreate:测试 ", urlHome);
        intview();
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
        new UpdateManager(this, "main").checkUpdate();   //检查更新
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

            }

            @Override
            public void keyBoardHide(int height) {
                lay.setVisibility(View.GONE);
                et.setVisibility(View.GONE);
                vLoadWb.loadUrl("javascript:callH52(" + "'" + CJID + "'" + ")");
            }
        });
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
                mPresenter.addPL(Pljson);
            }
        });
    }

    private void intview() {
        // view 初始化
        vLoadWb = (WebView) findViewById(R.id.web);
        vLoadingPb = (ProgressBar) findViewById(R.id.web_loading_pb);
        vNodateLly = (LinearLayout) findViewById(R.id.web_loading_error_layout);
        vNodateLly.setVisibility(View.GONE);
        if (Utils.isNull(urlHome)) {
            vNodateLly.setVisibility(View.VISIBLE);
        }
        // webview setting
        WebSettings settings = vLoadWb.getSettings();
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
        vLoadWb.setVerticalScrollBarEnabled(true);
        vLoadWb.setVerticalScrollbarOverlay(true);
        vLoadWb.setHorizontalScrollBarEnabled(false);
        vLoadWb.setHorizontalScrollbarOverlay(false);
        vLoadWb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                final int mProgress = newProgress;
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        if (mProgress >= 100) {
                            vLoadingPb.setVisibility(View.GONE);
                        } else {
                            vLoadingPb.setProgress(mProgress);
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

        vLoadWb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                if (url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://")) {
                    vLoadWb.loadUrl(url);
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
                    vLoadWb.setVisibility(View.GONE);
                    vNodateLly.setVisibility(View.VISIBLE);
                } else {
                    // 恢复图片加载
                    if (!vLoadWb.getSettings().getLoadsImagesAutomatically()) {
                        vLoadWb.getSettings().setLoadsImagesAutomatically(true);
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
        vLoadWb.loadUrl(urlHome);
        vLoadWb.addJavascriptInterface(new JsInterface(), "Android");
    }

    @Override
    public AssetManager getAssets() {
        return getResources().getAssets();
    }

    public class JsInterface {
        @JavascriptInterface
        public void showToast(String type) {
            Log.e("showToast: ", type);
            if (type.equals("3")) {
                if (logoutWarmDialog == null) {
                    String title = "是否退出登录";
                    String okStr = "确定";
                    String cancelStr = "取消";
                    logoutWarmDialog = new CommNotificationDialog(WebViewActivity.this, title, okStr, cancelStr, new CommBtnListener() {
                        @Override
                        public void CommOkBtnClick() {
                            dele();

                        }

                        @Override
                        public void CommCancelBtnClick() {

                        }
                    });
                }
                logoutWarmDialog.show();
            } else if (type.equals("6")) {
                Intent intent = new Intent(WebViewActivity.this, SecondActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            } else if (type.equals("21")) {
                types = type;
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            } else if (type.equals("22")) {
                dele();
            } else if (type.equals("25")) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                et.setVisibility(View.VISIBLE);
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
            } else {
                types = type;
            }
        }

        @JavascriptInterface
        public void showToast(String type, String UserId, String CJI) {
            if (type.equals("26")) {
                Log.e("showToast: ", ""+26);
                CJID = CJI;
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                et.setVisibility(View.VISIBLE);
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

        @JavascriptInterface
        public void showToast(String type, String b) {
            Log.e("showToast:  ", b);
            Log.e("showToast: ", type);
            if (type.equals("4")) {
                Intent intent = new Intent(WebViewActivity.this, JdActivity.class);
                intent.putExtra("CODE", b);
                startActivityForResult(intent, 1);
            } else if (type.equals("5")) {
                Intent intent = new Intent(WebViewActivity.this, AddActivity.class);
                intent.putExtra("CODE", b);
                startActivityForResult(intent, 1);
            } else if (type.equals("20")) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + b);
                intent.setData(data);
                startActivity(intent);
            } else if (type.equals("23")) {
                ISPHOTO = 1;
                CODE = b;
                if (minePopupWindow == null) {
                    minePopupWindow = new MinePopupWindow(WebViewActivity.this, itemOnClick);
                }
                minePopupWindow.showAtLocation(RelativeLayout1, Gravity.BOTTOM, 0, 0);
            } else if (type.equals("24")) {
                USERID = b;
                ISPHOTO = 2;
                if (minePopupWindow == null) {
                    minePopupWindow = new MinePopupWindow(WebViewActivity.this, itemOnClick);
                }
                minePopupWindow.showAtLocation(RelativeLayout1, Gravity.BOTTOM, 0, 0);
            } else if (type.equals("27")) {
                Intent intent = new Intent(WebViewActivity.this, PActivity.class);
                intent.putExtra("CODE", b);
                startActivityForResult(intent, 1);
            }else if (type.equals("28")) {
                if (!Utils.isNull(b)){
                    DonwloadSaveImg.donwloadImg(WebViewActivity.this,b);
                }

            }
        }
    }

    private void dele() {
        CustomApp.spUtils.remove("UserID");
        CustomApp.spUtils.remove("UserName");
        CustomApp.spUtils.remove("UnitNo");
        CustomApp.spUtils.remove("UnitName");
        CustomApp.spUtils.remove("ShortUnitName");
        CustomApp.spUtils.remove("ParentID");
        CustomApp.spUtils.remove("UserType");
        CustomApp.spUtils.remove("Post");
        CustomApp.spUtils.remove("SpType");
        startActivity(new Intent(WebViewActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (types.equals("1")) {
//                finish();
                return true;
            } else if (types.equals("2")) {
                vLoadWb.goBack();
                return true;
            } else if (types.equals("21")) {
                vLoadWb.goBack();
                return true;
            }


        }
        return super.onKeyDown(keyCode, event);
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
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (!Utils.isNull(result)) {
                        Intent intent = new Intent(WebViewActivity.this, CodeWebViewActivity.class);
                        intent.putExtra("URL", "http://106.37.229.146:7102/saoma/Dynamic.html?bridgCode=" + result + "&UserType=" + CustomApp.spUtils.getString("UserType") + "&UnitNo=" + CustomApp.spUtils.getString("UnitNo") + "&unitPost=" + CustomApp.spUtils.getString("Post") + "&userId=" + CustomApp.spUtils.getString("UserID") + "&spType=" + CustomApp.spUtils.getString("SpType"));
                        startActivity(intent);
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(WebViewActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }

        } else if (resultCode == 2) {
            if (requestCode == 1) {
                vLoadWb.loadUrl("javascript:callH5('Android OK !!!')");
            }
        } else if (requestCode == CHOOSE_PICTURE_CODE) {
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
                                    if (ISPHOTO == 1) {
                                        WebJson WebJson = new WebJson();
                                        WebJson.setBridgeGuid(CODE);
                                        List<String> piclist = new ArrayList<>();
                                        String strBlob = Utils.bmpToBase64String(imgPath);
                                        piclist.add(strBlob);
                                        WebJson.setPicList(piclist);
                                        String tijiaodates = gson.toJson(WebJson);
                                        mPresenter.testinfo(tijiaodates);
                                    } else if (ISPHOTO == 2) {
                                        String strBlob = Utils.bmpToBase64String(imgPath);
                                        mPresenter.addPhoto(USERID, strBlob);
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过程出现问题时调用
                                    Log.i("图片压缩失败", "");
                                }
                            }).launch();    //启动压缩
                }
            }
        } else if (requestCode == CAMERA_CODE) {
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
                                    if (ISPHOTO == 1) {
                                        WebJson WebJson = new WebJson();
                                        WebJson.setBridgeGuid(CODE);
                                        List<String> piclist = new ArrayList<>();
                                        String strBlob = Utils.bmpToBase64String(imgPath);
                                        piclist.add(strBlob);
                                        WebJson.setPicList(piclist);
                                        String tijiaodates = gson.toJson(WebJson);
                                        mPresenter.testinfo(tijiaodates);
                                    } else if (ISPHOTO == 2) {
                                        String strBlob = Utils.bmpToBase64String(imgPath);
                                        mPresenter.addPhoto(USERID, strBlob);
                                    }
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
        if (videoVos2.getSTATE().equals("1")) {
            vLoadWb.loadUrl("javascript:callH5('Android OK !!!')");
        }
    }

    @Override
    public void getData2(AddINTbean videoVos2) {
        if (videoVos2.getSTATE().equals("1")) {
            vLoadWb.loadUrl("javascript:callH5('Android OK !!!')");
        }
    }

    @Override
    public void getData3(AddINTbean videoVos2) {
        if (videoVos2.getSTATE().equals("1")) {
            vLoadWb.loadUrl("javascript:callH52(" + "'" + CJID + "'" + ")");
            et.setText("");
            et.setVisibility(View.GONE);
            lay.setVisibility(View.GONE);
            View view = WebViewActivity.this.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) WebViewActivity.this.getSystemService(WebViewActivity.this.INPUT_METHOD_SERVICE);
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
}


package com.zggk.bridge;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zggk.bridge.Bean.QQBean;
import com.zggk.bridge.bdphone.BDphoneActivity;
import com.zggk.bridge.info.LoginInfo;
import com.zggk.bridge.utils.PermissionUtils;
import com.zggk.bridge.utils.Utils;
import com.zggk.bridge.webview.WebViewActivity;
import com.zggk.bridge.xgmm.XgmmActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import okhttp3.Call;

/**
 * Created by 张成昆 on 2019-2-18.
 */
public class LoginActivity extends AppCompatActivity implements PlatformActionListener {
    @Bind(R.id.wjmm)
    LinearLayout wjmm;
    private EditText usernameedit;
    private EditText passwordedit;
    private final int REQUEST_CODE_UPDATE_VERSION = 1;

    String username = "";
    String password = "";
    private TextView loginbutton;
    private PermissionUtils permissionUtils;
    private ArrayList<String> permissionsOfVersionUpdate;
    private ImageView qq;
    private String userId;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Platform platform = (Platform) msg.obj;
                    userId = platform.getDb().getUserId();
                    String url = "GaveLogin";
                    OkHttpUtils
                            .get()
                            .addParams("userID", userId)
                            .url(CustomApp.BASEURL + url)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Toast.makeText(LoginActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    QQBean loginInfo = JSON.parseObject(response, QQBean.class);
                                    if (loginInfo.getSTATE().equals("0")) {
                                        Intent intent = new Intent(LoginActivity.this, BDphoneActivity.class);
                                        intent.putExtra("userId", userId);
                                        startActivity(intent);
                                        Log.e("handleMessage: ", userId);
                                    } else {
                                        QQBean.DATABean loginInfos = loginInfo.getDATA().get(0);
                                        CustomApp.app.spUtils.put("username", loginInfos.getUserID());
                                        CustomApp.app.spUtils.put("UserID", loginInfos.getUserID());
                                        CustomApp.app.spUtils.put("UserName", loginInfos.getUserName());
                                        CustomApp.app.spUtils.put("UnitNo", loginInfos.getUnitNo());
                                        CustomApp.app.spUtils.put("UnitName", loginInfos.getUnitName());
                                        CustomApp.app.spUtils.put("ShortUnitName", loginInfos.getShortUnitName());
                                        CustomApp.app.spUtils.put("ParentID", loginInfos.getParentID());
                                        CustomApp.app.spUtils.put("UserType", loginInfos.getUserType());
                                        CustomApp.app.spUtils.put("Post", loginInfos.getPost());
                                        CustomApp.app.spUtils.put("SpType", loginInfos.getSpType());
                                        Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
                                        intent.putExtra("isFromLoginActivity", true);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                    break;
                case 2:
                    Toast.makeText(LoginActivity.this, "授权登陆失败", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(LoginActivity.this, "授权登陆取消", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        loginbutton = (TextView) findViewById(R.id.land);
        usernameedit = (EditText) findViewById(R.id.username);
        passwordedit = (EditText) findViewById(R.id.password);
        if (!Utils.isNull(CustomApp.spUtils.getString("username"))) {
            usernameedit.setText(CustomApp.spUtils.getString("username"));
        }
        if (!Utils.isNull(CustomApp.spUtils.getString("password"))) {
            passwordedit.setText(CustomApp.spUtils.getString("password"));
        }
        qq = (ImageView) findViewById(R.id.qq);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utils.isNetworkAvailable(LoginActivity.this)) {//没网
                    CustomApp.app.customToast("您当前没有网络");
                } else {//有网
                    username = usernameedit.getText().toString().trim();
                    password = passwordedit.getText().toString().trim();
                    if (Utils.isNull(username) && Utils.isNull(password)) {
                        Toast.makeText(LoginActivity.this, "请输入正确的账号或密码", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        if (!checkPermissionsOfVersionUpdate()) {
                            loginbutton.setClickable(false);
                            getdate();
                        }

                    }
                }

            }


        });
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginByQQ();
            }
        });
        wjmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, XgmmActivity.class);
                startActivity(intent);
            }
        });
    }


    private void loginByQQ() {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(this);
        qq.SSOSetting(false);
        if (!qq.isClientValid()) {
            Toast.makeText(this, "QQ未安装,请先安装QQ", Toast.LENGTH_SHORT).show();
        }
        authorize(qq);

    }

    private void authorize(Platform platform) {
        if (platform == null) {
            return;
        }
        if (platform.isAuthValid()) { //如果授权就删除授权资料
            platform.removeAccount(true);
        }
        platform.showUser(null); //授权并获取用户信息
    }

    private void getdate() {
        String url = "Login";
        OkHttpUtils
                .get()
                .addParams("uname", username)
                .addParams("pwd", password)
                .url(CustomApp.BASEURL + url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loginbutton.setClickable(true);
                        Toast.makeText(LoginActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LoginInfo loginInfo = JSON.parseObject(response, LoginInfo.class);
                        if (loginInfo.getSTATE().equals("1") && loginInfo.getDATA().size() > 0) {
                            LoginInfo.DATABean loginInfos = loginInfo.getDATA().get(0);
                            CustomApp.app.spUtils.put("username", username);
                            CustomApp.app.spUtils.put("password", password);
                            CustomApp.app.spUtils.put("UserID", loginInfos.getUserID());
                            CustomApp.app.spUtils.put("UserName", loginInfos.getUserName());
                            CustomApp.app.spUtils.put("UnitNo", loginInfos.getUnitNo());
                            CustomApp.app.spUtils.put("UnitName", loginInfos.getUnitName());
                            CustomApp.app.spUtils.put("ShortUnitName", loginInfos.getShortUnitName());
                            CustomApp.app.spUtils.put("ParentID", loginInfos.getParentID());
                            CustomApp.app.spUtils.put("UserType", loginInfos.getUserType());
                            CustomApp.app.spUtils.put("Post", loginInfos.getPost());
                            CustomApp.app.spUtils.put("SpType", loginInfos.getSpType());
                            Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
                            intent.putExtra("isFromLoginActivity", true);
                            startActivity(intent);
                            finish();
                        } else {
                            loginbutton.setClickable(true);
                            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    private boolean checkPermissionsOfVersionUpdate() {
        if (permissionUtils == null) {
            permissionUtils = PermissionUtils
                    .newInstance(this);
        }
        if (permissionsOfVersionUpdate == null) {
            permissionsOfVersionUpdate = new ArrayList<String>();
            permissionsOfVersionUpdate.add(Manifest.permission.ACCESS_FINE_LOCATION);
            permissionsOfVersionUpdate.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            permissionsOfVersionUpdate.add(Manifest.permission.CALL_PHONE);
            permissionsOfVersionUpdate.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            permissionsOfVersionUpdate.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            permissionsOfVersionUpdate.add(Manifest.permission.READ_PHONE_STATE);
            permissionsOfVersionUpdate.add(Manifest.permission.CAMERA);
            permissionsOfVersionUpdate.add(Manifest.permission.RECORD_AUDIO);
            permissionsOfVersionUpdate.add(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS);
            permissionsOfVersionUpdate.add(Manifest.permission.CHANGE_WIFI_STATE);

        }
        return permissionUtils.requestPermissions(permissionsOfVersionUpdate, REQUEST_CODE_UPDATE_VERSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_UPDATE_VERSION) {
            if (permissionUtils != null && permissionsOfVersionUpdate != null) {
                if (permissionUtils.dealRequestPermissionsResult(permissionsOfVersionUpdate, requestCode, permissions, grantResults)) {
                    getdate();
                }
                permissionsOfVersionUpdate = null;
            }
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message message = Message.obtain();
        message.what = 1;
        message.obj = platform;
        mHandler.sendMessage(message);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Message message = Message.obtain();
        message.what = 2;
        message.obj = platform;
        mHandler.sendMessage(message);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Message message = Message.obtain();
        message.what = 3;
        message.obj = platform;
        mHandler.sendMessage(message);
    }
}


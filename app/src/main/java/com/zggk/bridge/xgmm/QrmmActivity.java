package com.zggk.bridge.xgmm;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zggk.bridge.Bean.AddINTbean;
import com.zggk.bridge.Bean.Basebean;
import com.zggk.bridge.CustomApp;
import com.zggk.bridge.LoginActivity;
import com.zggk.bridge.R;
import com.zggk.bridge.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by 张成昆 on 2019-5-24.
 */

public class QrmmActivity extends AppCompatActivity {


    @Bind(R.id.go_back)
    LinearLayout goBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.right)
    TextView right;
    @Bind(R.id.hea)
    RelativeLayout hea;
    @Bind(R.id.yz_code)
    EditText yzCode;
    @Bind(R.id.get_verification)
    TextView getVerification;
    @Bind(R.id.mm_code)
    EditText mmCode;
    @Bind(R.id.qrmm_code)
    EditText qrmmCode;
    @Bind(R.id.register_button)
    Button registerButton;
    @Bind(R.id.z_ly)
    LinearLayout zLy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_qrmm);
        ButterKnife.bind(this);

        timer.start();
        getVerification.setEnabled(false);
        getVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "SendShortMessage";
                OkHttpUtils
                        .get()
                        .addParams("phone", getIntent().getStringExtra("phone"))
                        .url(CustomApp.BASEURL + url)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                CustomApp.app.customToast("服务器异常");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Basebean videoVos2 = JSON.parseObject(response, Basebean.class);
                                if (videoVos2.getSTATE().equals("1")) {
                                    CustomApp.app.customToast("短信发送成功");
                                    timer.start();
                                    getVerification.setEnabled(false);
                                }
                            }
                        });
            }
        });
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        hea.setBackgroundColor(getResources().getColor(R.color.act_bg01));
        title.setText("");
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNull(yzCode.getText().toString())) {
                    CustomApp.app.customToast("请输入验证码");
                    return;
                } else if (mmCode.getText().toString().length() < 6) {
                    CustomApp.app.customToast("请输入至少6位的密码");
                    return;
                } else if (Utils.isNull(mmCode.getText().toString())) {
                    CustomApp.app.customToast("请输入密码");
                    return;
                } else if (!mmCode.getText().toString().equals(qrmmCode.getText().toString())) {
                    CustomApp.app.customToast("您两次输入的密码不一致");
                    return;
                } else {
                    String url = "CheckVerifiCode";
                    OkHttpUtils
                            .get()
                            .addParams("phone", getIntent().getStringExtra("phone"))
                            .addParams("code", yzCode.getText().toString())
                            .addParams("pwd", mmCode.getText().toString())
                            .url(CustomApp.BASEURL + url)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    CustomApp.app.customToast("服务器异常");
                                }
                                @Override
                                public void onResponse(String response, int id) {
                                    AddINTbean videoVos2 = JSON.parseObject(response, AddINTbean.class);
                                    if (videoVos2.getSTATE().equals("1")) {
                                        CustomApp.app.customToast("修改密码成功");
                                        CustomApp.spUtils.remove("password");
                                        CustomApp.spUtils.put("password",qrmmCode.getText().toString());
                                        Intent intent = new Intent(QrmmActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        CustomApp.app.customToast(videoVos2.getDATA());
                                    }
                                }
                            });
                }
            }
        });
    }

    CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            //每隔countDownInterval秒会回调一次onTick()方法
            getVerification.setText(millisUntilFinished / 1000 + " s");
        }

        @Override
        public void onFinish() {
            getVerification.setText("重新获取");
            getVerification.setEnabled(true);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}

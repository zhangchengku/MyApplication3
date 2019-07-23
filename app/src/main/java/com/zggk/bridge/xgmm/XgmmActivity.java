package com.zggk.bridge.xgmm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zggk.bridge.Bean.Basebean;
import com.zggk.bridge.CustomApp;
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

public class XgmmActivity extends AppCompatActivity {


    @Bind(R.id.go_back)
    LinearLayout goBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.right)
    TextView right;
    @Bind(R.id.hea)
    RelativeLayout hea;
    @Bind(R.id.phone_edit)
    EditText phoneEdit;
    @Bind(R.id.register_button)
    Button registerButton;
    @Bind(R.id.z_ly)
    LinearLayout zLy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_xgmm);
        ButterKnife.bind(this);
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
                if (!Utils.isNull(phoneEdit.getText().toString())) {
                    String url = "ForgetPassWord";
                    OkHttpUtils
                            .get()
                            .addParams("phone", phoneEdit.getText().toString())
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
                                    if (videoVos2.getSTATE().equals("0")) {
                                        CustomApp.app.customToast("该手机号没有注册");
                                    } else {
                                        sendCode();
                                    }
                                }
                            });
                } else {
                    CustomApp.app.customToast("请输入手机号");
                }
            }
        });
    }

    private void sendCode() {
        String url = "SendShortMessage";
        OkHttpUtils
                .get()
                .addParams("phone", phoneEdit.getText().toString())
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
                            Intent intent = new Intent(XgmmActivity.this, QrmmActivity.class);
                            intent.putExtra("phone", phoneEdit.getText().toString());
                            startActivity(intent);
                        }
                    }
                });
    }
}

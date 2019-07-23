package com.zggk.bridge;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.zggk.bridge.utils.Utils;
import com.zggk.bridge.webview.WebViewActivity;

/**
 * Created by 张成昆 on 2019-2-18.
 */

public class GuiActivity extends AppCompatActivity {
    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui);
        initdate();
    }
    private void initdate() {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
        } else {
            username = CustomApp.app.spUtils.getString("UserName", "");
            if (Utils.isNull(username)) {   //未登陆过
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 停止刷新
                        startActivity(new Intent(GuiActivity.this,LoginActivity.class));
                        finish();
                    }
                }, 2000); // 2秒后发送消息，停止刷新
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(GuiActivity.this,WebViewActivity.class);
                        intent.putExtra("isCheckOfflineData",true);
                        startActivity(intent);
                        finish();
                    }
                }, 2000); // 2秒后发送消息，停止刷新
            }
        }

    }

}


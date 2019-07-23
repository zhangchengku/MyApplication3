package com.zggk.bridge.bdphone;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zggk.bridge.Bean.AddBean;
import com.zggk.bridge.Bean.QQBean;
import com.zggk.bridge.CodeUtils;
import com.zggk.bridge.CustomApp;
import com.zggk.bridge.R;
import com.zggk.bridge.mvp.MVPBaseActivity;
import com.zggk.bridge.utils.Utils;
import com.zggk.bridge.webview.WebViewActivity;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BDphoneActivity extends MVPBaseActivity<BDphoneContract.View, BDphonePresenter> implements BDphoneContract.View {
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_bd);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNull(phoneEdit.getText().toString())) {
                    mPresenter.testinfo("1",getIntent().getStringExtra("userId"),  phoneEdit.getText().toString());
                } else {
                    CustomApp.app.customToast("请输入手机号");
                }

            }
        });
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        CodeUtils.getInstance().createBitmap().compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] bytes = baos.toByteArray();
//        Glide.with(BDphoneActivity.this)
//                .asBitmap()
//                .apply(CustomApp.app.options)
//                .load(bytes)
//                .into(im);
//        im.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                CodeUtils.getInstance().createBitmap().compress(Bitmap.CompressFormat.PNG, 100, baos);
//                byte[] bytes = baos.toByteArray();
//                Glide.with(BDphoneActivity.this)
//                        .asBitmap()
//                        .apply(CustomApp.app.options)
//                        .load(bytes)
//                        .into(im);
//            }
//        });
    }

    @Override
    public void getData(QQBean loginInfo) {
        if (loginInfo.getSTATE().equals("1") && loginInfo.getDATA().size() > 0) {
            QQBean.DATABean  loginInfos = loginInfo.getDATA().get(0);
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
            Intent intent = new Intent(BDphoneActivity.this, WebViewActivity.class);
            intent.putExtra("isFromLoginActivity", true);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(BDphoneActivity.this, loginInfo.getMSG(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestError(String msg) {

    }

    @Override
    public void onRequestEnd() {

    }
}

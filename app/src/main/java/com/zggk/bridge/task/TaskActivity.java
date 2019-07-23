package com.zggk.bridge.task;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.zggk.bridge.CustomApp;
import com.zggk.bridge.R;
import com.zggk.bridge.mvp.MVPBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayerStandard;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class TaskActivity extends MVPBaseActivity<TaskContract.View, TaskPresenter> implements TaskContract.View {


    @Bind(R.id.videoplayer)
    JZVideoPlayerStandard videoplayer;
    @Bind(R.id.backs)
    RelativeLayout backs;
    @Bind(R.id.layout)
    RelativeLayout layout;
    private String pic;
    private String vid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_task);
        ButterKnife.bind(this);
        pic = getIntent().getStringExtra("img2");
        vid = getIntent().getStringExtra("vid2");
        initdata();
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initdata() {
        Log.e("initdata: ", pic);
        Log.e("initdata: ", vid);
        videoplayer.setUp(vid, JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        Glide.with(this)
                .asBitmap()
                .apply(CustomApp.app.options)
                .load(pic)
                .into(videoplayer.thumbImageView);

    }

    @Override
    public void onBackPressed() {
        if (videoplayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoplayer.releaseAllVideos();
    }

}

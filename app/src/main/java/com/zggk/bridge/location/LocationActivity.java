package com.zggk.bridge.location;


import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.zggk.bridge.Bean.Basebean;
import com.zggk.bridge.CustomApp;
import com.zggk.bridge.R;
import com.zggk.bridge.mvp.MVPBaseActivity;
import com.zggk.bridge.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LocationActivity extends MVPBaseActivity<LocationContract.View, LocationPresenter> implements LocationContract.View, AMap.OnCameraChangeListener {
    MapView mMapView = null;
    AMap aMap = null;
    @Bind(R.id.title_lay)
    RelativeLayout titleLay;
    @Bind(R.id.zhushi)
    TextView zhushi;
    @Bind(R.id.jwd)
    LinearLayout jwd;
    @Bind(R.id.jd)
    TextView jd;
    @Bind(R.id.wd)
    TextView wd;
    @Bind(R.id.add)
    TextView add;
    @Bind(R.id.map)
    MapView map;
    @Bind(R.id.finish)
    RelativeLayout finish;
    @Bind(R.id.bridge_name)
    TextView bridgeName;
    private MyLocationStyle myLocationStyle;
    private UiSettings uiSettings;
    private LatLng latLng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_location);
        ButterKnife.bind(this);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
            uiSettings = aMap.getUiSettings();
        }
        uiSettings.setLogoBottomMargin(-50);
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setTiltGesturesEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);
        if (!Utils.isNull(getIntent().getStringExtra("lng2")) && !Utils.isNull(getIntent().getStringExtra("lat2"))) {
            LatLng marker1 = new LatLng(Double.valueOf(getIntent().getStringExtra("lat2")), Double.valueOf(getIntent().getStringExtra("lng2")));
            //设置中心点和缩放比例
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
        }
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
        aMap.setOnCameraChangeListener(this);
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                aMap.addCircle(new CircleOptions().
                        center(latLng).
                        radius(500).
                        fillColor(Color.parseColor("#80addbff")).
                        strokeColor(Color.parseColor("#80addbff")).
                        strokeWidth(1));
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNull(jd.getText().toString()) || Utils.isNull(wd.getText().toString())) {
                    CustomApp.app.customToast("请移动指针至圆圈范围内任意点匹配经纬度");
                } else {
                    if (!Utils.isNull(getIntent().getStringExtra("CODE"))) {
                        mPresenter.testinfo(getIntent().getStringExtra("CODE"), jd.getText().toString(), wd.getText().toString());
                    }
                }
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("jd", getIntent().getStringExtra("lng1"));
                intent.putExtra("wd", getIntent().getStringExtra("lat1"));
                setResult(3, intent);
                finish();
            }
        });
        bridgeName.setText(getIntent().getStringExtra("BridgeName"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        LatLng target = cameraPosition.target;
        if (AMapUtils.calculateLineDistance(latLng, target) <= 500) {
            jd.setText(target.longitude + "");
            wd.setText(target.latitude + "");
        } else {
            jd.setText("");
            wd.setText("");
        }
        Log.e("onCameraChange: 距离", AMapUtils.calculateLineDistance(latLng, target) + "");
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

    }

    @Override
    public void onRequestError(String msg) {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void getData(Basebean videoVos2) {
        if (videoVos2.getSTATE().equals("1")) {
            Intent intent = new Intent();
            intent.putExtra("jd", jd.getText().toString());
            intent.putExtra("wd", wd.getText().toString());
            setResult(3, intent);
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent();
            intent.putExtra("jd", getIntent().getStringExtra("lng1"));
            intent.putExtra("wd", getIntent().getStringExtra("lat1"));
            setResult(3, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}

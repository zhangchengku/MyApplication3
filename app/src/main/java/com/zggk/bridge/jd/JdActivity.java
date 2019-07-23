package com.zggk.bridge.jd;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.tooklkit.Tooklkit;
import com.zggk.bridge.Bean.AddINTbean;
import com.zggk.bridge.Bean.BridgeIntbean;
import com.zggk.bridge.Bean.ISBridgeIntbean;
import com.zggk.bridge.Bean.UploadBean;
import com.zggk.bridge.Bean.addjson;
import com.zggk.bridge.CustomApp;
import com.zggk.bridge.DictationResult;
import com.zggk.bridge.LoadDataDialog;
import com.zggk.bridge.R;
import com.zggk.bridge.Upload;
import com.zggk.bridge.location.LocationActivity;
import com.zggk.bridge.mvp.MVPBaseActivity;
import com.zggk.bridge.photo.NoScroolGridView;
import com.zggk.bridge.popuwindow.PopSelectListener;
import com.zggk.bridge.popuwindow.TimePopuwindow;
import com.zggk.bridge.task.TaskAdapter;
import com.zggk.bridge.task.TaskBean;
import com.zggk.bridge.utils.Utils;
import com.zggk.bridge.webview.WebViewActivity;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class JdActivity extends MVPBaseActivity<JdContract.View, JdPresenter> implements JdContract.View {


    @Bind(R.id.go_back)
    LinearLayout goBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.right)
    TextView right;
    @Bind(R.id.road_name)
    TextView roadName;
    @Bind(R.id.road_edit)
    EditText roadEdit;
    @Bind(R.id.yuyin)
    ImageView yuyin;
    @Bind(R.id.rrrrr)
    RelativeLayout rrrrr;
    @Bind(R.id.cai_ji_picture_add_grid)
    NoScroolGridView caiJiPictureAddGrid;
    @Bind(R.id.disease_new_bh_content_layou)
    LinearLayout diseaseNewBhContentLayou;
    @Bind(R.id.header)
    RelativeLayout header;
    @Bind(R.id.rq_te)
    TextView rqTe;
    @Bind(R.id.time_layout)
    LinearLayout timeLayout;
    @Bind(R.id.sgdw_te)
    TextView sgdwTe;
    @Bind(R.id.jd_te)
    TextView jdTe;
    @Bind(R.id.tttt)
    TextView tttt;
    @Bind(R.id.sx)
    TextView sx;
    @Bind(R.id.no_bridge_fw)
    RelativeLayout noBridgeFw;
    @Bind(R.id.is_bridge_fw)
    TextView isBridgeFw;
    @Bind(R.id.add)
    TextView add;
    @Bind(R.id.foord)
    LinearLayout foord;
    @Bind(R.id.activity_new_disease_zhe_zhao_layout)
    View activityNewDiseaseZheZhaoLayout;
    @Bind(R.id.bg_layout)
    RelativeLayout bgLayout;
    @Bind(R.id.hea)
    RelativeLayout hea;
    @Bind(R.id.gps)
    RelativeLayout gps;
    @Bind(R.id.location)
    LinearLayout location;
    @Bind(R.id.sssss)
    TextView sssss;
    private Map<Integer, TaskAdapter> mapAdapter = new HashMap<>();
    private SimpleDateFormat simpleDateFormat;
    private String lng1, lat1, lng2, lat2;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private String isBridgeFwte;
    private String BridgeCode, Description, ReportTime, UnintNo, UserID, Lon, Lat, Location, ConType;
    private String ID = UUID.randomUUID().toString();
    private Gson gson = new Gson();
    private List<UploadBean> bendidates = new ArrayList<>();
    private String cameraPath;//拍照获取图片的地址
    private int childViewPosition;//拍照获取图片的地址
    private LoadDataDialog loadDataDialog;
    private TimePopuwindow Timpopup;
    private static String APPID = "5bf211f5";
    private String dictationResultStr;
    private List<String> xf = new ArrayList<>();
    private List<String> zong = new ArrayList<>();
    private String CompletionState;
    private TaskAdapter taskAdapter;
    private String TYPE;
    private String currentDate;

    public void setCameraPath(String cameraPath) {
        this.cameraPath = cameraPath;
    }

    public void setChildViewPosition(int childViewPosition) {
        this.childViewPosition = childViewPosition;
    }

    private final int CHOOSE_PICTURE_CODE = 1;
    private final int CAMERA_CODE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        Utils.setStatusBarColor(this, R.color.halving_line);
        setContentView(R.layout.act_new);
        showLoadingDialogMethod("加载中...");
        ButterKnife.bind(this);
        hea.setBackgroundColor(getResources().getColor(R.color.halving_line));
        initview();
        initdate();
        inistener();
        initpopu();
    }

    public void showZheZhaoView() {
        if (!activityNewDiseaseZheZhaoLayout.isShown()) {
            activityNewDiseaseZheZhaoLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏遮罩view
     */
    public void hideZheZhaoView() {
        if (activityNewDiseaseZheZhaoLayout.isShown()) {
            activityNewDiseaseZheZhaoLayout.setVisibility(View.GONE);
        }
    }

    private void initpopu() {
        Timpopup = new TimePopuwindow(this, new PopSelectListener() {
            @Override
            public void selectResult(Object... result) {
                String selDataStr = result[0].toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
                try {
                    Date dateResult = sdf.parse(selDataStr);
                    selDataStr = simpleDateFormat.format(dateResult);
                    Log.e("selectResult: ", selDataStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                rqTe.setText(selDataStr);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent();
            setResult(2, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void inistener() {
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JdActivity.this,LocationActivity.class);
                intent.putExtra("lng1", lng1);
                intent.putExtra("lat1", lat1);
                intent.putExtra("lng2", lng2);
                intent.putExtra("lat2", lat2);
                intent.putExtra("BridgeName",roadName.getText().toString());
                intent.putExtra("CODE",getIntent().getStringExtra("CODE"));
                startActivityForResult(intent, 3);
            }
        });
        sx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sx.setClickable(false);
                showLoadingDialogMethod("加载中...");
                if (locationClient == null) {
                    initLocation();
                }
                startLocation();
            }
        });
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(2, intent);
                finish();
            }
        });
        yuyin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getkdxf(roadEdit);
            }
        });
        timeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timpopup.show(bgLayout);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNull(roadEdit.getText().toString()) || taskAdapter.getListImgUrl().size() > 0) {
                    getTYPE();
                    add.setClickable(false);
                    showLoadingDialogMethod("上传中...");
                    addjson addjson = new addjson();
                    addjson.setID(ID);
                    addjson.setCompletionState(CompletionState);
                    addjson.setBridgeCode(getIntent().getStringExtra("CODE"));
                    addjson.setDescription(roadEdit.getText().toString());
                    addjson.setReportTime(rqTe.getText().toString());
                    addjson.setUnintNo(CustomApp.spUtils.getString("UnitNo"));
                    addjson.setUserID(CustomApp.spUtils.getString("UserID"));
                    addjson.setLon(lng2);
                    addjson.setLat(lat2);
                    addjson.setLocation(isBridgeFw.getText().toString());
                    addjson.setConType(TYPE);
                    String tijiaodates = gson.toJson(addjson);
                    Log.i("图片提交的二", "=====" + tijiaodates);
                    mPresenter.add(tijiaodates);
                } else {
                    CustomApp.app.customToast("请输入相关描述");
                }

            }
        });
    }

    private void getTYPE() {
        if (!Utils.isNull(roadEdit.getText().toString())) {
            TYPE = "1";
        } else {
            if (taskAdapter.getListImgUrl().size() > 0) {
                for (int i = 0; i < taskAdapter.getListImgUrl().size(); i++) {
                    if (!Utils.isNull(taskAdapter.getListImgUrl().get(i).getVideo()) && !Utils.isNull(taskAdapter.getListImgUrl().get(i).getPic())) {
                        TYPE = "4";
                        break;
                    } else {
                        TYPE = "2";
                    }
                }
            } else {
                TYPE = "1";
            }
        }

    }

    private void initdate() {
        mPresenter.testinfo(getIntent().getStringExtra("CODE"));
    }

    private void initview() {
        title.setText("危桥项目进度");
        ArrayList<Drawable> listPicture = new ArrayList<>();
        ArrayList<TaskBean> listImgUrl = new ArrayList<>();
        Drawable addPicture = getResources().getDrawable(R.drawable.act_add);
        listPicture.add(addPicture);
        final int childPosition = diseaseNewBhContentLayou.getChildCount();
        taskAdapter = new TaskAdapter(this, listPicture, listImgUrl, childPosition);
        caiJiPictureAddGrid.setAdapter(taskAdapter);
        mapAdapter.put(childPosition, taskAdapter);
        Calendar calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        currentDate = simpleDateFormat.format(calendar.getTime());
        rqTe.setText(currentDate);//默认日期
        sgdwTe.setText(CustomApp.spUtils.getString("UnitName"));
    }

    @Override
    public void onRequestError(String msg) {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void getData(BridgeIntbean videoVos2) {
        lng1 = videoVos2.getDATA().get(0).getLon();
        lat1 = videoVos2.getDATA().get(0).getLat();
        CompletionState = videoVos2.getDATA().get(0).getCompletionState();
        isBridgeFwte = videoVos2.getDATA().get(0).getLocationInfo();
        roadName.setText(videoVos2.getDATA().get(0).getBridgeName());
        jdTe.setText(videoVos2.getDATA().get(0).getCompletionValue());
        if (Utils.isNull(lng1)) {
            gps.setVisibility(View.VISIBLE);
            if (loadDataDialog != null && loadDataDialog.isShowing()) {
                loadDataDialog.cancel();
            }
        } else {
            if (locationClient == null) {
                initLocation();
            }
            startLocation();
        }
    }

    @Override
    public void getData2(ISBridgeIntbean videoVos2) {
        sx.setClickable(true);
        if (videoVos2.getSTATE().equals("1")) {
            if (videoVos2.getDATA().equals("1")) {
                noBridgeFw.setVisibility(View.GONE);
                isBridgeFw.setVisibility(View.VISIBLE);
                isBridgeFw.setText(isBridgeFwte);
            } else {
                noBridgeFw.setVisibility(View.VISIBLE);
                isBridgeFw.setVisibility(View.GONE);
            }


        }
        if (loadDataDialog != null && loadDataDialog.isShowing()) {
            loadDataDialog.cancel();
        }
    }

    private void showLoadingDialogMethod(String str) {
        if (loadDataDialog == null) {
            loadDataDialog = new LoadDataDialog(this);
        }
        loadDataDialog.setTitleStr(str);
        loadDataDialog.show();
    }

    @Override
    public void getData3(AddINTbean videoVos2) {
        if (videoVos2.getSTATE().equals("1")) {
            if (videoVos2.getDATA().equals("操作成功")) {
                if (taskAdapter.getListImgUrl().size() > 0) {
                    ArrayList<TaskBean> listImgUrl = taskAdapter.getListImgUrl();
                    for (int i = 0; i < listImgUrl.size(); i++) {
                        listImgUrl.get(i).setGuid(ID);
                        listImgUrl.get(i).setReportTime(rqTe.getText().toString());
                        listImgUrl.get(i).setBridgeCode(getIntent().getStringExtra("CODE"));
                        Log.e("onClick:测试图片 ", listImgUrl.get(i).getPic());
                        if (listImgUrl.get(i).getVideo() != null) {
                            Log.e("onClick:测试图片 ", listImgUrl.get(i).getVideo());
                        }
                    }
                    gesdt();
                } else {
                    refreshDataMethod();
                }
            }
        }
        add.setClickable(true);
        if (loadDataDialog != null && loadDataDialog.isShowing()) {
            loadDataDialog.cancel();
        }
    }

    private void gesdt() {
        Upload uploadWaihandleDataDialog = new Upload(this, taskAdapter.getListImgUrl());
        uploadWaihandleDataDialog.show();
    }

    public void refreshDataMethod() {
        Intent intent = new Intent();
        setResult(2, intent);
        finish();
    }
    private void getkdxf(final EditText kdaxfdate) {
        dictationResultStr = "[";
        // 语音配置对象初始化
        SpeechUtility.createUtility(JdActivity.this, SpeechConstant.APPID + "=" + APPID);
        // 1.创建SpeechRecognizer对象，第2个参数：本地听写时传InitListener
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(JdActivity.this, null);
        // 交互动画
        final RecognizerDialog iatDialog = new RecognizerDialog(JdActivity.this, null);
        // 2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 3.开始听写
        iatDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult results, boolean isLast) {

                if (!isLast) {
                    dictationResultStr += results.getResultString() + ",";
                } else {
                    dictationResultStr += results.getResultString() + "]";
                }
                if (isLast) {
                    Gson gson = new Gson();
                    List<DictationResult> dictationResultList = gson
                            .fromJson(dictationResultStr,
                                    new TypeToken<List<DictationResult>>() {
                                    }.getType());
                    String finalResult = "";
                    for (int i = 0; i < dictationResultList.size() - 1; i++) {
                        finalResult += dictationResultList.get(i)
                                .toString();
                    }
                    xf.clear();
                    zong.clear();
                    zong.add(kdaxfdate.getText().toString());
                    zong.add(finalResult);
                    kdaxfdate.setText(listToString2(zong));
                    kdaxfdate.requestFocus();
                    kdaxfdate.setSelection(listToString2(zong).length());
                }
            }

            @Override
            public void onError(SpeechError error) {
                error.getPlainDescription(true);
            }

        });
        iatDialog.show();

    }

    public static String listToString2(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i));
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == CHOOSE_PICTURE_CODE) {//从相册获取照片
            if (intent != null) {
                ArrayList<String> listSelectPic = intent.getStringArrayListExtra("filelist");
                int position = intent.getIntExtra("position", -1);
                if (position != -1 && listSelectPic != null && listSelectPic.size() > 0) {
                    final TaskAdapter addPictureAdapter = mapAdapter.get(position);
                    final ArrayList<Drawable> listPicture = addPictureAdapter.getListPicture();
                    final ArrayList<TaskBean> listImgUrl = addPictureAdapter.getListImgUrl();

                    String pathStr = listSelectPic.get(0);
                    final String savePathStr = pathStr.substring(0, pathStr.lastIndexOf("/"));
                    Luban.with(this)
                            .load(listSelectPic)                                  // 传人要压缩的图片列表
                            .ignoreBy(100)                                  // 忽略不压缩图片的大小
//                            .setTargetDir(savePathStr)                     // 设置压缩后文件存储位置
                            .setCompressListener(new OnCompressListener() { //设置回调
                                @Override
                                public void onStart() {
                                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                }

                                @Override
                                public void onSuccess(File file) {
                                    // TODO 压缩成功后调用，返回压缩后的图片文件
                                    String imgPath = file.getPath();
                                    listPicture.remove(listPicture.size() - 1);
                                    Bitmap bitmap = Tooklkit.getImageThumbnail(imgPath, Tooklkit.dip2px(JdActivity.this, 480),
                                            Tooklkit.dip2px(JdActivity.this, 480));
                                    if (bitmap != null) {
                                        Log.i("图片压缩成功", savePathStr + "地址" + imgPath);
                                        Drawable drawable = new BitmapDrawable(bitmap);
                                        listPicture.add(drawable);
                                        TaskBean taskBean = new TaskBean();
                                        taskBean.setPic(imgPath);
                                        listImgUrl.add(taskBean);
                                        Drawable addPicture = getResources().getDrawable(R.drawable.act_add);
                                        listPicture.add(addPicture);
                                        addPictureAdapter.notifyDataSetChanged();
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
        } else if (requestCode == CAMERA_CODE) {//拍照获取照片
            if (resultCode == 102) {
                String path = intent.getStringExtra("path");
                final String url = intent.getStringExtra("url");
                Log.i("图片压缩成功", "地址" + path);
                Log.i("图片压缩成功", "地址" + url);
                if (!Utils.isNull(path) && childViewPosition != -1 && !Utils.isNull(url)) {
                    final TaskAdapter addPictureAdapter = mapAdapter.get(childViewPosition);
                    final ArrayList<Drawable> listPicture = addPictureAdapter.getListPicture();
                    final ArrayList<TaskBean> listImgUrl = addPictureAdapter.getListImgUrl();
                    Luban.with(this)
                            .load(path)                                  // 传人要压缩的图片列表
                            .ignoreBy(100)                                  // 忽略不压缩图片的大小
//                            .setTargetDir(savePathStr)                     // 设置压缩后文件存储位置
                            .setCompressListener(new OnCompressListener() { //设置回调
                                @Override
                                public void onStart() {
                                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                }

                                @Override
                                public void onSuccess(File file) {
                                    // TODO 压缩成功后调用，返回压缩后的图片文件
                                    String imgPath = file.getPath();
                                    Log.d("lubanLog", "图片的大小为：" + file.length() / 1024 + "KB");
                                    listPicture.remove(listPicture.size() - 1);
                                    Bitmap bitmap = Tooklkit.getImageThumbnail(imgPath, Tooklkit.dip2px(JdActivity.this, 480),
                                            Tooklkit.dip2px(JdActivity.this, 480));
                                    if (bitmap != null) {
                                        Drawable drawable = new BitmapDrawable(bitmap);
                                        listPicture.add(drawable);
                                        TaskBean taskBean = new TaskBean();
                                        taskBean.setPic(imgPath);
                                        taskBean.setVideo(url);
                                        listImgUrl.add(taskBean);
                                        Drawable addPicture = getResources().getDrawable(R.drawable.act_add);
                                        listPicture.add(addPicture);
                                        addPictureAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过程出现问题时调用
                                    Log.i("图片压缩失败", "");
                                }
                            }).launch();
                }

            } else if (resultCode == 101) {
                String path = intent.getStringExtra("path");
                Log.i("图片压缩成功", cameraPath + "地址" + path);
                if (!Utils.isNull(path) && childViewPosition != -1) {
                    final TaskAdapter addPictureAdapter = mapAdapter.get(childViewPosition);
                    final ArrayList<Drawable> listPicture = addPictureAdapter.getListPicture();
                    final ArrayList<TaskBean> listImgUrl = addPictureAdapter.getListImgUrl();
                    Luban.with(this)
                            .load(path)                                  // 传人要压缩的图片列表
                            .ignoreBy(100)                                  // 忽略不压缩图片的大小
//                            .setTargetDir(savePathStr)                     // 设置压缩后文件存储位置
                            .setCompressListener(new OnCompressListener() { //设置回调
                                @Override
                                public void onStart() {
                                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                }

                                @Override
                                public void onSuccess(File file) {
                                    // TODO 压缩成功后调用，返回压缩后的图片文件
                                    String imgPath = file.getPath();
                                    Log.d("lubanLog", "图片的大小为：" + file.length() / 1024 + "KB");
                                    listPicture.remove(listPicture.size() - 1);
                                    Bitmap bitmap = Tooklkit.getImageThumbnail(imgPath, Tooklkit.dip2px(JdActivity.this, 480),
                                            Tooklkit.dip2px(JdActivity.this, 480));
                                    if (bitmap != null) {
                                        Log.i("图片压缩成功", "地址" + imgPath);
                                        Drawable drawable = new BitmapDrawable(bitmap);
                                        listPicture.add(drawable);
                                        TaskBean taskBean = new TaskBean();
                                        taskBean.setPic(imgPath);
                                        listImgUrl.add(taskBean);
                                        Drawable addPicture = getResources().getDrawable(R.drawable.act_add);
                                        listPicture.add(addPicture);
                                        addPictureAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过程出现问题时调用
                                    Log.i("图片压缩失败", "");
                                }
                            }).launch();
                }
            }
        }else if (requestCode == 3){
            lng1 =intent.getStringExtra("jd");
            lat1 =intent.getStringExtra("wd");
            showLoadingDialogMethod("加载中...");
            if (locationClient == null) {
                initLocation();
            }
            startLocation();
        }
    }
    /********定位功能开始**********/
    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {//定位成功
                Double longitude = aMapLocation.getLongitude();//经度
                Double latitude = aMapLocation.getLatitude();//纬度
                lng2 = String.valueOf(longitude);
                lat2 = String.valueOf(latitude);
                mPresenter.isBridge(lng1, lat1, lng2, lat2);
            } else {//定位失败
                Log.i("定位失败", "定位失败");
            }
            stopLocation();
        }
    };

    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void startLocation() {
        // 设置定位参数
//        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();

    }
}

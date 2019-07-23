package com.zggk.bridge.add;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.logic.ImgFileListActivity;
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
import com.zggk.bridge.Bean.Add2json;
import com.zggk.bridge.Bean.AddBean;
import com.zggk.bridge.Bean.AddINTbean;
import com.zggk.bridge.CommBtnListener;
import com.zggk.bridge.CommNotificationDialog;
import com.zggk.bridge.CustomApp;
import com.zggk.bridge.DictationResult;
import com.zggk.bridge.LoadDataDialog;
import com.zggk.bridge.R;
import com.zggk.bridge.jd.JdActivity;
import com.zggk.bridge.mvp.MVPBaseActivity;
import com.zggk.bridge.photo.MinePopupWindow;
import com.zggk.bridge.popuwindow.AddAllPopuWindow;
import com.zggk.bridge.popuwindow.PopSelectListener;
import com.zggk.bridge.popuwindow.SelectPopuWindowListener;
import com.zggk.bridge.popuwindow.YMDTimePopuWindow;
import com.zggk.bridge.showImg.ShowImgActivity;
import com.zggk.bridge.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AddActivity extends MVPBaseActivity<AddContract.View, AddPresenter> implements AddContract.View {


    @Bind(R.id.go_back)
    LinearLayout goBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.right)
    TextView right;
    @Bind(R.id.hea)
    RelativeLayout hea;
    @Bind(R.id.add_bridge_code)
    TextView addBridgeCode;
    @Bind(R.id.luxian_warm_txt)
    TextView luxianWarmTxt;
    @Bind(R.id.disease_new_road_line_txt_label)
    TextView diseaseNewRoadLineTxtLabel;
    @Bind(R.id.add_bridge_position)
    TextView addBridgePosition;
    @Bind(R.id.disease_new_road_line_layout)
    RelativeLayout diseaseNewRoadLineLayout;
    @Bind(R.id.time_warm_txt)
    TextView timeWarmTxt;
    @Bind(R.id.disease_new_time_txt_label)
    TextView diseaseNewTimeTxtLabel;
    @Bind(R.id.add_bridge_Dw)
    TextView addBridgeDw;
    @Bind(R.id.disease_new_time_layout)
    RelativeLayout diseaseNewTimeLayout;
    @Bind(R.id.dclx_warm_txt)
    TextView dclxWarmTxt;
    @Bind(R.id.disease_new_to_examine_type_txt_label)
    TextView diseaseNewToExamineTypeTxtLabel;
    @Bind(R.id.add_bridge_time)
    TextView addBridgeTime;
    @Bind(R.id.add_bridge_time_lay)
    RelativeLayout addBridgeTimeLay;
    @Bind(R.id.dclx_warm_type)
    TextView dclxWarmType;
    @Bind(R.id.add_bridge_type_te)
    TextView addBridgeTypeTe;
    @Bind(R.id.add_bridge_type)
    TextView addBridgeType;
    @Bind(R.id.add_bridge_type_lay)
    RelativeLayout addBridgeTypeLay;
    @Bind(R.id.add_bridge_word_ig)
    TextView addBridgeWordIg;
    @Bind(R.id.add_bridge_word_te)
    TextView addBridgeWordTe;
    @Bind(R.id.add_bridge_word)
    TextView addBridgeWord;
    @Bind(R.id.add_bridge_word_lay)
    RelativeLayout addBridgeWordLay;
    @Bind(R.id.add_bridge_wctz_ig)
    TextView addBridgeWctzIg;
    @Bind(R.id.add_bridge_wctz_te)
    TextView addBridgeWctzTe;
    @Bind(R.id.add_bridge_wctz_ed)
    EditText addBridgeWctzEd;
    @Bind(R.id.add_bridge_wctz)
    TextView addBridgeWctz;
    @Bind(R.id.add_bridge_wctz_lay)
    RelativeLayout addBridgeWctzLay;
    @Bind(R.id.add_bridge_sgdw_ig)
    TextView addBridgeSgdwIg;
    @Bind(R.id.add_bridge_sgdw_te)
    TextView addBridgeSgdwTe;
    @Bind(R.id.add_bridge_sgdw)
    TextView addBridgeSgdw;
    @Bind(R.id.add_bridge_sgdw_lay)
    RelativeLayout addBridgeSgdwLay;
    @Bind(R.id.add_bridge_star_ig)
    TextView addBridgeStarIg;
    @Bind(R.id.add_bridge_star_te)
    TextView addBridgeStarTe;
    @Bind(R.id.add_bridge_star)
    TextView addBridgeStar;
    @Bind(R.id.add_bridge_star_lay)
    RelativeLayout addBridgeStarLay;

    @Bind(R.id.add_bridge_end_ig)
    TextView addBridgeEndIg;
    @Bind(R.id.add_bridge_end_te)
    TextView addBridgeEndTe;
    @Bind(R.id.add_bridge_end)
    TextView addBridgeEnd;
    @Bind(R.id.add_bridge_end_lay)
    RelativeLayout addBridgeEndLay;
    @Bind(R.id.czwt_bu)
    TextView czwtBu;
    @Bind(R.id.czwt_et)
    EditText czwtEt;
    @Bind(R.id.zpv)
    LinearLayout zpv;
    @Bind(R.id.shigonghou)
    ImageView shigonghou;
    @Bind(R.id.shigonghoude)
    ImageView shigonghoude;
    @Bind(R.id.disease_new_bh_content_layout)
    LinearLayout diseaseNewBhContentLayout;
    @Bind(R.id.add)
    TextView add;
    @Bind(R.id.activity_disease_new_scrollview)
    ScrollView activityDiseaseNewScrollview;
    @Bind(R.id.activity_new_disease_zhe_zhao_layout)
    View activityNewDiseaseZheZhaoLayout;
    @Bind(R.id.layout)
    RelativeLayout layout;
    @Bind(R.id.zs)
    TextView zs;
    @Bind(R.id.switch1)
    Switch switch1;
    private SimpleDateFormat simpleDateFormat;
    private YMDTimePopuWindow ymdTimePopuWindow;
    private ArrayList<String> gcResult = new ArrayList<>();
    private ArrayList<String> gzResult = new ArrayList<>();
    private AddAllPopuWindow GcPop;
    private AddAllPopuWindow GzPop;
    private AddBean.COMDATABean gcInfo;
    private AddBean.PRODATABean gzInfo;
    private List<AddBean.COMDATABean> gcResultINT = new ArrayList<>();
    private List<AddBean.PRODATABean> gzResultINT = new ArrayList<>();


    private final int CHOOSE_PICTURE_CODE = 1;
    private final int CAMERA_CODE = 2;

    private List<AddBean.QLDATABean> QLDATA = new ArrayList<>();
    private Gson gson = new Gson();
    private LoadDataDialog loadDataDialog;
    private CommNotificationDialog logoutWarmDialog;
    private String COMPLETE;
    private static String APPID = "5bf211f5";
    private String dictationResultStr;
    private List<String> xf = new ArrayList<>();
    private List<String> zong = new ArrayList<>();
    private YMDTimePopuWindow kgsjTimePopuWindow;
    private boolean shigonghoubig = true;
    private MinePopupWindow minePopupWindow;
    private String cameraPath;
    private int childViewPosition;
    private String REPORTDATE;
    private String currentDate;
    private String ISSAVE = "1";


    public void setCameraPath(String cameraPath) {
        this.cameraPath = cameraPath;
    }

    public void setChildViewPosition(int childViewPosition) {
        this.childViewPosition = childViewPosition;
    }

    private String SGHTP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//        Utils.setStatusBarColor(this, R.color.white);
        setContentView(R.layout.act_add);
        showLoadingDialogMethod("加载中...");
        ButterKnife.bind(this);
        hea.setBackgroundColor(getResources().getColor(R.color.white));
        initpopu();
        initdate();
        linstener();
    }

    private void show(String type) {
        if (type.equals("未开工")) {
            addBridgeWordLay.setVisibility(View.VISIBLE);
            addBridgeStarLay.setVisibility(View.GONE);
            addBridgeEndLay.setVisibility(View.GONE);
//            zpv.setVisibility(View.GONE);
//            diseaseNewBhContentLayout.setVisibility(View.GONE);
//            zs.setVisibility(View.GONE);
            add.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttom_tui));
        } else if (type.equals("已开工")) {
            addBridgeWordLay.setVisibility(View.GONE);
            addBridgeStarLay.setVisibility(View.VISIBLE);
            addBridgeEndLay.setVisibility(View.GONE);
//            zpv.setVisibility(View.GONE);
//            diseaseNewBhContentLayout.setVisibility(View.GONE);
//            zs.setVisibility(View.GONE);
            add.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttom_tui));
        } else if (type.equals("已完工")) {
            addBridgeWordLay.setVisibility(View.GONE);
            addBridgeStarLay.setVisibility(View.VISIBLE);
            addBridgeEndLay.setVisibility(View.VISIBLE);
//            zpv.setVisibility(View.VISIBLE);
//            diseaseNewBhContentLayout.setVisibility(View.VISIBLE);
//            zs.setVisibility(View.VISIBLE);
            add.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttom_tui));
        } else if (type.equals("已交工")) {
            addBridgeWordLay.setVisibility(View.VISIBLE);
            addBridgeStarLay.setVisibility(View.VISIBLE);
            addBridgeEndLay.setVisibility(View.VISIBLE);
//            zpv.setVisibility(View.GONE);
//            diseaseNewBhContentLayout.setVisibility(View.GONE);
//            zs.setVisibility(View.GONE);
            add.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_shou));
        } else if (type.equals("已竣工")) {
            addBridgeWordLay.setVisibility(View.VISIBLE);
            addBridgeStarLay.setVisibility(View.VISIBLE);
            addBridgeEndLay.setVisibility(View.VISIBLE);
//            zpv.setVisibility(View.GONE);
//            diseaseNewBhContentLayout.setVisibility(View.GONE);
//            zs.setVisibility(View.GONE);
            add.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_shou));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == CHOOSE_PICTURE_CODE) {//从相册获取照片
            if (intent != null) {
                ArrayList<String> listSelectPic = intent.getStringArrayListExtra("filelist");
                int position = intent.getIntExtra("position", -1);
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
                                    shigonghoubig = true;
                                    String imgPath = file.getPath();
                                    SGHTP = imgPath;
                                    Glide.with(AddActivity.this)
                                            .asBitmap()
                                            .apply(CustomApp.app.options)
                                            .load(imgPath)
                                            .into(shigonghou);
                                    shigonghoude.setVisibility(View.VISIBLE);
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
                                    shigonghoubig = true;
                                    String imgPath = file.getPath();
                                    Bitmap bitmap = Tooklkit.getImageThumbnail(imgPath, Tooklkit.dip2px(AddActivity.this, 480),
                                            Tooklkit.dip2px(AddActivity.this, 480));
                                    Log.i("图片压缩成功", "地址" + imgPath);
                                    SGHTP = imgPath;
                                    shigonghou.setImageBitmap(bitmap);
                                    shigonghoude.setVisibility(View.VISIBLE);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent();
            setResult(2, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void linstener() {
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //Todo
                    ISSAVE="1";
                }else {
                    //Todo
                    ISSAVE="0";
                }
            }
        });

        shigonghoude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(AddActivity.this)
                        .asBitmap()
                        .apply(CustomApp.app.options)
                        .load(R.drawable.morentu)
                        .into(shigonghou);
                shigonghoude.setVisibility(View.GONE);
                shigonghoubig = false;
                SGHTP = "";
            }
        });
        shigonghou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shigonghoubig == false) {
                    if (minePopupWindow == null) {
                        minePopupWindow = new MinePopupWindow(AddActivity.this, itemOnClick);
                    }
                    minePopupWindow.showAtLocation(activityDiseaseNewScrollview, Gravity.BOTTOM, 0, 0);
                } else {
                    Intent intent = new Intent(AddActivity.this, ShowImgActivity.class);
                    intent.putExtra("img2", SGHTP);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                }

            }
        });
        czwtBu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getkdxf(czwtEt);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                REPORTDATE="1";
                if (REPORTDATE.equals("0")){
                    if (logoutWarmDialog == null) {
                        String title = "数据上报时间为每月月末7个自然日，请在数据上报窗口期内报送月报数据";
                        String okStr = "确定";
                        String cancelStr = "取消";
                        logoutWarmDialog = new CommNotificationDialog(AddActivity.this, title, okStr, cancelStr, new CommBtnListener() {
                            @Override
                            public void CommOkBtnClick() {

                            }

                            @Override
                            public void CommCancelBtnClick() {

                            }
                        });
                    }
                    logoutWarmDialog.show();
                }else {
                    if (QLDATA.get(0).getSpStates().equals("0")){
                        if (logoutWarmDialog == null) {
                            String title = "上月进度未审批通过，请联系审核员";
                            String okStr = "确定";
                            String cancelStr = "取消";
                            logoutWarmDialog = new CommNotificationDialog(AddActivity.this, title, okStr, cancelStr, new CommBtnListener() {
                                @Override
                                public void CommOkBtnClick() {

                                }

                                @Override
                                public void CommCancelBtnClick() {

                                }
                            });
                        }
                        logoutWarmDialog.show();
                    }else if (QLDATA.get(0).getSpStates().equals("1")){
                            if (Utils.isNull(addBridgeWctzEd.getText().toString())) {
                                Toast.makeText(AddActivity.this, "请输入金额", Toast.LENGTH_SHORT).show();
                            } else {
                                if (COMPLETE.equals("0")) {
                                    adddate();
                                } else {
                                    if (Float.valueOf(addBridgeWctzEd.getText().toString()) < Float.valueOf(COMPLETE)) {
                                        if (logoutWarmDialog == null) {
                                            String title = "您本月完成投资总额小于上月，请确认无误？";
                                            String okStr = "确定";
                                            String cancelStr = "取消";
                                            logoutWarmDialog = new CommNotificationDialog(AddActivity.this, title, okStr, cancelStr, new CommBtnListener() {
                                                @Override
                                                public void CommOkBtnClick() {
                                                    adddate();
                                                }

                                                @Override
                                                public void CommCancelBtnClick() {

                                                }
                                            });
                                        }
                                        logoutWarmDialog.show();
                                    } else {
                                        if (logoutWarmDialog == null) {
                                            String title = "是否报送";
                                            String okStr = "确定";
                                            String cancelStr = "取消";
                                            logoutWarmDialog = new CommNotificationDialog(AddActivity.this, title, okStr, cancelStr, new CommBtnListener() {
                                                @Override
                                                public void CommOkBtnClick() {
                                                    adddate();
                                                }

                                                @Override
                                                public void CommCancelBtnClick() {

                                                }
                                            });
                                        }
                                        logoutWarmDialog.show();
                                    }
                                }
                            }
                    }
                }
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
        addBridgeEndLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ymdTimePopuWindow.show(layout);
            }
        });
        addBridgeTypeLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GcPop.show(layout);
            }
        });
        addBridgeWordLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GzPop.show(layout);
            }
        });
        addBridgeStarLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kgsjTimePopuWindow.show(layout);
            }
        });
    }

    private void adddate() {
        add.setClickable(false);
        showLoadingDialogMethod("上传中...");
        Add2json addjson = new Add2json();
        addjson.setUserId(CustomApp.spUtils.getString("UserID"));
        addjson.setBridgeGuid(QLDATA.get(0).getGUID());
        addjson.setBridgeCode(getIntent().getStringExtra("CODE"));
        addjson.setBridgeLocation(QLDATA.get(0).getCjLocation());
        addjson.setUnitNo(CustomApp.spUtils.getString("UnitNo"));
        addjson.setUnitName(CustomApp.spUtils.getString("UnitName"));
        addjson.setReportTime(addBridgeTime.getText().toString());
        addjson.setEngineerState(gcInfo.getZdname());
        addjson.setPriorWork(gzInfo.getZdname());
        addjson.setCompleteInvestment(addBridgeWctzEd.getText().toString());
        addjson.setKaiGongTime(addBridgeStar.getText().toString());
        addjson.setWanGongTime(addBridgeEnd.getText().toString());
        addjson.setRemark(czwtEt.getText().toString());
        ArrayList<String> listPic = new ArrayList<>();
        String strBlob = Utils.bmpToBase64String(SGHTP);
        if (Utils.isNull(strBlob)){
            addjson.setPicUrl(SGHTP);
            addjson.setPicList(listPic);
        }else {
            Log.i("测试", "=====" + strBlob);
            addjson.setPicUrl("");
            listPic.add(strBlob);
            addjson.setPicList(listPic);
        }
        addjson.setIsSave(ISSAVE);
        String tijiaodates = gson.toJson(addjson);
        Log.i("测试", "=====" + tijiaodates);
        mPresenter.add(tijiaodates);
    }

    private void showLoadingDialogMethod(String str) {
        if (loadDataDialog == null) {
            loadDataDialog = new LoadDataDialog(this);
        }
        loadDataDialog.setTitleStr(str);
        loadDataDialog.show();
    }

    private void initpopu() {
        kgsjTimePopuWindow = new YMDTimePopuWindow(this, new PopSelectListener() {
            @Override
            public void selectResult(Object... result) {
                String selDataStr = result[0].toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                try {
                    Date dateResult = sdf.parse(selDataStr);
                    selDataStr = simpleDateFormat.format(dateResult);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                addBridgeStar.setText(selDataStr);
            }
        });
        ymdTimePopuWindow = new YMDTimePopuWindow(this, new PopSelectListener() {
            @Override
            public void selectResult(Object... result) {
                String selDataStr = result[0].toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                try {
                    Date dateResult = sdf.parse(selDataStr);
                    selDataStr = simpleDateFormat.format(dateResult);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                addBridgeEnd.setText(selDataStr);
            }
        });
        GcPop = new AddAllPopuWindow(this, "请选择工程状态", gcResult, new SelectPopuWindowListener() {
            @Override
            public void selectPosition(int position) {
                addBridgeType.setText(gcResult.get(position));
                gcInfo = gcResultINT.get(position);
                show(gcResult.get(position));
            }
        });
        GzPop = new AddAllPopuWindow(this, "请选择前期工作", gzResult, new SelectPopuWindowListener() {
            @Override
            public void selectPosition(int position) {
                addBridgeWord.setText(gzResult.get(position));
                gzInfo = gzResultINT.get(position);
            }
        });
    }

    private void initdate() {
        SimpleDateFormat  simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar1 = Calendar.getInstance();
         currentDate = simpleDateFormat1.format(calendar1.getTime());
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String time = simpleDateFormat.format(calendar.getTime());
        addBridgeTime.setText(time);
        addBridgeSgdw.setText(CustomApp.spUtils.getString("UnitName"));
        mPresenter.testinfo(getIntent().getStringExtra("CODE"));
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

    @Override
    public void onRequestError(String msg) {

    }

    @Override
    public void onRequestEnd() {

    }

    public static String replaceNull(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    @Override
    public void getData(AddBean videoVos2) {
        REPORTDATE =videoVos2.getREPORTDATE();
        if (videoVos2.getCOMDATA() != null && videoVos2.getCOMDATA().size() > 0) {
            gcResultINT = videoVos2.getCOMDATA();
            gcResult.clear();
            for (int i = 0; i < videoVos2.getCOMDATA().size(); i++) {
                gcResult.add(videoVos2.getCOMDATA().get(i).getZdValue());
            }
            GcPop.notifityData();
        }
        if (videoVos2.getPRODATA() != null && videoVos2.getPRODATA().size() > 0) {
            gzResultINT = videoVos2.getPRODATA();
            gzResult.clear();
            for (int i = 0; i < videoVos2.getPRODATA().size(); i++) {
                gzResult.add(videoVos2.getPRODATA().get(i).getZdValue());
            }
            GzPop.notifityData();
        }
        if (videoVos2.getQLDATA() != null && videoVos2.getQLDATA().size() > 0) {
            QLDATA = videoVos2.getQLDATA();
            if (Utils.isNull(videoVos2.getQLDATA().get(0).getCompleteInvestment())) {
                COMPLETE = "0";
            } else {
                COMPLETE = videoVos2.getQLDATA().get(0).getCompleteInvestment();
            }
            if (Utils.isNull(videoVos2.getQLDATA().get(0).getEngineerState())) {
                addBridgeType.setText(videoVos2.getCOMDATA().get(0).getZdValue());
                show(videoVos2.getCOMDATA().get(0).getZdValue());
                gcInfo = videoVos2.getCOMDATA().get(0);
            } else {
                for (int i = 0; i < videoVos2.getCOMDATA().size(); i++) {
                    if (videoVos2.getCOMDATA().get(i).getZdname().equals(videoVos2.getQLDATA().get(0).getEngineerState())) {
                        addBridgeType.setText(videoVos2.getCOMDATA().get(i).getZdValue());
                        show(videoVos2.getCOMDATA().get(i).getZdValue());
                        gcInfo = videoVos2.getCOMDATA().get(i);
                    }
                }
            }
            if (Utils.isNull(videoVos2.getQLDATA().get(0).getPriorWork())) {
                gzInfo = videoVos2.getPRODATA().get(0);
                addBridgeWord.setText(videoVos2.getPRODATA().get(0).getZdValue());
            } else {
                for (int i = 0; i < videoVos2.getPRODATA().size(); i++) {
                    if (videoVos2.getPRODATA().get(i).getZdname().equals(videoVos2.getQLDATA().get(0).getPriorWork())) {
                        addBridgeWord.setText(videoVos2.getPRODATA().get(i).getZdValue());
                        gzInfo = videoVos2.getPRODATA().get(i);
                    }
                }
            }
            if (Utils.isNull(COMPLETE)) {
                addBridgeWctzEd.setHint("请输入");
            } else {
                addBridgeWctzEd.setHint(COMPLETE);
            }
            if (!Utils.isNull(videoVos2.getQLDATA().get(0).getKaiGongTime())) {
                addBridgeStar.setText(videoVos2.getQLDATA().get(0).getKaiGongTime());
            }
            if (!Utils.isNull(videoVos2.getQLDATA().get(0).getWanGongTime())) {
                addBridgeEnd.setText(videoVos2.getQLDATA().get(0).getWanGongTime());
            }
            if (!Utils.isNull(videoVos2.getQLDATA().get(0).getRemark())) {
                czwtEt.setText(videoVos2.getQLDATA().get(0).getRemark());
            }
            addBridgeCode.setText(videoVos2.getQLDATA().get(0).getBridgeCode());
            addBridgePosition.setText(videoVos2.getQLDATA().get(0).getCjLocation());
            addBridgeDw.setText(videoVos2.getQLDATA().get(0).getUnitName());
            title.setText(videoVos2.getQLDATA().get(0).getBridgeName());
            if (!Utils.isNull(videoVos2.getQLDATA().get(0).getImg())) {
                shigonghoude.setVisibility(View.VISIBLE);
                shigonghoubig = true;
                SGHTP = videoVos2.getQLDATA().get(0).getImg();
                Glide.with(AddActivity.this)
                        .asBitmap()
                        .apply(CustomApp.app.options)
                        .load(videoVos2.getQLDATA().get(0).getImg())
                        .into(shigonghou);
            } else {
                shigonghoude.setVisibility(View.GONE);
                shigonghoubig = false;
            }
        }
        if (loadDataDialog != null && loadDataDialog.isShowing()) {
            loadDataDialog.cancel();
        }
    }

    @Override
    public void getData3(AddINTbean videoVos2) {
        add.setClickable(true);
        if (loadDataDialog != null && loadDataDialog.isShowing()) {
            loadDataDialog.cancel();
        }
        if (videoVos2.getSTATE().equals("1")) {
            CustomApp.app.customToast("上传成功");
            Intent intent = new Intent();
            setResult(2, intent);
            finish();
        }
    }

    private void getkdxf(final EditText kdaxfdate) {
        dictationResultStr = "[";
        // 语音配置对象初始化
        SpeechUtility.createUtility(AddActivity.this, SpeechConstant.APPID + "=" + APPID);
        // 1.创建SpeechRecognizer对象，第2个参数：本地听写时传InitListener
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(AddActivity.this, null);
        // 交互动画
        final RecognizerDialog iatDialog = new RecognizerDialog(AddActivity.this, null);
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

}

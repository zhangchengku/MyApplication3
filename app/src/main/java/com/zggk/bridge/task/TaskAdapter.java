package com.zggk.bridge.task;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;


import com.bumptech.glide.Glide;
import com.example.logic.ImgFileListActivity;
import com.tooklkit.Tooklkit;
import com.zggk.bridge.CameraActivity;
import com.zggk.bridge.CustomApp;

import com.zggk.bridge.R;


import com.zggk.bridge.jd.JdActivity;
import com.zggk.bridge.photo.MinePopupWindow;
import com.zggk.bridge.showImg.ShowImgActivity;
import com.zggk.bridge.utils.Utils;

import java.io.File;
import java.util.ArrayList;
/**
 * Created by 张成昆 on 2019-4-9.
 */

public class TaskAdapter extends BaseAdapter {

    private Context context;//上下文对象
    private ArrayList<Drawable> listPicture;// 图片集合
    private ArrayList<String> listVido = new ArrayList<String>();// 选择的图片地址集合
    private ArrayList<TaskBean> listImgUrl = new ArrayList<TaskBean>();// 选择的图片地址集合
    private ArrayList<String> listPictureUrl;// 网络图片地址集合
    private int showType;//1添加病害 需要有添加图片删除图片的功能  2病害详情 只做展示图片用
    private final int CHOOSE_PICTURE_CODE = 1;
    private final int CAMERA_CODE = 2;
    private int childViewPosition;
    private MinePopupWindow minePopupWindow;


    public ArrayList<TaskBean> getListImgUrl() {
        return listImgUrl;
    }

    public ArrayList<Drawable> getListPicture() {
        return listPicture;
    }
    public ArrayList<String> getListVido() {
        return listVido;
    }
    public TaskAdapter(Context context,
                       ArrayList<Drawable> listPicture, ArrayList<TaskBean> listImgUrl, int childViewPosition) {
        this.context = context;
        this.listPicture = listPicture;
        this.listImgUrl = listImgUrl;
        this.childViewPosition = childViewPosition;
        showType = 1;
    }

    public TaskAdapter(Context context,
                       ArrayList<String> listPictureUrl) {
        this.context = context;
        this.listPictureUrl = listPictureUrl;
        showType = 2;
    }

    @Override
    public int getCount() {
        if (listPicture != null && listPicture.size() > 0) {
            return listPicture.size();
        } else if (listPictureUrl != null && listPictureUrl.size() > 0) {
            return listPictureUrl.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (listPicture != null && listPicture.size() > 0) {
            return listPicture.get(position);
        } else if (listPictureUrl != null && listPictureUrl.size() > 0) {
            return listPictureUrl.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.picture_adapter_item, null);
            vh.pictureImg = (ImageView) convertView
                    .findViewById(R.id.send_topic_picture_adapter_item_img);
            vh.pictureCloseImg = (ImageView) convertView
                    .findViewById(R.id.send_topic_picture_adapter_item_close_img);
            vh.bofang = (ImageView) convertView
                    .findViewById(R.id.bofang);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final int fpos = position;
        vh.pictureCloseImg.setVisibility(View.GONE);
        int width = (Tooklkit.getWidth((Activity) context) - Tooklkit.dip2px(context, 10) * 3) / 4;
        LayoutParams params = new LayoutParams(width, width);
        vh.pictureImg.setLayoutParams(params);

        // 判断是否显示图片右上方的删除按钮
        if (showType == 1) {
            vh.pictureImg.setImageDrawable(listPicture.get(position));
            if (position == listPicture.size() - 1) {
                vh.pictureCloseImg.setVisibility(View.GONE);
                vh.bofang.setVisibility(View.GONE);
                if (context instanceof JdActivity) {
                    vh.pictureImg.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (minePopupWindow == null) {
                                minePopupWindow = new MinePopupWindow((JdActivity) context, itemOnClick);
                            }
                            minePopupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
                            Utils.hideInputWindow((JdActivity)context);
                        }
                    });
                }
            } else {
                vh.pictureImg.setOnClickListener(null);
                if (Utils.isNull(listImgUrl.get(fpos).getVideo())){
                    vh.bofang.setVisibility(View.GONE);
                }else {
                    vh.bofang.setVisibility(View.VISIBLE);
                }
                vh.pictureCloseImg.setVisibility(View.VISIBLE);
                // 图片右上方的删除图片的按钮
                vh.pictureCloseImg.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        listImgUrl.remove(fpos);
                        listPicture.remove(fpos);
                        v.setOnClickListener(null);
                        notifyDataSetChanged();
                    }
                });
                vh.pictureImg.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.isNull(listImgUrl.get(fpos).getVideo())){
                            Intent intent = new Intent(context, ShowImgActivity.class);
                            intent.putExtra("img2", listImgUrl.get(fpos).getPic());
                            intent.putExtra("position", fpos);
                            context.startActivity(intent);
                        }else {
                            Intent intent = new Intent(context, TaskActivity.class);
                            intent.putExtra("img2", listImgUrl.get(fpos).getPic());
                            intent.putExtra("vid2", listImgUrl.get(fpos).getVideo());
                            context.startActivity(intent);
                        }
                    }
                });
            }
        } else {
            vh.pictureCloseImg.setVisibility(View.GONE);
            String imgUrl = listPictureUrl.get(position);
            Glide.with(context)
                    .asBitmap()
                    .apply(CustomApp.app.options)
                    .load(imgUrl)
                    .into(vh.pictureImg);
            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, ShowImgActivity.class);
//                    intent.putExtra("img", listPictureUrl);
//                    intent.putExtra("position", fpos);
//                    context.startActivity(intent);
                }
            });
        }
        return convertView;
    }

    class ViewHolder {
        ImageView pictureImg;//选择的图片
        ImageView pictureCloseImg;//关闭按钮
        ImageView bofang;//关闭按钮
    }

    @Override
    public int getViewTypeCount() {
        if (listPicture != null) {
            if (listPicture.size() > 0) {
                return listPicture.size();
            } else {
                return 1;
            }
        } else if (listPictureUrl != null) {
            if (listPictureUrl.size() > 0) {
                return listPictureUrl.size();
            } else {
                return 1;
            }
        }
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     * 自定义pop监听
     */
    private OnClickListener itemOnClick = new OnClickListener() {
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
     * 从相册获取
     */
    public void getPicFromPhoto() {
        Intent intent = new Intent(context, ImgFileListActivity.class);
        intent.putExtra("position", childViewPosition);
        intent.putExtra("isLimitedNumber",false);
        intent.putExtra("maxsize", 1);
        ((JdActivity) context).startActivityForResult(intent, CHOOSE_PICTURE_CODE);
    }

    /**
     * 照相获取
     */
    public void getPicFromCamera() {
        Intent intent = new Intent(context, CameraActivity.class);
        ((JdActivity) context).setChildViewPosition(childViewPosition);
        ((JdActivity) context).startActivityForResult(intent, CAMERA_CODE);

    }

}
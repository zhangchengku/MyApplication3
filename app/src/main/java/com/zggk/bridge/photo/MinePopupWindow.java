package com.zggk.bridge.photo;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.zggk.bridge.R;


/**
 * 点击头像弹出对话框 查看证书弹出对话框
 *
 * @author zyh
 *
 */
public class MinePopupWindow extends PopupWindow {
    private Button vMineCarema;// 照相
    private Button vMinePhoto;// 相册
    private Button vMineCancel;// 取消
    private View vMenuView;// 对话框

    public MinePopupWindow(Activity context, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vMenuView = inflater.inflate(R.layout.mine_personal_head_pop, null);
        vMineCarema = (Button) vMenuView.findViewById(R.id.mine_camera_btn);
        vMinePhoto = (Button) vMenuView.findViewById(R.id.mine_photo_btn);
        vMineCancel = (Button) vMenuView.findViewById(R.id.mine_cancel_btn);
        vMineCarema.setOnClickListener(itemsOnClick);
        vMinePhoto.setOnClickListener(itemsOnClick);
        vMineCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // 设置SelectPicPopupWindow的View
        this.setContentView(vMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        // this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        // ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(new BitmapDrawable());
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        vMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = vMenuView.findViewById(R.id.mine_personal_rly)
                        .getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


}


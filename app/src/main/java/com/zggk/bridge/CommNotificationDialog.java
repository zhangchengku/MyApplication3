package com.zggk.bridge;

/**
 * Created by 张成昆 on 2019-4-4.
 */

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;


import com.tooklkit.Tooklkit;


/**
 * 通用提醒对话框
 *
 * @author xiaoqing
 *
 */
public class CommNotificationDialog {

    private Dialog dialog;// 对话框
    public TextView txtCancel;//取消按钮
    public TextView txtOk;//确定按钮
    public TextView txtTitle;//提示内容文本

    public Dialog getDialog() {
        return dialog;
    }

    /**
     * 构造方法
     *
     * @param context
     *            使用该对话框的类
     * @param title
     *            标题
     * @param okStr
     *            确定按钮文字
     * @param cancelStr
     *            取消按钮文字
     * @param okListener
     *            确定按钮点击监听
     */
    public CommNotificationDialog(final Activity context, String title,
                                  String okStr, String cancelStr, final CommBtnListener okListener) {
        dialog = new Dialog(context, R.style.CustomDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        View viewDialog = LayoutInflater.from(context).inflate(
                R.layout.update_dialog, null);
        LayoutParams params = new LayoutParams(Tooklkit.dip2px(context, 300),
                LayoutParams.WRAP_CONTENT);
        dialog.addContentView(viewDialog, params);
        txtTitle = (TextView) viewDialog
                .findViewById(R.id.com_notification_dialog_title_txt);
        txtOk = (TextView) viewDialog
                .findViewById(R.id.com_notification_dialog_ok_btn);
        txtCancel = (TextView) viewDialog
                .findViewById(R.id.com_notification_dialog_cancel_btn);
        txtTitle.setText(title);
        txtOk.setText(okStr);
        txtCancel.setText(cancelStr);
        // 确定按钮
        txtOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                okListener.CommOkBtnClick();
                if(dialog.isShowing()){
                    dialog.cancel();
                }
            }
        });
        // 取消按钮
        txtCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                okListener.CommCancelBtnClick();
                if(dialog.isShowing()){
                    dialog.cancel();
                }
            }
        });

    }

    /**
     * 设置对话框的提示内容
     */
    public void setWarmTitle(String title){
        txtTitle.setText(title);
    }

    /**
     * 显示
     */
    public void show() {
        dialog.show();
    }

    /**
     * 关闭
     */
    public void close() {
        dialog.cancel();
    }
}


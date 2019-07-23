package com.zggk.bridge.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zggk.bridge.R;


/**
 * 评分对话框
 * 
 * @author wangqian
 *
 */
public class CommAlertDialog extends Dialog implements View.OnClickListener {

	// *************** 控件声明 ***************//
	/** 标题区域 */
	private TextView tvTitle;
	/** 内容消息区域 */
	private TextView tvMessage;
	/** 左边的操作区域 */
	private Button btnActionLeft;
	/** 右边的操作区域 */
	private Button btnActionRight;
	/** 操作区域的分割线-顶部 */
	private View vActionLineTop;
	/** 操作区域的分割线-中间 */
	private View vActionLineMiddle;

	// *************** 变量声明 ***************//
	/** 相关操作的监听 */
	private OnDialogAlertActionListener listener;

	/**
	 * “通用对话框”的构造方法
	 * 
	 * @param context
	 *            上下文对象
	 */
	public CommAlertDialog(Context context) {
		super(context, R.style.CustomDialogStyle);
		View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_alert, null);
		setContentView(contentView, new ViewGroup.LayoutParams(Utils.calculateDialogWidth(context),
				ViewGroup.LayoutParams.WRAP_CONTENT));
		setCanceledOnTouchOutside(false);
		initViews(contentView);
		setAlertTitle(null);
		setAlertMsg(null);
		setAlertAction(null, null, null);
	}

	/**
	 * 获取对话框的标题控件
	 * @return
	 */
	public TextView getAlertTitleView(){
		return tvTitle;
	}

	/**
	 * 获取对话框的消息内容控件
	 * @return
	 */
	public TextView getAlertMessageView(){
		return tvMessage;
	}

	/**
	 * 获取对话框的左侧操作按钮
	 * @return
	 */
	public Button getAlertActionleftView(){
		return btnActionLeft;
	}

	/**
	 * 获取对话框的右侧操作按钮
	 * @return
	 */
	public Button getAlertActionRightView(){
		return btnActionRight;
	}

	/**
	 * 设置对话框的标题文本
	 * 
	 * @param title
	 */
	public void setAlertTitle(String title) {
		if (tvTitle == null) {
			return;
		}
		if (TextUtils.isEmpty(title)) {
			tvTitle.setText("");
			tvTitle.setVisibility(View.GONE);
		} else {
			tvTitle.setText(title);
			tvTitle.setVisibility(View.VISIBLE);
		}
	}


	/**
	 * 设置对话框的消息文本
	 * 
	 * @param msg
	 */
	public void setAlertMsg(String msg) {
		if (tvMessage == null) {
			return;
		}
		if (TextUtils.isEmpty(msg)) {
			tvMessage.setText("");
			tvMessage.setVisibility(View.GONE);
		} else {
			tvMessage.setText(msg);
			tvMessage.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 设置对话框的消息对齐方式
	 * @param gravity
     */
	public void setAlertMsgGravity(int gravity) {
		if (tvMessage == null) {
			return;
		}
		tvMessage.setGravity(gravity);
	}

	/**
	 * 设置对话框的左边的操作文字
	 * 
	 * @param txtActionLeft
	 *            左边操作文本
	 */
	public void setActionLeftText(String txtActionLeft) {
		if (btnActionLeft == null) {
			return;
		}
		if (TextUtils.isEmpty(txtActionLeft)) {
			btnActionLeft.setText("");
			btnActionLeft.setVisibility(View.GONE);
		} else {
			btnActionLeft.setText(txtActionLeft);
			btnActionLeft.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 设置对话框的右边的操作文字
	 * 
	 * @param txtActionRight
	 *            右边操作文本
	 */
	public void setActionRightText(String txtActionRight) {
		if (btnActionRight == null) {
			return;
		}
		if (TextUtils.isEmpty(txtActionRight)) {
			btnActionRight.setText("");
			btnActionRight.setVisibility(View.GONE);
		} else {
			btnActionRight.setText(txtActionRight);
			btnActionRight.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 设置对话框的操作区域
	 * 
	 * @param txtActionLeft
	 *            左边操作文本
	 * @param txtActionRight
	 *            右边操作文本
	 * @param listener
	 *            操作的监听
	 */
	public void setAlertAction(String txtActionLeft, String txtActionRight, OnDialogAlertActionListener listener) {
		if (btnActionLeft == null || btnActionRight == null) {
			return;
		}
		setActionLeftText(txtActionLeft);
		setActionRightText(txtActionRight);
		this.listener = listener;
	}

	/**
	 * 在显示对话框之前，重置操作区域的布局
	 */
	private void preShow() {
		int visibilityOfActionLeft = btnActionLeft.getVisibility();
		int visibilityOfActionRight = btnActionRight.getVisibility();
		// 只显示右边操作区域
		if (visibilityOfActionLeft == View.GONE && visibilityOfActionRight == View.VISIBLE) {
			vActionLineMiddle.setVisibility(View.GONE);
			btnActionRight.setBackgroundResource(R.drawable.shape_roundrect_white_b_sel);
		}
		// 只显示左边操作区域
		else if (visibilityOfActionLeft == View.VISIBLE && visibilityOfActionRight == View.GONE) {
			vActionLineMiddle.setVisibility(View.GONE);
			btnActionLeft.setBackgroundResource(R.drawable.shape_roundrect_white_b_sel);
		}
		// 左右操作区域都显示
		else if (visibilityOfActionLeft == View.VISIBLE && visibilityOfActionRight == View.VISIBLE) {
			vActionLineMiddle.setVisibility(View.VISIBLE);
			btnActionLeft.setBackgroundResource(R.drawable.shape_roundrect_white_bl_sel);
			btnActionRight.setBackgroundResource(R.drawable.shape_roundrect_white_br_sel);
		}
		// 操作区域全都隐藏
		else {
			vActionLineTop.setVisibility(View.GONE);
			vActionLineMiddle.setVisibility(View.GONE);
		}
	}

	@Override
	public void show() {
		preShow();
		super.show();
	}

	/**
	 * 初始化控件
	 * 
	 * @param view
	 */
	private void initViews(View view) {
		tvTitle = (TextView) view.findViewById(R.id.dialog_alert_title_tv);
		tvMessage = (TextView) view.findViewById(R.id.dialog_alert_msg_tv);
		btnActionLeft = (Button) view.findViewById(R.id.dialog_alert_action_left_btn);
		btnActionRight = (Button) view.findViewById(R.id.dialog_alert_action_right_btn);
		vActionLineTop = view.findViewById(R.id.dialog_alert_action_line_top);
		vActionLineMiddle = view.findViewById(R.id.dialog_alert_action_line_middle);

		btnActionLeft.setOnClickListener(this);
		btnActionRight.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_alert_action_left_btn:
			dismiss();
			if (listener != null) {
				listener.onClickAction(1);
			}
			break;

		case R.id.dialog_alert_action_right_btn:
			dismiss();
			if (listener != null) {
				listener.onClickAction(2);
			}
			break;
		}
	}

	/**
	 * 对话框的相关的操作监听
	 * 
	 * @author wangqian
	 *
	 */
	public interface OnDialogAlertActionListener {
		/**
		 * 点击操作区域的回调
		 * 
		 * @param actionType
		 *            1：ActionLeft，2：ActionRight
		 */
		void onClickAction(int actionType);
	}
}

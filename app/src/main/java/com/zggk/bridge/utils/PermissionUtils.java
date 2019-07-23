package com.zggk.bridge.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;


import com.zggk.bridge.CustomApp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 权限管理类
 * 
 * @author wangqian
 *
 */
public class PermissionUtils {

	/** 同时申请多个权限的请求码 */
	public final int CODE_REQ_MULTIPLE_PERMISSIONS = 26;

	/** 当前的Activity */
	private Activity curActivity;
	/** */
	private CommAlertDialog dialogAlert;

	public static PermissionUtils newInstance(Activity curActivity) {
		return new PermissionUtils(curActivity);
	}

	public PermissionUtils(Activity curActivity) {
		this.curActivity = curActivity;
	}

	/**
	 * 获取提示框
	 * 
	 * @return
	 */
	private CommAlertDialog getDialogAlert() {
		if (dialogAlert == null) {
			dialogAlert = new CommAlertDialog(curActivity);
			dialogAlert.setAlertMsg("");
		}
		return dialogAlert;
	}

	/**
	 * 检测使用的权限
	 * 
	 * @param permissionsList
	 * @return true：需要请求权限；false：无需请求权限
	 */
	public boolean requestPermissions(List<String> permissionsList,int requestCode) {
		if (permissionsList == null || permissionsList.size() <= 0 || Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			return false;
		}
		List<String> permissionsNeeded = new ArrayList<String>();
		for (String permission : permissionsList) {
			if (!TextUtils.isEmpty(permission)
					&& ContextCompat.checkSelfPermission(curActivity, permission) != PackageManager.PERMISSION_GRANTED) {
				permissionsNeeded.add(permission);
				// Check for Rationale Option
				// 第一次请求权限时，用户拒绝了，下一次请求该方法后，返回true。 应该显示一些为什么需要这个权限的说明
				// 第二次请求权限时，用户拒绝了，并选择了“不在提醒”的选项时, 返回false
				// 设备的策略禁止当前应用获取这个权限的授权，返回false
				if (!ActivityCompat.shouldShowRequestPermissionRationale(curActivity, permission)) {
					// TODO 弹出框提示用户授权该权限
					// getDialogAlert().show();
				}
			}
		}
		if (permissionsNeeded.size() > 0) {
			ActivityCompat.requestPermissions(curActivity, permissionsNeeded.toArray(new String[permissionsNeeded.size()]),
					requestCode);
			return true;
		}
		return false;
	}

	public boolean requestPermissions(List<String> permissionsList) {
		return requestPermissions(permissionsList,CODE_REQ_MULTIPLE_PERMISSIONS);
	}

	/**
	 * 处理权限请求的结果
	 * 
	 * @param permissionsReqList
	 *            请求的权限列表
	 * @param requestCode
	 * @param permissions
	 * @param grantResults
	 * @return true：权限授权成功；false：某些权限被拒绝
	 */
	public boolean dealRequestPermissionsResult(List<String> permissionsReqList, int requestCode, String[] permissions,
			int[] grantResults) {
		switch (requestCode) {
		case CODE_REQ_MULTIPLE_PERMISSIONS: {
			if (permissionsReqList == null || permissionsReqList.size() <= 0) {
				break;
			}
			Map<String, Integer> perms = new HashMap<String, Integer>();
			// 初始化权限集合
			for (String permission : permissionsReqList) {
				perms.put(permission, PackageManager.PERMISSION_GRANTED);
			}
			// 填充结果
			for (int i = 0; i < permissions.length; i++) {
				perms.put(permissions[i], grantResults[i]);
			}
			// 检测授权结果
			boolean flag = true;
			for (Entry<String, Integer> perm : perms.entrySet()) {
				if (perm.getValue() != PackageManager.PERMISSION_GRANTED) {
					flag = false;
					break;
				}
			}
			if (flag) {
				// 所有的权限授权成功
				return true;
			} else {
				// 某些权限被拒绝
				CustomApp.app.customToast("某些权限的授权被拒绝");
				return false;
			}
		}
		}
		return true;
	}

}

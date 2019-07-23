package com.example.logic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.choisemorepictures.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImgFileListActivity extends Activity implements
		OnItemClickListener {

	ImageView imgBack;
	TextView vTitle;
	ListView listView;
	RelativeLayout vTopNavLayout;
	Util util;
	ImgFileListAdapter listAdapter;
	List<FileTraversal> locallist;
	boolean isLimitedNumber;//是否限制选择图片的个数
	private int position;//传入进来的当前添加病害项的下标
	int pictureNum;//之前已经选择了多少张图片 跳转界面时传入进来的值
	int maxsize;//允许选择的最大值 默认是9张
	final int MAX_SIZE=9;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imgfilelist);
		isLimitedNumber = getIntent().getBooleanExtra("isLimitedNumber", false);
		maxsize=getIntent().getIntExtra("maxsize",MAX_SIZE);
		position=getIntent().getIntExtra("position",-1);
		pictureNum=getIntent().getIntExtra("pictureNum", 0);
		listView = (ListView) findViewById(R.id.listViewImgFile);
		imgBack = (ImageView) findViewById(R.id.image_file_back_img);
		vTopNavLayout = (RelativeLayout) findViewById(R.id.image_file_top_nav_layout);
		vTopNavLayout.setBackgroundColor(getResources().getColor(
				R.color.title_bg_color));
		vTitle = (TextView) findViewById(R.id.choose_picture_list_title);
		imgBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		util = new Util(this);
		locallist = util.LocalImgFileList();
		List<HashMap<String, String>> listdata = new ArrayList<HashMap<String, String>>();
		Bitmap bitmap[] = null;
		if (locallist != null) {
			bitmap = new Bitmap[locallist.size()];
			String unit = getResources()
					.getString(R.string.choose_picture_unit);
			for (int i = 0; i < locallist.size(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("filecount", locallist.get(i).filecontent.size() + unit);
				map.put("imgpath",
						locallist.get(i).filecontent.get(0) == null ? null
								: (locallist.get(i).filecontent.get(0)));
				map.put("filename", locallist.get(i).filename);
				listdata.add(map);
			}
		}
		listAdapter = new ImgFileListAdapter(this, listdata);
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(this, ImgsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable("data", locallist.get(arg2));
		bundle.putBoolean("isLimitedNumber", isLimitedNumber);
		bundle.putInt("pictureNum", pictureNum);
		bundle.putInt("maxsize",maxsize);
		intent.putExtras(bundle);
		startActivityForResult(intent, 199);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 199 && data != null) {
			Intent intent = new Intent();
			intent.putStringArrayListExtra("filelist",
					data.getStringArrayListExtra("filelist"));
			intent.putExtra("position",position);
			setResult(RESULT_OK, intent);
			finish();
		}
	}

}

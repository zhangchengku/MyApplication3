package com.example.logic;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.choisemorepictures.MainActivity;
import com.example.choisemorepictures.R;
import com.example.logic.ImgsAdapter.OnItemClickClass;

import static android.R.attr.max;

public class ImgsActivity extends Activity {

	LinearLayout vBackLayout;
	TextView vTitle;
	RelativeLayout vTopNavLayout;
	TextView vOkTxt;
	Bundle bundle;
	FileTraversal fileTraversal;
	GridView imgGridView;
	ImgsAdapter imgsAdapter;
	LinearLayout select_layout;
	Util util;
	RelativeLayout relativeLayout2;
	HashMap<Integer, ImageView> hashImage;
	Button choise_button;
	ArrayList<String> filelist;
	boolean isLimitedNumber;
	int pictureNum;
	String strChoosePicCount = "";
	int maxsize=9;//允许选择的最大值 默认是9张

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photogrally);
		strChoosePicCount = getResources().getString(
				R.string.choose_picture_count);
		imgGridView = (GridView) findViewById(R.id.gridView1);
		vBackLayout = (LinearLayout) findViewById(R.id.photo_grally_back_layout);
		vTitle = (TextView) findViewById(R.id.photo_grally_title_txt);
		vOkTxt = (TextView) findViewById(R.id.photo_grally_ok_txt);
		vTopNavLayout = (RelativeLayout) findViewById(R.id.photo_grally_top_nav_layout);
		vTopNavLayout.setBackgroundColor(getResources().getColor(
				R.color.title_bg_color));
		vBackLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tobreak();
			}
		});
		// 确定
		vOkTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectOk();
			}
		});
		try{
			bundle = getIntent().getExtras();
			fileTraversal = bundle.getParcelable("data");
			isLimitedNumber = bundle.getBoolean("isLimitedNumber", false);
			pictureNum = bundle.getInt("pictureNum");
			maxsize=bundle.getInt("maxsize");
			imgsAdapter = new ImgsAdapter(this, fileTraversal.filecontent,
					onItemClickClass);
			imgGridView.setAdapter(imgsAdapter);
		}catch (Exception e){
			e.printStackTrace();
		}
		select_layout = (LinearLayout) findViewById(R.id.selected_image_layout);
		relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayoutImagesGradeView);
		choise_button = (Button) findViewById(R.id.button3);
		choise_button.setText(String.format(strChoosePicCount, 0));
		hashImage = new HashMap<Integer, ImageView>();
		filelist = new ArrayList<String>();
		// imgGridView.setOnItemClickListener(this);
		util = new Util(this);

		if (pictureNum > 0) {
			choise_button.setText(String.format(strChoosePicCount, pictureNum));
		}

	}

	class BottomImgIcon implements OnItemClickListener {

		int index;

		public BottomImgIcon(int index) {
			this.index = index;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

		}
	}

	@SuppressLint("NewApi")
	public ImageView iconImage(String filepath, int index, CheckBox checkBox)
			throws FileNotFoundException {
		LinearLayout.LayoutParams params = new LayoutParams(
				relativeLayout2.getMeasuredHeight() - 10,
				relativeLayout2.getMeasuredHeight() - 10);
		ImageView imageView = new ImageView(this);
		imageView.setLayoutParams(params);
		imageView.setBackgroundResource(R.drawable.imgbg_def);
		float alpha = 100;
		imageView.setAlpha(alpha);
		util.imgExcute(imageView, imgCallBack, filepath);
		imageView.setOnClickListener(new ImgOnclick(filepath, checkBox));
		return imageView;
	}

	ImgCallBack imgCallBack = new ImgCallBack() {
		@Override
		public void resultImgCall(ImageView imageView, Bitmap bitmap) {
			imageView.setImageBitmap(bitmap);
		}
	};

	class ImgOnclick implements OnClickListener {
		String filepath;
		CheckBox checkBox;

		public ImgOnclick(String filepath, CheckBox checkBox) {
			this.filepath = filepath;
			this.checkBox = checkBox;
		}

		@Override
		public void onClick(View arg0) {
			checkBox.setChecked(false);
			select_layout.removeView(arg0);
			choise_button.setText(String.format(strChoosePicCount,
					select_layout.getChildCount()));
			filelist.remove(filepath);
		}
	}

	ImgsAdapter.OnItemClickClass onItemClickClass = new OnItemClickClass() {
		@Override
		public void OnItemClick(View v, int Position, CheckBox checkBox) {
			if(fileTraversal!=null&&fileTraversal.filecontent!=null&&fileTraversal.filecontent.size()>Position){
				String filapath = fileTraversal.filecontent.get(Position);
				if (checkBox.isChecked()) {
					checkBox.setChecked(false);
					select_layout.removeView(hashImage.get(Position));
					filelist.remove(filapath);
					int num = pictureNum + select_layout.getChildCount();
					choise_button.setText(String.format(strChoosePicCount, num));
				} else {
					try {
						if (isLimitedNumber
								&& (select_layout.getChildCount() + pictureNum == maxsize)) {
							String warm_title=String.format(getResources().getString(
									R.string.choose_picture_tips_max_num_warm),String.valueOf(maxsize));
							Toast.makeText(
									ImgsActivity.this,
									warm_title,
									Toast.LENGTH_SHORT).show();
						} else {
							checkBox.setChecked(true);
							Log.i("img", "img choise position->" + Position);
							ImageView imageView = iconImage(filapath, Position,
									checkBox);
							if (imageView != null) {
								hashImage.put(Position, imageView);
								filelist.add(filapath);
								select_layout.addView(imageView);
								int num = pictureNum
										+ select_layout.getChildCount();
								choise_button.setText(String.format(
										strChoosePicCount, num));
							}
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
	};

	public void tobreak() {
		finish();
	}

	/**
	 * 检查图片是否已经选择
	 *
	 * @return
	 */
	private boolean checkChoseFileList() {
		if (filelist == null || filelist.size() <= 0) {
			Toast.makeText(
					ImgsActivity.this,
					getResources()
							.getString(R.string.choose_picture_tips_empty),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	/**
	 * FIXME 亲只需要在这个方法把选中的文档目录已list的形式传过去即可
	 *
	 * @param view
	 */
	public void sendfiles(View view) {
		if (!checkChoseFileList())
			return;
		Intent intent = new Intent(this, MainActivity.class);
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("files", filelist);
		intent.putExtras(bundle);
		startActivity(intent);

	}

	public void selectOk() {
		if (!checkChoseFileList())
			return;
		Intent intent = new Intent();
		intent.putStringArrayListExtra("filelist", filelist);
		setResult(RESULT_OK, intent);
		finish();
	}
}

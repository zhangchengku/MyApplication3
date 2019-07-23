package com.zggk.bridge.popuwindow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.zggk.bridge.R;

import java.util.ArrayList;

/**
 *
 * @author dongxiaoqing
 *
 */
public class PopMyDataSelectAdapter extends AbstractWheelTextAdapter {

	private ArrayList<String> dates;
	private String fontSize="14";//字体大小
	private Context context;
	private int currentIndex;

	public PopMyDataSelectAdapter(Context context, ArrayList<String> list, int currentIndex) {
		super(context, R.layout.age_wheel_item, R.id.text_age_wheel_item,currentIndex);
		this.context=context;
		dates = list;
        this.currentIndex=currentIndex;
	}

	@Override
	public int getItemsCount() {
		return dates.size();
	}

	@Override
	protected CharSequence getItemText(int index) {
		return dates.get(index);
	}

	@Override
	public View getItem(int index, View convertView, ViewGroup parent) {
		View view = super.getItem(index, convertView, parent);
		TextView txt = (TextView) view.findViewById(R.id.text_age_wheel_item);
		txt.setText(getItemText(index));
		txt.setTextSize(Float.parseFloat(fontSize));
		if(index==currentIndex){
			txt.setTextColor(context.getResources().getColor(R.color.text_3));
		}else{
			txt.setTextColor(context.getResources().getColor(R.color.text_9));
		}
		return view;
	}


}

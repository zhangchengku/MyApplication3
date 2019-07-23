package com.zggk.bridge.popuwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.zggk.bridge.R;

import java.util.ArrayList;

/**
 * 模糊搜索列表适配器
 * Created by dongxiaoqing on 2018/9/29.
 */

public class EditSearchAdapter extends BaseAdapter {

    private ArrayList<String> listData;
    private Context context;


    public EditSearchAdapter(Context context, ArrayList<String> listData){
        this.context=context;
        this.listData=listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView==null){
            vh=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.edit_searc_item,parent,false);
            vh.txtContent=(TextView)convertView.findViewById(R.id.edit_search_item_txt);
            convertView.setTag(vh);
        }else{
            vh=(ViewHolder)convertView.getTag();
        }
        vh.txtContent.setText(listData.get(position));

        return convertView;
    }

    class ViewHolder{
        TextView txtContent;
    }
}

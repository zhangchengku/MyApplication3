package com.zggk.bridge.popuwindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.util.ArrayList;
import com.tooklkit.Tooklkit;
import com.zggk.bridge.R;
import com.zggk.bridge.add.AddActivity;
import com.zggk.bridge.utils.Utils;

/**
 * Created by 张成昆 on 2019-4-4.
 */

public class AddAllPopuWindow  extends PopupWindow {

    private Activity activity;
    private ArrayList<String> listDataStr;
    private ListView listView;
    private TextView vTitleTxt;
    private EditSearchAdapter adapter;
    private SelectPopuWindowListener listener;
    private String titleStr;

    public AddAllPopuWindow(Activity activity, String titleStr, ArrayList<String> listDataStr, SelectPopuWindowListener listener){
        this.activity = activity;
        this.listDataStr=listDataStr;
        this.listener=listener;
        this.titleStr=titleStr;
        View contentView = LayoutInflater.from(activity).inflate(
                R.layout.add_all_popu, null);
        setContentView(contentView);
        setWidth(Tooklkit.getWidth(activity)- Tooklkit.dip2px(activity,25));
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        setAnimationStyle(R.style.mypopwindow_anim_style);
        initViews(contentView);
    }

    private void initViews(View contentView){
        vTitleTxt=(TextView)contentView.findViewById(R.id.disease_new_sel_obj_title_txt);
        listView=contentView.findViewById(R.id.disease_new_sel_obj_list);
        adapter=new EditSearchAdapter(activity,listDataStr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.selectPosition(position);
                close();
            }
        });
        if(!Utils.isNull(titleStr)){
            vTitleTxt.setVisibility(View.VISIBLE);
            vTitleTxt.setText(titleStr);
        }else{
            vTitleTxt.setVisibility(View.GONE);
        }

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if(activity instanceof AddActivity){
                    ((AddActivity)activity).hideZheZhaoView();
                }
            }
        });
    }

    /**
     * 显示筛选窗口
     */
    public void show(View anchor) {
        if (isShowing()) {
            return;
        }
        if (anchor != null) {
            showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
        }
        if (activity instanceof AddActivity) {
            ((AddActivity)activity).showZheZhaoView();
        }
    }

    /**
     * 关闭筛选窗口
     */
    public void close() {
        if (isShowing()) {
            dismiss();
        }
    }

    public void notifityData(){
        adapter.notifyDataSetChanged();
    }
}

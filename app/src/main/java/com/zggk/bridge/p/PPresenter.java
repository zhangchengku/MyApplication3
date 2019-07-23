package com.zggk.bridge.p;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.zggk.bridge.Bean.AddINTbean;
import com.zggk.bridge.Bean.BridgeIntbean;
import com.zggk.bridge.Bean.ISBridgeIntbean;
import com.zggk.bridge.Bean.Pbean;
import com.zggk.bridge.CustomApp;
import com.zggk.bridge.mvp.BasePresenterImpl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PPresenter extends BasePresenterImpl<PContract.View> implements PContract.Presenter{
    @Override
    public void testinfo(String cjid) {

        OkHttpUtils.get()
                .tag(this)
                .addParams("cjid", cjid)
                .url(CustomApp.BASEURL+"QueryCjInfoByCjId")
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Pbean videoVos2 = JSON.parseObject(response, Pbean.class);
                        mView.getData(videoVos2);
                    }
                });

    }
    @Override
    public void isBridge(String lng1,String lat1,String lng2,String lat2) {

        OkHttpUtils.get()
                .tag(this)
                .addParams("lng1", lng1)
                .addParams("lat1", lat1)
                .addParams("lng2", lng2)
                .addParams("lat2", lat2)
                .url(CustomApp.BASEURL+"JudgeRange")
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        ISBridgeIntbean videoVos2 = JSON.parseObject(response, ISBridgeIntbean.class);
                        mView.getData2(videoVos2);
                    }
                });

    }
    @Override
    public void add(String json) {
        OkHttpUtils.get()
                .tag(this)
                .addParams("json", json)
                .url(CustomApp.BASEURL+"UptCjInfo")
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        AddINTbean videoVos2 = JSON.parseObject(response, AddINTbean.class);
                        mView.getData3(videoVos2);
                    }
                });

    }
}

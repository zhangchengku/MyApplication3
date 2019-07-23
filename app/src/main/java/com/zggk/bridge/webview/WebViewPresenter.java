package com.zggk.bridge.webview;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.zggk.bridge.Bean.AddINTbean;
import com.zggk.bridge.CustomApp;
import com.zggk.bridge.mvp.BasePresenterImpl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class WebViewPresenter extends BasePresenterImpl<WebViewContract.View> implements WebViewContract.Presenter{
    @Override
    public void testinfo(String json) {

        OkHttpUtils.post()
                .tag(this)
                .addParams("json", json)
                .url(CustomApp.BASEURL+"SaveQLImageInfo")
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        AddINTbean videoVos2 = JSON.parseObject(response, AddINTbean.class);
                        mView.getData(videoVos2);
                    }
                });

    }
    @Override
    public void addPhoto(String userId,String bytes) {

        OkHttpUtils.post()
                .tag(this)
                .addParams("userId", userId)
                .addParams("bytes", bytes)
                .url(CustomApp.BASEURL+"UpdateUserPhoto")
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        AddINTbean videoVos2 = JSON.parseObject(response, AddINTbean.class);
                        mView.getData2(videoVos2);
                    }
                });

    }
    //UserId":"test","Content":"1111111","ToFrom":"","CJID":"89c2d14c-28d5-4529-a73c-0dd61cd36636"
    @Override
    public void addPL(String json) {

        OkHttpUtils.get()
                .tag(this)
                .addParams("json", json)
                .url(CustomApp.BASEURL+"SaveCollectDiscuss")
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

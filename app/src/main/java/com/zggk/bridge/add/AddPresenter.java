package com.zggk.bridge.add;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.zggk.bridge.Bean.AddBean;
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

public class AddPresenter extends BasePresenterImpl<AddContract.View> implements AddContract.Presenter{
    @Override
    public void testinfo(String bridgCode) {

        OkHttpUtils.get()
                .tag(this)
                .addParams("bridgeCode", bridgCode)
                .url(CustomApp.BASEURL+"InitJdInfo")
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        AddBean videoVos2 = JSON.parseObject(response, AddBean.class);
                        mView.getData(videoVos2);
                    }
                });

    }
    @Override
    public void add(String json) {
        OkHttpUtils.post()
                .tag(this)
                .addParams("json", json)
                .url(CustomApp.BASEURL+"SaveJdybInfo")
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

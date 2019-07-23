package com.zggk.bridge.bdphone;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.zggk.bridge.Bean.AddBean;
import com.zggk.bridge.Bean.QQBean;
import com.zggk.bridge.CustomApp;
import com.zggk.bridge.mvp.BasePresenterImpl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BDphonePresenter extends BasePresenterImpl<BDphoneContract.View> implements BDphoneContract.Presenter{
    @Override
    public void testinfo(String type,String getUserID,String loginUserID) {

        OkHttpUtils.get()
                .addParams("type", type)
                .addParams("gaveUserID", getUserID)
                .addParams("loginUserID", loginUserID)
                .url(CustomApp.BASEURL+"SetGaveLogin")
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        QQBean videoVos2 = JSON.parseObject(response, QQBean.class);
                        mView.getData(videoVos2);
                    }
                });

    }
}

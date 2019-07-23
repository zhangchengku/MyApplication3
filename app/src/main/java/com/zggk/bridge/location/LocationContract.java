package com.zggk.bridge.location;

import android.content.Context;

import com.zggk.bridge.Bean.Basebean;
import com.zggk.bridge.mvp.BasePresenter;
import com.zggk.bridge.mvp.BaseRequestView;
import com.zggk.bridge.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LocationContract {
    interface View extends BaseRequestView {
        void getData(Basebean videoVos2);
    }

    interface  Presenter extends BasePresenter<View> {//网络
        void testinfo(String code,String lon,String lat);

    }
}

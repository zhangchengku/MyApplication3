package com.zggk.bridge.bdphone;

import android.content.Context;

import com.zggk.bridge.Bean.AddBean;
import com.zggk.bridge.Bean.QQBean;
import com.zggk.bridge.mvp.BasePresenter;
import com.zggk.bridge.mvp.BaseRequestView;
import com.zggk.bridge.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BDphoneContract {
    interface View extends BaseRequestView {
        void getData(QQBean videoVos2);
    }

    interface  Presenter extends BasePresenter<View> {//网络
        void testinfo(String Uid,String type,String phone);

    }
}

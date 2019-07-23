package com.zggk.bridge.update;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wuliang on 2017/7/24.
 * <p>
 * App版本号
 */

public class AppVersionBO implements Serializable {


    /**
     * STATE : 1
     * DATA : [{"versionCode":"1","appVision":"1.0.0","updateMessage":"完善基本功能","minVersionCode":"1","minVersionName":"1.0.1","url":"/UpdateApp/"}]
     */

    private String STATE;
    private List<DATABean> DATA;

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public List<DATABean> getDATA() {
        return DATA;
    }

    public void setDATA(List<DATABean> DATA) {
        this.DATA = DATA;
    }

    public static class DATABean {
        /**
         * versionCode : 1
         * appVision : 1.0.0
         * updateMessage : 完善基本功能
         * minVersionCode : 1
         * minVersionName : 1.0.1
         * url : /UpdateApp/
         */

        private String versionCode;
        private String appVision;
        private String updateMessage;
        private String minVersionCode;
        private String minVersionName;
        private String url;

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getAppVision() {
            return appVision;
        }

        public void setAppVision(String appVision) {
            this.appVision = appVision;
        }

        public String getUpdateMessage() {
            return updateMessage;
        }

        public void setUpdateMessage(String updateMessage) {
            this.updateMessage = updateMessage;
        }

        public String getMinVersionCode() {
            return minVersionCode;
        }

        public void setMinVersionCode(String minVersionCode) {
            this.minVersionCode = minVersionCode;
        }

        public String getMinVersionName() {
            return minVersionName;
        }

        public void setMinVersionName(String minVersionName) {
            this.minVersionName = minVersionName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

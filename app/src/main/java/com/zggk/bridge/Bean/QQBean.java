package com.zggk.bridge.Bean;

import java.util.List;

/**
 * Created by 张成昆 on 2019-5-14.
 */

public class QQBean {


    /**
     * STATE : 2
     * DATA : [{"UserID":"test","UserName":"采集账户","UnitNo":"400","UnitName":"保利长大工程有限公司","ShortUnitName":"保利长大二公司","ParentID":"0","UserType":"2","Post":" ","SpType":"0"}]
     */

    private String STATE;
    private List<DATABean> DATA;
    private String MSG;

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

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
         * UserID : test
         * UserName : 采集账户
         * UnitNo : 400
         * UnitName : 保利长大工程有限公司
         * ShortUnitName : 保利长大二公司
         * ParentID : 0
         * UserType : 2
         * Post :
         * SpType : 0
         */

        private String UserID;
        private String UserName;
        private String UnitNo;
        private String UnitName;
        private String ShortUnitName;
        private String ParentID;
        private String UserType;
        private String Post;
        private String SpType;

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getUnitNo() {
            return UnitNo;
        }

        public void setUnitNo(String UnitNo) {
            this.UnitNo = UnitNo;
        }

        public String getUnitName() {
            return UnitName;
        }

        public void setUnitName(String UnitName) {
            this.UnitName = UnitName;
        }

        public String getShortUnitName() {
            return ShortUnitName;
        }

        public void setShortUnitName(String ShortUnitName) {
            this.ShortUnitName = ShortUnitName;
        }

        public String getParentID() {
            return ParentID;
        }

        public void setParentID(String ParentID) {
            this.ParentID = ParentID;
        }

        public String getUserType() {
            return UserType;
        }

        public void setUserType(String UserType) {
            this.UserType = UserType;
        }

        public String getPost() {
            return Post;
        }

        public void setPost(String Post) {
            this.Post = Post;
        }

        public String getSpType() {
            return SpType;
        }

        public void setSpType(String SpType) {
            this.SpType = SpType;
        }
    }
}

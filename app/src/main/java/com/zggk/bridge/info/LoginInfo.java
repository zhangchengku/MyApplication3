package com.zggk.bridge.info;

import java.util.List;

/**
 * Created by 张成昆 on 2019-2-18.
 */

public class LoginInfo {

    /**
     * DATA : [{"UserID":"GD440000","UserName":"广东省公路事务中心","UnitNo":"5000001000","UnitName":"广东省公路事务中心","ShortUnitName":"广东公路","ParentID":"0"}]
     * STATE : 1
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
         * UserID : GD440000
         * UserName : 广东省公路事务中心
         * UnitNo : 5000001000
         * UnitName : 广东省公路事务中心
         * ShortUnitName : 广东公路
         * ParentID : 0
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

        public String getSpType() {
            return SpType;
        }

        public void setSpType(String spType) {
            SpType = spType;
        }

        public String getPost() {
            return Post;
        }

        public void setPost(String post) {
            Post = post;
        }

        public String getUserType() {
            return UserType;
        }

        public void setUserType(String userType) {
            UserType = userType;
        }

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
    }
}

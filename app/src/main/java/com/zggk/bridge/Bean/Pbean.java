package com.zggk.bridge.Bean;

import java.util.List;

/**
 * Created by 张成昆 on 2019-5-7.
 */

public class Pbean {

    /**
     * STATE : 1
     * DATA : [{"ID":"8cd7c181-3af6-4c80-9f24-379caeb6a2ae","Description":"通过这个录音来实现进度信息的汇报","ReportTime":"2019-04-24T14:44:00","UnintNo":"400","UnitName":"广东长大二公司","BridgeName":"莲花桥","Lon":"113.28156","Lat":"23.117353","Location":"","COMPLETION":2,"COMPLETIONNAME":"已完工","files":"2dac8234-7d85-4d7d-9bd6-3664775860f7|http://106.37.229.146:7103/Files/QLCJ/C420441803L0010/2019-04-24/69521d1d-ec93-4557-bd4e-bd406d88239c.jpg,3cae2f06-291b-46ad-85bb-0f8b7c643dc3|http://106.37.229.146:7103/Files/QLCJ/C420441803L0010/2019-04-24/1cadd94d-c7af-4a1f-9f74-f25c843cc977.jpg|http://106.37.229.146:7103/Files/QLCJ/C420441803L0010/2019-04-24/246a1b95-42e0-4f37-aa92-1dc51b2ae47c.mp4"}]
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
         * ID : 8cd7c181-3af6-4c80-9f24-379caeb6a2ae
         * Description : 通过这个录音来实现进度信息的汇报
         * ReportTime : 2019-04-24T14:44:00
         * UnintNo : 400
         * UnitName : 广东长大二公司
         * BridgeName : 莲花桥
         * Lon : 113.28156
         * Lat : 23.117353
         * Location :
         * COMPLETION : 2
         * COMPLETIONNAME : 已完工
         * files : 2dac8234-7d85-4d7d-9bd6-3664775860f7|http://106.37.229.146:7103/Files/QLCJ/C420441803L0010/2019-04-24/69521d1d-ec93-4557-bd4e-bd406d88239c.jpg,3cae2f06-291b-46ad-85bb-0f8b7c643dc3|http://106.37.229.146:7103/Files/QLCJ/C420441803L0010/2019-04-24/1cadd94d-c7af-4a1f-9f74-f25c843cc977.jpg|http://106.37.229.146:7103/Files/QLCJ/C420441803L0010/2019-04-24/246a1b95-42e0-4f37-aa92-1dc51b2ae47c.mp4
         */

        private String ID;
        private String Description;
        private String ReportTime;
        private String UnintNo;
        private String UnitName;
        private String BridgeCode;
        private String BridgeName;
        private String Lon;
        private String Lat;
        private String Location;
        private String COMPLETION;
        private String COMPLETIONNAME;
        private String files;

        public String getBridgeCode() {
            return BridgeCode;
        }

        public void setBridgeCode(String bridgeCode) {
            BridgeCode = bridgeCode;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getReportTime() {
            return ReportTime;
        }

        public void setReportTime(String ReportTime) {
            this.ReportTime = ReportTime;
        }

        public String getUnintNo() {
            return UnintNo;
        }

        public void setUnintNo(String UnintNo) {
            this.UnintNo = UnintNo;
        }

        public String getUnitName() {
            return UnitName;
        }

        public void setUnitName(String UnitName) {
            this.UnitName = UnitName;
        }

        public String getBridgeName() {
            return BridgeName;
        }

        public void setBridgeName(String BridgeName) {
            this.BridgeName = BridgeName;
        }

        public String getLon() {
            return Lon;
        }

        public void setLon(String Lon) {
            this.Lon = Lon;
        }

        public String getLat() {
            return Lat;
        }

        public void setLat(String Lat) {
            this.Lat = Lat;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String Location) {
            this.Location = Location;
        }

        public String getCOMPLETION() {
            return COMPLETION;
        }

        public void setCOMPLETION(String COMPLETION) {
            this.COMPLETION = COMPLETION;
        }

        public String getCOMPLETIONNAME() {
            return COMPLETIONNAME;
        }

        public void setCOMPLETIONNAME(String COMPLETIONNAME) {
            this.COMPLETIONNAME = COMPLETIONNAME;
        }

        public String getFiles() {
            return files;
        }

        public void setFiles(String files) {
            this.files = files;
        }
    }
}

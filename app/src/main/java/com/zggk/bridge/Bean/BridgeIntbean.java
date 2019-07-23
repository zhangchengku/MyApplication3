package com.zggk.bridge.Bean;

import java.util.List;

/**
 * Created by 张成昆 on 2019-4-3.
 */

public class BridgeIntbean {


    /**
     * DATA : [{"CompletionState":"0","CompletionValue":"未开工","Lon":"116.173278","Lat":"40.059234","LocationInfo":"动态市.动态县.莲花桥"}]
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
         * CompletionState : 0
         * CompletionValue : 未开工
         * Lon : 116.173278
         * Lat : 40.059234
         * LocationInfo : 动态市.动态县.莲花桥
         */

        private String CompletionState;
        private String CompletionValue;
        private String Lon;
        private String Lat;
        private String LocationInfo;
        private String BridgeName;

        public String getBridgeName() {
            return BridgeName;
        }

        public void setBridgeName(String bridgeName) {
            BridgeName = bridgeName;
        }

        public String getCompletionState() {
            return CompletionState;
        }

        public void setCompletionState(String CompletionState) {
            this.CompletionState = CompletionState;
        }

        public String getCompletionValue() {
            return CompletionValue;
        }

        public void setCompletionValue(String CompletionValue) {
            this.CompletionValue = CompletionValue;
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

        public String getLocationInfo() {
            return LocationInfo;
        }

        public void setLocationInfo(String LocationInfo) {
            this.LocationInfo = LocationInfo;
        }
    }
}

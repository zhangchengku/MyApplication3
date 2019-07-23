package com.zggk.bridge.Bean;

import java.util.List;

/**
 * Created by 张成昆 on 2019-4-4.
 */

public class AddBean {

    /**
     * PRODATA : [{"Zdname":"4","ZdValue":"施工图设计备案"},{"Zdname":"5","ZdValue":"开工报告备案"}]
     * STATE : 1
     * QLDATA : [{"GUID":"01c352fa2bd4423c94eb01b7ad713f2e","BridgeCode":"C420441803L0010","BridgeName":"莲花桥","CjLocation":"C420 格水-莲花 K0+092","UnitName":"清远市清新区地方公路站","CurrentCompletion":"0"}]
     * COMDATA : [{"Zdname":"0","ZdValue":"未开工"},{"Zdname":"1","ZdValue":"已开工"},{"Zdname":"2","ZdValue":"已完工"}]
     */
    private String REPORTDATE;

    public String getREPORTDATE() {
        return REPORTDATE;
    }

    public void setREPORTDATE(String REPORTDATE) {
        this.REPORTDATE = REPORTDATE;
    }

    private String STATE;
    private List<PRODATABean> PRODATA;
    private List<QLDATABean> QLDATA;
    private List<COMDATABean> COMDATA;

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public List<PRODATABean> getPRODATA() {
        return PRODATA;
    }

    public void setPRODATA(List<PRODATABean> PRODATA) {
        this.PRODATA = PRODATA;
    }

    public List<QLDATABean> getQLDATA() {
        return QLDATA;
    }

    public void setQLDATA(List<QLDATABean> QLDATA) {
        this.QLDATA = QLDATA;
    }

    public List<COMDATABean> getCOMDATA() {
        return COMDATA;
    }

    public void setCOMDATA(List<COMDATABean> COMDATA) {
        this.COMDATA = COMDATA;
    }

    public static class PRODATABean {
        /**
         * Zdname : 4
         * ZdValue : 施工图设计备案
         */

        private String Zdname;
        private String ZdValue;

        public String getZdname() {
            return Zdname;
        }

        public void setZdname(String Zdname) {
            this.Zdname = Zdname;
        }

        public String getZdValue() {
            return ZdValue;
        }

        public void setZdValue(String ZdValue) {
            this.ZdValue = ZdValue;
        }
    }

    public static class QLDATABean {

        /**
         * GUID : 01c352fa2bd4423c94eb01b7ad713f2e
         * BridgeCode : C420441803L0010
         * BridgeName : 莲花桥
         * CjLocation : C420 格水-莲花 K0+092
         * UnitName : 清远市清新区地方公路站
         * BridgeCode1 : C420441803L0010
         * EngineerState : 0
         * PriorWork : 0
         * CompleteInvestment : 120
         * KaiGongTime : 2019-04-10T00:00:00
         * WanGongTime : 2019-04-10T00:00:00
         * Remark :
         */

        private String GUID;
        private String BridgeCode;
        private String BridgeName;
        private String CjLocation;
        private String UnitName;
        private String BridgeCode1;
        private String EngineerState;
        private String PriorWork;
        private String CompleteInvestment;
        private String KaiGongTime;
        private String WanGongTime;
        private String Remark;
        private String img;
        private String SpStates;

        public String getSpStates() {
            return SpStates;
        }

        public void setSpStates(String spStates) {
            SpStates = spStates;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getGUID() {
            return GUID;
        }

        public void setGUID(String GUID) {
            this.GUID = GUID;
        }

        public String getBridgeCode() {
            return BridgeCode;
        }

        public void setBridgeCode(String BridgeCode) {
            this.BridgeCode = BridgeCode;
        }

        public String getBridgeName() {
            return BridgeName;
        }

        public void setBridgeName(String BridgeName) {
            this.BridgeName = BridgeName;
        }

        public String getCjLocation() {
            return CjLocation;
        }

        public void setCjLocation(String CjLocation) {
            this.CjLocation = CjLocation;
        }

        public String getUnitName() {
            return UnitName;
        }

        public void setUnitName(String UnitName) {
            this.UnitName = UnitName;
        }

        public String getBridgeCode1() {
            return BridgeCode1;
        }

        public void setBridgeCode1(String BridgeCode1) {
            this.BridgeCode1 = BridgeCode1;
        }

        public String getEngineerState() {
            return EngineerState;
        }

        public void setEngineerState(String EngineerState) {
            this.EngineerState = EngineerState;
        }

        public String getPriorWork() {
            return PriorWork;
        }

        public void setPriorWork(String PriorWork) {
            this.PriorWork = PriorWork;
        }

        public String getCompleteInvestment() {
            return CompleteInvestment;
        }

        public void setCompleteInvestment(String CompleteInvestment) {
            this.CompleteInvestment = CompleteInvestment;
        }

        public String getKaiGongTime() {
            return KaiGongTime;
        }

        public void setKaiGongTime(String KaiGongTime) {
            this.KaiGongTime = KaiGongTime;
        }

        public String getWanGongTime() {
            return WanGongTime;
        }

        public void setWanGongTime(String WanGongTime) {
            this.WanGongTime = WanGongTime;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }
    }

    public static class COMDATABean {
        /**
         * Zdname : 0
         * ZdValue : 未开工
         */

        private String Zdname;
        private String ZdValue;

        public String getZdname() {
            return Zdname;
        }

        public void setZdname(String Zdname) {
            this.Zdname = Zdname;
        }

        public String getZdValue() {
            return ZdValue;
        }

        public void setZdValue(String ZdValue) {
            this.ZdValue = ZdValue;
        }
    }
}

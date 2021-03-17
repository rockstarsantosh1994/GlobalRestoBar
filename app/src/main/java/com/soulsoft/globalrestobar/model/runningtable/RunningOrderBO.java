package com.soulsoft.globalrestobar.model.runningtable;

public class RunningOrderBO {
    private String TID;
    private String TABLE;
    private String AMOUNT;
    private String EMPLOYEE;
    private String EMPID;

    public String getTID( ) {
        return TID;
    }

    public void setTID(String TID) {
        this.TID = TID;
    }

    public String getTABLE( ) {
        return TABLE;
    }

    public void setTABLE(String TABLE) {
        this.TABLE = TABLE;
    }

    public String getAMOUNT( ) {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getEMPLOYEE( ) {
        return EMPLOYEE;
    }

    public void setEMPLOYEE(String EMPLOYEE) {
        this.EMPLOYEE = EMPLOYEE;
    }

    public String getEMPID( ) {
        return EMPID;
    }

    public void setEMPID(String EMPID) {
        this.EMPID = EMPID;
    }
}

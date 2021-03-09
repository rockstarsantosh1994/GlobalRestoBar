package com.soulsoft.globalrestobar.model;

public class TakeMenuOrder {

    public String GoodsCode;
    public String GoodsName;
    public String ItemType;
    public String UnitCode;
    public String Rate;
    public String SplRequest;
    public String Quantity;
    public String Total;
    public String Unitname;

    public TakeMenuOrder(){

    }

    public TakeMenuOrder(String goodsCode, String goodsName, String itemType, String unitCode, String rate, String splRequest, String quantity, String total, String unitname) {
        GoodsCode = goodsCode;
        GoodsName = goodsName;
        ItemType = itemType;
        UnitCode = unitCode;
        Rate = rate;
        SplRequest = splRequest;
        Quantity = quantity;
        Total = total;
        Unitname = unitname;
    }

    public String getUnitname() {
        return Unitname;
    }

    public void setUnitname(String unitname) {
        Unitname = unitname;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }

    public String getGoodsCode() {
        return GoodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        GoodsCode = goodsCode;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getItemType() {
        return ItemType;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
    }

    public String getUnitCode() {
        return UnitCode;
    }

    public void setUnitCode(String unitCode) {
        UnitCode = unitCode;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getSplRequest() {
        return SplRequest;
    }

    public void setSplRequest(String splRequest) {
        SplRequest = splRequest;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }
}

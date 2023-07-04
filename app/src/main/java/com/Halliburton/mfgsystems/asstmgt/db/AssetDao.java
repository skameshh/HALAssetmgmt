package com.Halliburton.mfgsystems.asstmgt.db;

import java.io.Serializable;

public class AssetDao implements Serializable {
    private Integer id;
    private String plant;
    private String cocd;
    private String assetNumber;
    private String mainAssetNumber;
    private String assetSubNumber;
    private String assetCls;
    private String posidWbsElement;
    private String costCenterCode;
    private String costCenterName;
    private String assetDesc;
    private String assetPostuShortDesc;
    private String serialNumber;
    private String inventoryNumber;
    private String assetCapDate;
    private String deactivationDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getCocd() {
        return cocd;
    }

    public void setCocd(String cocd) {
        this.cocd = cocd;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getMainAssetNumber() {
        return mainAssetNumber;
    }

    public void setMainAssetNumber(String mainAssetNumber) {
        this.mainAssetNumber = mainAssetNumber;
    }

    public String getAssetSubNumber() {
        return assetSubNumber;
    }

    public void setAssetSubNumber(String assetSubNumber) {
        this.assetSubNumber = assetSubNumber;
    }

    public String getAssetCls() {
        return assetCls;
    }

    public void setAssetCls(String assetCls) {
        this.assetCls = assetCls;
    }

    public String getCostCenterCode() {
        return costCenterCode;
    }

    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
    }

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }

    public String getAssetDesc() {
        return assetDesc;
    }

    public void setAssetDesc(String assetDesc) {
        this.assetDesc = assetDesc;
    }

    public String getAssetPostuShortDesc() {
        return assetPostuShortDesc;
    }

    public void setAssetPostuShortDesc(String assetPostuShortDesc) {
        this.assetPostuShortDesc = assetPostuShortDesc;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public String getAssetCapDate() {
        return assetCapDate;
    }

    public void setAssetCapDate(String assetCapDate) {
        this.assetCapDate = assetCapDate;
    }

    public String getDeactivationDate() {
        return deactivationDate;
    }

    public void setDeactivationDate(String deactivationDate) {
        this.deactivationDate = deactivationDate;
    }

    public String getPosidWbsElement() {
        return posidWbsElement;
    }

    public void setPosidWbsElement(String posidWbsElement) {
        this.posidWbsElement = posidWbsElement;
    }

    @Override
    public String toString() {
        return "AssetDao{" +
                "id=" + id +
                ", plant='" + plant + '\'' +
                ", cocd='" + cocd + '\'' +
                ", assetNumber='" + assetNumber + '\'' +
                ", mainAssetNumber='" + mainAssetNumber + '\'' +
                ", assetSubNumber='" + assetSubNumber + '\'' +
                ", assetCls='" + assetCls + '\'' +
                ", posidWbsElement='" + posidWbsElement + '\'' +
                ", costCenterCode='" + costCenterCode + '\'' +
                ", costCenterName='" + costCenterName + '\'' +
                ", assetDesc='" + assetDesc + '\'' +
                ", assetPostuShortDesc='" + assetPostuShortDesc + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", inventoryNumber='" + inventoryNumber + '\'' +
                ", assetCapDate='" + assetCapDate + '\'' +
                ", deactivationDate='" + deactivationDate + '\'' +
                '}';
    }
}

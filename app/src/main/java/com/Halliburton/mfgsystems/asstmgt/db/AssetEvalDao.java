package com.Halliburton.mfgsystems.asstmgt.db;

import java.io.Serializable;

public class AssetEvalDao implements Serializable {
    private Integer id;
    private String assetEvalBy;
    private String assetEvalDate;
    private String assetNbr;
    private String plant;
    private String costCntr;
    private String serialNbr;
    private int assetStatus;
    private String assetNotFoundComments;
    private String assetSerialNbrVisible;
    private String assetDescVisible;
    private String assetCostCntrVisible;
    private String assetInUseComments;
    private String assetWIthNoIssuesComments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssetEvalBy() {
        return assetEvalBy;
    }

    public void setAssetEvalBy(String assetEvalBy) {
        this.assetEvalBy = assetEvalBy;
    }

    public String getAssetEvalDate() {
        return assetEvalDate;
    }

    public void setAssetEvalDate(String assetEvalDate) {
        this.assetEvalDate = assetEvalDate;
    }

    public String getAssetNbr() {
        return assetNbr;
    }

    public void setAssetNbr(String assetNbr) {
        this.assetNbr = assetNbr;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getCostCntr() {
        return costCntr;
    }

    public void setCostCntr(String costCntr) {
        this.costCntr = costCntr;
    }

    public String getSerialNbr() {
        return serialNbr;
    }

    public void setSerialNbr(String serialNbr) {
        this.serialNbr = serialNbr;
    }

    public int getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(int assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getAssetNotFoundComments() {
        return assetNotFoundComments;
    }

    public void setAssetNotFoundComments(String assetNotFoundComments) {
        this.assetNotFoundComments = assetNotFoundComments;
    }

    public String getAssetSerialNbrVisible() {
        return assetSerialNbrVisible;
    }

    public void setAssetSerialNbrVisible(String assetSerialNbrVisible) {
        this.assetSerialNbrVisible = assetSerialNbrVisible;
    }

    public String getAssetDescVisible() {
        return assetDescVisible;
    }

    public void setAssetDescVisible(String assetDescVisible) {
        this.assetDescVisible = assetDescVisible;
    }

    public String getAssetCostCntrVisible() {
        return assetCostCntrVisible;
    }

    public void setAssetCostCntrVisible(String assetCostCntrVisible) {
        this.assetCostCntrVisible = assetCostCntrVisible;
    }

    public String getAssetInUseComments() {
        return assetInUseComments;
    }

    public void setAssetInUseComments(String assetInUseComments) {
        this.assetInUseComments = assetInUseComments;
    }

    public String getAssetWIthNoIssuesComments() {
        return assetWIthNoIssuesComments;
    }

    public void setAssetWIthNoIssuesComments(String assetWIthNoIssuesComments) {
        this.assetWIthNoIssuesComments = assetWIthNoIssuesComments;
    }


    @Override
    public String toString() {
        return "AssetEvalDao{" +
                "id=" + id +
                ", assetEvalBy='" + assetEvalBy + '\'' +
                ", assetEvalDate='" + assetEvalDate + '\'' +
                ", assetNbr='" + assetNbr + '\'' +
                ", plant='" + plant + '\'' +
                ", costCntr='" + costCntr + '\'' +
                ", serialNbr='" + serialNbr + '\'' +
                ", assetStatus=" + assetStatus +
                ", assetNotFoundComments='" + assetNotFoundComments + '\'' +
                ", assetSerialNbrVisible='" + assetSerialNbrVisible + '\'' +
                ", assetDescVisible='" + assetDescVisible + '\'' +
                ", assetCostCntrVisible='" + assetCostCntrVisible + '\'' +
                ", assetInUseComments='" + assetInUseComments + '\'' +
                ", assetWIthNoIssuesComments='" + assetWIthNoIssuesComments + '\'' +
                '}';
    }
}

package com.Halliburton.mfgsystems.asstmgt.db;

public class ProdOrderDao {
    private String prodOrdNo;
    private String materialNo;
    private String oldMaterialNo;
    private String batchNo;



    public String getProdOrdNo() {
        return prodOrdNo;
    }
    public void setProdOrdNo(String prodOrdNo) {
        this.prodOrdNo = prodOrdNo;
    }
    public String getMaterialNo() {
        return materialNo;
    }
    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }
    public String getOldMaterialNo() {
        return oldMaterialNo;
    }
    public void setOldMaterialNo(String oldMaterialNo) {
        this.oldMaterialNo = oldMaterialNo;
    }
    public String getBatchNo() {
        return batchNo;
    }
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }


}

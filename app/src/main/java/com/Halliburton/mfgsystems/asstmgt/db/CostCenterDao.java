package com.Halliburton.mfgsystems.asstmgt.db;

import java.io.Serializable;

public class CostCenterDao implements Serializable {
    private String costCenterName;
    private String costCenterCode;
    private String plant;

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }

    public String getCostCenterCode() {
        return costCenterCode;
    }

    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }
}

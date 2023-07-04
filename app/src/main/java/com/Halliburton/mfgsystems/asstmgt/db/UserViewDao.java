package com.Halliburton.mfgsystems.asstmgt.db;

public class UserViewDao {
    private Integer id ;
    private String fullName;
    private String hid;
    private String department;
    private String costCenter;
    private String plant;
    private String roleName;
    private String empId;
    private String password;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "UserViewDao{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", hid='" + hid + '\'' +
                ", department='" + department + '\'' +
                ", costCenter='" + costCenter + '\'' +
                ", plant='" + plant + '\'' +
                ", roleName='" + roleName + '\'' +
                ", empId='" + empId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

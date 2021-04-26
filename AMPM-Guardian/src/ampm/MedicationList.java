/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

/**
 *
 * @author johnz
 */
public class MedicationList {

    //The private values for this object, each one represents a col in the DB
    private String genericName;
    private String brandName;
    private String genericClass;
    private String uses;
    private String alerts;

    MedicationList(String genericClass, String genericName, String brandName) {
        this.genericClass = genericClass;
        this.genericName = genericName;
        this.brandName = brandName;
    }

    MedicationList() {

    }

    public String getMedicationClass() {
        String sqlSelect = "SELECT distinct Class FROM AMPM.Generic_Class";
        return sqlSelect;
    }

    public String getGenericaNameByClass(String geneClass) {
        String sqlSelect = "SELECT distinct Generic_Name FROM AMPM.Generic_Class "
                + "WHERE Class ='" + geneClass + "'";
        return sqlSelect;
    }

    public String getBrandNameByGenericaName(String geneName) {
        String sqlSelect = "SELECT distinct Brand_Name FROM AMPM.Brand_Name "
                + "WHERE Generic_Name ='" + geneName + "'";
        return sqlSelect;
    }

    public String getUsesByGenericaName(String geneName) {
        String sqlSelect = "SELECT Uses FROM AMPM.Generic_Uses "
                + "WHERE Generic_Name ='" + geneName + "'";
        return sqlSelect;
    }

    public String getAlertsByGenericaName(String geneName) {
        String sqlSelect = "SELECT distinct Alerts FROM AMPM.Generic_Uses "
                + "WHERE Generic_Name ='" + geneName + "'";
        return sqlSelect;
    }

    public String insertBrandName() {
        String sqlInsert = "INSERT INTO AMPM.Brand_Name (Generic_Name, Brand_Name) Values ('"
                + this.genericName + "', '"
                + this.brandName + "')";
        return sqlInsert;
    }

    public String insertClass() {
        String sqlInsert = "INSERT INTO AMPM.Generic_Class (Generic_Name, Class) Values ('"
                + this.genericName + "', '"
                + this.genericClass + "')";
        return sqlInsert;
    }

    public String insertUses(String uses, String alerts) {
        String sqlInsert = "INSERT INTO AMPM.Generic_Uses (Generic_Name, Uses, Alerts) Values ('"
                + this.genericName + "', '"
                + this.uses + "', '"
                + this.alerts + "')";
        return sqlInsert;
    }
}

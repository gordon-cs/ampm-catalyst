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

    MedicationList(String genericClass, String genericName, String brandName) {
        this.genericClass = genericClass;
        this.genericName = genericName;
        this.brandName = brandName;
    }

    MedicationList() {

    }

    public String getMedicationClassByGeneName(String geneName) {
        String sqlSelect = "SELECT distinct Class FROM AMPM.Medication_List "
                + "WHERE GenericName ='" + geneName + "'";
        return sqlSelect;
    }

    public String getMedicationClassByBrandName(String brandName) {
        String sqlSelect = "SELECT distinct Class FROM AMPM.Medication_List "
                + "WHERE BrandName ='" + brandName + "'";
        return sqlSelect;
    }

    public String getGenericaName() {
        String sqlSelect = "SELECT distinct GenericName FROM AMPM.Medication_List";
        return sqlSelect;
    }

    public String getBrandName() {
        String sqlSelect = "SELECT distinct BrandName FROM AMPM.Medication_List ";
        return sqlSelect;
    }

    public String getBrandNameByGenericName(String geneName) {
        String sqlSelect = "SELECT distinct BrandName FROM AMPM.Medication_List "
                + "WHERE GenericName ='" + geneName + "'";
        return sqlSelect;
    }

    public String getGenericNameByBrandName(String brandName) {
        String sqlSelect = "SELECT distinct GenericName FROM AMPM.Medication_List "
                + "WHERE BrandName ='" + brandName + "'";
        return sqlSelect;
    }

    public String insertMedication() {
        String sqlInsert = "INSERT INTO AMPM.Medication_List (Class, GenericName, BrandName, UsedFor) Values ('"
                + this.genericName + "', '"
                + this.brandName + "')";
        return sqlInsert;
    }
}

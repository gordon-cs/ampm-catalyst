package ampm;

import java.util.Date;

/**
 *
 * @author john.zhu
 */
public class Medication {

    private String clientID;
    private String medicationClass;
    private String genericName;
    private String brandName;
    private String dose;
    private String frequency;
    private String startDate;
    private String prescribedBy;
    private String usedFor;
    private String dateStopped;
    private String provider;

    Medication(String clientID, String medicationClass, String genericName, String brandName, String dose, String frequency,
            String startDate, String prescribedBy, String usedFor, String dateStopped, String provider) {
        this.clientID = clientID;
        this.medicationClass = medicationClass;
        this.genericName = genericName;
        this.brandName = brandName;
        this.dose = dose;
        this.frequency = frequency;
        this.startDate = startDate;
        this.prescribedBy = prescribedBy;
        this.usedFor = usedFor;
        this.dateStopped = dateStopped;
        this.provider = provider;
    }

    Medication(String clientID) {
        this.clientID = clientID;
    }

    // Generates the sql string that can be used to insert this client info into
    // the medication table
    public String getSQLInsert() {
        String sqlInsert;

        sqlInsert = "INSERT INTO AMPM.Medication (ClientID, Class, GenericName, BrandName, Dose, "
                + "Frequency,StartDate, PrescribedBy,UsedFor, DataStopped, Provider) Values ('"
                + this.clientID + "','"
                + this.medicationClass + "','"
                + this.genericName + "', '"
                + this.brandName + "', '"
                + this.dose + "',' "
                + this.frequency + "', '"
                + this.startDate + "', "
                + this.prescribedBy + "', '"
                + this.usedFor + "', '"
                + this.dateStopped + "', '"
                + this.provider + "')";
        return sqlInsert;
    }

    // Generates the sql string that can be used to update this provide info
    // into the Provide table
    public String getSQLUpdate() {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.Medication SET Class='" + this.medicationClass + "',"
                + "GenericName='" + this.genericName + "', "
                + "BrandName='" + this.brandName + "', "
                + "Dose='" + this.dose + "', "
                + "Frequency='" + this.frequency + "', "
                + "StartDate='" + this.startDate + "', "
                + "PrescribedBy='" + this.prescribedBy + "', "
                + "UsedFor='" + this.usedFor + "', "
                + "DateStopped='" + this.dateStopped + "', "
                + "Provider='" + this.provider + "'"
                + "WHERE ClientID='" + this.clientID + "'";

        return sqlUpdate;
    }

    public String getSQLUpdateNewItem(String newItem) {
        String sqlUpdate;

        sqlUpdate = "UPDATE AMPM.Medication SET Class='" + this.medicationClass + "',"
                + "GenericName='" + newItem + "', "
                + "BrandName='" + this.brandName + "', "
                + "Dose='" + this.dose + "', "
                + "Frequency='" + this.frequency + "', "
                + "StartDate='" + this.startDate + "', "
                + "PrescribedBy='" + this.prescribedBy + "', "
                + "UsedFor='" + this.usedFor + "', "
                + "DateStopped='" + this.dateStopped + "', "
                + "Provider='" + this.provider + "'"
                + "WHERE ClientID='" + this.clientID + "'"
                + "AND GenericName='" + this.genericName + "'";

        return sqlUpdate;
    }

    public String getSQLSelect() {
        String sqlSelect = "SELECT * FROM AMPM.Medication WHERE ClientID='"
                + this.clientID + "'";

        return sqlSelect;
    }

    public String getSQLSelectByGenericName(String name) {
        String sqlSelect = "SELECT * FROM AMPM.Medication WHERE ClientID ='" + this.clientID 
                + "'AND GenericName = '"
                + name + "'";
        
        return sqlSelect;
    }
}

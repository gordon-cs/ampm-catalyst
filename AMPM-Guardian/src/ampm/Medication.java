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
    private Date startDate;
    private String prescribedBy;
    private String usedFor;
    private Date dateStopped;
    private String provider;

    Medication(String clientID, String medicationClass, String genericName, String brandName, String dose, String frequency,
            Date startDate, String prescribedBy, String usedFor, Date dateStopped, String provider) {
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
                + "DateStopped='" + this.prescribedBy + "', "
                + "Provider='" + this.dateStopped + "'"
                + "WHERE ClientID='" + this.clientID + "'";

        return sqlUpdate;
    }

    public String getSQLSelect() {
        String sqlSelect = "SELECT * FROM AMPM.Medication WHERE ClientID='"
                + this.clientID + "'";

        System.out.println(sqlSelect);
        return sqlSelect;
    }
}

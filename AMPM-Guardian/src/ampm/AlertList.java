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
public class AlertList {

    //Insert new alert type to AlertList database table
    public String insertNewType(String type) {
        String sqlSelect = "INSERT INTO AMPM.AlertList (AlertType) VALUES ('"
                + type + "')";
        return sqlSelect;
    }

    //Check if this type exist in database table
    public String checkType(String type) {
        String sqlSelect = "SELECT * FROM AMPM.AlertList WHERE AlertType = '" + type + "'";
        return sqlSelect;
    }

    //Get all the type from AlertList database table
    public String getProviderType() {
        String sqlSelect = "SELECT * FROM AMPM.AlertList";
        return sqlSelect;
    }
}

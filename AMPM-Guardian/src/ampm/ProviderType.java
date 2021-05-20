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
public class ProviderType {

    //Insert new type to Provider_Type database table
    public String insertNewType(String type) {
        String sqlSelect = "INSERT INTO AMPM.Provider_Type (Type) VALUES ('"
                + type + "')";
        return sqlSelect;
    }

    //Check if this type exist in database table
    public String checkType(String type) {
        String sqlSelect = "SELECT * FROM AMPM.Provider_Type WHERE Type = '" + type + "'";
        return sqlSelect;
    }

    //Get all the type from ProviderType database table
    public String getProviderType() {
        String sqlSelect = "SELECT * FROM AMPM.Provider_Type";
        return sqlSelect;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author johnz
 */
public class ProviderBox {

    private final StringProperty provider;
    private final StringProperty type;

    public ProviderBox(String type, String provider) {
        this.type = new SimpleStringProperty(type);
        this.provider = new SimpleStringProperty(provider);
    }

    //getters
    public String getType() {
        return type.get();
    }

    public String getProvider() {
        return provider.get();
    }

    //setters
    public void setType(String value) {
        type.set(value);
    }

    public void setProvider(String value) {
        provider.set(value);
    }

    //property values
    public StringProperty typeProperty() {
        return type;
    }

    public StringProperty providerProperty() {
        return provider;
    }

}

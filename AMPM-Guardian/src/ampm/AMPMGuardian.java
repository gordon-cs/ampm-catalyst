/* The main class, loads the login screen at start up. 
 *
 */
package ampm;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author John and Ben 
 */
public class AMPMGuardian extends Application {
    public static Scene loginScene;
    private static List<Client> clients;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        loginScene = scene;
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static List<Client> getClients() {
        return clients;
    }
    
    public static void addClientToList(Client client) {
        clients.add(client);
    }
    
    public static void removeClientFromList(Client client) {
        clients.remove(client);
    }
}

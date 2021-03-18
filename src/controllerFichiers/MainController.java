/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerFichiers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author zenume
 */
public class MainController implements Initializable {

    @FXML
    private Button home;
    @FXML
    private Button produit;
    @FXML
    private Button client;
    @FXML
    private Button vente;
    @FXML
    private Button paiement;
    
    private Label label;
    @FXML
    private BorderPane mainPane;
    @FXML
    private AnchorPane ap;
    @FXML
    private Button login;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void home(MouseEvent event) {
        mainPane.setCenter(ap);
        
    }

    @FXML
    private void produit(MouseEvent event) {
        loadPage("produit");
    }

    @FXML
    private void client(MouseEvent event) {
        loadPage("client");
    }

    @FXML
    private void vente(MouseEvent event) {
        loadPage("vente");
    }

    @FXML
    private void paiement(MouseEvent event) {
        loadPage("paiement");
    }
    
    @FXML
    private void login(MouseEvent event) throws IOException { 
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxmlFichiers/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Se connecter");
        stage.setScene(scene);
        stage.show();
        closeMain();
        
    }
     public void closeMain() {
        Stage stage=null;
     stage = (Stage) login.getScene().getWindow();
     stage.close();
}
private void loadPage(String page){
Parent root=null;
        try {
            root=FXMLLoader.load(getClass().getResource("/fxmlFichiers/"+page+".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainPane.setCenter(root);
}


   
}

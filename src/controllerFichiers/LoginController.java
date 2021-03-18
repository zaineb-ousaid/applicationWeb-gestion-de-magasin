/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerFichiers;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author zenume
 */
public class LoginController implements Initializable {

    @FXML
    private Button btnconnecter;
    @FXML
    private TextField tfutilisateur;
    @FXML
    private TextField tfmdp;
    @FXML
    private Label label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
   
    @FXML
    private void handleButtonAction(ActionEvent event) {
         Connection conn=getConnection();
        String query="SELECT * FROM users WHERE NOM='"+tfutilisateur.getText()+"' AND MDP='"+tfmdp.getText()+"' ";
     Statement st;
     ResultSet rs;
     try{
     st= conn.createStatement();
     rs=st.executeQuery(query);
   
     if(rs.next()){
         
      FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxmlFichiers/Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100,500);
        Stage stage = new Stage();
        stage.setTitle("Gestion de magasin");
        stage.setScene(scene);
        stage.show();
        closeLogin();
        
     }else{
     label.setText("Accès est refusé!");
     }
     }
     catch(Exception ex){
     ex.printStackTrace();
     }
    }
    public void closeLogin() {
        Stage stage=null;
     stage = (Stage) btnconnecter.getScene().getWindow();
     stage.close();
}
     
    
     public Connection getConnection(){
        Connection conn;
        try{
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionMagazin","root","");
            return conn;
            
        }catch(Exception ex){
            System.out.println("Erreur"+ex.getMessage());
            return null;
}
    
}
}
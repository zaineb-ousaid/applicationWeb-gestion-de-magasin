/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerFichiers;

import gestion_magazin.Paiement;
import gestion_magazin.Vente;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author zenume
 */
public class ReglementController implements Initializable {

    @FXML
    private TableView<Paiement> tvreglement;
    @FXML
    private TableColumn<Paiement, Integer> coln;
    @FXML
    private TableColumn<Paiement, Double> collmontant;
    @FXML
    private TableColumn<Paiement, Date> coldate;
    @FXML
    private TableColumn<Paiement, String> coltype;
    @FXML
    private TableColumn<Paiement, String> colncheque;
    @FXML
    private TableColumn<Paiement, String> colbanque;
    @FXML
    private TextField tfn;
    @FXML
    private DatePicker tfdate;
    @FXML
    private TextField tfmontant;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Label labelclient;
    @FXML
    private Label labeln;
    @FXML
    private Label labeldate;
    @FXML
    private Label labeltotal;
    @FXML
    private Button btnajouter;
    @FXML
    private Button btnsupprimer;
    @FXML
    private Button btnmodifier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       tfdate.setValue(LocalDate.now());
       comboBox.getItems().addAll("ESPECE","CHEQUE");
        showPaiements();
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if(event.getSource()==btnajouter){
              insertPaiement();
    }else if(event.getSource()==btnsupprimer){
                 deletePaiements();
    }else if(event.getSource()==btnmodifier){
                 updatePaiements();
    }
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
    
    public ObservableList<Paiement> getPaiements(){
     ObservableList<Paiement> listePaiements=FXCollections.observableArrayList();
     Connection conn=getConnection();
     String query="SELECT * FROM paiement,vente WHERE paiement.ID_VENTE=vente.ID_VENTE AND paiement.ID_VENTE="+labeln.getText()+"";
     Statement st;
     ResultSet rs;
     try{
     st= conn.createStatement();
     rs=st.executeQuery(query);
     Paiement p;
     
     while(rs.next()){
     
      p=new Paiement(rs.getInt("ID_PAIEMENT"),rs.getDouble("MONTANT"),rs.getDate("date"),rs.getString("TYPE"));
      labelclient.setText(rs.getString("vente.CLIENT"));
      labeldate.setText(""+rs.getDate("vente.DATE"));
      labeltotal.setText(""+rs.getDouble("vente.TOTAL"));
     listePaiements.add(p);
          
     }
     }
     catch(Exception ex){
     ex.printStackTrace();
     }
     return listePaiements;
     }
   
     public void showPaiements(){
         ObservableList<Paiement> list=getPaiements();
         
        coln.setCellValueFactory(new PropertyValueFactory<Paiement,Integer>("id"));
        collmontant.setCellValueFactory(new PropertyValueFactory<Paiement,Double>("montant"));
    
         coltype.setCellValueFactory(new PropertyValueFactory<Paiement,String>("type"));
         coldate.setCellValueFactory(new PropertyValueFactory<Paiement,Date>("date")); 
         colncheque.setCellValueFactory(new PropertyValueFactory<Paiement,String>("cheque")); 
          colbanque.setCellValueFactory(new PropertyValueFactory<Paiement,String>("banque")); 
     
         tvreglement.setItems(list);
  
     }
     private void insertPaiement(){
     
         String query="INSERT INTO paiement VALUES(NULL,"+tfmontant.getText()+", '"+tfdate.getValue()+"' , '"+comboBox.getValue()+"' , '--' , '--', "+labeln.getText()+" )";
         
    
         executeQuery(query);
         showPaiements();
         
     }
      private void updatePaiements(){
    
     String query="UPDATE paiement SET MONTANT = '"+tfmontant.getText()+"', date= '"+tfdate.getValue()+"' ,TYPE= '"+comboBox.getValue()+"' WHERE ID_PAIEMENT="+tfn.getText()+"";
    executeQuery(query);
    showPaiements();
    
     }
    private void deletePaiements(){
    String query="DELETE FROM paiement WHERE ID_PAIEMENT="+tfn.getText()+"";
        executeQuery(query);
        showPaiements();
    }
    private void executeQuery(String query) {
        Connection conn=getConnection();
        
        Statement st;
        try{
        st=conn.createStatement();
        st.executeUpdate(query);
        }catch(Exception ex){
        ex.printStackTrace();
        }
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        Paiement p= tvreglement.getSelectionModel().getSelectedItem();
       tfn.setText(""+p.getId());
       tfmontant.setText(""+p.getMontant());
       comboBox.setValue(p.getType());
    }
    
}

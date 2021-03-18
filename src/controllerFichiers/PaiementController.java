/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerFichiers;

import gestion_magazin.AutoCompleteComboBox;
import gestion_magazin.Clients;
import gestion_magazin.Paiement;
import gestion_magazin.Vente;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author zenume
 */
public class PaiementController implements Initializable {

    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Button btnconfirmer;
    @FXML
    private TableView tvpaiement;
    @FXML
    private TableColumn<Vente, Integer> coln;
    @FXML
    private TableColumn<Vente, String> colclient;
    @FXML
    private TableColumn<Vente, Date> coldate;
    @FXML
    private TableColumn<Vente, Double> coltotal;
    @FXML
    private Label labeltotal;
    @FXML
    private Label labelpay;
    @FXML
    private Label labelreste;
    @FXML
    private TableColumn<Vente, String> colmontant;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
           fillComboBox();
     new AutoCompleteComboBox<>(comboBox);
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) {
         if(event.getSource()==btnconfirmer){
    
        showVentes();
        calculerPaye();
        calculerReste();
       
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
     public void fillComboBox(){
     ObservableList<Clients> listeClients=FXCollections.observableArrayList();
     Connection conn=getConnection();
     String query="SELECT * FROM client";
     Statement st;
     ResultSet rs;
     try{
     st= conn.createStatement();
     rs=st.executeQuery(query);
  
     while(rs.next()){
           comboBox.getItems().addAll(rs.getString("PRENOM")+" "+rs.getString("NOM"));
          
    
     }
     }
     catch(Exception ex){
     ex.printStackTrace();
     }
 }
     
        
        public ObservableList<Vente> getVentes(){
     ObservableList listePaiements=FXCollections.observableArrayList();
     Connection conn=getConnection();
     String query="SELECT * FROM vente,paiement WHERE vente.ID_VENTE=paiement.ID_VENTE AND vente.CLIENT='"+comboBox.getValue()+"' ";
     Statement st;
     ResultSet rs;
     try{
     st= conn.createStatement();
     rs=st.executeQuery(query);
     Paiement paiement;
     Vente vente;
     while(rs.next()){
          paiement=new Paiement(rs.getInt("ID_PAIEMENT"),rs.getDouble("MONTANT"),rs.getDate("date"),rs.getString("TYPE"));
      vente=new Vente(rs.getInt("ID_VENTE"),rs.getDate("DATE"),rs.getString("CLIENT"),  paiement,rs.getDouble("TOTAL"));  
      labeltotal.setText(""+rs.getDouble("TOTAL"));
    
     listePaiements.add(vente);
     }
     }
     catch(Exception ex){
     ex.printStackTrace();
     }
     return listePaiements;
     }
  
     public void showVentes(){
         ObservableList<Vente> list=getVentes();
         coln.setCellValueFactory(new PropertyValueFactory<Vente,Integer>("numero"));
         colclient.setCellValueFactory(new PropertyValueFactory<Vente,String>("client"));
         coldate.setCellValueFactory(new PropertyValueFactory<Vente,Date>("date")); 
         coltotal.setCellValueFactory(new PropertyValueFactory<Vente,Double>("total"));
           colmontant.setCellValueFactory(cellData ->
    new ReadOnlyStringWrapper(Double.toString(cellData.getValue().getP().getMontant())));
         //colreste.setCellValueFactory(new PropertyValueFactory<Vente,Double>("reste"));
       
      tvpaiement.setItems(list);
    
     }
     
      
     public void calculerPaye(){
 double paye=0;
  ObservableList<Vente> list=getVentes();
  for(Vente v:list)
      paye+=v.getP().getMontant();
  labelpay.setText(paye+"");

 }
     public double calculerMontant(){
 double montant=0;
  ObservableList<Vente> list=getVentes();
  for(Vente v:list)
      montant+=v.getP().getMontant();
  return montant;

 } 
     public void calculerReste(){
 double reste=0;
  ObservableList<Vente> list=getVentes();
  
  for(Vente v:list)
      reste=v.getTotal()-calculerMontant();
  labelreste.setText(reste+"");

 }
        
}

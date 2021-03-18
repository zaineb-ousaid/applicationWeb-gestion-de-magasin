/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerFichiers;


import gestion_magazin.AutoCompleteComboBox;
import gestion_magazin.Produits;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

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
public class ProduitController implements Initializable {

    @FXML
    private TextField tfid;
    @FXML
    private TextField tfdesignation;
    @FXML
    private TextField tfprixachat;
    @FXML
    private TextField tfprixvente;
    private DatePicker datePicker = new DatePicker();
   
    @FXML
    private Button btnsupprimer;
    @FXML
    private Button btnmodifier;
    @FXML
    private Button btnajouter;
    @FXML
    private TableView<Produits> tvproduits;
    @FXML
    private TableColumn<Produits, Integer> colid;
    @FXML
    private TableColumn<Produits, String> coldesignation;
    @FXML
    private TableColumn<Produits, Double> colprixachat;
    @FXML
    private TableColumn<Produits, Double> colprixvente;
    @FXML
    private TableColumn<Produits,Date> coldate;
    @FXML
    private TextField tfsearch;
    @FXML
    private TextField tfqte;
    @FXML
    private TableColumn<Produits, Integer> colqte;
      ObservableList<Produits> listM;
    ObservableList<Produits> dataList;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private DatePicker date;
    @FXML
    private TableColumn<Produits, String> colcategorie;
    
    //private ComboBox<String> categorie_box;
    @FXML
    private void handleMouseAction(MouseEvent event) {
       Produits produit= tvproduits.getSelectionModel().getSelectedItem();
       tfid.setText(""+produit.getId());
       tfdesignation.setText(produit.getDesignation());
       tfprixachat.setText(""+produit.getPrixAchat());
       tfprixvente.setText(""+produit.getPrixVente());
       tfqte.setText(""+produit.getQte());
       comboBox.setValue(produit.getCategorie());
      
       date.setValue(LocalDate.now());
    }
     
    /**
     * Initializes the controller class.
     */
    //LocalDate date;
           
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         comboBox.getItems().addAll("Telephones","Tablettes","Vetements","PC");
     new AutoCompleteComboBox<>(comboBox);
        
        showProduits();
    }    

    @FXML
    private void handButtonAction(ActionEvent event) {
         // date=datePicker.getValue();
          //System.out.println(date.toString());
        
        if(event.getSource()==btnajouter){
        insertProduits();
        }
        else if(event.getSource()==btnmodifier){
        updateProduits();
    }else if(event.getSource()==btnsupprimer){
        deleteButton();
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
     
     public ObservableList<Produits> getProduits(){
     ObservableList<Produits> listeProduits=FXCollections.observableArrayList();
     Connection conn=getConnection();
     String query="SELECT * FROM produits";
     Statement st;
     ResultSet rs;
     try{
     st= conn.createStatement();
     rs=st.executeQuery(query);
     Produits produits;
     while(rs.next()){
         
     produits=new Produits(rs.getInt("ID_PRODUIT"),rs.getString("DESIGNATION"),rs.getDouble("PRIX_ACHAT"),rs.getDouble("PRIX_VENTE"),rs.getInt("QTE"),rs.getDate("date"),rs.getString("CATEGORIE"));
     listeProduits.add(produits);
     }
     }
     catch(Exception ex){
     ex.printStackTrace();
     }
     return listeProduits;
     }
   
     public void showProduits(){
         ObservableList<Produits> list=getProduits();
         
         colid.setCellValueFactory(new PropertyValueFactory<Produits,Integer>("id"));
         coldesignation.setCellValueFactory(new PropertyValueFactory<Produits,String>("designation"));
         colprixachat.setCellValueFactory(new PropertyValueFactory<Produits,Double>("prixAchat"));
         colprixvente.setCellValueFactory(new PropertyValueFactory<Produits,Double>("prixVente"));
         colqte.setCellValueFactory(new PropertyValueFactory<Produits,Integer>("qte"));
         coldate.setCellValueFactory(new PropertyValueFactory<Produits,Date>("date")); 
         colcategorie.setCellValueFactory(new PropertyValueFactory<Produits,String>("categorie"));
     
         tvproduits.setItems(list);
       searchProduits();
     }
     private void insertProduits(){
     
         String query="INSERT INTO produits VALUES("+tfid.getText()+",'"+tfdesignation.getText()+"',"+tfprixachat.getText()+","+tfprixvente.getText()+","+tfqte.getText()+","+date.getValue()+", '"+comboBox.getValue()+"' )";
    
         executeQuery(query);
         searchProduits();
         showProduits();
     }
     private void updateProduits(){
    
     String query="UPDATE produits SET DESIGNATION = '"+tfdesignation.getText()+"', PRIX_ACHAT = '"+tfprixachat.getText()+"', PRIX_VENTE = '"+tfprixvente.getText()+"' , QTE= '"+tfqte.getText()+ "', DATE= '"+date.getValue()+"' ,CATEGORIE= '"+comboBox.getValue()+"' WHERE ID_PRODUIT="+tfid.getText()+"";
    executeQuery(query);
    searchProduits();
    showProduits();
    
     }
    private void deleteButton(){
    String query="DELETE FROM produits WHERE ID_PRODUIT="+tfid.getText()+"";
        executeQuery(query);
        searchProduits();
        showProduits();
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
    
 void searchProduits() {          
        colid.setCellValueFactory(new PropertyValueFactory<Produits,Integer>("id"));
         coldesignation.setCellValueFactory(new PropertyValueFactory<Produits,String>("designation"));
         colprixachat.setCellValueFactory(new PropertyValueFactory<Produits,Double>("prixAchat"));
         colprixvente.setCellValueFactory(new PropertyValueFactory<Produits,Double>("prixVente"));
         colqte.setCellValueFactory(new PropertyValueFactory<Produits,Integer>("qte"));
        
        dataList = getProduits();
        tvproduits.setItems(dataList);
        FilteredList<Produits> filteredData = new FilteredList<>(dataList, b -> true);  
 tfsearch.textProperty().addListener((observable, oldValue, newValue) -> {
 filteredData.setPredicate(produit -> {
    if (newValue == null || newValue.isEmpty()) {
     return true;
    }    
    String lowerCaseFilter = newValue.toLowerCase();
    
    if (produit.getDesignation().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
     return true; // bien filtree
    }
                                
         else  
          return false; //rien n'est trouvee
   });
  });  
  SortedList<Produits> sortedData = new SortedList<>(filteredData);  
  sortedData.comparatorProperty().bind(tvproduits.comparatorProperty());  
  tvproduits.setItems(sortedData);      
    }

   
    
}
    

    

   
     
    


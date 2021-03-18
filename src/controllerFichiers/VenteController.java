/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerFichiers;

import gestion_magazin.AutoCompleteComboBox;
import gestion_magazin.Clients;
import gestion_magazin.LigneCommande;
import gestion_magazin.Produits;
import gestion_magazin.Vente;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author zenume
 */
public class VenteController implements Initializable {

    @FXML
    private TableView<Produits> tvProduit;
    private TableColumn<Produits, Integer> coln1;
    @FXML
    private TableColumn<Produits, String> coldesignation1;
    @FXML
    private TableColumn<Produits, Double> colprixAchat;
    @FXML
    private TableColumn<Produits, Double> colprixVente;
    @FXML
    private TableColumn<Produits, Integer> colqte1;
    @FXML
    private TableView<LigneCommande> tvCommande;
    @FXML
    private TableColumn<LigneCommande, String> coldesignation2;
    @FXML
    private TableColumn<LigneCommande,String> colprix;
    @FXML
    private TableColumn<LigneCommande,Double> coltva;
    @FXML
    private TableColumn<LigneCommande, Integer> colqte2;
    @FXML
    private TableColumn<LigneCommande, Double> colstotal;
    @FXML
    private TextField tfsearch;
    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField tfdesignation;
    @FXML
    private TextField tfprix;
    @FXML
    private TextField tfqte;
    @FXML
    private Button btnmoins;
    @FXML
    private Button btnplus;
    @FXML
    private Button btnModifier;
    
     ObservableList<Produits> dataList;
     
     ObservableList clients=FXCollections.observableArrayList();
    @FXML
    private Button btnEnregistrer;
    private Label lbHt;
    
    @FXML
    private TextField tfvente;
    @FXML
    private TextField tfncommande;
    @FXML
    private DatePicker date;
    @FXML
    private TableColumn<Produits, String> colcategorie;
    @FXML
    private TableColumn<Produits, Date> coldate;
    @FXML
    private TextField tfidp;
    @FXML
    private Label labelht;
    @FXML
    private Label labeltva;
    @FXML
    private Label labeltotal;
    @FXML
    private Button reglement;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        date.setValue(LocalDate.now());
        fillCombox();
     new AutoCompleteComboBox<>(comboBox);
        showProduits();
          showLignes();
        
       
    }  
    public void fillCombox(){
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
   
 
 
    @FXML
    private void handleMouseAction(MouseEvent event) {
        Produits produit= tvProduit.getSelectionModel().getSelectedItem();
        LigneCommande lc= tvCommande.getSelectionModel().getSelectedItem();
       tfidp.setText(""+produit.getId());
       tfdesignation.setText(produit.getDesignation());
       tfprix.setText(""+produit.getPrixVente());
      tfdesignation.setText(lc.getProduit().getDesignation());
       tfprix.setText(""+lc.getProduit().getPrixVente());
       tfncommande.setText(""+lc.getId());
      tfqte.setText(""+lc.getQte());
   
    
    }
    
    

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
  
         if(event.getSource()==btnplus){
        insertLignes();
       calculerHT();
       calculerTva();
       calculerTotal();
        }
        else if(event.getSource()==btnModifier){
        updateLignes();
        showLignes();
          calculerHT();
       calculerTva();
       calculerTotal();
    }else if(event.getSource()==btnmoins){
        deleteLignes();
         calculerHT();
       calculerTva();
       calculerTotal();
    }else if(event.getSource()==btnEnregistrer){
        insertVente();
          calculerHT();
       calculerTva();
       calculerTotal();
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
        /////////////////////////////////////produits///////////////////////////////////////////////////////

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
         
     produits=new Produits(rs.getInt("ID_PRODUIT"),rs.getString("DESIGNATION"),rs.getDouble("PRIX_ACHAT"),rs.getDouble("PRIX_VENTE"),rs.getInt("QTE"),rs.getDate("DATE"),rs.getString("CATEGORIE"));
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
         
         coldesignation1.setCellValueFactory(new PropertyValueFactory<Produits,String>("designation"));
         colprixAchat.setCellValueFactory(new PropertyValueFactory<Produits,Double>("prixAchat"));
         colprixVente.setCellValueFactory(new PropertyValueFactory<Produits,Double>("prixVente"));
         colqte1.setCellValueFactory(new PropertyValueFactory<Produits,Integer>("qte"));
         coldate.setCellValueFactory(new PropertyValueFactory<Produits,Date>("date")); 
         colcategorie.setCellValueFactory(new PropertyValueFactory<Produits,String>("categorie"));
     
         tvProduit.setItems(list);
       searchProduits();
     }
     void searchProduits() {          

         coldesignation1.setCellValueFactory(new PropertyValueFactory<Produits,String>("designation"));
         colprixAchat.setCellValueFactory(new PropertyValueFactory<Produits,Double>("prixAchat"));
         colprixVente.setCellValueFactory(new PropertyValueFactory<Produits,Double>("prixVente"));
         colqte1.setCellValueFactory(new PropertyValueFactory<Produits,Integer>("qte"));
         coldate.setCellValueFactory(new PropertyValueFactory<Produits,Date>("date")); 
         colcategorie.setCellValueFactory(new PropertyValueFactory<Produits,String>("categorie"));
        
        dataList = getProduits();
        tvProduit.setItems(dataList);
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
  sortedData.comparatorProperty().bind(tvProduit.comparatorProperty());  
  tvProduit.setItems(sortedData);      
    }
     
     
      /////////////////////////////////////////////////////lignes//////////////////////////////////////////////
 
     
     public ObservableList<LigneCommande> getLignes(){
     ObservableList<LigneCommande> listeLignes=FXCollections.observableArrayList();
     Connection conn=getConnection();
     String query="SELECT * FROM ligneCommande,vente,produits WHERE ligneCommande.ID_VENTE=vente.ID_VENTE AND ligneCommande.ID_PRODUIT=produits.ID_PRODUIT AND ligneCommande.ID_VENTE="+tfvente.getText()+"";
     Statement st;
     ResultSet rs;
     try{
     st= conn.createStatement();
     rs=st.executeQuery(query);
     LigneCommande lc;
     Produits p;
     Clients c;
     Vente v;
     while(rs.next()){
     
      p=new Produits(rs.getInt("ID_PRODUIT"),rs.getString("DESIGNATION"),rs.getDouble("PRIX_ACHAT"),rs.getDouble("PRIX_VENTE"),rs.getInt("QTE"),rs.getDate("DATE"),rs.getString("CATEGORIE"));
      lc=new LigneCommande(rs.getInt("ID_LIGNE"),rs.getInt("qte"),p,rs.getDouble("PRIX_VENTE"));
     listeLignes.add(lc);
        System.out.println(lc.getProduit().getDesignation());
     }
     }
     catch(Exception ex){
     ex.printStackTrace();
     }
     return listeLignes;
     }
   
     public void showLignes(){
         ObservableList<LigneCommande> list=getLignes();
          coldesignation2.setCellValueFactory(cellData ->
    new ReadOnlyStringWrapper(cellData.getValue().getProduit().getDesignation()));
        colprix.setCellValueFactory(cellData ->
    new ReadOnlyStringWrapper(Double.toString(cellData.getValue().getProduit().getPrixVente())));
         colqte2.setCellValueFactory(new PropertyValueFactory<LigneCommande,Integer>("qte"));
         coltva.setCellValueFactory(new PropertyValueFactory<LigneCommande,Double>("tva")); 
         colstotal.setCellValueFactory(new PropertyValueFactory<LigneCommande,Double>("sousTotal")); 
     
         tvCommande.setItems(list);
       
  
     }
     private void insertLignes(){
     
         String query="INSERT INTO ligneCommande VALUES(NULL,"+tfqte.getText()+","+tfvente.getText()+","+tfidp.getText()+")";
         
    
         executeQuery(query);
         showLignes();
         
     }
     private void updateLignes(){
       
     String query="UPDATE ligneCommande SET  qte = "+tfqte.getText()+", ID_VENTE="+tfvente.getText()+", ID_PRODUIT="+tfidp.getText()+" WHERE ID_LIGNE="+tfncommande.getText()+"";
     
    executeQuery(query);
  
    showLignes();
    
     }
    private void deleteLignes(){
    String query="DELETE FROM ligneCommande WHERE ID_LIGNE="+tfncommande.getText()+"";
        executeQuery(query);
        showLignes();
    }
    private void insertVente(){
     
        String query="INSERT INTO vente VALUES( '"+tfvente.getText()+"','"+date.getValue()+"', '"+comboBox.getValue()+"' , '"+labeltotal.getText()+"' )";
         executeQuery(query);
       
         
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


 public void calculerTotal(){
 double total=0;
  ObservableList<LigneCommande> list=getLignes();
  for(LigneCommande lc:list)
      total+=lc.getSousTotal();
  labeltotal.setText(total+"");

 }
  private void calculerHT(){
 double ht=0;
  ObservableList<LigneCommande> list=getLignes();
  for(LigneCommande lc:list)
      ht+=lc.getHT();
  labelht.setText(ht+"");

 }
   private void calculerTva(){
 double tva=0;
  ObservableList<LigneCommande> list=getLignes();
  for(LigneCommande lc:list)
      tva+=lc.getTvaa();
  labeltva.setText(tva+"");

 }


    @FXML
    private void reglement(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxmlFichiers/reglement.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Reglement");
        stage.setScene(scene);
        stage.show();
    }


   

}

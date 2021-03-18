/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerFichiers;

import gestion_magazin.Clients;
import gestion_magazin.Produits;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import static java.sql.JDBCType.NULL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
public class ClientController implements Initializable {

    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfadress;
    @FXML
    private TextField tftel;
    @FXML
    private TextField tfemail;
    @FXML
    private TextField tfsearch;
    @FXML
    private TableView<Clients> tvClient;
    @FXML
    private TableColumn<Clients, Integer> colcode;
    @FXML
    private TableColumn<Clients, String> colprenom;
    @FXML
    private TableColumn<Clients, String> colnom;
    @FXML
    private TableColumn<Clients, String> coladress;
    @FXML
    private TableColumn<Clients, String> coltel;
    @FXML
    private TableColumn<Clients, String> colemail;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnAjouter;
    @FXML
    private TextField tfcode;
 
    ObservableList<Clients> dataList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showClients();
    }    

    @FXML
    private void handlingMouseAction(MouseEvent event) {
        Clients client= tvClient.getSelectionModel().getSelectedItem();
       tfcode.setText(""+client.getCode());
       tfnom.setText(client.getNom());
       tfprenom.setText(client.getPrenom());
       tftel.setText(client.getTel());
       tfemail.setText(client.getEmail());
       tfadress.setText(client.getAdress());
    }

    @FXML
    private void handlingButtonAction(ActionEvent event) {
        if(event.getSource()==btnAjouter){
        insertClients();
        }
        else if(event.getSource()==btnModifier){
        updateClients();
    }else if(event.getSource()==btnSupprimer){
        deleteClients();
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
     
     public ObservableList<Clients> getClients(){
     ObservableList<Clients> listeClients=FXCollections.observableArrayList();
     Connection conn=getConnection();
     String query="SELECT * FROM client";
     Statement st;
     ResultSet rs;
     try{
     st= conn.createStatement();
     rs=st.executeQuery(query);
     Clients clients;
     while(rs.next()){
         
     clients=new Clients(rs.getString("NOM"),rs.getString("PRENOM"),rs.getString("EMAIL"),rs.getString("TEL"),rs.getString("ADRESS"),rs.getInt("ID_CLIENT"));
     listeClients.add(clients);
     }
     }
     catch(Exception ex){
     ex.printStackTrace();
     }
     return listeClients;
     }
   
     public void showClients(){
         ObservableList<Clients> list=getClients();
         
         colnom.setCellValueFactory(new PropertyValueFactory<Clients,String>("nom"));
         colprenom.setCellValueFactory(new PropertyValueFactory<Clients,String>("prenom"));
         coltel.setCellValueFactory(new PropertyValueFactory<Clients,String>("tel"));
         colemail.setCellValueFactory(new PropertyValueFactory<Clients,String>("email"));
         coladress.setCellValueFactory(new PropertyValueFactory<Clients,String>("adress"));
         colcode.setCellValueFactory(new PropertyValueFactory<Clients,Integer>("code")); 
     
         tvClient.setItems(list);
       searchClients();
     }
     private void insertClients(){
     
         String query="INSERT INTO client VALUES('"+tfnom.getText()+"' , '"+tfprenom.getText()+"' , '"+tftel.getText()+"','"+tfemail.getText()+"' , '"+tfadress.getText()+"' , "+tfcode.getText()+")";
    
         executeQuery(query);
         searchClients();
         showClients();
     }
     private void updateClients(){
      
     String query="UPDATE client SET NOM = '"+tfnom.getText()+"' , PRENOM = '"+tfprenom.getText()+"' , TEL = '"+tftel.getText()+"' , EMAIL= '"+tfemail.getText()+"',ADRESS='"+tfadress.getText()+"' WHERE ID_CLIENT="+tfcode.getText()+"";
    executeQuery(query);
    searchClients();
    showClients();
    
     }
    private void deleteClients(){
    String query="DELETE FROM client WHERE ID_CLIENT="+tfcode.getText()+"";
        executeQuery(query);
        searchClients();
        showClients();
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
    
 void searchClients() {          
        colcode.setCellValueFactory(new PropertyValueFactory<Clients,Integer>("code"));
         colnom.setCellValueFactory(new PropertyValueFactory<Clients,String>("nom"));
         colprenom.setCellValueFactory(new PropertyValueFactory<Clients,String>("prenom"));
         coltel.setCellValueFactory(new PropertyValueFactory<Clients,String>("tel"));
         colemail.setCellValueFactory(new PropertyValueFactory<Clients,String>("email"));
         coladress.setCellValueFactory(new PropertyValueFactory<Clients,String>("adress"));
        
        dataList = getClients();
        tvClient.setItems(dataList);
        FilteredList<Clients> filteredData = new FilteredList<>(dataList, b -> true);  
 tfsearch.textProperty().addListener((observable, oldValue, newValue) -> {
 filteredData.setPredicate(client -> {
    if (newValue == null || newValue.isEmpty()) {
     return true;
    }    
    String lowerCaseFilter = newValue.toLowerCase();
    
    if (client.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
     return true; // bien filtree
    }else if (client.getPrenom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
     return true; // bien filtree
    }else if (client.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
     return true; // bien filtree
    }else if (client.getTel().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
     return true; // bien filtree
    }else if (client.getAdress().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
     return true; // bien filtree
    } 
                                
         else  
          return false; //rien n'est trouvee
   });
  });  
  SortedList<Clients> sortedData = new SortedList<>(filteredData);  
  sortedData.comparatorProperty().bind(tvClient.comparatorProperty());  
  tvClient.setItems(sortedData);      
    }
    
}

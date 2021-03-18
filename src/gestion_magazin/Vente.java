/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion_magazin;
import controllerFichiers.VenteController;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author zenume
 */
public class Vente {
   private int numero;
   private Date date;
   private String client;
   private double total=0;
   //private double pay=0;
   //private double reste=0;
   
   private Paiement p;

    public Vente(int numero,Date date, String client, Paiement p,double total) {
        this.numero=numero;
        this.date = date;
        this.client = client;
        this.p=p;
        this.total=total;
    }

    public Paiement getP() {
        return p;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

  

    public double getTotal() {
//      calculerTotal();
        return total;
    }


}

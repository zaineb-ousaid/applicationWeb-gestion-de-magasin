/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion_magazin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author zenume
 */
public class Produits {
 private int id;
 private String designation;
 private double prixAchat;
 private double prixVente;
 private Date date;
 private int qte;
 private String categorie;
 private List<LigneCommande> lc=new ArrayList<>();

    public Produits(int id, String designation, double prixAchat, double prixVente,int qte,Date date,String categorie) {
        this.id = id;
        this.designation = designation;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
        this.qte=qte;
        this.date=date;
        this.categorie=categorie;
        
      
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Produits(String designation, double prixVente) {
        this.designation=designation;
        this.prixVente=prixVente;
    }

    public int getId() {
        return id;
    }

    public String getDesignation() {
        return designation;
    }

    public double getPrixAchat() {
        return prixAchat;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public Date getDate() {
        return date;
    }
 public int getQte() {
        return qte;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

  
 
}

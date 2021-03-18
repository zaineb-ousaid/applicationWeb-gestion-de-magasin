/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion_magazin;

import java.util.Date;

/**
 *
 * @author zenume
 */
public class Paiement {
    private int id;
    private double montant;
    private String type;
    private Date date;
    private String banque="--";
    private String cheque="--";
    private Vente v;

    public Paiement(int id,double montant ,Date date,String type) {
        this.id = id;
        this.montant=montant;
        this.date=date;
        this.type = type;
        //this.v = v;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBanque() {
        return banque;
    }

    public void setBanque(String banque) {
        this.banque = banque;
    }

    public String getCheque() {
        return cheque;
    }

    public void setCheque(String cheque) {
        this.cheque = cheque;
    }

    public Vente getV() {
        return v;
    }

    public void setV(Vente v) {
        this.v = v;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    
    
}

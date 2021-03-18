/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion_magazin;

/**
 *
 * @author zenume
 */
public class LigneCommande {
    private int id;
    private int qte;
    private double sousTotal=0;
    private final double tva=0.2;
    private double tvaa;
    private double HT;
    private Produits produit;
  
    private Vente vente;
    public LigneCommande( int id,int qte,Produits produit,double sousTotal) {
       this.id=id;
        this.qte = qte;
        this.produit=produit;
        this.sousTotal=sousTotal;
        
        
    }

    public double getTva() {
        return tva;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
      
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;

    }

    public double getSousTotal() {
        calculerSousTotal();
        return sousTotal;
    }

    public Produits getProduit() {
        return produit;
    }

    public void setProduit(Produits produit) {
        this.produit = produit;
    }

   

      private void calculerSousTotal() {
      sousTotal=this.qte*this.produit.getPrixVente();
    }
     public void calculerHT(){
    HT= (this.qte*this.produit.getPrixVente())/1.2;


   } 
   public void calculerTva(){
    tvaa= (this.qte*this.produit.getPrixVente())-getHT();
   } 

    public double getTvaa() {
        calculerTva();
        return tvaa;
    }

    public double getHT() {
        calculerHT();
        return HT;
    }
   
}

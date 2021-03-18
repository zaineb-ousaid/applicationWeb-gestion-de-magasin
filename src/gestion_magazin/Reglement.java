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
public class Reglement {
   private LigneCommande lc;
   private Produits p;
   private double HT=0;
   private double tva=0;
   private double total=0;

   
   Reglement(double HT,double tva,LigneCommande lc){
    this.HT=HT;
    this.tva=tva;
    this.lc=lc;
   
}

    public double getHT() {
        calculerHT();
        return HT;
    }

    public double getTva() {
        calculerTva();
        return tva;
    }

    public double getTotal() {
        calculerTotal();
        return total;
    }
   
   
   public void calculerHT(){
    HT= (lc.getQte()*this.p.getPrixVente())/1.2;


   } 
   public void calculerTva(){
    tva= lc.getQte()*this.p.getPrixVente()-this.HT;
 
   } 

    private void calculerTotal() {
       
    }
}

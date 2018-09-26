/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482final;

/**
 *
 * @author Matthew
 */
public class Outsourced extends Part {
    String companyName;
    
    public Outsourced(int newID, String newName, double newPrice,int newStock, int minStock, int maxStock, String newCompanyName){
        super(newID, newName, newPrice, newStock, minStock, maxStock);
        this.companyName = newCompanyName;
    }
    
    public Outsourced(){
        super(0,"",0,0,0,0);
        this.setCompanyName("");
    }
    
    //get
    public String getCompanyName(){
        return companyName;
    }
    
    //set
    public void setCompanyName(String newCompanyName){
        this.companyName = newCompanyName;
    }
}

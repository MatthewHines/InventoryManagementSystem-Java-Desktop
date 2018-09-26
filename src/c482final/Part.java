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
public abstract class Part {
    
    int partID;
    String name;
    double price;
    int inStock;
    int min;
    int max;
    
    public Part(){
        
    }
    
    public Part(int ID){
        Inventory.lookupPart(ID);
    }
    
    public Part(int newID, String newName, double newPrice,int newStock, int minStock, int maxStock){
        this.setPartID(newID);
        this.setName(newName);
        this.setPrice(newPrice);
        this.setInStock(newStock);
        this.setMin(minStock);
        this.setMax(maxStock);   
    }
    
    
    //gets
    public String getName(){
        return this.name;
    }
    
    public double getPrice(){
        return this.price;
    }
    
    public int getInStock(){
        return this.inStock;
    }
    
    public int getMin(){
        return this.min;
    }
    
    public int getMax(){
        return this.max;
    }
    
    public int getPartID(){
        return this.partID;
    }
    
    //sets
    public void setName(String newName){
        this.name = newName;
    }
    
    public void setPrice(double newPrice){
        this.price = newPrice;
    }
    
    public void setInStock(int newInStock){
        this.inStock = newInStock;
    }
    
    public void setMin(int newMin){
        this.min = newMin;
    }
    
    public void setMax(int newMax){
        this.max = newMax;
    }
    
    public void setPartID(int newPartID){
        this.partID = newPartID;
    }
    
}

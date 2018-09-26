/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482final;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Matthew
 */
public class Product {
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    int productID;
    String name;
    double price;
    int inStock;
    int min;
    int max;
    
    public Product(){
        this.setProductID(Inventory.getMaxProductID()+1);
    }
    
    public Product(int newID, String newName, double newPrice,int newStock, int minStock, int maxStock, ObservableList<Part> ol){
        this.productID = newID;
        this.name = newName;
        this.price = newPrice;
        this.inStock = newStock;
        this.min = minStock;
        this.max = maxStock;
        this.setAssociatedParts(ol);
    }
    
    public Product(int newID, String newName, double newPrice,int newStock, int minStock, int maxStock){
        this.productID = newID;
        this.name = newName;
        this.price = newPrice;
        this.inStock = newStock;
        this.min = minStock;
        this.max = maxStock;
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
    
    public int getProductID(){
        return this.productID;
    }   
    
    public ObservableList<Part> getAssociatedParts(){
        return this.associatedParts;
    }
    
    //sets
    public void addAssociatedPart(Part newPart){
        this.associatedParts.add(newPart);
    }
    
    public void remAssociatedPart(Part oldPart){
        this.associatedParts.remove(oldPart);
    }
    
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
    
    public void setProductID(int newPartID){
        this.productID = newPartID;
    }
    
    public void setAssociatedParts(ObservableList<Part> parts){
        this.associatedParts = parts;
    }
    
}

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
public class Inventory {
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Product> products = FXCollections.observableArrayList();
    public static int maxPartID = 0;
    public static int maxProdID = 0;
    
    public static void addProduct(Product newProd){
        maxProdID++;
        products.add(newProd);
    }
    
    public static boolean removeProduct(Product prodToDel){
        
        boolean removed = false;
        
        if(lookupProduct(prodToDel.getProductID()) != null){
            products.remove(prodToDel);
            removed = true;
        }

        return removed;
    }
    
    public static Product lookupProduct(int prodID){  
        Product result = null;
        for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getProductID() == prodID) {
                    result = products.get(i);
                }
        }
        return result;
    }
    
    public static void updateProduct(int prodID){
        Product oldProduct = lookupProduct(prodID);
    }
    
    public static void addPart(Inhouse newPart){
        maxPartID++;
        allParts.add(newPart);
    }
    
    public static void addPart(Outsourced newPart){
        maxPartID++;
        allParts.add(newPart);
    }
    
    public static boolean deletePart(Part partToDel){
        
        boolean removed = false;
        
        if(lookupPart(partToDel.getPartID()) != null){
            allParts.remove(partToDel);
            removed = true;
        }

        return removed;
    }
    
    public static Part lookupPart(int partID){
        Part result = null;
        for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getPartID() == partID) {
                    
                    result = allParts.get(i);
                          
                }
        }
        return result;
                
    }
    
    public static int getMaxPartID(){
        return maxPartID;
    }
    
    public static int getMaxProductID(){
        return maxProdID;
    }
    
    public static void updatePart(int partID, Inhouse newPart){
        Part oldPart = lookupPart(partID);
        newPart.setPartID(oldPart.getPartID());
        deletePart(oldPart);
        addPart(newPart);
    }
    
    public static void updatePart(int partID, Outsourced newPart){
        Part oldPart = lookupPart(partID);
        newPart.setPartID(oldPart.getPartID());
        deletePart(oldPart);
        addPart(newPart);
    }
    
}


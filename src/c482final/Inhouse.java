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
public class Inhouse extends Part {
    protected int machineID;
    
    public Inhouse(int newID, String newName, double newPrice,int newStock, int minStock, int maxStock, int newMachineID){
        super(newID, newName, newPrice, newStock, minStock, maxStock);
        this.machineID = newMachineID;
    }
    
    public Inhouse(){
        super(0,"",0,0,0,0);
        this.setMachineID(0);
    }
    
    //get
    public int getMachineID(){
        return machineID;
    }
    
//set
    public void setMachineID(int newMachineID){
        machineID = newMachineID;
    }
}

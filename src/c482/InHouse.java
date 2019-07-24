/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482;

/**
 *
 * @author Ayumu Suzuki
 */
public class InHouse extends Part{
    private int machineId;
    /*
    constructor
    */
    public InHouse(int id, String name, double price, 
            int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    
    /*
    setter
    */
    public void setMahineId(int machineId){this.machineId = machineId;}
    
    /*
    getter
    */
    public int getMachineId(){return this.machineId;}
}

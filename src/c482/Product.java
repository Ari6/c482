/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482;

import javafx.collections.ObservableList;

/**
 *
 * @author Ayumu Suzuki
 */
public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    /*
    constructor
    */
    public Product(int id, String name, double price, 
            int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    /*
    setters
    */
    public void setName(String name){this.name = name;}
    public void setPrice(double price){this.price = price;}
    public void setStock(int stock){this.stock = stock;}
    public void setMin(int min){this.min = min;}
    public void setMax(int max) {this.max = max;}
    public void setPrice(int max){this.price = max;}

    /*
    getters
    */
    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public double getPrice() {return this.price;}
    public int getStock() { return this.stock;}
    public int getMin() { return this.min;}
    public int getMax() { return this.max;}
    
    /*
    add
    */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    
    /*
    delete
    */
    public void deleteAssociatedPart(Part associatedPart){
        associatedParts.remove(associatedPart);
    }
    
    /*
    get all
    */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}

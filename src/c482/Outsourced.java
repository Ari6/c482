/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482;

/**
 *
 * @author A Suzuki
 */
public class Outsourced extends Part{
    private String companyName;
    
    /*
    constractor
    */
    public Outsourced(int id, String name, double price, int stock, 
            int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /*
    setter
    */
    public void setCompanyName(String companyName){
            this.companyName = companyName;
    }
    /*
    getter
    */
    public String getCompanyName(){return this.companyName;}
}
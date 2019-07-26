/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Ayumu Suzuki
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    /*
    add
    */
    public void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    /*
    lookup
    */
    public Part lookupPart(int partId){
        for(Part p : allParts){
            if(p.getId() == partId){
                return p;
            }
        }
        return null;
    }
    
    public Product lookupProduct(int productId){
        for(Product p : allProducts){
            if(p.getId() == productId){
                return p;
            }
        }
        return null;
    }
    
    public ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> ret = FXCollections.observableArrayList();
        for(Part p : allParts){
            if(p.getName().contains(partName)){
                ret.add(p);
            }
        }
        return ret;
    }
    
    public ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> ret = FXCollections.observableArrayList();
        for(Product p : allProducts){
            if(p.getName().contains(productName)){
                ret.add(p);
            }
        }
        return ret;
    }
    
    /*
    update
    */
    public void updatePart(int index, Part selectedPart){
        allParts.remove(index);
        allParts.add(index, selectedPart);
    }
    
    public void updateProduct(int index, Product selectedProduct){
        allProducts.remove(index);
        allProducts.add(index, selectedProduct);
    }
    
    /*
    delete
    */
    public void deletePart(Part selectedPart){
        allParts.remove(selectedPart);
    }
    
    public void deleteProduct(Product selectedProduct){
        allProducts.removeAll(selectedProduct);
    }
    
    /*
    get
    */
    public ObservableList<Part> getAllParts(){return allParts;}
    public ObservableList<Product> getAllProducts(){return allProducts;}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import c482.InHouse;
import c482.Inventory;
import c482.Outsourced;
import c482.Part;
import c482.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
/**
 *
 * @author A Suzuki
 */
public class MainScreenController implements Initializable {
    static private boolean first = true;
    Inventory inventory = new Inventory();
    
    @FXML private Button exitButton;
    
    @FXML private Button partsSearchButton;
    @FXML private Button partsAddButton;
    @FXML private Button partsModifyButton;
    @FXML private Button partsDeleteButton;
    
    @FXML private Button productsSearchButton;
    @FXML private Button productsAddButton;
    @FXML private Button productsModifyButton;
    @FXML private Button productsDeleteButton;
    
    //parts list
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> partsPartID;
    @FXML private TableColumn<Part, String> partsPartName;
    @FXML private TableColumn<Part, Integer> partsInventoryLevel;
    @FXML private TableColumn<Part, Double> partsPriceCostPerUnit;
    
    //product list
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, Integer> productsProductID;
    @FXML private TableColumn<Product, String> productsProductName;
    @FXML private TableColumn<Product, Integer> productsInventoryLevel;
    @FXML private TableColumn<Product, Double> productsPricePerUnit;
    @FXML private TextField partsSearch;
    @FXML private TextField productsSearch;
    
    @FXML
    private void exitButtonOnClick(ActionEvent event){
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void partsSearchButtonOnAction() {
        int searchId;
        String searchName;
        try{
            searchId = Integer.parseInt(partsSearch.getText());
            partsTable.setItems(FXCollections.observableArrayList(inventory.lookupPart(searchId)));
        } catch (NumberFormatException e) {
            searchName = partsSearch.getText();
            if(searchName.equals("")){
                partsTable.setItems(inventory.getAllParts());
            } else {
                partsTable.setItems(inventory.lookupPart(searchName));
            }
        }
    }
    
    @FXML
    private void partsAddButtonOnClick(ActionEvent event) throws Exception{
        int nextId;
        try{
            //get the current last id + 1
            nextId = inventory.getAllParts().stream().max((a,b) -> a.getId() - b.getId()).get().getId() + 1;
        } catch (Exception e){
            nextId = 1;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/AddPartG.fxml"));
        Parent root = loader.load();
        
        //set id to addPart window
        AddPartController addPartController = loader.getController();
        addPartController.setId(nextId);
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();        
        stage.setTitle("Parts Add");
        stage.setScene(scene);
        stage.show();
        //close current window
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        
    }
    
    @FXML
    private void partsModifyButtonOnClick(ActionEvent event)throws IOException{
        try{
            ModifyPartController modifyPartController;
            FXMLLoader loader = new FXMLLoader();
            
            if(partsTable.getSelectionModel().getSelectedItem() instanceof InHouse){
                loader.setLocation(getClass().getResource("/View/ModifyPart.fxml"));
            } else {
                loader.setLocation(getClass().getResource("/View/ModifyPartOut.fxml"));
            }
            
            Parent root = loader.load();
            modifyPartController = loader.getController();
            modifyPartController.sendPart(partsTable.getSelectionModel().getSelectedItem());
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Parts Modify");
            stage.setScene(scene);
            stage.show();
            //close current window
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (NullPointerException e) {
            System.out.println("NullPointerException at partsModifyButtonOnClick");
        }
    }
    
    
    @FXML
    private void partsDeleteButtonOnClick(){
        try{
            inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
        } catch (NullPointerException e) {
            System.out.println("Nullponter Error in partsDeleteButton");
        }
    }
    
    @FXML
    private void productsSearchButtonOnAction(){
        int searchId;
        String searchName;
        try{
            searchId = Integer.parseInt(productsSearch.getText());
            productsTable.setItems(FXCollections.observableArrayList(inventory.lookupProduct(searchId)));
        } catch (NumberFormatException e) {
            searchName = productsSearch.getText();
            if(searchName.equals("")){
                productsTable.setItems(inventory.getAllProducts());
            } else {
                productsTable.setItems(inventory.lookupProduct(searchName));
            }
        }        
    }
    
    @FXML
    private void productsAddButtonOnClick(ActionEvent event) throws Exception {
        int nextId;
        try{
            nextId = (inventory.getAllProducts().stream().max((a,b) -> a.getId() - b.getId()).get().getId()) + 1;
        } catch (Exception e){
            nextId = 1;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/AddProduct.fxml"));
        Parent root = loader.load();
        
        AddProductController addProductController = loader.getController();
        addProductController.setNextId(nextId);
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();        
        stage.setTitle("Product Add");
        stage.setScene(scene);
        stage.show();
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
    
    @FXML
    private void productsModifyButtonOnClick(ActionEvent event)throws Exception {        
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyProduct.fxml"));
            Parent root = loader.load();

            ModifyProductController modifyProductController = loader.getController();
            modifyProductController.sendProduct(productsTable.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Product Modify");
            stage.setScene(scene);
            stage.show();
            //close current window
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
            
        } catch (NullPointerException e) {
        }
    }
    
    @FXML
    private void productsDeleteButtonOnClick(){
        inventory.deleteProduct(productsTable.getSelectionModel().getSelectedItem());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(first){
            Part p1 = new InHouse(1, "Part 1", 12.5, 10, 5, 2, 1000);
            Part p2 = new Outsourced(2, "Part 2", 15.0, 20, 10, 4, "Company 1");
            Part p3 = new InHouse(3, "Part 3", 16.5, 25, 4, 3, 1003);
            Product pro1 = new Product(1, "Product 1", 123.4, 3, 1, 2);
            Product pro2 = new Product(2, "Product 2", 099.4, 9, 9, 9);
            pro1.addAssociatedPart(p3);
            pro2.addAssociatedPart(p1);
            pro2.addAssociatedPart(p2);
            inventory.addPart(p1);
            inventory.addPart(p2);
            inventory.addPart(p3);
            inventory.addProduct(pro1);
            inventory.addProduct(pro2);

        }
            //set parts table
            partsPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
            partsPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
            partsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partsPriceCostPerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

            partsTable.setItems(inventory.getAllParts());
            
            //set products table
            productsProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
            productsProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
            productsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productsPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

            productsTable.setItems(inventory.getAllProducts());
            
            first = false;
    }    
    
}
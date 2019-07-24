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
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
/**
 *
 * @author Ayumu Suzuki
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
    
    
    @FXML
    private void exitButtonOnClick(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void partsSearchButtonOnClick() {}
    
    @FXML
    private void partsAddButtonOnClick() throws Exception{
        int nextId = inventory.getAllParts().stream().max((a,b) -> a.getId() - b.getId()).get().getId();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/AddPartG.fxml"));
        loader.load();
        
        Stage stageOld = (Stage) partsAddButton.getScene().getWindow();
        stageOld.close();
        
        AddPartGController addPartController = loader.getController();
        addPartController.setId(nextId);
        
        Parent root = loader.getRoot();
        
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        
        stage.setTitle("Parts Add");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void partsModifyButtonOnClick()throws IOException{
        try{
        ModifyPartGController modifyPartController;
        ModifyPartOutController modifyPartOutController;
        FXMLLoader loader = new FXMLLoader();

        if(partsTable.getSelectionModel().getSelectedItem() instanceof InHouse){
            loader.setLocation(getClass().getResource("/View/ModifyPartG.fxml"));
            loader.load();
            modifyPartController = loader.getController();
            modifyPartController.sendPart(partsTable.getSelectionModel().getSelectedItem());
        } else {
            loader.setLocation(getClass().getResource("/View/ModifyPartOut.fxml"));
            loader.load();
            modifyPartOutController = loader.getController();
            modifyPartOutController.sendPart(partsTable.getSelectionModel().getSelectedItem());
        }
        
        Stage stage = (Stage) partsModifyButton.getScene().getWindow();
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        
        stage.setTitle("Parts Modify");
        
        stage.setScene(scene);
        stage.show();
        } catch (NullPointerException e) {
        }
    }
    
    
    @FXML
    private void partsDeleteButtonOnClick(){
        try{
            inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
        } catch (NullPointerException e) {
        }
    }
    
    @FXML
    private void productsSearchButtonOnClick(){
    
    }
    
    @FXML
    private void productsAddButtonOnClick() throws Exception {
        int nextId = inventory.getAllProducts().stream().max((a,b) -> a.getId() - b.getId()).get().getId();
        
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddProduct.fxml"));
        
        Stage stageOld = (Stage) productsAddButton.getScene().getWindow();
        stageOld.close();
        
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        
        stage.setTitle("Product Add");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void productsModifyButtonOnClick()throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyProduct.fxml"));
        
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        
        stage.setTitle("Product Modify");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void productsDeleteButtonOnClick(){}
    
    public void showParts(Part addedPart){
        partsTable.setItems(inventory.getAllParts());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(first){
            Part p1 = new InHouse(1, "Part 1", 12.5, 10, 5, 2, 1000);
            Part p2 = new Outsourced(2, "Part 2", 15.0, 20, 10, 4, "Company 1");
            Part p3 = new InHouse(3, "Part 3", 16.5, 25, 4, 3, 1003);
            ObservableList<Part> ol1 = FXCollections.observableArrayList(p3);
            Product pro1 = new Product(1, "Product 1", 123.4, 3, 1, 2);
            
            inventory.addPart(p1);
            inventory.addPart(p2);
            inventory.addPart(p3);
            inventory.addProduct(pro1);

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
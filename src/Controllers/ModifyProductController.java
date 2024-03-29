/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import c482.Inventory;
import c482.Part;
import c482.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author A Suzuki
 */
public class ModifyProductController implements Initializable {
    @FXML private TextField id;
    @FXML private TextField name;
    @FXML private TextField inv;
    @FXML private TextField price;
    @FXML private TextField max;
    @FXML private TextField min;
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> partsId;
    @FXML private TableColumn<Part, String> partsName;
    @FXML private TableColumn<Part, Integer> partsInventoryLevel;
    @FXML private TableColumn<Part, Double> partsPricePerUnit;
    @FXML private TableView<Part> assocTable;
    @FXML private TableColumn<Part, Integer> assocId;
    @FXML private TableColumn<Part, String> assocName;
    @FXML private TableColumn<Part, Integer> assocInventoryLevel;
    @FXML private TableColumn<Part, Double> assocPricePerUnit;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    @FXML private Button deleteButton;
    @FXML private Button searchButton;
    @FXML private TextField search;
    @FXML private Button addButton;
    
    Inventory inventory = new Inventory();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //bind assocTable
        assocId.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        partsTable.setItems(inventory.getAllParts());
        
    }    

    @FXML
    private void cancelButtonOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/MainScreen.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Stage oldStage = (Stage) cancelButton.getScene().getWindow();
        oldStage.close();
        
        Stage stage = new Stage();
        Scene scene = new Scene(loader.getRoot());
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();        
    }
    
    @FXML
    private void addButtonOnAction(ActionEvent event) throws IOException {
        ObservableList<Part> assocPart = FXCollections.observableArrayList();
        assocPart.addAll(assocTable.getItems());
        
        boolean addFlag = true;
        for(Part p : assocTable.getItems()){
            if(p.getId() == partsTable.getSelectionModel().getSelectedItem().getId()){
                addFlag = false;
                break;
            }
        }
        if(addFlag){
            assocPart.add(partsTable.getSelectionModel().getSelectedItem());
            assocTable.setItems(assocPart);
        }
    }
    @FXML
    private void saveButtonOnAction(ActionEvent event) throws IOException {
        int assocIdArg;
        int assocInvArg;
        double assocPriceArg;
        int assocMinArg;
        int assocMaxArg;
        try {
            assocIdArg = Integer.parseInt(id.getText());
        } catch (NumberFormatException e) {
            assocIdArg = 0;
        }
        try{
            assocInvArg = Integer.parseInt(inv.getText());
        } catch (NumberFormatException e) {
            assocInvArg = 0;
        }
        try{
            assocPriceArg = Double.parseDouble(price.getText());
        } catch (NumberFormatException e) {
            assocPriceArg = 0.0;
        }
        try{
            assocMinArg = Integer.parseInt(min.getText());
        } catch (NumberFormatException e){
            assocMinArg = 0;
        }
        try {
            assocMaxArg = Integer.parseInt(max.getText());
        } catch (NumberFormatException e) {
            assocMaxArg = 0;
        }
        if(assocInvArg < assocMinArg || assocInvArg > assocMaxArg){
            FXMLLoader popLoader = new FXMLLoader();
            popLoader.setLocation(getClass().getResource("/View/popup.fxml"));
            popLoader.load();
            
            PopupController popCon = popLoader.getController();
            popCon.setMessage("You have to set inventory between min and max.");
            Stage popUp = new Stage();
            Scene popScene = new Scene(popLoader.getRoot());
            popUp.setScene(popScene);
            popUp.setTitle("Error");
            popUp.show();
        } else if(assocPriceArg < assocTable.getItems().stream().map(a->a.getPrice()).reduce(0.0, (a,b)->a+b)){
            FXMLLoader popLoader = new FXMLLoader();
            popLoader.setLocation(getClass().getResource("/View/popup.fxml"));
            popLoader.load();
            
            PopupController popCon = popLoader.getController();
            popCon.setMessage("You have to set product's price above the total price of parts.");
            Stage popUp = new Stage();
            Scene popScene = new Scene(popLoader.getRoot());
            popUp.setScene(popScene);
            popUp.setTitle("Error");
            popUp.show();
        } else {
            Product newProduct = new Product(assocIdArg, name.getText(), assocPriceArg,
                assocInvArg, assocMinArg, assocMaxArg);
            for(Part p : assocTable.getItems()){
                newProduct.addAssociatedPart(p);
            }
            int index = -1;
            int i = 0;
            for(Product p : inventory.getAllProducts()){
                if(p.getId() == newProduct.getId()){
                    index = i;
                } else {
                    i++;
                }
            }
            inventory.updateProduct(index, newProduct);

            Stage oldStage = (Stage) cancelButton.getScene().getWindow();
            oldStage.close();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/MainScreen.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Stage stage = new Stage();
            stage.setTitle("Main");
            Scene scene = new Scene(loader.getRoot());
            stage.setScene(scene);
            stage.show();    
        }
    }

    @FXML
    private void deleteButtonOnAction(ActionEvent event) {
        assocTable.getItems().remove(assocTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void searchButtonOnAction(ActionEvent event) {
        int searchId;
        String searchName;
        try{
            searchId = Integer.parseInt(search.getText());
            partsTable.setItems(FXCollections.observableArrayList(inventory.lookupPart(searchId)));
        } catch (NumberFormatException e) {
            searchName = search.getText();
            if(searchName.equals("")){
                partsTable.setItems(inventory.getAllParts());
            } else {
                partsTable.setItems(inventory.lookupPart(searchName));
            }
        }  
    }
    
    public void sendProduct(Product product){
        id.setText(String.valueOf(product.getId()));
        name.setText(product.getName());
        inv.setText(String.valueOf(product.getStock()));
        price.setText(String.valueOf(product.getPrice()));
        min.setText(String.valueOf(product.getMin()));
        max.setText(String.valueOf(product.getMax()));
        assocTable.setItems(product.getAllAssociatedParts());     
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import c482.InputCheck;
import c482.Inventory;
import c482.Part;
import c482.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    private void cancelButtonOnAction(ActionEvent event) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();        
        //Close current window
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
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

        assocIdArg = InputCheck.inputIntChk(id.getText());
        assocPriceArg = InputCheck.inputDoubleChk(price.getText());
        assocInvArg = InputCheck.inputIntChk(inv.getText());
        assocMinArg = InputCheck.inputIntChk(min.getText());
        assocMaxArg = InputCheck.inputIntChk(max.getText());
        
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
            popUp.showAndWait();
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
            popUp.showAndWait();
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
            
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Main");
            stage.setScene(scene);
            stage.show();
            //Close current window
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
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

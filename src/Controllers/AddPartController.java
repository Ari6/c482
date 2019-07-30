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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author A Suzuki
 */
public class AddPartController implements Initializable {
    @FXML private ToggleGroup A;
    @FXML private RadioButton inHouse;
    @FXML private RadioButton outsourced;
    @FXML private TextField partId;
    @FXML private TextField name;
    @FXML private TextField inv;
    @FXML private TextField priceCost;
    @FXML private TextField max;
    @FXML private TextField min;
    @FXML private TextField machineId;
    @FXML private TextField companyName;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    Inventory inventory = new Inventory();
    
    /*
    Radio buttons controll
    */
    @FXML private void inHouseSelect(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddPart.fxml"));
        Parent root = loader.load();
        
        //set partId to inHouse window
        AddPartController addPartController = loader.getController();
        addPartController.setId(partId.getText());
        
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        
        stage.setTitle("Product Add");
        stage.setScene(scene);
        stage.show();
        //close current window
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
    
    @FXML private void outsourcedSelect(ActionEvent event) throws IOException{   
        //set next window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddPartOut.fxml"));
        Parent root = loader.load();
        //set partId to outSourced window
        AddPartController addPartOutController = loader.getController();
        addPartOutController.setId(partId.getText());
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();        
        stage.setTitle("Product Add");
        stage.setScene(scene);
        stage.show();
        //close current window
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
    
    /*
    save button
    */
    @FXML private void saveButtonOnAction(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainScreen.fxml"));
        Parent root = loader.load();
        MainScreenController mainScreenController = loader.getController();
        int partIdArg;
        double priceCostArg;
        int invArg;
        int minArg;
        int maxArg;
        int machineIdArg;
        
        try{
            partIdArg = Integer.parseInt(partId.getText());
        } catch (NumberFormatException e){
            partIdArg = 0;
        }
        try{
            priceCostArg = Double.parseDouble(priceCost.getText());
        } catch (NumberFormatException e) {
            priceCostArg = 0.0;
        }
        try{
            invArg = Integer.parseInt(inv.getText());
        } catch (NumberFormatException e) {
            invArg = 0;
        }
        try{
            minArg = Integer.parseInt(min.getText());
        } catch (NumberFormatException e) {
            minArg = 0;
        }
        try{
            maxArg = Integer.parseInt(max.getText());
        } catch (NumberFormatException e){
            maxArg = 0;
        }

        if(invArg < minArg || invArg > maxArg){
            FXMLLoader popLoader = new FXMLLoader(getClass().getResource("/View/popup.fxml"));
            Parent popRoot = popLoader.load();
            
            PopupController popCon = popLoader.getController();
            popCon.setMessage("You have to set inventory between min and max.");

            Stage popUp = new Stage();
            Scene popScene = new Scene(popRoot);
            popUp.setScene(popScene);
            popUp.setTitle("Error");
            popUp.showAndWait();
        } else {
            if(inHouse.isSelected()){
                try{
                    machineIdArg = Integer.parseInt(machineId.getText());
                } catch (NumberFormatException e){
                    machineIdArg = 0;
                }
                Part addedPart = new InHouse(partIdArg, 
                        name.getText(), priceCostArg, 
                        invArg, minArg, maxArg, machineIdArg);
                inventory.addPart(addedPart);
            } else {
                Part addedPart = new Outsourced(partIdArg, 
                        name.getText(), priceCostArg, 
                        invArg, minArg, maxArg, companyName.getText());
                inventory.addPart(addedPart);
            }

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setTitle("Main");
            stage.setScene(scene);
            stage.show();
            //close current window
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        }
    }
    
    /*
    cancel button
    */
    @FXML private void cancelButtonOnAction(ActionEvent event) throws IOException{
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close(); //close current window

        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Scene scene = new Scene(root);
        
        Stage stage = new Stage();
        stage.setTitle("Main");
        stage.show();
    }
    
    /*
    set ID
    */
    public void setId(int i) {
        partId.setText(String.valueOf(i));
    }
    public void setId(String s){
        partId.setText(s);
    }
    
    /**
     * Initializes the controller class.
     */    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

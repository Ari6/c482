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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ayumu Suzuki
 */
public class ModifyPartGController implements Initializable {
    @FXML private ToggleGroup A;
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourcedRadioButton;
    @FXML private TextField partId;
    @FXML private TextField name;
    @FXML private TextField inv;
    @FXML private TextField priceCost;
    @FXML private TextField max;
    @FXML private TextField min;
    //for In house
    @FXML private TextField machineId;

    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    Inventory inventory = new Inventory();
    
    public void sendPart(Part pt){
        partId.setText(String.valueOf(pt.getId()));
        name.setText(pt.getName());
        inv.setText(String.valueOf(pt.getStock()));
        priceCost.setText(String.valueOf(pt.getPrice()));
        max.setText(String.valueOf(pt.getMax()));
        min.setText(String.valueOf(pt.getMin()));
        machineId.setText(String.valueOf(((InHouse)pt).getMachineId()));
    }
    
    @FXML void inHouseRadioButtonOnAction() throws IOException{
        
    }
    
    @FXML void outsourcedRadioButtonOnAction() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyPartOut.fxml"));
        loader.load();
        
        ModifyPartOutController modifyPartOutController = loader.getController();
        
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
        
        Part partToOutsourced = new Outsourced(partIdArg, name.getText(),
                priceCostArg, invArg, minArg, maxArg, "");
        modifyPartOutController.sendPart(partToOutsourced);
        
        Stage oldStage = (Stage) outsourcedRadioButton.getScene().getWindow();
        oldStage.close();
                
        
        Stage stage = new Stage();
        Scene scene = new Scene(loader.getRoot());
        
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();
        
    }
    @FXML void cancelButtonOnAction() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/MainScreen.fxml"));
        loader.load();
        
        Stage oldStage = (Stage) cancelButton.getScene().getWindow();
        oldStage.close();
                
        
        Stage stage = new Stage();
        Scene scene = new Scene(loader.getRoot());
        
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
    }
    @FXML void saveButtonOnAction() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/MainScreen.fxml"));
        loader.load();
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
        try{
            machineIdArg = Integer.parseInt(machineId.getText());
        } catch (NumberFormatException e){
            machineIdArg = 0;
        }
        if(invArg < minArg || invArg > maxArg){
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
        } else {        
            Part addedPart = new InHouse(partIdArg, 
                    name.getText(), priceCostArg, 
                    invArg, minArg, maxArg, machineIdArg);
            int i = 0;
            int index = -1;
            for(Part p : inventory.getAllParts()){
                if(addedPart.getId() == p.getId()){
                    index = i;
                } else {
                    i++;
                }
            }
            inventory.updatePart(index, addedPart);

            Stage stageOld = (Stage) saveButton.getScene().getWindow();
            stageOld.close();

            Stage stage = new Stage();
            Scene scene = new Scene(loader.getRoot());

            stage.setTitle("Main");
            stage.setScene(scene);
            stage.show();
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

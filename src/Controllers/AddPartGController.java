/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import c482.InHouse;
import c482.Inventory;
import c482.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
 * @author Ayumu Suzuki
 */
public class AddPartGController implements Initializable {
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
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    Inventory inventory = new Inventory();
    
    /**
     * Initializes the controller class.
     */
    
    @FXML private void inHouseSelect() throws IOException{

    } 
    
    @FXML private void saveButtonOnAction() throws IOException{
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
            inventory.addPart(addedPart);

            Stage stageOld = (Stage) saveButton.getScene().getWindow();
            stageOld.close();

            Stage stage = new Stage();
            Scene scene = new Scene(loader.getRoot());

            stage.setTitle("Main");
            stage.setScene(scene);
            stage.show();
        }
    }
    
    @FXML private void cancelButtonOnAction() throws IOException{
        Stage oldStage = (Stage) cancelButton.getScene().getWindow();
        oldStage.close();
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/MainScreen.fxml"));
        loader.load();
        
        Stage stage = new Stage();
        Scene scene = new Scene(loader.getRoot());
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML private void outsourcedSelect() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/AddPartOut.fxml"));
        loader.load();
        AddPartOutController addPartOutController = loader.getController();
        addPartOutController.setId(partId.getText());
        
        Stage stageOld = (Stage) outsourced.getScene().getWindow();
        stageOld.close();
        
        Parent root = loader.getRoot();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        
        stage.setTitle("Product Add");
        stage.setScene(scene);
        stage.show();
    }
    
    public void setId(int i) {
        partId.setText(String.valueOf(i));
    }
    public void setId(String s){
        partId.setText(s);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

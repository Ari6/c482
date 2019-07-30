/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import c482.InHouse;
import c482.InputCheck;
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
public class ModifyPartController implements Initializable {
    @FXML private ToggleGroup A;
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourcedRadioButton;
    @FXML private TextField partId;
    @FXML private TextField name;
    @FXML private TextField inv;
    @FXML private TextField priceCost;
    @FXML private TextField max;
    @FXML private TextField min;
    @FXML private TextField machineId;    //for In house
    @FXML private TextField companyName; // for outsourced
    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    Inventory inventory = new Inventory();
    //private Object addedPart;
    
    public void sendPart(Part pt){
        partId.setText(String.valueOf(pt.getId()));
        name.setText(pt.getName());
        inv.setText(String.valueOf(pt.getStock()));
        priceCost.setText(String.valueOf(pt.getPrice()));
        max.setText(String.valueOf(pt.getMax()));
        min.setText(String.valueOf(pt.getMin()));
        if(pt instanceof InHouse){
            machineId.setText(String.valueOf(((InHouse)pt).getMachineId()));
        } else {
            companyName.setText(((Outsourced)pt).getCompanyName());
        }
    }
    
    @FXML void inHouseRadioButtonOnAction(ActionEvent event) throws IOException{                
        int partIdArg;
        double priceCostArg;
        int invArg;
        int minArg;
        int maxArg;
        
        //Input check
        partIdArg = InputCheck.inputIntChk(partId.getText());
        priceCostArg = InputCheck.inputDoubleChk(priceCost.getText());
        invArg = InputCheck.inputIntChk(inv.getText());
        minArg = InputCheck.inputIntChk(min.getText());
        maxArg = InputCheck.inputIntChk(max.getText());
        
        Part partToInHouse = new InHouse(partIdArg, name.getText(),
                priceCostArg, invArg, minArg, maxArg, 0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyPart.fxml"));
        Parent root = loader.load();

        ModifyPartController MPController = loader.getController();
        MPController.sendPart(partToInHouse);
        sendPart(partToInHouse);
        
        Scene scene = new Scene(root);
        
        Stage stage = new Stage();
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();
        //close current window
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
    
    @FXML void outsourcedRadioButtonOnAction(ActionEvent event) throws IOException{                
        int partIdArg;
        double priceCostArg;
        int invArg;
        int minArg;
        int maxArg;
        int machineIdArg;
        
        //Input check
        partIdArg = InputCheck.inputIntChk(partId.getText());
        priceCostArg = InputCheck.inputDoubleChk(priceCost.getText());
        invArg = InputCheck.inputIntChk(inv.getText());
        minArg = InputCheck.inputIntChk(min.getText());
        maxArg = InputCheck.inputIntChk(max.getText());
        
        Part partToOutsourced = new Outsourced(partIdArg, name.getText(),
                priceCostArg, invArg, minArg, maxArg, "");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyPartOut.fxml"));
        Parent root = loader.load();

        ModifyPartController MPController = loader.getController();
        MPController.sendPart(partToOutsourced);
        Scene scene = new Scene(root);
        
        Stage stage = new Stage();
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();
        //close current window
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        
    }
    @FXML void cancelButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
        //close current window
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
    
    @FXML void saveButtonOnAction(ActionEvent event) throws IOException{        
        int partIdArg;
        double priceCostArg;
        int invArg;
        int minArg;
        int maxArg;
        int machineIdArg;
        Part addedPart;
        
        partIdArg = InputCheck.inputIntChk(partId.getText());
        priceCostArg = InputCheck.inputDoubleChk(priceCost.getText());
        invArg = InputCheck.inputIntChk(inv.getText());
        minArg = InputCheck.inputIntChk(min.getText());
        maxArg = InputCheck.inputIntChk(max.getText());
        machineIdArg = InputCheck.inputIntChk(machineId.getText());
                
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
            popUp.showAndWait();
        } else {
            if(inHouseRadioButton.isSelected()){
                addedPart = new InHouse(partIdArg, 
                        name.getText(), priceCostArg, 
                        invArg, minArg, maxArg, machineIdArg);
                } else {
                    addedPart = new Outsourced(partIdArg, 
                        name.getText(), priceCostArg, 
                        invArg, minArg, maxArg, companyName.getText());
                }
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
            
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Main");
            stage.setScene(scene);
            stage.show();
            //close current window
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
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

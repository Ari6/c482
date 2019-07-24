/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import c482.InHouse;
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
 * @author Ayumu Suzuki <asuzuk2@wgu.edu>
 */
public class ModifyPartOutController implements Initializable {
    @FXML private ToggleGroup A;
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourcedRadioButton;
    @FXML private TextField partId;
    @FXML private TextField name;
    @FXML private TextField inv;
    @FXML private TextField priceCost;
    @FXML private TextField max;
    @FXML private TextField min;
    @FXML private TextField companyName;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    
    @FXML private void saveButtonOnAction() throws IOException {
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

        Part addedPart = new Outsourced(partIdArg, 
                name.getText(), priceCostArg, 
                invArg, minArg, maxArg, companyName.getText());
        int i = 0;
        int index = -1;
        for(Part p : mainScreenController.inventory.getAllParts()){
            if(addedPart.getId() == p.getId()){
                index = i;
            } else {
                i++;
            }
        }
        mainScreenController.inventory.updatePart(index, addedPart);
        mainScreenController.showParts(addedPart);

        Stage stageOld = (Stage) saveButton.getScene().getWindow();
        stageOld.close();
        
        Stage stage = new Stage();
        Scene scene = new Scene(loader.getRoot());
        
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();        
    }
    
    @FXML void inHouseRadioButtonOnAction() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyPartG.fxml"));
        loader.load();
        
        ModifyPartGController modifyPartOutController = loader.getController();
        
        int partIdArg;
        double priceCostArg;
        int invArg;
        int minArg;
        int maxArg;
        
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
        
        Part partToInHouse = new InHouse(partIdArg, name.getText(),
                priceCostArg, invArg, minArg, maxArg, 0);
        modifyPartOutController.sendPart(partToInHouse);
        
        Stage oldStage = (Stage) inHouseRadioButton.getScene().getWindow();
        oldStage.close();
                
        
        Stage stage = new Stage();
        Scene scene = new Scene(loader.getRoot());
        
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();        
    }
    
    @FXML void outsourcedRadioButtonOnAction() throws IOException{

    }
    
    @FXML private void cancelButtonOnAction() throws IOException {
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
    
    public void sendPart(Part pt){
        partId.setText(String.valueOf(pt.getId()));
        name.setText(pt.getName());
        inv.setText(String.valueOf(pt.getStock()));
        priceCost.setText(String.valueOf(pt.getPrice()));
        max.setText(String.valueOf(pt.getMax()));
        min.setText(String.valueOf(pt.getMin()));
        companyName.setText(((Outsourced)pt).getCompanyName());
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

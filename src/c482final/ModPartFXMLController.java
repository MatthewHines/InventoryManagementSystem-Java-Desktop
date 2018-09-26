/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482final;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Matthew
 */
public class ModPartFXMLController implements Initializable {
    
    @FXML
    private javafx.scene.control.Button modPartSaveBtn;
    @FXML
    private javafx.scene.control.Button cancelModPartBtn;
    @FXML
    private javafx.scene.control.RadioButton inHousePartRadioBtn;
    @FXML
    private javafx.scene.control.RadioButton outsourcedPartRadioBtn;
    @FXML
    private javafx.scene.control.TextField idField;
    @FXML
    private javafx.scene.control.TextField nameField;
    @FXML
    private IntTextField invField;
    @FXML
    private DoubleTextField priceField;
    @FXML
    private IntTextField maxField;
    @FXML
    private IntTextField minField;
    @FXML
    private javafx.scene.control.Label srcLbl;
    @FXML
    private javafx.scene.control.TextField companyField;
    @FXML
    private IntTextField machineIdField;
    
    int oldPartID;
    Part newPart;
    
    @FXML
    private void cancelButtonAction(){
        ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.NONE,"Are you sure you want to leave this screen?",ok,no);
        alert.setTitle("Exit Screen - IMS v1.0");
        Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ok) {
                Stage stage = (Stage) cancelModPartBtn.getScene().getWindow();
                stage.fireEvent(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST)); 
            }    
              
    }
    
    @FXML void inHouse(){
        srcLbl.setText("Machine ID");
        companyField.clear();
        machineIdField.clear();
        companyField.setVisible(false);
        machineIdField.setVisible(true);
        inHousePartRadioBtn.selectedProperty().set(true);
                
    }
    
    @FXML void outsourced(){
        srcLbl.setText("Company Name");
        machineIdField.clear();
        companyField.clear();
        machineIdField.setVisible(false);
        companyField.setVisible(true);
        outsourcedPartRadioBtn.selectedProperty().set(true);
    }
    
    @FXML
    private void modPartSave(){
        
        //priceField.setText(Double.toString(Double.parseDouble(priceField.getText())));
        
        if(nameField.getText() != null && !nameField.getText().isEmpty() && invField.getText() != null && !invField.getText().isEmpty() && priceField.getText() != null && !priceField.getText().isEmpty() && minField.getText() != null && !minField.getText().isEmpty() && maxField.getText() != null && !maxField.getText().isEmpty() ){
            if(Integer.parseInt(minField.getText())<=Integer.parseInt(maxField.getText())){
            if (Integer.parseInt(invField.getText())<=Integer.parseInt(maxField.getText()) && Integer.parseInt(invField.getText())>=Integer.parseInt(minField.getText())){    
                if(inHousePartRadioBtn.isSelected() && machineIdField.getText() != null && !machineIdField.getText().isEmpty()){    

                    Inhouse newInhousePart = new Inhouse();
                    newInhousePart.setPartID(newPart.getPartID());
                    newInhousePart.setName(nameField.getText());
                    newInhousePart.setPrice(Double.parseDouble(priceField.getText()));
                    newInhousePart.setInStock(Integer.parseInt(invField.getText()));
                    newInhousePart.setMin(Integer.parseInt(minField.getText()));
                    newInhousePart.setMax(Integer.parseInt(maxField.getText()));
                    newInhousePart.setMachineID(Integer.parseInt(machineIdField.getText()));

                    Inventory.updatePart(oldPartID,newInhousePart);                       

                        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        Alert alert = new Alert(Alert.AlertType.NONE,"Part updated successfully.",ok);
                        alert.setTitle("Modify Part - IMS v1.0");
                        Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ok) {
                                cancelButtonAction();
                            } else {
                                cancelButtonAction();
                            }

                } else if(outsourcedPartRadioBtn.isSelected() && companyField.getText() != null && !companyField.getText().isEmpty()){
                    

                    Outsourced newOutsourcedPart = new Outsourced();
                    newOutsourcedPart.setPartID(newPart.getPartID());
                    newOutsourcedPart.setName(nameField.getText());
                    newOutsourcedPart.setPrice(Double.parseDouble(priceField.getText()));
                    newOutsourcedPart.setInStock(Integer.parseInt(invField.getText()));
                    newOutsourcedPart.setMin(Integer.parseInt(minField.getText()));
                    newOutsourcedPart.setMax(Integer.parseInt(maxField.getText()));
                    newOutsourcedPart.setCompanyName(companyField.getText());    

                    Inventory.updatePart(oldPartID,newOutsourcedPart);

                        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        Alert alert = new Alert(Alert.AlertType.NONE,"Part updated successfully.",ok);
                        alert.setTitle("Modify Part - IMS v1.0");
                        Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ok) {
                                cancelButtonAction();
                            } else {
                                cancelButtonAction();
                            }

                } else {
                    ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    Alert alert = new Alert(Alert.AlertType.NONE,"Unable to update part. Please ensure all fields are populated.",ok);
                    alert.setTitle("Modify Part - IMS v1.0");
                    alert.show();
                }
            }else{
                        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        Alert alert = new Alert(Alert.AlertType.NONE,"Update failed.\nMax inventory must be greater than or equal to min inventory.",ok);
                        alert.setTitle("Modify Part - IMS v1.0");
                        alert.show();
                    }
            
            } else{
                        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        Alert alert = new Alert(Alert.AlertType.NONE,"Update failed.\nMax inventory must be greater than or equal to min inventory.",ok);
                        alert.setTitle("Modify Part - IMS v1.0");
                        alert.show();
                    }
        } else {
            ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.NONE,"Unable to update part. Please ensure all fields are populated.",ok);
            alert.setTitle("Modify Part - IMS v1.0");
            alert.show();
        }
        
    }
    
    public void preFillForm(Part modPart){
        
        oldPartID = modPart.getPartID();
        newPart = modPart;
        
        idField.setText(Integer.toString(modPart.getPartID()));
        nameField.setText(modPart.getName());
        priceField.setText(String.format("%.02f",modPart.getPrice()));
        invField.setText(Integer.toString(modPart.getInStock()));
        maxField.setText(Integer.toString(modPart.getMax()));
        minField.setText(Integer.toString(modPart.getMin()));
        if (modPart instanceof Inhouse) {
            inHouse();
            machineIdField.setText(Integer.toString(((Inhouse) modPart).getMachineID()));
        } else {
            outsourced();
            companyField.setText(((Outsourced) modPart).getCompanyName());
        }
       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
    }    
    
}

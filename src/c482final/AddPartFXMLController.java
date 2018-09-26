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
 *
 * @author Matthew
 */
public class AddPartFXMLController implements Initializable {
    
    @FXML
    private javafx.scene.control.Button addPartSaveBtn;
    @FXML
    private javafx.scene.control.Button cancelAddPartBtn;
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
    
    private int newPartID;

    @FXML
    private void cancelButtonAction(){
        ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.NONE,"Are you sure you want to leave this screen?",ok,no);
        alert.setTitle("Exit Screen - IMS v1.0");
        Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ok) {
                Stage stage = (Stage) cancelAddPartBtn.getScene().getWindow();
                stage.fireEvent(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST)); 
            }
              
    }
    
    
    
    @FXML void inHouse(){
        srcLbl.setText("Machine ID");
        companyField.clear();
        machineIdField.clear();
        companyField.setVisible(false);
        machineIdField.setVisible(true);
                
    }
    
    @FXML void outsourced(){
        srcLbl.setText("Company Name");
        machineIdField.clear();
        companyField.clear();
        machineIdField.setVisible(false);
        companyField.setVisible(true);   
    }
    
    @FXML
    private void addPartSave(){
        
        if(nameField.getText() != null && !nameField.getText().isEmpty() && invField.getText() != null && !invField.getText().isEmpty() && priceField.getText() != null && !priceField.getText().isEmpty() && minField.getText() != null && !minField.getText().isEmpty() && maxField.getText() != null && !maxField.getText().isEmpty() ){
            if(Integer.parseInt(minField.getText())<=Integer.parseInt(maxField.getText())){
                if (Integer.parseInt(invField.getText())<=Integer.parseInt(maxField.getText()) && Integer.parseInt(invField.getText())>=Integer.parseInt(minField.getText())){                
                    if(inHousePartRadioBtn.isSelected() && machineIdField.getText() != null && !machineIdField.getText().isEmpty()){    


                            Inhouse newInhousePart = new Inhouse();
                            newInhousePart.setPartID(Integer.parseInt(idField.getText()));
                            newInhousePart.setName(nameField.getText());
                            newInhousePart.setPrice(Double.parseDouble(priceField.getText()));
                            newInhousePart.setInStock(Integer.parseInt(invField.getText()));
                            newInhousePart.setMin(Integer.parseInt(minField.getText()));
                            newInhousePart.setMax(Integer.parseInt(maxField.getText()));
                            newInhousePart.setMachineID(Integer.parseInt(machineIdField.getText()));

                            Inventory.addPart(newInhousePart);  

                            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                            Alert alert = new Alert(Alert.AlertType.NONE,"Part saved successfully as Part ID "+Integer.toString(newInhousePart.getPartID())+".",yes);
                            alert.setTitle("Add Part - IMS v1.0");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == yes) {
                                cancelButtonAction();
                            } else {
                                cancelButtonAction();
                            }

                        } else if(outsourcedPartRadioBtn.isSelected() && companyField.getText() != null && !companyField.getText().isEmpty()){

                                    Outsourced newOutsourcedPart = new Outsourced();
                                    newOutsourcedPart.setPartID(Integer.parseInt(idField.getText()));
                                    newOutsourcedPart.setName(nameField.getText());
                                    newOutsourcedPart.setPrice(Double.parseDouble(priceField.getText()));
                                    newOutsourcedPart.setInStock(Integer.parseInt(invField.getText()));
                                    newOutsourcedPart.setMin(Integer.parseInt(minField.getText()));
                                    newOutsourcedPart.setMax(Integer.parseInt(maxField.getText()));
                                    newOutsourcedPart.setCompanyName(companyField.getText());

                                    Inventory.addPart(newOutsourcedPart); 

                                    ButtonType yes = new ButtonType("OK", ButtonBar.ButtonData.YES);
                                    Alert alert = new Alert(Alert.AlertType.NONE,"Part saved successfully as Part ID "+Integer.toString(newOutsourcedPart.getPartID())+".",yes);
                                    alert.setTitle("Add Part - IMS v1.0");
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.isPresent() && result.get() == yes) {
                                        cancelButtonAction();
                                    } else {
                                        cancelButtonAction();
                                    }

                        } else{
                            ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                            Alert alert = new Alert(Alert.AlertType.NONE,"Unable to add part. Please ensure all fields are populated.",ok);
                            alert.setTitle("Add Part - IMS v1.0");
                            alert.show();
                        }
                    } else{
                                ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                Alert alert = new Alert(Alert.AlertType.NONE,"Unable to add part.\nInventory must be between Min Stock ("+minField.getText()+") & Max Stock ("+maxField.getText()+").",ok);
                                alert.setTitle("Add Part - IMS v1.0");
                                alert.show();
                    }        
                }   else {
                    ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    Alert alert = new Alert(Alert.AlertType.NONE,"Add part failed.\nMax inventory must be greater than or equal to min inventory.",ok);
                    alert.setTitle("Modify Part - IMS v1.0");
                    alert.show();
                }
        } else {
            ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.NONE,"Unable to add part. Please ensure all fields are populated.",ok);
            alert.setTitle("Add Part - IMS v1.0");
            alert.show();
        }
    }
    
    private void clearForm(){
        idField.clear();
        nameField.clear();
        invField.clear();
        priceField.clear();
        maxField.clear();
        minField.clear();
        companyField.clear();
        machineIdField.clear();
        updateID();
    }
    
    private void updateID(){
        idField.setText(Integer.toString(Inventory.getMaxPartID()+1));
        newPartID = Inventory.getMaxPartID()+1;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clearForm();
    }    
    
    
}

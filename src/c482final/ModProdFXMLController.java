/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482final;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Matthew
 */
public class ModProdFXMLController implements Initializable {

    private Product newProd = new Product();
    private Product modProd;
    
    //Buttons and TextFields
    @FXML private javafx.scene.control.Button cancelModProdBtn;
    @FXML  private javafx.scene.control.Button modProdSaveBtn;
    @FXML private javafx.scene.control.TextField idField;
    @FXML private javafx.scene.control.TextField nameField;
    @FXML private IntTextField invField;
    @FXML private DoubleTextField priceField;
    @FXML private IntTextField maxField;
    @FXML private IntTextField minField;
    @FXML private javafx.scene.control.Button addPartBtn;
    @FXML private javafx.scene.control.Button delPartBtn;
    
    //search buttons and fields
    @FXML private javafx.scene.control.Button unPartSearchBtn;
    @FXML private javafx.scene.control.TextField unPartSearchField;
    @FXML  private javafx.scene.control.Button asPartSearchBtn;
    @FXML private javafx.scene.control.TextField asPartSearchField;
    
    //Unassociated Parts TableView
    @FXML private javafx.scene.control.TableColumn<Part,Integer> unPartIDCol;
    @FXML private javafx.scene.control.TableColumn<Part,String> unPartNameCol;
    @FXML private javafx.scene.control.TableColumn<Part,Integer> unPartInvLvlCol;
    @FXML private javafx.scene.control.TableColumn<Part,Number> unPartPriceCol;
    
    //Associated Parts TableView
    @FXML private javafx.scene.control.TableColumn<Part,Integer> asPartIDCol;
    @FXML private javafx.scene.control.TableColumn<Part,String> asPartNameCol;
    @FXML private javafx.scene.control.TableColumn<Part,Integer> asPartInvLvlCol;
    @FXML private javafx.scene.control.TableColumn<Part,Number> asPartPriceCol;
    
    //TableView and DB
    @FXML private javafx.scene.control.TableView unPartsTable;
    @FXML private ObservableList<Part> unPartsData = FXCollections.observableArrayList(Inventory.allParts);
    @FXML private javafx.scene.control.TableView asPartsTable;
    @FXML private ObservableList<Part> asPartsData = newProd.getAssociatedParts();
    
    //Search function filters for **unassociated** parts
    @FXML private FilteredList<Part> unPSF = new FilteredList<>(unPartsData, p -> true);
    @FXML private SortedList<Part> sortedUnPartsData = new SortedList<>(unPSF);
    
    //Search function filters for **associated** parts
    @FXML private FilteredList<Part> asPSF = new FilteredList<>(asPartsData, p -> true);
    @FXML private SortedList<Part> sortedAsPartsData = new SortedList<>(asPSF);
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buildUnPartsTable();
        buildAsPartsTable();
        
    }    
    
    @FXML
    private void cancelButtonAction(){
        ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.NONE,"Are you sure you want to leave this screen?",ok,no);
        alert.setTitle("Exit Screen - IMS v1.0");
        Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ok) {
                Stage stage = (Stage) cancelModProdBtn.getScene().getWindow();
                stage.fireEvent(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST)); 
            }    
    }
    
    @FXML
    private void modProdSaveBtnAction(){   

        //Adds new Product to table
        if(nameField.getText() != null && !nameField.getText().isEmpty() && invField.getText() != null && !invField.getText().isEmpty() && priceField.getText() != null && !priceField.getText().isEmpty() && minField.getText() != null && !minField.getText().isEmpty() && maxField.getText() != null && !maxField.getText().isEmpty() ){ 
            if(Integer.parseInt(minField.getText())<=Integer.parseInt(maxField.getText())){
                double totalCost = 0;
                for (int i = 0; i < newProd.getAssociatedParts().size(); i++){
                    totalCost = totalCost + newProd.getAssociatedParts().get(i).getPrice();
                }
                if(totalCost <= Double.parseDouble(priceField.getText())){
                        
                    if(newProd.getAssociatedParts().size()>0){
                        
                        if (Integer.parseInt(invField.getText())<=Integer.parseInt(maxField.getText()) && Integer.parseInt(invField.getText())>=Integer.parseInt(minField.getText())){
                        
                            newProd.setProductID(modProd.getProductID());
                            newProd.setName(nameField.getText());
                            newProd.setPrice(Double.parseDouble(priceField.getText()));
                            newProd.setInStock(Integer.parseInt(invField.getText()));
                            newProd.setMin(Integer.parseInt(minField.getText()));
                            newProd.setMax(Integer.parseInt(maxField.getText()));

                            Inventory.removeProduct(modProd);
                            Inventory.addProduct(newProd);

                            ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.YES);
                            Alert alert = new Alert(Alert.AlertType.NONE,"Product successfully updated.",ok);
                            alert.setTitle("Modify Product - IMS v1.0");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ok) {
                                cancelButtonAction();
                            } else {
                                cancelButtonAction();
                            }
                        } else{
                            ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                            Alert alert = new Alert(Alert.AlertType.NONE,"Unable to modify product.\nInventory must be between Min Stock ("+minField.getText()+") & Max Stock ("+maxField.getText()+").",ok);
                            alert.setTitle("Add Product - IMS v1.0");
                            alert.show();
                        }
                        
                    } else{
                        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        Alert alert = new Alert(Alert.AlertType.NONE,"Unable to modify product.\nAt least one part must be associated with the product to save.\nPlease add a part before saving the product.",ok);
                        alert.setTitle("Add Product - IMS v1.0");
                        alert.show();
                    }    
                        
                } else{
                    ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    Alert alert = new Alert(Alert.AlertType.NONE,"Unable to modify product.\nPrice must be greater than or equal to total cost of associated parts.\nAssociated parts total "+String.format("$%.2f", totalCost)+".",ok);
                    alert.setTitle("Modify Product - IMS v1.0");
                    alert.show();
                }
                
                
            } else{
                ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.NONE,"Modify product failed.\nMax inventory must be greater than or equal to min inventory.",ok);
                alert.setTitle("Modify Product - IMS v1.0");
                alert.show();
            }

        } else {
            ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.NONE,"Unable to modify product. Please ensure all fields are populated.",ok);
            alert.setTitle("Modify Product - IMS v1.0");
            alert.show();
        }

    }
    
    @FXML
    public void buildUnPartsTable(){
        
        unPartIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        unPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        unPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        unPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        unPartsTable.setItems(sortedUnPartsData);
        sortedUnPartsData.comparatorProperty().bind(unPartsTable.comparatorProperty());
        unPartsTable.getSelectionModel().selectFirst();
        
        unPartPriceCol.setCellFactory((TableColumn<Part, Number> col) -> 
            new TableCell<Part, Number>() {
                @Override 
                public void updateItem(Number price, boolean empty) {
                    super.updateItem(price, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(String.format("$%.2f", price.doubleValue()));
                    }
                }
            });
        
        
      }
    
    @FXML
    private void buildAsPartsTable(){
        
        //asPartsData = newProd.getAssociatedParts();
        
        asPartIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        asPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        asPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        asPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        asPartsTable.setItems(sortedAsPartsData);
        sortedAsPartsData.comparatorProperty().bind(asPartsTable.comparatorProperty());
        asPartsTable.getSelectionModel().selectFirst();
        
        asPartPriceCol.setCellFactory((TableColumn<Part, Number> col) -> 
            new TableCell<Part, Number>() {
                @Override 
                public void updateItem(Number price, boolean empty) {
                    super.updateItem(price, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(String.format("$%.2f", price.doubleValue()));
                    }
                }
            });
    }
    
    @FXML
    private void addPartBtnAction(){
        if (unPartsTable.getSelectionModel().getSelectedItem() != null){
            TablePosition pos = (TablePosition) unPartsTable.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            Part selected = sortedUnPartsData.get(row);
            unPartsData.remove(selected);
            newProd.addAssociatedPart(selected);
        }
        
    }
    
    @FXML
    private void delPartBtnAction(){
        if (asPartsTable.getSelectionModel().getSelectedItem() != null){
            TablePosition pos = (TablePosition) asPartsTable.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            Part selected = sortedAsPartsData.get(row);
            unPartsData.add(selected);
            newProd.remAssociatedPart(selected);
        }
    }
    
    private void removeDuplicateParts(){
        
        for(int i = 0; i < asPartsData.size(); i++) {
            for(int j = 0; j < unPartsData.size(); j++){
                if(unPartsData.get(j).getPartID() == asPartsData.get(i).getPartID()){
                    unPartsData.remove(unPartsData.get(j));
                }
            }
        }
    }
    
    public void preFillForm(Product oldProd){
        
        modProd = oldProd;
        
        for(int i = 0; i < oldProd.getAssociatedParts().size(); i++) {
            newProd.addAssociatedPart(oldProd.getAssociatedParts().get(i));
        }

        idField.setText(Integer.toString(modProd.getProductID()));
        nameField.setText(oldProd.getName());
        priceField.setText(String.format("%.02f",modProd.getPrice()));
        invField.setText(Integer.toString(modProd.getInStock()));
        maxField.setText(Integer.toString(modProd.getMax()));
        minField.setText(Integer.toString(modProd.getMin()));
        removeDuplicateParts();
       
    }
    
    @FXML
    private void unPartSearchBtnAction(){
        StringProperty newValue = unPartSearchField.textProperty();
        unPSF.setPredicate(part -> {
            if (newValue.getValue() == null || newValue.getValue().isEmpty()) {
                return true;
            }
            String lcf = newValue.getValue();
            if (String.valueOf(part.getPartID()).toLowerCase().contains(lcf)) {
                return true;
            } else if (String.valueOf(part.getName()).toLowerCase().contains(lcf)) {
                return true; 
            } else if (String.valueOf(part.getInStock()).toLowerCase().contains(lcf)){
                return true;
            } else if (String.valueOf(part.getPrice()).toLowerCase().contains(lcf)){
                return true;
            }
            return false; 
        });
 
        sortedUnPartsData = new SortedList<>(unPSF);
        sortedUnPartsData.comparatorProperty().bind(unPartsTable.comparatorProperty());
    }
    
    @FXML
    private void asPartSearchBtnAction(){
        StringProperty newValue = asPartSearchField.textProperty();
        asPSF.setPredicate(part -> {
            if (newValue.getValue() == null || newValue.getValue().isEmpty()) {
                return true;
            }
            String lcf = newValue.getValue();
            if (String.valueOf(part.getPartID()).toLowerCase().contains(lcf)) {
                return true;
            } else if (String.valueOf(part.getName()).toLowerCase().contains(lcf)) {
                return true; 
            } else if (String.valueOf(part.getInStock()).toLowerCase().contains(lcf)){
                return true;
            } else if (String.valueOf(part.getPrice()).toLowerCase().contains(lcf)){
                return true;
            }
            return false; 
        });
 
        sortedAsPartsData = new SortedList<>(asPSF);
        sortedAsPartsData.comparatorProperty().bind(asPartsTable.comparatorProperty());
    }
    
}

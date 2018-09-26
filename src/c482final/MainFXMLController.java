/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482final;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Matthew
 */
public class MainFXMLController implements Initializable {
    
    //Main Form buttons
    @FXML private javafx.scene.control.Button btnExit;
    @FXML private javafx.scene.control.Button addPartBtn;
    @FXML private javafx.scene.control.Button addProdBtn;
    @FXML private javafx.scene.control.Button modPartBtn;
    @FXML private javafx.scene.control.Button modProdBtn;
    @FXML private javafx.scene.control.Button delPartBtn;
    @FXML private javafx.scene.control.Button delProdBtn;
    @FXML private javafx.scene.control.Button partSearchBtn;
    @FXML private javafx.scene.control.Button prodSearchBtn;
    
    //Search Fields
    @FXML private javafx.scene.control.TextField partSearchField;
    @FXML private javafx.scene.control.TextField prodSearchField;
    
    //Parts TableView
    @FXML private javafx.scene.control.TableColumn<Part,Integer> partIDCol;
    @FXML private javafx.scene.control.TableColumn<Part,String> partNameCol;
    @FXML private javafx.scene.control.TableColumn<Part,Integer> partInvLvlCol;
    @FXML private javafx.scene.control.TableColumn<Part,Number> partPriceCol;
    
    //Products TableView
    @FXML private javafx.scene.control.TableColumn<Product,Integer> prodIDCol;
    @FXML private javafx.scene.control.TableColumn<Product,String> prodNameCol;
    @FXML private javafx.scene.control.TableColumn<Product,Integer> prodInvLvlCol;
    @FXML private javafx.scene.control.TableColumn<Product,Number> prodPriceCol;

    //TableView and DB
    @FXML private javafx.scene.control.TableView partsTable;
    @FXML private ObservableList<Part> partsData = Inventory.allParts;
    @FXML private javafx.scene.control.TableView productsTable;
    @FXML private ObservableList<Product> productsData = Inventory.products;
    
    //Search function filters
    @FXML private FilteredList<Part> partSearchFilter = new FilteredList<>(partsData, p -> true);
    @FXML private SortedList<Part> sortedPartData = new SortedList<>(partSearchFilter);
    @FXML private FilteredList<Product> prodSearchFilter = new FilteredList<>(productsData, p -> true);
    @FXML private SortedList<Product> sortedProdData = new SortedList<>(prodSearchFilter);

    public void buildPartsTable(){

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(sortedPartData);
        sortedPartData.comparatorProperty().bind(partsTable.comparatorProperty());
        partsTable.getSelectionModel().selectFirst();
        
        partPriceCol.setCellFactory((TableColumn<Part, Number> col) -> 
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
    
    public void buildProductsTable(){

        prodIDCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsTable.setItems(sortedProdData);
        sortedProdData.comparatorProperty().bind(productsTable.comparatorProperty());
        productsTable.getSelectionModel().selectFirst();

        prodPriceCol.setCellFactory((TableColumn<Product, Number> col) -> 
            new TableCell<Product, Number>() {
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
    private void deletePartButtonAction(){
        
        TablePosition pos = (TablePosition) partsTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        
        Part partToDelete = sortedPartData.get(row);

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.NONE,"Are you sure you want to delete Part ID "+Integer.toString(partToDelete.getPartID())+"?",yes,no);
        alert.setTitle("Delete Part - IMS v1.0");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yes) {
            if (Inventory.deletePart(partToDelete) == true){
                
                ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert = new Alert(Alert.AlertType.NONE,"Part successfully deleted.",ok);
                alert.setTitle("Delete Part - IMS v1.0");
                alert.show();
                
            } else{
                
                ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert = new Alert(Alert.AlertType.NONE,"Part could not be deleted.",ok);
                alert.setTitle("Delete Part - IMS v1.0");
                alert.show();
            }
        }

 
    }
    
    @FXML
    private void deleteProdButtonAction(){
        
        TablePosition pos = (TablePosition) productsTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        
        Product prodToDelete = sortedProdData.get(row);

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.NONE,"Are you sure you want to delete Part ID "+Integer.toString(prodToDelete.getProductID())+"?",yes,no);
        alert.setTitle("Delete Product - IMS v1.0");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yes) {
            if(prodToDelete.getAssociatedParts().size()>0){
                
                ButtonType yes1 = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancel = new ButtonType("Cancel",ButtonBar.ButtonData.NO);
                alert = new Alert(Alert.AlertType.NONE,"The selected product has "+prodToDelete.getAssociatedParts().size()+" associated part(s). Are you sure you want to delete the product? The associations will be lost.",yes1,cancel);
                alert.setTitle("Delete Product - IMS v1.0");
                result = alert.showAndWait();
                if (result.isPresent() && result.get() == yes1) {
                    if (Inventory.removeProduct(prodToDelete) == true){

                    ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert = new Alert(Alert.AlertType.NONE,"Part successfully deleted.",ok);
                    alert.setTitle("Delete Product - IMS v1.0");
                    alert.show();

                    } else{

                        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        alert = new Alert(Alert.AlertType.NONE,"Part could not be deleted.",ok);
                        alert.setTitle("Delete Product - IMS v1.0");
                        alert.show();
                    }
                } 
            } else {
                ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancel = new ButtonType("Cancel",ButtonBar.ButtonData.NO);
                alert = new Alert(Alert.AlertType.NONE,"The selected product has no associated parts. Are you sure you want to delete the product?",ok,cancel);
                alert.setTitle("Delete Product - IMS v1.0");
                result = alert.showAndWait();
                if (result.isPresent() && result.get() == ok) {
                    if (Inventory.removeProduct(prodToDelete) == true){

                        ButtonType ok1 = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        alert = new Alert(Alert.AlertType.NONE,"Part successfully deleted.",ok1);
                        alert.setTitle("Delete Product - IMS v1.0");
                        alert.show();

                    } else{

                        ButtonType ok1 = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        alert = new Alert(Alert.AlertType.NONE,"Part could not be deleted.",ok1);
                        alert.setTitle("Delete Product - IMS v1.0");
                        alert.show();
                    }
                }
            }
        }
    }

    //application exit check
    @FXML
    private void closeButtonAction() {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        Alert alert = new Alert(AlertType.NONE,"Are you sure you want to exit the application?",yes,no);
        alert.setTitle("Exit IMS v1.0");

        alert.showAndWait()
            .filter(response -> response == yes)
            .ifPresent(response -> Platform.exit());
        
    }
    
    //screen callers
    @FXML
    private void showAddPartScreen() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("AddPartFXML.fxml"));
        showNewStage(root);
    }
    
    @FXML
    private void showModPartScreen() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModPartFXML.fxml"));
        Parent root = (Parent) loader.load();
        ModPartFXMLController modCtrl = loader.getController();
        
        TablePosition pos = (TablePosition) partsTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        Part selected = sortedPartData.get(row);
        
        modCtrl.preFillForm(selected);
        
        Stage parentStage = (Stage) addPartBtn.getScene().getWindow();
        Stage childStage = new Stage();
        Scene scene = new Scene(root);
        
        
        parentStage.hide();
        childStage.setScene(scene);
        childStage.setTitle("Inventory Management System - Version 1.0");
        childStage.show();
        childStage.setResizable(false);
        returnToMain(parentStage,childStage);
    }
    
    @FXML
    private void showAddProdScreen() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("AddProdFXML.fxml"));
        showNewStage(root);
    }
    
    @FXML
    private void showModProdScreen() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModProdFXML.fxml"));
        Parent root = (Parent) loader.load();
        ModProdFXMLController modCtrl = loader.getController();
        
        TablePosition pos = (TablePosition) productsTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        Product selected = sortedProdData.get(row);
        
        modCtrl.preFillForm(selected);
        
        Stage parentStage = (Stage) addProdBtn.getScene().getWindow();
        Stage childStage = new Stage();
        Scene scene = new Scene(root);
        
        
        parentStage.hide();
        childStage.setScene(scene);
        childStage.setTitle("Inventory Management System - Version 1.0");
        childStage.show();
        childStage.setResizable(false);
        returnToMain(parentStage,childStage);
        
        
    }
       
    private void showNewStage(Parent root) {
        Stage parentStage = (Stage) addPartBtn.getScene().getWindow();
        Stage childStage = new Stage();
        Scene scene = new Scene(root);

        parentStage.hide();
        childStage.setScene(scene);
        childStage.setTitle("Inventory Management System - Version 1.0");
        childStage.show();
        childStage.setResizable(false);
        returnToMain(parentStage,childStage);
        
    }
    
    private void returnToMain(Stage parentStage,Stage childStage){
        childStage.setOnCloseRequest((WindowEvent we) -> {
            childStage.close();
            parentStage.show();
            buildPartsTable();
            buildProductsTable();
        });
    }
    
    private void addTestData(){
        Inhouse a = new Inhouse(1, "Test Part 1", 2.50,10, 2, 25, 1071);
        Inhouse b = new Inhouse(2, "Test Part 2", 5.00,100, 30, 250, 1072);
        Inhouse c = new Inhouse(3, "Test Part 3", 25.00,57, 25, 125, 1077);
        Outsourced d = new Outsourced(4, "Test Part 4", 25.00,55, 25, 120, "Orlando Bloom's Cousin");
        Outsourced e = new Outsourced(5, "Test Part 5", 25.00,92, 23, 100, "Large Mutant Worm Co.");
        
        Inventory.addPart(a);
        Inventory.addPart(b);
        Inventory.addPart(c);
        Inventory.addPart(d);
        Inventory.addPart(e);
        
        ObservableList<Part> product2Parts = FXCollections.observableArrayList();
        product2Parts.add(a);
        product2Parts.add(b);
        product2Parts.add(c);
        
        ObservableList<Part> product3Parts = FXCollections.observableArrayList();
        product3Parts.add(b);
        product3Parts.add(e);
        
        Product f = new Product(1, "Test Product 1", 2.50,10, 2, 25);
        Product g = new Product(2, "Product w/ Parts", 5.00,100, 30, 250, product2Parts);
        Product h = new Product(3, "Product w/ Parts 2", 25.00,55, 25, 125, product3Parts);
        
        
        Inventory.addProduct(f);
        Inventory.addProduct(g);
        Inventory.addProduct(h);
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addTestData();
        buildPartsTable();
        buildProductsTable();
    }    
    
    @FXML
    private void partSearchBtnAction(){

        StringProperty newValue = partSearchField.textProperty();
        partSearchFilter.setPredicate(part -> {
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
 
        sortedPartData = new SortedList<>(partSearchFilter);
        sortedPartData.comparatorProperty().bind(partsTable.comparatorProperty());
        //partsTable.setItems(sortedPartData);
    }
    
    @FXML
    private void prodSearchBtnAction(){
        StringProperty newValue = prodSearchField.textProperty();
        prodSearchFilter.setPredicate(prod -> {
            if (newValue.getValue() == null || newValue.getValue().isEmpty()) {
                return true;
            }
            String lcf = newValue.getValue();
            if (String.valueOf(prod.getProductID()).toLowerCase().contains(lcf)) {
                return true;
            } else if (String.valueOf(prod.getName()).toLowerCase().contains(lcf)) {
                return true; 
            } else if (String.valueOf(prod.getInStock()).toLowerCase().contains(lcf)){
                return true;
            } else if (String.valueOf(prod.getPrice()).toLowerCase().contains(lcf)){
                return true;
            }
            return false; 
        });
 
        sortedProdData = new SortedList<>(prodSearchFilter);
        sortedProdData.comparatorProperty().bind(productsTable.comparatorProperty());
        //productsTable.setItems(sortedProdData);
    }

    
    
}

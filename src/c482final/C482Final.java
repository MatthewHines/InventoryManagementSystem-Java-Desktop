/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482final;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Matthew
 */
public class C482Final extends Application {
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Inventory inv = new Inventory();
        
        Parent root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Inventory Management System - Version 1.0");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

package c482final;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

//@author Matthew

public class DoubleTextField extends TextField {
    int maxLength;
    
    public DoubleTextField(){
        super();
        this.maxLength = 7;
        textProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1){
                if (!s1.matches("\\d{0,"+Integer.toString(maxLength)+"}([\\.]\\d{0,2})?")){
                    setText(s);
                }
            }
        });        
    }
    
    public int getMaxLength(){
        return maxLength;
    }
    
    public void setMaxLength(int newMax){
        this.maxLength = newMax;
    }
    
}

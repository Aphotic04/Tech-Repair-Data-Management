/* 
I certify, that this computer program submitted by me is of my own work. Signed: Ty Steinbach
Ty Steinbach
06/26/2024
CSC 322
Final Project
Create a program to handle a database for a business's customers and their transactions
using JavaFX recursion, and many more new learned subjects
Sources:
https://www.baeldung.com/java-string-to-date#:~:text=LocalDate%20date%20%3D%20LocalDate.parse(,LocalDateTime%20objects%20are%20timezone%20agnostic.
https://stackoverflow.com/questions/56874422/parse-data-between-the-brackets-from-a-string-java
https://coderanch.com/t/672505/java/ArrayList-Subclass-Methods
https://stackoverflow.com/questions/36629522/convert-arraylist-to-observable-list-for-javafx-program
https://stackoverflow.com/questions/17388866/getting-selected-item-from-a-javafx-tableview
https://stackoverflow.com/questions/42381290/javafx-editable-tablecell-for-double-property
https://stackoverflow.com/questions/36657299/how-can-i-populate-a-listview-in-javafx-using-custom-objects
https://stackoverflow.com/questions/67251492/serialization-with-inheritance
https://docs.oracle.com/javafx/2/ui_controls/table-view.htm
*/
package csc322steinbachfinalproject;
//Importing stuff
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Cleaning class
public class Cleaning extends Item implements Serializable{
    //Member variables
    private final double COMPUTER_CLEANING = 80;
    private final double LAPTOP_CLEANING = 80;
    private final double MOBILE_CLEANING = 40;
    private final double CONTROLLER_CLEANING = 20;
    private transient StringProperty itemSubType;
    private final String itemType = "Cleaning";
    
    //Constructor
    public Cleaning() {
        initializePropertiesSub();
        itemSubType.set("");
    }
    
    //Initializes properties seperately
    private void initializePropertiesSub() {
        itemSubType = new SimpleStringProperty();
    }
    
    //WriteObject override for customer serialization
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(getItemSubType());
    }

    // Custom deserialization logic
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        initializePropertiesSub();
        setItemSubType(in.readUTF());
    }
    
    //Switch to decide subtype
    @Override
    void setPrices(String itemType, double tempPrice, double tempHourly) {
        setItemSubType(itemType);
        switch (getItemSubType().toLowerCase()) {
            case "computer" -> setPrice(COMPUTER_CLEANING);
            case "laptop" -> setPrice(LAPTOP_CLEANING);
            case "mobile" -> setPrice(MOBILE_CLEANING);
            case "controller" -> setPrice(CONTROLLER_CLEANING);
            default -> {
                setPrice(tempPrice);
                setHourlyPrice(tempHourly);
            }
        }
        setTotalPrice();
    }
    
    //Gets Item type
    @Override
    public String getItemType() {
        return itemType;
    }
    
    //Item subtype setter, getter, and property getter
    @Override
    public String getItemSubType() {
        return(itemSubType.get());
    }
    
    @Override
    public void setItemSubType(String itemSubType) {
        this.itemSubType.set(itemSubType);
    }
    
    @Override
    public StringProperty itemSubTypeProperty() {
        return itemSubType;
    }
    
    //How the object prints
    @Override
    public String toString() {
        return "Cleaning";
    }
}

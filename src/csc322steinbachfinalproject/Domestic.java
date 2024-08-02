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


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.Serializable;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
//Domestic class that is derived from customer
public class Domestic extends Customer implements Serializable{
    //Customer member variables
    private StringProperty firstName;
    private StringProperty lastName;
    private static final String CUSTOMER_TYPE = "Domestic";
    private StringProperty primaryName;
    
    //Domesctic constructor
    public Domestic() {
        initializePropertiesSub();
        firstName.set("New");
        lastName.set("New");
        setPrimaryName();
    }
    
    //Initializes properties seperately
    private void initializePropertiesSub() {
        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        primaryName = new SimpleStringProperty();
    }
    
    //writeObject override for serializing
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(getFirstName());
        out.writeUTF(getLastName());
    }

    // Custom deserialization logic
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        initializePropertiesSub();
        setFirstName(in.readUTF());
        setLastName(in.readUTF());
        setPrimaryName();
    }
    
    //Primary name setter, getter, and property getter
    @Override
    public String getPrimaryName() {
        return(primaryName.get());
    }
    
    @Override 
    public void setPrimaryName() {
        primaryName.set(getLastName() + ", " + getFirstName());
    }
    
    @Override
    public StringProperty primaryNameProperty() {
        return(primaryName);
    }
    
    //First name setter, getter, and property getter
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    
    public String getFirstName() {
        return(firstName.get());
    }
    
    public StringProperty firstNameProperty() {
        return firstName;
    }
    
    //Last name setter, getter, and property getter
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    
    public String getLastName() {
        return(lastName.get());
    }
    
    public StringProperty lastNameProperty() {
        return lastName;
    }
    
    //Customer type getter and property getter
    @Override
    public String getCustomerType(){
        return(CUSTOMER_TYPE);
    }
    
    @Override
    public StringProperty customerTypeProperty() {
        return(new SimpleStringProperty(getCustomerType()));
    }
    
    //How the object prints
    @Override
    public String toString() {
        return(super.toString());
    }
}

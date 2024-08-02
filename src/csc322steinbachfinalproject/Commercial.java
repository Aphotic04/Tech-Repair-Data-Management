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
//Importing everything
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Commercial class that extends Customer class
public class Commercial extends Customer implements Serializable{
    //Member variables
    private StringProperty businessName;
    private StringProperty transactorFirst;
    private StringProperty transactorLast;
    private static final String CUSTOMER_TYPE = "Commercial";
    private StringProperty primaryName;
    
    //Commercial constructor
    public Commercial() {
        initializePropertiesSub();
        businessName.set("New");
        transactorFirst.set("");
        transactorLast.set("");
        setPrimaryName();
    }
    
    //Initializes properties seperately
    private void initializePropertiesSub() {
        businessName = new SimpleStringProperty();
        transactorFirst = new SimpleStringProperty();
        transactorLast = new SimpleStringProperty();
        primaryName = new SimpleStringProperty();
    }
    
    //writeObject override for serializing
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(getBusinessName());
        out.writeUTF(getTransactorFirst());
        out.writeUTF(getTransactorLast());
    }

    // Custom deserialization logic
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        initializePropertiesSub();
        setBusinessName(in.readUTF());
        setTransactorFirst(in.readUTF());
        setTransactorLast(in.readUTF());
        setPrimaryName();
    }
    
    //Primary name setter, getter, and property getter
    @Override
    public String getPrimaryName() {
        return primaryName.get();
    }
    
    @Override
    public void setPrimaryName() {
        primaryName.set(getBusinessName());
    }
    
    @Override
    public StringProperty primaryNameProperty() {
        return(primaryName);
    }
    
    //Business name setter, getter, and property getter
    public void setBusinessName(String businessName) {
        this.businessName.set(businessName);
    }
    
    public String getBusinessName() {
        return(businessName.get());
    }
    
    public StringProperty businessNameProperty() {
        return businessName;
    }
    
    //Transactor first name setter, getter, and property getter
    public void setTransactorFirst(String transactorFirst) {
        this.transactorFirst.set(transactorFirst);
    }
    
    public String getTransactorFirst() {
        return(transactorFirst.get());
    }
    
    public StringProperty transactorFirstProperty() {
        return transactorFirst;
    }
    
    //Transactor last name setter, getter, and property getter
    public void setTransactorLast(String transactorLast) {
        this.transactorLast.set(transactorLast);
    }
    
    public String getTransactorLast() {
        return(transactorLast.get());
    }
    
    public StringProperty transactorLastProperty() {
        return transactorLast;
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

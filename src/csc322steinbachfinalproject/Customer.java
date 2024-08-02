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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Customer class
public abstract class Customer implements Serializable{
    //Member variables
    static private long currentId;
    private transient LongProperty customerId;
    private transient StringProperty email;
    private transient LongProperty phoneNumber;
    private transient DoubleProperty discount;
    private transient  ObservableList<Transaction> transactions;
    
    
    //Customer constructor
    public Customer() {
        initializeProperties();
        ++currentId;
        customerId.set(currentId);
        email.set("");
        phoneNumber.set(0L);
        discount.set(0);
    }
    
    //Initializes properties seperately
    protected void initializeProperties() {
        customerId = new SimpleLongProperty();
        email = new SimpleStringProperty();
        phoneNumber = new SimpleLongProperty();
        discount = new SimpleDoubleProperty();
        transactions = FXCollections.observableArrayList(transaction ->
                new Observable[] { //The methods that need to be observed
                    transaction.transactionIdProperty(),
                    transaction.dateTimeTransactionProperty(),
                    transaction.subTotalProperty(),
                    transaction.discountProperty(),
                    transaction.taxProperty(),
                    transaction.totalProperty(),
                    transaction.amountPaidProperty(),
                    transaction.amountDueProperty(),
                    transaction.paymentMethodProperty(),
                    transaction.getItems()
                }
        );
    }
    
    //writeObject override for serializing
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeLong(getCustomerId());
        out.writeUTF(getEmail());
        out.writeLong(getPhoneNumber());
        out.writeDouble(getDiscount());
        ArrayList<Transaction> transactionsArray = new ArrayList<>(getTransactions());
        out.writeObject(transactionsArray);
    }

    // Custom deserialization logic
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        initializeProperties();
        long tempLong = in.readLong();
        setCurrentId(tempLong);
        setCustomerId(tempLong);
        setEmail(in.readUTF());
        setPhoneNumber(in.readLong());
        setDiscount(in.readDouble());
        ArrayList<Transaction> transactionsArray = (ArrayList<Transaction>)in.readObject();
        transactionsArray.forEach(e -> addTransaction(e));
    }
    
    
    //Sets the base Id for initializing
    public static void setBaseId(long id) {
        currentId = id;
    }
    
    //Abstract methods
    public abstract String getPrimaryName();
    
    public abstract StringProperty primaryNameProperty();
    
    public abstract void setPrimaryName();
    
    public abstract String getCustomerType();
    
    public abstract StringProperty customerTypeProperty();
    
    
    //Adds transaction to transactions
    public void addTransaction(Transaction transaction) {
        transaction.setDiscount(getDiscount());
        transactions.add(transaction);
    }
    
    //Sets transaction from transactions
    public void setTransaction(int index, Transaction transaction) {
        transactions.set(index, transaction);
    }
    
    //Removes transaction from transactions
    public void removeTransaction(int index) {
        transactions.remove(index);
    }
    
    //Gets transaction from transactions
    public Transaction getTransaction(int index) {
        return(transactions.get(index));
    }
    
    //Gets the whole dang array list
    public ObservableList<Transaction> getTransactions() {
        return(transactions);
    }
    
    //gets the transaction size
    public int getTransactionsSize() {
        return(transactions.size());
    }
    
    //Sets currentID
    public void setCurrentId(long currentId) {
        Customer.currentId = currentId;
    }
    
    //Current ID setter, getter, and property getter
    public void setCustomerId (long customerId) {
        this.customerId.set(customerId);
    }
    
    public long getCustomerId() {
        return(customerId.get());
    }
    
    public LongProperty customerIdProperty() {
        return customerId;
    }
    
    //Email setter, getter, and property getter
    public void setEmail(String email) {
        this.email.set(email);
    }
    
    
    public String getEmail() {
        return(email.get());
    }
    
    public StringProperty emailProperty() {
        return email;
    }
    
    //Phone number setter, getter, and property getter
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }
    
    public long getPhoneNumber() {
        return(phoneNumber.get());
    }
    
    public LongProperty phoneNumberProperty() {
        return phoneNumber;
    }
    
    //Discount setter, getter, and property getter
    public void setDiscount(double discount) {
        this.discount.set(discount / 100);
    }
    
    public double getDiscount() {
        return(discount.get() * 100);
    }
    
    public DoubleProperty discountProperty() {
        return discount;
    }
    
    //How the object prints
    @Override
    public String toString() {
        return(String.valueOf(getCustomerId()));
    }
}

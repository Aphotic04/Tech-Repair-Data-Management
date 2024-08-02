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

//Imports for ArrayList and for the date and time
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Transaction class
public class Transaction implements Serializable{
    //Transaction member variables
    static private long currentId;
    private double temp;
    private static SimpleDateFormat dateFormat;
    private transient LongProperty transactionId;
    private transient StringProperty dateTimeTransaction;
    private transient DoubleProperty subTotal;
    private transient DoubleProperty discount;
    private transient DoubleProperty tax;
    private transient DoubleProperty total;
    private transient DoubleProperty amountPaid;
    private transient DoubleProperty amountDue;
    private transient StringProperty paymentMethod;
    ObservableList<Item> items;
    
    //Transaction constructor
    public Transaction() {
        
        initializeProperties();
        temp = 0;
        transactionId.set(currentId);
        dateFormat.applyPattern("MM/dd/yy hh:mm:ss");
        dateTimeTransaction.set(dateFormat.format(new Date()));
        subTotal.set(0);
        discount.set(1);
        tax.set(1);
        total.set(0);
        amountPaid.set(0);
        amountDue.set(0);
        paymentMethod.set("NONE");
    }
    
    //Initializes properties seperately
    private void initializeProperties() {
        dateFormat = new SimpleDateFormat();
        transactionId = new SimpleLongProperty();
        dateTimeTransaction = new SimpleStringProperty();
        subTotal = new SimpleDoubleProperty();
        discount = new SimpleDoubleProperty();
        tax = new SimpleDoubleProperty();
        total = new SimpleDoubleProperty();
        amountPaid = new SimpleDoubleProperty();
        amountDue = new SimpleDoubleProperty();
        paymentMethod = new SimpleStringProperty();
        items = FXCollections.observableArrayList(item ->
                new Observable[] { //The methods that need to be observed
                    item.itemSubTypeProperty(),
                    item.descriptionProperty(),
                    item.quantityProperty(),
                    item.priceProperty(),
                    item.hourlyPriceProperty(),
                    item.hoursProperty(),
                    item.totalPriceProperty()
                }
        );
    }
    
    //writeObject override for serializing
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeLong(getTransactionId());
        out.writeUTF(getDateTimeTransaction());
        out.writeDouble(getSubTotal());
        out.writeDouble(getDiscount());
        out.writeDouble(getTax());
        out.writeDouble(getTotal());
        out.writeDouble(getAmountPaid());
        out.writeDouble(getAmountDue());
        out.writeUTF(getPaymentMethod());
        ArrayList<Item> itemsArray = new ArrayList<>(getItems());
        out.writeObject(itemsArray);
    }

    // Custom deserialization logic
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        initializeProperties();
        long tempLong = in.readLong();
        if (tempLong > currentId) {
            setCurrentId(tempLong + 1);
        }
        setTransactionId(tempLong);
        setDateTimeTransaction(in.readUTF());
        setSubTotal(in.readDouble());
        setDiscount(in.readDouble());
        setTax(in.readDouble());
        setTotal(in.readDouble());
        setAmountPaid(in.readDouble());
        setAmountDue(in.readDouble());
        setPaymentMethod(in.readUTF());
        ArrayList<Item> itemsArray = (ArrayList<Item>)in.readObject();
        itemsArray.forEach(e -> addItem(e));
    }
    
    //Adds item to items
    public void addItem(Item item) {
        items.add(item);
    }
    
    //Sets item in items
    public void setItem(int index, Item item) {
        items.set(index, item);
    }
    
    //Removes item from items
    public void removeItem(int index) {
        items.remove(index);
    }
    
    //Gets item from items
    public Item getItem(int index) {
        return(items.get(index));
    }
    
    //Gets the whole dang list
    public ObservableList<Item> getItems() {
        return(items);
    }
    
    //Gets size of items
    public int getItemsSize() {
        return(items.size());
    }
    
    
    public void setCurrentId(long currentId) {
        Transaction.currentId = currentId;
    }
    
    public void incrementCurrentId() {
        ++Transaction.currentId;
    }
    
    //Transaction ID setter, getter, and property getter
    public void setTransactionId(long transactionId) {
        this.transactionId.set(transactionId);
    }
    
    public long getTransactionId() {
        return(transactionId.get());
    }
    
    public LongProperty transactionIdProperty() {
        return transactionId;
    }
    
    //Date/Time setter, getter, and property getter
    public void setDateTimeTransaction(String date){
        dateTimeTransaction.set(date);
    }
    
    public String getDateTimeTransaction() {
        return(dateTimeTransaction.get());
    }
    
    public StringProperty dateTimeTransactionProperty() {
        return dateTimeTransaction;
    }
    
    //Discount setter, getter, and property getter
    public void setDiscount(double discount) {
        this.discount.set(discount/100);
    }
    
    public double getDiscount() {
        return(discount.get()*100);
    }
    
    public DoubleProperty discountProperty() {
        return discount;
    }
    
    //Tax setter, getter, and property getter
    public void setTax(double tax) {
        this.tax.set(tax/100);
    }
    
    public double getTax() {
        return(tax.get()*100);
    }
    
    public DoubleProperty taxProperty() {
        return tax;
    }
    
    //Amount paid setter, getter, and property getter
    public void setAmountPaid(double amountPaid) {
        this.amountPaid.set(amountPaid);
    }
    
    public double getAmountPaid() {
        return(amountPaid.get());
    }
    
    public DoubleProperty amountPaidProperty() {
        return amountPaid;
    }
    
    //Current ID setter based on enum, getter, and property getter
    public void setPaymentMethod(String paymentMethod) {
        
        switch (PaymentMethod.valueOf(paymentMethod)) {
            case CREDIT -> this.paymentMethod.set("CREDIT");
            case DEBIT -> this.paymentMethod.set("DEBIT");
            case CASH -> this.paymentMethod.set("CASH");
            case CHECK -> this.paymentMethod.set("CHECK");
            case DIRECT -> this.paymentMethod.set("DIRECT");
            default -> {
                this.paymentMethod.set("NONE");
            }
        }
    }
    
    public String getPaymentMethod() {
        return(paymentMethod.get());
    }
    
    public StringProperty paymentMethodProperty() {
        return paymentMethod;
    }
        
    //Initializes totals
    public void initializeTotals() {
        temp = 0;
        items.forEach( (n) -> {temp += n.getTotalPrice();});
        setSubTotal(temp);
        setTotal((getSubTotal() * (1 - discount.get())) * (1 + tax.get()));
        setAmountDue(getAmountPaid() - getTotal());
    }
    
    //Subtotal setter, getter, and property getter
    public void setSubTotal(double subTotal) {
        this.subTotal.set(subTotal);
    }
    
    public double getSubTotal() {
        return(subTotal.get());
    }
    
    public DoubleProperty subTotalProperty() {
        return subTotal;
    }
    
    public void setTotal(double total) {
        this.total.set(total);
    }
    
    //Gets total
    public double getTotal() {
        return(total.get());
    }
    
    //Gets total property
    public DoubleProperty totalProperty() {
        return total;
    }
    
    //Amount due setter, getter, and property getter
    public void setAmountDue(double amountDue) {
        this.amountDue.set(amountDue);
    }
    
    //Gets amount due
    public double getAmountDue() {
        return(amountDue.get());
    }
    
    public DoubleProperty amountDueProperty() {
        return amountDue;
    }
    
    //How the object prints
    @Override
    public String toString() {
        return(String.valueOf(transactionId));
    }
}

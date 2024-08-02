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
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
//Item class
public abstract class Item implements Serializable{
    // serialVersionUID for serialization
    private static final long serialVersionUID = 1L;
    
    //Member variables
    private transient StringProperty description;
    private transient IntegerProperty quantity;
    protected transient DoubleProperty price;
    protected transient DoubleProperty hourlyPrice;
    private transient DoubleProperty hours;
    private transient DoubleProperty totalPrice;
    
    //Item constructor
    public Item() {
        initializeProperties();
        description.set("");
        quantity.set(0);
        price.set(0);
        hourlyPrice.set(0);
        hours.set(0);
        totalPrice.set(0);
    }
    
    //Initializes properties seperately
    protected void initializeProperties() {
        description = new SimpleStringProperty();
        quantity = new SimpleIntegerProperty();
        price = new SimpleDoubleProperty();
        hourlyPrice = new SimpleDoubleProperty();
        hours = new SimpleDoubleProperty();
        totalPrice = new SimpleDoubleProperty();
    }
    
    //WriteObject override for customer serialization
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(description.get());
        out.writeInt(quantity.get());
        out.writeDouble(price.get());
        out.writeDouble(hourlyPrice.get());
        out.writeDouble(hours.get());
        out.writeDouble(totalPrice.get());
    }

    // Custom deserialization logic
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        initializeProperties();
        description.set(in.readUTF());
        quantity.set(in.readInt());
        price.set(in.readDouble());
        hourlyPrice.set(in.readDouble());
        hours.set(in.readDouble());
        totalPrice.set(in.readDouble());
    }
    
    //Abstract that makes derived classes require these methods
    abstract void setPrices(String itemType, double tempPrice, double tempHourly);
    abstract String getItemSubType();
    abstract void setItemSubType(String itemSubType);
    abstract StringProperty itemSubTypeProperty();
    abstract String getItemType();
    
    //Price setter, getter, and property getter
    public double getPrice() {
        return(price.get());
    }
    
    public void setPrice(double price) {
        this.price.set(price);
        setTotalPrice();
    }
    
    public DoubleProperty priceProperty() {
        return price;
    }
    
    //Hourly setter, getter, and property getter
    public double getHourlyPrice() {
        return(hourlyPrice.get());
    }
    
    public void setHourlyPrice(double hourlyPrice) {
        this.hourlyPrice.set(hourlyPrice);
        setTotalPrice();
    }
    
    public DoubleProperty hourlyPriceProperty() {
        return hourlyPrice;
    }
    
    //Quantity setter, getter, and property getter
    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
        setTotalPrice();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    //Description setter, getter, and property getter
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }
    
    //Hours setter, getter, and property getter
    public void setHours(double hours) {
        this.hours.set(hours);
        setTotalPrice();
    }
    
    public double getHours() {
        return (hours.get());
    }
    
    public DoubleProperty hoursProperty() {
        return hours;
    }
    
    //Sets the total price based on other member variables
    public void setTotalPrice() {
        totalPrice.set(getQuantity()*((getHours() * getHourlyPrice()) + getPrice()));
    }
    
    //Gets the total price
    public double getTotalPrice() {
        return(totalPrice.get());
    }
    
    //Gets total price property
    public DoubleProperty totalPriceProperty() {
        return totalPrice;
    }
    
    //How items print
    @Override
    public String toString() {
        return Double.toString(getTotalPrice());
    }
}

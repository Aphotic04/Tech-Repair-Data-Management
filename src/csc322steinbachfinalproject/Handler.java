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

//Importing all the neccessary stuff
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

//Main Handler class
public class Handler extends Application{
    //File name
    private final String fileName = "src/Files/customers.dat";
    //ObservableList to hold Customers
    ObservableList<Customer> customers = FXCollections.observableArrayList(customer ->
                new Observable[] { //The methods that need to be observed
                    customer.primaryNameProperty(),
                    customer.customerTypeProperty(),
                    customer.customerIdProperty(),
                    customer.emailProperty(),
                    customer.phoneNumberProperty(),
                    customer.discountProperty(),
                    customer.getTransactions()
                }
    );
    
    @Override // Override the start method in the Application class 
    public void start(Stage primaryStage) throws IOException, FileNotFoundException, ClassNotFoundException {
        //Creates file if necessary, loads up customer, then creates new custom Stage object
        createFile();
        loadCustomers();
        FXMainStage stage = new FXMainStage(customers);
        
        //Saves Customers to file
        stage.setOnCloseRequest(r -> saveCustomers());
    }
    
    //Creates a new file if there is none
    public void createFile() {
        try {
            File myObj = new File(fileName);
            
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
            } 
            
            else {
              System.out.println("File already exists.");
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
      }
    }
    
    //Loads the contacts from file
    private void loadCustomers() throws ClassNotFoundException {
        //Exception handling
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName))) {
                try {
                    //Casts ArrayList from read object
                    ArrayList<Customer> customersArray = (ArrayList<Customer>) input.readObject();
                    customersArray.forEach(e -> customers.add(e));
                    System.out.println("Contacts loaded successfully from file: " + fileName);
                } catch (EOFException e) {
                    // End of file reached
                } catch (IOException e) {
                    System.err.println("IO error while reading file: " + fileName);
                }
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        } catch (IOException e) {
            System.out.println("IO error while reading file: " + fileName);
        } catch (ClassCastException e) {
            System.out.println("Error casting object to ObservableList<Contact>.");
        }
    }
    
    private void saveCustomers() {
        //Exception handling
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            //Creats ArrayList from ObservableList and saves it
            ArrayList<Customer> customersArray = new ArrayList<>(customers);
            output.writeObject(customersArray);
            output.flush();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        } catch (IOException e) {
            System.out.println("IO error while writing file: " + fileName);
        } catch (ClassCastException e) {
            System.out.println("Error casting object to ObservableList<Contact>.");
        }
    }

    //Launches everything; The Big Bang of the project
    public static void main (String [] args) throws FileNotFoundException{
        Application.launch(args);
    }
}

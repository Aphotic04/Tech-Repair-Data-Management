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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

//Stage for editing specific Customer
public class FXCustomerStage extends Stage{
    //Member variables to be references across methods
    Domestic domestic;
    Commercial commercial;
    GridPane gridPane = new GridPane();
    Button updateButton1 = new Button("Update");
    Button updateButton2 = new Button("Update");
    
    //Constructor to auto-call when creating object of this class
    public FXCustomerStage(Customer customer) {
        //Formats GridPane
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        
        //Decides which GridPane to implement depending on Customer type
        if (customer.getCustomerType().equalsIgnoreCase("domestic")) {
            domestic = (Domestic) customer;
            gridPane = getDomesticGridPane();
        }
        else {
            commercial = (Commercial) customer;
            gridPane = getCommercialGridPane();
        }
        
        // Create a scene and place it in the stage 
        Scene scene = new Scene(gridPane);
        this.setTitle("ShowBorderPane"); // Set the stage title 
        this.setScene(scene); // Place the scene in the stage 
        this.show(); // Display the stage 
    }
    
    //Implements, formats, and creates GridPane for Domestic type Customer
    private GridPane getDomesticGridPane() {
        //Labels
        Label customerIdLabel = new Label("Customer ID: " + domestic.getCustomerId());
        Label firstNameLabel = new Label("First Name: ");
        Label lastNameLabel = new Label("Last Name: ");
        Label emailLabel = new Label("Email: ");
        Label phoneNumberLabel = new Label("Phone Number: ");
        Label discountLabel = new Label("Discount: ");
        
        //TextFields
        TextField firstNameTF = new TextField(domestic.getFirstName());
        TextField lastNameTF = new TextField(domestic.getLastName());
        TextField emailTF = new TextField(domestic.getEmail());
        TextField phoneNumberTF = new TextField(String.valueOf(domestic.getPhoneNumber()));
        TextField discountTF = new TextField(Double.toString(domestic.getDiscount()));
        
        //Adding to GridPane
        gridPane.add(customerIdLabel, 0, 0);
        
        gridPane.add(firstNameLabel, 0, 1);
        gridPane.add(firstNameTF, 1, 1);
        gridPane.add(lastNameLabel, 2, 1);
        gridPane.add(lastNameTF, 3, 1);
        
        gridPane.add(emailLabel, 0, 2);
        gridPane.add(emailTF, 1, 2, 2, 1);
        
        gridPane.add(phoneNumberLabel, 0, 3);
        gridPane.add(phoneNumberTF, 1, 3, 2, 1);
        
        gridPane.add(discountLabel, 0, 4);
        gridPane.add(discountTF, 1, 4);
        
        gridPane.add(updateButton1, 3, 5);
        
        //Updates Customer data upon pressing
        updateButton1.setOnAction(e -> { 
            try {
                domestic.setFirstName(firstNameTF.getText());
                domestic.setLastName(lastNameTF.getText());
                domestic.setEmail(emailTF.getText());
                domestic.setPhoneNumber(Long.valueOf(phoneNumberTF.getText()));
                domestic.setDiscount(Double.valueOf(discountTF.getText()));
                domestic.setPrimaryName();
            }
            catch (NumberFormatException ex) {
                System.out.println("Number Format Exception: Incorrect data type");
            }
        });
        
        return(gridPane);
    }
    
    //Implements, formats, and creates GridPane for Commercial type Customer
    private GridPane getCommercialGridPane() {
        //Creates Labels
        Label customerIdLabel = new Label("Customer ID: " + commercial.getCustomerId());
        Label businessNameLabel = new Label("Business Name: ");
        Label transactorFirstLabel = new Label("Transactor First Name: ");
        Label transactorLastLabel = new Label("Transactor Last Name: ");
        Label emailLabel = new Label("Email: ");
        Label phoneNumberLabel = new Label("Phone Number: ");
        Label discountLabel = new Label("Discount: ");
        
        //textFields
        TextField businessNameTF = new TextField(commercial.getBusinessName());
        TextField transactorFirstTF = new TextField(commercial.getTransactorFirst());
        TextField transactorLastTF = new TextField(commercial.getTransactorLast());
        TextField emailTF = new TextField(commercial.getEmail());
        TextField phoneNumberTF = new TextField(String.valueOf(commercial.getPhoneNumber()));
        TextField discountTF = new TextField(Double.toString(commercial.getDiscount()));
        
        //Adding everything to GridPane
        gridPane.add(customerIdLabel, 0, 0);
        
        gridPane.add(businessNameLabel, 0, 1);
        gridPane.add(businessNameTF, 1, 1, 2, 1);
        
        gridPane.add(transactorFirstLabel, 0, 2);
        gridPane.add(transactorFirstTF, 1, 2);
        gridPane.add(transactorLastLabel, 2, 2);
        gridPane.add(transactorLastTF, 3, 2);
        
        gridPane.add(emailLabel, 0, 3);
        gridPane.add(emailTF, 1, 3, 2, 1);
        
        gridPane.add(phoneNumberLabel, 0, 4);
        gridPane.add(phoneNumberTF, 1, 4, 2, 1);
        
        gridPane.add(discountLabel, 0, 5);
        gridPane.add(discountTF, 1, 5);
        
        gridPane.add(updateButton2, 3, 6);
        
        //Updates Customer data upon pressing
        updateButton2.setOnAction(e -> {
            try {
                commercial.setBusinessName(businessNameTF.getText());
                commercial.setTransactorFirst(transactorFirstTF.getText());
                commercial.setTransactorLast(transactorLastTF.getText());
                commercial.setEmail(emailTF.getText());
                commercial.setPhoneNumber(Long.valueOf(phoneNumberTF.getText()));
                commercial.setDiscount(Double.valueOf(discountTF.getText()));
                commercial.setPrimaryName();
            }
            catch (NumberFormatException ex) {
                System.out.println("Number Format Exception: Incorrect data type");
            }
        });
        
        return(gridPane);
    }
}

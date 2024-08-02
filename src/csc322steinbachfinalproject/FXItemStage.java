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
//Importing the fun stuff
import java.text.DecimalFormat;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

//Stage for editing a specific Item
public class FXItemStage extends Stage{
    //Member variables to be referenced acroos all methods
    private Item item;
    private GridPane gridPane = new GridPane();
    private final DecimalFormat df = new DecimalFormat("#.##");
    
    //Constructor to auto-run upon object creation
    public FXItemStage(Item item) {
        //Integrates item and sets up/formats GridPane
        this.item = item;
        gridPane = getGridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        
        // Create a scene and place it in the stage 
        Scene scene = new Scene(gridPane);
        this.setTitle("ShowBorderPane"); // Set the stage title 
        this.setScene(scene); // Place the scene in the stage 
        this.show(); // Display the stage 
    }
    
    //Sets up GridPane
    private GridPane getGridPane() {
        //Sets up the appropriate ComboBox, Label, and TextFields
        ComboBox<String> subTypeCombo = new ComboBox<>();
        Button updateButton = new Button("Update");
        
        Label title = new Label(item.toString());
        
        //Options for ComboBox depending on Item type
        Label subTypeLabel = new Label("Enter " + item.toString() + " type: ");
        switch (item.toString()) {
            case "Repair" -> {
                String[] repairs = {"Computer", "Laptop", "Mobile", "Controller", "Misc"};
                subTypeCombo.getItems().addAll(repairs);
            }
            case "Cleaning" -> {
                String[] cleanings = {"Computer", "Laptop", "Mobile", "Controller", "Misc"};
                subTypeCombo.getItems().addAll(cleanings);
            }
            case "Upgrade" -> {
                String[] upgrades = {"Computer", "Laptop", "Controller", "Misc"};
                subTypeCombo.getItems().addAll(upgrades);
            }
            default -> {
                String[] miscellaneous = {"Locked Access", "Set Up", "Set Up Transfer", 
                    "Laptop Screen Replacement", "Mobile Screen Replacement", 
                "Hard Drive Recovery", "Misc"};
                subTypeCombo.getItems().addAll(miscellaneous);
            }
        }
        subTypeCombo.setValue(item.getItemSubType());
        
        
        
        Label descriptionLabel = new Label("Description: ");
        TextField descriptionText = new TextField(item.getDescription());
        
        Label priceLabel = new Label("Price: ");
        TextField priceText = new TextField(String.valueOf(df.format(item.getPrice())));
        

        Label hourlyLabel = new Label("Hourly price: ");
        TextField hourlyText = new TextField(String.valueOf(df.format(item.getHourlyPrice())));
        
        
        Label hoursLabel = new Label("Hours: ");
        TextField hoursText = new TextField(String.valueOf(df.format(item.getHours())));
        if (!item.toString().equals("Repair")) {
                hoursText.setEditable(false);
        }
        
        if (!subTypeCombo.getValue().equals("Misc")) {
            hourlyText.setEditable(false);
            priceText.setEditable(false);
        }
        
        //Makes certain fields editable depending on the type/subtype
        subTypeCombo.setOnAction(eh -> {
            if (!subTypeCombo.getValue().equals("Misc")) {
                hourlyText.setEditable(false);
                priceText.setEditable(false);
            }
            if (subTypeCombo.getValue().equals("Misc")) {
                priceText.setEditable(true);
                hourlyText.setEditable(true);
                hoursText.setEditable(true);
            }
            
        });
            
        
            
        
        
        
        Label quantityLabel = new Label("Quantity: ");
        TextField quantityText = new TextField(String.valueOf(item.getQuantity()));
        
        Label totalLabel = new Label("Total: ");
        TextField totalText = new TextField(String.valueOf(df.format(item.getTotalPrice())));
        totalText.setEditable(false);
        
        //Adds everything to the GridPane
        gridPane.add(title, 1, 0);
        gridPane.add(subTypeLabel, 0, 1);
        gridPane.add(subTypeCombo, 1, 1);
        gridPane.add(descriptionLabel, 0, 2);
        gridPane.add(descriptionText, 1, 2, 2, 1);
        gridPane.add(priceLabel, 0, 3);
        gridPane.add(priceText,1, 3);
        gridPane.add(hourlyLabel, 0, 4);
        gridPane.add(hourlyText, 1, 4);
        gridPane.add(hoursLabel, 0, 5);
        gridPane.add(hoursText, 1, 5);
        gridPane.add(quantityLabel, 0, 6);
        gridPane.add(quantityText, 1, 6);
        gridPane.add(totalLabel, 0, 7);
        gridPane.add(totalText, 1, 7);
        gridPane.add(updateButton, 1, 8);
        
        //Sets appropriate values upon pressing
        updateButton.setOnAction(e -> { 
            try {
                item.setPrices(subTypeCombo.getValue(), Double.valueOf(priceText.getText()),
                    Double.valueOf(hourlyText.getText()));
                item.setDescription(descriptionText.getText());
                item.setHours(Double.valueOf(hoursText.getText()));
                item.setQuantity(Integer.valueOf(quantityText.getText()));
                this.gridPane = getGridPane();
            }
            catch(NumberFormatException ex) {
                System.out.println("Number Format Exception: Incorrect data type");
            }
        });
        
        return(gridPane);
    }
}
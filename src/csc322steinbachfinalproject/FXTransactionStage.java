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

//Importing the works
import java.text.DecimalFormat;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

//Stage for editing specific Transaction
public class FXTransactionStage extends Stage{
    //All the member variables to be referenced across methods
    private Transaction transaction;
    private GridPane gridPane = new GridPane();
    private TableView<Item> table = new TableView<>();
    private final GridPane lowerGridPane = new GridPane();
    private Button updateButton = new Button("Update");
    private final DecimalFormat df = new DecimalFormat("#.##");
    private final Button newItem = new Button("New");
    private final Button deleteItem = new Button("Delete");
    private TextField dateTimeTransactionTF;
    private TextField transactionIdTF;
    private TextField subTotalTF;
    private TextField taxTF;
    private TextField discountTF;
    private TextField totalTF;
    private TextField amountPaidTF;
    private TextField amountDueTF;
    private TextField paymentMethodTF;
    
    //Constructor to auto-call when creating object of this class
    public FXTransactionStage(Transaction transaction) {
        //Adds Transaction to the member variable, sets up GridPane, TableView, and 
        //lower GridPane
        this.transaction = transaction;
        gridPane.add(new Label("Transaction Items"), 0, 0, 2, 1);
        table = getTableView();
        gridPane.add(table, 0, 1, 2, 1);
        getLowerGridPane();
        
        //Creates a scene and places it in the Stage
        Scene scene = new Scene(gridPane);
        this.setTitle("ShowBorderPane"); // Set the stage title 
        this.setScene(scene); // Place the scene in the stage 
        this.show(); // Display the stage 
    }
    
    //The TableView that displays Items
    private TableView<Item> getTableView() {
        //Allows for the Table to be editable
        table.setEditable(true);
        
        //Item Type column
        TableColumn itemType = new TableColumn("Item Type");
        itemType.setMinWidth(100);
        itemType.setCellValueFactory(
                new PropertyValueFactory<Item, String>("itemType"));
        TableColumn itemSubType = new TableColumn("Item Sub Type");
        itemSubType.setMinWidth(100);
        itemSubType.setCellValueFactory(
                new PropertyValueFactory<Item, String>("itemSubType"));
        //How to set value when editing
        itemSubType.setCellFactory(TextFieldTableCell.forTableColumn());
        itemSubType.setOnEditCommit(
            new EventHandler<CellEditEvent<Item, String>>() {
                @Override
                public void handle(CellEditEvent<Item, String> t) {
                    ((Item) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setPrices(t.getNewValue(),0,0);
                    transaction.initializeTotals();
                    updateTransactionDetails();
                }
            }
        );
        
        //Description column
        TableColumn description = new TableColumn("Description");
        description.setMinWidth(200);
        description.setCellValueFactory(
                new PropertyValueFactory<Item, String>("description"));
        //How to set value on editing
        description.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setOnEditCommit(
            new EventHandler<CellEditEvent<Item, String>>() {
                @Override
                public void handle(CellEditEvent<Item, String> t) {
                    ((Item) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setDescription(t.getNewValue());
                }
            }
        );
        
        //Price column
        TableColumn price = new TableColumn("Price");
        price.setMinWidth(100);
        price.setCellValueFactory(
                new PropertyValueFactory<Item, String>("price"));
        //How to set value when editing
        price.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        price.setOnEditCommit(
            new EventHandler<CellEditEvent<Item, Double>>() {
                @Override
                public void handle(CellEditEvent<Item, Double> t) {
                    //Only allows editable when item SubType is Misc or the Item type is Repair
                    if (((Item) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).getItemSubType().equals("Misc") ||
                            ((Item) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).getItemType().equals("Repair")) {
                        
                        ((Item) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setPrice(Double.valueOf(t.getNewValue()));
                        transaction.initializeTotals();
                        updateTransactionDetails();
                    }
                    else {
                        System.out.println("Only the Misc sub item type and the "
                                + "Repair item type have editable price and hourly price");
                    }
                }
            }
        );
        
        //Hourly price column
        TableColumn hourlyPrice = new TableColumn("Hourly Price");
        hourlyPrice.setMinWidth(100);
        hourlyPrice.setCellValueFactory(
                new PropertyValueFactory<Item, String>("hourlyPrice"));
        //How to set value when editing
        hourlyPrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        hourlyPrice.setOnEditCommit(
            new EventHandler<CellEditEvent<Item, Double>>() {
                @Override
                public void handle(CellEditEvent<Item, Double> t) {
                    //Only allows editable when item SubType is Misc or the Item type is Repair
                    if (((Item) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).getItemSubType().equals("Misc") ||
                            ((Item) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).getItemType().equals("Repair")) {
                        
                        ((Item) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setHourlyPrice(Double.valueOf(t.getNewValue()));
                        transaction.initializeTotals();
                        updateTransactionDetails();
                    }
                    else {
                        System.out.println("Only the Misc sub item type and the "
                                + "Repair item type have editable price and hourly price");
                    }
                }
            }
        );
        
        //Hours column
        TableColumn hours = new TableColumn("Hours");
        hours.setMinWidth(100);
        hours.setCellValueFactory(
                new PropertyValueFactory<Item, String>("hours"));
        //How to set value when editing
        hours.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        hours.setOnEditCommit(
            new EventHandler<CellEditEvent<Item, Double>>() {
                @Override
                public void handle(CellEditEvent<Item, Double> t) {
                    ((Item) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setHours(Double.valueOf(t.getNewValue()));
                    transaction.initializeTotals();
                    updateTransactionDetails();
                }
            }
        );
        
        //Quantity column
        TableColumn quantity = new TableColumn("Quantity");
        quantity.setMinWidth(100);
        quantity.setCellValueFactory(
                new PropertyValueFactory<Item, String>("quantity"));
        //How to set value when editing
        quantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantity.setOnEditCommit(
            new EventHandler<CellEditEvent<Item, Integer>>() {
                @Override
                public void handle(CellEditEvent<Item, Integer> t) {
                    ((Item) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setQuantity(Integer.valueOf(t.getNewValue()));
                    transaction.initializeTotals();
                    updateTransactionDetails();
                }
            }
        );
        
        //Total column
        TableColumn total = new TableColumn("Total");
        total.setMinWidth(100);
        total.setCellValueFactory(
                new PropertyValueFactory<Item, String>("totalPrice"));
 

        //Sets items into TableView and adds columns
        table.setItems(transaction.getItems());
        table.getColumns().addAll(itemType, itemSubType, description, price, 
                hourlyPrice, hours, quantity, total);
        
        //Adds new item on pressing
        newItem.setOnAction(e -> {
            newItemStage newItStage = new newItemStage();
            newItStage.setOnCloseRequest(r -> {
                String newCustomerChoice = newItStage.getComboValue();
                if (newCustomerChoice.equals("Repair")) {
                    transaction.addItem(new Repair());
                }
                else if (newCustomerChoice.equals("Cleaning")) {
                    transaction.addItem(new Cleaning());
                }
                else if (newCustomerChoice.equals("Upgrade")) {
                    transaction.addItem(new Upgrade());
                }
                else if (newCustomerChoice.equals("Misc")) {
                    transaction.addItem(new Misc());
                }
                else {
                    System.out.println("Can't create new Item");
                }
            });
        });
        //Deletes selected item upon pressing
        deleteItem.setOnAction(e -> transaction.removeItem(table.getSelectionModel().getSelectedIndex()));
        
        //Adds the Buttons to GridPane
        lowerGridPane.add(newItem, 4, 0);
        lowerGridPane.add(deleteItem, 5, 0);
        
        
        return(table);
    }
    
    //Sets up lower GridPane (displays transaction info)
    private void getLowerGridPane() {
        //Formatting LowerGridPane
        lowerGridPane.setHgap(10);
        lowerGridPane.setVgap(6);
        lowerGridPane.setPadding(new Insets(10, 10, 10, 10));
        
        //Line for easy readability
        String lineStr = "-----------------------------------------------------"
                + "-----------------------------------------------------------";
        //Labels for the line above
        Label line1 = new Label(lineStr);
        Label line2 = new Label(lineStr);
        Label line3 = new Label(lineStr);
        Label line4 = new Label(lineStr);
        Label line5 = new Label(lineStr);
        Label line6 = new Label(lineStr);
        Label line7 = new Label(lineStr);
        Label line8 = new Label(lineStr);
        Label line9 = new Label(lineStr);
        
        //Labels for Transaction info
        Label dateTimeTransactionLabel = new Label ("Date|Time Transaction: ");
        Label transactionIdLabel = new Label("Transaction ID: ");
        Label subTotalLabel = new Label("Sub Total: ");
        Label taxLabel = new Label("Tax: ");
        Label discountLabel = new Label("Discount: ");
        Label totalLabel = new Label("Total: ");
        Label amountPaidLabel = new Label("Amount Paid: ");
        Label amountDueLabel = new Label("Amount Due: ");
        Label paymentMethodLabel = new Label("Payment Method: ");
        
        //TextFields for Transaction info
        dateTimeTransactionTF = new TextField(transaction.getDateTimeTransaction());
        transactionIdTF = new TextField(String.valueOf(transaction.getTransactionId()));
        subTotalTF = new TextField(String.valueOf(df.format(transaction.getSubTotal())));
        taxTF = new TextField(String.valueOf(df.format(transaction.getTax())));
        discountTF = new TextField(String.valueOf(df.format(transaction.getDiscount())));
        totalTF = new TextField(String.valueOf(df.format(transaction.getTotal())));
        amountPaidTF = new TextField(String.valueOf(df.format(transaction.getAmountPaid())));
        amountDueTF = new TextField(String.valueOf(df.format(transaction.getAmountDue())));
        paymentMethodTF = new TextField(transaction.getPaymentMethod());
                
        //Making apropriate fields uneditable
        transactionIdTF.setEditable(false);
        subTotalTF.setEditable(false);
        totalTF.setEditable(false);
        amountDueTF.setEditable(false);
        
        //Adds it all to the lower GridPane
        lowerGridPane.add(dateTimeTransactionLabel, 0, 0);
        lowerGridPane.add(transactionIdLabel, 0, 1);
        lowerGridPane.add(subTotalLabel, 0, 2);
        lowerGridPane.add(taxLabel, 0, 3);
        lowerGridPane.add(discountLabel, 0, 4);
        lowerGridPane.add(totalLabel, 0, 5);
        lowerGridPane.add(amountPaidLabel, 0, 6);
        lowerGridPane.add(amountDueLabel, 0, 7);
        lowerGridPane.add(paymentMethodLabel, 0, 8);
        
        lowerGridPane.add(line3, 1, 2, 3, 1);
        lowerGridPane.add(line4, 1, 3, 3, 1);
        lowerGridPane.add(line5, 1, 4, 3, 1);
        lowerGridPane.add(line6, 1, 5, 3, 1);
        lowerGridPane.add(line7, 1, 6, 3, 1);
        lowerGridPane.add(line8, 1, 7, 3, 1);
        lowerGridPane.add(line9, 1, 8, 3, 1);

        
        lowerGridPane.add(dateTimeTransactionTF, 1, 0);
        lowerGridPane.add(transactionIdTF, 1, 1);
        lowerGridPane.add(subTotalTF, 5, 2);
        lowerGridPane.add(taxTF, 5, 3);
        lowerGridPane.add(discountTF, 5, 4);
        lowerGridPane.add(totalTF, 5, 5);
        lowerGridPane.add(amountPaidTF, 5, 6);
        lowerGridPane.add(amountDueTF, 5, 7);
        lowerGridPane.add(paymentMethodTF, 5, 8);
        
        lowerGridPane.add(updateButton, 5, 9);
        
        //Updates the Transaction values upon pressing
        updateButton.setOnAction(e -> { 
            try {
                transaction.setDateTimeTransaction(dateTimeTransactionTF.getText());
                transaction.setTax(Double.valueOf(taxTF.getText()));
                transaction.setDiscount(Double.valueOf(discountTF.getText()));
                transaction.setAmountPaid(Double.valueOf(amountPaidTF.getText()));
                if (paymentMethodTF.getText().equalsIgnoreCase("cash")) {
                    transaction.setPaymentMethod("CASH");
                }
                else if (paymentMethodTF.getText().equalsIgnoreCase("credit")) {
                    transaction.setPaymentMethod("CREDIT");
                }
                else if (paymentMethodTF.getText().equalsIgnoreCase("debit")) {
                    transaction.setPaymentMethod("DEBIT");
                }
                else if (paymentMethodTF.getText().equalsIgnoreCase("check")) {
                    transaction.setPaymentMethod("CHECK");
                }
                else if (paymentMethodTF.getText().equalsIgnoreCase("direct")) {
                    transaction.setPaymentMethod("DIRECT");
                }
                else {
                    transaction.setPaymentMethod("NONE");
                }
                transaction.initializeTotals();
                updateTransactionDetails();
            }
            catch(NumberFormatException ex) {
                System.out.println("Number Format Exception: Incorrect data type");
            }
        });
        gridPane.add(lowerGridPane, 0, 2, 2, 1);
    }
    
    //Sets text in TextField to apropriate values
    private void updateTransactionDetails() {
        subTotalTF.setText(String.valueOf(df.format(transaction.getSubTotal())));
        totalTF.setText(String.valueOf(df.format(transaction.getTotal())));
        amountDueTF.setText(String.valueOf(df.format(transaction.getAmountDue())));
    }
}


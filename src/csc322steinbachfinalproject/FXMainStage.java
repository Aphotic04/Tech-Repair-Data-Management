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

//Importing everythanggg
import java.text.DecimalFormat;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Main Stage for FX
public class FXMainStage extends Stage{
    //ListViews for the 3 classes
    private ListView customersLV = new ListView();
    private ListView transactionsLV = new ListView();
    private ListView itemsLV = new ListView();
    //New and Delete buttons
    private final Button newCustomer = new Button("New");
    private final Button deleteCustomer = new Button("Delete");
    private final Button newTransaction = new Button("New");
    private final Button deleteTransaction = new Button("Delete");
    private final Button newItem = new Button("New");
    private final Button deleteItem = new Button("Delete");
    //Search bars and search buttons
    private final Label customerSearchLabel = new Label("Search to edit by Customer ID:");
    private final TextField customerSearchTF = new TextField();
    private final Button searchCustomer = new Button("Search");
    private final Label transactionSearchLabel = new Label("Search to edit by Transaction ID:");
    private final TextField transactionSearchTF = new TextField();
    private final Button searchTransaction = new Button("Search");
    //Customers observable list
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
    
    //Stage constructor (method to auto-run when Object is created)
    public FXMainStage(ObservableList<Customer> customers){
        //Sets local variable to value of parameter
        this.customers = customers;
        //GridPane made from method that creates and formats a GridPane
        GridPane gridPane = gridPane();
        
        //Sets up list view
        customersListView();
        //Sets up search bars/buttons
        searchHandle();
        
        // Create a scene and place it in the stage 
        Scene scene = new Scene(gridPane);
        this.setTitle("ShowBorderPane"); // Set the stage title 
        this.setScene(scene); // Place the scene in the stage 
        this.show(); // Display the stage 
    }
    
    private GridPane gridPane() {
        //Sets up main GridPane and sub GridPanes  that goes in HBox for Buttons and search stuff
        GridPane gridPane = new GridPane();
        GridPane gridPaneLeft = new GridPane();
        GridPane gridPaneMiddle = new GridPane();
        GridPane gridPaneRight = new GridPane();
        HBox hbox = new HBox(50);
        
        //Formats main GridPane
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        
        //Format's HBox
        hbox.setAlignment(Pos.CENTER);
        
        //Format's sub GridPanes
        gridPaneLeft.setHgap(6);
        gridPaneLeft.setVgap(3);
        
        gridPaneMiddle.setHgap(6);
        gridPaneMiddle.setVgap(3);
        
        gridPaneRight.setHgap(120);
        
        //Adds Labels and ListViews to main GridPane
        gridPane.add(new Label("Customers"), 0, 0);
        gridPane.add(new Label("Transactions"), 1, 0);
        gridPane.add(new Label("Items"), 2, 0);
        gridPane.add(customersLV, 0, 1);
        gridPane.add(transactionsLV, 1, 1);
        gridPane.add(itemsLV, 2, 1);

        //Adds leftmost GridPane nodes
        gridPaneLeft.add(newCustomer, 0, 0);
        gridPaneLeft.add(deleteCustomer, 1, 0);
        gridPaneLeft.add(customerSearchLabel, 0, 1, 2, 1);
        gridPaneLeft.add(customerSearchTF, 0, 2);
        gridPaneLeft.add(searchCustomer, 1, 2);
        
        //Adds middle GridPane nodes
        gridPaneMiddle.add(newTransaction, 0, 0);
        gridPaneMiddle.add(deleteTransaction, 1, 0);
        gridPaneMiddle.add(transactionSearchLabel, 0, 1);
        gridPaneMiddle.add(transactionSearchTF, 0, 2);
        gridPaneMiddle.add(searchTransaction, 1, 2);
        
        //Adds rightmost GridPane nodes
        gridPaneRight.add(newItem, 0, 0);
        gridPaneRight.add(deleteItem, 1, 0);
        
        //Adds sub GridPanes to HBox and HBox to main GridPane
        hbox.getChildren().addAll(gridPaneLeft, gridPaneMiddle, gridPaneRight);
        gridPane.add(hbox, 0, 2, 3, 1);
        
        return gridPane;
    }
    
    //Sets up Customer ListView
    private void customersListView() {
        //Sets ListView up with ObservableList items and makes selection mode single
        customersLV.setItems(customers);
        customersLV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        //Cell Factory for ListView for custom methods showing up in the ListView
        customersLV.setCellFactory(param -> new WordListCellCustomer());
        
        //On double mouse click open window for editing individual Customers
        customersLV.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() ==2) {
                new FXCustomerStage(customers.get(customersLV.getSelectionModel().getSelectedIndex()));
            }
        });
        
        //Populates Transaction ListView
        customersLV.getSelectionModel().selectedItemProperty().addListener(
        ov -> {
            int index = customersLV.getSelectionModel().getSelectedIndex();
            if (index > -1 && index < customers.size()) {
                transactionsListView(customers.get(index));
            } else {
                transactionsListView(new Domestic());
            }
        });
        
        //When pressing new Button, new Stage for choosing type of customer
        //Also creates new Customer once closing the window
        newCustomer.setOnAction(e -> {
            newCustomerStage newCustStage = new newCustomerStage();
            newCustStage.setOnCloseRequest(r -> {
                String newCustomerChoice = newCustStage.getComboValue();
                if (newCustomerChoice.equals("Domestic")) {
                    customers.add(new Domestic());
                }
                else if (newCustomerChoice.equals("Commercial")) {
                    customers.add(new Commercial());
                }
                else {
                    System.out.println("Can't create new Customer");
                }
            });
            
        });
        
        //Deletes selected Customer
        deleteCustomer.setOnAction(e -> {
            try {
                customers.remove(customersLV.getSelectionModel().getSelectedIndex());
                if (customers.isEmpty()) {
                    transactionsLV.setItems(FXCollections.observableArrayList());
                    itemsLV.setItems(FXCollections.observableArrayList());
                }
            }
            catch (IndexOutOfBoundsException ex) {
                System.out.println("Index Out Of Bounds Exception: No Customer to Delete");
            }
        });
        
    }
    
    //Sets up Transaction ListView
    private void transactionsListView(Customer customer) {
        //Creates ObservableList from a Customer's ObservaleList and sets up ListView from it
        ObservableList<Transaction> transactions = customer.getTransactions();
        transactionsLV.setItems(transactions);
        //Selection mode single
        transactionsLV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        //Custom ListView display
        transactionsLV.setCellFactory(param -> new WordListCellTransaction());
        
        //On double mouse click open window for editing individual Transaction
        transactionsLV.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                new FXTransactionStage(transactions.get(transactionsLV.getSelectionModel().getSelectedIndex()));
            }
        });
        
        //Populates Item ListView when selecting Transaction
        transactionsLV.getSelectionModel().selectedItemProperty().addListener(
        ov -> {
            int index = transactionsLV.getSelectionModel().getSelectedIndex();
            if (index > -1 && index < transactions.size()) {
                itemsListView(customer.getTransaction(index));
            }
            else {
                itemsListView(new Transaction());
            }
            
        
        });
        
        //Creates new Transaction on new Button press
        newTransaction.setOnAction(e -> {
            if (!customers.isEmpty()){
                Transaction transaction = new Transaction();
                transaction.incrementCurrentId();
                customer.addTransaction(transaction);
                
            }
        });
        
        //Deletes selected Transaction
        deleteTransaction.setOnAction(e -> {
            try {
                customer.removeTransaction(transactionsLV.getSelectionModel().getSelectedIndex());
                if (customer.getTransactions().isEmpty()) {
                    itemsLV.setItems(FXCollections.observableArrayList());
                }
            }
            catch (IndexOutOfBoundsException ex) {
                System.out.println("Index Out Of Bounds Exception: No Transaction to Delete");
            }
        });
    }
    
    //Sets up Item ListView
    private void itemsListView(Transaction transaction) {
        //Creates ObservableList from a Transaction's ObservaleList and sets up ListView from it
        ObservableList<Item> items = transaction.getItems();
        itemsLV.setItems(items);
        //Makes selection mode single
        itemsLV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        //Custom ListView display
        itemsLV.setCellFactory(param -> new WordListCellItem());
        
        //On double mouse click open window for editing individual Transaction
        itemsLV.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() ==2) {
                new FXItemStage(items.get(itemsLV.getSelectionModel().getSelectedIndex()));
            }
        });
        
        //Makes new Stage to choose new Item type and creates it
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
        
        //Deletes selected Item
        deleteItem.setOnAction(e -> {
            try {
                transaction.removeItem(itemsLV.getSelectionModel().getSelectedIndex());
            }
            catch (IndexOutOfBoundsException ex) {
                System.out.println("Index Out Of Bounds Exception: No Item to Delete");
            }
        });
    }

    
    //Sets up search stuff
    private void searchHandle() {
        //Searches for Customer by ID upon pressing search Button but also populates the 
        //ListViews when entering the correct phrase
        searchCustomer.setOnAction(e -> {
            try {
                if (customerSearchTF.getText().equals("\\populateDirectory")) {
                    populateDirectory();
                }
                int index = customerSearch(customers, Long.valueOf(customerSearchTF.getText()),
                        0, customers.size() - 1);
                if (index > -1) {
                    new FXCustomerStage(customers.get(index));
                }
                else {
                    System.out.println("No Customer with that ID");
                }
            }
            catch (NumberFormatException ex) {
                System.out.println("Number Format Exception: Incorrect data type");
            }
        });
        
        //Searches for Transaction by ID upon pressing search Button
        searchTransaction.setOnAction(e -> {
            try {
                boolean found = false;
                for (int i = 0; i < customers.size(); ++i) {
                    Customer customer = customers.get(i);
                    ObservableList<Transaction> transactions = customer.getTransactions();

                    int index = transactionSearch(transactions, Long.valueOf(transactionSearchTF.getText()),
                            0, transactions.size() - 1);

                    if (index > -1) {
                        new FXTransactionStage(transactions.get(index));
                        found = true;

                    }
                }
                if (!found) {
                    System.out.println("No Transaction with that ID");
                }
            }
            catch (NumberFormatException ex) {
                System.out.println("Number Format Exception: Incorrect data type");
            }
        });
    }
    
    //Recursive Binary search for Customer by ID
    private int customerSearch(ObservableList<Customer> list, long key, int low, 
            int high) {
        
        if (low > high) // The list has been exhausted without a match
            return -low - 1;

        int mid = (low + high) / 2;
        if (key < list.get(mid).getCustomerId())
            return customerSearch(list, key, low, mid - 1);
        else if (key == list.get(mid).getCustomerId())
            return mid;
        else
            return customerSearch(list, key, mid + 1, high);
    }
    
    //Recursive Binary search for Customer by ID
    private int transactionSearch(ObservableList<Transaction> list, long key, int low, 
            int high) {
        
        if (low > high) // The list has been exhausted without a match
            return -low - 1;

        int mid = (low + high) / 2;
        if (key < list.get(mid).getTransactionId())
            return transactionSearch(list, key, low, mid - 1);
        else if (key == list.get(mid).getTransactionId())
            return mid;
        else
            return transactionSearch(list, key, mid + 1, high);
    }
    
    //Method to populate the lists
    private void populateDirectory() {
        Domestic customer1 = new Domestic();
        Commercial customer2 = new Commercial();
        Domestic customer3 = new Domestic();

        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        Transaction transaction3 = new Transaction();
        Transaction transaction4 = new Transaction();
        Transaction transaction5 = new Transaction();
        Transaction transaction6 = new Transaction();
        
        Upgrade item1 = new Upgrade();
        Cleaning item2 = new Cleaning();
        Repair item3 = new Repair();
        Cleaning item4 = new Cleaning();
        Upgrade item5 = new Upgrade();
        Cleaning item6 = new Cleaning();
        Misc item7 = new Misc();
        Cleaning item8 = new Cleaning();
        Repair item9 = new Repair();
        
        transaction1.addItem(item1);
        transaction1.addItem(item2);
        transaction3.addItem(item3);
        transaction3.addItem(item4);
        transaction3.addItem(item5);
        transaction4.addItem(item6);
        transaction5.addItem(item7);
        transaction5.addItem(item8);
        transaction6.addItem(item9);
        
        customer1.addTransaction(transaction1);
        customer1.addTransaction(transaction2);
        customer1.addTransaction(transaction3);
        customer2.addTransaction(transaction4);
        customer3.addTransaction(transaction5);
        customer3.addTransaction(transaction6);
        
        
        
        
        item1.setDescription("RAM Upgrade");
        item1.setPrices("computer", 0, 0);
        item1.setQuantity(1);
        item1.setTotalPrice();
        
        item2.setDescription("Laptop Cleaning");
        item2.setPrices("laptop", 0, 0);
        item2.setQuantity(2);
        item2.setTotalPrice();
        
        item3.setDescription("Arcade Game Repair");
        item3.setPrices("misc", 50, 70);
        item3.setHours(3);
        item3.setQuantity(1);
        item3.setTotalPrice();
        
        item4.setDescription("Phone Deep Clean");
        item4.setPrices("mobile", 0, 0);
        item4.setQuantity(1);
        item4.setTotalPrice();
        
        item5.setDescription("Laptop SSD Upgrade");
        item5.setPrices("laptop", 0, 0);
        item5.setQuantity(2);
        item5.setTotalPrice();
        
        item6.setDescription("Controller Deep Clean");
        item6.setPrices("controller", 0, 0);
        item6.setQuantity(4);
        item6.setTotalPrice();
        
        item7.setDescription("Windows Unlock: No Password");
        item7.setPrices("locked access", 0, 0);
        item7.setQuantity(1);
        item7.setTotalPrice();
        
        item8.setDescription("Computer Dusting");
        item8.setPrices("computer", 0, 0);
        item8.setQuantity(1);
        item8.setTotalPrice();
        
        item9.setDescription("Computer GPU Failure");
        item9.setPrices("computer", 120, 100);
        item9.setHours(2);
        item9.setQuantity(1);
        item9.setTotalPrice();
        
        transaction1.setAmountPaid(59);
        transaction1.setTransactionId(55L);
        transaction1.setDiscount(32);
        transaction1.setPaymentMethod("CASH");
        transaction1.setTax(3);
        transaction1.initializeTotals();
        
        transaction2.setAmountPaid(101);
        transaction2.setTransactionId(56L);
        transaction2.setDiscount(22);
        transaction2.setPaymentMethod("DIRECT");
        transaction2.setTax(2);
        transaction2.initializeTotals();
        
        transaction3.setAmountPaid(170);
        transaction3.setTransactionId(57L);
        transaction3.setDiscount(10);
        transaction3.setPaymentMethod("CREDIT");
        transaction3.setTax(8);
        transaction3.initializeTotals();
        
        transaction4.setAmountPaid(40);
        transaction4.setTransactionId(58L);
        transaction4.setDiscount(50);
        transaction4.setPaymentMethod("CHECK");
        transaction4.setTax(4);
        transaction4.initializeTotals();
        
        transaction5.setAmountPaid(12);
        transaction5.setTransactionId(59L);
        transaction5.setDiscount(23);
        transaction5.setPaymentMethod("DEBIT");
        transaction5.setTax(6);
        transaction5.initializeTotals();
        
        transaction6.setAmountPaid(500);
        transaction6.setTransactionId(60L);
        transaction6.setDiscount(0);
        transaction6.setPaymentMethod("CASH");
        transaction6.setTax(6);
        transaction6.initializeTotals();
        transaction6.setCurrentId(61L);
        
        customer1.setFirstName("Rex");
        customer1.setLastName("Schraeder");
        customer1.setPrimaryName();
        customer1.setEmail("rexSchraeder555@gmail.com");
        customer1.setPhoneNumber(5555554444L);
        customer1.setCustomerId(33);
        customer2.setBusinessName("Keller Inc.");
        customer2.setPrimaryName();
        customer2.setTransactorFirst("Mike");
        customer2.setTransactorLast("Keller");
        customer2.setEmail("kellerinc@gmail.com");
        customer2.setPhoneNumber(18003352323L);
        customer2.setCustomerId(35);
        customer3.setFirstName("Ella");
        customer3.setLastName("Roberts");
        customer3.setPrimaryName();
        customer3.setEmail("turtlelover32@gmail.com");
        customer3.setPhoneNumber(7123331022L);
        customer3.setCustomerId(37);
        customer3.setCurrentId(37);
        
        
        customers.addAll(customer1, customer2, customer3);
    }
}

//Class for Customer ListView cell
class WordListCellCustomer extends ListCell<Customer> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final VBox layout = new VBox(title, detail);

    public WordListCellCustomer() {
        super();
        title.setStyle("-fx-font-size: 20px;");
    }

    @Override
    protected void updateItem(Customer item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);

        if (empty || item == null) {
            title.setText(null);
            detail.setText(null);
            setGraphic(null);
        } else {
            //Display's the Customer's primary name and Customer type
            title.setText(item.getPrimaryName());
            detail.setText(item.getCustomerType());
            setGraphic(layout);
        }
    }
    
    
}

//Class for Transaction ListView cell
class WordListCellTransaction extends ListCell<Transaction> {
    private final DecimalFormat df = new DecimalFormat("#.##");
    private final Label title = new Label();
    private final Label detail = new Label();
    private final VBox layout = new VBox(title, detail);

    public WordListCellTransaction() {
        super();
        title.setStyle("-fx-font-size: 20px;");
    }

    @Override
    protected void updateItem(Transaction item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);

        if (empty || item == null) {
            title.setText(null);
            detail.setText(null);
            setGraphic(null);
        } else {
            //Displays Transaction's Date/Time and the total
            title.setText(item.getDateTimeTransaction());
            detail.setText(df.format(item.getTotal()));
            setGraphic(layout);
        }
    }
}

//Class for Item ListView cell
class WordListCellItem extends ListCell<Item> {
    private final DecimalFormat df = new DecimalFormat("#.##");
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label subDetail = new Label();
    private final VBox layout = new VBox(title, detail, subDetail);

    public WordListCellItem() {
        super();
        title.setStyle("-fx-font-size: 20px;");
    }

    @Override
    protected void updateItem(Item item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);

        if (empty || item == null) {
            title.setText(null);
            detail.setText(null);
            subDetail.setText(null);
            setGraphic(null);
        } else {
            //Displays Item's description, sub type, and total price
            title.setText(item.getDescription());
            detail.setText(item.getItemSubType());
            subDetail.setText(df.format(item.getTotalPrice()));
            setGraphic(layout);
        }
    }
}

//Stage to choose new Customer type
class newCustomerStage extends Stage {
    String[] customerOptions = {"Domestic","Commercial"};
    ComboBox<String> customerCombo = new ComboBox<>();

    public newCustomerStage() {
        customerCombo.getItems().addAll(customerOptions);
        Label label = new Label("Choose customer type:  ");

        HBox pane = new HBox(label,customerCombo);
        pane.setPadding(new Insets(20, 20, 20, 20));
        Scene scene = new Scene(pane);
        this.setTitle("ChooseCustomer"); // Set the stage title 
        this.setScene(scene); // Place the scene in the stage 
        this.show(); // Display the stage 
    }

    public String getComboValue() {
        return customerCombo.getValue();
    }
}

//Stage to choose new Item type
class newItemStage extends Stage {
    String[] itemOptions = {"Repair","Cleaning","Upgrade","Misc"};
    ComboBox<String> itemCombo = new ComboBox<>();

    public newItemStage() {
        itemCombo.getItems().addAll(itemOptions);
        Label label = new Label("Choose item type:  ");

        HBox pane = new HBox(label,itemCombo);
        pane.setPadding(new Insets(20, 20, 20, 20));
        Scene scene = new Scene(pane);
        this.setTitle("ChooseItem"); // Set the stage title 
        this.setScene(scene); // Place the scene in the stage 
        this.show(); // Display the stage 
    }

    public String getComboValue() {
        return itemCombo.getValue();
    }
}
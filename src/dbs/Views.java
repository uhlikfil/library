/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbs;

import java.util.Optional;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author W8
 */
public class Views {
    
    private Stage window;
    private DbController controller;
    private Scene loginScene, booksScene, customersScene, lentBooksScene;
    private TableView<Book> bookTable;
    private TableView<Customer> cusTable;
    private TableView<CustomerBook> cusBookTable;

    private final int ID_CELL_WIDTH = 30;
    private final int NAME_CELL_WIDTH = 150;
    private final int TOP_BUTTON_WIDTH = 90;
    private final int TOP_BUTTON_HEIGHT = 30;
    private final int SIDE_BUTTON_WIDTH = 80;
    private final int SIDE_BUTTON_HEIGHT = 30;
    private final Insets SIDE_INSETS = new Insets(15, 15, 15, 15);
    private final int SIDE_SPACING = 10;
    
    public Views(Stage window, DbController controller) {
        this.window = window;
        this.controller = controller;
        
        //login
        BorderPane bpLogin = new BorderPane();
        bpLogin.setCenter(makeLoginWindow());
        loginScene = new Scene(bpLogin, 800, 600);
         
        // books
        BorderPane bpBooks = new BorderPane();
        bpBooks.setTop(makeTopMenuBar());
        bookTable = makeBooksTable();
        bpBooks.setCenter(bookTable);
        bpBooks.setLeft(makeBookControls());
        booksScene = new Scene(bpBooks, 800, 600);
        
        // customers
        BorderPane bpCustomers = new BorderPane();
        bpCustomers.setTop(makeTopMenuBar());
        cusTable = makeCustomersTable();
        bpCustomers.setCenter(cusTable);
        bpCustomers.setLeft(makeCustomerControls());
        customersScene = new Scene(bpCustomers, 800, 600);
        
        // lent books
        BorderPane bpLentBooks = new BorderPane();
        bpLentBooks.setTop(makeTopMenuBar());
        cusBookTable = makeLentBooksTable();
        bpLentBooks.setCenter(cusBookTable);
        bpLentBooks.setLeft(makeLoanControls());
        lentBooksScene = new Scene(bpLentBooks, 800, 600);
    }
    
    private VBox makeLoginWindow() {
        VBox login = new VBox();
        login.setPadding(new Insets(20, 20, 20, 20));
        login.setSpacing(10);
        login.setMaxSize(300, 200);
        login.setStyle("-fx-background-color: #991111;");
        
        Label username = new Label("Username");
        username.setTextFill(Color.WHITE);
        TextField usernameField = new TextField();
        
        Label pass = new Label("Password");
        pass.setTextFill(Color.WHITE);
        PasswordField passField = new PasswordField();
        
        Button log = new Button("LOG IN");
        log.setPrefSize(TOP_BUTTON_WIDTH, TOP_BUTTON_HEIGHT);
        log.setOnAction(e -> login(usernameField.getText(), passField.getText()));
        
        login.getChildren().addAll(username, usernameField, pass, passField, log);
        
        return login;
    }
    
    private void login(String username, String pass) {
        if (username.equals("admin") && pass.equals("admin")) {
            window.setScene(customersScene);
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Login unsuccessfull!");
            alert.setHeaderText("Wrong combination of username and password.");
        
            alert.showAndWait();
        }
    }
        
    private HBox makeTopMenuBar() {
        HBox topMenu = new HBox();
        topMenu.setPadding(new Insets(20, 20, 20, 20));
        topMenu.setSpacing(50);
        topMenu.setStyle("-fx-background-color: #330000;");
        
        Button customersButton = new Button("Customers");
        customersButton.setPrefSize(TOP_BUTTON_WIDTH, TOP_BUTTON_HEIGHT);
        customersButton.setOnAction(e -> window.setScene(customersScene));
        
        Button booksButton = new Button("Books");
        booksButton.setPrefSize(TOP_BUTTON_WIDTH, TOP_BUTTON_HEIGHT);
        booksButton.setOnAction(e -> window.setScene(booksScene));
        
        Button lentBooksButton = new Button("Lent books");
        lentBooksButton.setPrefSize(TOP_BUTTON_WIDTH, TOP_BUTTON_HEIGHT);
        lentBooksButton.setOnAction(e -> window.setScene(lentBooksScene));
        

        topMenu.getChildren().addAll(customersButton, booksButton, lentBooksButton);
        
        return topMenu;
    }
    
    private VBox makeCustomerControls() {
        VBox leftMenu = new VBox();
        leftMenu.setPadding(SIDE_INSETS);
        leftMenu.setSpacing(SIDE_SPACING);
        leftMenu.setAlignment(Pos.CENTER);
        leftMenu.setStyle("-fx-background-color: #aaaaaa;");
        
        Button addCustomerButton = new Button("Add");
        addCustomerButton.setPrefSize(SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT);
        addCustomerButton.setOnAction(e -> addCustomer());
        
        Button editCustomerButton = new Button("Edit");
        editCustomerButton.setPrefSize(SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT);
        editCustomerButton.setOnAction(e -> updateCustomer());
        
        Button removeCustomerButton = new Button("Remove");
        removeCustomerButton.setPrefSize(SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT);
        removeCustomerButton.setOnAction(e -> removeCustomer());
        
        Button setActiveButton = new Button("Activate");
        setActiveButton.setPrefSize(SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT);
        setActiveButton.setOnAction(e -> activateCustomer());
        
        Button lendBookButton = new Button("Lend" + System.getProperty("line.separator") + "a book");
        lendBookButton.textAlignmentProperty().set(TextAlignment.CENTER);
        lendBookButton.setPrefSize(SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT*2);
        lendBookButton.setOnAction(e -> lendBook());
        
        leftMenu.getChildren().addAll(addCustomerButton, editCustomerButton, removeCustomerButton, setActiveButton, lendBookButton);
        
        return leftMenu;
    }
        
    private VBox makeBookControls() {
        VBox leftMenu = new VBox();
        leftMenu.setPadding(SIDE_INSETS);
        leftMenu.setSpacing(SIDE_SPACING);
        leftMenu.setAlignment(Pos.CENTER);
        leftMenu.setStyle("-fx-background-color: #aaaaaa;");
        
        Button addBookButton = new Button("Add");
        addBookButton.setPrefSize(SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT);
        addBookButton.setOnAction(e -> addBook());
        
        Button editBookButton = new Button("Edit");
        editBookButton.setPrefSize(SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT);
        editBookButton.setOnAction(e -> updateBook());
        
        Button removeBookButton = new Button("Remove");
        removeBookButton.setPrefSize(SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT);
        removeBookButton.setOnAction(e -> removeBook());
        
        leftMenu.getChildren().addAll(addBookButton, editBookButton, removeBookButton);
        
        return leftMenu;
    }
    
    private VBox makeLoanControls() {
        VBox leftMenu = new VBox();
        leftMenu.setPadding(SIDE_INSETS);
        leftMenu.setSpacing(SIDE_SPACING);
        leftMenu.setAlignment(Pos.CENTER);
        leftMenu.setStyle("-fx-background-color: #aaaaaa;");
        
        Button removeLoanButton = new Button("Remove");
        removeLoanButton.setPrefSize(SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT);
        removeLoanButton.setOnAction(e -> removeLoan());
        
        leftMenu.getChildren().addAll(removeLoanButton);
        
        return leftMenu;
    }
    
    private TableView<Customer> makeCustomersTable() {
        TableView<Customer> table;
        
        // id col
        TableColumn<Customer, Integer> idCol = new TableColumn<>("ID");
        idCol.setMinWidth(ID_CELL_WIDTH);
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        
        // firstname col
        TableColumn<Customer, String> firstnameCol = new TableColumn<>("Firstname");
        firstnameCol.setMinWidth(NAME_CELL_WIDTH);
        firstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        
        // surname col
        TableColumn<Customer, String> surnameCol = new TableColumn<>("Surname");
        surnameCol.setMinWidth(NAME_CELL_WIDTH);
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        
        // active col
        TableColumn<Customer, String> activeCol = new TableColumn<>("Active");
        activeCol.setMinWidth(ID_CELL_WIDTH);
        activeCol.setCellValueFactory(cellData -> {
            boolean active = cellData.getValue().getActive();
            String activeString;
            if(active) {
                activeString = "YES";
            } else {
                activeString = "NO";
            }
            return new ReadOnlyStringWrapper(activeString);
        });
        
        table = new TableView<>();
        table.setItems(controller.getAllCustomers());
        table.getColumns().addAll(idCol, firstnameCol, surnameCol, activeCol);
        table.getSelectionModel().selectFirst();
        
        return table;
    }
    
    private TableView<Book> makeBooksTable() {
        TableView<Book> table;
        
        // id col
        TableColumn<Book, Integer> idCol = new TableColumn<>("ID");
        idCol.setMinWidth(ID_CELL_WIDTH);
        idCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        
        // title col
        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setMinWidth(NAME_CELL_WIDTH);
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        
        // author col
        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        authorCol.setMinWidth(NAME_CELL_WIDTH);
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        
        // age restricted
        TableColumn<Book, String> ageRestrCol = new TableColumn<>("Age restricted");
        ageRestrCol.setMinWidth(ID_CELL_WIDTH);
        ageRestrCol.setCellValueFactory(cellData -> {
            boolean restricted = cellData.getValue().getAgeRestricted();
            String restrictedStr;
            if(restricted) {
                restrictedStr = "YES";
            } else {
                restrictedStr = "NO";
            }
            return new ReadOnlyStringWrapper(restrictedStr);
        });
        
        TableColumn<Book, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setMinWidth(ID_CELL_WIDTH);
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        table = new TableView<>();
        table.setItems(controller.getAllBooks());
        table.getColumns().addAll(idCol, titleCol, authorCol, ageRestrCol, quantityCol);
        table.getSelectionModel().selectFirst();
        
        return table;
    }
    
    private TableView<CustomerBook> makeLentBooksTable() {
        TableView<CustomerBook> table;
        
        //id col
        TableColumn<CustomerBook, Integer> loanIdCol = new TableColumn<>("ID");
        loanIdCol.setMinWidth(ID_CELL_WIDTH);
        loanIdCol.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        
        // id col
        TableColumn<CustomerBook, Integer> cusIdCol = new TableColumn<>("ID C");
        cusIdCol.setMinWidth(ID_CELL_WIDTH);
        cusIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        
        // firstname col
        TableColumn<CustomerBook, String> firstnameCol = new TableColumn<>("Firstname");
        firstnameCol.setMinWidth(NAME_CELL_WIDTH);
        firstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        
        // surname col
        TableColumn<CustomerBook, String> surnameCol = new TableColumn<>("Surname");
        surnameCol.setMinWidth(NAME_CELL_WIDTH);
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        
        // id col
        TableColumn<CustomerBook, Integer> bookIdCol = new TableColumn<>("ID B");
        bookIdCol.setMinWidth(ID_CELL_WIDTH);
        bookIdCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        
        // title col
        TableColumn<CustomerBook, String> titleCol = new TableColumn<>("Title");
        titleCol.setMinWidth(NAME_CELL_WIDTH);
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        
        // author col
        TableColumn<CustomerBook, String> authorCol = new TableColumn<>("Author");
        authorCol.setMinWidth(NAME_CELL_WIDTH);
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        
        table = new TableView<>();
        table.setItems(controller.getAllLoans());
        table.getColumns().addAll(loanIdCol, cusIdCol, firstnameCol, surnameCol, bookIdCol, titleCol, authorCol);
        table.getSelectionModel().selectFirst();
        
        return table;
    }
    
    private void addCustomer() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add new customer");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        Label firstname = new Label("Firstname");
        TextField firstnameField = new TextField();
        Label surname = new Label("Surname");
        TextField surnameField = new TextField();
        Label birthYear = new Label("Year of birth");
        TextField birthYearField = new TextField();
        
        dialogPane.setContent(new VBox(firstname, firstnameField, surname, surnameField, birthYear, birthYearField));
        
        Optional<ButtonType> result = dialog.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            if (controller.checkInput(firstnameField.getText()) && controller.checkInput(surnameField.getText())
                    && controller.isInteger(birthYearField.getText()) && birthYearField.getText().trim().length() == 4) {
                controller.addCustomer(firstnameField.getText(), surnameField.getText(), Integer.parseInt(birthYearField.getText()));
                refreshTables();
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Customer was not added!");
                alert.setHeaderText("Plase, fill all the fields.");
        
                alert.showAndWait();
            }
        }
    }
        
    private void addBook() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add new book");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        Label title = new Label("Book Title");
        TextField titleField = new TextField();
        Label author = new Label("Book Author");
        TextField authorField = new TextField();
        Label ageRestr = new Label("Age restriction");
        CheckBox ageRestrCB = new CheckBox();
        Label quantity = new Label("Quantity");
        TextField quantityField = new TextField();
        
        dialogPane.setContent(new VBox(title, titleField, author, authorField, ageRestr, ageRestrCB, quantity, quantityField));
        
        Optional<ButtonType> result = dialog.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            if (controller.checkInput(titleField.getText()) && controller.checkInput(authorField.getText())
            && controller.isInteger(quantityField.getText()) && Integer.parseInt(quantityField.getText()) > 0) {
                controller.addBook(titleField.getText(), authorField.getText(), ageRestrCB.isSelected(), Integer.parseInt(quantityField.getText()));
                refreshTables();
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Book was not added!");
                alert.setHeaderText("Plase, fill all the fields.");
        
                alert.showAndWait();
            }
        }
    }
    
    private Customer getSelectedCustomer() {
        TablePosition pos = cusTable.getSelectionModel().getSelectedCells().get(0);
        int index = pos.getRow();
        return cusTable.getItems().get(index);
    }
        
    private Book getSelectedBook() {
        TablePosition pos = bookTable.getSelectionModel().getSelectedCells().get(0);
        int index = pos.getRow();
        return bookTable.getItems().get(index);
    }
    
    private CustomerBook getSelectedLoan() {
        TablePosition pos = cusBookTable.getSelectionModel().getSelectedCells().get(0);
        int index = pos.getRow();
        return cusBookTable.getItems().get(index);
    }
    
    private void updateCustomer() {
        Customer customer = getSelectedCustomer();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Update customer");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        Label firstname = new Label("Firstname");
        TextField newFirstname = new TextField(customer.getFirstname());
        Label surname = new Label("Surname");
        TextField newSurname = new TextField(customer.getSurname());
        
        dialogPane.setContent(new VBox(firstname, newFirstname, surname, newSurname));
        
        Optional<ButtonType> result = dialog.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            if (controller.checkInput(newFirstname.getText()) && controller.checkInput(newSurname.getText())) {
                controller.editCustomerById(customer.getCustomerId(), newFirstname.getText(), newSurname.getText());
                refreshTables();
            }
        }
    }
        
    private void updateBook() {
        Book book = getSelectedBook();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Update book");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        Label title = new Label("Book Title");
        TextField newTitle = new TextField(book.getTitle());
        Label author = new Label("Book Author");
        TextField newAuthor = new TextField(book.getAuthor());
        Label ageRestr = new Label("Age restriction");
        CheckBox ageRestrCB = new CheckBox();
        ageRestrCB.setSelected(book.getAgeRestricted());
        
        dialogPane.setContent(new VBox(title, newTitle, author, newAuthor, ageRestr, ageRestrCB));
        
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.get() == ButtonType.OK) {
            if (controller.checkInput(newTitle.getText()) && controller.checkInput(newAuthor.getText())) {
                controller.editBookById(book.getBookId(), newTitle.getText(), newAuthor.getText(), ageRestrCB.isSelected()); 
                refreshTables();
            }
        }
    }
    
    private void removeCustomer() {
        Customer customer = getSelectedCustomer();
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Customer delete");
        alert.setHeaderText("Do you really wish to delete this customer?");
        alert.setContentText(customer.getFirstname()+ " " + customer.getSurname());

        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            if (!controller.hasBook(customer)) {
                controller.removeCustomerById(customer.getCustomerId());
                refreshTables();
            } else {
                Alert alert2 = new Alert(AlertType.INFORMATION);
                alert2.setTitle("Customer has a book!");
                alert2.setHeaderText("You cannot delete a customer if he has a borrowed book.");
                alert2.setContentText("Get it back first!");
                
                alert2.showAndWait();
            }
        }
    }
        
    private void removeBook() {
        Book book = getSelectedBook();
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Book delete");
        alert.setHeaderText("Do you really wish to delete this book?");
        alert.setContentText(book.getTitle() + " by " + book.getAuthor());

        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            if (!controller.isBookLent(book)) {
                controller.removeBookById(book.getBookId());
                refreshTables();
            } else {
                Alert alert2 = new Alert(AlertType.INFORMATION);
                alert2.setTitle("Book is lent!");
                alert2.setHeaderText("You cannot delete a lent book from the database.");
                alert2.setContentText("Get it back first!");
                
                alert2.showAndWait();
            }
        }
    }
    
    private void removeLoan() {
        CustomerBook loan = getSelectedLoan();
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Loan delete");
        alert.setHeaderText("Do you really wish to delete this loan?");
        alert.setContentText(loan.getTitle() + " for " + loan.getSurname());

        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            controller.removeLoanById(loan.getLoanId());
            refreshTables();
        }
    }
    
    private void activateCustomer() {
        Customer customer = getSelectedCustomer();
        
        if (!customer.getActive()) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Activate customer");
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            Label label = new Label("Do you wish to activate " + customer.getFirstname() 
                                    + " " + customer.getSurname() + "?");

            dialogPane.setContent(new VBox(label));

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.get() == ButtonType.OK) {
                controller.activateCustomerById(customer.getCustomerId());
                refreshTables();
            }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Customer already active!");
            alert.setHeaderText("This customer cannot be activated right now.");
            alert.setContentText("");

            alert.showAndWait();
        }
    }
    
    private void lendBook() {
        Customer customer = getSelectedCustomer();
        
        if (customer.getActive()) {
        
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Lend a book");
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            Label label = new Label("Which book does " + customer.getFirstname() 
                                    + " " + customer.getSurname() + " wish to borrow?");
            ChoiceBox<String> choiceBox = new ChoiceBox<>();
            ObservableList<Book> books = controller.getAllBooks();
            for (Book book : books) {
                choiceBox.getItems().add("ID: " + book.getBookId() + " " + book.getTitle() + " by " + book.getAuthor());
            }

            dialogPane.setContent(new VBox(label, choiceBox));

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.get() == ButtonType.OK) {
                String[] book = choiceBox.getValue().split(" ");
                int bookId = Integer.parseInt(book[1]);

                Book b = controller.findBookById(bookId);
                
                if (controller.canBorrow(customer, b)) {
                    controller.lendBook(customer.getCustomerId(), b.getBookId());
                    refreshTables();
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Cannot lend the book!");
                    alert.setHeaderText(customer.getSurname() + " can't borrow " + b.getTitle() + " right now.");

                    alert.showAndWait();
                }
                
            }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Customer not active!");
            alert.setHeaderText("This customer cannot borrow a book.");
            alert.setContentText("Please, ask " + customer.getFirstname() + " " 
                                + customer.getSurname() + " to pay the library fee first.");

            alert.showAndWait();
        }
    }
        
    private void refreshCustomers() {
        controller.deactivateCustomers(System.currentTimeMillis());
        cusTable.setItems(controller.getAllCustomers());
        cusTable.getColumns().get(0).setVisible(false);
        cusTable.getColumns().get(0).setVisible(true);
        cusTable.getSelectionModel().selectFirst();
    }
    
    private void refreshBooks() {
        bookTable.setItems(controller.getAllBooks());
        bookTable.getColumns().get(0).setVisible(false);
        bookTable.getColumns().get(0).setVisible(true);
        bookTable.getSelectionModel().selectFirst();
    }
    
    private void refreshLoans() {
        cusBookTable.setItems(controller.getAllLoans());
        cusBookTable.getColumns().get(0).setVisible(false);
        cusBookTable.getColumns().get(0).setVisible(true);
        cusBookTable.getSelectionModel().selectFirst();
    }
    
    private void refreshTables() {
        refreshCustomers();
        refreshBooks();
        refreshLoans();
    }

    public Scene getLoginScene() {
        return loginScene;
    }   
}

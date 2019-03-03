package phoneBook;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *
 * @author Adam
 */
public class ViewController implements Initializable {
    
    @FXML
    TableView table;
    @FXML
    TextField inputLastName;
    @FXML
    TextField inputFirstName;
    @FXML
    TextField inputEmail;
    @FXML
    Button addNewContactButton;
    @FXML
    StackPane menuPane;
    @FXML
    Pane contactPane;
    @FXML
    Pane exportPane;
    @FXML
    TextField inputExport;
    @FXML
    Button exportButton;
    @FXML
    AnchorPane anchor;
    @FXML
    SplitPane mainSplit;
    
    DB db = new DB();
    
    private final String MENU_CONTACTS = "Contacts";
    private final String MENU_LIST = "List";
    private final String MENU_EXPORT = "Export";
    private final String MENU_EXIT = "Exit";
    
    private final ObservableList<Person> data = FXCollections.observableArrayList();
    
    public void setTableData() {
        TableColumn lastNameCol = new TableColumn("Last name");
        lastNameCol.setMaxWidth(125);
        lastNameCol.setMinWidth(75);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn()); 
        //  beallitjuk ,hogy minden cellanak TextField legyen a tartalma > 8.video
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        /*Egyel fentebb > var tolunk egy parametert, a new PropertyValueFactory()-t
        PropertyValueFactory() az egy olyan objektum a 2 dolgot tud atvenni >
        > 1. POJO (milyen erteket es parameterkent mgmondjuk neki ,hogy milyen neven talalja) 
        > A Person-bol Stringkent fogunk megjeleniteni egy lastName nevu valtozot
        */
        lastNameCol.setOnEditCommit(
            new javafx.event.EventHandler<TableColumn.CellEditEvent<Person, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Person, String> t) {
                    Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    actualPerson.setLastName(t.getNewValue());
                    
                    db.updateContact(actualPerson);
            }
        }
    );
        
        TableColumn firstNameCol = new TableColumn("First name");
        firstNameCol.setMaxWidth(125);
        firstNameCol.setMinWidth(75);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        firstNameCol.setOnEditCommit(
            new javafx.event.EventHandler<TableColumn.CellEditEvent<Person, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Person, String> t) {
                    Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    actualPerson.setFirstName(t.getNewValue());
                    
                    db.updateContact(actualPerson);
                }
            }
        );
        
        TableColumn emailCol = new TableColumn("Email");
        emailCol.setMaxWidth(250);
        emailCol.setMinWidth(175);
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        emailCol.setOnEditCommit(
            new javafx.event.EventHandler<TableColumn.CellEditEvent<Person, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Person, String> t) {
                    Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    actualPerson.setEmail(t.getNewValue());
                    
                    db.updateContact(actualPerson);
                }
            }
        );
        
        TableColumn removeCol = new TableColumn("Delete");
        emailCol.setMinWidth(100);
        
        Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactory =
                new Callback<TableColumn<Person, String>, TableCell<Person, String>>() {
            @Override
            public TableCell call (final TableColumn<Person, String> param) {
                final TableCell<Person, String> cell = new TableCell<Person, String>()
                {
                    final Button btn = new Button("Delete");
                    
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction((ActionEvent event) ->
                {
                    Person person = getTableView().getItems().get(getIndex());
                    data.remove(person);
                    db.removeContact(person);
                }
                            );
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        
        removeCol.setCellFactory(cellFactory);
        
        table.getColumns().addAll(lastNameCol, firstNameCol, emailCol, removeCol);
        data.addAll(db.getAllContacts());
        table.setItems(data);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableData();
        setMenuData();
    }    

    private void setMenuData() {
        TreeItem<String> treeItamBoot1 = new TreeItem<>("Menu");
        TreeView<String> treeView = new TreeView<>(treeItamBoot1);
        treeView.setShowRoot(false);
        
        TreeItem<String> nodeItamA = new TreeItem<>(MENU_CONTACTS);
        TreeItem<String> nodeItamB = new TreeItem<>(MENU_EXIT);
        
        //nodeItamA.setExpanded(true);
        
        Node contactsNode = new ImageView(new Image(getClass().getResourceAsStream("/contacts.png")));
        Node exportNode = new ImageView(new Image(getClass().getResourceAsStream("/export.png")));
        
        TreeItem<String> nodeItamA1 = new TreeItem<>(MENU_LIST, contactsNode);
        TreeItem<String> nodeItamA2 = new TreeItem<>(MENU_EXPORT, exportNode);
        
        nodeItamA.getChildren().addAll(nodeItamA1, nodeItamA2);
        treeItamBoot1.getChildren().addAll(nodeItamA, nodeItamB);
        
        menuPane.getChildren().add(treeView);
        
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                    String selectedMenu;
                    selectedMenu= selectedItem.getValue();
                    
                    if(null != selectedMenu) {
                        switch(selectedMenu) {
                            case MENU_CONTACTS : selectedItem.setExpanded(true);
                                break;
                            case MENU_LIST :
                                contactPane.setVisible(true);
                                exportPane.setVisible(false);
                                break;
                            case MENU_EXPORT :
                                contactPane.setVisible(false);
                                exportPane.setVisible(true);
                                break;
                            case MENU_EXIT : System.exit(0);
                                break;
                        }
                    }
                }
        });
    }
    
    @FXML
    private void addContact(ActionEvent event) {
        String email = inputEmail.getText();
        if(email.length() > 5 && email.contains("@") && email.contains(".")) {
            Person newPerson = new Person(inputFirstName.getText(), inputLastName.getText(), email);
            data.add(newPerson);
            db.addContact(newPerson);
            inputFirstName.clear();
            inputLastName.clear();
            inputEmail.clear();
        } else {
            alert("Give the real email address");
        }
        
        
    }
        
    @FXML
    private void exportList(ActionEvent event) {
        String fileName = inputExport.getText();
        fileName = fileName.replaceAll("\\s+", "");
        if(fileName != null && !fileName.equals("")) {
           PdfGeneration pdfCreator = new PdfGeneration();
            pdfCreator.pdfGeneration(fileName, data); 
        } else {
            alert("Give a file name");
        }
    }
    
    private void alert(String text) {
        mainSplit.setDisable(true);
        mainSplit.setOpacity(0.4);
        
        Label label = new Label(text);
        Button alertButton = new Button("OK");
        VBox vbox = new VBox(label, alertButton);
        vbox.setAlignment(Pos.CENTER);
        
        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainSplit.setDisable(false);
                mainSplit.setOpacity(1);
                vbox.setVisible(false);
            }
        });
        
        anchor.getChildren().add(vbox);
        anchor.setTopAnchor(vbox, 300.0);
        anchor.setLeftAnchor(vbox, 300.0);
        
        
    }
    
}

package View_Controller;

import Model.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**This class controls the actions that occur on the main screen of the application. */
public class MainScreenController implements Initializable {

    @FXML
    public TableView<Product> productsTableView;

    @FXML
    public TableColumn productIDCol;

    @FXML
    public TableColumn productNameCol;

    @FXML
    public TableColumn productInvCol;

    @FXML
    public TableColumn productPriceCol;

    @FXML
    public TextField searchProducts;

    @FXML
    private TextField searchParts;

    @FXML
    public TableView<Part> partsTableView;

    @FXML
    private TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;


    /** This method takes the user to the Add Parts screen.
     Using the Add button on main screen under the parts table will take user to the Add Parts screen.
     @param actionEvent When the Add button is clicked, this action will occur
     */
    @FXML
    public void onActionAddPartScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/addPartInhouse.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 470);
        stage.setTitle("Add In-house Part");
        stage.setScene(scene);
        stage.show();
    }


    /** This method exits the inventory program.
     Using the Exit button on the main screen will exit the program; an alert pop-up will confirm the selection from
     the user
     @param actionEvent Clicking the exit button will cause this action to occur
     */
    @FXML
    public void onActionExitProgram(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }


    /** This method initializes the main screen.
     On loading of the main screen, the table views of the Parts Table and the Products table are initialized.
     @param url The url
     @param resourceBundle The resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Initialize Parts Table
        partsTableView.setItems(Inventory.getAllParts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        //Initialize Products Table
        productsTableView.setItems(Inventory.getAllProducts());

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


    /** This method takes the user to Modify In-house Parts screen.
     Using the Modify button under the Parts Table will take user to the Modify In-house Parts Screen.
     RUNTIME ERROR: This method threw a NullPointerException and an IllegalStateException if an item was not
     selected from the table.  To fix this, I surrounded the code in a try/catch block to attempt to run the code
     and catch the exceptions if they occur and ignore them.  From there, I added an alert box to prompt the user
     to select an item to modify.
     @param actionEvent Clicking the modify button will cause this action to occur

     */
    @FXML
    public void onActionModifyScreen(ActionEvent actionEvent) throws IOException {

        //Receive Part object information from the PartController in order to populate the Modify Parts text fields
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View_Controller/modifyPartInhouse.fxml"));
            loader.load();

            PartController pController = loader.getController();

            /*If the Part object received from the Part Controller is an In-house Part, user is taken to the modify
            in-house part screen */
            if (partsTableView.getSelectionModel().getSelectedItem() != null) {
                if (partsTableView.getSelectionModel().getSelectedItem() instanceof InhousePart) {
                    pController.sendPart((InhousePart) partsTableView.getSelectionModel().getSelectedItem());

                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Parent scene = loader.getRoot();
                    stage.setTitle("Modify In-house Part");
                    stage.setScene(new Scene(scene));
                    stage.showAndWait();
                }

                //If the Part object is an outsourced Part, the user is taken to the modify outsourced part screen
                else {

                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/View_Controller/modifyPartOutsourced.fxml"));
                    loader.load();

                    pController = loader.getController();

                    pController.sendPartOut((OutsourcedPart) partsTableView.getSelectionModel().getSelectedItem());

                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Parent scene = loader.getRoot();
                    stage.setTitle("Modify Outsourced Part");
                    stage.setScene(new Scene(scene));
                    stage.showAndWait();
                }
            }
            else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("Click on an item to modify.");
                alert2.showAndWait();
            }
        } catch (IllegalStateException e) {
            //Ignore
        }
        catch (NullPointerException n) {
            //Ignore
        }
    }

    /** This method searches the Parts Table.
     The Parts Table search box will search for parts based on name first, and if name is null, it will search by id.
     RUNTIME ERROR: This method threw a NullPointerException, which I surrounded in a try/catch block. A LOGICAL ERROR
     that I encountered was figuring out how to set up the alert boxes, which prompt a user that a product/part could
     not be found, and which also generate every time I enter an id number (as opposed to a string name),
     even if the product/part with the entered id number is present in the table.  My solution to this is to prompt
     the user that the part name could not be found, and if a number was entered into the search box, an id
     search would commence automatically.  If the part id was also not found in the table, another pop-up would generate
     to alert the user that the part name AND id could not be located in the table.
     @param actionEvent Typing into the search box and hitting enter will cause this event to happen
     */
    @FXML
    public void onActionSearchParts(ActionEvent actionEvent) {
        String search = searchParts.getText();

        ObservableList<Part> searched = Inventory.lookupPart(search);

        if (searched.size() == 0) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error Message");
            alert1.setContentText("Part name not found. If a number is entered in the search box, an id search will occur");
            alert1.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert1.showAndWait();
            try {
                int id = Integer.parseInt(search);
                Part part1 = Inventory.lookupPart(id);
                if (part1 != null) {
                    searched.add(part1);
                }
                else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Error Message");
                    alert2.setContentText("Part name and id not found.");
                    alert2.showAndWait();
                }
            } catch (NumberFormatException e) {
                //ignore
            }
            catch (NullPointerException n) {
                //ignore
            }
        }
        partsTableView.setItems(searched);
    }

    /**  This method deletes a part from the Parts Table.
     When the delete button under the Parts Table is pressed, the selected part will be deleted from the table.
     RUNTIME ERROR: This method threw a NullPointerException and an IllegalStateException if an item was not
     selected from the table.  To fix this, I surrounded the code in a try/catch block to attempt to run the code
     and catch the exceptions if they occur and ignore them.  From there, I added an alert box to prompt the user
     to select an item to delete.
     @param actionEvent Clicking the delete button will cause this event to happen
     */
    @FXML
    public void onActionDeletePart(ActionEvent actionEvent) throws IOException {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected item?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {


                if(partsTableView.getSelectionModel().getSelectedItem() != null) {
                    Inventory.deletePart(partsTableView.getSelectionModel().getSelectedItem());
                    Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1062, 498);
                    stage.setTitle("Main Screen");
                    stage.setScene(scene);
                    stage.show();
                }
                else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setContentText("Click on an item to delete.");
                    alert2.showAndWait();
                }
            }
        }
        catch (NullPointerException n) {
            //ignore
        }
    }


    /** This method takes user to the Add Products screen.
     The add button under the Products Table will take user to the Add Products screen.
     @param actionEvent Clicking the Add button will cause this action to occur
     */
    @FXML
    public void onActionToAddProductScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/addProduct.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 980, 560);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /** This method will take user to Modify Product screen.
     The modify button under the Products Table will take user to the Modify Products screen.
     RUNTIME ERROR: This method threw a NullPointerException if an item was not selected from the table.  To fix this,
     I surrounded the code in a try/catch block to attempt to run the code and catch the exceptions if they occur and
     ignore them.  From there, I added an alert box to prompt the user to select an item to modify.
     @param actionEvent Clicking the Modify button will cause this event to happen
     */
    @FXML
    public void onActionToModifyProductScreen(ActionEvent actionEvent) throws IOException {

        try {

            //Get product information from the Product Controller in order to populate the modify screen text fields
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View_Controller/modifyProduct.fxml"));
            loader.load();

            ProductController proController = loader.getController();

            if (productsTableView.getSelectionModel().getSelectedItem() != null) {
                proController.sendProduct(productsTableView.getSelectionModel().getSelectedItem());

                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setTitle("Modify In-house Part");
                stage.setScene(new Scene(scene));
                stage.showAndWait();
            }
            else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("Click on an item to modify.");
                alert2.showAndWait();
            }
        } catch (IllegalStateException e) {
            //ignore
        }
        catch (NullPointerException n) {
            //ignore
        }
    }


    /** This method will search for products in the Products Table.
     The Product Table search box will search for a product based on name, and then by id.
     RUNTIME ERROR: This method threw a NullPointerException, which I surrounded in a try/catch block. A LOGICAL ERROR
     that I encountered was figuring out how to set up the alert boxes, which prompt a user that a product could
     not be found, and which also generate every time I enter an id number (as opposed to a string name),
     even if the product with the entered id number is present in the table.  My solution to this is to prompt
     the user that the product name could not be found, and if a number was entered into the search box, an id
     search would commence automatically.  If the product id was also not found in the table, another pop-up would generate
     to alert the user that the product name AND id could not be located in the table.
     @param actionEvent Typing in the search box and hitting enter will cause this action to happen
     */
    public void onActionSearchProducts(ActionEvent actionEvent) {

        try {
            String search = searchProducts.getText();

            ObservableList<Product> searched = Inventory.lookupProduct(search);

            if (searched.size() == 0) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Name not found");
                alert1.setContentText("Product name not found. If a number is entered in the search box, an id search will occur.");
                alert1.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert1.showAndWait();
                try {
                    int id = Integer.parseInt(search);
                    Product product1 = Inventory.lookupProduct(id);
                    if (product1 != null) {
                        searched.add(product1);
                    }
                    else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Error Message");
                        alert2.setContentText("Product name and id not found.");
                        alert2.showAndWait();
                    }
                } catch (NumberFormatException e) {
                    //ignore
                }
            }

            productsTableView.setItems(searched);
        }
        catch (NullPointerException n) {
            //ignore
        }
    }

    /** This method will delete a product from the Products Table.
     Delete button under Products Table will remove selected item from the table.  An alert pop-up will confirm delete.
     RUNTIME ERROR: NullPointerException is handled by wrapping the code in a try/catch block.
     @param actionEvent Clicking the delete button will cause this action to occur
     */
    public void onActionDeleteProduct(ActionEvent actionEvent) throws IOException {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected item?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                if (productsTableView.getSelectionModel().getSelectedItem() != null) {
                    Product product = productsTableView.getSelectionModel().getSelectedItem();
                    if (product.getAllAssociatedParts().size() == 0) {

                        Inventory.deleteProduct(productsTableView.getSelectionModel().getSelectedItem());
                        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 1062, 498);
                        stage.setTitle("Main Screen");
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setContentText("Products with associated parts cannot be deleted.");
                        alert2.showAndWait();
                    }
                }
                else {
                    Alert alert3 = new Alert(Alert.AlertType.ERROR);
                    alert3.setContentText("Click on an item to delete.");
                    alert3.showAndWait();
                }
            }
        }
        catch (NullPointerException n) {
            //ignore
        }
    }
}









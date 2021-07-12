package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
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
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the actions that occur on the add/modify products screens. */
public class ProductController implements Initializable {

    @FXML
    public TableView<Part> partsProductTableView;

    @FXML
    public TableColumn partIDProdCol;

    @FXML
    public TableColumn partNameProdCol;

    @FXML
    public TableColumn partInvProdCol;

    @FXML
    public TableColumn partPriceProdCol;

    @FXML
    public TextField productNameTxt;

    @FXML
    public TextField productInvTxt;

    @FXML
    public TextField productPriceTxt;

    @FXML
    public TextField productMaxTxt;

    @FXML
    public TextField productMinTxt;

    @FXML
    public TextField searchParts;

    @FXML
    public TableView<Part> associatedPartsTableView;

    @FXML
    public Button addAssociatedParts;

    @FXML
    public TableColumn partIDAssocCol;

    @FXML
    public TableColumn partNameAssocCol;

    @FXML
    public TableColumn partInvAssocCol;

    @FXML
    public TableColumn partPriceAssocCol;

    @FXML
    public TextField modProductIDTxt;

    @FXML
    private ObservableList<Part> allAssociatedParts = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Part> modAllAssociatedParts = FXCollections.observableArrayList();


    /** This method initializes the add/modify products screens.
     The Parts Table View and the Associated Parts Table View are initialized here.
     @param url The url
     @param resourceBundle The resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsProductTableView.setItems(Inventory.getAllParts());

        partIDProdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameProdCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvProdCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceProdCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTableView.setItems(allAssociatedParts);
        partIDAssocCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameAssocCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvAssocCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceAssocCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }


    /**This method takes the user back to the main screen upon confirmation.
     This method occurs when cancel buttons are clicked.
     @param actionEvent Clicking a cancel button causes this action to occur
     */
    @FXML
    public void onActionBackToMainScreen(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1062, 498);
            stage.setTitle("Main Form");
            stage.setScene(scene);
            stage.show();
        }
    }


    /** This method adds a product.
     Saves a Product object to the Products Table in the Main Screen upon confirmation from the user.
     RUNTIME ERROR: An error that I encountered with this method was a NumberFormatException error when incorrect
     data types were entered into the text fields.  In order to address this, I wrapped the code in a try/catch block
     to ignore the exception and created an alert box to inform the user to enter valid data into the text fields.
     @param actionEvent Clicking a save button causes this action to occur
     */
    @FXML
    public void onActionSaveProduct(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add the selected item?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {

                int id = PartController.GenerateUniqueID.generateID();

                if (productNameTxt.getText().isEmpty()) {
                    Alert alert5 = new Alert(Alert.AlertType.ERROR);
                    alert5.setContentText("Product must have a name.");
                    alert5.showAndWait();
                }
                else {
                    String name = productNameTxt.getText();
                    Double price = Double.parseDouble(productPriceTxt.getText());


                    if (Integer.parseInt(productMaxTxt.getText()) > Integer.parseInt(productMinTxt.getText()) ||
                            Integer.parseInt(productMaxTxt.getText()) == Integer.parseInt(productMinTxt.getText())) {

                        int max = Integer.parseInt(productMaxTxt.getText());
                        int min = Integer.parseInt(productMinTxt.getText());


                        if (Integer.parseInt(productInvTxt.getText()) <= max && Integer.parseInt(productInvTxt.getText()) >= min) {
                            int stock = Integer.parseInt(productInvTxt.getText());

                            Product product1 = new Product(id, name, price, stock, min, max);

                            //Adds the associated parts to the AssociatedParts Observable List in the Product class
                            for (int i = 0; i < allAssociatedParts.size(); i++) {
                                product1.addAssociatedPart(allAssociatedParts.get(i));
                            }

                            Inventory.addProduct(product1);


                            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
                            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root, 1062, 498);
                            stage.setTitle("Main Form");
                            stage.setScene(scene);
                            stage.show();

                        } else {
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            alert2.setTitle("Error Message");
                            alert2.setContentText("Please enter an inventory amount between min and max");
                            alert2.showAndWait();

                        }
                    } else {
                        Alert alert3 = new Alert(Alert.AlertType.ERROR);
                        alert3.setTitle("Error Message");
                        alert3.setContentText("Maximum inventory must be greater than minimum inventory");
                        alert3.showAndWait();
                    }
                }

            } catch (NumberFormatException | IOException e) {
                Alert alert4 = new Alert(Alert.AlertType.ERROR);
                alert4.setTitle("Error Message");
                alert4.setContentText("Please enter valid part data.");
                alert4.showAndWait();
            }
        }
    }


    /** This method searches for products.
     Searches through the Products in the Product Table by name, then by id.
     RUNTIME ERROR: NullPointerException and NumberFormatException handled with a try/catch block.
     @param actionEvent Typing into the search box and hitting enter will cause this action to occur
     */
    @FXML
    public void onActionSearchParts(ActionEvent actionEvent) {
        String search = searchParts.getText();

        ObservableList<Part> searched = Inventory.lookupPart(search);

        if (searched.size() == 0) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error Message");
            alert1.setContentText("Part name not found. If a number is entered in the search box, an id search will occur.");
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
            } catch (NullPointerException n) {
                //ignore
            }
        }
        partsProductTableView.setItems(searched);
    }


    /** This method adds associated parts to a product.
     Upon confirmation, adds items from the Parts Table to the Associated Parts Table in the Add Product screen.
     @param actionEvent Clicking the add button will cause this action to occur
     */
    @FXML
    public void onActionAddAssociatedParts(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add the selected item?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (partsProductTableView.getSelectionModel().getSelectedItem() != null) {
                Part part1 = partsProductTableView.getSelectionModel().getSelectedItem();
                if (part1 == null)
                    return;

                allAssociatedParts.add(part1);

            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("Click on an item to add.");
                alert2.showAndWait();
            }
        }
    }


    /** This method is used to send Product object information to the other controllers.
     Sets the variables needed to instantiate a product object.
     @param product1 The Product object to be sent to the other controllers
     */
    @FXML
    public void sendProduct(Product product1) {
        modProductIDTxt.setText(String.valueOf(product1.getId()));
        productNameTxt.setText(product1.getName());
        productPriceTxt.setText(String.valueOf(product1.getPrice()));
        productInvTxt.setText(String.valueOf(product1.getStock()));
        productMaxTxt.setText(String.valueOf(product1.getMax()));
        productMinTxt.setText(String.valueOf(product1.getMin()));
        associatedPartsTableView.setItems(product1.getAllAssociatedParts());
        for (int i = 0; i < associatedPartsTableView.getItems().size(); i++) {
            allAssociatedParts.add(product1.getAllAssociatedParts().get(i));
        }
    }



    /** This method removes associated parts.
     Upon confirmation, removes an associate part from the Associated Parts Table in the Add Product Screen.
     RUNTIME ERROR:  NullPointerException handled by wrapping the code in a try/catch block.
     @param actionEvent Clicking the Remove button will cause this action to occur
     */
    @FXML
    public void onActionRemoveAssociatedPart(ActionEvent actionEvent) throws IOException, NullPointerException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected item?");

        try {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                if (associatedPartsTableView.getSelectionModel().getSelectedItem() != null) {

                    allAssociatedParts.remove(associatedPartsTableView.getSelectionModel().getSelectedItem());

                    Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/addProducts.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 980, 560);
                    stage.setTitle("Main Screen");
                    stage.setScene(scene);
                    stage.show();
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setContentText("Click on an item to delete.");
                    alert2.showAndWait();
                }
            }
        } catch (NullPointerException e) {
            //ignore
        }
    }


    /** This method removes associated parts.
     Upon confirmation, removes an associate part from the Associated Parts Table in the Modify Product Screen.
     RUNTIME ERROR:  NullPointerException handled by wrapping the code in a try/catch block.
     @param actionEvent Clicking the Remove button will cause this action to occur
     */
    @FXML
    public void onActionRemoveModAssociatedPart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected item?");

        try {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                if (associatedPartsTableView.getSelectionModel().getSelectedItem() != null) {

                    allAssociatedParts.remove(associatedPartsTableView.getSelectionModel().getSelectedItem());
                    associatedPartsTableView.setItems(allAssociatedParts);

                    Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/modifyProducts.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 980, 560);
                    stage.setTitle("Main Screen");
                    stage.setScene(scene);
                    stage.show();
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setContentText("Click on an item to delete.");
                    alert2.showAndWait();
                }
            }
        } catch (NullPointerException | IOException e) {
            //ignore
        }
    }


    /** This method adds associated parts to a product.
     Upon confirmation, adds items from the Parts Table to the Associated Parts Table in the Modify Product screen.
     @param actionEvent Clicking the add button will cause this action to occur
     */
    @FXML
    public void onActionAddModAssociatedParts(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add the selected item?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (partsProductTableView.getSelectionModel().getSelectedItem() != null) {
                Part part1 = partsProductTableView.getSelectionModel().getSelectedItem();
                if (part1 == null)
                    return;

                allAssociatedParts.add(part1);
                associatedPartsTableView.setItems(allAssociatedParts);

            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("Click on an item to add.");
                alert2.showAndWait();
            }
        }
    }

    /** This method modifies a product.
     Saves an updated Product object to the Products Table in the Main Screen upon confirmation from the user.
     RUNTIME ERROR:  A LOGICAL ERROR that I encountered when writing this method was trying to figure out how to
     populate the Modify Product text fields with information from the selected product. I used the same
     reasoning that I used for populating the Modify Part screens (see description in PartController) by using a helper
     method (sendProduct) to populate the text fields. However, I encountered problems with getting the associated parts
     table to populate correctly when clicking on a product to modify without either deleting all of the parts
     associated with a product, or upon saving, having duplicate product entries in the products table. It took some
     trial and error, but I discovered that one of the problems I was having was based on the fact that I was saving
     associated parts data into the correct observable list, but I was not updating the associated parts table view in
     order to reflect those changes.  Once I corrected that error, I was able to correctly populate the associated parts
     table with information pertaining to the product, as well as add and delete associated parts for the product.
     @param actionEvent Clicking a save button causes this action to occur
     */
    @FXML
    public void onActionModifyProduct(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add the selected item?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {

                int id = Integer.parseInt(modProductIDTxt.getText());

                if (productNameTxt.getText().isEmpty()) {
                    Alert alert5 = new Alert(Alert.AlertType.ERROR);
                    alert5.setContentText("Product must have a name.");
                    alert5.showAndWait();
                }
                else {
                    String name = productNameTxt.getText();
                    Double price = Double.parseDouble(productPriceTxt.getText());


                    if (Integer.parseInt(productMaxTxt.getText()) > Integer.parseInt(productMinTxt.getText()) ||
                            Integer.parseInt(productMaxTxt.getText()) == Integer.parseInt(productMinTxt.getText())) {

                        int max = Integer.parseInt(productMaxTxt.getText());
                        int min = Integer.parseInt(productMinTxt.getText());


                        if (Integer.parseInt(productInvTxt.getText()) <= max && Integer.parseInt(productInvTxt.getText()) >= min) {
                            int stock = Integer.parseInt(productInvTxt.getText());

                            Product product1 = new Product(id, name, price, stock, min, max);

                            //Adds the associated parts to the AssociatedParts Observable List in the Product class
                            for (int i = 0; i < allAssociatedParts.size(); i++) {
                                product1.addAssociatedPart(allAssociatedParts.get(i));
                            }

                            Inventory.updateProduct(id, product1);


                            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
                            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root, 1062, 498);
                            stage.setTitle("Main Form");
                            stage.setScene(scene);
                            stage.show();

                        } else {
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            alert2.setTitle("Error Message");
                            alert2.setContentText("Please enter an inventory amount between min and max");
                            alert2.showAndWait();

                        }
                    } else {
                        Alert alert3 = new Alert(Alert.AlertType.ERROR);
                        alert3.setTitle("Error Message");
                        alert3.setContentText("Maximum inventory must be greater than minimum inventory");
                        alert3.showAndWait();
                    }
                }

            } catch (NumberFormatException | IOException e) {
                Alert alert4 = new Alert(Alert.AlertType.ERROR);
                alert4.setTitle("Error Message");
                alert4.setContentText("Please enter valid part data.");
                alert4.showAndWait();
            }
        }
    }

}


package View_Controller;
import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.InvalidPropertiesFormatException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;


/** This class controls the actions that occur on the add/modify parts screens. */
public class PartController implements Initializable {

    @FXML
    public RadioButton modPartInhouseRad;

    @FXML
    public RadioButton modPartOutsourcedRad;

    @FXML
    public TextField modifyIDTXT;

    @FXML
    public TextField modifyNameITxt;

    @FXML
    public TextField modifyInvITxt;

    @FXML
    public TextField modifyPriceITxt;

    @FXML
    public TextField modifyMaxITxt;

    @FXML
    public TextField modMachineIDTxt;

    @FXML
    public TextField modifyPartMinITxt;

    @FXML
    public TextField modifyIDOTxt;

    @FXML
    public TextField modifyNameOTxt;

    @FXML
    public TextField modifyInvOTxt;

    @FXML
    public TextField modifyPriceOTxt;

    @FXML
    public TextField modifyMaxOTxt;

    @FXML
    public TextField modCompanyNameTxt;

    @FXML
    public TextField modifyMinOTxt;

    @FXML
    public RadioButton outSourcedRadio;

    @FXML
    public Label machineIDModLabel;

    @FXML
    public Label companyNameModLabel;

    @FXML
    public TextField idTxt;

    @FXML
    private TextField partNameOTxt;

    @FXML
    private TextField partInvOTxt;

    @FXML
    private TextField partPriceOTxt;

    @FXML
    private TextField partMaxOTxt;

    @FXML
    private TextField companyNameTxt;

    @FXML
    private TextField partMinOTxt;

    @FXML
    private TextField partNameITxt;

    @FXML
    private TextField partInvITxt;

    @FXML
    private TextField partPriceITxt;

    @FXML
    private TextField partMaxITxt;

    @FXML
    private TextField machineIDTxt;

    @FXML
    private TextField partMinITxt;


    /**
     This class generates unique id numbers.
     */
    public static class GenerateUniqueID {
        private static final AtomicInteger newID = new AtomicInteger();

        /**
         This method generates unique id numbers.
         Used to generate unique id numbers for parts and products.
         @return Returns a new, unique int id number
         */
        public static int generateID() {
            return newID.getAndIncrement();
        }
    }


    /**
     This method saves an In-house part.
     The save button in the Add In-house Part screen will add a new part to the Parts Table upon confirmation.
     RUNTIME ERROR:  A runtime error that I encountered with this method was to figure out how to generate unique ID
     numbers for each part/product.  At first, I tried assigning id numbers based on the size of the observable list,
     which worked unless parts/products were deleted, and then the method generated duplicate numbers. I did some
     research and discovered the AtomicInteger class, which is an incremented counter that generates a unique number
     each time it is called. I assigned an AtomicInteger object to the ID variable, which resulted in each part/product
     object having a unique ID regardless of the size of the observable list.
     @param actionEvent Clicking the Save button will cause this action to occur
     */
    @FXML
    public void onActionSavePart(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add the selected item?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            try {

                int id = GenerateUniqueID.generateID();

                if (partNameITxt.getText().isEmpty()) {
                    Alert alert5 = new Alert(Alert.AlertType.ERROR);
                    alert5.setContentText("Part must have a name.");
                    alert5.showAndWait();
                } else {
                    String name = partNameITxt.getText();
                    Double price = Double.parseDouble(partPriceITxt.getText());

                    //If the value of the Max field is greater than the value of the Min field,
                    // or if Max and Min are equal, set Max and Min
                    if (Integer.parseInt(partMaxITxt.getText()) > Integer.parseInt((partMinITxt.getText())) ||
                            Integer.parseInt(partMaxITxt.getText()) == Integer.parseInt((partMinITxt.getText()))) {
                        int max = Integer.parseInt(partMaxITxt.getText());
                        int min = Integer.parseInt(partMinITxt.getText());

                        //If the value of the inventory in Stock is between the Max and Min values
                        if (Integer.parseInt(partInvITxt.getText()) <= max && Integer.parseInt(partInvITxt.getText()) >= min) {
                            int stock = Integer.parseInt(partInvITxt.getText());


                            int machineID = Integer.parseInt(machineIDTxt.getText());

                            Inventory.addPart(new InhousePart(id, name, price, stock, min, max, machineID));

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

            } catch (NumberFormatException e) {
                Alert alert4 = new Alert(Alert.AlertType.ERROR);
                alert4.setTitle("Error Message");
                alert4.setContentText("Please enter valid part data.");
                alert4.showAndWait();
            }
        }
    }


    /**
     This method returns user to the main screen.
     This method is attached to all cancel buttons in the Parts section and takes the user back to the main screen upon confirmation.
     @param actionEvent Clicking the Cancel button will cause this action to occur
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


    /**
    This method takes the user to the Add Outsourced Part screen.
    Takes user to the Add Outsourced Part screen via the radio buttons.
    @param actionEvent Clicking a radio button will cause this action to occur
     */
    @FXML
    public void onActionToAddOutsourcedPartScreen(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/addPartOutsourced.fxml"));
        Stage stage = (Stage) ((RadioButton) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 464);
        stage.setTitle("Outsourced Parts");
        stage.setScene(scene);
        stage.show();
    }


    /**
    This method takes the user to the Add In-house Parts screen.
    Takes user back to Add In-house Part screen via the radio buttons.
    @param actionEvent Clicking a radio button will cause this action to occur
     */
    @FXML
    public void onActionToAddPartInhouseScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/addPartInhouse.fxml"));
        Stage stage = (Stage) ((RadioButton) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 464);
        stage.setTitle("In-house Parts");
        stage.setScene(scene);
        stage.show();
    }


    /**
     This method saves an Outsourced part.
     Adds an outsourced part, upon confirmation from the user.
     RUNTIME ERROR:  NumberFormatException wrapping in a try/catch block, and an alert box informs the user to enter
     valid data.
     @param actionEvent Clicking the save button causes this action to occur
     */
    @FXML
    public void onActionAddPartOutsourced(ActionEvent actionEvent) throws IOException {

        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add the selected item?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                int id = GenerateUniqueID.generateID();

                if (partNameOTxt.getText().isEmpty()) {
                    Alert alert5 = new Alert(Alert.AlertType.ERROR);
                    alert5.setContentText("Part must have a name.");
                    alert5.showAndWait();
                } else {


                    String name = partNameOTxt.getText();
                    Double price = Double.parseDouble(partPriceOTxt.getText());

                    if (Integer.parseInt(partMaxOTxt.getText()) > Integer.parseInt(partMinOTxt.getText()) ||
                            Integer.parseInt(partMaxOTxt.getText()) == Integer.parseInt(partMinOTxt.getText())) {
                        int max = Integer.parseInt(partMaxOTxt.getText());
                        int min = Integer.parseInt(partMinOTxt.getText());

                        if (Integer.parseInt(partInvOTxt.getText()) <= max && Integer.parseInt(partInvOTxt.getText()) >= min) {
                            int stock = Integer.parseInt(partInvOTxt.getText());

                            String company = companyNameTxt.getText();

                            Inventory.addPart(new OutsourcedPart(id, name, price, stock, min, max, company));

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
            }
        } catch (NumberFormatException e) {
            Alert alert4 = new Alert(Alert.AlertType.ERROR);
            alert4.setTitle("Error Message");
            alert4.setContentText("Please enter valid part data.");
            alert4.showAndWait();
        }
    }


    /**
     This method takes the user to the Modify In-house Parts screen.
     Takes the user to the Modify In-house Part screen when radio button is toggled.
     RUNTIME ERROR: A LOGICAL ERROR that I had with creating this method was trying to figure out how to get the
     variables from the outsourced part text fields to display in the text fields of the Modify In-house Parts screen.
     I kept receiving a NullPointerException. My solution to this was to simply stay on the current Modify Outsourced
     Parts screen, but change the "Company Name" label to "Machine ID" when the radio button is toggled.  Then, I
     include logic in another method (OnActionSaveModPartOutsourced) which saves the part as either in-house or
     outsourced depending on which button is toggled.
     @param actionEvent Clicking a radio button would cause this action to occur
     */
    @FXML
    public void OnActionToModPartInhouse(ActionEvent actionEvent) throws IOException {
        if (machineIDModLabel != null) {
            machineIDModLabel.setText("Machine ID");
        }

        if (companyNameModLabel != null) {
            companyNameModLabel.setText("Machine ID");
        }
    }


    /**
     This method saves changes to a modified part.
     Saves changes to a modified In-house part upon confirmation from the user.
     RUNTIME ERROR:  A LOGICAL ERROR that I encountered when writing this method was trying to figure out how to
     populate the Modify Part text fields with information from the selected part. To solve this,
     I created "helper" methods (see SendPart and SendPartOut methods, which send the part information from the tableview
     in the Main Screen) to set the text fields in the Modified Part screens with the part data for each variable.
     Those text fields would be populated with the data upon loading of the Modify Part screens. Then I created another
     method to "get" the pre-populated text from each field in order to set the variables for the updated parts.
     @param actionEvent Clicking the save button causes this action to occur
     */
    @FXML
    public void OnActionSaveModPartInhouse(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to modify the selected item?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            try {
                if (modPartInhouseRad.isSelected()) {
                    int id = Integer.parseInt(modifyIDTXT.getText());

                    if (modifyNameITxt.getText().isEmpty()) {
                        Alert alert5 = new Alert(Alert.AlertType.ERROR);
                        alert5.setContentText("Part must have a name.");
                        alert5.showAndWait();
                    } else {
                        String name = modifyNameITxt.getText();
                        Double price = Double.parseDouble(modifyPriceITxt.getText());

                        if (Integer.parseInt(modifyMaxITxt.getText()) > Integer.parseInt(modifyPartMinITxt.getText()) ||
                                Integer.parseInt(modifyMaxITxt.getText()) == Integer.parseInt(modifyPartMinITxt.getText())) {
                            int max = Integer.parseInt(modifyMaxITxt.getText());
                            int min = Integer.parseInt(modifyPartMinITxt.getText());

                            if (Integer.parseInt(modifyInvITxt.getText()) <= max && Integer.parseInt(modifyInvITxt.getText()) >= min) {
                                int stock = Integer.parseInt(modifyInvITxt.getText());

                                int machineID = Integer.parseInt(modMachineIDTxt.getText());

                                Part modifiedPart = new InhousePart(id, name, price, stock, min, max, machineID);

                                Inventory.updatePart(id, modifiedPart);


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
                } else {

                    int id2 = Integer.parseInt(modifyIDTXT.getText());

                    if (modifyNameITxt.getText().isEmpty()) {
                        Alert alert5 = new Alert(Alert.AlertType.ERROR);
                        alert5.setContentText("Part must have a name.");
                        alert5.showAndWait();
                    } else {
                        String name2 = modifyNameITxt.getText();

                        Double price2 = Double.parseDouble(modifyPriceITxt.getText());

                        if (Integer.parseInt(modifyMaxITxt.getText()) > Integer.parseInt(modifyPartMinITxt.getText()) ||
                                Integer.parseInt(modifyMaxITxt.getText()) == Integer.parseInt(modifyPartMinITxt.getText())) {
                            int max2 = Integer.parseInt(modifyMaxITxt.getText());
                            int min2 = Integer.parseInt(modifyPartMinITxt.getText());

                            if (Integer.parseInt(modifyInvITxt.getText()) <= max2 && Integer.parseInt(modifyInvITxt.getText()) >= min2) {
                                int stock2 = Integer.parseInt(modifyInvITxt.getText());

                                String company = modMachineIDTxt.getText();

                                Inventory.deletePart(Objects.requireNonNull(Inventory.lookupPart(Integer.parseInt(modifyIDTXT.getText()))));

                                Inventory.addPart(new OutsourcedPart(id2, name2, price2, stock2, min2, max2, company));
                                //System.out.println(id2 + " " + name2 + " " + price2 + " " + stock2 + " " + min2 + " " + max2 + " " + company);

                                Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
                                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                                Scene scene = new Scene(root, 1062, 498);
                                stage.setTitle("Main Form");
                                stage.setScene(scene);
                                stage.show();
                            }
                        else {
                                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                                alert2.setTitle("Error Message");
                                alert2.setContentText("Please enter an inventory amount between min and max");
                                alert2.showAndWait();
                            }

                    }
                        else {
                            Alert alert3 = new Alert(Alert.AlertType.ERROR);
                            alert3.setTitle("Error Message");
                            alert3.setContentText("Maximum inventory must be greater than minimum inventory");
                            alert3.showAndWait();
                        }
                    }
                }
            } catch (NumberFormatException e) {
                Alert alert4 = new Alert(Alert.AlertType.ERROR);
                alert4.setTitle("Error Message");
                alert4.setContentText("Please enter valid part data.");
                alert4.showAndWait();
            }
        }
    }

    /**
    This method takes user to the Modify Outsourced Parts screen.
    Takes the user to the Modify Outsourced Part screen when radio button is toggled.
     RUNTIME ERROR: A LOGICAL ERROR that I had with creating this method was trying to figure out how to get the
     variables from the in-house part text fields to display in the text fields of the Modify Outsourced Parts screen.
     I kept receiving a NullPointerException. My solution to this was to simply stay on the current Modify In-house
     Parts screen, but change the "Machine ID" label to "Company Name" when the radio button is toggled.  Then, I
     include logic in another method (OnActionSaveModPartInhouse) which saves the part as either in-house or
     outsourced depending on which button is toggled.
    @param actionEvent Clicking a radio button would cause this action to occur
     */
    @FXML
    public void onActionToModPartOutsourced(ActionEvent actionEvent) throws IOException {
        if (machineIDModLabel != null) {
            machineIDModLabel.setText("Company Name");
            // System.out.println(modifyIDTXT.getText());
        }

        if (companyNameModLabel != null) {
            companyNameModLabel.setText("Company Name");
        }

    }


    /**
     This method saves changes to a modified part.
     Saves changes to a modified Outsourced part upon confirmation from the user.
     RUNTIME ERROR:  A LOGICAL ERROR that I encountered when writing this method was trying to figure out how to
     populate the Modify Part text fields with information from the selected part. To solve this,
     I created "helper" methods (see SendPart and SendPartOut methods, which send the part information from the tableview
     in the Main Screen) to set the text fields in the Modified Part screens with the part data for each variable.
     Those text fields would be populated with the data upon loading of the Modify Part screens. Then I created another
     method to "get" the pre-populated text from each field in order to set the variables for the updated parts.
     @param actionEvent Clicking the save button causes this action to occur
     */
    @FXML
    public void OnActionSaveModPartOutsourced(ActionEvent actionEvent) throws IOException {

        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to modify the selected item?");
            Optional<ButtonType> result = alert.showAndWait();


                if (result.isPresent() && result.get() == ButtonType.OK) {

                    if (outSourcedRadio.isSelected()) {
                        if (outSourcedRadio.isSelected()) {
                            int id = Integer.parseInt(modifyIDOTxt.getText());

                            if (modifyNameOTxt.getText().isEmpty()) {
                                Alert alert5 = new Alert(Alert.AlertType.ERROR);
                                alert5.setContentText("Part must have a name.");
                                alert5.showAndWait();
                            } else {
                                String name = modifyNameOTxt.getText();
                                Double price = Double.parseDouble(modifyPriceOTxt.getText());


                                if (Integer.parseInt(modifyMaxOTxt.getText()) > Integer.parseInt(modifyMinOTxt.getText()) ||
                                        Integer.parseInt(modifyMaxOTxt.getText()) == Integer.parseInt(modifyMinOTxt.getText())) {


                                    int max = Integer.parseInt(modifyMaxOTxt.getText());
                                    int min = Integer.parseInt(modifyMinOTxt.getText());

                                    if (Integer.parseInt(modifyInvOTxt.getText()) <= max && Integer.parseInt(modifyInvOTxt.getText()) >= min) {
                                        int stock = Integer.parseInt(modifyInvOTxt.getText());

                                        String company = modCompanyNameTxt.getText();

                                        Part part1 = new OutsourcedPart(id, name, price, stock, min, max, company);


                                        Inventory.updatePart(id, part1);

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
                        }
                    } else {
                        int id2 = Integer.parseInt(modifyIDOTxt.getText());

                        if (modifyNameOTxt.getText().isEmpty()) {
                            Alert alert5 = new Alert(Alert.AlertType.ERROR);
                            alert5.setContentText("Part must have a name.");
                            alert5.showAndWait();
                        } else {
                            String name2 = modifyNameOTxt.getText();
                            Double price2 = Double.parseDouble(modifyPriceOTxt.getText());


                            if (Integer.parseInt(modifyMaxOTxt.getText()) > Integer.parseInt(modifyMinOTxt.getText()) ||
                                    Integer.parseInt(modifyMaxOTxt.getText()) == Integer.parseInt(modifyMinOTxt.getText())) {


                                int max2 = Integer.parseInt(modifyMaxOTxt.getText());
                                int min2 = Integer.parseInt(modifyMinOTxt.getText());

                                if (Integer.parseInt(modifyInvOTxt.getText()) <= max2 && Integer.parseInt(modifyInvOTxt.getText()) >= min2) {
                                    int stock2 = Integer.parseInt(modifyInvOTxt.getText());

                                    int machine = Integer.parseInt(modCompanyNameTxt.getText());


                                    Inventory.deletePart(Objects.requireNonNull(Inventory.lookupPart(Integer.parseInt(modifyIDOTxt.getText()))));

                                    Inventory.addPart(new InhousePart(id2, name2, price2, stock2, min2, max2, machine));
                                    //System.out.println(id2 + " " + name2 + " " + price2 + " " + stock2 + " " + min2 + " " + max2 + " " + machine);

                                    Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
                                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                                    Scene scene = new Scene(root, 1062, 498);
                                    stage.setTitle("Main Form");
                                    stage.setScene(scene);
                                    stage.show();

                                }
                                else {
                                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                                    alert2.setTitle("Error Message");
                                    alert2.setContentText("Please enter an inventory amount between min and max");
                                    alert2.showAndWait();
                                }
                            }
                            else {
                                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                                alert3.setTitle("Error Message");
                                alert3.setContentText("Maximum inventory must be greater than minimum inventory");
                                alert3.showAndWait();
                            }
                        }
                    }
                }
        } catch (NumberFormatException e) {
            Alert alert4 = new Alert(Alert.AlertType.ERROR);
            alert4.setTitle("Error Message");
            alert4.setContentText("Please enter valid part data.");
            alert4.showAndWait();
        }
    }



    /** This method is used to send In-house Part object data to the other controllers.
     Sets the variables needed to instantiate Part objects.
     @param part1 The Part object being sent to other controllers
     */
    public void sendPart(InhousePart part1) {
        modifyIDTXT.setText(String.valueOf(part1.getId()));
        modifyNameITxt.setText(part1.getName());
        modifyPriceITxt.setText(String.valueOf(part1.getPrice()));
        modifyInvITxt.setText(String.valueOf(part1.getStock()));
        modifyMaxITxt.setText(String.valueOf(part1.getMax()));
        modifyPartMinITxt.setText(String.valueOf(part1.getMin()));
        modMachineIDTxt.setText(String.valueOf(part1.getMachineID()));
    }


    /** This method is used to send Outsourced Part object data to the other controllers.
     Sets the variables needed to instantiate Part objects.
     @param part1 The Part object being sent to other controllers
     */
    public void sendPartOut(OutsourcedPart part1) {
        modifyIDOTxt.setText(String.valueOf(part1.getId()));
        modifyNameOTxt.setText(part1.getName());
        modifyPriceOTxt.setText(String.valueOf(part1.getPrice()));
        modifyInvOTxt.setText(String.valueOf(part1.getStock()));
        modifyMaxOTxt.setText(String.valueOf(part1.getMax()));
        modifyMinOTxt.setText(String.valueOf(part1.getMin()));
        modCompanyNameTxt.setText(String.valueOf(part1.getCompanyName()));
    }

    /** This method initializes the data needed for the add/modify parts screens.
     Currently empty; no data needs to be initialized from this controller.
     @param url The url
     @param resourceBundle The resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}


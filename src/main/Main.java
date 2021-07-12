package main;

import Model.InhousePart;
import Model.Inventory;
import Model.Part;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/** This class (Main) creates an inventory system application.
 JAVADOC LOCATION:  JavaDoc is located in the the zipped file submitted for this project, in a folder named "javadoc."
 FUTURE ENHANCEMENT: If this application were to be updated, a future enhancement would be to allow the user to choose
 a part or product to modify directly from the Modify Parts/Products screens--perhaps by having a table of all of the
 parts/products in inventory on the modify screen (essentially combining the main screen and modify screens). This would
 save the user from having to constantly toggle back and forth between the main screen and the modify screens, or between
 the in-house and outsourced screens.  Another enhancement would be to allow for modifications to occur directly from
 the table view by making the table columns editable. The user could click on the part/product he or she would like to
 modify and could type the modifications directly into the table from the main screen, again preventing the constant
 toggling back and forth between screens.
 RUNTIME ERRORS/LOGIC ERRORS: Detailed descriptions of logic and runtime errors are included in the JavaDoc comments
 above the methods in which they occurred. Most of the logic errors I encountered consisted of getting table views and
 text fields to populate with part or product data in the modify screens.  I included solutions that I used in the
 JavaDoc comments for each of the corresponding methods where the problems occurred.  Most runtime errors I encountered
 were handled by wrapping the necessary code in a try/catch block. I also include descriptions of the runtime errors
 in the JavaDoc method comments.
 */
public class Main extends Application {

    /** This method initializes the main screen.
     The table views for the parts and product tables on the main screen are displayed.
     @param primaryStage Sets the first scene, which is the main screen */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
        primaryStage.setTitle("Main Form");
        primaryStage.setScene(new Scene(root, 1062, 498));
        primaryStage.show();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Welcome! Click the 'Add' buttons to add new parts or products to inventory.");
        alert.setTitle("Welcome to the Terri Hurd Inventory System!");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    /** This method launches the app.
     This is the starting point of the application.
     @param args Takes in all of the arguments/methods created by the app */
    public static void main(String[] args) {

        launch(args);
    }
}

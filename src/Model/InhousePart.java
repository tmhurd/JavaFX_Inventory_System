package Model;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;



/** This class specifies the parameters for an in-house part.  Extends the Part class.*/
    public class InhousePart extends Part {

        private int machine;

        /** This method creates the default constructor for the in-house part.
         The default constructor takes in the variables need to instantiate an in-house part.
         @param id In-house part id
         @param name In-house part name
         @param price In-house part price
         @param stock In-house part inventory level
         @param min Minimum inventory level
         @param max Maximum inventory level
         @param machineID In-house part machine id */
        public InhousePart(int id, String name, double price, int stock, int min, int max, int machineID) {
            super(id, name, price, stock, min, max);
            machine = machineID;
        }

        /**Setter method for machineID.
         Takes in a variable and sets that variable as the machine ID.
         @param machineID The machine ID */
        public void setMachineID(int machineID) {
            machine = machineID;
        }

        /**Getter method for machineID.
         Returns the machine ID of an In-house part when called.
         @return Returns machine ID*/
        public int getMachineID() {
            return machine;
        }

    }




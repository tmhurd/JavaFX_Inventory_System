package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class specifies the parameters for a product. */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** This method creates the default constructor for the product.
     The default constructor takes in the variables need to instantiate a product.
     @param id Product id
     @param name Product name
     @param price Product price
     @param stock Product inventory level
     @param min Minimum inventory level
     @param max Maximum inventory level
     */
    public Product (int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /** Getter method.
     Returns the product id when called.
     @return the id
     */
    public int getId() {
        return id;
    }

    /** Setter method.
     Sets the product id when called.
     @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter method.
     Gets the product name when called.
     @return the name
     */
    public String getName() {
        return name;
    }

    /** Setter method.
     Sets the product name when called.
     @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter method.
     Gets the product price when called.
     @return the price
     */
    public double getPrice() {
        return price;
    }

    /** Setter method.
     Sets the product price when called.
     @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Getter method.
     Gets the product inventory level when called.
     @return the stock
     */
    public int getStock() {
        return stock;
    }

    /** Setter method.
     Sets the product inventory level when called.
     @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Getter method.
     Gets the minimum number of products needed in inventory.
     @return the min
     */
    public int getMin() {
        return min;
    }

    /** Setter method.
     Sets the minimum number of products needed in inventory.
     @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** Getter method.
     Gets the maximum number of products that can be in inventory when called.
     @return the max
     */
    public int getMax() {
        return max;
    }

    /** Setter method.
     Sets the maximum number of products that can be in inventory when called.
     @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }


    /**This method adds associated parts to a product.
     Adds associated parts selected by the user to the associatedParts Observable List.
     @param part The part to be added to associated parts list
     */
    public void addAssociatedPart(Part part){

        associatedParts.add(part);
    }


    /** This method deletes an associated part.
     Deletes associated parts selected by the user from the associatedParts Observable List.
     @param selectedAssociatedPart The part selected for deletion from the list
     @return Returns a boolean value
     */
    public boolean deleteAssociatePart(Part selectedAssociatedPart) {

        for (int i = 0; i < associatedParts.size(); i++) {
            Part partToRemove = associatedParts.get(i);


            if (selectedAssociatedPart == partToRemove) {
                return associatedParts.remove(partToRemove);
            }

        }
        return false;
    }


    /** This method returns a list of associated parts.
     Returns a list of all the associated parts held in the associatedParts Observable List that are associated with a product
     @return Returns all of the associated parts connected to a product
     */
    public ObservableList<Part> getAllAssociatedParts() {

        return associatedParts;
    }
}

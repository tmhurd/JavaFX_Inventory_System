package Model;

import java.text.DecimalFormat;

/** This class is used to create a Part object. */
public abstract class Part {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** This method creates the default constructor for a Part object.
     The method takes in variables needed to instantiate a Part object.
     @param id part id
     @param name part name
     @param price part price
     @param stock part inventory level
     @param min Minimum inventory level
     @param max Maximum inventory level
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Getter method.
     Returns the part id when called.
     @return the id
     */
    public int getId() {
        return id;
    }

    /** Setter method.
     Sets the part id when called.
     @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter method.
     Gets the part name when called.
     @return the name
     */
    public String getName() {
        return name;
    }

    /** Setter method.
     Sets the part name when called.
     @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter method.
     Gets the part price when called.
     @return the price
     */
    public double getPrice() { return price; }

    /** Setter method.
     Sets the part price when called.
     @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Getter method.
     Gets the part inventory level when called.
     @return the stock
     */
    public int getStock() {
        return stock;
    }

    /** Setter method.
     Sets the part inventory level when called.
     @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Getter method.
     Gets the minimum number of parts needed in inventory.
     @return the min
     */
    public int getMin() {
        return min;
    }

    /** Setter method.
     Sets the minimum number of parts needed in inventory.
     @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** Getter method.
     Gets the maximum number of parts that can be in inventory when called.
     @return the max
     */
    public int getMax() {
        return max;
    }

    /** Setter method.
     Sets the maximum number of parts that can be in inventory when called.
     @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

}

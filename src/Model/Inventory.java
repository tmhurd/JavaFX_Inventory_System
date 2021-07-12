package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class creates the Observable List arrays that will hold part and product objects and creates methods
 that access and manipulate the data held in the lists. */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /** This method adds parts.
     Adds parts to the allParts Observable List so that they can be displayed in the table on the main screen.
     @param newPart The new part being added to the list
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }


    /** This method adds products.
     Adds products to the allProducts Observable List so that they can be displayed in the table on the main screen.
     @param newProduct The new product being added
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }


    /** Getter method.
     Returns a list of all of the Part objects stored in the allParts Observable List.
     @return Returns all the parts in the allParts Observable list
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }


    /** Getter method.
     Returns a list of all the Product objects stored in the allProducts Observable List.
     @return Returns all the products in the allProducts Observable list
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


    /** This method looks up a part by id number.
     Returns a part from the allParts List based on the part's id.
     @param partID The part's id number
     @return Returns a Part object
     */
    public static Part lookupPart(int partID) {
        ObservableList<Part> allAddedParts = Inventory.getAllParts();

        for (int i = 0; i < allAddedParts.size(); i++) {
            Part part1 = allAddedParts.get(i);


            if (part1.getId() == partID) {
                return part1;
            }
        }
        return null;
    }


    /** This method looks up a product by id number.
     Returns a product from the allProducts List based on the product's id.
     @param productID The product's id number
     @return Returns a Product object
     */
    public static Product lookupProduct(int productID) {
        ObservableList<Product> allAddedProducts = Inventory.getAllProducts();

        for (int i = 0; i < allAddedProducts.size(); i++) {
            Product product1 = allAddedProducts.get(i);

            if (product1.getId() == productID) {
                return product1;
            }
        }
        return null;
    }


    /** This method looks up a part by name.
     Returns a part from the allParts List based on the part's name.
     @param partName The part's name
     @return Returns an ObservableList
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> requestedParts = FXCollections.observableArrayList();
        ObservableList<Part> allAddedParts = Inventory.getAllParts();

        for (Part searchedPart : allAddedParts) {
            if (searchedPart.getName().contains(partName) || searchedPart.getName().equalsIgnoreCase(partName)) {
                requestedParts.add(searchedPart);
            }
        }
        return requestedParts;
    }


    /** This method looks up a product by name.
     Returns a product from the allProducts List based on the product's name.
     @param productName The product's name
     @return Returns an ObservableList
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> requestedProduct = FXCollections.observableArrayList();
        ObservableList<Product> allAddedProducts = Inventory.getAllProducts();
        for (Product searchedProduct : allAddedProducts) {
            if (searchedProduct.getName().contains(productName) || searchedProduct.getName().equalsIgnoreCase(productName)) {
                requestedProduct.add(searchedProduct);
            }
        }
        return requestedProduct;
    }


    /** This method deletes a part.
     Deletes a part from the allParts list, and it will no longer be viewable in the table on the main screen
     @param selectedPart The part selected for deletion
     @return Returns a boolean value
     */
    public static boolean deletePart(Part selectedPart) {
        Part partToRemove = lookupPart(selectedPart.getId());

        if (selectedPart == partToRemove) {
            return Inventory.getAllParts().remove(partToRemove);
        }

        return false;
    }


    /** This method deletes a product.
     Deletes a product from the allProducts list, and it will no longer be viewable in the table on the main screen
     @param selectedProduct The product selected for deletion
     @return Returns a boolean value
     */
    public static boolean deleteProduct(Product selectedProduct) {
        Product productToRemove = lookupProduct(selectedProduct.getId());

        if (selectedProduct == productToRemove) {
            return Inventory.getAllProducts().remove(productToRemove);
        }

        return false;
    }


    /** This method updates a part.
     Updates a part in the allParts list if the part selected by the user matches the id of a part held in the list
     @param index The part id
     @param selectedPart The part selected to update
     */
    public static void updatePart(int index, Part selectedPart) {

        Part partToRemove = lookupPart(selectedPart.getId());

        if (selectedPart.getId() == partToRemove.getId()) {
            Inventory.getAllParts().remove(partToRemove);
            allParts.add(selectedPart);
        }
    }


    /** This method updates a product.
     Updates a Product object if the Product selected by the user from the table matches the id of a Product held in the list.
     RUNTIME ERROR:  An error that I encountered associated with this method was that when I updated a product, there
     would be duplicate entries for the same product.  I realized that I not only had to add the new product to the
     allProducts list, but I also had to delete the former product that utilized the same id as the updated product.
     Initially, I attempted to use allProducts.set(index, newProduct), which would replace the old product with the new.
     However, I was unable to get that to work--I think this is because I used AtomicInteger to set the indices of the
     products instead of using something like an arraylist that would keep track of each product's index.
     @param index The product id
     @param newProduct The product selected to update
     */
    public static void updateProduct(int index, Product newProduct) {

        Product productToRemove = lookupProduct(index);

        if (newProduct.getId() == productToRemove.getId()) {
            allProducts.remove(productToRemove);
        }
            allProducts.add(newProduct);


        }
    }









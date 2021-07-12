package Model;

/** This class creates the methods need to instantiate an outsourced part. Extends the Parts class. */
public class OutsourcedPart extends Part {

    private String company;

    /** This method creates the default constructor for the outsourced part.
     The default constructor takes in the variables needed to instantiate an outsourced part object
     @param id Outsourced part id
     @param name Outsourced part name
     @param price Outsourced part price
     @param stock Outsourced part inventory level
     @param min Minimum inventory level
     @param max Maximum inventory level
     @param companyName The name of the company
     */
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);

        company = companyName;
    }

    /**Setter method for company name.
     Takes in a variable and sets that variable as the company name.
     @param companyName The company name */
    public void setCompanyName(String companyName) {
        company = companyName;
    }

    /**Getter method for company name.
     Returns the company name of an Outsourced part when called.
     @return Returns company name*/
    public String getCompanyName() {
        return company;
    }

}

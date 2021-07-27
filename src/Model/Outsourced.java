package Model;


/**
 * Extends Part class, creates a Part that was made out of our workshop.
 * @author Joseph Lawter
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * constructor that creates a part that was produced outside our company
     * @param id id of the part
     * @param name name of the part
     * @param price price of the part
     * @param stock how many of this part we have
     * @param min min number of parts we can have at any one time
     * @param max max number of parts we can have at any one time
     * @param companyName name of the company that produced this part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     *
     * @param companyName The name of the company who produced the part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     *
     * @return returns the name of the company who produced the part
     */
    public String getCompanyName() {
        return this.companyName;
    }
}

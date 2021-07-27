package Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class which creates the Product object.
 * @author Joseph Lawter
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * constructor that produces a product object
     * @param id id of the product
     * @param name name of the product
     * @param price price of the product
     * @param stock how many of the product we have on hand
     * @param min min number of product we can have on hand
     * @param max max number of product we can have on hand
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     *
     * @param id new id of the product
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     *
     * @param name new name of the product
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     *
     * @param price new price of the product
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     *
     * @param stock new stock of the item
     */
    public void setStock(int stock){
        this.stock = stock;
    }

    /**
     *
     * @param min new min of the item
     */
    public void setMin(int min){
        this.min = min;
    }

    /**
     *
     * @param max new max of the item
     */
    public void setMax(int max){
        this.max = max;
    }

    /**
     *
     * @return Id of the item
     */
    public int getId(){
        return this.id;
    }

    /**
     *
     * @return name of the item
     */
    public String getName(){
        return this.name;
    }

    /**
     *
     * @return the price of the item
     */
    public double getPrice(){
        return this.price;
    }

    /**
     *
     * @return the stock of the item
     */
    public int getStock(){
        return this.stock;
    }

    /**
     *
     * @return the min of the item
     */
    public int getMin(){
        return this.min;
    }

    /**
     *
     * @return the max of the item
     */
    public int getMax() {
        return this.max;
    }

    /**
     *
     * @param part adds a Part object to the list of associated parts
     * RUNTIME ERROR I used the incorrect set method (set(Object, index, element), which
     * ended up giving some errors which were difficult to track down.
     * Switching to the list.set() method fixed this issue.
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     *
     * @param selectedAssociatedPart removes the selected associatd part
     * @return true if the item is deleted, false if not
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
        if (!associatedParts.contains(selectedAssociatedPart)){
            return true;
        }
        return false;
    }

    /**
     *
     * @return the associatedParts observableList object
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }


}

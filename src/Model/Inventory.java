package Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *  This class creates an object which tracks all inventory.
 * @author joseph Lawter
 */
public class Inventory {
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** @param part the part to be added to allParts list*/
    public void addPart(Part part) {
        this.allParts.add(part);
    }

    /** @param product product to be added to the allProducts list */
    public void addProduct(Product product) {
        this.allProducts.add(product);
    }

    /**
     * @param partId specifies the id of the part you wish to find
     * @return returns part based on the part id
     */
    public Part lookupPart(int partId) {
        for (int i = 0; i < allParts.size(); i++){
            if (allParts.get(i).getId() == partId)
            return allParts.get(i);
        }
        //ADD EXCEPTION!
        return allParts.get(0);
    }

    /**
     *
     * @param productId specifies the id of the product you wish to find
     * @return returns product based on the product id
     */
    public Product lookupProduct(int productId) {
        for (int i = 0; i < allProducts.size(); i++){
            if (allProducts.get(i).getId() == productId)
                return allProducts.get(i);
        }
        //ADD EXCEPTION!
        return allProducts.get(0);
    }

    /**
     *
     * @param partName name of the part you wish to find
     * @return returns part based on the part name
     */
    public Part lookupPart(String partName){
        for (int i = 0; i < allParts.size(); i++){
            if (allParts.get(i).getName().equals(partName))
                return allParts.get(i);
        }
        //ADD EXCEPTION!
        return allParts.get(0);
    }

    /**
     *
     * @param productName name of the product you wish to find
     * @return returns product based on the part name
     */
    public Product lookupProduct(String productName){
        for (Product allProduct : allProducts) {
            if (allProduct.getName().equals(productName)) {
                return allProduct;
            }
        }
        //add exceptions!
        return allProducts.get(0);
    }

    /**
     *
     * @param index element index you wish to update
     * @param selectedPart part that  you will replace the existing part with
     * RUNTIME ERROR The original version of this method just added the part on. The problem was
     * that I was finding the Part Id rather than the part's index.
     */
    public void updatePart(int index, Part selectedPart){
        allParts.set(index,selectedPart);
    }

    /**
     *
     * @param index element index you wish to update
     * @param newProduct
     */
    public void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    public boolean deletePart(Part selectedPart){
        allParts.remove(selectedPart);
        //add an exception
        return true;
    }

    /**
     *
     * @param selectedProduct Product object. Deletes the product that was passed into the method
     * @return returns a True or False based on whether the deletion was successful
     */
    public boolean deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
        // add an exception
        return true;
    }

    /**
     *
     * @return returns the observableList object allParts
     */
    public ObservableList<Part> getAllParts(){
        return this.allParts;
    }

    /**
     *
     * @return returns the observableList object allProducts
     */
    public ObservableList<Product> getAllProducts() {
        return this.allProducts;
    }

}

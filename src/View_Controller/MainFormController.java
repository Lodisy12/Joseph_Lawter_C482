package View_Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class that controls the main form, handling the button presses and inputs etc.
 * @author Joseph Lawter
 */
public class MainFormController implements Initializable {
    private static Inventory allInventory = new Inventory();
    private ObservableList<Part> partList = FXCollections.observableArrayList();
    private ObservableList<Product> productList = FXCollections.observableArrayList();

    public TextField mainPartSearch;
    public TableView mainPartTable;
    public TableView mainProductTable;
    public TextField mainProductSearch;
    public TableColumn mainPartIdCol;
    public TableColumn mainPartNameCol;
    public TableColumn mainPartInvLevCol;
    public TableColumn mainPartPriceCol;
    public TableColumn mainProdIdCol;
    public TableColumn mainProdNameCol;
    public TableColumn mainProdInvLevCol;
    public TableColumn mainProdPriceCol;
    public static Inventory passData(){
        return allInventory;
    }
    public Product modProduct(){
        return (Product)mainProductTable.getSelectionModel().getSelectedItem();
    }

/*    private static boolean firstTime = true;
    private void addTestData(){
        if(!firstTime){
            return;
        }
        firstTime = false;
        allInventory.addPart(new Outsourced(0,"basket",12.99,20,5, 30,"Wheel's Inc"));
        allInventory.addPart(new Outsourced(1,"seat",11.50,25,5,30,"albatross inc"));
        allInventory.addPart(new InHouse(2,"Wheel", 9.50,11, 5,30,113));
        allInventory.addProduct(new Product(1,"Rocket Bicycle", 125, 11, 2, 12));

    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

 /*         addTestData();*/
        mainPartTable.setItems(allInventory.getAllParts());
        mainProductTable.setItems(allInventory.getAllProducts());

        mainPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainPartInvLevCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainProdIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        mainProdInvLevCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainProdNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    public void MainPartAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().
                getResource("/View_Controller/AddPart.fxml"));
        Stage stage = (Stage)((Button)
                actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 800);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();

    }

    public void MainPartModify(ActionEvent actionEvent) throws IOException {
        try {
            ModifyPartController.passModPart((Part)mainPartTable.getSelectionModel().getSelectedItem());
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyPart.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 800);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
        catch(LoadException e){
            System.out.println(e);
        }
    }

    public void MainPartDelete(ActionEvent actionEvent) {
        Alert reallyDelete = new Alert(Alert.AlertType.CONFIRMATION,
                "This Product will be deleted. Are you sure?");
        Optional<ButtonType> result = reallyDelete.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part partToRemove = (Part)mainPartTable.getSelectionModel().getSelectedItem();
            allInventory.deletePart(partToRemove);
        }
    }

    public void MainProductAdd(ActionEvent actionEvent) throws IOException {
        System.out.println("add button clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/AddProduct.fxml"));
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,850,600);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    public void MainProductModify(ActionEvent actionEvent) throws IOException {
      try {
          ModifyProductController.passModProduct((Product) mainProductTable.getSelectionModel().getSelectedItem());
          Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyProduct.fxml"));
          Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
          Scene scene = new Scene(root, 850, 600);
          stage.setTitle("Modify Product");
          stage.setScene(scene);
          stage.show();
      }
      catch (LoadException e){
          System.out.println(e);
          System.out.println("No selection");
      }
    }

    public void MainProductDelete(ActionEvent actionEvent) {
        Product productToDelete = (Product) mainProductTable.getSelectionModel().getSelectedItem();
        if (productToDelete.getAllAssociatedParts().size() > 0) {
            Alert partAlert = new Alert(Alert.AlertType.ERROR);
            partAlert.setTitle("Cannot delete");
            partAlert.setContentText("Cannot delete a product that has parts associated with it.");
            partAlert.showAndWait();
            return;
        }
        Alert reallyDelete = new Alert(Alert.AlertType.CONFIRMATION,
                "This Product will be deleted. Are you sure?");
        Optional<ButtonType> result = reallyDelete.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            allInventory.deleteProduct(
                    (Product) mainProductTable.getSelectionModel().getSelectedItem());
        }
    }

    public void MainExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    private ObservableList<Part> searchByPartName(String partialPart){
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = allInventory.getAllParts();
        for(Part part : allParts){
            if (part.getName().contains(partialPart)){
                searchedParts.add(part);
            }
        }
        return searchedParts;
    }

    private ObservableList<Product> searchByProductName(String partialProd){
        ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = allInventory.getAllProducts();
        for(Product product : allProducts){
            if (product.getName().contains(partialProd)){
                searchedProducts.add(product);
            }
        }
        return searchedProducts;
    }

    private Product searchProductByID(int ID){
        ObservableList<Product> allProducts = allInventory.getAllProducts();
        for (Product product : allProducts){
            if (product.getId() == ID)
                return product;
        }
        return null;
    }
    private Part searchPartByID(int ID){
        ObservableList<Part> allParts = allInventory.getAllParts();
        for (Part part : allParts){
            if (part.getId() == ID)
                return part;
        }
        return null;
    }

    public void productSearchOnAction(ActionEvent actionEvent) {
        String q = mainProductSearch.getText();
        ObservableList<Product> products = searchByProductName(q);
        try {
            if (products.size() == 0) {
                int ID = Integer.parseInt(q);
                Product product = searchProductByID(ID);
                if (product != null)
                    products.add(product);
            }
        }
        catch (NumberFormatException e){
            // ignore
        }
        mainProductTable.setItems(products);
    }

    public void partSearchOnAction(ActionEvent actionEvent) {
        String q = mainPartSearch.getText();
        ObservableList<Part> parts = searchByPartName(q);
        try {
            if (parts.size() == 0) {
                int ID = Integer.parseInt(q);
                Part part = searchPartByID(ID);
                if (part != null)
                    parts.add(part);
            }
        }
        catch (NumberFormatException e){
                    //ignore
        }
        mainPartTable.setItems(parts);
    }


        /*for(int i = 0; i < allInventory.getAllParts().size();i++){
            Part part = allInventory.getAllParts().get(i);*/




}

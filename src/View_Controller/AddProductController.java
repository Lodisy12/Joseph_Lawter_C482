package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {
    public Label NameErrorLabel;
    public Label InvErrorLabel;
    public Label PriceErrorLabel;
    public Label MaxErrorLabel;
    public Label MinErrorLabel;
    private Inventory allInventory;
    private Inventory someInventory;
    public TextField AddProdIdText;
    public TextField AddProdNameText;
    public TextField AddProdInvText;
    public TextField AddProdPriceText;
    public TextField AddProdMaxText;
    public TextField AddProdMinText;
    public TableView AddProdAddTable;
    public TableColumn AddProdAddIdCol;
    public TableColumn AddProdAddNameCol;
    public TableColumn AddProdAddInvCol;
    public TableColumn AddProdAddPriceCol;
    public TextField AddPartSearchText;
    public TableView AddProdRemTable;
    public TableColumn AddProdRemIdCol;
    public TableColumn AddProdRemNameCol;
    public TableColumn AddProdRemInvCol;
    public TableColumn AddProdRemPriceCol;
    public Button AddProdAddPartButton;
    public Button AddProdRemovePartButton;
    public Button AddProdSaveButton;
    public Button AddProdCancelButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Inventory someInventory = new Inventory();
        someInventory = new Inventory();
        allInventory = MainFormController.passData();
        AddProdAddTable.setItems(allInventory.getAllParts());
        AddProdAddIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProdAddNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProdAddInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProdAddPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        AddProdRemTable.setItems(someInventory.getAllParts());
        AddProdRemIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProdRemNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProdRemInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProdRemPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private int UniqueID(ObservableList<Product> products) {
        boolean taken = true;
        if (products.size()==0)
            return 0;
        for (int i = 0; ;i++){
            for (Product product:products) {
                if (product.getId() == i) {
                    taken = true;
                    break;
                }
                else taken = false;
            }
            if (taken)
                continue;
            return i;
        }
    }

    public void AddButtonAction(ActionEvent actionEvent) {
        if (!someInventory.getAllParts().contains((Part)AddProdAddTable.getSelectionModel().getSelectedItem()))
            someInventory.addPart((Part)AddProdAddTable.getSelectionModel().getSelectedItem());
        else
            return;
    }

    public void RemoveButtonAction(ActionEvent actionEvent) {
        someInventory.deletePart((Part)AddProdRemTable.getSelectionModel().getSelectedItem());
    }

    public void SaveButtonAction(ActionEvent actionEvent) throws IOException {
        NameErrorLabel.setText("");
        InvErrorLabel.setText("");
        PriceErrorLabel.setText("");
        MaxErrorLabel.setText("");
        MinErrorLabel.setText("");
        boolean errors = false;

        if (AddProdNameText.getText().equals("")) {
            NameErrorLabel.setText("Exception: No data in name field");
            errors = true;
        }
        int inv2 = 0;
        try{
            String inv = AddProdInvText.getText();
            inv2 = Integer.parseInt(inv);
        }
        catch (Exception e){
            System.out.println(e);
            InvErrorLabel.setText("Inventory is not an integer");
            errors = true;
        }
        try{
            String price = AddProdPriceText.getText();
            Double.parseDouble(price);
        }
        catch (NumberFormatException e){
            System.out.println(e);
            PriceErrorLabel.setText("Price is not a double");
            errors = true;
        }
        int max2 = 0;
        try{
            String max = AddProdMaxText.getText();
            max2 = Integer.parseInt(max);
        }
        catch (NumberFormatException e){
            System.out.println(e);
            MaxErrorLabel.setText("Max is not an Integer");
            errors = true;
        }
        int min2 = 0;
        try{
            String min = AddProdMinText.getText();
            min2 = Integer.parseInt(min);
        }
        catch (NumberFormatException e){
            System.out.println(e);
            MinErrorLabel.setText("Min is not an Integer");
            errors = true;
        }
        if (inv2 > max2 || inv2 <min2){
            PriceErrorLabel.setText("Exception: Inv must be between Min and Max");
            errors = true;
        }
        if (min2 > max2){
            PriceErrorLabel.setText("Exception: min must be less than Max");
            errors = true;
        }
        if (errors)
            return;

        int uniqueId = UniqueID(allInventory.getAllProducts());

        Product addedProduct = new Product(
        uniqueId,
        AddProdNameText.getText(),
        Double.parseDouble(AddProdPriceText.getText()),
        Integer.parseInt(AddProdInvText.getText()),
        Integer.parseInt(AddProdMinText.getText()),
        Integer.parseInt(AddProdMaxText.getText()));
        for (int i = 0; i < someInventory.getAllParts().size(); i++) {
            addedProduct.addAssociatedPart((Part) someInventory.getAllParts().get(i));
        }
        allInventory.addProduct(addedProduct);

        Parent root = FXMLLoader.load(getClass().
                getResource("/View_Controller/MainForm.fxml"));
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1175, 450);
        stage.setTitle("Inventory management");
        stage.setScene(scene);
        stage.show();

    }

    public void CancelButtonAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().
                getResource("/View_Controller/MainForm.fxml"));
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1175, 450);
        stage.setTitle("Inventory management");
        stage.setScene(scene);
        stage.show();

    }

}
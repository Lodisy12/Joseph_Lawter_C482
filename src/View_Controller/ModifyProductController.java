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

public class ModifyProductController implements Initializable {
    private Inventory allInventory;
    private ObservableList<Part> someInventory;
    private static Product modProduct = null;

    public Label NameErrorLabel;
    public Label InvErrorLabel;
    public Label PriceErrorLabel;
    public Label MaxErrorLabel;
    public Label MinErrorLabel;
    public static void passModProduct(Product pass){ modProduct = pass; }
    public TextField ModProdIdText;
    public TextField ModProdNameText;
    public TextField ModProdInvText;
    public TextField ModProdPriceText;
    public TextField ModProdMaxText;
    public TextField ModProdMinText;
    public TableView ModProdAddTable;
    public TableColumn ModProdAddIdCol;
    public TableColumn ModProdAddNameCol;
    public TableColumn ModProdAddInvCol;
    public TableColumn ModProdAddPriceCol;
    public TextField AddPartSearchText;
    public TableView ModProdRemTable;
    public TableColumn ModProdRemIdCol;
    public TableColumn ModProdRemNameCol;
    public TableColumn ModProdRemInvCol;
    public TableColumn ModProdRemPriceCol;
    public Button ModProdAddPartButton;
    public Button ModProdRemovePartButton;
    public Button ModProdSaveButton;
    public Button ModProdCancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        someInventory = modProduct.getAllAssociatedParts();
        allInventory = MainFormController.passData();
        ModProdIdText.setText(String.valueOf(modProduct.getId()));
        ModProdNameText.setText(modProduct.getName());
        ModProdInvText.setText(String.valueOf(modProduct.getStock()));
        ModProdPriceText.setText(String.valueOf(modProduct.getPrice()));
        ModProdMaxText.setText(String.valueOf(modProduct.getMax()));
        ModProdMinText.setText(String.valueOf(modProduct.getMin()));

        ModProdAddTable.setItems(allInventory.getAllParts());
        ModProdAddIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModProdAddNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModProdAddInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProdAddPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        ModProdRemTable.setItems(someInventory);
        ModProdRemIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModProdRemNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModProdRemInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProdRemPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void AddButtonAction(ActionEvent actionEvent) {
        if (!someInventory.contains((Part)ModProdAddTable.getSelectionModel().getSelectedItem()))
            someInventory.add((Part)ModProdAddTable.getSelectionModel().getSelectedItem());
        else
            return;
    }

    public void RemoveButtonAction(ActionEvent actionEvent) {
        someInventory.remove((Part)ModProdRemTable.getSelectionModel().getSelectedItem());

    }

    public void SaveButtonAction(ActionEvent actionEvent) throws IOException {
        NameErrorLabel.setText("");
        InvErrorLabel.setText("");
        PriceErrorLabel.setText("");
        MaxErrorLabel.setText("");
        MinErrorLabel.setText("");
        boolean errors = false;
        if (ModProdNameText.getText().equals("")) {
            NameErrorLabel.setText("Exception: No data in name field");
            errors = true;
        }
        int inv2 = 0;
        try{
            String inv = ModProdInvText.getText();
            inv2 = Integer.parseInt(inv);
        }
        catch (Exception e){
            System.out.println(e);
            InvErrorLabel.setText("Inventory is not an integer");
            errors = true;
        }
        try{
            String price = ModProdPriceText.getText();
            Double.parseDouble(price);
        }
        catch (NumberFormatException e){
            System.out.println(e);
            PriceErrorLabel.setText("Price is not a double");
            errors = true;
        }
        int max2 = 0;
        try{
            String max = ModProdMaxText.getText();
            max2 = Integer.parseInt(max);
        }
        catch (NumberFormatException e){
            System.out.println(e);
            MaxErrorLabel.setText("Max is not an Integer");
            errors = true;
        }
        int min2 = 0;
        try{
            String min = ModProdMinText.getText();
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

        int index = allInventory.getAllProducts().indexOf(allInventory.
        lookupProduct(Integer.parseInt(ModProdIdText.getText())));
        Product modifiedProduct = new Product(
        Integer.parseInt(ModProdIdText.getText()),
        ModProdNameText.getText(),
        Double.parseDouble(ModProdPriceText.getText()),
        Integer.parseInt(ModProdInvText.getText()),
        Integer.parseInt(ModProdMinText.getText()),
        Integer.parseInt(ModProdMaxText.getText()));
        int size = modifiedProduct.getAllAssociatedParts().size();
        for (int i = 0;i<size;i++){
            Part part = modifiedProduct.getAllAssociatedParts().get(0);
            modifiedProduct.deleteAssociatedPart(part);
        }
        for (int i = 0; i < someInventory.size(); i++) {
            modifiedProduct.addAssociatedPart((Part)someInventory.get(i));
        }
        allInventory.updateProduct(index,modifiedProduct);

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
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }
}

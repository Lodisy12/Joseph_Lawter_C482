package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    public Label NameErrorLabel;
    public Label InvErrorLabel;
    public Label PriceErrorLabel;
    public Label MaxErrorLabel;
    public Label MinErrorLabel;
    //void initialize() {}
    private Inventory allInventory;
    //public static Inventory initData(Inventory allInventory) { return allInventory; }
    public Label AddPartChangeLabel;
    public TextField AddPartIdText;
    public TextField AddPartNameText;
    public TextField AddPartInvText;
    public TextField AddPartPriceText;
    public TextField AddPartMaxText;
    public TextField AddPartChangeText;
    public TextField AddPartMinText;
    public Button AddPartSaveBtn;
    public Button AddPartCancelBtn;
    public RadioButton AddPartOutsourcedRadio;
    public ToggleGroup AddPartToggleGroup;
    public RadioButton AddPartInHouseRadio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        allInventory = MainFormController.passData();
        //AddPartIdText.setText(UniqueID(allInventory.getAllParts()));

    }

    private int UniqueID(ObservableList<Part> parts) {
        boolean taken = true;
        if (parts.size()==0)
            return 0;
        for (int i = 0; ;i++){
            for (Part part:parts) {
                if (part.getId() == i) {
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


    public void AddPartSaveOnAction(ActionEvent actionEvent) throws IOException {
        NameErrorLabel.setText("");
        InvErrorLabel.setText("");
        PriceErrorLabel.setText("");
        MaxErrorLabel.setText("");
        MinErrorLabel.setText("");
        boolean errors = false;

        if (AddPartNameText.getText().equals("")) {
            NameErrorLabel.setText("Exception: No data in name field");
            errors = true;
        }
        int inv2 = 0;
        try{
            String inv = AddPartInvText.getText();
            inv2 = Integer.parseInt(inv);
        }
        catch (Exception e){
            System.out.println(e);
            InvErrorLabel.setText("Inventory is not an integer");
            errors = true;
        }
        try{
            String price = AddPartPriceText.getText();
            Double.parseDouble(price);
        }
        catch (NumberFormatException e){
            System.out.println(e);
            PriceErrorLabel.setText("Price is not a double");
            errors = true;
        }
        int max2 = 0;
        try{
            String max = AddPartMaxText.getText();
            max2 = Integer.parseInt(max);
        }
        catch (NumberFormatException e){
            System.out.println(e);
            MaxErrorLabel.setText("Max is not an Integer");
            errors = true;
        }
        int min2 = 0;
        try{
            String min = AddPartMinText.getText();
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

        int uniqueId = UniqueID(allInventory.getAllParts());
        if (AddPartChangeLabel.getText().equals("Machine ID")) {
            allInventory.addPart(new InHouse(uniqueId,
                    AddPartNameText.getText(),
                    Double.parseDouble(AddPartPriceText.getText()),
                    Integer.parseInt(AddPartInvText.getText()),
                    Integer.parseInt(AddPartMinText.getText()),
                    Integer.parseInt(AddPartMaxText.getText()),
                    Integer.parseInt(AddPartChangeText.getText())));
        }
        else{
            allInventory.addPart(new Outsourced(uniqueId,
                    AddPartNameText.getText(),
                    Double.parseDouble(AddPartPriceText.getText()),
                    Integer.parseInt(AddPartInvText.getText()),
                    Integer.parseInt(AddPartMinText.getText()),
                    Integer.parseInt(AddPartMaxText.getText()),
                    AddPartChangeText.getText()));
        }
        Parent root = FXMLLoader.load(getClass().getResource(
                "/View_Controller/MainForm.fxml"));
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1175, 450);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    public void AddPartCancelOnAction(ActionEvent actionEvent) throws IOException {
        //System.out.println("add button clicked");
        Parent root = FXMLLoader.load(getClass().
                getResource("/View_Controller/MainForm.fxml"));
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1175, 450);
        stage.setTitle("Inventory management");
        stage.setScene(scene);
        stage.show();
    }

    public void AddPartInHouseRadioOnAction(ActionEvent actionEvent) {
        AddPartChangeLabel.setText("MachineId");
    }

    public void AddPartOutsourcedRadioOnAction(ActionEvent actionEvent) {
        AddPartChangeLabel.setText("Company Name");
    }
}

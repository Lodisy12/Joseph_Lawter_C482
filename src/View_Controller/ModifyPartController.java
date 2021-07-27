package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
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

public class ModifyPartController implements Initializable {
    private Inventory allInventory;
    private static Part modPart = null;
    public static void passModPart(Part pass){ modPart = pass; }
    public Label ModifyPartChangeLabel;
    public TextField ModifyPartIdText;
    public TextField ModifyPartNameText;
    public TextField ModifyPartInvText;
    public TextField ModifyPartPriceText;
    public TextField ModifyPartMaxText;
    public TextField ModifyPartChangeText;
    public TextField ModifyPartMinText;
    public Button ModifyPartSaveBtn;
    public Button ModifyPartCancelBtn;
    public RadioButton ModifyPartOutsourcedRadio;
    public ToggleGroup ModifyPartToggleGroup;
    public RadioButton ModifyPartInHouseRadio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        allInventory = MainFormController.passData();
        ModifyPartIdText.setText(String.valueOf(modPart.getId()));
        ModifyPartNameText.setText(modPart.getName());
        ModifyPartInvText.setText(String.valueOf(modPart.getStock()));
        ModifyPartPriceText.setText(String.valueOf(modPart.getPrice()));
        ModifyPartMaxText.setText(String.valueOf(modPart.getMax()));
        ModifyPartMinText.setText(String.valueOf(modPart.getMin()));
        if (modPart instanceof InHouse) {
            ModifyPartChangeText.setText(String.valueOf(((InHouse) modPart).getMachineId()));
        }
        else{
            ModifyPartChangeLabel.setText("Company Name");
            ModifyPartChangeText.setText(((Outsourced) modPart).getCompanyName());
            ModifyPartOutsourcedRadio.setSelected(true);
        }
    }

    public void ModifyPartSaveOnAction(ActionEvent actionEvent) throws IOException {
        if (ModifyPartChangeLabel.getText().equals("Machine ID")) {
            int i = allInventory.getAllParts().indexOf(allInventory.
                    lookupPart((Integer.parseInt(ModifyPartIdText.getText()))));
            allInventory.updatePart(
                    i,
                    new InHouse(Integer.parseInt(ModifyPartIdText.getText()),
                            ModifyPartNameText.getText(),
                            Double.parseDouble(ModifyPartPriceText.getText()),
                            Integer.parseInt(ModifyPartInvText.getText()),
                            Integer.parseInt(ModifyPartMinText.getText()),
                            Integer.parseInt(ModifyPartMaxText.getText()),
                            Integer.parseInt(ModifyPartChangeText.getText())));
        } else {
            int i = allInventory.getAllParts().indexOf(allInventory.
                    lookupPart(Integer.parseInt(ModifyPartIdText.getText())));
            allInventory.updatePart(
                    i,
                    new Outsourced(Integer.parseInt(ModifyPartIdText.getText()),
                            ModifyPartNameText.getText(),
                            Double.parseDouble(ModifyPartPriceText.getText()),
                            Integer.parseInt(ModifyPartInvText.getText()),
                            Integer.parseInt(ModifyPartMinText.getText()),
                            Integer.parseInt(ModifyPartMaxText.getText()),
                            ModifyPartChangeText.getText()));
        }
        Parent root = FXMLLoader.load(getClass().getResource(
                "/View_Controller/MainForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1175, 450);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    public void ModifyPartCancelOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().
                getResource("/View_Controller/MainForm.fxml"));
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1175, 450);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    public void ModifyPartInHouseRadioOnAction(ActionEvent actionEvent) {
        ModifyPartChangeLabel.setText("MachineId");
    }

    public void ModifyPartOutsourcedRadioOnAction(ActionEvent actionEvent) {
        ModifyPartChangeLabel.setText("Company Name");
    }
}
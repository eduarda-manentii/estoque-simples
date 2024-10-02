package br.com.eduarda.simplestock.controlls;


import br.com.eduarda.simplestock.Measurements;
import br.com.eduarda.simplestock.Product;
import br.com.eduarda.simplestock.models.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ProductFormController implements Initializable {

    @FXML
    private TextField amountTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private ComboBox<Measurements> measureCbb;

    @FXML
    private Button cancelButton;

    private Product selectedProduct;

    @FXML
    void onButtonSaveClicked() {
        try {
            validate();
            Stock instance = Stock.getInstance();
            if (selectedProduct != null) {
                instance.remove(selectedProduct);
                selectedProduct.setDescription(descriptionTextField.getText());
                selectedProduct.setAmount(Double.parseDouble(amountTextField.getText()));
                selectedProduct.setMeasurements(measureCbb.getValue());
                instance.add(selectedProduct);
                selectedProduct = null;
            } else {
                Product product = new Product(Double.parseDouble(amountTextField.getText()), measureCbb.getValue(), descriptionTextField.getText());
                instance.add(product);
            }
            onButtonCancelClicked();
        } catch (Exception e) {
            showMessage(e.getMessage());
        }
    }

    @FXML
    void onButtonCancelClicked() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Measurements> list = Arrays.asList(Measurements.values());
        ObservableList<Measurements> obList = FXCollections.observableList(list);
        measureCbb.setItems(obList);
    }

    private void showMessage(String message) {
        ButtonType loginButtonType = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Aviso!");
        dialog.setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(loginButtonType);
        boolean disabled = false;
        dialog.getDialogPane().lookupButton(loginButtonType).setDisable(disabled);
        dialog.showAndWait();
    }

    private void validate() {
        if (descriptionTextField.getText().isEmpty()) {
            throw new NullPointerException("A descrição é obrigatória");
        }
        if (measureCbb.getSelectionModel().isEmpty()) {
            throw new NullPointerException("A unidade é obrigatória");
        }
        try {
            Double.parseDouble(amountTextField.getText());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Apenas números são aceitos na quantidade!");
        }
    }

    public void setAttributes(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        descriptionTextField.setText(selectedProduct.getDescription());
        measureCbb.setValue(selectedProduct.getMeasurements());
        amountTextField.setText(String.valueOf(selectedProduct.getAmount()));
    }

}

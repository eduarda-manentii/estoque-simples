package br.com.eduarda.simplestock.controlls;

import br.com.eduarda.simplestock.Measurements;
import br.com.eduarda.simplestock.Product;
import br.com.eduarda.simplestock.models.Stock;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;

public class MainWindowController implements Initializable {

    @FXML
    private TextField filterNameTextField;

    @FXML
    private ComboBox<Measurements> measureCbb;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, String> columnId;

    @FXML
    private TableColumn<Product, String> columnDesc;

    @FXML
    private TableColumn<Product, String> columnMeasur;

    @FXML
    private TableColumn<Product, String> columnAmount;

    @FXML
    void onButtonInsertClicked() throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/br/com/eduarda/simplestock/product-form.fxml")));
        Stage popup = new Stage();
        popup.setTitle("");
        Scene scene = new Scene(parent);
        popup.setScene(scene);
        popup.showAndWait();
        productsTable.setItems(initialData());
    }

    @FXML
    void onEditButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/eduarda/simplestock/product-form.fxml"));
        Parent root = loader.load();
        ProductFormController productController = loader.getController();
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            warningMessage();
        } else {
            productController.setAttributes(selectedProduct);
            Scene scene = new Scene(root);
            Stage popup = new Stage();
            popup.setTitle("");
            popup.setScene(scene);
            popup.showAndWait();
            productsTable.setItems(initialData());
            productsTable.refresh();
        }
    }

    @FXML
    void onRemoveButtonClicked() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            warningMessage();
        } else {
            Stock stock = Stock.getInstance();
            confirmationMessage(() -> {
                stock.remove(selectedProduct);
                productsTable.setItems(initialData());
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDropDown();
        initializeTableColumns();
    }

    private void initializeDropDown() {
        List<Measurements> list = Arrays.asList(Measurements.values());
        ObservableList<Measurements> obList = FXCollections.observableArrayList(list);
        obList.add(0, null);
        measureCbb.setItems(obList);
    }

    private void initializeTableColumns() {
        columnId.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId().toString()));
        columnDesc.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDescription()));
        columnMeasur.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMeasurements().getDescription()));
        columnAmount.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getAmount().toString()));
        productsTable.setItems(initialData());
    }

    ObservableList<Product> initialData() {
        Stock stock = Stock.getInstance();
        return FXCollections.observableList(stock.getList());
    }

    public void filter() {
        List<Product> products = Stock.getInstance().getList();

        boolean isInformed = !filterNameTextField.getText().isBlank();
        boolean isSelected = measureCbb.getValue() != null;

        if (isInformed || isSelected) {
            if (isInformed) {
                products = products
                        .stream()
                        .filter(value -> value.getDescription().equals(filterNameTextField.getText()))
                        .toList();
            }

            if (isSelected) {
                products = products
                        .stream()
                        .filter(value -> value.getMeasurements().getDescription().equals(measureCbb.getValue().getDescription()))
                        .toList();
            }

            productsTable.setItems(FXCollections.observableList(products));
        } else {
            productsTable.setItems(initialData());
        }
    }

    private void warningMessage() {
        ButtonType loginButtonType = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Aviso!");
        dialog.setContentText("Selecione um produto.");
        dialog.getDialogPane().getButtonTypes().add(loginButtonType);
        boolean disabled = false;
        dialog.getDialogPane().lookupButton(loginButtonType).setDisable(disabled);
        dialog.showAndWait();
    }

    private void confirmationMessage(Runnable action) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnYes = new ButtonType("Sim");
        ButtonType btnNo = new ButtonType("Não");
        dialog.setContentText("Tem certeza que deseja remover?");
        dialog.getButtonTypes().setAll(btnYes, btnNo);
        dialog.showAndWait().ifPresent(b -> {
            if (b == btnYes) {
                action.run();
            }
        });
    }

}

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

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private Button editButton;

    @FXML
    private Label filterLabel;

    @FXML
    private TextField filterNameTextField;

    @FXML
    private Button insertButton;

    @FXML
    private ComboBox<Measurements> measureCbb;

    @FXML
    private Label measureLabel;

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
    private Button removeButton;

    @FXML
    private Button searchButton;

    @FXML
    private Label stockControlLabel;

    @FXML
    void cadastrar() throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/br/com/eduarda/simplestock/product-form.fxml")));
        Stage popup = new Stage();
        popup.setTitle("");
        Scene scene = new Scene(parent);
        popup.setScene(scene);
        popup.showAndWait();
        productsTable.setItems(initialData());
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
        Stock instance = Stock.getInstance();
        return FXCollections.observableList(instance.getList());
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
}

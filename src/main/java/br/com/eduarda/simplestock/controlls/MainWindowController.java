package br.com.eduarda.simplestock.controlls;

import br.com.eduarda.simplestock.Measurements;
import br.com.eduarda.simplestock.models.Product;
import br.com.eduarda.simplestock.models.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
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
    private ComboBox<?> measureCbb;

    @FXML
    private Label measureLabel;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Integer> id;

    @FXML
    private TableColumn<Product, String> description;

    @FXML
    private TableColumn<Product, Measurements> measurement;

    @FXML
    private TableColumn<Product, Double> amount;

    @FXML
    private Button removeButton;

    @FXML
    private Button searchButton;

    @FXML
    private Label stockControlLabel;

    @FXML
    void cadastrar() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/br/com/eduarda/simplestock/product-form.fxml"));
        Stage popup = new Stage();
        popup.setTitle("");
        Scene scene = new Scene(parent);
        popup.setScene(scene);
        popup.showAndWait();
        productsTable.setItems(initialData());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        measurement.setCellValueFactory(new PropertyValueFactory<>("measurement"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        productsTable.setItems(initialData());
    }

    ObservableList<Product> initialData() {
        Stock instance = Stock.getInstance();
        return FXCollections.observableList(instance.getList());
    }
}

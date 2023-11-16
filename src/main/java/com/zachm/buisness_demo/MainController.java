package com.zachm.buisness_demo;

import com.zachm.buisness_demo.util.Product;
import javafx.collections.ObservableList;
import javafx.css.converter.StringConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    TableView<Product> Monday_TableView;
    @FXML
    TableColumn<Product, String> Monday_Vendor;
    @FXML
    TableColumn<Product, String> Monday_Product;
    @FXML
    TableColumn<Product, Integer> Monday_Case;
    @FXML
    TableColumn<Product, Integer> Monday_Sales;
    @FXML
    TableColumn<Product, Integer> Monday_Backstock;
    @FXML
    TableColumn<Product, Integer> Monday_Order;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Monday_Vendor.setCellValueFactory(new PropertyValueFactory<Product,String>("vendor"));
        Monday_Product.setCellValueFactory(new PropertyValueFactory<Product,String>("product"));
        Monday_Case.setCellValueFactory(new PropertyValueFactory<Product,Integer>("quantity"));
        Monday_Sales.setCellValueFactory(new PropertyValueFactory<Product,Integer>("sales"));
        Monday_Backstock.setCellValueFactory(new PropertyValueFactory<Product,Integer>("backstock"));
        Monday_Backstock.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        Monday_Order.setCellValueFactory(new PropertyValueFactory<Product,Integer>("order"));

        Monday_TableView.setEditable(true);
        Monday_Backstock.setEditable(true);


        ObservableList<Product> list = Monday_TableView.getItems();
        list.add(new Product("Mitica", "Parmesan", 24, 77, 0, 21));
        Monday_TableView.setItems(list);
    }

    public void onCommit(TableColumn.CellEditEvent<Product, Integer> event) {
        Product original = event.getTableView().getItems().get(event.getTableView().getItems().indexOf(event.getRowValue()));
        Product product = new Product(original.getVendor(), original.getProduct(), original.getQuantity(), original.getSales(), event.getNewValue(), original.getDays());
        event.getTableView().getItems().set(event.getTableView().getItems().indexOf(event.getRowValue()),product);
    }
}
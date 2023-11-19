package com.zachm.buisness_demo;

import com.zachm.buisness_demo.util.JsonHelper;
import com.zachm.buisness_demo.util.TableHelper;
import com.zachm.buisness_demo.util.FileHelper;
import com.zachm.buisness_demo.util.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    MenuItem File_Open;

    @FXML
    TabPane Tab_Pane;
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
        TableHelper.setTable(Monday_TableView, Monday_Vendor, Monday_Product, Monday_Case, Monday_Sales, Monday_Backstock, Monday_Order);

        //ObservableList<Product> list = Monday_TableView.getItems();
        //list.add(new Product("Mitica", "Parmesan", 24, 77, 0, 21));
        //Monday_TableView.setItems(list);
    }
    public void onCommit(TableColumn.CellEditEvent<Product, Integer> event) {
        //TODO make a new TextFieldCell that only accepts Integers
        Product original = event.getTableView().getItems().get(event.getTableView().getItems().indexOf(event.getRowValue()));
        Product product = new Product(original.getVendor(), original.getProduct(), original.getQuantity(), original.getSales(), event.getNewValue(), original.getDays());
        event.getTableView().getItems().set(event.getTableView().getItems().indexOf(event.getRowValue()),product);
    }

    public void onMenuAction(ActionEvent event) {
        if(event.getSource() == File_Open) {
            List<Product> list = new ArrayList<>();
            File file = FileHelper.chooseFile();
            list = JsonHelper.readOrderJson(file);
            Tab_Pane.getSelectionModel().getSelectedItem();
            ObservableList<Product> table_list = Monday_TableView.getItems();
            table_list.clear();
            table_list.addAll(list);
            Monday_TableView.setItems(table_list);

        }
    }
}
package com.zachm.buisness_demo;

import com.zachm.buisness_demo.util.JsonHelper;
import com.zachm.buisness_demo.util.TableHelper;
import com.zachm.buisness_demo.util.FileHelper;
import com.zachm.buisness_demo.util.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    MenuItem File_Open;
    @FXML
    Menu File_OpenRecent;

    @FXML
    TabPane Tab_Pane;
    @FXML
    Tab Tab_Monday;
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

    /**
     * We override the initialize function to apply our tables.
     * We then use a utility class to save space for readability.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableHelper.setTable(Monday_TableView, Monday_Vendor, Monday_Product, Monday_Case, Monday_Sales, Monday_Backstock, Monday_Order);
        File_OpenRecent.getItems().clear();
    }

    /**
     * This event gets fired when any of the backstock numbers get changed
     */
    public void onTableAction(TableColumn.CellEditEvent<Product, Integer> event) {
        Product original = event.getTableView().getItems().get(event.getTableView().getItems().indexOf(event.getRowValue()));
        if(event.getNewValue() != null) {
            Product product = new Product(original.getVendor(), original.getProduct(), original.getQuantity(), original.getSales(), event.getNewValue(), original.getDays());
            event.getTableView().getItems().set(event.getTableView().getItems().indexOf(event.getRowValue()),product);
        }
        else {
            //TODO Make a window popup explaining only numbers
            event.getTableView().getItems().set(event.getTableView().getItems().indexOf(event.getRowValue()),original);
        }
    }

    /**
     * This event gets fired whenever we use a menu item
     */
    public void onMenuAction(ActionEvent event) {
        if(event.getSource() == File_Open) {
            List<Product> list = new ArrayList<>();
            File file = FileHelper.chooseFile();
            JsonHelper.writeFilePathJson(file);
            list = JsonHelper.writeOrderJson(file);
            Tab tab = Tab_Pane.getSelectionModel().getSelectedItem();

            //TODO Switch case whenever I add other tables
            //switch (tab.getId()) {
                //case "Tab_Monday":
            //}


            ObservableList<Product> table_list = Monday_TableView.getItems();
            table_list.clear();
            table_list.addAll(list);
            Monday_TableView.setItems(table_list);
        }
    }
}
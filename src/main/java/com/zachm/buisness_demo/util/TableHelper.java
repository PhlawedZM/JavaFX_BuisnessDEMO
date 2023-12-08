package com.zachm.buisness_demo.util;

import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class TableHelper {

    /**
     * Sets the cells for our tables.
     * We can use raw forms of TableView and TableColumn here, but I don't want to throw exceptions if I can avoid it
     */
    public static void setTable(TableView<Product> tableView, TableColumn<Product, String> vendor, TableColumn<Product, String> product, TableColumn<Product, Integer> quantity, TableColumn<Product, Integer> sales, TableColumn<Product, Integer> backstock, TableColumn<Product, Integer> order) {
        vendor.setCellValueFactory(new PropertyValueFactory<Product,String>("vendor"));
        product.setCellValueFactory(new PropertyValueFactory<Product,String>("product"));
        quantity.setCellValueFactory(new PropertyValueFactory<Product,Integer>("quantity"));
        sales.setCellValueFactory(new PropertyValueFactory<Product,Integer>("sales"));
        backstock.setCellValueFactory(new PropertyValueFactory<Product,Integer>("backstock"));
        backstock.setCellFactory(TextFieldTableCell.forTableColumn(new BetterIntegerStringConverter()));
        order.setCellValueFactory(new PropertyValueFactory<Product,Integer>("order"));

        tableView.setEditable(true);
        backstock.setEditable(true);
    }
    public static void updateTableSize(TableView<Product> table) {
        table.getColumns().forEach(column -> {
            column.setMinWidth(table.getWidth()/table.getColumns().size());
            column.setMaxWidth(table.getWidth()/table.getColumns().size());
        });
    }
    public static void getAndSetTable(TabPane pane) {
        //TODO We will access this a lot.
        pane.getSelectionModel().getSelectedItem();
    }
}

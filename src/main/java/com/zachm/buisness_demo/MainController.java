package com.zachm.buisness_demo;

import com.zachm.buisness_demo.util.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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
        initializeTables();
        initializeMenus();
        initializeTabs();
    }

    public void initializeTables() {
        TableHelper.setTable(Monday_TableView, Monday_Vendor, Monday_Product, Monday_Case, Monday_Sales, Monday_Backstock, Monday_Order);
    }

    public void initializeTabs() {
        LocalDate date = LocalDate.now();

        LocalDate Sunday = date.minusDays(date.getDayOfWeek().getValue());

        //TODO For loop to set dates for all tabs
        Tab_Monday.setText("Monday " + "(" + Sunday.plusDays(1).getMonth().getValue() + "/" + Sunday.plusDays(1).getDayOfMonth() + ")");
    }

    /**
     * Logic for our menu
     * This is where our memory for Open Recent is utilized
     */
    public void initializeMenus() {
        File_OpenRecent.getItems().clear();

        FilePath paths = JsonHelper.readFilePathJson();
        int index = 0;

        if(paths.containsPaths()) {
            paths.getPaths().forEach(path -> {
                try {
                    File file = new File(path);
                    MenuItem menu = new MenuItem();
                    menu.setText(file.getName());
                    menu.setOnAction(this::onMenuAction);
                    File_OpenRecent.getItems().add(menu);
                    
                } catch (Exception e) {
                    //TODO Remove from list/use backup
                    System.out.println("Cant find file!");
                }
            });
        }
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

            //If canceled/closed it is null, we check if it isn't
            if(file != null) {
                JsonHelper.writeFilePathJson(file);
                list = JsonHelper.readOrderJson(file);
            }

            Tab tab = Tab_Pane.getSelectionModel().getSelectedItem();

            //TODO Switch case whenever I add other tables
            //switch (tab.getId()) {
                //case "Tab_Monday":
            //}


            //TEMP CODE
            ObservableList<Product> table_list = Monday_TableView.getItems();
            table_list.clear();
            table_list.addAll(list);
            Monday_TableView.setItems(table_list);
        }
        if(event.getSource() instanceof MenuItem) {
            Menu parent = ((MenuItem) event.getSource()).getParentMenu();
            List<Product> list = new ArrayList<>();

            if(parent == File_OpenRecent) {
                //TODO Ran out of time, but we get the file here from a list in initialize.
                //list = JsonHelper.readOrderJson(file);

                //TEMP CODE
                ObservableList<Product> table_list = Monday_TableView.getItems();
                table_list.clear();
                table_list.addAll(list);
                Monday_TableView.setItems(table_list);
            }
        }
    }
}
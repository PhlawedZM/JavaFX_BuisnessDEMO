package com.zachm.buisness_demo;

import com.zachm.buisness_demo.util.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class MainController implements Initializable {

    @FXML
    MenuItem File_Open;
    @FXML
    Menu File_OpenRecent;

    @FXML
    TabPane Tab_Pane;
    @FXML
    Tab Monday_Tab;
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


    private List<File> file_list = new ArrayList<>();
    private List<Product> list = new ArrayList<>();


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
        //TODO set table to scale based on width of app
        TableHelper.setTable(Monday_TableView, Monday_Vendor, Monday_Product, Monday_Case, Monday_Sales, Monday_Backstock, Monday_Order);
    }

    public void initializeTabs() {
        LocalDate date = LocalDate.now();

        LocalDate Sunday = date.minusDays(date.getDayOfWeek().getValue());

        //TODO For loop to set dates for all tabs
        Monday_Tab.setText("Monday " + "(" + Sunday.plusDays(1).getMonth().getValue() + "/" + Sunday.plusDays(1).getDayOfMonth() + ")");
    }

    public void initializeMenus() {
        updateOpenRecent();
    }

    /**
     * This is where our logic for Open Recent is
     */
    public void updateOpenRecent() {
        File_OpenRecent.getItems().clear();

        FilePath paths = JsonHelper.readFilePathJson();

        if(paths.containsPaths()) {
            paths.getPaths().forEach(path -> {
                try {
                    File file = new File(path);
                    MenuItem menu = new MenuItem();
                    menu.setText(file.getName());
                    menu.setOnAction(this::onMenuAction);
                    File_OpenRecent.getItems().add(menu);
                    file_list.clear();
                    file_list.add(file);

                    System.out.println(file);

                } catch (Exception e) {
                    //TODO Remove from list/use backup
                    System.out.println(e.getMessage());
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
            event.getTableView().getItems().set(event.getTableView().getItems().indexOf(event.getRowValue()),original);
        }
    }

    /**
     * This event gets fired whenever we use a menu item
     */
    public void onMenuAction(ActionEvent event) {
        if(event.getSource() == File_Open) {
            list.clear();
            File file = FileHelper.chooseFile();

            //If canceled/closed it is null, we check if it isn't
            //Adds the file to memory
            if(file != null) {
                JsonHelper.writeFilePathJson(file);
                list = JsonHelper.readOrderJson(file);
            }

            Tab tab = Tab_Pane.getSelectionModel().getSelectedItem();

            //TODO Switch case whenever I add other tables
            //switch (tab.getId()) {
                //case "Monday_Tab":
            //}


            //TEMP CODE
            ObservableList<Product> table_list = Monday_TableView.getItems();
            table_list.clear();
            table_list.addAll(list);
            Monday_TableView.setItems(table_list);

            //TODO This line crashes/low priority
            //updateOpenRecent();
        }

        if(event.getSource() instanceof MenuItem menu) {
            Menu parent = menu.getParentMenu();


            if(parent == File_OpenRecent) {
                file_list.forEach(file -> {
                    if(file.getName().equals(menu.getText())) {
                        list.clear();
                        list = JsonHelper.readOrderJson(file);

                        //TEMP CODE
                        ObservableList<Product> table_list = Monday_TableView.getItems();
                        table_list.clear();
                        table_list.addAll(list);
                        Monday_TableView.setItems(table_list);
                    }
                });
            }
        }
    }
}
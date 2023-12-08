package com.zachm.buisness_demo;

import com.zachm.buisness_demo.util.FilePath;
import com.zachm.buisness_demo.util.JsonHelper;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class MainApplication extends Application {

    public static File directory = new File("data");

    /**
     * Application class, this is our main class.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxml = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxml.load());
        scene.getStylesheets().add(MainApplication.class.getResource("main_dark.css").toExternalForm());
        stage.setTitle("Order App");
        stage.setScene(scene);
        stage.show();



        if(!directory.exists()) {
            createDirectory();
        }
        //JsonHelper.createTestOrderJson();

    }
    public void onResize(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Creates all of our directories
     * We check above to see if they exist or not
     */
    public static void createDirectory() {
        directory.mkdirs();
    }
}
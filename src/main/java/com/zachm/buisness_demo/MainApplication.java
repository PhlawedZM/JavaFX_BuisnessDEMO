package com.zachm.buisness_demo;

import com.zachm.buisness_demo.util.JsonHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Order App");
        stage.setScene(scene);
        stage.show();
        //JsonHelper.createTestOrderJson();
        //JsonHelper.readOrderJson();
    }

    public static void main(String[] args) {
        launch();
    }
}
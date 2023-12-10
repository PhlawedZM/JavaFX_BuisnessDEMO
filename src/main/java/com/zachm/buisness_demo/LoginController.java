package com.zachm.buisness_demo;

import com.zachm.buisness_demo.util.ResizeHelper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.kordamp.ikonli.javafx.Icon;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    Button Button_Login;
    @FXML
    TextField TF_Username;
    @FXML
    PasswordField TF_Password;
    @FXML
    Label Label_Forgot;
    @FXML
    Label Label_Admin;
    @FXML
    ImageView Icon_Person;
    @FXML
    ImageView Icon_Lock;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onTextAction(ActionEvent event) {

    }

    /**
     * This gets fired whenever we type in the textfield
     * This is so our Icons do not get in the way, it looks ugly if it does
     */
    public void onTextKeyAction(KeyEvent event) {
        Icon_Person.setVisible(TF_Username.getText().length() <= 20);
        Icon_Lock.setVisible(TF_Password.getText().length() <= 20);
    }

    /**
     * This gets fired whenever we press a button
     */
    public void onButtonAction(ActionEvent event) throws IOException {
        if(event.getSource() == Button_Login) {

            //Load our Ordering Software
            FXMLLoader fxml = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxml.load());
            scene.getStylesheets().add(MainApplication.class.getResource("main_dark.css").toExternalForm());
            stage.setTitle("Order App");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            ResizeHelper.addResizeListener(stage);

            //Close the login
            Stage login = (Stage) Button_Login.getScene().getWindow();
            login.close();
        }
    }
}

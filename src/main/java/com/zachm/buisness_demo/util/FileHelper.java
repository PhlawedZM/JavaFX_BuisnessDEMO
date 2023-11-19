package com.zachm.buisness_demo.util;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileHelper {

    public static File chooseFile(){
        FileChooser finder = new FileChooser();
        finder.setTitle("Open File...");
        finder.setInitialDirectory(new File("C://"));
        finder.setInitialFileName("Untitled.json");
        finder.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("JSON File", ".json"));
        return finder.showOpenDialog(new Stage());
    }
}
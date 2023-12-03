package com.zachm.buisness_demo.util;

import com.zachm.buisness_demo.MainApplication;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.List;
import java.util.Properties;

public class FileHelper{

    //TODO Open Recent Memory...
    private Properties prop;
    private List<String> path;

    public static File chooseFile(){
        FileChooser finder = new FileChooser();
        finder.setTitle("Open File...");
        finder.setInitialDirectory(new File("C://"));
        finder.setInitialFileName("Untitled.json");
        finder.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("JSON File", "*.json"));
        return finder.showOpenDialog(new Stage());
    }

    public static void savePath(File file) {
        Properties prop = new Properties();

    }

}

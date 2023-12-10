package com.zachm.buisness_demo.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachm.buisness_demo.MainApplication;

import java.io.DataInput;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class for Jackson's json
 * Should be plug and play for the most part
 * Created 12/10/23
 *
 * @author Zachary Martinson
 */

public class JsonHelper {

    private static final File path_file = new File(MainApplication.directory.getAbsolutePath() + "/files.json");

    /**
     * Reads Json Made for Order
     * Ideally this would be made automatic through a database online
     * You can do this by connecting with JDBC and then connecting with ObjectMapper.ReadTree(URL);
     */
    public static <T> List<T> readJsonList(File file, Class<T> clazz) {
        //Make Backup Files Here
        List<T> list = new ArrayList<>();
        ObjectMapper map = new ObjectMapper();

        try {
            JsonNode node = map.readTree(file);
            if(!node.isArray()) {

            }
            node.forEach(jsonNode -> {
                JsonParser parser = jsonNode.traverse();
                try {
                    list.add(map.readValue(parser, clazz));
                    parser.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    public static <T> T readJsonObject(File file, Class<T> clazz) {
        //Make Backup Files Here
        ObjectMapper map = new ObjectMapper();

        try {
            JsonNode node = map.readTree(file);
            if(node.isObject()) {
                return map.readValue(file, clazz);
            }

        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Writes a json file.
     */
    public static <T> void writeJson(File file, List<T> list) {
        ObjectMapper map = new ObjectMapper();
        try {
            map.writerWithDefaultPrettyPrinter().writeValue(file, list);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates a json file for testing
     */
    public static void writeTestOrderJson(){
        ObjectMapper map = new ObjectMapper();
        File file = new File("D://test.json");
        List<Product> list = new ArrayList<>();
        list.add(new Product("Mitica", "Parmesan", 24, 77, 0, 15));
        list.add(new Product("Valrhona", "Chocolate 15pc 65%", 35, 2, 0, 7));
        list.add(new Product("Coors", "Coors Lite 8pck", 4, 21, 0, 7));

        writeJson(file, list);
    }

    /**
     * Writes the file paths for every opened/saved file.
     * We need to keep the most recent on the top
     * I stuck with json here, but you can use xml as well
     * It's the exact same with Jackson except you use XmlMapper
     */
    public static void writeFilePathJson(File file){
        ObjectMapper map = new ObjectMapper();
        FilePath paths;

        if(!path_file.exists()) {
            createPathFile();
        }
        if(path_file.exists()){
            paths = readJsonObject(path_file, FilePath.class);

            //Check if the file has any paths/if the file is bad
            //TODO Write a way to avoid this null
            if(paths == null) {
                List<String> list = new ArrayList<>();
                list.add(file.getPath());
                FilePath new_paths = new FilePath();
                new_paths.setPaths(list);
                paths = new_paths;
            }

            //Check if file doesn't already have it.
            //Just a basic add and removal with our limit.
            if (!paths.getPaths().contains(file.getPath())) {
                paths.getPaths().add(0, file.getPath());

                //Could run a for loop here in case of overflow, shouldn't be possible tho.
                int MAX_FILE_PATHS = 10;
                if (paths.getPaths().size() > MAX_FILE_PATHS) {
                    paths.getPaths().remove(MAX_FILE_PATHS + 1);
                }
            }

            //Lastly just replaces the index.
            //Note that we don't need to use a for loop thanks to how ArrayList<> works.
            else {
                paths.getPaths().remove(file.getPath());
                paths.getPaths().add(0, file.getPath());
            }

            try {
                map.writer().withDefaultPrettyPrinter().writeValue(path_file, paths);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Creates the Path File
     */
    public static void createPathFile() {
        try {
            path_file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

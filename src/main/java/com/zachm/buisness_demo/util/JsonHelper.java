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
import java.util.List;

public class JsonHelper {

    private static File path_file = new File(MainApplication.directory.getAbsolutePath() + "/files.json");
    private static int MAX_FILE_PATHS = 10;

    /**
     * Reads Json Made for Order
     * Ideally this would be made automatic through a database online
     * You can do this by connecting with JDBC and then connecting with ObjectMapper.ReadTree(URL);
     */
    public static List<Product> writeOrderJson(File file){
        //TODO Make backup files incase they click wrong file.
        List<Product> list = new ArrayList<>();
        ObjectMapper map = new ObjectMapper();

        try {
            JsonNode node = map.readTree(file);
            node.forEach(jsonNode -> {
                JsonParser parser = jsonNode.traverse();
                try {
                    list.add(map.readValue(parser, Product.class));
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

        try{
            map.writer().withDefaultPrettyPrinter().writeValue(file,list);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Writes the file paths for every opened/saved file.
     * We need to keep the most recent on the top
     * I stuck with json here, but you can use xml as well
     * It's the exact same with Jackson except you use XmlMapper
     */
    public static void writeFilePathJson(File file){
        ObjectMapper map = new ObjectMapper();
        FilePath paths = readFilePathJson();

        if(path_file.exists()){

            //Check if file isn't empty.
            if (!paths.containsPaths()) {
                List<String> list = new ArrayList<>();
                paths.setPaths(list);
                paths.getPaths().add(file.getPath());
            }

            //Check if file doesn't already have it.
            //Just a basic add and removal with our limit.
            if (!paths.getPaths().contains(file.getPath())) {
                paths.getPaths().add(file.getPath());

                //Could run a for loop here in case of overflow, shouldn't be possible tho.
                //Might implement it later.
                if (paths.getPaths().size() > MAX_FILE_PATHS) {
                    paths.getPaths().remove(MAX_FILE_PATHS + 1);
                }
            }

            //Lastly just replaces the index.
            //Note that we don't need to use a for loop thinks to how ArrayList<> works.
            else {
                paths.getPaths().remove(file.getPath());
                paths.getPaths().add(file.getPath());
            }

            try {
                System.out.println(map.writer().withDefaultPrettyPrinter().writeValueAsString(paths));
                System.out.println(path_file.getAbsolutePath());
                map.writer().withDefaultPrettyPrinter().writeValue(path_file, paths);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Reads the FilePath Json File
     * We access this a lot for memory of Open Recent...
     */
    public static FilePath readFilePathJson(){
        ObjectMapper map = new ObjectMapper();
        FilePath paths = new FilePath();
        List<String> list = new ArrayList<>();

        if(!path_file.exists()) {
            createPathFile();
        }
        if(path_file.length() < 1) {
            return paths;
        }
        else
        {
            try{
                paths = map.readValue(path_file, FilePath.class);
            }
            catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }

        return paths;
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

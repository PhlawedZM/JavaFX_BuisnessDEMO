package com.zachm.buisness_demo.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {
    /**
     * Reads Json Made for Order
     * Ideally this would be made automatic through a database online
     * You can do this by connecting with JDBC and then connecting with ObjectMapper.ReadTree(URL);
     */
    public static List<Product> readOrderJson(File file){
        //TODO Make backup files incase they click wrong file.
        List<Product> list = new ArrayList<>();
        ObjectMapper map = new ObjectMapper();

        try {
            JsonNode node = map.readTree(file);
            node.forEach(jsonNode -> {
                JsonParser parser = jsonNode.traverse();
                try {
                    list.add(map.readValue(parser, Product.class));
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
    public static void createTestOrderJson(){
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
}

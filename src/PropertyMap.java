package src;
import java.io.FileReader;
import java.util.*;
public class PropertyMap {
    private final Map<String, Object> defaultMap = new HashMap<>();
    private Map<String, Object> currentMap = new HashMap<>();

    /**
     * Constructor for the PropertyMap class
     * @param fileName - File Name as a String
     */
    PropertyMap (String fileName) {
        setDefaultMap();
        Properties properties = parseFile(fileName);
        if (properties.isEmpty()) {
            currentMap = defaultMap;
        }
        else {
            mapItems(properties);
        }
    }

    /**
     * Returns the value associated with a given key in the PropertyMap
     * @param key - Key for the PropertyMap passed as a string
     * @return - Returns the value in the property map associated with the given key
     */
    public Object get(String key) {
        return currentMap.get(key);
    }

    /**
     * Takes in the properties as a parameter and checks to see if the values are valid.
     * Stores the correct key-value pairs in a HashMap
     *
     * @param properties - Properties parsed from a file
     */
    private void mapItems (Properties properties) {
        for (String key : properties.stringPropertyNames()) {
            try {
                String value = String.valueOf(properties.get(key));
                switch (key) {
                    case "structures" -> {
                        if (properties.get(key).equals("linked") || !properties.get(key).equals("array")) {
                            currentMap.put(key, defaultMap.get(key));
                        } else {
                            currentMap.put(key, value);
                        }
                    }
                    case "floors" -> {
                        if (Integer.parseInt(value) < 2) {
                            currentMap.put(key, defaultMap.get(key));
                        } else {
                            currentMap.put(key, Integer.parseInt(value));
                        }
                    }
                    case "passengers" -> {
                        if (Float.parseFloat(value) <= 0 || Float.parseFloat(value) >= 1.0) {
                            currentMap.put(key, defaultMap.get(key));
                        } else {
                            currentMap.put(key, Float.parseFloat(value));
                        }
                    }
                    case "elevators", "elevatorCapacity", "duration" -> {
                        if (Integer.parseInt(value) < 1) {
                            currentMap.put(key, defaultMap.get(key));
                        } else {
                            currentMap.put(key, Integer.parseInt(value));
                        }
                    }
                }
            } catch (Exception e) {
                currentMap.put(key, defaultMap.get(key));
            }
        }
        for (String key : currentMap.keySet()) {
            if (!currentMap.containsKey(key)) {
                currentMap.put(key, defaultMap.get(key));
            }
        }
    }

    /**
     * Establishes the default map for cases of incorrect/missing values in property.txt file
     */
    private void setDefaultMap() {
        defaultMap.put("structures", "linked");
        defaultMap.put("floors", 32);
        defaultMap.put("passengers", 0.03);
        defaultMap.put("elevators", 1);
        defaultMap.put("elevatorCapacity", 10);
        defaultMap.put("duration", 500);
    }

    /**
     * Returns the Properties provided after parsing a file
     *
     * @param fileName - File name as a string
     * @return - Returns the Properties class loaded with the file data
     */
    public Properties parseFile(String fileName) {
        Properties properties = new Properties();
        try {
            FileReader reader = new FileReader(fileName);
            properties.load(reader);
        } catch (Exception e) {
            return properties;
        }
        return properties;
    }
}


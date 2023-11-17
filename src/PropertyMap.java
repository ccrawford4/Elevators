package src;

import java.io.FileReader;
import java.util.*;
public class PropertyMap {
    private final Map<String, Object> defaultMap = new HashMap<>();
    private Map<String, Object> currentMap = new HashMap<>();

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

    public Object get(String key) {
        return currentMap.get(key);
    }

    public void printMap() {
        for (String key : currentMap.keySet()) {
            System.out.println("Key: " + key + " Value: " + currentMap.get(key));
        }
    }

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

    private void setDefaultMap() {
        defaultMap.put("structures", "linked");
        defaultMap.put("floors", 32);
        defaultMap.put("passengers", 0.03);
        defaultMap.put("elevators", 1);
        defaultMap.put("elevatorCapacity", 10);
        defaultMap.put("duration", 500);
    }

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


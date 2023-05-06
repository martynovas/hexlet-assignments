package exercise;

import java.util.Map;

// BEGIN
public class FileKV implements KeyValueStorage {
    private String filePath;

    private Map<String, String> readFromFile() {
        String fileData = Utils.readFile(filePath);
        return Utils.unserialize(fileData);
    }

    private void writeToFile(Map<String, String> data) {
        String fileData = Utils.serialize(data);
        Utils.writeFile(filePath, fileData);
    }


    public FileKV(String filePath, Map<String, String> data) {
        this.filePath = filePath;
        writeToFile(data);
    }


    @Override
    public void set(String key, String value) {
        var map = readFromFile();
        map.put(key, value);
        writeToFile(map);
    }

    @Override
    public void unset(String key) {
        var map = readFromFile();
        map.remove(key);
        writeToFile(map);
    }

    @Override
    public String get(String key, String defaultValue) {
        var map = readFromFile();
        return map.getOrDefault(key, defaultValue);
    }

    @Override
    public Map<String, String> toMap() {
        return readFromFile();
    }

}
// END

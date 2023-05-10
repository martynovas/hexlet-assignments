package exercise;

import lombok.SneakyThrows;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

// BEGIN
public class App {
    @SneakyThrows
    public static void save(Path path, Car car){
        Files.write(path,car.serialize().getBytes());
    }

    @SneakyThrows
    public static Car extract(Path path){
        var data = Files.readString(path);
        return Car.unserialize(data);
    }
}
// END

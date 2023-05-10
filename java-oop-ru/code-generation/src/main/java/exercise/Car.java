package exercise;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;
import com.fasterxml.jackson.databind.ObjectMapper;

// BEGIN
@Value
// END
class Car {
    int id;
    String brand;
    String model;
    String color;
    User owner;

    // BEGIN
    @SneakyThrows

    public static Car unserialize(String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(data, Car.class);
    }
    @SneakyThrows
    public String serialize() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
    // END
}

package de.hilling.jee.payload;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
import java.time.LocalDateTime;

public class Greeting {
    @JsonbProperty("person-greeting")
    public final String greeting;
    @JsonbProperty("person-name")
    public final String name;
    @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss.SSS")
    public final LocalDateTime now = LocalDateTime.now();

    public Greeting(String greeting, String name) {
        this.greeting = greeting;
        this.name = name;
    }
}

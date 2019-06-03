package de.hilling.jee.json;

import de.hilling.jee.jpa.ReceivedRequest;

import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.time.LocalDateTime;

@RequestScoped
public class JsonRequestParser {

    public ReceivedRequest parse(String json) {
        final ReceivedRequest result = new ReceivedRequest();
        try (JsonReader reader = Json.createReader(new StringReader(json))) {
            final JsonObject jsonObject = reader.readObject();
            result.setDescription(jsonObject.getString("description"));
            result.setProject(jsonObject.getString("project"));
            result.setType(jsonObject.getString("type"));
            result.setSummary(jsonObject.getString("summary"));
            result.setRequestedAt(LocalDateTime.now());
            return result;
        }
    }

}

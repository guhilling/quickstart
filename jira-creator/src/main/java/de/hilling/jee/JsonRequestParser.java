package de.hilling.jee;

import de.hilling.jee.jpa.ReceivedRequest;

import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

@RequestScoped
public class JsonRequestParser {

    public ReceivedRequest parse(String json) {
        final ReceivedRequest result = new ReceivedRequest();
        final JsonReader reader = Json.createReader(new StringReader(json));
        final JsonObject jsonObject = reader.readObject();
        result.setContent(jsonObject.getString("content"));
        return result;
    }

}

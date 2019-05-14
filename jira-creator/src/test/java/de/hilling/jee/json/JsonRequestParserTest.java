package de.hilling.jee.json;

import de.hilling.jee.jpa.ReceivedRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonRequestParserTest {

    private JsonRequestParser parser;

    @BeforeEach
    void setup() {
        parser = new JsonRequestParser();
    }

    @Test
    void parse() {
        final ReceivedRequest receivedRequest = parser.parse("{ \"type\": \"Bug\", \"summary\": \"Alles kaputt!\"}");
        Assertions.assertEquals("Bug", receivedRequest.getType());
    }
}
package de.hilling.jee.json;

import de.hilling.jee.jpa.ReceivedRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonRequestParserTest {

    public static final String PROJECT_CDIT = "{ \"type\": \"Bug\", \"summary\": \"Alles kaputt!\", \"project\": \"CDIT\"}";
    private JsonRequestParser parser;

    @BeforeEach
    void setup() {
        parser = new JsonRequestParser();
    }

    @Test
    void parse() {
        final ReceivedRequest receivedRequest = parser.parse(PROJECT_CDIT);
        Assertions.assertEquals("Bug", receivedRequest.getType());
        Assertions.assertEquals("CDIT", receivedRequest.getProject());
    }
}
package de.hilling.jee;

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
        parser.parse("content");
    }
}
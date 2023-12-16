package org.comcom.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CustomLocalTimeDeserializer extends StdDeserializer<LocalTime> {

    public CustomLocalTimeDeserializer() {
        this(null);
    }

    public CustomLocalTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String timeAsString = node.toString();

        // Parse the provided time string to a LocalTime
        LocalTime localTime = LocalTime.parse(timeAsString, DateTimeFormatter.ofPattern("HH:mm:ss"));

        return localTime;
    }
}


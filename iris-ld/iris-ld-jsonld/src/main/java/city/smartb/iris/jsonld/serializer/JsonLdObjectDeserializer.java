package city.smartb.iris.jsonld.serializer;

import city.smartb.iris.jsonld.JsonLdObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Map;

public class JsonLdObjectDeserializer extends StdDeserializer<JsonLdObject> {

    public JsonLdObjectDeserializer() { this(null); }

    public JsonLdObjectDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public JsonLdObject deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> result = mapper.convertValue(node, new TypeReference<Map<String, Object>>(){});

        return new JsonLdObject(result);
    }
}

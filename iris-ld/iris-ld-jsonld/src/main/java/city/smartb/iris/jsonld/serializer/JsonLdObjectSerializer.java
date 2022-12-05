package city.smartb.iris.jsonld.serializer;

import city.smartb.iris.jsonld.JsonLdObject;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;

public class JsonLdObjectSerializer extends StdSerializer<JsonLdObject> {

    public JsonLdObjectSerializer() {
        this(null);
    }

    public JsonLdObjectSerializer(Class<JsonLdObject> t) {
        super(t);
    }

    @Override
    public void serialize(JsonLdObject value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeObject(value.asJson());
    }
}

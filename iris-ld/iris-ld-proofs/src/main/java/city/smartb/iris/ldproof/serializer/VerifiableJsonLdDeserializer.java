package city.smartb.iris.ldproof.serializer;

import city.smartb.iris.ldproof.VerifiableJsonLd;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Map;

public class VerifiableJsonLdDeserializer extends StdDeserializer<VerifiableJsonLd> {

    public VerifiableJsonLdDeserializer() { this(null); }

    public VerifiableJsonLdDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public VerifiableJsonLd deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> result = mapper.convertValue(node, new TypeReference<Map<String, Object>>(){});

        return new VerifiableJsonLd(result);
    }
}

package city.smartb.iris.did.deserializer;

import city.smartb.iris.did.DIDDocument;
import city.smartb.iris.did.model.DIDVerificationMethod;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Map;

public class DIDDocumentDeserializer extends StdDeserializer<DIDDocument> {

    public DIDDocumentDeserializer() { this(null); }

    public DIDDocumentDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public DIDDocument deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> result = mapper.convertValue(node, new TypeReference<Map<String, Object>>(){});

        return new DIDDocument(result);
    }
}

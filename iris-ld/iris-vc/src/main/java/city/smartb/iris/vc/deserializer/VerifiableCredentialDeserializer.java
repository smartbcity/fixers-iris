package city.smartb.iris.vc.deserializer;

import city.smartb.iris.vc.VerifiableCredential;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Map;

public class VerifiableCredentialDeserializer extends StdDeserializer<VerifiableCredential> {

    public VerifiableCredentialDeserializer() {
        this(null);
    }

    public VerifiableCredentialDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public VerifiableCredential deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = mapper.convertValue(node, new TypeReference<>(){});

        return new VerifiableCredential(result);
    }
}

package city.smartb.iris.did.deserializer;

import city.smartb.iris.did.model.DIDVerificationMethod;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DIDVerificationMethodDeserializer extends StdDeserializer<DIDVerificationMethod> {

    public DIDVerificationMethodDeserializer() { this(null); }

    public DIDVerificationMethodDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public DIDVerificationMethod deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        ObjectMapper mapper = new ObjectMapper();

//        List<Object> result = new LinkedHashMap<>();
//        node.forEach( it ->
//                result.
//        );
        Map<String, Object> result = mapper.convertValue(node, new TypeReference<Map<String, Object>>(){});

        return new DIDVerificationMethod(result);
    }
}

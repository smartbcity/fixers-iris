package city.smartb.iris.did.deserializer;

import city.smartb.iris.did.DIDDocument;
import city.smartb.iris.jsonld.JsonLdObject;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class DIDDocumentSerializer extends StdSerializer<DIDDocument> {

    public DIDDocumentSerializer() {
        this(null);
    }

    public DIDDocumentSerializer(Class<DIDDocument> t) {
        super(t);
    }

    @Override
    public void serialize(DIDDocument value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeObject(value.asJson());
    }
}

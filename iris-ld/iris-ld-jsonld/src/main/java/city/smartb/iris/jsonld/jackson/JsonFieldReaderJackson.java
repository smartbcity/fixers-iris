package city.smartb.iris.jsonld.jackson;

import city.smartb.iris.jsonld.reader.JsonField;
import city.smartb.iris.jsonld.reader.JsonFieldReader;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonFieldReaderJackson implements JsonFieldReader {

     private final ObjectMapper mapper;

     public JsonFieldReaderJackson() {
          this(new ObjectMapperFactory().create());
     }

     public JsonFieldReaderJackson(ObjectMapper mapper) {
          this.mapper = mapper;
     }

     public JsonField read(Map<String, Object> json, String key) {
          return new JsonFieldJackson(mapper, json, key);
     }
}

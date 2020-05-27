package city.smartb.iris.jsonld.jackson;

import city.smartb.iris.jsonld.reader.ToJsonConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ToToJsonConvertorJackson implements ToJsonConverter {

    private final ObjectMapper mapper;

    public ToToJsonConvertorJackson() {
        this(new ObjectMapperFactory().create());
    }

    public ToToJsonConvertorJackson(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> Object toJson(T object) {
        Object json = mapper.convertValue(object, new TypeReference<Object>() {});
        return json;
    }

    @Override
    public <T> List<Object> toJson(List<T> object) {
        List<Object> json = mapper.convertValue(object, new TypeReference<List<Object>>() {});
        return json;
    }
}

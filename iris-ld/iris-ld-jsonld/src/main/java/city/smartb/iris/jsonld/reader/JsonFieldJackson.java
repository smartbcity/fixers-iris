package city.smartb.iris.jsonld.reader;

import city.smartb.iris.jsonld.jackson.ObjectMapperFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JsonFieldJackson extends JsonField {

    private final ObjectMapper mapper;

    public JsonFieldJackson(ObjectMapper mapper, Map<String, Object> json, String key) {
        super(json, key);
        this.mapper = mapper;
    }

    public JsonFieldJackson(Map<String, Object> json, String key) {
        this(new ObjectMapperFactory().create(), json, key);
    }

    @Override
    public String asString() {
        Object obj = json.get(key);
        return mapper.convertValue(obj, String.class);
    }

    @Override
    public Integer asInteger() {
        Object obj = json.get(key);
        return mapper.convertValue(obj, Integer.class);
    }

    @Override
    public Map<String, Object> asMap() {
        Object obj = json.get(key);
        return mapper.convertValue(obj, new TypeReference<Map<String, Object>>() {});
    }

    @Override
    public List<Map<String, Object>> getMaps() {
        Object obj = json.get(key);
        return mapper.convertValue(obj, new TypeReference<List<Map<String, Object>>>() {});
    }

    @Override
    public <T> T asObject(Class<T> clazz) {
        Object obj = json.get(key);
        return mapper.convertValue(obj, clazz);
    }

    @Override
    public <T> List<T> asListObjects(Class<T> clazz) {
        Object obj = json.get(key);
        return mapper.convertValue(obj, new TypeReference<List<T>>() {});
    }

    @Override
    public LocalDate asLocalDate() {
        Object obj = json.get(key);
        return mapper.convertValue(obj, LocalDate.class);
    }

    @Override
    public LocalDateTime asLocalDateTime() {
        Object obj = json.get(key);
        return mapper.convertValue(obj, LocalDateTime.class);
    }
}

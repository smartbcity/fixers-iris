package city.smartb.iris.jsonld.reader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class JsonField {

    protected final Map<String, Object> json;
    protected final String key;

    public JsonField(Map<String, Object> json, String key) {
        this.json = json;
        this.key = key;
    }

    public abstract String asString();

    public abstract Integer asInteger();

    public abstract Map<String, Object> asMap();

    public abstract List<Map<String, Object>> getMaps();

    public abstract <T> T asObject(Class<T> clazz);

    public abstract <T> List<T> asListObjects(Class<T> clazz);

    public abstract LocalDate asLocalDate();

    public abstract LocalDateTime asLocalDateTime();
}

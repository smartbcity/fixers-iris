package city.smartb.iris.jsonld.reader;

import java.util.Map;

public interface JsonFieldReader {

     JsonField read(Map<String, Object> json, String key);
}

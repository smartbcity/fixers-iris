package city.smartb.iris.jsonld.reader;

import java.util.List;

public interface ToJsonConverter {

     <T> Object toJson(T json);

     <T> List<Object> toJson(List<T> object);
}

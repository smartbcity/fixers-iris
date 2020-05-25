package city.smartb.iris.did.model;

import city.smartb.iris.jsonld.JsonLdObject;
import city.smartb.iris.jsonld.reader.JsonFieldReader;

import java.util.Map;

public abstract class ControledJsonLdObject extends JsonLdObject {

	public static final String JSON_LD_CONTROLER = "controller";

	public ControledJsonLdObject(Map<String, Object> jsonLdObject) {
		super(jsonLdObject);
	}

	public ControledJsonLdObject(Map<String, Object> jsonLdObject, JsonFieldReader fieldReader) {
		super(jsonLdObject, fieldReader);
	}

	public String getController() {
		return this.get(JSON_LD_CONTROLER).asString();
	}

}
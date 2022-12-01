package city.smartb.iris.did.model;

import city.smartb.iris.jsonld.JsonLdObject;
import city.smartb.iris.jsonld.reader.JsonFieldReader;

import java.util.Map;

public class DIDService extends JsonLdObject {

	public static final String JSON_LD_SERVICE_ENDPOINT = "serviceEndpoint";

	public DIDService(Map<String, Object> json) {
		super(json);
	}

	public DIDService(Map<String, Object> json, JsonFieldReader reader) {
		super(json, reader);
	}

	public String getServiceEndpoint() {
		return this.get(JSON_LD_SERVICE_ENDPOINT).asString();
	}

}
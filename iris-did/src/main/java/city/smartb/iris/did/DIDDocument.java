package city.smartb.iris.did;

import city.smartb.iris.did.model.ControledJsonLdObject;
import city.smartb.iris.did.model.DIDAuthentication;
import city.smartb.iris.did.model.DIDPublicKey;
import city.smartb.iris.did.model.DIDService;
import city.smartb.iris.jsonld.reader.JsonFieldReader;
import city.smartb.iris.ldproof.LdProof;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DIDDocument extends ControledJsonLdObject {

	public static final String MIME_TYPE = "application/did+ld+json";

	public static final String JSON_LD_PUBLICKEY = "publicKey";
	public static final String JSON_LD_SERVICE = "service";
	public static final String JSON_LD_AUTHENTICATION = "authentication";

	public DIDDocument(Map<String, Object> json) {
		super(json);
	}

	public DIDDocument(Map<String, Object> json, JsonFieldReader reader) {
		super(json, reader);
	}

	public List<DIDPublicKey> getPublicKey() {
		return this.get(JSON_LD_PUBLICKEY).asListObjects(DIDPublicKey.class);
	}

	public List<DIDService> getService() {
		return this.get(JSON_LD_SERVICE).asListObjects(DIDService.class);
	}

	public List<DIDAuthentication> getAuthentication() {
		return this.get(JSON_LD_AUTHENTICATION)
				.asListObjects(Object.class)
				.stream()
				.map(it -> new DIDAuthentication(it))
				.collect(Collectors.toList());
	}

	public LdProof getProof() {
		Map<String, Object> map = this.get(LdProof.JSON_LD_PROOF).asMap();
		return LdProof.fromMap(map);
	}

}

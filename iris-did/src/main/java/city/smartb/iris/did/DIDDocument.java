package city.smartb.iris.did;

import city.smartb.iris.did.deserializer.DIDDocumentDeserializer;
import city.smartb.iris.did.model.ControledJsonLdObject;
import city.smartb.iris.did.model.DIDAuthentication;
import city.smartb.iris.did.model.DIDService;
import city.smartb.iris.did.model.DIDVerificationMethod;
import city.smartb.iris.jsonld.reader.JsonFieldReader;
import city.smartb.iris.ldproof.LdProof;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonDeserialize(using = DIDDocumentDeserializer.class)
public class DIDDocument extends ControledJsonLdObject {
	public static final String MIME_TYPE = "application/did+ld+json";
	public static final String JSON_LD_SERVICE = "service";
	public static final String JSON_LD_AUTHENTICATION = "authentication";
	public static final String JSON_LD_VERIFICATION_METHOD = "verificationMethod";

	public DIDDocument() { super( new LinkedHashMap<>()); }

	public DIDDocument(Map<String, Object> json) {
		super(json);
	}

	public DIDDocument(Map<String, Object> json, JsonFieldReader reader) {
		super(json, reader);
	}

	public List<DIDVerificationMethod> getVerificationMethod() {
		return this.get(JSON_LD_VERIFICATION_METHOD).asListObjects(DIDVerificationMethod.class);
	}

	public DIDDocument setVerificationMethod(DIDVerificationMethod method) {
		List<DIDVerificationMethod> methods = Collections.singletonList(method);
		List<Object> methodsJson = methods.parallelStream().map(it -> it.toJSON()).collect(Collectors.toList());
		jsonLdObject.put(DIDDocument.JSON_LD_VERIFICATION_METHOD, methodsJson);
		return this;
	}

	public List<DIDService> getService() {
		return this.get(JSON_LD_SERVICE).asListObjects(DIDService.class);
	}

	public List<DIDAuthentication> getAuthentication() {
		try {
			return this.get(JSON_LD_AUTHENTICATION)
					.asListObjects(Object.class)
					.stream()
					.map(it -> new DIDAuthentication(it))
					.collect(Collectors.toList());
		} catch (NullPointerException e) {
			return new ArrayList<>();
		}
	}

	public LdProof getProof() {
		Map<String, Object> map = this.get(LdProof.JSON_LD_PROOF).asMap();
		return LdProof.fromMap(map);
	}
}

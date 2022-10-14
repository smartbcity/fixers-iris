package city.smartb.iris.did.model;

import city.smartb.iris.did.deserializer.DIDVerificationMethodDeserializer;
import city.smartb.iris.jsonld.JsonLdObject;
import city.smartb.iris.jsonld.reader.JsonFieldReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonDeserialize(using = DIDVerificationMethodDeserializer.class)
public class DIDVerificationMethod extends ControledJsonLdObject {

	public static final String JSON_LD_PUBLICKEYBASE64 = "publicKeyBase64";
	public static final String JSON_LD_PUBLICKEYBASE58 = "publicKeyBase58";
	public static final String JSON_LD_PUBLICKEYHEX = "publicKeyHex";
	public static final String JSON_LD_PUBLICKEYPEM = "publicKeyPem";
	public static final String JSON_LD_PUBLICKEYJWK = "publicKeyJwk";
	public static final String RSA_VERIFICATION_2018 = "RsaVerificationKey2018";
	public DIDVerificationMethod(Map<String, Object> json) {
		super(json);
	}
	public DIDVerificationMethod() {
		super(new LinkedHashMap<>());
	}
	public DIDVerificationMethod(Map<String, Object> json, JsonFieldReader reader) {
		super(json, reader);
	}
	public String getPublicKeyBase64() {
		return this.get(JSON_LD_PUBLICKEYBASE64).asString();
	}
	public String getPublicKeyBase58() {
		return this.get(JSON_LD_PUBLICKEYBASE58).asString();
	}
	public String getPublicKeyHex() {
		return this.get(JSON_LD_PUBLICKEYHEX).asString();
	}
	public String getPublicKeyPem() {
		return this.get(JSON_LD_PUBLICKEYPEM).asString();
	}
	public Object getPublicKeyJwk() { return this.get(JSON_LD_PUBLICKEYJWK).asMap(); }
	public Object toJSON() {
		return jsonLdObject;
	}
}

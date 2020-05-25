package city.smartb.iris.did.model;

import city.smartb.iris.jsonld.reader.JsonFieldReader;
import java.util.Map;

public class DIDPublicKey extends ControledJsonLdObject {

	public static final String JSON_LD_PUBLICKEYBASE64 = "publicKeyBase64";
	public static final String JSON_LD_PUBLICKEYBASE58 = "publicKeyBase58";
	public static final String JSON_LD_PUBLICKEYHEX = "publicKeyHex";
	public static final String JSON_LD_PUBLICKEYPEM = "publicKeyPem";

	public DIDPublicKey(Map<String, Object> json) {
		super(json);
	}

	public DIDPublicKey(Map<String, Object> json, JsonFieldReader reader) {
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
}
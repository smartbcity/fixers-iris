package city.smartb.iris.did;

import city.smartb.iris.did.model.ControledJsonLdObject;
import city.smartb.iris.did.model.DIDVerificationMethod;
import city.smartb.iris.jsonld.JsonLdObject;
import city.smartb.iris.jsonld.reader.JsonFieldReader;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;

import javax.annotation.Nullable;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class DIDVerificationMethodBuilder extends JsonLdObject {

	@Nullable
	private PublicKey publicKey;

	public static DIDVerificationMethodBuilder create() {
		return new DIDVerificationMethodBuilder(new LinkedHashMap<>(), null);
	}

	public DIDVerificationMethodBuilder(Map<String, Object> json, PublicKey pubkey)
	{
		super(json);
		this.publicKey = pubkey;
	}

	public DIDVerificationMethodBuilder withId(String id) {
		jsonLdObject.put(JsonLdObject.JSON_LD_ID, id);
		return this;
	}
	
	public DIDVerificationMethodBuilder withController(String controller) {
		jsonLdObject.put(ControledJsonLdObject.JSON_LD_CONTROLER, controller);
		return this;
	}

	public DIDVerificationMethodBuilder withType(String type) {
		jsonLdObject.put(JsonLdObject.JSON_LD_TYPE, type);
		return this;
	}

	public DIDVerificationMethodBuilder withPublicKey(PublicKey key) {
		this.publicKey = key;
		return this;
	}

	public DIDVerificationMethod build() {
		addPublicKeyProperties();
		return new DIDVerificationMethod(this.jsonLdObject);
	}

	private DIDVerificationMethodBuilder addPublicKeyProperties() {
		if (this.publicKey == null) return this;

		if (this.getType() == DIDVerificationMethod.RSA_VERIFICATION_2018) {

			JWK jwk = new RSAKey.Builder((RSAPublicKey)this.publicKey)
					.keyUse(KeyUse.SIGNATURE)
					.keyID(UUID.randomUUID().toString())
					.build();

			jsonLdObject.put(DIDVerificationMethod.JSON_LD_PUBLICKEYJWK, jwk.toPublicJWK().toJSONObject());
		}

		return this;
	}
}

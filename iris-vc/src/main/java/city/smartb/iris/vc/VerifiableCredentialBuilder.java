package city.smartb.iris.vc;

import city.smartb.iris.jsonld.JsonLdObject;
import city.smartb.iris.jsonld.jackson.ToToJsonConvertorJackson;
import city.smartb.iris.ldproof.LdProof;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VerifiableCredentialBuilder<T> extends JsonLdObject {

    private final ToToJsonConvertorJackson convertorJackson = new ToToJsonConvertorJackson();

    public static <T> VerifiableCredentialBuilder<T> create() {
        return new VerifiableCredentialBuilder<T>(new LinkedHashMap<>());
    }

    public static <T> VerifiableCredentialBuilder<T> fromMap(LinkedHashMap<String, Object> json) {
        return new VerifiableCredentialBuilder<T>(json);
    }

    public static final String VC_DEFAULT_CONTEXT = "https://www.w3.org/2018/credentials/v1";

    public VerifiableCredentialBuilder(Map<String, Object> jsonLdObject) {
        super(jsonLdObject);
    }

    public VerifiableCredentialBuilder<T> withId(String id) {
        this.jsonLdObject.put(JsonLdObject.JSON_LD_ID, id);
        return this;
    }

    public VerifiableCredentialBuilder<T> withType(String type) {
        this.jsonLdObject.put(JsonLdObject.JSON_LD_TYPE, type);
        return this;
    }

    public <Ojb> VerifiableCredentialBuilder<T> with(String key, Ojb o) {
        this.jsonLdObject.put(key, convertorJackson.toJson(o));
        return this;
    }

    public <Ojb> VerifiableCredentialBuilder<T> with(String key, List<Ojb> o) {
        this.jsonLdObject.put(key, convertorJackson.toJson(o));
        return this;
    }

    public VerifiableCredentialBuilder<T> withContext(List<String> context) {
        this.jsonLdObject.put(JsonLdObject.JSON_LD_CONTEXT, context);
        return this;
    }

    public VerifiableCredentialBuilder<T> withContextDefault() {
        return withContext(Collections.singletonList(VC_DEFAULT_CONTEXT));
    }

    public VerifiableCredentialBuilder<T> withIssuanceDate(LocalDateTime issuanceDate) {
        this.jsonLdObject.put(VerifiableCredential.VC_ISSUANCE_DATE, issuanceDate.toString());
        return this;
    }

    public VerifiableCredentialBuilder<T> withIssuanceDate(String issuanceDate) {
        this.jsonLdObject.put(VerifiableCredential.VC_ISSUANCE_DATE, issuanceDate);
        return this;
    }

    public VerifiableCredentialBuilder<T> withIssuanceDateNow() {
        return withIssuanceDate(LocalDateTime.now().toString());
    }

    public VerifiableCredentialBuilder<T> withIssuer(String issuer) {
        this.jsonLdObject.put(VerifiableCredential.VC_ISSUER, issuer);
        return this;
    }


    public VerifiableCredentialBuilder<T> withCredentialSubject(T subject) {
        this.jsonLdObject.put(VerifiableCredential.VC_CREDENTIAL_SUBJECT, convertorJackson.toJson(subject));
        return this;
    }

    public VerifiableCredentialBuilder<T> withCredentialSubject(List<T> subject) {
        ToToJsonConvertorJackson convertorJackson = new ToToJsonConvertorJackson();
        this.jsonLdObject.put(VerifiableCredential.VC_CREDENTIAL_SUBJECT, convertorJackson.toJson(subject));
        return this;
    }

    public LinkedHashMap<String, Object> asJson() {
        return new LinkedHashMap<>(jsonLdObject);
    }

    public VerifiableCredential build(LdProof proof) {
        this.jsonLdObject.put(LdProof.JSON_LD_PROOF, proof.asJson());
        return  new VerifiableCredential(this.jsonLdObject);
    }
}

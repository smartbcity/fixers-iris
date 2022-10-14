package city.smartb.iris.did;

import city.smartb.iris.did.model.ControledJsonLdObject;
import city.smartb.iris.did.model.DIDAuthentication;
import city.smartb.iris.did.model.DIDService;
import city.smartb.iris.did.model.DIDVerificationMethod;
import city.smartb.iris.jsonld.JsonLdObject;
import city.smartb.iris.jsonld.reader.JsonFieldReader;
import city.smartb.iris.ldproof.LdProof;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DIDDocumentBuilder extends JsonLdObject {

    public static DIDDocumentBuilder create() {
        return new DIDDocumentBuilder(new LinkedHashMap<>());
    }

    public static DIDDocumentBuilder fromMap(LinkedHashMap<String, Object> json) {
        return new DIDDocumentBuilder(json);
    }

    public DIDDocumentBuilder(Map<String, Object> json) {
        super(json);
    }

    public DIDDocumentBuilder(Map<String, Object> json, JsonFieldReader reader) {
        super(json, reader);
    }

    public DIDDocumentBuilder withId(String id) {
        jsonLdObject.put(JsonLdObject.JSON_LD_ID, id);
        return this;
    }

    public DIDDocumentBuilder withVerificationMethods(List<DIDVerificationMethod> methods) {
        List<Object> methodsJson = methods.parallelStream().map(method -> method.asJson()).collect(Collectors.toList());
        jsonLdObject.put(DIDDocument.JSON_LD_VERIFICATION_METHOD, methodsJson);
        return this;
    }

    public DIDDocumentBuilder withVerificationMethod(DIDVerificationMethod method) {
        List<DIDVerificationMethod> methods = Collections.singletonList(method);
        return this.withVerificationMethods(methods);
    }

    public DIDDocumentBuilder withServices(List<DIDService> services) {
        List<Map<String, Object>> servicesJSon = services.parallelStream().map(service -> service.asJson()).collect(Collectors.toList());
        jsonLdObject.put(DIDDocument.JSON_LD_SERVICE, servicesJSon);
        return this;
    }

    public DIDDocumentBuilder withService(DIDService service) {
        List<DIDService> services = Collections.singletonList(service);
        return this.withServices(services);
    }

    public DIDDocumentBuilder withAuthentications(List<DIDAuthentication> authentications) {
        List<Object> authenticationsJson = authentications.parallelStream().map(authentication -> authentication.toJSON()).collect(Collectors.toList());
        jsonLdObject.put(DIDDocument.JSON_LD_AUTHENTICATION, authenticationsJson);
        return this;
    }

    public DIDDocumentBuilder withAuthentication(DIDAuthentication authentication) {
        List<DIDAuthentication> authentications = Collections.singletonList(authentication);
        return this.withAuthentications(authentications);
    }

    public DIDDocumentBuilder withController(String controller) {
        jsonLdObject.put(ControledJsonLdObject.JSON_LD_CONTROLER, controller);
        return this;
    }

    public List<DIDAuthentication> getProof() {
        return this.get(DIDDocument.JSON_LD_AUTHENTICATION)
                .asListObjects(Object.class)
                .stream()
                .map(it -> new DIDAuthentication(it))
                .collect(Collectors.toList());
    }

    public Map<String, Object> asJson() {
        return this.jsonLdObject;
    }

    public DIDDocument asJson(LdProof proof) {
        this.jsonLdObject.put(LdProof.JSON_LD_PROOF, proof);
        return  new DIDDocument(this.jsonLdObject);
    }
}

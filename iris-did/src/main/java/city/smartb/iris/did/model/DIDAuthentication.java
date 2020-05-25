package city.smartb.iris.did.model;

public class DIDAuthentication {

	private final Object json;

	public DIDAuthentication(Object json) {
		this.json = json;
	}

	public String getPublicKeyId() {
		if(json instanceof String) {
			return (String) json;
		}
		if(json instanceof DIDPublicKey) {
			return ((DIDPublicKey) json).getId();
		}
		return null;
	}

	public Object toJSON() {
		return json;
	}

}
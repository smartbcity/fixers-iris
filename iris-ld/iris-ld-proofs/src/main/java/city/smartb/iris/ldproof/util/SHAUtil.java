package city.smartb.iris.ldproof.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtil {

	private SHAUtil() {
	}

	public static byte[] sha256(String string) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			return digest.digest(string.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}


	}
}

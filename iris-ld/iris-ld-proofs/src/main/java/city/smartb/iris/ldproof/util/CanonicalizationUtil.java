package city.smartb.iris.ldproof.util;

import com.github.jsonldjava.core.JsonLdConsts;
import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;

public class CanonicalizationUtil {

	private CanonicalizationUtil() {
	}

	public static String buildCanonicalizedDocument(Object jsonLdObject) throws JsonLdError {
		JsonLdOptions options = new JsonLdOptions();
		options.format = JsonLdConsts.APPLICATION_NQUADS;
		String canonicalizedDocument = (String) JsonLdProcessor.normalize(jsonLdObject, options);
		return canonicalizedDocument;
	}

}

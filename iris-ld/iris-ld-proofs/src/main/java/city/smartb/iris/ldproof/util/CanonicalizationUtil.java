package city.smartb.iris.ldproof.util;

import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;

import com.apicatalog.jsonld.JsonLd;
import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.rdf.RdfDataset;
import com.apicatalog.rdf.RdfNQuad;
import com.apicatalog.rdf.RdfValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jsonldjava.core.JsonLdConsts;

import io.setl.rdf.normalization.RdfNormalize;
import jakarta.json.JsonStructure;

public class CanonicalizationUtil {

	private CanonicalizationUtil() {
	}

	public static String buildCanonicalizedDocument(Object jsonLdObject) {
		try {
			ObjectWriter writer = new ObjectMapper()
				.registerModule(new JavaTimeModule())
				.writer();
			String jsonStr = writer.writeValueAsString(jsonLdObject);

			JsonDocument json = JsonDocument.of(new StringReader(jsonStr));
			JsonStructure context = (JsonStructure) json.getJsonContent().get().asJsonObject().get(JsonLdConsts.CONTEXT);

			RdfDataset rdf = JsonLd.toRdf(json).context(context).get();
			RdfDataset rdfNormalized = RdfNormalize.normalize(rdf, "urdna2015");

			return stringify(rdfNormalized);
		} catch (JsonProcessingException | JsonLdError | NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String stringify(RdfDataset rdf) {
		return rdf.toList()
			.stream()
			.map(CanonicalizationUtil::stringify)
			.sorted()
			.collect(Collectors.joining());
	}

	private static String stringify(RdfNQuad quad) {
		StringBuilder builder = new StringBuilder();

		appendTo(builder, quad.getSubject());
		builder.append(" ");
		appendTo(builder, quad.getPredicate());
		builder.append(" ");
		appendTo(builder, quad.getObject());
		builder.append(" ");
		builder.append(quad.getGraphName().isPresent() ? quad.getGraphName() : ".");

		return builder.toString();
	}

	private static void appendTo(StringBuilder builder, RdfValue value) {
		builder.append("<").append(value).append(">");
	}
}

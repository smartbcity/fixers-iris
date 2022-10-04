package city.smartb.iris.ldproof.util;

import city.smartb.iris.jsonld.JsonLdConsts;
import com.apicatalog.jsonld.JsonLd;
import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.rdf.RdfDataset;
import com.apicatalog.rdf.RdfNQuad;
import com.apicatalog.rdf.RdfValue;
import com.apicatalog.rdf.io.nquad.NQuadsWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import foundation.identity.jsonld.JsonLDException;
import io.setl.rdf.normalization.RdfNormalize;
import jakarta.json.JsonStructure;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;

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
			return normalize(rdf, "urdna2015");
		} catch (JsonLDException | JsonLdError | NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static String normalize(RdfDataset rdf, String algorithm) throws JsonLDException, NoSuchAlgorithmException, IOException {
		RdfDataset rdfDataset = rdf;
		rdfDataset = RdfNormalize.normalize(rdfDataset, algorithm);
		StringWriter stringWriter = new StringWriter();
		NQuadsWriter nQuadsWriter = new NQuadsWriter(stringWriter);
		nQuadsWriter.write(rdfDataset);
		return stringWriter.getBuffer().toString();
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

package city.smartb.iris.ld.ldproof.util

import city.smartb.iris.ld.jsonld.JsonLdConsts
import com.apicatalog.jsonld.JsonLd
import com.apicatalog.jsonld.JsonLdError
import com.apicatalog.jsonld.document.JsonDocument
import com.apicatalog.rdf.RdfDataset
import com.apicatalog.rdf.RdfNQuad
import com.apicatalog.rdf.RdfValue
import com.apicatalog.rdf.io.nquad.NQuadsWriter
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import foundation.identity.jsonld.JsonLDException
import io.setl.rdf.normalization.RdfNormalize
import jakarta.json.JsonStructure
import java.io.IOException
import java.io.StringReader
import java.io.StringWriter
import java.security.NoSuchAlgorithmException

object CanonicalizationUtil {
    fun buildCanonicalizedDocument(jsonLdObject: Any?): String {
        return try {
            val writer = ObjectMapper()
                .registerModule(JavaTimeModule())
                .writer()
            val jsonStr = writer.writeValueAsString(jsonLdObject)
            val json = JsonDocument.of(StringReader(jsonStr))
            val context = json.jsonContent.get().asJsonObject()[JsonLdConsts.CONTEXT] as JsonStructure?
            val rdf = JsonLd.toRdf(json).context(context).get()
            normalize(rdf, "urdna2015")
        } catch (e: JsonLDException) {
            e.printStackTrace()
            throw RuntimeException(e)
        } catch (e: JsonLdError) {
            e.printStackTrace()
            throw RuntimeException(e)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            throw RuntimeException(e)
        } catch (e: IOException) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }

    @Throws(JsonLDException::class, NoSuchAlgorithmException::class, IOException::class)
    fun normalize(rdf: RdfDataset?, algorithm: String?): String {
        var rdfDataset = rdf
        rdfDataset = RdfNormalize.normalize(rdfDataset, algorithm)
        val stringWriter = StringWriter()
        val nQuadsWriter = NQuadsWriter(stringWriter)
        nQuadsWriter.write(rdfDataset)
        return stringWriter.buffer.toString()
    }

    private fun stringify(quad: RdfNQuad): String {
        val builder = StringBuilder()
        appendTo(builder, quad.subject)
        builder.append(" ")
        appendTo(builder, quad.predicate)
        builder.append(" ")
        appendTo(builder, quad.getObject())
        builder.append(" ")
        builder.append(if (quad.graphName.isPresent) quad.graphName else ".")
        return builder.toString()
    }

    private fun appendTo(builder: StringBuilder, value: RdfValue) {
        builder.append("<").append(value).append(">")
    }
}

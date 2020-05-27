package city.smartb.iris.api.rest.model

import city.smartb.iris.api.rest.utils.BaseTest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class MessageResponseTest : BaseTest() {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `should deserialiase MessageResponse`() {
        val json = """
              {
              	"action": "CREDENTIAL_REQUEST",
              	"payload": {
              		"claimsResponse": {
              			"availableCredentials": [{
              				"type": "Name",
              				"values": ["Seb", "Test5"]
              			}],
              			"requester": ""
              		}
              	}
              }
        """.trimIndent()
        val resp: MessageResponse = objectMapper.readValue(json, MessageResponse::class.java)
        val claimsResponse = resp.payload["claimsResponse"]

        Assertions.assertThat(claimsResponse).isNotNull
    }

}
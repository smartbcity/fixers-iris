package city.smartb.iris.did.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import ssm.chaincode.dsl.model.uri.ChaincodeUri

@Configuration
open class SsmConfig {
    @Value("\${ssm.channelId}")
    lateinit var channelId: String

    @Value("\${ssm.chaincodeId}")
    lateinit var chaincodeId: String

    @Value("\${ssm.ssmName}")
    lateinit var ssmName: String

    fun getChaincodeUri(): ChaincodeUri {
        return ChaincodeUri("${channelId}:${chaincodeId}:${ssmName}")
    }
}

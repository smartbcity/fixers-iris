package city.smartb.iris.s2.config

import city.smartb.iris.s2.domain.DidId
import city.smartb.iris.s2.domain.DidState
import city.smartb.iris.s2.domain.didS2
import city.smartb.iris.s2.entity.DidEntity
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import s2.dsl.automate.S2Automate
import s2.spring.automate.executor.S2AutomateExecutorSpring
import s2.spring.automate.ssm.S2SsmConfigurerAdapter
import ssm.chaincode.dsl.model.Agent
import ssm.chaincode.dsl.model.uri.ChaincodeUri
import ssm.chaincode.dsl.model.uri.from
import ssm.sdk.sign.extention.loadFromFile

@Configuration
open class DidS2Config(
	private val didS2Aggregate: DidS2Aggregate
) : S2SsmConfigurerAdapter<DidState, String, DidEntity, DidS2Aggregate>() {

	override fun automate(): S2Automate = didS2()
	override fun entityType(): Class<DidEntity> = DidEntity::class.java

	override fun executor(): DidS2Aggregate = didS2Aggregate

	override fun chaincodeUri(): ChaincodeUri {
		return ChaincodeUri.from(
			channelId = "sandbox",
			chaincodeId = "ssm",
		)
	}

	override fun signerAgent(): Agent {
		return loadFromFile("ssm-admin","user/ssm-admin")
	}

}

@Service
class DidS2Aggregate : S2AutomateExecutorSpring<DidState, DidId, DidEntity>()
@Bean
fun jacksonObjectMapperBean(): ObjectMapper = jacksonObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)

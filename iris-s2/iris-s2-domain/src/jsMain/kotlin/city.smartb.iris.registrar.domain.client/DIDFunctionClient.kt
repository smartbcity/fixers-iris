package city.smartb.iris.registrar.domain.client

import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.Protocol
import f2.client.ktor.get
import f2.dsl.fnc.F2Supplier
import kotlin.js.Promise

@JsName("didClient")
@JsExport
actual fun didClient(protocol: Protocol, host: String, port: Int, path: String?): F2Supplier<DIDFunctionClient> {
    return object : F2Supplier<DIDFunctionClient> {
        override fun invoke(): Promise<Array<DIDFunctionClient>> {
            return F2ClientBuilder.get(protocol, host, port, path).then { s2Client ->
                arrayOf(DIDFunctionClient(s2Client))
            }
        }
    }
}

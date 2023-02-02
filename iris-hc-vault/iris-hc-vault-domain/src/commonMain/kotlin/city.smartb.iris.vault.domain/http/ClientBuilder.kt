package city.smartb.iris.vault.domain.http

import io.ktor.client.HttpClient

interface ClientBuilder {
	fun build(): HttpClient
}

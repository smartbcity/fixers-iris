package city.smartb.iris.vault.domain.http

abstract class ClientJvm(
    baseUrl: String,
    generateBearerToken: suspend () -> String? = { null },
): Client(
    baseUrl = baseUrl,
    generateBearerToken = generateBearerToken,
    httpClientBuilder = HttpClientBuilderJvm
)

package backend.homework.itunes.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class Client {

    @Bean
    fun itunesClient(): RestClient {
        return RestClient.builder()
            .baseUrl("https://itunes.apple.com")
            .build()
    }
}
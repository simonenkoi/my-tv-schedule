package edu.khai.simonenko.mytvschedule.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class TvMazeIntegrationConfiguration {

    @Bean
    fun tvMazeWebClient(@Value("\${integration.tvmaze.url}") tvMazeUrl: String): WebClient {
        return WebClient.create(tvMazeUrl)
    }
}

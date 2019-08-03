package edu.khai.simonenko.mytvschedule.api.controller.advice

import edu.khai.simonenko.mytvschedule.common.Logging
import edu.khai.simonenko.mytvschedule.common.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.reactive.function.client.WebClientResponseException


@ControllerAdvice
class ShowEndpointExceptionsAdvice : Logging {

    @ExceptionHandler(WebClientResponseException::class)
    fun handleShowEndpointClientException(ex: WebClientResponseException): ResponseEntity<String> {
        logger().error("Error from Show Endpoint - Status {}, Body {}", ex.rawStatusCode, ex.responseBodyAsString, ex)
        return ResponseEntity.status(ex.rawStatusCode).body(ex.responseBodyAsString)
    }

}
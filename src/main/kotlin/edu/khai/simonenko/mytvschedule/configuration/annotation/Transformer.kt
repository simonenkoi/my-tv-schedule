package edu.khai.simonenko.mytvschedule.configuration.annotation

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class Transformer(

    @get:AliasFor(annotation = Component::class)
    val value: String = ""
)
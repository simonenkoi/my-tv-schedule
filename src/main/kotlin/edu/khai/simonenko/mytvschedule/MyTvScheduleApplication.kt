package edu.khai.simonenko.mytvschedule

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EntityScan(basePackages = ["edu.khai.simonenko.mytvschedule.repository.model"])
@EnableJpaRepositories(basePackages = ["edu.khai.simonenko.mytvschedule.repository"])
class MyTvScheduleApplication

fun main(args: Array<String>) {
    runApplication<MyTvScheduleApplication>(*args)
}

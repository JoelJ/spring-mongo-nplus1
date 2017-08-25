package com.workfront.mongotest

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing

@SpringBootApplication
@EnableMongoAuditing
open class MongoTestApp

fun main(args: Array<String>) {
    SpringApplication.run(MongoTestApp::class.java, *args)
}

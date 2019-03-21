package com.oauth.shenanigans.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OAuthDemoApplication

fun main(args: Array<String>) {
    runApplication<OAuthDemoApplication>(*args)
}

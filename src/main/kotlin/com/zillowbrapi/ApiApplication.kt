package com.zillowbrapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ZillowBrApiApplication

fun main(args: Array<String>) {
    runApplication<ZillowBrApiApplication>(*args)
}

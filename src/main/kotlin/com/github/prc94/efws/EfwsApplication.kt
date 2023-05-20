package com.github.prc94.efws

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EfwsApplication

fun main(args: Array<String>) {
	runApplication<EfwsApplication>(*args)
}
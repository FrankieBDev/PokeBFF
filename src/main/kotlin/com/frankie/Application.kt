package com.frankie

import com.frankie.com.frankie.plugins.configureMonitoring
import com.frankie.com.frankie.plugins.configureRouting
import com.frankie.com.frankie.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureMonitoring()
    configureRouting()
}

package com.frankie

//import com.frankie.com.frankie.plugins.configureMonitoring
import com.frankie.routes.configureRouting
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
}

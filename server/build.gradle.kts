plugins {
  application
  kotlin("jvm")
  kotlin("plugin.serialization")
}

application {
    mainClass.set("app.whiteboard.server.ApplicationKt")
}

dependencies {
  implementation(project(":common-core"))

  // Ktor
  implementation(libs.ktor.server.cors)
  implementation(libs.ktor.server.netty)
  implementation(libs.ktor.server.server)
  implementation(libs.ktor.server.websockets)
  implementation(libs.ktor.server.compression)
  implementation(libs.ktor.server.contentNegotiation)
  implementation(libs.ktor.serialization.json)

  implementation(libs.logback)

  // Test
  testImplementation(libs.kotlin.test)
}

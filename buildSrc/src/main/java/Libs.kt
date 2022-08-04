@file:Suppress("ClassName")

object libs {
  object kotlin {
    const val version = "1.7.10"
    const val test = "org.jetbrains.kotlin:kotlin-test:$version"
  }

  object kotlinx {
    object coroutines {
      private const val version = "1.6.4"

      const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
      const val debug = "org.jetbrains.kotlinx:kotlinx-coroutines-debug:$version"
      const val swing = "org.jetbrains.kotlinx:kotlinx-coroutines-swing:$version"
      const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
      const val play_services = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$version"
      const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object serialization {
      private const val version = "1.3.3"
      const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:$version"
    }

    const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.4.0"
  }

  object android {
    object versions {
      const val minSdk = 23
      const val compileSdk = 32
      const val targetSdk = compileSdk
      const val plugin = "7.2.1"
    }
  }

  object androidx {
    object compose {
      const val version = "1.3.0-alpha01"
      const val compilerVersion = "1.3.0-beta01"

      object ui {
        const val ui = "androidx.compose.ui:ui:$version"
        const val tooling = "androidx.compose.ui:ui-tooling:$version"
      }

      const val foundation = "androidx.compose.foundation:foundation:$version"
      const val material = "androidx.compose.material:material:$version"
    }

    object activity {
      private const val version = "1.4.0"
      const val compose = "androidx.activity:activity-compose:$version"
    }

    object lifecycle {
      private const val version = "2.4.1"
      const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
    }

    object appcompat {
      private const val version = "1.4.2"
      const val appcompat = "androidx.appcompat:appcompat:$version"
    }
  }

  object ktor {
    const val version = "2.0.3"

    object client {
      const val core = "io.ktor:ktor-client-core:$version"
      const val logging = "io.ktor:ktor-client-logging:$version"
      const val okhttp = "io.ktor:ktor-client-okhttp:$version"
      const val darwin = "io.ktor:ktor-client-darwin:$version"
      const val js = "io.ktor:ktor-client-js:$version"
      const val encoding = "io.ktor:ktor-client-encoding:$version"
      const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"
    }

    object server {
      const val netty = "io.ktor:ktor-server-netty:$version"
      const val server = "io.ktor:ktor-server:$version"
      const val cors = "io.ktor:ktor-server-cors:$version"
      const val websockets = "io.ktor:ktor-server-websockets:$version"
      const val compression = "io.ktor:ktor-server-compression:$version"
      const val contentNegotiation = "io.ktor:ktor-server-content-negotiation:$version"
    }

    object serialization {
      const val json = "io.ktor:ktor-serialization-kotlinx-json:$version"
    }
  }

  object koin {
    private const val version = "3.1.5"

    const val core = "io.insert-koin:koin-core:$version"
    const val android = "io.insert-koin:koin-android:$version"
    const val compose = "io.insert-koin:koin-androidx-compose:$version"
  }

  object desugar {
    private const val version = "1.1.5"
    const val jdk_libs = "com.android.tools:desugar_jdk_libs:$version"
  }

  const val logback = "ch.qos.logback:logback-classic:1.2.5"
  const val turbine = "app.cash.turbine:turbine:0.7.0"

  object plugins {
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.kotlin.version}"
    const val android = "com.android.tools.build:gradle:${libs.android.versions.plugin}"
    const val kotlin_serialization = "org.jetbrains.kotlin:kotlin-serialization:${libs.kotlin.version}"
    const val jb_compose = "org.jetbrains.compose:compose-gradle-plugin:1.2.0-alpha01-dev753"
  }
}

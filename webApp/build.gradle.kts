import org.jetbrains.compose.compose

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose")
}

kotlin {
  js(IR) {
    browser {
      commonWebpackConfig {
        cssSupport.enabled = true
      }
    }
    binaries.executable()
  }
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(project(":common-core"))
      }
    }

    val jsMain by getting {
      dependencies {
        implementation(compose.web.core)

        // Dev
        implementation(devNpm("sass-loader", "^13.0.0"))
        implementation(devNpm("sass", "^1.52.1"))
      }
    }

    configureEach {
      languageSettings {
        optIn("kotlin.RequiresOptIn")
        optIn("kotlin.ExperimentalStdlibApi")
        optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
      }
    }
  }
}
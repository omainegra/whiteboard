plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose")
}

compose.desktop {
  application {
    mainClass = "app.whiteboard.desktop.ApplicationKt"
  }
}

compose.experimental {
  web.application {}
}

kotlin {
  jvm()
  js(IR) {
    browser()
    binaries.executable()
  }
  listOf(macosX64(), macosArm64()).forEach { target ->
    target.binaries {
      executable {
        entryPoint = "main"
        freeCompilerArgs += listOf(
          "-linker-option", "-framework", "-linker-option", "Metal", "-Xdisable-phases=VerifyBitcode"
        )
      }
    }
  }

  sourceSets {
    all {
      languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
    }

    val commonMain by getting {
      dependencies {
        implementation(project(":common-core"))
        implementation(project(":common-ui"))
      }
    }

    val jvmMain by getting {
      dependencies {
        implementation(compose.desktop.currentOs)
        implementation(libs.kotlinx.coroutines.swing)
      }
    }

    val jsMain by getting {
      dependencies {
        implementation(compose.web.core)
      }
    }

    val macosX64Main by getting
    val macosArm64Main by getting
    val macosMain by creating {
      dependsOn(commonMain)
      macosX64Main.dependsOn(this)
      macosArm64Main.dependsOn(this)
    }
  }
}

plugins {
  kotlin("multiplatform")
  kotlin("plugin.serialization")
  id("com.android.library")
  id("com.rickclephas.kmp.nativecoroutines") version "0.12.6-new-mm"
}

kotlin {
  explicitApi()

  jvm()

  android()

  js(IR) {
    browser {
      commonWebpackConfig {
        cssSupport.enabled = true
      }
    }
    binaries.executable()
  }

  for (target in listOf(iosX64(), iosArm64(), iosSimulatorArm64(), macosX64(), macosArm64())) {
    target.binaries.framework {
      freeCompilerArgs = listOf("-Xgc=cms")
    }
  }

  sourceSets {
    all {
      languageSettings.optIn("kotlinx.coroutines.FlowPreview")
      languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
    }

    val commonMain by getting {
      dependencies {
        // Kotlinx
        implementation(libs.kotlinx.datetime)
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.serialization.json)

        // Network
        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.logging)
        implementation(libs.ktor.client.encoding)
        implementation(libs.ktor.client.contentNegotiation)
        implementation(libs.ktor.serialization.json)

        // DI
        implementation(libs.koin.core)
      }
    }

    val commonTest by getting {
      dependencies {
        implementation(libs.turbine)
        implementation(libs.kotlin.test)
        implementation(libs.kotlinx.coroutines.test)
      }
    }

    val androidMain by getting {
      dependencies {
        implementation(libs.ktor.client.okhttp)
        implementation(libs.androidx.lifecycle.viewmodel)
        implementation(libs.androidx.activity.compose)
        implementation(libs.kotlinx.coroutines.android)
      }
    }

    val jvmMain by getting {
      dependencies {
        implementation(libs.ktor.client.okhttp)
      }
    }

    val jsMain by getting {
      dependencies {
        implementation(libs.ktor.client.js)
        implementation(npm(name = "uuid", version = "8.3.2"))
      }
    }

    val iosX64Main by getting
    val iosArm64Main by getting
    val iosSimulatorArm64Main by getting
    val macosX64Main by getting
    val macosArm64Main by getting

    val darwinMain by creating {
      dependsOn(commonMain)
      iosX64Main.dependsOn(this)
      iosArm64Main.dependsOn(this)
      iosSimulatorArm64Main.dependsOn(this)
      macosX64Main.dependsOn(this)
      macosArm64Main.dependsOn(this)

      dependencies {
        // Network
        implementation(libs.ktor.client.darwin)
      }
    }
  }
}

android {
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  compileSdk = libs.android.versions.compileSdk

  defaultConfig {
    minSdk = libs.android.versions.minSdk
    targetSdk = libs.android.versions.targetSdk
  }
  compileOptions {
    // Flag to enable support for the new language APIs
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  dependencies {
    coreLibraryDesugaring(libs.desugar.jdk_libs)
  }
}

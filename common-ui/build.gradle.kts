import org.jetbrains.compose.compose

plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("org.jetbrains.compose")
}

kotlin {
  jvm()
  android()
  js(IR) {
    browser()
    binaries.library()
  }
  macosX64()
  macosArm64()

  sourceSets {
    val commonMain by getting {
      dependencies {
        api(project(":common-core"))

        api(compose.runtime)
        api(compose.foundation)
        api(compose.ui)
        api(compose.material)
      }
    }

    val jvmMain by getting

    val androidMain by getting

    val jsMain by getting {
      dependencies {
        implementation(compose.web.core)
      }
    }

    val macosMain by creating {
      dependsOn(commonMain)
    }

    val macosX64Main by getting {
      dependsOn(macosMain)
    }
    val macosArm64Main by getting {
      dependsOn(macosMain)
    }
  }
}

android {
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  compileSdk = libs.android.versions.compileSdk

  defaultConfig {
    minSdk = libs.android.versions.minSdk
    targetSdk = libs.android.versions.targetSdk

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),  "proguard-rules.pro"
      )
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
}

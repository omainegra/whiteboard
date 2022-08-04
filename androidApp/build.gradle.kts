plugins {
  id("com.android.application")
  kotlin("android")
  id("org.jetbrains.compose")
}

android {
  namespace = "app.whiteboard.android"
  compileSdk = libs.android.versions.compileSdk

  defaultConfig {
    minSdk = libs.android.versions.minSdk
    targetSdk = libs.android.versions.targetSdk

    applicationId = "app.whiteboard.android"
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {
  implementation(project(":common-core"))
  implementation(project(":common-ui"))

  implementation(libs.androidx.appcompat.appcompat)
  implementation(libs.androidx.activity.compose)
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    dependencies {
        classpath(libs.plugins.kotlin)
        classpath(libs.plugins.android)
        classpath(libs.plugins.jb_compose)
        classpath(libs.plugins.kotlin_serialization)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
    }
}

allprojects {
  repositories {
      google()
      mavenCentral()
      maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
      maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }

  group = "app.whiteboard"
  version = "1.0.0"

  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile> {
    kotlinOptions.suppressKotlinVersionCompatibilityCheck()
  }

  tasks.withType<org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile> {
    kotlinOptions.suppressKotlinVersionCompatibilityCheck()
  }

  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile> {
    kotlinOptions.suppressKotlinVersionCompatibilityCheck()
  }
}

fun org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions.suppressKotlinVersionCompatibilityCheck() {
  freeCompilerArgs += listOf("-P", "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=true")
}

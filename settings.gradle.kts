pluginManagement {
  repositories {
    gradlePluginPortal()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }
}

rootProject.name = "Whiteboard"

include(":common-core")
include(":common-ui")
include(":server")
include(":androidApp")
include(":desktopApp")
include(":webApp")

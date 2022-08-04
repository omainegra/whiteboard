# Whiteboard: A Kotlin (and Compose) Multiplatform demo

 ![Untitled](https://user-images.githubusercontent.com/7846263/182958297-3da12c00-b8a3-41e6-bf20-243381d5bf51.gif)

## Modules

- `common-core`
- `server`
- `common-ui`
- `androidApp`
- `iOSApp` 
- `desktopApp`
- `webApp`

## Requirements

- JDK **11** (creating a distributable desktop app (e.g.: `.dmg`, `.msi`, `.deb`) requires JDK **15**)
  - If using [SDKMAN](https://sdkman.io/) just run `sdk env install`

## Running the apps

1. Start web server locally

```
./gradlew :server:run
```

2. Replace [`host`][1] 
variable in `common-core` module to connect to your local server (e.g.: `10.0.0.2`)

3. Run Android app

```
./gradlew :androidApp:installDebug
adb shell am start -n app.whiteboard.android/.MainActivity
```

4. Run iOS app

Open XCode project from command line to preserve `JAVA_HOME` environment variable

```
open iOSApp/iOSApp.xcodeproj/
```

and run it.

4. Run JVM Desktop app

```
./gradlew :desktopApp:run
```

5. Run macOS native Desktop app

- Intel

```
./gradlew :desktopApp:runDebugExecutableMacosX64
```

- ARM

```
./gradlew :desktopApp:runDebugExecutableMacosArm64
```

6. Run Web app (Compose for Web)

```
./gradlew :webApp:jsBrowserRun
```

## Next steps

- Create a distributable NPM package and use it from a React app.

[1]: https://github.com/omainegra/whiteboard/blob/main/common-core/src/commonMain/kotlin/app/whiteboard/common/Api.kt#L67

# Repository Guidelines

## Project Structure & Architecture

PriceTag Mobile is a Kotlin/Jetpack Compose Android app using Material 3, Hilt, Retrofit, Coroutines/Flow, DataStore, and Navigation Compose. Use JDK 21 and the checked-in Gradle wrapper.

The `app/` module contains code under `app/src/main/java/com/example/price_tag/`. Features such as `auth`, `cart`, and `product` follow clean-architecture layers:

- `data/`: DTOs, mappers, remote sources, and repository implementations.
- `domain/`: framework-independent models, repository interfaces, and use cases.
- `presentation/`: Compose screens, ViewModels, contracts, and components.

Shared navigation, networking, DI, session storage, themes, and UI live in `core/`; resources are in `app/src/main/res/`. The `utilites/` library provides helpers such as `ResourceState`. Keep dependency versions in `gradle/libs.versions.toml` or existing `buildSrc/` constants. Never edit generated build output.

## Build, Test, and Development Commands

- `./gradlew :app:assembleDebug` builds the debug APK.
- `./gradlew :app:installDebug` installs it on a device or emulator.
- `./gradlew test` runs all local JUnit 4 tests.
- `./gradlew connectedAndroidTest` runs AndroidX JUnit/Espresso device tests.
- `./gradlew lint` runs Android static analysis.
- `./gradlew clean` clears stale generated output.

Before submitting changes, run `./gradlew test lint`; run device tests for Compose UI or Android-platform behavior.

## Coding Style & Feature Patterns

Follow the official Kotlin style in `gradle.properties`: four-space indentation and trailing commas in multiline declarations. Use `PascalCase` for classes/composables, `camelCase` for functions/properties, and `UPPER_SNAKE_CASE` for constants.

Preserve the MVI flow: Compose UI sends intents to a Hilt ViewModel exposing immutable state and one-off effects. Keep composables stateless; put business logic in use cases and networking in data sources/repositories. Reuse `core/ui/components` before adding UI. Follow existing Hilt patterns.

## API Integration & Security

The sibling `../PriceTag_Backend` Spring Boot project defines the API contract. Before changing `ApiService`, inspect its controller and request/response DTOs, then keep Kotlinx-serialized DTOs and mappers compatible. Do not compensate for backend contract defects in the UI.

Authentication belongs in DataStore and OkHttp interceptors; never handle or log tokens in screens. The development base URL is in `core/common/AppConstants.kt`. Do not commit secrets or `local.properties`.

## Testing, Commits & Pull Requests

Place JVM tests in `src/test` and device/UI tests in `src/androidTest`; name classes `*Test` and methods for behavior. No coverage threshold exists, so test changed mappers, use cases, and ViewModel transitions.

History favors `<scope>: <summary>`, for example `cart: fix pull-to-refresh state`. Keep commits focused. Pull requests should describe behavior and verification, link issues, identify API/configuration changes, and include visuals for UI work.

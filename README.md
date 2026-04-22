# Zebpay V3 Design System

Android multi-module project for Zebpay's Compose-based V3 UI system.

## Documentation

- Main docs entry point: [docs/README.md](docs/README.md)
- Component analysis overview: [docs/components/overview.md](docs/components/overview.md)
- Layer docs:
  - [Atoms](docs/components/atoms.md)
  - [Molecules](docs/components/molecules.md)
  - [Organisms](docs/components/organisms.md)
  - [Coverage gaps](docs/components/gaps.md)
- Machine-readable indexes:
  - [component-index.json](docs/components/component-index.json)
  - [component-summary.json](docs/components/component-summary.json)

## What is in this repository

- `:v3` (`com.android.library`): reusable design system + component library.
- `:catalog` (`com.android.application`): showcase app for browsing and testing components.
- `build-logic` (included build): convention plugins for Android + Kotlin + Compose defaults.

## High-level architecture

### `:v3` library module

Main packages:

- `com.zebpay.ui.designsystem.v3`
  - `color`: color tokens and schemes
  - `theme`: `ZTheme`, `ZebpayTheme`, typography, shapes, theme mode model
  - `utils`: preview annotations and helpers
- `com.zebpay.ui.v3.components`
  - `atoms`, `molecules`, `organisms`, `templates`
  - `resource`: icon mapping/resources
  - `organisms.webview`: `WebViewContainer`, `WebViewState`, `ZWebViewBridge`

The library publishes a Maven artifact:

- Group: `com.zebpay.ui.v3`
- Artifact: `v3`
- Version: `libraryVersion` Gradle property (fallback `1.0.0`)

### `:catalog` showcase app

- Application class: `ZCatalogApp` registers showcase modules into `ShowcaseRegistry`.
- Navigation host dynamically renders screens from registered `ShowcaseModule` items.
- Domains currently showcased: typography, colors, buttons, tabs, pills, tags, misc, header, keyboard, empty state, dropdown, toast, bottom sheet, input field.

## Build system

### Core setup

- Gradle wrapper: `9.3.1`
- Android Gradle Plugin: `9.1.1`
- Kotlin: `2.3.20`
- Java/Kotlin target: `17`
- Compose compiler plugin: `org.jetbrains.kotlin.plugin.compose`
- Compose BOM: `2026.03.01`

Versions are centralized in [`gradle/libs.versions.toml`](gradle/libs.versions.toml).

### Included build (`build-logic`)

Convention plugins:

- `zebpay.android.library.compose`
- `zebpay.android.application.compose`

They configure:

- `compileSdk` / `minSdk`
- Java 17 compile options
- Kotlin `compilerOptions` (`JvmTarget.JVM_17`, opt-ins)
- Compose `buildFeatures` and BOM wiring

Note: current `:v3` and `:catalog` modules are configured directly in their own `build.gradle.kts` files.

## Requirements

- JDK 17
- Android Studio version that supports:
  - AGP `9.1.1`
  - Kotlin `2.3.20`

This repo pins Gradle to Java 17 via `gradle.properties` and daemon JVM toolchain files.

## Common commands

From repo root:

```bash
# Build both modules
./gradlew :v3:assemble :catalog:assemble

# Install catalog on a connected device/emulator
./gradlew :catalog:installDebug

# Unit tests
./gradlew :v3:testDebugUnitTest :catalog:testDebugUnitTest

# Instrumentation tests (device/emulator required)
./gradlew :v3:connectedDebugAndroidTest :catalog:connectedDebugAndroidTest
```

## Using the library in another module

### Local module dependency

```kotlin
dependencies {
    implementation(project(":v3"))
}
```

### Basic theme usage

```kotlin
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme

@Composable
fun AppUi() {
    ZebpayTheme {
        // screen content
    }
}
```

## Publishing

### Publish locally

```bash
./gradlew :v3:publishReleasePublicationToLocalMavenRepository
```

Output repository: `local-maven/` (ignored by default in `.gitignore`).

### Publish to GitHub Packages

Set either Gradle properties or environment variables:

- `gpr.user` / `GITHUB_ACTOR`
- `gpr.key` / `GITHUB_TOKEN`

Then run:

```bash
./gradlew :v3:publishReleasePublicationToGitHubPackagesRepository
```

### Override version during publish

```bash
./gradlew :v3:publishReleasePublicationToLocalMavenRepository -PlibraryVersion=1.2.0
```

## Compose previews

Current state:

- `:v3` includes preview dependencies (`ui-tooling-preview`, debug `ui-tooling`).
- `:catalog` preview annotations exist across showcase screens, but preview tooling is not explicitly declared in `:catalog` and may rely on transitive dependencies from `:v3`.

If previews do not render in Studio, first verify IDE compatibility with AGP/Kotlin above, then add explicit preview deps to `:catalog` if needed.

## WebView features in `:v3`

`WebViewContainer` supports:

- runtime permission handling (location/microphone/file access flows)
- JS bridge integration through `ZWebViewBridge`
- external URL loading via `rememberWebViewState`

Library manifest currently declares:

- `ACCESS_FINE_LOCATION`
- `ACCESS_COARSE_LOCATION`
- `RECORD_AUDIO`

Consumers should review and keep only permissions they need for their app policy.

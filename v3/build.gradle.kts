import org.jetbrains.kotlin.gradle.dsl.JvmTarget

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

android {
    namespace = "com.zebpay.ui.v3"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

kotlin {
    compilerOptions {
        allWarningsAsErrors.set(false)
        jvmTarget.set(JvmTarget.JVM_17)
        optIn.add("androidx.compose.material3.ExperimentalMaterial3Api")
    }
}

dependencies {
    // Core AndroidX
    implementation(libs.androidx.core.ktx)

    // Compose BOM — controls all compose artifact versions
    implementation(platform(libs.androidx.compose.bom))

    // Compose — api() so consumers get them transitively
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.animation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    debugApi(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.font)
    api(libs.androidx.compose.constraint)

    // Image loading
    api(libs.coil.kt)
    api(libs.coil.kt.compose)
    api(libs.coil.kt.gif)
    api(libs.coil.kt.svg)
    api(libs.coil.kt.okhttp)

    // Lottie animations
    api(libs.lottie.compose)

    // Lifecycle & Navigation — api() for convenience of consumers
    api(libs.androidx.lifecycle.runtimeCompose)
    api(libs.androidx.navigation.compose)

    // WebView
    implementation(libs.androidx.webkit)

    // Logging (was transitive via :android:common; now direct)
    implementation(libs.timber)

    // Tests
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.zebpay.ui.v3"
            artifactId = "v3"
            version = project.findProperty("libraryVersion")?.toString() ?: "1.0.0"
            afterEvaluate {
                from(components["release"])
            }
        }
    }
    repositories {
        maven {
            name = "LocalMaven"
            url = uri("${rootDir}/local-maven")
        }
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/zebpay/zebpay-v3-design-system")
            credentials {
                username = project.findProperty("gpr.user")?.toString() ?: System.getenv("GITHUB_ACTOR") ?: ""
                password = project.findProperty("gpr.key")?.toString() ?: System.getenv("GITHUB_TOKEN") ?: ""
            }
        }
    }
}

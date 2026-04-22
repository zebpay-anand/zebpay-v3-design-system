import com.android.build.api.dsl.ApplicationExtension
import com.zebpay.configureAndroidCompose
import com.zebpay.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.compose")
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val extension = extensions.getByType<ApplicationExtension>()
            configureKotlinAndroid(extension)
            configureAndroidCompose(extension)
            extension.defaultConfig {
                targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
            }
        }
    }
}

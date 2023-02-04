import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

// Without these suppressions version catalog usage here and in other build
// files is marked red by IntelliJ:
// https://youtrack.jetbrains.com/issue/KTIJ-19369.
@Suppress("DSL_SCOPE_VIOLATION",)
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.hilt.plugin) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.gradleDependencyUpdate)
    alias(libs.plugins.spotless)
}

subprojects {
    apply(plugin = rootProject.libs.plugins.spotless.get().pluginId)
    spotless {
        val ktlintVersion = "0.43.2"
        kotlin {
            target("**/*.kt")
            targetExclude("**/build/**/*.kt")
            licenseHeaderFile(
                rootProject.file("spotless/copyright.kt"), "^(package|object|import|interface)"
            )
            ktlint(ktlintVersion)
        }

        kotlinGradle {
            target("**/*.gradle.kts")
            targetExclude("**/build/**/*.kts")
            licenseHeaderFile(
                rootProject.file("spotless/copyright.kts"),
                "(^(?![\\/ ]\\*).*\$)"
            )
            ktlint(ktlintVersion)
        }

        format("xml") {
            target("**/*.xml")
            targetExclude("**/build/**/*.xml")
            licenseHeaderFile(
                rootProject.file("spotless/copyright.xml"),
                "(<[^!?])")
        }
    }
}

fun String.isNonStable(): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(this)
    return isStable.not()
}

tasks.withType<DependencyUpdatesTask> {
    resolutionStrategy {
        componentSelection {
            all {
                if (candidate.version.isNonStable() && !currentVersion.isNonStable()) {
                    reject("Release candidate")
                }
            }
        }
    }
}

fun shouldTreatCompilerWarningsAsErrors(): Boolean {
    return project.findProperty("warningsAsErrors") == "true"
}

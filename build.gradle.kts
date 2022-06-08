import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import plugins.BuildPlugins
import utils.DependencyUpdates

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}

plugins.apply(BuildPlugins.GIT_HOOKS)
plugins.apply(BuildPlugins.KTLINT)

subprojects {
    plugins.apply(BuildPlugins.SPOTLESS)
    plugins.apply(BuildPlugins.KTLINT)
    plugins.apply(BuildPlugins.JACOCO)

    tasks {
        withType<Test> {
            testLogging {
                // set options for log level LIFECYCLE
                events = setOf(
                    org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.STARTED,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT
                )
                exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
                showExceptions = true
                showCauses = true
                showStackTraces = true
            }

            maxParallelForks =
                (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
        }
        withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = "11"
                kotlinOptions.allWarningsAsErrors = shouldTreatCompilerWarningsAsErrors()
                kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.register("dependencyUpdates", DependencyUpdatesTask::class.java) {
    rejectVersionIf {
        val current = DependencyUpdates.versionToRelease(currentVersion)
        if (current == utils.ReleaseType.SNAPSHOT) return@rejectVersionIf true
        val candidate = DependencyUpdates.versionToRelease(candidate.version)
        return@rejectVersionIf candidate.isLessStableThan(current)
    }
}

fun shouldTreatCompilerWarningsAsErrors(): Boolean {
    return project.findProperty("warningsAsErrors") == "true"
}

package app.passwordstore.gradle

import com.github.benmanes.gradle.versions.VersionsPlugin
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import nl.littlerobots.vcu.plugin.VersionCatalogUpdateExtension
import nl.littlerobots.vcu.plugin.VersionCatalogUpdatePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType

@Suppress("Unused")
class DependencyUpdatesPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    project.pluginManager.apply(VersionsPlugin::class)
    project.pluginManager.apply(VersionCatalogUpdatePlugin::class)
    project.tasks.withType<DependencyUpdatesTask>().configureEach {
      rejectVersionIf {
        when (candidate.group) {
          "commons-codec",
          "com.android.tools.build",
          "org.eclipse.jgit" -> true
          else -> false
        }
      }
      checkForGradleUpdate = false
    }
    project.extensions.getByType<VersionCatalogUpdateExtension>().run {
      keep.keepUnusedLibraries.set(true)
    }
  }
}

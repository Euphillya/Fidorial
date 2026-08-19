package fr.fidorial.registrygen;

import fr.fidorial.registrygen.task.DownloadServerJarTask;
import fr.fidorial.registrygen.task.GenerateBlockStatesTask;
import fr.fidorial.registrygen.task.GeneratePacketsTask;
import fr.fidorial.registrygen.task.GenerateRegistriesTask;
import fr.fidorial.registrygen.task.GenerateReportsTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskProvider;

import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Gradle plugin that downloads the Minecraft server, generates Mojang reports,
 * and generates typed registry source files.
 *
 * @since 0.1.0
 */
public final class FidorialRegistryGeneratorPlugin implements Plugin<Project> {

  public static final String DOWNLOAD_MANIFEST_URL = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";

  public static final String EXTENSION_NAME = "fidorialRegistryGenerator";
  public static final String DOWNLOAD_TASK_NAME = "downloadMinecraftServer";
  public static final String REPORTS_TASK_NAME = "generateMinecraftReports";
  public static final String REGISTRIES_TASK_NAME = "generateRegistries";
  public static final String PACKET_CATALOGS_TASK_NAME = "generatePacketCatalogs";
  public static final String BLOCK_STATES_TASK_NAME = "generateBlockStates";

  @Override
  public void apply(final Project project) {
    final FidorialRegistryGeneratorExtension extension = project.getExtensions().create(EXTENSION_NAME,
            FidorialRegistryGeneratorExtension.class);

    configureDefaults(project, extension);

    final TaskProvider<DownloadServerJarTask> downloadTask = registerDownloadTask(project, extension);
    final TaskProvider<GenerateReportsTask> reportsTask = registerReportsTask(project, extension, downloadTask);
    final TaskProvider<GenerateRegistriesTask> registriesTask = registerRegistriesTask(project, extension, reportsTask);
    registerPacketsTask(project, extension, reportsTask);
    registerBlockStatesTask(project, extension, reportsTask);

    registerLifecycleTask(project, registriesTask);
  }

  private static void configureDefaults(final Project project, final FidorialRegistryGeneratorExtension extension) {

    extension.getWorkingDirectory().convention(project.getLayout().getBuildDirectory().dir("working"));

    extension.getGeneratedSourcesDirectory().convention(project.getLayout()
            .getBuildDirectory()
            .dir("generated/sources/fidorialRegistries/"));

    extension.getGeneratedPackage().convention("fr.fidorial.registry");
    extension.getRegistries().convention(Map.of());
    extension.getDataGeneratorArguments().convention(List.of("--reports"));
  }

  private static TaskProvider<DownloadServerJarTask> registerDownloadTask(final Project project,
                                                                          final FidorialRegistryGeneratorExtension extension) {

    return project.getTasks().register(DOWNLOAD_TASK_NAME, DownloadServerJarTask.class, task -> {
      task.setGroup("fidorial registry generation");
      task.setDescription("Downloads the official Minecraft server JAR.");

      task.getMinecraftVersion().set(extension.getMinecraftVersion());
      task.getServerJar().set(extension.getWorkingDirectory()
              .file(extension.getMinecraftVersion().map(version -> "minecraft/" + version + "/jar/server.jar")));
    });
  }

  private static TaskProvider<GenerateReportsTask> registerReportsTask(final Project project,
                                                                       final FidorialRegistryGeneratorExtension extension,
                                                                       final TaskProvider<DownloadServerJarTask> downloadTask) {

    return project.getTasks().register(REPORTS_TASK_NAME, GenerateReportsTask.class, task -> {
      task.setGroup("fidorial registry generation");
      task.setDescription("Runs Mojang's data generator.");
      task.dependsOn(downloadTask);

      task.getMinecraftVersion().set(extension.getMinecraftVersion());

      task.getJavaExecutable().convention(Path.of(System.getProperty("java.home"), "bin", executableName("java")).toString());

      task.getDataGeneratorArguments().set(extension.getDataGeneratorArguments());
      task.getServerJar().set(downloadTask.flatMap(DownloadServerJarTask::getServerJar));

      task.getDataDirectory().set(extension.getWorkingDirectory().dir(extension.getMinecraftVersion()
              .map(version -> "minecraft/" + version + "/data")));
    });
  }

  private static TaskProvider<GenerateRegistriesTask> registerRegistriesTask(final Project project,
                                                                             final FidorialRegistryGeneratorExtension extension,
                                                                             final TaskProvider<GenerateReportsTask> reportsTask) {

    return project.getTasks().register(REGISTRIES_TASK_NAME, GenerateRegistriesTask.class, task -> {
      task.setGroup("fidorial registry generation");
      task.setDescription("Generates typed registry Java sources.");
      task.dependsOn(reportsTask);

      task.getMinecraftVersion().set(extension.getMinecraftVersion());
      task.getGeneratedPackage().set(extension.getGeneratedPackage());
      task.getRegistries().set(extension.getRegistries());
      task.getGenerateRegistryKey().set(extension.getGenerateRegistryKey().orElse(true));

      task.getReportsDirectory().set(reportsTask.flatMap(GenerateReportsTask::getDataDirectory)
                                             .map(directory -> directory.dir("generated/reports")));

      task.getGeneratedSourcesDirectory().set(extension.getGeneratedSourcesDirectory());
    });
  }

  private static TaskProvider<GeneratePacketsTask> registerPacketsTask(final Project project,
                                                                       final FidorialRegistryGeneratorExtension extension,
                                                                       final TaskProvider<GenerateReportsTask> reportsTask) {

    return project.getTasks().register(PACKET_CATALOGS_TASK_NAME, GeneratePacketsTask.class, task -> {
       task.setGroup("fidorial registry generation");
       task.setDescription("Generates packet identifier catalog classes.");
       task.dependsOn(reportsTask);

       task.onlyIf(_ -> extension.getGeneratePacketCatalogs().getOrElse(false));

       task.getPacketsReport().set(reportsTask.flatMap(GenerateReportsTask::getDataDirectory)
               .map(dir -> dir.file("generated/reports/packets.json")));

       task.getGeneratedSourcesDirectory().set(extension.getGeneratedSourcesDirectory());
    });
  }

  private static TaskProvider<GenerateBlockStatesTask> registerBlockStatesTask(final Project project,
                                                                               final FidorialRegistryGeneratorExtension extension,
                                                                               final TaskProvider<GenerateReportsTask> reportsTask) {

    return project.getTasks().register(BLOCK_STATES_TASK_NAME, GenerateBlockStatesTask.class, task -> {
       task.setGroup("fidorial registry generation");
       task.setDescription("Generates BlockType registrations from Mojang's blocks report.");
       task.dependsOn(reportsTask);

       task.onlyIf(_ -> extension.getGenerateBlockStates().getOrElse(false));

       task.getBlocksReport().set(reportsTask.flatMap(GenerateReportsTask::getDataDirectory)
              .map(dir -> dir.file("generated/reports/blocks.json")));

       task.getGeneratedSourcesDirectory().set(extension.getGeneratedSourcesDirectory());
    });
  }

  private static void registerLifecycleTask(final Project project, final TaskProvider<GenerateRegistriesTask> registriesTask) {
    project.getTasks().register("generateFidorialRegistries", task -> {
      task.setGroup("fidorial registry generation");
      task.setDescription("Runs the complete registry generation pipeline.");
      task.dependsOn(registriesTask);
    });
  }

  private static String executableName(final String executable) {
    return (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("win"))? executable + ".exe" : executable;
  }
}

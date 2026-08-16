package fr.euphyllia.fidorial.gradle.patcher.util

const val PLUGIN_NAME = "dependency patcher"
const val EXTENSION_NAME = "dependencyPatcher"

const val PATCHER_TASK_GROUP = PLUGIN_NAME
const val INTERNAL_PATCHER_TASK_GROUP = "$PLUGIN_NAME internal"

const val JAVA_PLUGIN_ID = "java"
const val PATCHED_CLASSIFIER = "patched"

const val DIFF_PATCH_COORDINATES_PREFIX = "io.codechicken:DiffPatch"

private const val CONFIG_PREFIX = EXTENSION_NAME

const val DIFF_PATCH_CONFIG_NAME = "${CONFIG_PREFIX}DiffPatchConfig"
const val DIFF_PATCH_RESOLVABLE_CONFIG_NAME = "${CONFIG_PREFIX}DiffPatchResolvableConfig"

private const val BUILD_OUTPUT_ROOT = "dependency-patcher"
private const val GENERATED_SOURCES_ROOT = "generated/sources/dependency-patcher"

fun sourcesConfigName(patchSetName: String) = "$CONFIG_PREFIX${patchSetName.replaceFirstChar(Char::uppercase)}SourcesConfig"
fun binaryConfigName(patchSetName: String) = "$CONFIG_PREFIX${patchSetName.replaceFirstChar(Char::uppercase)}BinaryConfig"

fun workspaceSourceSetName(patchSetName: String) = "${patchSetName}Workspace"
fun patchSourceSetName(patchSetName: String) = "${patchSetName}Patch"

fun applyPatchesTaskName(capitalized: String) = "apply${capitalized}Patches"
fun setupWorkspaceTaskName(capitalized: String) = "setup${capitalized}PatchWorkspace"
fun rebuildPatchesTaskName(capitalized: String) = "rebuild${capitalized}Patches"
fun extractWorkspaceChangesTaskName(capitalized: String) = "extract${capitalized}WorkspaceChanges"
fun patchedJarTaskName(capitalized: String) = "patched${capitalized}Jar"

fun patchOutputDir(patchSetName: String) = "$BUILD_OUTPUT_ROOT/$patchSetName"
fun patchedZipPath(patchSetName: String) = "${patchOutputDir(patchSetName)}/patched.zip"
fun rejectsDirPath(patchSetName: String) = "${patchOutputDir(patchSetName)}/rejects"
fun generatedSourcesDir(patchSetName: String) = "$GENERATED_SOURCES_ROOT/$patchSetName"

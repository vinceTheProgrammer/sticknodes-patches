extension {
    name = "extensions/extension.rve"
}

android {
    namespace = "io.github.vincetheprogrammer.extension"
}

val localProperties = File(rootDir, "local.properties")
if (localProperties.exists()) {
    localProperties.forEachLine {
        val (key, value) = it.split("=", limit = 2)
        project.extra[key.trim()] = value.trim()
    }
}
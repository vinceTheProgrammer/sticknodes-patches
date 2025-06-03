group = "io.github.vincetheprogrammer"

patches {
    about {
        name = "Sticknodes patches"
        description = "Sticknodes patch repository for ReVanced."
        source = "git@github.com:vinceTheProgrammer/sticknodes-patches.git"
        author = "ReVanced + vinceTheProgrammer"
        contact = "vincetheprogrammer@gmail.com"
        website = "https://github.com/vinceTheProgrammer/sticknodes-patches"
        license = "GNU General Public License v3.0"
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}

val localProperties = File(rootDir, "local.properties")
if (localProperties.exists()) {
    localProperties.forEachLine {
        val (key, value) = it.split("=", limit = 2)
        project.extra[key.trim()] = value.trim()
    }
}
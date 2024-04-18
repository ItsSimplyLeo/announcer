plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.papermc.paperweight.userdev") version "1.5.11"
    id("net.kyori.blossom") version "1.3.1"
}

group = "cx.leo.announcer"
version = "1.0.0"

var apiVersion = "1.20"
var pluginDescription = "AutoAnnouncer plugin using the PaperMC-API"
var authors = listOf("ItSimplyLeo")

repositories {
    mavenCentral()
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://ci.ender.zone/plugin/repository/everything/")
}

dependencies {
    // PaperMC-API
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")

    // PlaceholderAPI
    compileOnly("me.clip:placeholderapi:2.11.2")

    // Vault
    compileOnly("net.milkbowl.vault:VaultAPI:1.7")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()

        filesMatching("plugin.yml") {
            expand(
                "apiVersion" to apiVersion,
                "pluginVersion" to project.version,
                "description" to pluginDescription,
            )
        }
    }

    shadowJar {
        fun relocatePkg(pkg: String) = relocate(pkg, "${project.group}.lib.$pkg")
    }

    build {
        dependsOn(shadowJar)
    }
}
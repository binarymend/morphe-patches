group = "app.binarymend"

patches {
    about {
        name = "Binarymend Patches"
        description = "Custom Android patches maintained by Binarymend"
        source = "https://github.com/binarymend/morphe-patches"
        author = "binarymend"
        contact = "https://github.com/binarymend/morphe-patches/issues"
        website = "https://github.com/binarymend/morphe-patches"
        license = "GPLv3"
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}

dependencies {
    // Used by JsonGenerator.
    implementation(libs.gson)
}

tasks {
    register<JavaExec>("generatePatchesList") {
        description = "Build patch with patch list"

        dependsOn(build)

        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("app.morphe.util.PatchListGeneratorKt")
    }
    // Used by gradle-semantic-release-plugin.
    publish {
        dependsOn("generatePatchesList")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}

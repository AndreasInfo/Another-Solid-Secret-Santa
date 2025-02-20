plugins {
    application
}
group = ""
version = "1.0-SNAPSHOT"
description = "another-solid-secret-santa"
java.sourceCompatibility = JavaVersion.VERSION_23

application {
    mainClass = "de.amrim.Main"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("org.apache.logging.log4j:log4j-api:2.24.3")
    implementation("org.apache.logging.log4j:log4j-core:2.24.3")
    implementation("org.simplejavamail:simple-java-mail:8.12.4")
//    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.23.1")
//    implementation("org.slf4j:slf4j-simple:2.0.16")
    runtimeOnly("org.slf4j:slf4j-nop:2.0.16")
}


tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass
    }

    duplicatesStrategy = DuplicatesStrategy.WARN

    from(configurations.runtimeClasspath.map { config ->
        config.map { if (it.isDirectory) it else zipTree(it) }
    })
    exclude("**/DEPENDENCIES")
    exclude("**/LICENSE")
    exclude("**/LICENSE*.md")
    exclude("**/LICENSE*.txt")
    exclude("**/NOTICE")
    exclude("**/NOTICE*.md")
    exclude("**/NOTICE*.txt")
    exclude("**/module-info.class")
}

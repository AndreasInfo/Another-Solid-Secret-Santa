plugins {
    application
}
group = ""
version = "1.0-SNAPSHOT"
description = "another-solid-secret-santa"
java.sourceCompatibility = JavaVersion.VERSION_23

application {
    mainClass = "Main"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("org.apache.logging.log4j:log4j-api:2.24.3")
    implementation("org.apache.logging.log4j:log4j-core:2.24.3")
    implementation("org.simplejavamail:simple-java-mail:8.12.4")
}


tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

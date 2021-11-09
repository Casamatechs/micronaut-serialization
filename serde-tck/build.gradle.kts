plugins {
    id("io.micronaut.build.internal.module")
}

dependencies {
    annotationProcessor(mn.micronaut.inject.java)
    annotationProcessor(projects.serdeProcessor)
    implementation(mn.micronaut.inject.java)
    implementation(projects.serdeApi)
    implementation(projects.serdeProcessor)
    implementation(mn.micronaut.inject.java.test)
    compileOnly(mn.micronaut.inject.groovy)
    implementation(mn.micronaut.test.spock)

    if (!JavaVersion.current().isJava9Compatible()) {
        implementation(files(org.gradle.internal.jvm.Jvm.current().toolsJar))
    }
}

tasks["checkstyleMain"].enabled = false
tasks["checkstyleNohttp"].enabled = false
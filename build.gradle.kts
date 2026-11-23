import com.google.protobuf.gradle.id
import org.gradle.api.plugins.quality.Checkstyle

plugins {
    java
    checkstyle
    id("org.springframework.boot") version "4.1.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.google.protobuf") version "0.9.6"
}

val checkstyleVersion = "13.7.0"
val grpcVersion = "1.84.1"
val logstashLogbackEncoderVersion = "9.0"
val protobufVersion = "4.36.2"
val flyingSaucerVersion = "10.6.0"
val chaosMonkeyVersion = "4.0.0"
val junit5Version = "4.7.5"
val testcontainersVersion = "2.0.5"
val resilience4jVersion = "2.4.0"
val mapstructVersion = "1.6.3"
val lombokMapstructBindingVersion = "0.2.0"

group = "org.spring.divas"
version = "0.0.1-SNAPSHOT"
description = "venue-and-menu-service"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("de.codecentric:chaos-monkey-spring-boot:$chaosMonkeyVersion")
    implementation("org.springframework.boot:spring-boot-starter-kafka")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-aspectj")
    implementation("org.springframework.boot:spring-boot-starter-opentelemetry")
    implementation("io.github.resilience4j:resilience4j-spring-boot4:$resilience4jVersion")
    implementation("org.springframework.boot:spring-boot-starter-grpc-server")
    implementation("io.grpc:grpc-protobuf")
    implementation("io.grpc:grpc-stub")
    implementation("com.google.protobuf:protobuf-java:$protobufVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:${logstashLogbackEncoderVersion}")
    implementation("org.xhtmlrenderer:flying-saucer-pdf:${flyingSaucerVersion}")
    implementation("org.mapstruct:mapstruct:$mapstructVersion")

    compileOnly("jakarta.annotation:jakarta.annotation-api")
    compileOnly("org.projectlombok:lombok")

    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly("org.springframework.boot:spring-boot-restclient")
    runtimeOnly("org.postgresql:postgresql")

    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:$lombokMapstructBindingVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
    testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("au.com.dius.pact.provider:junit5:$junit5Version")
    testImplementation("org.testcontainers:testcontainers-postgresql:$testcontainersVersion")
    testImplementation("org.testcontainers:testcontainers-toxiproxy:$testcontainersVersion")

    testRuntimeOnly("com.h2database:h2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testCompileOnly("org.projectlombok:lombok")

    testAnnotationProcessor("org.projectlombok:lombok")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
            }
        }
    }
}

checkstyle {
    toolVersion = checkstyleVersion
    configFile = file("checks.xml")
}

tasks.named<Checkstyle>("checkstyleMain") {
    source = fileTree("src/main/java")
}

tasks.withType<Test> {
    useJUnitPlatform()
    val pactFolder = System.getProperty(
        "pactfolder.path",
        project.file("../common/contract/pact").absolutePath
    )
    inputs.dir(pactFolder)
    systemProperty("pactfolder.path", pactFolder)
}

import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    kotlin("jvm") version "1.4.10"
    id("com.google.protobuf") version "0.8.13"
    idea
}

val grpcVersion = "1.32.1"
val grpcKotlinVersion = "1.0.0"
val protobufVersion = "3.13.0"

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    google()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
    implementation("javax.annotation:javax.annotation-api:1.3.2")

    api("com.google.protobuf:protobuf-java-util:$protobufVersion")
    api("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")

    runtimeOnly("io.grpc:grpc-netty:$grpcVersion")
}


protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk7@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}

idea {
    module {
        sourceDirs.addAll(
            listOf(
                file("src/generated/main/java"),
                file("src/generated/main/grpc")
            )
        )
        generatedSourceDirs.addAll(
            listOf(
                file("src/generated/main/java"),
                file("src/generated/main/grpc")
            )
        )
    }
}
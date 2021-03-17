plugins {
  java
  kotlin("jvm") version "1.4.31"
  application
}

version = "0.1.0-Dev"
group = "moe.uki.app"

repositories {
  mavenCentral()
  maven{
    url = uri("https://github.com/psiegman/mvn-repo/raw/master/releases") // epublib
  }
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation("nl.siegmann.epublib:epublib-core:3.1")
  implementation("org.jsoup:jsoup:1.13.1")
}

java {
  sourceCompatibility = JavaVersion.VERSION_14
  targetCompatibility = JavaVersion.VERSION_14
}

tasks {
  compileKotlin {
    kotlinOptions.jvmTarget = "13"
  }
}

application {
  mainClassName = "moe.uki.app.epub2markdown.MainKt"
}

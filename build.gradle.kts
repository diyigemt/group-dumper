plugins {
  val kotlinVersion = "1.6.21"
  kotlin("jvm") version kotlinVersion
  kotlin("plugin.serialization") version kotlinVersion

  id("net.mamoe.mirai-console") version "2.11.1"
}

group = "com.diyigemt"
version = "1.0.0"

repositories {
  maven("https://maven.aliyun.com/repository/public")
  mavenCentral()
}

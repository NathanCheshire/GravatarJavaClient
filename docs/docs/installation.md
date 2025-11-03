---
sidebar_position: 2
---

# Installation

You can use GravatarJavaClient with the standard build systems.

## Gradle

Add the following to your **build.gradle** file:

```groovy
implementation 'io.github.nathancheshire:GravatarJavaClient:2.1.3'
```

Or if you're using Kotlin DSL, add the following to your **build.gradle.kts** file:

```kotlin
implementation("io.github.nathancheshire:GravatarJavaClient:2.1.3")
```

## Maven

Add the following dependency to your **pom.xml**:

```xml
<dependency>
    <groupId>io.github.nathancheshire</groupId>
    <artifactId>GravatarJavaClient</artifactId>
    <version>2.1.3</version>
</dependency>
```

## Jitpack

Add the JitPack repository to your **build.gradle**:

```groovy
repositories {
    mavenCentral()

    // Jitpack recommends this be at the end of the repositories block
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.nathancheshire:gravatarjavaclient:2.1.3'
}
```

If you would like to view the official Jitpack build artifacts and logs, click [here](https://jitpack.io/com/github/nathancheshire/gravatarjavaclient/).

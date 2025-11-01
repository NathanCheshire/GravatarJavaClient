---
sidebar_position: 2
---

# Installation

## Gradle

Add the following to your **build.gradle** file:

```groovy
implementation 'io.github.nathancheshire:GravatarJavaClient:2.1.0'
```

Or if you're using the Kotlin DSL, add the following to your **build.gradle.kts** file:

```kotlin
implementation("io.github.nathancheshire:GravatarJavaClient:2.1.0")
```

## Maven central

Add the following dependency to your **pom.xml**:

```xml
io.github.nathancheshire
GravatarJavaClient
2.1.0
```

### Jitpack

Add the JitPack repository to your **build.gradle**:

```groovy
repositories {
    mavenCentral()

    // Jitpack recommends this be at the end of the repositories block
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.nathancheshire:gravatarjavaclient:2.1.0'
}
```

If you would like to view the official Jitpack build log or artifacts list:

- [Artifacts](https://jitpack.io/com/github/nathancheshire/gravatarjavaclient/2.1.0/)
- [Build log](https://jitpack.io/com/github/nathancheshire/gravatarjavaclient/2.1.0/build.log)

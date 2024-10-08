# GravatarJavaClient

[![codecov](https://codecov.io/gh/NathanCheshire/GravatarJavaClient/branch/main/graph/badge.svg?token=T0DQD31N7S)](https://codecov.io/gh/NathanCheshire/GravatarJavaClient)

## Intro

This library is a Java/JVM wrapper for the Gravatar Avatar and Profile APIs.

## Using with Gradle

Add the following line in your `repositories` scope within your `build.gradle` (or `build.gradle.kts`).
Jitpack recommends it be placed at the end of the repositories scope.

`maven { url 'https://jitpack.io' }`

Then add the following to your dependencies:
`implementation 'com.github.nathancheshire:gravatarjavaclient:2.0.4'`.

# Jitpack artifacts

These links are mostly for me but if you would like to view the official Jitpack build log or artifacts list, these
are the following links:

Artifacts: https://jitpack.io/com/github/nathancheshire/gravatarjavaclient/VERSION/

Build log: https://jitpack.io/com/github/nathancheshire/gravatarjavaclient/VERSION/build.log

## API Support

This library features support for the comprehensive Gravatar API, that of both `avatar` and `profile` requests. This
library also follows [Effective Java](https://www.amazon.com/Effective-Java-Joshua-Bloch/dp/0134685997) principles.
The `GravatarAvatarRequest` handles all needs related to the `avatar` API whilst the `GravatarProfileRequest` handles
all needs related to the `profile` API.

For requesting Avatars (images):

```java
// Create the request
GravatarAvatarRequest request = GravatarAvatarRequest.fromEmail("your.email@email.com")
        .setSize(800)
        .setRating(GravatarRating.R)
        .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH);
  
// Get a BufferedImage
BufferedImage bufferedImage = request.getBufferedImage();

// Get an ImageIcon
ImageIcon imageIcon = request.getImageIcon();

// Save to a local File
boolean wasSaved = request.saveTo(new File("output.png"),"png");
```

For requesting Profiles:

```java
// Create the request
GravatarProfileRequest request = GravatarProfileRequest.fromEmail("your.email@email.com");

// Get the profile
GravatarProfile profile = request.getProfile();

// Save to a local File
boolean wasSaved = request.writeToFile(new File("MyProfile.json"));

// Get a GravatarProfile with authenticated fields present
GravatarProfileTokenProvider provider = new GravatarProfileTokenProvider(
        () -> new byte[]{0x12,0x34,0x56,0x78},"primaryAuthenticator");
GravatarProfile profileWithAuthenticatedFields = GravatarProfileRequest.fromEmail("your.email@email.com")
        .setTokenSupplier(provider)
        .getProfile();
```

For generating QR codes:

```java
// Create the request
GravatarQrCodeRequest request = GravatarQrCodeRequest.fromEmail("your.email@email.com")
        .setSize(800)
        .setImageType(GravatarQrImageType.USER)
        .setVersion(GravatarQrImageVersion.THREE);

// Get a BufferedImage
BufferedImage image = request.getBufferedImage();

// Save to a local file (API returns a PNG image)
boolean wasSaved = request.saveTo(new File("path/to/your/file.png"));
```

Because `GravatarProfileRequest` accepts a `GravatarProfileTokenProvider` instead of a literal string, you as the
developer have the ability to prevent token strings from appearing in the string pool if you deem it to be worth
the effort. Instead of simply returning a string from the provider, the provider returns a byte array which allows
you to grab your token using a method that does not create a string and pass it along to the `GravatarProfileRequest`
to use. The implementation of the `GravatarProfileRequest` class ensures that the token is never converted to a string.

## Contributing

If you think a feature is missing, or have an idea for how to improve the API, then by all means contribute! Make sure
to follow clean-code styles. I closely follow the principles set forth by books such as Effective Java by Joshua Bloch,
Clean Code, and The Clean Coder by Robert Cecil Martin.

General guidelines are as follows:

- Make sure your implementation closely matches the implementation you see present in the source files (same code style)
- Make sure to add Javadoc to every member, field, class, method, etc.
- Make sure to write unit tests if applicable for your added code up update any tests which might be failing due to a
  change you made
- Make sure all unit tests are passing before you submit a PR
- Make sure that code coverage is maintained at 100% before you submit a PR

## Issues

I do not care about a specific issue format, just make sure an issue does not already exist for what you want to report.
If you want to submit a PR to fix an issue, make sure to create an issue that you can reference from the PR if one does
not already exist.

## Resources

[Gravatar API Documentation](https://en.gravatar.com/site/implement)
<br/>
Raw link: https://en.gravatar.com/site/implement

[Abstract Factory Design Pattern](https://refactoring.guru/design-patterns/abstract-factory)
<br/>
Raw link: https://refactoring.guru/design-patterns/abstract-factory

[Builder Design pattern](https://refactoring.guru/design-patterns/builder)
<br/>
RawLink: https://refactoring.guru/design-patterns/builder

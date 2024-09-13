# GravatarJavaClient

![description](https://user-images.githubusercontent.com/60986919/219560104-58f321f8-4a7e-4d3a-9c73-884507442c36.png)

![author](https://user-images.githubusercontent.com/60986919/219560101-6fd7400d-4e24-49b9-9b3a-82247e777d81.png)

[![codecov](https://codecov.io/gh/NathanCheshire/GravatarJavaClient/branch/main/graph/badge.svg?token=T0DQD31N7S)](https://codecov.io/gh/NathanCheshire/GravatarJavaClient)

## Intro

This library is a Java wrapper for the Gravatar image and profile API.

## Using with Gradle

Add the following line in your `repositories` scope within your `build.gradle` (or `build.gradle.kts`).
Jitpack recommends it be placed at the end of the repositories scope.

`maven { url 'https://jitpack.io' }`

Then add the following to your dependencies:
`implementation 'com.github.nathancheshire:gravatarjavaclient:2.0.2'`.

## API Support

This library features support for the comprehensive Gravatar API, that of both `avatar` and `profile` requests. This
library also follows [Effective Java](https://www.amazon.com/Effective-Java-Joshua-Bloch/dp/0134685997) principles.
The `GravatarAvatarRequest` handles all needs related to the `avatar` API whilst the `GravatarProfileRequest` handles
all needs related to the `profile` API.

For requesting Avatars (images):

```java
// Get a BufferedImage
BufferedImage bufferedImage = GravatarAvatarRequest.fromEmail("your.email@email.com")
        .setSize(800)
        .setRating(GravatarRating.R)
        .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
        .getBufferedImage();

// Get an ImageIcon
ImageIcon imageIcon = GravatarAvatarRequest.fromEmail("your.email@email.com")
        .setSize(800)
        .setRating(GravatarRating.R)
        .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
        .getImageIcon();

// Save to a local File
boolean wasSaved = GravatarAvatarRequest.fromEmail("your.email@email.com")
        .setSize(800)
        .setRating(GravatarRating.R)
        .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
        .saveTo(new File("output.png"),"png");
```

For requesting Profiles:

```java
// Get a GravatarProfile
GravatarProfile profile = GravatarProfileRequest.fromEmail("your.email@email.com")
        .getProfile();

// Save to a local File
boolean wasSaved = GravatarProfileRequest.fromEmail("your.email@email.com")
        .writeToFile(new File("MyProfile.json"));

// Get a GravatarProfile with authenticated fields present
GravatarProfileTokenProvider provider = new GravatarProfileTokenProvider(
        () -> new byte[]{0x12,0x34,0x56,0x78},"primaryAuthenticator");
GravatarProfile profileWithAuthenticatedFields = GravatarProfileRequest.fromEmail("your.email@email.com")
        .setTokenSupplier(provider)
        .getProfile();
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

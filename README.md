# GravatarJavaClient

![description](https://user-images.githubusercontent.com/60986919/219560104-58f321f8-4a7e-4d3a-9c73-884507442c36.png)

![author](https://user-images.githubusercontent.com/60986919/219560101-6fd7400d-4e24-49b9-9b3a-82247e777d81.png)

[![codecov](https://codecov.io/gh/NathanCheshire/GravatarJavaClient/branch/main/graph/badge.svg?token=T0DQD31N7S)](https://codecov.io/gh/NathanCheshire/GravatarJavaClient)

## Intro

This library is a Java wrapper for the Gravatar image and profile API. When wanting to integrate the Gravatar API
into [Cyder](https://github.com/NathanCheshire/Cyder), a project of mine, I came across ancient Java API wrappers,
some of which have not been touched for 14 years! The last-updated statistic by itself does not indicate a library is
not maintained or poorly implemented, but in this case there were some issues and PRs on the GitHub repository which
were never addressed. I looked at this and thought "I can do it myself and do it better." Henceforth, GravatarJavaClient
was born.

## Using with Gradle

Make sure you have the following line in your `repositories` Gradle config (it is recommended that JitPack be at the end
of the repositories section):

`maven { url 'https://jitpack.io' }`

Then add the following to your dependencies:
`implementation 'com.github.nathancheshire:gravatarjavaclient:COMMIT_HASH_OR_RELEASE'`.

The `COMMIT_HASH_OR_RELEASE` may be whatever commit hash or release you want to use, `b011c6e` or `v1.0.0` for example.

## API Support

This client features support for the comprehensive Gravatar API, that of:

- Gravatar image requests
- Gravatar profile requests

This client also follows [Effective Java](https://www.amazon.com/Effective-Java-Joshua-Bloch/dp/0134685997) items, such
as item 52: refer to objects by their interfaces. I cannot see a case where a developer using this library would want to
implement their own `GravatarImageRequestBuilder`, but if for whatever reason it was desired, a developer could easily
do so by inheriting from the `GravatarImageRequestBuilder` interface. The `GravatarImageRequestHandler` will accept the
interface instead of my implementation (GravatarImageRequestBuilderImpl) making the API work for both mine and possibly
yours should you feel the need to write one. The default implementation (`GravatarImageRequestBuilderImpl`) I will be
maintaining and keeping up to date, ensuring tested and feature rich methods are exposed. If a method you would like to
exist in this class is not present, and you think others would benefit from it, feel free to implement it yourself and
create a PR.

## Getting Started

Using the client is extremely straight forward. `GravatarImageRequestHandlerImpl` uses a builder pattern allowing you to
set parameters as follows:

```java
GravatarImageRequestBuilderImpl builder=new GravatarImageRequestBuilderImpl("EmailAddress@email.domain.com")
        .setRating(GravatarRating.R)
        .setSize(500)
        .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
        .setShouldAppendJpgSuffix(true);
```

You may then pass this builder to the `GravatarImageRequestHandler` to perform certain actions:

- Getting the URL representing the state of your builder
- Getting a buffered image read from the generated URL
- Saving the buffered image to a file (if no file is provided, a file is generated with the naming scheme of "
  emailHash-timestamp")

```java
// Get the URL to the image.
String url=GravatarImageRequestHandler.buildUrl(builder);
        // Read the BufferedImage into memory from the URL.
        BufferedImage image=GravatarImageRequestHandler.getImage(builder);
        // Save the BufferedImage to the local directory.
        File imageFile=GravatarImageRequestHandler.saveImage(builder);
        // Save the bufferedImage to a specific File pointer.
        File imageFile=GravatarImageRequestHandler.saveImage(builder,new File("/path/to/my/image_file.png"));
```

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

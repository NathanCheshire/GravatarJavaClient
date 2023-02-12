# GravatarJavaClient
### By Nate Cheshire

## Intro

This library is a Java API wrapper for Gravatar image and profile requests. When wanting to integrate the Gravatar API into [Cyder](https://github.com/NathanCheshire/Cyder) (my main project), I came across super old Java API wrappers which have not been touched for 14 years! The last updated statistic by itself does not indicate a library is not maintained or poorly implemented, but in this case there were some issues and PRs on the GitHub repository which were never addressed. I looked at this and thought "I can do it myself and do it better." Henceforce, GravatarJavaClient was born, being named by ChatGPT.

## API Support

This client features support for all Gravatar requests, that of:
- Gravatar image requests
- Gravatar profile requests

This client also follows [Effective Java](https://www.amazon.com/Effective-Java-Joshua-Bloch/dp/0134685997) item 52, refer to objects by their interfaces. I can't see a case where a developer using this library wants to implement their own `GravatarImageRequestBuilder`, but if for whatever reason they wanted to, they could easily do so by implementing from the `GravatarImageRequestBuilder` interface. The default implementation, however, I'll be maintaining and keeping up to date, ensuring tested and feature rich methods are exposed. If a method you'd like to exist in this class is not present, feel free to make it yourself and create a PR.

## Getting Started

Using the client is extremely straight forward. `GravatarImageRequestHandlerImpl` uses a builder pattern allowing you to set parameters as follows:

```java
GravatarImageRequestBuilderImpl builder = new GravatarImageRequestBuilderImpl("EmailAddress@email.domain.com")
  .setRating(GravatarRating.R)
  .setSize(500)
  .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH)
  .setShouldAppendJpgSuffix(true);
```

You may then pass this builder to the `GravatarImageRequestHandler` to perform certain actions:
- Getting the URL representing the state of your builder
- Getting a buffered image read from the generated URL
- Saving the buffered image to a file (if no file is provided, a file is generated with the naming scheme of "emailHash-timestamp")

```java
String url = GravatarImageRequestHandler.buildUrl(builder);
BufferedImage image = GravatarImageRequestHandler.getImage(builder);
File imageFile = GravatarImageRequestHandler.getImage(builder);
File imageFile = GravatarImageRequestHandler.getImage(builder, new File("/path/to/my/image_file.png"));
```

## Contributing

If you think some feature is missing or have an idea for how to improve the API, then by all means, contribute! Make sure you follow clean-code styles and elagent API implementation. I closely follow the principles set forth in books such as Effective Java by Joshua Bloch, Clean Code, and The Clean Coder by Robert Cecil Martin.

General guidelines for this project are as follows:

- Make sure your implementation closely matches the implementation you see present in the source files (same code style)
- Make sure to add Javadoc to every member, field, class, method, etc.
- Make sure to write unit tests if applicable for your added code up update any tests which might be failing due to a change you made
- Make sure all unit tests are passing before you submit a PR
- Make sure that 100% code coverage is still maintained before you submit a PR

## Issues

I don't care really care about an issue format, just make sure an issue does not already exist for what you want to report. If you want to submit a PR to fix an issue, make sure to create an issue that you can reference from the PR if one does not aleady exist.

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

# GravatarJavaClient
### By Nate Cheshire

## Intro

This library is a Java API for Gravatar image and profile requests. When wanting to integrate the Gravatar API into [Cyder](https://github.com/NathanCheshire/Cyder) (my main project), I came across super old projects which were last updated 14 years ago! The last updated statistic by itself does not indicate a library is out of date, but in this case there were some issues and PRs on the GitHub repository which were never addressed. I looked at this and thought "I can do it myself and do it better." Henceforce, GravatarJavaClient was born, being named by ChatGPT.

## API Support

This client features support for all Gravatar requests, that of:
- Gravatar image Requests
- Gravatar profile requests

This client also follows Effective Java item 52, refer to objects by their interfaces. Currently, I can't see a need for a user of this library to implement their own `GravatarImageRequestBuilder`, but if for whatever reason a developer wanted to, they could easily do so by implementing from the `GravatarImageRequestBuilder` interface. The default implementation, however, I'll be maintaining and keeping up to date, ensuring tested and feature rich methods are exposed.

## Getting Started

todo

## Contributing

todo

## Issues

todo

## Resources

todo

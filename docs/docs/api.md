---
sidebar_position: 3
---

# API

The GravatarJavaClient API contains support for the comprehensive Gravatar API, that of both the **avatar** and **profile** requests. This
library also follows [Effective Java](https://www.amazon.com/Effective-Java-Joshua-Bloch/dp/0134685997) principles.
The `GravatarAvatarRequest` handles all needs related to the `avatar` API whilst the `GravatarProfileRequest` handles
all needs related to the `profile` API.

## Avatars

The standard Gravatar API for getting an avatar:

```java
// Define a reusable request
GravatarAvatarRequest request = GravatarAvatarRequest.fromEmail("your.email@email.com")
        .setSize(800)
        .setRating(GravatarRating.R)
        .setDefaultImageType(GravatarDefaultImageType.ROBO_HASH);

// You can get a BufferedImage
BufferedImage bufferedImage = request.getBufferedImage();

File profileFile = new File("/path/to/your/avatar.png", ".png");
boolean wasSaved = request.saveTo(profileFile, "png");
```

You can also check how many images have been saved to the local file system during the current JVM session:

```java
int count = GravatarAvatarRequestImageSaver.INSTANCE.getSavedCount();
```

## Profiles

When requesting profiles, there are two modes:

- unauthenticated
- authenticated

**unauthenticated** requests will not return certain fields whilst **authenticated** requests will return all available fields.

Authenticated requests require a valid API token to be provided which will be used in the authentication HTTP header.

```java
// Define a reusable request
GravatarProfileRequest request = GravatarProfileRequest.fromEmail("your.email@email.com");

// Get the profile
GravatarProfile profile = request.getProfile();

// Or save the profile to a JSON file
File profileJsonFile = new File("/path/to/profile.json")
boolean wasSaved = request.writeToFile(profileJsonFile);

// Get a GravatarProfile with authenticated fields present
GravatarProfile profileWithAuthenticatedFields = GravatarProfileRequest.fromEmail("your.email@email.com")
        .setToken("myApiToken")
        .getProfile();
```

You can also see how many unauthenticated and authenticated requests have been sent during the current JVM session:

```java
const unauthenticatedCount = GravatarProfileRequestHandler.INSTANCE.getUnauthenticatedRequestCount();
const authenticatedCount = GravatarProfileRequestHandler.INSTANCE.getAuthenticatedRequestCount();
```

## QR codes

You can generate a QR code for your Gravatar profile using a `GravatarQrCodeRequest`:

```java
// Define a reusable request
GravatarQrCodeRequest request = GravatarQrCodeRequest.fromEmail("your.email@email.com")
        .setSize(800)
        .setImageType(GravatarQrImageType.USER)
        .setVersion(GravatarQrImageVersion.THREE);

// Save the QR code to a file
File fileToSaveTo = new File("/path/to/your/qr_code_file.png")
boolean wasSaved = request.saveTo(fileToSaveTo);
```

Note, the Gravatar API returns a PNG for QR codes, presumably for lossless compression, which is why the GravatarJavaClient API does not allow for an encoding parameter in the way `GravatarAvatarRequest` does for the `saveTo` method.

You can also check how many QR codes have been saved to the local file system during the current JVM session:

```java
int count = GravatarAvatarRequestImageSaver.INSTANCE.getSavedCount();
```

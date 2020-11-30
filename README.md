## Application for agileengine Image gallery search test task

## Requirements
For building and running the application you need:

**JDK 11.0.2**

## Running the application

First of all, you need to specify **api.key** variable in *src/main/resources/application.properties* as it is in task description (I am not comitting it for security reasons)

As stated in p.6 you can also configure reload period of time variable here. Corresponding variable is **IMAGES_RELOAD_TIME_MS** (default variable now is 1800000ms (30mins)) 

One way to run the application, is to execute the main method in the *com.agileengineinterview.imagegallerysearch.ImageGallerySearchApplication*

Alternatively, you can run the following command in a terminal (in the image-gallery-search directory):

```
./mvnw spring-boot:run
```

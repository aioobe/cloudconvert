# CloudConvert Java API

[Jersey-client](https://jersey.java.net/documentation/2.7/user-guide.html#client) based implementation of the [CloudConvert.org REST API](https://cloudconvert.org/page/api).

## Dependency

```<dependency>
    <groupId>org.aioobe.cloudconvert</groupId>
    <artifactId>client</artifactId>
    <version>1.0</version>
</dependency>
```

## Example Usage

```java
// Create service object
CloudConvertService service = new CloudConvertService("<api key>");

// Create conversion process
ConvertProcess process = service.startProcess("jpg", "png");

// Perform conversion
process.startConversion(new File("test.jpg"));

// Wait for result
ProcessStatus status;
waitLoop: while (true) {
    status = process.getStatus();
    
    switch (status.step) {
    case FINISHED: break waitLoop;
    case ERROR: throw new RuntimeException(status.message);
    }
    
    // Be gentle
    Thread.sleep(200);
}

// Download result
service.download(status.output.url, new File("output.png"));

// Clean up
process.delete();
```

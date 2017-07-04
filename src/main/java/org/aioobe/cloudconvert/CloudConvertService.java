package org.aioobe.cloudconvert;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.*;
import java.net.*;
import java.util.List;

import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;

public class CloudConvertService {
    
    private final static String API_ROOT = "https://api.cloudconvert.com";
    
    String apiKey;
    WebTarget apiRoot;
    
    /**
     * Constructs a {@code CloudConvertService} with the given API key.
     */
    public CloudConvertService(String apiKey) {
        this.apiKey = apiKey;
        apiRoot = ClientUtil.createClient().target(API_ROOT);
    }
    
    /**
     * Starts a conversion process and returns the associated {@code ConvertProcess} object.
     */
    public ConvertProcess startProcess(String inputFormat, String outputFormat) throws URISyntaxException {
        ProcessArguments args = new ProcessArguments(apiKey, inputFormat, outputFormat);
        ProcessResponse response = apiRoot.path("process")
                                          .request(APPLICATION_JSON)
                                          .post(Entity.entity(args, APPLICATION_JSON),
                                                ProcessResponse.class);
        
        return new ConvertProcess(response.url, args);
    }
    
    /**
     * Returns a list of the currently running conversion processes.
     */
    public List<ProcessEntry> listRunningProcesses() {
        return apiRoot.path("processes")
                      .queryParam("apikey", apiKey)
                      .request(APPLICATION_JSON)
                      .get(new GenericType<List<ProcessEntry>>() {});
    }

    /**
     * Helper method for downloading a URL to a file.
     */
    public void download(URI url, File dest) throws IOException {
        InputStream is = new BufferedInputStream(download(url));
        
        FileOutputStream fos = new FileOutputStream(dest);
        
        int read;
        byte[] buf = new byte[16 * 1024];
        while ((read = is.read(buf)) != -1)
            fos.write(buf, 0, read);
        
        fos.close();
        is.close();
    }

    /**
     * Get an InputStream for downloading of a specific URL.
     */
    public InputStream download(URI url) {
        return ClientBuilder.newClient().target(url).request().get(InputStream.class);
    }
    
    @Override
    public String toString() {
        return String.format("%s[apiKey: %s]", getClass().getSimpleName(), apiKey);
    }
}

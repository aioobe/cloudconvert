package org.aioobe.cloudconvert;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.*;
import java.net.*;
import java.util.List;

import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;

public class CloudConvertService {
    
    private final static String API_ROOT = "https://api.cloudconvert.org";
    
    String apiKey;
    WebTarget apiRoot;
    
    public CloudConvertService(String apiKey) {
        this.apiKey = apiKey;
        apiRoot = ClientUtil.createClient().target(API_ROOT);
    }
    
    
    public ConvertProcess startProcess(String inputFormat, String outputFormat) throws URISyntaxException {
        ProcessArguments args = new ProcessArguments(apiKey, inputFormat, outputFormat);
        ProcessResponse response = apiRoot.path("process")
                                          .request(APPLICATION_JSON)
                                          .post(Entity.entity(args, APPLICATION_JSON),
                                                ProcessResponse.class);
        
        return new ConvertProcess(response.url, args);
    }
    
    
    public List<ProcessEntry> listRunningProcesses() {
        return apiRoot.path("processes")
                      .queryParam("apikey", apiKey)
                      .request(APPLICATION_JSON)
                      .get(new GenericType<List<ProcessEntry>>() {});
    }
    
    
    @Override
    public String toString() {
        return String.format("%s[apiKey: %s]", getClass().getSimpleName(), apiKey);
    }


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
    
    public InputStream download(URI url) {
        return ClientBuilder.newClient().target(url).request().get(InputStream.class);
    }
}

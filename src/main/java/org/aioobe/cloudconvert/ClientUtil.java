package org.aioobe.cloudconvert;

import javax.ws.rs.client.*;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

public class ClientUtil {
    
    /**
     * Creates a CloudConvert {@code Client} object.
     */
    public static Client createClient() {
        return ClientBuilder.newBuilder()
                            .register(JacksonFeature.class)
                            .register(new CloudConvertMapperProvider("https"))
                            .register(MultiPartFeature.class)
                            //.register(LoggingFilter.class) // For debugging
                            .build();
    }
    
}

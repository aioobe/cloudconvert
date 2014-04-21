package org.aioobe.cloudconvert;

import java.io.IOException;
import java.net.*;

import javax.ws.rs.ext.*;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.map.module.SimpleModule;

@Provider
public class CloudConvertMapperProvider implements ContextResolver<ObjectMapper> {
    
    String defaultScheme;
    
    public CloudConvertMapperProvider(String defaultScheme) {
        this.defaultScheme = defaultScheme;
    }
    
    public ObjectMapper getContext(Class<?> cls) {
        ObjectMapper mapper = new ObjectMapper().configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new UriSchemeModule());
        return mapper;
    }
    
    private class UriSchemeModule extends SimpleModule {

        public UriSchemeModule() {
            super(CloudConvertMapperProvider.class.getSimpleName(), Version.unknownVersion());
            
            addDeserializer(URI.class, new JsonDeserializer<URI>() {
                @Override
                public URI deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
                    URI url = URI.create(jp.readValueAs(String.class));
                    
                    if (url.getScheme() == null) {
                        try {
                            url = new URI(defaultScheme, url.getSchemeSpecificPart(), url.getFragment());
                        } catch (URISyntaxException e) {
                            throw new JsonProcessingException("Malformed URL", e) {};
                        }
                    }
                    return url;
                }
                
            });
        }
        
    }
}

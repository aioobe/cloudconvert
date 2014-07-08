package org.aioobe.cloudconvert;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;



@Provider
public class CloudConvertMapperProvider implements ContextResolver<ObjectMapper> {
    
    String defaultScheme;
    
    public CloudConvertMapperProvider(String defaultScheme) {
        this.defaultScheme = defaultScheme;
    }
    
    public ObjectMapper getContext(Class<?> cls) {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        mapper.setVisibility(PropertyAccessor.GETTER, Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.SETTER, Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.IS_GETTER, Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        mapper.setVisibility(PropertyAccessor.CREATOR, Visibility.ANY);
        
        mapper.registerModule(new UriSchemeModule());
        
        return mapper;
    }
    
    private class UriSchemeModule extends SimpleModule {

        public UriSchemeModule() {
            super(CloudConvertMapperProvider.class.getSimpleName());
            
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

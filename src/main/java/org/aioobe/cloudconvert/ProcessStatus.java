package org.aioobe.cloudconvert;

import java.net.URI;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ProcessStatus {
    
    public static enum Step {
        /** Input file is downloaded, e.g. from a URL or S3 storage. */
        INPUT,
        
        /** Conversion has to wait for some reason. Happens only in very special cases. */
        WAIT,
        
        /** The actucal conversion takes place. */
        CONVERT,
        
        /** The output file is uploaded, e.g. to S3, Google Drive or Dropbox. */
        OUTPUT,
        
        /** Something went wrong. message contains an error description. */
        ERROR,
        
        /** We are done! */
        FINISHED;
        
        @JsonCreator
        public static Step fromJson(String text) {
            return valueOf(text.toUpperCase());
        }
    }
    
    
    public String id;
    public String url;
    public double percent;
    public String message;
    public Step step;
    public long starttime;
    public long endtime;
    public long expire;
    public Input input;
    public Converter converter;
    public Output output;
    
    public static class Input {
        public String type;
        public String filename;
        public long size;
        public String name;
        public String ext;
    }
    
    public static class Converter {
        public String format;
        public String type;
        public double duration;
        public Map<String, String> options;
    }
    
    
    public static class Output {
        public String filename;
        public String ext;
        public long size;
        public URI url;
        public long downloads;
        public List<String> files;
    }
}

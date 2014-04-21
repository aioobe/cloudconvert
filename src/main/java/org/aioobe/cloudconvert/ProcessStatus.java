package org.aioobe.cloudconvert;

import java.net.URI;
import java.util.*;

public class ProcessStatus {
    
    public String id;
    public String url;
    public double percent;
    public String message;
    public String step;
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

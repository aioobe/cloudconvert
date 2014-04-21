package org.aioobe.cloudconvert;

public class ProcessArguments {
    
    String apikey;
    String inputformat;
    String outputformat;
    
    public ProcessArguments(String apikey, String inputformat, String outputformat) {
        this.apikey = apikey;
        this.inputformat = inputformat;
        this.outputformat = outputformat;
    }
    
}

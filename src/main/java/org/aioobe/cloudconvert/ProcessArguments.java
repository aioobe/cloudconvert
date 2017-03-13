package org.aioobe.cloudconvert;

public class ProcessArguments {
    
    String apikey;
    String inputformat;
    String outputformat;
    
    /**
     * Creates a {@code ProcessArguments} object with the given arguments.
     */
    public ProcessArguments(String apikey, String inputformat, String outputformat) {
        this.apikey = apikey;
        this.inputformat = inputformat;
        this.outputformat = outputformat;
    }
    
}

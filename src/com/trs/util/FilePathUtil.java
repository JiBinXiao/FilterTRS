package com.trs.util;

public class FilePathUtil {  
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");  
    //public static final String FILE_SEPARATOR = File.separator;  
    
    public static String getSplitFilePath() {
    	if(FILE_SEPARATOR.equals("\\"))
    		return FILE_SEPARATOR+FILE_SEPARATOR;
    	else if(FILE_SEPARATOR.equals("/"))
    		return FILE_SEPARATOR;
    	else
    		return null;
    		
    }
  
    public static String getRealFilePath(String path) {  
        return path.replace("/", FILE_SEPARATOR).replace("\\", FILE_SEPARATOR);  
    }  
  
    public static String getHttpURLPath(String path) {  
        return path.replace("\\", "/");  
    }  
} 

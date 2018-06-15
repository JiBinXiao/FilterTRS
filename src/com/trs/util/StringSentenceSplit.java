package com.trs.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本分割工具
 * @作者 邹许红
 * @日期 2017-8-7 上午9:53:49
 */
public class StringSentenceSplit {
	public static String regEx="[。？！?!]"; 
	public static   String[]  SentenceSplit(String str){
		  if(str==null)
			  return null;
		  Pattern p =Pattern.compile(regEx);  
          Matcher m = p.matcher(str);  
          
          /*按照句子结束符分割句子*/  
          String[] substrs = p.split(str);  
		  
          if(substrs.length > 0)  {
        	  int count = 0;  
              while(count < substrs.length)  
              {  
                  if(m.find())  
                  {  
                      substrs[count] += m.group();  
                  }  
                  count++;  
              } 
          }else{
        	  substrs=new String[1];
        	  substrs[0]=str;
          }
          
		return substrs;
	}
	
	
	
}

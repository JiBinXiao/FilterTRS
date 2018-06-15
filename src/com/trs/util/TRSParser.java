package com.trs.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * TRS数据文件解析
 * @author 邹许红
 * @date   2017/11/01
 */
public class TRSParser{
	
private static  Logger logger = Logger.getLogger(TRSParser.class);

public  static  List<Map<String,Object>> readFile(File  datafile,String charset){
	return readFile(datafile, charset, false, null);
}

/**
 * 解析trs格式文件
 * @param datafile
 *        数据文件
 * @param charset
 *        文件字符集
 * @param cp
 *        是否备份
 * @param cppath
 *        备份地址
 * @return
 */
public  static  List<Map<String,Object>> readFile(File  datafile,String charset,boolean cp,String cppath){
    List<String>  fileNames = new ArrayList<String>();
	
	if(datafile==null){
		logger.debug("读取数据文件null");
		return null;
	}
	if(!datafile.exists()){
		logger.debug("读取数据文件不存在");
		return null;
	}
	if(datafile.isDirectory()){
		logger.debug("读取为目录");
		return null;
	}
	
    String prefix = "";
    Pattern pattern = Pattern.compile("^" + prefix.replace("$", "\\$").replace("^", "\\^").replace("*", "\\*").replace("?", "\\?").replace("}", "\\}").replace("!", "\\!").replace("=", "\\=").replace("{", "\\{") + "<[^>=]+>=");
    LineIterator it = null;
    List<Map<String,Object>> outputDatas = new ArrayList<Map<String,Object>>();

    try {

	  it = FileUtils.lineIterator(datafile, charset);      
      int pos = -1;
      String line = null;
      StringBuilder fieldValue = null;
      
      Map<String,Object>  outputData = new HashMap<String, Object>();
      while (it.hasNext()) {
	         line = it.nextLine();

        if (line.equalsIgnoreCase(prefix + "<REC>")) {
        	 if (outputData != null && !outputData.isEmpty()) {
        		 if(pos>=0){
                 	outputData.put(fileNames.get(pos), fieldValue.toString());
                 }else{
                 	outputData = new HashMap<String, Object>();
                 }
                 pos = -1;
                 fieldValue = null;
                 outputDatas.add(outputData);
               }
        }else
        {
          Matcher matcher = pattern.matcher(line);
          if (matcher.find()) {
            String tmp = matcher.group();
            String fieldName = tmp.substring(tmp.lastIndexOf("<") + 1, tmp.length() - 2);
           if(fileNames.contains(fieldName)){
        	   
           }else{
        	   fileNames.add(fieldName);
           }
            if(pos>=0){
            	outputData.put(fileNames.get(pos), fieldValue.toString());
            }else{
            	outputData = new HashMap<String, Object>();
            }
            pos = fileNames.indexOf(fieldName);
            
            fieldValue = new StringBuilder(line.substring(tmp.length()));
          }else{
        	  if(fieldValue!=null)
        	  fieldValue.append("\n").append(line); 
        	  else
        		fieldValue = new StringBuilder();  
          }
        }
}
      if (outputData != null && !outputData.isEmpty()) {
 		 if(pos>=0){
          	outputData.put(fileNames.get(pos), fieldValue.toString());
          }else{
          	outputData = new HashMap<String, Object>();
          }
          pos = -1;
          fieldValue = null;
          outputDatas.add(outputData);
        }
      }catch (Exception e) {
		// TODO: handle exception
    	  logger.error("读取数据文件失败"+datafile.getAbsolutePath()+":"+e.getMessage());
    	  return null;
	}finally{
		it.close();
	
	}
	

	if(cp){
		String  path =  cppath+File.separator+DateUtils.dateFormat(new Date(), "yyyy-MM-dd");
		File file2 =new File(path);
		if(!file2.exists()){
			file2.mkdirs();
		}
		File fileto = new File(path+File.separator+datafile.getName());
		if(fileto.exists()){
			fileto.delete();
		}
		datafile.renameTo(new File(path+File.separator+datafile.getName()));
		File okfileto = new File(path+File.separator+datafile.getName().replace(".trs", ".ok"));
        if(!okfileto.exists()){
        	try {
				okfileto.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }

	}else{
		//datafile.delete();
	}
	return outputDatas;
}

/**
 * 提取TRS文件字段列表
 * @param templatePath
 *        模板地址
 * @return
 */
public  static  List<String>  readFileFs(String templatePath){
    List<String>  fileNames=new ArrayList<String>();
	
	if (StringUtils.isEmpty(templatePath)) {
      return fileNames;
    }
    File  file=new File(templatePath);
   
   String prefix = "";
    Pattern pattern = Pattern.compile("^" + prefix.replace("$", "\\$").replace("^", "\\^").replace("*", "\\*").replace("?", "\\?").replace("}", "\\}").replace("!", "\\!").replace("=", "\\=").replace("{", "\\{") + "<[^>=]+>=");
    LineIterator it=null;
	try {

		it = FileUtils.lineIterator(file, "GBK");
      
      boolean first = false;
      String line = null;
        while (it.hasNext()) {
	         line = it.nextLine();

        if (line.equalsIgnoreCase(prefix + "<REC>")) {
        	if(first){
        		 break;
        	}else{
        		first=true;
        	}
        }
        else
        {
          Matcher matcher = pattern.matcher(line);
          if (matcher.find()) {
            String tmp = matcher.group();
            String fieldName = tmp.substring(tmp.lastIndexOf("<") + 1, tmp.length() - 2);
                   
          if(fileNames.contains(fieldName)){}else{
        	  fileNames.add(fieldName);
          }
        }}
}
      }catch (Exception e) {
		// TODO: handle exception
	}
	return fileNames;
}


public static StringBuilder filePathHandling(File trsFile, StringBuilder builder)
{
  if (trsFile == null)
  {
    return builder;
  }

  String filePath = builder.toString();

  StringBuilder sb = new StringBuilder();
  String[] array = filePath.split(";");
  for (String path : array) {
    boolean atflag = false;
    if (path.startsWith("@")) {
      path = path.substring(1);
      atflag = true;
    }

    String path2 = path;
    int pos = path2.indexOf("^");
    if (pos > 0) {
      path2 = path2.substring(0, pos);
    }

    if (!new File(path2).exists()) {
      String path_ = path;
      if ((path.startsWith("/")) || (path.startsWith("\\"))) {
        path = path.substring(1);
      }

      if (new File(trsFile.getParent() + File.separator + path2).exists())
        path = trsFile.getParent() + File.separator + path;
      else {
        path = path_;
      }
    }
    if (atflag) {
      sb.append("@");
    }
    sb.append(path).append('');
  }
  sb.deleteCharAt(sb.length() - 1);
  return sb;
}
}


package com.trs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.ahocorasick.trie.Trie;


public class SystemConfig {
	public  static  String configname = "config.properties";
	//TRS文件路径
	public static String readpath="H:\\TRS\\data\\过滤\\";
	
	//企业名单 库路径
	public static String fullnamePath="H:\\TRS\\data\\过滤\\上市.txt";
	//企业名单 库路径
	public static String writePath="H:\\TRS\\data\\过滤\\过滤后";
    //日志文件
	public static String logpath="H:\\TRS\\data\\过滤\\Filter.log";


	
	//存储配置值
    public static   Properties props = new Properties();

	

	//从top.ini中初始化数据
	public  static  void  initconfig() throws UnsupportedEncodingException, IOException{
		 File   file=new   File("");   
		 String path=file.getAbsolutePath();
	
		 path=path+File.separator+SystemConfig.configname;

		 InputStream  inputstream  = new  FileInputStream(path);
		 props.load(new InputStreamReader(inputstream,"UTF-8"));
		
		 readpath = props.getProperty("readpath");
		 
		 fullnamePath = props.getProperty("fullnamePath");
		 writePath = props.getProperty("writePath");
		 logpath = props.getProperty("logpath");
		
		 
		 System.out.println("config.properties 加载成功");
		
	}
}
package com.trs;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.trs.domain.News;
import com.trs.task.NewFilterThreadTrie;
import com.trs.util.FileUtil;
import com.trs.util.SystemConfig;
import com.trs.util.TRSParser;


public class FilterTRSTest extends Thread{
	
	 public    boolean isrun = false;
	 
	 private  static List<String> fullnames=new ArrayList<>();
	 
	
	 
//20180412,20180413,20180414,20180415,20180416,20180510,20180511,20180512,20180513,20180514,20180515
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		// TODO Auto-generated method stub
		//20180510,20180511,20180412,20180413
		SystemConfig.initconfig();
		//初始化全称
		FileUtil.readFiletoTrie(SystemConfig.fullnamePath, "utf-8");		
//		Scanner scanner=new Scanner(System.in);
//		
//		String readfolderName=scanner.nextLine();
		System.out.println(args.length);
		if(args.length==0) {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			String readfolderName = formatter.format(currentTime);
			runee(readfolderName);
		}else {
			for (String readfolderName : args) {
				
				runee(readfolderName);

			}
		}
		
	}
	
	
	public static void runee(String readfolderName) {
		String readfolderPath=SystemConfig.readpath+readfolderName;

		File dataDir = new File(readfolderPath);
		if(!dataDir.exists()){
			System.out.println(readfolderPath+"该文件夹不存在");
			System.out.println("exit");
			return ;
		}
		
		//取出为.ok的文件
		File[] listfs = dataDir.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				if(pathname.getName().endsWith(".ok"))
					return true;
				return false;
			}
		});
		
	    ConcurrentLinkedDeque<File> concurrentLinkedDeque = new ConcurrentLinkedDeque<File>();
		//需要遍历的文件放入队列中
		for (File file : listfs) {
			concurrentLinkedDeque.add(file);
		}
	  
		NewFilterThreadTrie newFilterThread=new NewFilterThreadTrie(concurrentLinkedDeque);
		newFilterThread.setName("Thread "+readfolderName);
		newFilterThread.start();
	}

	
	
	/**
	 * 依据电脑CPU核心数，创建线程数组，返回每个线程遍历的文件名
	 * @param readfolderPath
	 * @return
	 */
	@Deprecated
	static List<List<String>>  getThreadList(String readfolderPath) {
		List<List<String>> threadList=new ArrayList<List<String>>();
		 //得到线程数
        int TreadCount=Runtime.getRuntime().availableProcessors();
        int avaliableTreadCount=TreadCount-4;
		
		File dataDir = new File(readfolderPath);
		if(!dataDir.exists()){
			System.out.println("该文件夹不存在");
			System.out.println("exit");
			return null;
			}
		
		//取出为.ok的文件
		File[] listfs = dataDir.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				if(pathname.getName().endsWith(".ok"))
					return true;
				return false;
			}
		});
		
		//将文件按照名称排序
		List<File> fileList = Arrays.asList(listfs);
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });
        
       
        //声明线程数组
        for (int i = 0; i < avaliableTreadCount; i++) {
        	ArrayList<String> temp=new ArrayList<>();
        	threadList.add(temp);
		}
        
        //初始化线程数组
        for (int i = 0; i < listfs.length; i++) {
        	String okfilepath = listfs[i].getAbsolutePath();   	
			threadList.get( i%avaliableTreadCount ).add(okfilepath);
		}
     
        
        return threadList;
	}

}

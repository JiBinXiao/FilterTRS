package com.trs.task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

import com.trs.domain.News;
import com.trs.util.FilePathUtil;
import com.trs.util.FileUtil;
import com.trs.util.SystemConfig;
import com.trs.util.TRSParser;

public class NewFilterThreadTrie extends Thread {
	 public    boolean isrun = false;
	 private List<String> readFilePath=null;
	 
	public  static  Trie trie=null;
	
	private  ConcurrentLinkedDeque<File> concurrentLinkedDeque = new ConcurrentLinkedDeque<File>();
	
	static {
		 trie = new Trie(false);
		 trie.setPool(true);
	}

    //线程安全的set
    public static Set<String> set=Collections.synchronizedSet(new HashSet<String>());
		
	 
	 NewFilterThreadTrie(List<String> readFilePath){
		 this.readFilePath=readFilePath;
	 }
	 
	 public NewFilterThreadTrie(ConcurrentLinkedDeque<File> concurrentLinkedDeque) {
		
		this.concurrentLinkedDeque=concurrentLinkedDeque;
	}

	@Override
	public synchronized void run() {
		if(isrun)
			return;
		isrun = true;
		try {
			long start=System.currentTimeMillis();
	
		   while (!concurrentLinkedDeque.isEmpty()) {
				File file= concurrentLinkedDeque.poll();	
				parse(file);
				String log=currentThread().getName()+"\t过滤\t"+file.getAbsolutePath()+"\t完成";
				System.out.println(log);
			}
			
			long end=System.currentTimeMillis();
			System.out.println(currentThread().getName()+"运行时间"+(end-start)/1000+"秒");
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		
		isrun = false;
		

	}
	 
	/**
	 * 过滤
	 * @param readFilePath
	 */
	void parse(File okFile) {
		
		String okfilepath = FilePathUtil.getRealFilePath(okFile.getAbsolutePath());

		String trsfilepath = FilePathUtil.getRealFilePath(okfilepath.replace(".ok", ".trs"));;
	  
		//取出.ok文件名
		String[] okfilenames=okfilepath.split(FilePathUtil.getSplitFilePath());
		
		String okfilename=okfilenames[okfilenames.length-1];
	
		String writeokfilename = SystemConfig.writePath+ FilePathUtil.FILE_SEPARATOR+okfilename;
	    
	    //取出.trs文件名
		String[] filenames=trsfilepath.split(FilePathUtil.getSplitFilePath());
		
		String trsfilename=filenames[filenames.length-1];
	
		String writefilename = SystemConfig.writePath+ FilePathUtil.FILE_SEPARATOR+trsfilename;
		
		File trsfile = new File(trsfilepath);
		if(!trsfile.exists()){
			return;
		}
		//读取出.trs文件
		List<Map<String, Object>> datalist = TRSParser.readFile(trsfile, "GBK");
		
		List<News> list=filterData(datalist);
		
		if(list.size()>0) {
			//生成.trs文件
			
			for (News news : list) {
				FileUtil.writeStringToFile(news.toTRS(), writefilename);
			}
			File file=new File(writeokfilename);
			//生成.ok文件
			if( !file.exists() )
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("创建"+writeokfilename+"文件失败！");
					e.printStackTrace();
				}	
		}
	}
	
	
	/**
	 * 过滤数据 只取出GROUPNAME为国内新闻，且标题和正文中含有企业名单库的数据
	 * @param datalist
	 * @return
	 */
	public List<News> filterData(List<Map<String, Object>> datalist) {
		List<News> list=new ArrayList<>();
		for (Map<String, Object> map : datalist) {
			
			String GROUPNAME=String.valueOf(map.get("IR_GROUPNAME"));
			//判断是否是国内新闻
			if( !GROUPNAME.equals("国内新闻") ) {
				continue;
			}
			
			String IR_URLTITLE = String.valueOf(map.get("IR_URLTITLE"));
			String IR_CONTENT = String.valueOf(map.get("IR_CONTENT"));
			String IR_HKEY = String.valueOf(map.get("IR_HKEY"));
			
			if("NULL".equals(IR_HKEY))
				IR_HKEY = null;
			
			//如果已经存储过此trs文件
			if(set.contains(IR_HKEY)) {
				continue;
			}
			
			//利用trie判断
			Collection<Emit> sd=null;
		
			sd=trie.parseText(IR_URLTITLE+"\t"+IR_CONTENT);

			if( !sd.isEmpty() ) {
				set.add(IR_HKEY);
				News news=mapToNews(map);
				list.add(news);
			}
			
			//在企业名单中依次判断
//			for (int i = 0; i < fullnames.size(); i++) {
//				String fullname=fullnames.get(i);
//			
//				//int index1=IR_URLTITLE.indexOf(fullname);
//				
//				//现在标题中判断是否含有企业名称，若存在，则直接取出。若没有，则在正文中去判断是否含有企业名称。
//				if(IR_URLTITLE.contains(fullname)) {
//					News news=mapToNews(map);
//					set.add(IR_HKEY);
//					list.add(news);
//					break;
//				}else {
//					String IR_CONTENT = String.valueOf(map.get("IR_CONTENT"));
//					//int index2=IR_CONTENT.indexOf(fullname);
//					
//					if(IR_CONTENT.contains(fullname)) {
//						News news=mapToNews(map);
//						set.add(IR_HKEY);
//						list.add(news);
//						break;
//					}
//					
//				}
//			}
		}	
		return list;
	}
	
	/**
	 * 将map中的数据转为news对象
	 * @param map
	 * @return
	 */
	public static News mapToNews(Map<String, Object> map) {
		String IR_URLNAME = String.valueOf(map.get("IR_URLNAME"));
		String IR_CONTENT = String.valueOf(map.get("IR_CONTENT"));
		
		String IR_URLTITLE = String.valueOf(map.get("IR_URLTITLE"));
		
		String IR_HKEY = String.valueOf(map.get("IR_HKEY"));
		
		if("NULL".equals(IR_HKEY))
			IR_HKEY = null;
		if("NULL".equals(IR_URLNAME))
			IR_URLNAME = null;
		
		String IR_SITENAME = String.valueOf(map.get("IR_SITENAME"));
		if("NULL".equals(IR_SITENAME))
			IR_SITENAME = null;
		
		String IR_CHANNEL = String.valueOf(map.get("IR_CHANNEL"));
		if("NULL".equals(IR_CHANNEL))
			IR_CHANNEL = null;
		
		String IR_GROUPNAME = String.valueOf(map.get("IR_GROUPNAME"));
		if("NULL".equals(IR_GROUPNAME))
			IR_GROUPNAME = null;
	
		if("NULL".equals(IR_URLTITLE))
			IR_URLTITLE = null;
	
		String IR_URLDATE = String.valueOf(map.get("IR_URLDATE"));
		if("NULL".equals(IR_URLDATE))
			IR_SITENAME = null;
		
		String IR_URLTIME = String.valueOf(map.get("IR_URLTIME"));
		if("NULL".equals(IR_URLTIME))
			IR_URLTIME = null;
		
		String IR_LOADTIME = String.valueOf(map.get("IR_LOADTIME"));
		if("NULL".equals(IR_LOADTIME))
			IR_LOADTIME = null;
		//
		String IR_SRCNAME = String.valueOf(map.get("IR_SRCNAME"));
		if("NULL".equals(IR_SRCNAME))
			IR_SRCNAME = null;
		
		String IR_KEYWORDS = String.valueOf(map.get("IR_KEYWORDS"));
		if("NULL".equals(IR_KEYWORDS))
			IR_KEYWORDS = null;
		
		String IR_ABSTRACT = String.valueOf(map.get("IR_ABSTRACT"));
		if("NULL".equals(IR_ABSTRACT))
			IR_ABSTRACT = null;
		
	
		if("NULL".equals(IR_CONTENT))
			IR_CONTENT = null;
		String IR_AUTHORS = String.valueOf(map.get("IR_AUTHORS"));
		if("NULL".equals(IR_AUTHORS))
			IR_AUTHORS = null;
	
		News news=new News();
		news.setIR_HKEY(IR_HKEY);
		news.setIR_URLNAME(IR_URLNAME);
		news.setIR_SITENAME(IR_SITENAME);
		news.setIR_CHANNEL(IR_CHANNEL);
		news.setIR_GROUPNAME(IR_GROUPNAME);
		news.setIR_URLTITLE(IR_URLTITLE);
		
		news.setIR_URLDATE(IR_URLDATE);
		news.setIR_URLTIME(IR_URLTIME);
		news.setIR_LOADTIME(IR_LOADTIME);
		news.setIR_SRCNAME(IR_SRCNAME);
		news.setIR_KEYWORDS(IR_KEYWORDS);
		
		news.setIR_ABSTRACT(IR_ABSTRACT);
		news.setIR_CONTENT(IR_CONTENT);
		news.setIR_AUTHORS(IR_AUTHORS);
		
		set.add(IR_HKEY);
		return news;
	}

}

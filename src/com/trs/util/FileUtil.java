package com.trs.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ahocorasick.trie.Trie;

import com.trs.task.NewFilterThreadTrie;


public class FileUtil {
	
	public static String[] FILTER_DATA= {"无效","待删除 ","暂不使用","欠息不欠本","关停","无本欠息","信息重复","村民委员会","生产队","电业局","公安局",
		"财政局","地税局","管理局","保障局","企业局","农业局","水产局","药监局","公路局 ","资源局","地质局","林水局","渔业局",
		"水利局","农牧局","交通局","重建局","司法局","水利局","供销社"};
	
	public static String[] FILTER_DATA2= {"股份有限公司","有限责任公司","有限公司"};
	
	public static Set<String> set=new HashSet<String>();
	
	
	//判断文件是否存在 存在则删除，不存在则穿件
	public static void save(String fileName) {
		 File file = new File(fileName);
        // 判断是否存在，不存在则创建
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else
        	file.delete();
	}
	
	
	
	/**
	 * 
	 * @param fileReadName  需要分割的文件
	 * @param newFilePath 分割后的文件存的路径
	 */
	public static void readFileByLines(String fileReadName,String newFilePath) {  
        File file = new File(fileReadName);  
        BufferedReader reader = null;  
        try {  
            System.out.println("begin");  
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            int line = 1;  
            while ((tempString = reader.readLine()) != null) {  
            	// 一个小文件的行数
                int no2 = line / 100000 + 1;  
                int no1 = no2 + 2;  
                String fileWriteName = newFilePath + "This is " + no1 + " and " + (no2 < 10 ? "0" + no2 : no2) + ".txt";  
                FileWriter writer = new FileWriter(fileWriteName, true);  
                writer.write(tempString);  
                writer.write("\n");  
                writer.close();  
                System.out.println(line);  
                line++;  
            }  
            reader.close();  
            System.out.println("finish!");  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
    }  
	
	
	//解析文件 初次解析 提取出每行的企业名称
	public static void analyDataByName(List<String> fileReadNames) {
		for (String fileReadName : fileReadNames) {
			File file = new File(fileReadName);  
	        BufferedReader reader = null;  
	      
	        try {  
	        	System.out.println("begin");  
	            reader = new BufferedReader(new FileReader(file));  
	            String tempString = null;  
	            int line = 1;  
	            while ((tempString = reader.readLine()) != null) {       
	            	
	                String[] splitName=tempString.split("\\|!"); 
	                //经验之谈中文字符都在第28个，但为了保险起见，对所有分割出的数据进行判断
	                //String name=splitName[27];
	                for (String string : splitName) {
	                	//判断字符中是否存在中文
	                	Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
						Matcher m = p.matcher(string);
						//判断分割是否为空，并判断是否是中文，判断长度是否大于3,若成功则加入hashset
						if(string!=null && !string.equals("") && m.find() &&string.length()>3 &&string.length()<30) {
							//对数据的特殊名进行处理	
							set.add(string);
						}						
					}
	                line++;  
	            }  
	            reader.close();  
	            System.out.println("总共"+line+"行,finish!");  
	            
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {   
	            if (reader != null) {  
	                try {  
	                    reader.close();  
	                } catch (IOException e1) {  
	                }  
	            }  
	        }  
		}
		
	}
	
	//提取ent_pri_inv
	public static void  analyzeDataBySplit(String filename) {
		File file = new File(filename);  
        BufferedReader reader = null;  
      
        try {  
        	System.out.println("begin");  
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            int line = 1;  
            while ((tempString = reader.readLine()) != null) {       
            	
                String[] splitName=tempString.split("	"); 
            
				set.add(splitName[0]);

                line++;  
            }  
            reader.close();  
            System.out.println("总共"+line+"行,finish!");  
            
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {   
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
	}
	
	
	
	//将hashSet的数据存入文件中
	public static void writeSetDataIntoFile(HashSet<String> set,String fileWriteName,String encoding ) {

		OutputStreamWriter  writer =null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(fileWriteName,true), encoding);
		
			for (String string :set) {
	            writer.write(string);  
	            writer.write("\n");  		
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("共"+set.size()+"数据");
		
		
	}
	
	
	//将文件内容转化为List String
	public static List<String> fileDataToList(String fileName,String encoding){
		List<String> list=new ArrayList<String>();
		File file=new File(fileName);
		BufferedReader reader=null;
		
		String tempString=null;
	
		try {
			FileInputStream in = new FileInputStream(file);
			
			reader=new BufferedReader(new InputStreamReader(in,encoding));
			while( (tempString = reader.readLine()) != null ) {
				if(tempString.equals("end"))
					break;
				list.add(tempString);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if( reader != null ) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		return list;
		
	}
	
	
	
	
	/**
	 * 将文件数据转化为list 对象数组
	 * @param fileName
	 * @param encoding
	 * @return
	 */		
	public static List<String[]> fileDataToStringArrayInList(String fileName,String encoding){
		List<String[]> list=new ArrayList<String[]>();
		File file=new File(fileName);
		BufferedReader reader=null;
		int count=0;
		String tempString=null;
	
		try {
			FileInputStream in = new FileInputStream(file);
	
			reader=new BufferedReader(new InputStreamReader(in,encoding));
			while( (tempString = reader.readLine()) != null) {
				String[] tempData=tempString.split("    ");
				
				list.add(tempData);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if( reader != null ) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		return list;
		
	}
	
	
	public static String readFileToString(String fileName,String encoding){
	
		File file=new File(fileName);
		BufferedReader reader=null;
		String tempString=null;
		String allString="";
		try {
			FileInputStream in = new FileInputStream(file);
	
			reader=new BufferedReader(new InputStreamReader(in,encoding));
			while( (tempString = reader.readLine()) != null) {
				allString+=tempString;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if( reader != null ) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		return allString;
		
	}
	
	
	
	/**
	 * 将字符串写入文件
	 * @param data
	 */
	public static void writeStringToFile(String data,String fileWriteName) {
		//将数据写入文件
		
		
		//FileUtil.save(fileWriteName);
		OutputStreamWriter  writer =null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(fileWriteName,true), "GBK");
			//System.out.println("写入data+"+fileWriteName);
            writer.write(data);  
            writer.write("\n");  		            
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 将list集合写入文件
	 * 
	 * @param datas
	 * @param filename
	 */
	public static void writeStringByBufferedWriter(List<String> datas,String filename) {
		 try {
	            File file = new File(filename);
	            if (!file.exists()) {
	                file.createNewFile();
	            }
	             // 一次写入的文件大小小于10M时， bufferedWriter并不能显著降低时间,而且此时BufferedOutputStream仍是占优的
	            FileWriter fWriter = new FileWriter(file,true);
	            BufferedWriter bufferedWriter = new BufferedWriter(fWriter);
	            long begin = System.currentTimeMillis();
	            for (String string : datas) {
	            	bufferedWriter.write(string+"\n");
				}
	            bufferedWriter.flush();
	            bufferedWriter.close();
	            System.out.println("BufferedWriter 执行耗时: " + (System.currentTimeMillis() - begin));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	
	public static HashSet<String> readFiletoSet(String filename,String encoding){
		HashSet<String> set=new HashSet<>();
	
		File file=new File(filename);
		BufferedReader reader=null;
		//int count=0;
		String tempString=null;
	
		try {
			FileInputStream in = new FileInputStream(file);
	
			reader=new BufferedReader(new InputStreamReader(in,encoding));
			while( (tempString = reader.readLine()) != null) {
				
				
				set.add(tempString);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if( reader != null ) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return set;
	}
	

	public static List<String> readFiletoList(String filename,String encoding){
		List<String> datas=new ArrayList<>();
	
		File file=new File(filename);
		BufferedReader reader=null;
		//int count=0;
		String tempString=null;
		
		try {
			FileInputStream in = new FileInputStream(file);
	
			reader=new BufferedReader(new InputStreamReader(in,encoding));
			while( (tempString = reader.readLine()) != null) {
				
				
				datas.add(tempString);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if( reader != null ) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("读取"+SystemConfig.fullnamePath+"完成");
		return datas;
	}
	
	public static void readFiletoTrie(String filename,String encoding){
		
	
		File file=new File(filename);
		BufferedReader reader=null;
		//int count=0;
		String tempString=null;
		
		try {
			FileInputStream in = new FileInputStream(file);
	
			reader=new BufferedReader(new InputStreamReader(in,encoding));
			while( (tempString = reader.readLine()) != null) {
				NewFilterThreadTrie.trie.addKeyword(tempString);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if( reader != null ) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("读取"+SystemConfig.fullnamePath+"完成");
	
	}
	
}

package com.cssiot.cssutil.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/****
 * 文件操作类
 * @author athena
 * 
 */
public class FileUtil {

	/**
	 * 删除指定目录下的所有文件
	 * @param folderPath 目录路径
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean delAllFile(String folderPath) {
		boolean flag = false;
		File file = new File(folderPath);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		if(null!=tempList){
			for (int i = 0; i < tempList.length; i++) {
				if (folderPath.endsWith(File.separator)) {
					temp = new File(folderPath + tempList[i]);
				} else {
					temp = new File(folderPath + File.separator + tempList[i]);
				}
				if (temp.isFile()) {
					temp.delete();
				}
				if (temp.isDirectory()) {
					delAllFile(folderPath + "/" + tempList[i]);// 先删除文件夹里面的文件
					delFolder(folderPath + "/" + tempList[i]);// 再删除空文件夹
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * 删除指定文件
	 * @param filePath 指定文件的路径
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean delFile(String filePath) {
		boolean flag = false;
		File file = new File(filePath);
		if (!file.exists()) {
			return flag;
		}
		flag = (new File(filePath)).delete();
		return flag;
	}

	/**
	 * 删除指定文件夹(包括文件夹下的所有文件)
	 * @param folderPath 指定文件夹路径
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
 	/**
 	 * 读取文本文件的内容
 	 * @param curfile   文本文件路径
 	 * @return 返回文件内容
 	 */
 	public static String readFile(String curfile)
 	{
			File f = new File(curfile);
		    try
		    {
	 		  if (!f.exists()) throw new Exception();
	 		  FileReader cf=new FileReader(curfile);
			  BufferedReader is = new BufferedReader(cf);
			  String filecontent = ""; 			  
			  String str = is.readLine();
			  while (str != null){
			  	filecontent += str;
		    	str = is.readLine();
		    	if (str != null)
		    		filecontent += "\n";
			  }
			  is.close();
			  cf.close();
			  return filecontent;
		    }
		    catch (Exception e)
		    {
		      System.err.println("不能读属性文件: "+curfile+" \n"+e.getMessage());
		      return "";
		    }
 		
 	}
	/**
	 * 取指定文件的扩展名
	 * @param filePathName  文件路径
	 * @return 扩展名
	 */
	public static String getFileExt(String filePathName)
	{
	    int pos = 0;
	    pos = filePathName.lastIndexOf('.');
	    if(pos != -1)
	        return filePathName.substring(pos + 1, filePathName.length());
	    else
	        return "";
		
	}
	
	/**
	 * 读取文件大小
	 * @param filename 指定文件路径
	 * @return 文件大小
	 */
	public static int getFileSize(String filename)
	{
		try
		{
			File fl = new File(filename);
			int length = (int)fl.length();
			return length;
	  	} 
	  	catch (Exception e)
	  	{
	  		return 0;
	  	}
		
	}
	
	/**
	 * 新建文件到指定目录
	 * @param content 内容
	 * @param fileName 文件路径名称
	 * @return flag true:成功 false:失败
	 * @throws Exception
	 * @author athena
	 * @time 2016-03-22
	 */
	public static boolean createFile(String content,String fileName)throws Exception{  
		boolean flag=false;
		FileOutputStream o=null;
		try{  
			File file = new File(fileName);
			if(!file.exists()){  
				file.createNewFile();
				if(null !=content && !"".equals(content)){
					o = new FileOutputStream(file);  
//				   	o.write(content.getBytes("GBK"));
					o.write(content.getBytes("UTF-8"));
				   	o.close();
				}
				flag=true;  
			}  
		}catch(Exception e){  
			e.printStackTrace();  
		}  
	  return flag;
	}
	
	/**
	 * 复制内容到指定文件
	 * @param content 内容 
	 * @param fileName 文件路径名称
	 * @return flag true:成功 false:失败
	 * @throws Exception
	 * @author athena
	 * @time 2016-03-22
	 */
	public static boolean contentToFile(String content,String fileName)throws Exception{  
		boolean flag=false;
	 	try {  
	 		File file = new File(fileName);
	 		if (file.exists()) {
	 			boolean delflag=delFile(fileName);
	 			if(delflag){
	            	flag=createFile(content,fileName);
	          	}
	       	} else {
	       		// 判断文件夹是否存在，不存在就根据上面的路径创建文件夹
	       		if (!file.getParentFile().exists()) {
	       			file.getParentFile().mkdirs();
				}
	       		flag=createFile(content,fileName);
	       	}  
	 	} catch (Exception e) {  
	    	e.printStackTrace();  
	 	}
		return flag;
	}
	
	/**
	 * 拷贝文件到指定目录
	 * @param srcPath 源文件路径
	 * @param destPath 目标文件路径
	 * @return true:拷贝成功 false:拷贝失败
	 */
	public static boolean copyFile(String srcPath,String destPath){
		try
		{
			File fl = new File(srcPath);
			int length = (int)fl.length();
		  	FileInputStream is = new FileInputStream(srcPath);
		  	FileOutputStream os = new FileOutputStream(destPath);
			byte[] b = new byte[length];
		  	is.read(b);
		  	os.write(b);
		  	is.close();
		  	os.close();
		  	return true;
	  	} 
	  	catch (Exception e)
	  	{
	  		return false;
	  	}
	}
	
	/**
	  * 复制整个文件夹的内容
	  *
	  * @param oldPath 准备拷贝的目录
	  * @param newPath 指定绝对路径的新目录
	  * @return true:复制成功 false:复制失败
	  */
	 public static boolean copyFolder(String oldPath, String newPath) {
		  File f1 = new File(newPath);
		  f1.mkdirs();
		  File[] file = new File(oldPath).listFiles();
		  FileInputStream in = null;
		  FileOutputStream out = null;
		  if(null!=file){
			  for(int i=0; i<file.length; i++){
			   if(file[i].isFile()){
			    try {
			     in = new FileInputStream(file[i]);
			     out = new FileOutputStream(newPath+"/"+file[i].getName());
			     byte[] b = new byte[1024*10];
			     int len = 0;
			     while((len=in.read(b))!=-1){
			      out.write(b);
			     }
			    } catch (FileNotFoundException e) {
			     e.printStackTrace();
			     return false;
			    } catch (IOException e) {
			     e.printStackTrace();
			     return false;
			    } finally{
			     try {
			      out.flush();
			      out.close();
			      in.close();
			     } catch (IOException e) {
			      e.printStackTrace();
			      return false;
			     }
			    }
			   }
			   if(file[i].isDirectory()){
			    copyFolder(oldPath+"/"+file[i].getName(),newPath+"/"+file[i].getName());
			   }
			  }
		  }
		  return true;
	 }

	 /**
	  * 移动文件
	  *
	  * @param oldPath 准备拷贝的目录及文件名
	  * @param newPath 指定新的文件路径及文件名
	  * @return true:移动成功 false:移动失败
	  */
	 public static boolean moveFile(String oldPath, String newPath) {
		  FileInputStream in = null;
		  FileOutputStream out = null;
		  File file = null;
		  try {
		   file = new File(oldPath);
		   in = new FileInputStream(oldPath);
		   out = new FileOutputStream(newPath);
		   int b = 0;
		   while ((b = in.read()) != -1) {
		    out.write(b);
		   }
		   out.close();
		   in.close();
		   file.delete();
		   ////System.out.println("文件移动成功");
		  } catch (FileNotFoundException e) {
		   e.printStackTrace();
		   return false;
		  } catch (IOException e) {
		   e.printStackTrace();
		   return false;
		  } finally {
		   try {
			   if(null!=out){
				   out.close();
			   }
			   if(null!=in){
				   in.close();
			   }
		   } catch (IOException e) {
		    e.printStackTrace();
		    return false;
		   }
		  }
		  return true;
	 }
	 
	 /**
	  * 把file转换成ISO8859-1编码格式
	  * 
	  * @param file
	  * @return
	  */
	 public static String getFileName(String file) {
         String fileName= "";
             try {
                      fileName = new String(file.getBytes(),"ISO8859-1"); 
                  } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                       }
             return fileName;
        }
	 
	 /**
	  * 把file转换成UTF-8编码格式
	  * 
	  * @param file
	  * @return
	  */
	 public static String getFileNameu(String file) {
         String fileName= "";
             try {
                      fileName = new String(file.getBytes(),"UTF-8"); 
                  } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                       }
             return fileName;
        }
	 
	 /**
	  * 把file转换成GB2312编码格式
	  * 
	  * @param file
	  * @return
	  */
	 public static String getFileNameg(String file) {
         String fileName= "";
             try {
                      fileName = new String(file.getBytes(),"GB2312"); 
                  } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                       }
             return fileName;
        }
	    
    /** 
     * 压缩文件
     * @param source 源路径,可以是文件,也可以目录
     * @param destinct  目标路径,压缩文件名
     * @throws IOException,FileNotFoundException
     */ 
    public static void zip(String source, String destinct) throws IOException, FileNotFoundException  {
        File file = new File(source);
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream((new FileOutputStream(destinct))));
        
        // 压缩目录和文件
        if(file.isDirectory()) {
            compressFile(file,zos,"");
        } else {
            compressFile(file,zos,file.getName());
        }
        
        zos.close();
    }
	    
    /** 
     * 递归压缩文件
     * @param file 可以是文件,也可以目录
     * @param zos  压缩文件
     * @param base 目录
     * @throws IOException,FileNotFoundException
     */ 
    public static void compressFile(File file, ZipOutputStream zos, String base) throws IOException, FileNotFoundException  {
        if(file.isDirectory()) {
            File[] innerFile = file.listFiles();
            if(innerFile != null) {
                zos.putNextEntry(new ZipEntry(base+"/"));
                base = base.length() == 0 ?"":base+"/";
                for(int i = 0; i<innerFile.length; i++) {
                    compressFile(innerFile[i],zos,base+innerFile[i].getName());
                }
            }
            
        } else {
            byte[] buf = new byte[2048];
            int len;
            ZipEntry entry = new ZipEntry(base);
            FileInputStream fin = new FileInputStream(file);
            BufferedInputStream in = new BufferedInputStream(fin);
            zos.putNextEntry(entry);
            while(( len = in.read(buf)) >=0) {
                zos.write(buf);
            }
            in.close();
            zos.closeEntry();
            
        }
    }

}

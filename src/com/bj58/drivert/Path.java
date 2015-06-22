package com.bj58.drivert;
import java.io.File;
public class Path {
public static String  getCurrentPath(){
		
		String sPath = System.getProperty("serviceframe.config.path");
		System.out.println("serviceframe.config.path:"+sPath);
		
		if(sPath == null || sPath.equalsIgnoreCase("")){
			Class<?>caller = getCaller();
			if(caller == null){
				caller=Path.class;
			}
			sPath = getCurrentPath(caller);
		}
		System.out.println("utility path getCurrentPath:"+sPath);
		return sPath;
	}
	public static Class<?> getCaller(){//Class<?>是什么意思
		StackTraceElement stack[]=new Throwable().getStackTrace();
		System.out.println("StackTraceElement length:"+stack.length);
		if(stack.length <3){  //为什么是3
			return Path.class;
		}
		String classname="";
		for(int i=0;i<3;i++){
			classname = stack[i].getClassName();
			System.out.println("getcaller classname"+i+":"+classname);
		}
		try{
			return Class.forName(classname);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return null;
	}
	public static  String getCurrentPath (Class<?> cls){
		String sPath = cls.getProtectionDomain().getCodeSource().getLocation().getPath();
		sPath =sPath.replaceFirst("file/", "");//把字符串中的"file/"替换为""第一次出现的 
		sPath = sPath.replaceAll("!/", "");//所有的都替换为 ""
		if(sPath.lastIndexOf(File.separator)>=0){//报告指定  字符在此实例中的最后一个匹配项的索引位置
			sPath.substring(0,sPath.lastIndexOf(File.separator)); //从0 到 sPath.lastIndexOf(File.separator)位置截取字符串
		}
		if(sPath.substring(0,1).equalsIgnoreCase("/")){
			String osname = System.getProperty("os.name").toLowerCase();
			
			if(osname.indexOf("window")>0){
				sPath= sPath.substring(1);
			}
		}
		
		return sPath;
	}
}

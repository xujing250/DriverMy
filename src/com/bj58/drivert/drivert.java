package com.bj58.drivert;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;

import java.util.List;
import com.bj58.opt.cpc.as.contract.IJingZhunService;
import com.bj58.opt.cpc.as.entity.JingZhunEntity;

import com.bj58.spat.scf.client.SCFInit;
import com.bj58.spat.scf.client.proxy.builder.ProxyFactory;
public class drivert {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub//加载配置文件
	   SCFInit.init(Path.getCurrentPath()+ "com\\bj58\\drivert\\scf.config");
	   
		final String url="tcp://drivert/JingZhunService";
		
		IJingZhunService jinzhunservice = ProxyFactory.create(IJingZhunService.class,url);
		
		String condition = readFileByLines(Path.getCurrentPath()+"com/bj58/drivert/param.txt");
        
		List<JingZhunEntity> list= jinzhunservice.getJingZhunList(condition);
		
		for(JingZhunEntity jingzhun :list){
			System.out.println("ID is:"+jingzhun.getEntityId()+"contact is"+list.toString());
			writeDataToFile(list.toString());
		}
	}
	
	public static String readFileByLines(String filename){
		File file = new File(filename);
		BufferedReader  reader=null;
		StringBuilder sb = new StringBuilder();
		try{
			reader = new BufferedReader(new FileReader(file));
			String TempString= null;
			while((TempString = reader.readLine() )!=null){
				sb.append(TempString);
			}
			reader.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}finally{
			if(reader!=null){
				try{
					reader.close();
				}catch(IOException e1){	
				}
				
			}
		}
		
		return sb.toString();
	}
	public static void writeDataToFile(String sData){
		
		File file = new File(Path.getCurrentPath()+"com/bj58/drivert/ReceiveData.txt");
		BufferedWriter  writer=null;
		try{
			if(file.exists()){
				file.delete();
			}else{
				file.createNewFile();
			}
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(sData);
			writer.newLine();
            writer.write("\n");		

			
		}
		catch(IOException e){
			e.printStackTrace();
		}finally{
			if(writer!=null){
				try{
					writer.close();
				}catch(IOException e1){	
				}
				
			}
		}
	}
}

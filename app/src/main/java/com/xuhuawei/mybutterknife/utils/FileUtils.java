package com.xuhuawei.mybutterknife.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class FileUtils {

	public static void output(String info){  
        if(info == null){  
            return;  
        }  
        try {  
            BufferedWriter writer = new BufferedWriter(new FileWriter("d://jason.txt",true));  
            writer.newLine();  
            writer.append(info);  
            writer.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

}

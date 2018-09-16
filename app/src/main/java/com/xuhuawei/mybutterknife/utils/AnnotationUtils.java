package com.xuhuawei.mybutterknife.utils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element; 

public class AnnotationUtils {

	 /** 
     * 获取包名 
     * @param env 
     * @param element 
     * @return 
     */  
    public static String getPackageName(ProcessingEnvironment env,Element element){  
        return env.getElementUtils().getPackageOf(element).getQualifiedName().toString();  
    }

}

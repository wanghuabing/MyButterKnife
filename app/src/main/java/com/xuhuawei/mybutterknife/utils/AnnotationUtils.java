package com.xuhuawei.mybutterknife.utils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element; 

public class AnnotationUtils {

	 /** 
     * ��ȡ���� 
     * @param env 
     * @param element 
     * @return 
     */  
    public static String getPackageName(ProcessingEnvironment env,Element element){  
        return env.getElementUtils().getPackageOf(element).getQualifiedName().toString();  
    }

}

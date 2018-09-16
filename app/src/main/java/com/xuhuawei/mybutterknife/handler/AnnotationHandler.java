package com.xuhuawei.mybutterknife.handler;

import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.VariableElement;
/** 
 * 注解处理器 
 * @date 2016年3月21日 
 * @version 1.0 
 */  
public interface AnnotationHandler {
	/** 
     * 关联“处理环境” 
     * @param env 
     */  
    void attachProcessingEnvironment(ProcessingEnvironment env);  
      
    /** 
     * 处理注解 
     * @param env “周边环境” 
     * @return Map<key 注解的宿主类名, value 宿主类中使用了我们注解的属性集合> 
     */  
    Map<String, List<VariableElement>> handleAnnotation(RoundEnvironment env);
}

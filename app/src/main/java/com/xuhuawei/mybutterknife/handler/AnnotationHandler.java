package com.xuhuawei.mybutterknife.handler;

import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.VariableElement;
/** 
 * ע�⴦���� 
 * @date 2016��3��21�� 
 * @version 1.0 
 */  
public interface AnnotationHandler {
	/** 
     * �������������� 
     * @param env 
     */  
    void attachProcessingEnvironment(ProcessingEnvironment env);  
      
    /** 
     * ����ע�� 
     * @param env ���ܱ߻����� 
     * @return Map<key ע�����������, value ��������ʹ��������ע������Լ���> 
     */  
    Map<String, List<VariableElement>> handleAnnotation(RoundEnvironment env);
}

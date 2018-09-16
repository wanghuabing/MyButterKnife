package com.xuhuawei.mybutterknife.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.element.Element; 

import com.xuhuawei.mybutterknife.annotation.ViewInjector;
import com.xuhuawei.mybutterknife.utils.AnnotationUtils;
import com.xuhuawei.mybutterknife.utils.FileUtils;

public class ViewInjectHandler implements AnnotationHandler {

	private ProcessingEnvironment processingEnv;

	@Override
	public void attachProcessingEnvironment(ProcessingEnvironment env) {
		this.processingEnv = env;
	}

	@Override
	public Map<String, List<VariableElement>> handleAnnotation(
			RoundEnvironment env) {
		Map<String, List<VariableElement>> annotationMap = new HashMap<String, List<VariableElement>>();  
        
        //��ȡ����ʹ����ViewInjectorע���Ԫ�أ����ԣ�  
        Set<? extends Element> elementSet = env.getElementsAnnotatedWith(ViewInjector.class);  
        //������ĸ�����������һ����  
        for (Element element : elementSet) {  
            //VariableElement �������ԡ�ö�ٵȵ�  
            VariableElement varElement = (VariableElement)element;  
            //��ȡ������������������  
            String className = getParentClassName(varElement);  
              
            //key->value  
            List<VariableElement> cacheElements = annotationMap.get(className);  
            if(cacheElements == null){  
                cacheElements = new ArrayList<VariableElement>();  
                annotationMap.put(className, cacheElements);  
            }  
            cacheElements.add(varElement);  
        }  
        //-----------------for test  
        FileUtils.output(annotationMap.toString());  
          
        return annotationMap;  
	}
	/** 
     * ��ȡ������������������ 
     * @param varElement 
     * @return 
     */  
    private String getParentClassName(VariableElement varElement) {  
        TypeElement typeElement = (TypeElement)varElement.getEnclosingElement();  
        String packageName =  AnnotationUtils.getPackageName(processingEnv, typeElement);  
        //��������  
        return packageName + "." + typeElement.getSimpleName().toString();  
    }  
}

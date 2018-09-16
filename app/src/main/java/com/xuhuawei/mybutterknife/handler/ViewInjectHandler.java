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
        
        //获取所有使用了ViewInjector注解的元素（属性）  
        Set<? extends Element> elementSet = env.getElementsAnnotatedWith(ViewInjector.class);  
        //搞清楚哪个属性属于哪一个类  
        for (Element element : elementSet) {  
            //VariableElement 代表属性、枚举等等  
            VariableElement varElement = (VariableElement)element;  
            //获取属性宿主类的完成类名  
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
     * 获取属性宿主类的完成类名 
     * @param varElement 
     * @return 
     */  
    private String getParentClassName(VariableElement varElement) {  
        TypeElement typeElement = (TypeElement)varElement.getEnclosingElement();  
        String packageName =  AnnotationUtils.getPackageName(processingEnv, typeElement);  
        //完整类名  
        return packageName + "." + typeElement.getSimpleName().toString();  
    }  
}

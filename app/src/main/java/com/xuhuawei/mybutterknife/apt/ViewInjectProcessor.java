package com.xuhuawei.mybutterknife.apt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import com.xuhuawei.mybutterknife.handler.AnnotationHandler;
import com.xuhuawei.mybutterknife.handler.ViewInjectHandler;
import com.xuhuawei.mybutterknife.writer.AbstractWriter;
import com.xuhuawei.mybutterknife.writer.DefaultJavaFileWriter;

/**
 * 通过注解指定APT工具的访问规则
 * @author Administrator
 *
 *APT运行的时候 会调用ViewInjectProcessor的 getSupportedAnnotationTypes()方法
 *而这个方法会去扫描SupportedAnnotationTypes中的字符中的注解
 *我们的代码特性 支持需要jdk1.6以上 所以 使用了SupportedSourceVersiong
 *
 */
@SupportedAnnotationTypes("com.xuhuawei.mybutterknife.annotation.ViewInjector")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class ViewInjectProcessor extends AbstractProcessor {
	private List<AnnotationHandler> mHandlers = new ArrayList<AnnotationHandler>();  
	private AbstractWriter mWriter;  
	private Map<String, List<VariableElement>> map = new HashMap<String, List<VariableElement>>();  
      
    //初始化工作  
    @Override  
    public synchronized void init(ProcessingEnvironment processingEnv) {  
        super.init(processingEnv);  
        //初始化注解处理器  
        registerHandler(new ViewInjectHandler());  
          
        //初始化辅助类生成器  
        mWriter = new DefaultJavaFileWriter(processingEnv);  
    }  
      
    /** 
     * 注册处理器 
     * @param handler 
     */  
    protected void registerHandler(AnnotationHandler handler){  
        mHandlers.add(handler);  
    }  
	//符合规则的类信息，都在这里处理
	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		//roundEnv 一团乱麻  ->整理出Map<String,List<VariableElement>> 其中Key是类名 ，values->List<VariableElement>属性集合
//		ViewInjectHandler handler=new ViewInjectHandler();
//		handler.attachProcessingEnvironment(processingEnv);
//		
		 //1.拿到注解信息（暂时只有处理注入视图的注解，将来还有可能要处理注入布局、事件等等）  
        for (AnnotationHandler handler : mHandlers) {  
            //关联环境  
            handler.attachProcessingEnvironment(processingEnv);  
            //处理注解  
            map.putAll(handler.handleAnnotation(roundEnv));  
        }  
          
        //2.生成Java文件  
        mWriter.generate(map);  
		
		return false;
	}

}

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
 * ͨ��ע��ָ��APT���ߵķ��ʹ���
 * @author Administrator
 *
 *APT���е�ʱ�� �����ViewInjectProcessor�� getSupportedAnnotationTypes()����
 *�����������ȥɨ��SupportedAnnotationTypes�е��ַ��е�ע��
 *���ǵĴ������� ֧����Ҫjdk1.6���� ���� ʹ����SupportedSourceVersiong
 *
 */
@SupportedAnnotationTypes("com.xuhuawei.mybutterknife.annotation.ViewInjector")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class ViewInjectProcessor extends AbstractProcessor {
	private List<AnnotationHandler> mHandlers = new ArrayList<AnnotationHandler>();  
	private AbstractWriter mWriter;  
	private Map<String, List<VariableElement>> map = new HashMap<String, List<VariableElement>>();  
      
    //��ʼ������  
    @Override  
    public synchronized void init(ProcessingEnvironment processingEnv) {  
        super.init(processingEnv);  
        //��ʼ��ע�⴦����  
        registerHandler(new ViewInjectHandler());  
          
        //��ʼ��������������  
        mWriter = new DefaultJavaFileWriter(processingEnv);  
    }  
      
    /** 
     * ע�ᴦ���� 
     * @param handler 
     */  
    protected void registerHandler(AnnotationHandler handler){  
        mHandlers.add(handler);  
    }  
	//���Ϲ��������Ϣ���������ﴦ��
	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		//roundEnv һ������  ->�����Map<String,List<VariableElement>> ����Key������ ��values->List<VariableElement>���Լ���
//		ViewInjectHandler handler=new ViewInjectHandler();
//		handler.attachProcessingEnvironment(processingEnv);
//		
		 //1.�õ�ע����Ϣ����ʱֻ�д���ע����ͼ��ע�⣬�������п���Ҫ����ע�벼�֡��¼��ȵȣ�  
        for (AnnotationHandler handler : mHandlers) {  
            //��������  
            handler.attachProcessingEnvironment(processingEnv);  
            //����ע��  
            map.putAll(handler.handleAnnotation(roundEnv));  
        }  
          
        //2.����Java�ļ�  
        mWriter.generate(map);  
		
		return false;
	}

}

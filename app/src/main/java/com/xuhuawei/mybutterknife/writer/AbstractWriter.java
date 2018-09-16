package com.xuhuawei.mybutterknife.writer;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;

import com.xuhuawei.mybutterknife.MyButterKnife;
import com.xuhuawei.mybutterknife.utils.AnnotationUtils;
import com.xuhuawei.mybutterknife.utils.FileUtils;
import com.xuhuawei.mybutterknife.utils.IOUtil;

/** 
 * ��������������ģ�巽�� 
 * @date 2016��3��21�� 
 * @version 1.0 
 */  
public abstract class AbstractWriter implements AdapterWriter{
	protected ProcessingEnvironment mProcessingEnv;  
    //���ڴ���Դ�ļ�  
    protected Filer mFiler;  
      
    public AbstractWriter(ProcessingEnvironment mProcessingEnv) {  
        super();  
        this.mProcessingEnv = mProcessingEnv;  
        this.mFiler = mProcessingEnv.getFiler();  
        FileUtils.output("AbstractWriter ��ʼ��");  
    }  
  
    /** 
     * ģ�巽�� 
     */  
    @Override  
    public void generate(Map<String, List<VariableElement>> map) {  
        Iterator<Entry<String, List<VariableElement>>> iterator = map.entrySet().iterator();  
        FileUtils.output("generate:"+map);  
        while(iterator.hasNext()){  
            Entry<String, List<VariableElement>> entry = iterator.next();  
            //���Լ���  
            List<VariableElement> cacheElements = entry.getValue();  
            if(cacheElements == null || cacheElements.size() == 0){  
                continue;  
            }  
            //����JavaԴ�ļ�  
            //�ü����еĵ�0������Ԫ�س���������һ��InjectInfo  
            InjectInfo info = createInjectInfo(cacheElements.get(0));  
            Writer writer = null;  
            try {  
                JavaFileObject javaFileObject = mFiler.createSourceFile(info.getClassFullName());  
                //ͨ��Writer��дJava����  
                writer = javaFileObject.openWriter();  
                //ͷ��  
                FileUtils.output("generateImport");  
                generateImport(writer, info);  
                //���Ը�ֵ����  
                for (VariableElement variableElement : cacheElements) {  
                    writeField(writer, variableElement, info);  
                }  
                FileUtils.output("writeField");  
                //β��  
                writeEnd(writer);  
                FileUtils.output("writeEnd");  
            } catch (IOException e) {  
                e.printStackTrace();  
            } finally{  
                IOUtil.closeQuietly(writer);  
            }  
        }  
    }  
      
    private InjectInfo createInjectInfo(VariableElement element){  
        //��ȡ��������������-����Ԫ�أ���תΪTypeElement  
        TypeElement typeElement = (TypeElement)element.getEnclosingElement();  
        String packageName = AnnotationUtils.getPackageName(mProcessingEnv, element);  
        String className = typeElement.getSimpleName().toString();  
        return new InjectInfo(packageName, className);  
    }  
      
    /** 
     * ����ͷ�� 
     * @param writer 
     * @param info 
     */  
    protected abstract void generateImport(Writer writer, InjectInfo info)throws IOException;  
      
    /** 
     * ����Ϊ���Ը�ֵ�Ĵ��� 
     * @param writer 
     * @param element 
     * @param info 
     */  
    protected abstract void writeField(Writer writer, VariableElement element, InjectInfo info)throws IOException;  
      
    /** 
     * ������β���� 
     * @param writer 
     */  
    protected abstract void writeEnd(Writer writer)throws IOException;  
      
    /** 
     * ע����Ϣ 
     * @author Jason 
     * QQ: 1476949583 
     * @date 2016��3��23�� 
     * @version 1.0 
     */  
    class InjectInfo{  
        //������İ���  
        public String packageName;  
        //�����������  
        public String className;  
        //Ҫ�����ĸ����������  
        public String newClassName;  
          
        public InjectInfo(String packageName, String className) {  
            super();  
            this.packageName = packageName;  
            this.className = className;  
            this.newClassName = className + MyButterKnife.SUFFIX;  
        }  
          
        /** 
         * ��ȡ��������������� 
         * @return 
         */  
        public String getClassFullName(){  
            return this.packageName + "." + this.newClassName;  
        }  
          
    }  

}

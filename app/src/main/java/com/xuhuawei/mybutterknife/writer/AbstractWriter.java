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
 * 辅助类生成器的模板方法 
 * @date 2016年3月21日 
 * @version 1.0 
 */  
public abstract class AbstractWriter implements AdapterWriter{
	protected ProcessingEnvironment mProcessingEnv;  
    //用于创建源文件  
    protected Filer mFiler;  
      
    public AbstractWriter(ProcessingEnvironment mProcessingEnv) {  
        super();  
        this.mProcessingEnv = mProcessingEnv;  
        this.mFiler = mProcessingEnv.getFiler();  
        FileUtils.output("AbstractWriter 初始化");  
    }  
  
    /** 
     * 模板方法 
     */  
    @Override  
    public void generate(Map<String, List<VariableElement>> map) {  
        Iterator<Entry<String, List<VariableElement>>> iterator = map.entrySet().iterator();  
        FileUtils.output("generate:"+map);  
        while(iterator.hasNext()){  
            Entry<String, List<VariableElement>> entry = iterator.next();  
            //属性集合  
            List<VariableElement> cacheElements = entry.getValue();  
            if(cacheElements == null || cacheElements.size() == 0){  
                continue;  
            }  
            //创建Java源文件  
            //拿集合中的第0个属性元素出来，构建一个InjectInfo  
            InjectInfo info = createInjectInfo(cacheElements.get(0));  
            Writer writer = null;  
            try {  
                JavaFileObject javaFileObject = mFiler.createSourceFile(info.getClassFullName());  
                //通过Writer编写Java代码  
                writer = javaFileObject.openWriter();  
                //头部  
                FileUtils.output("generateImport");  
                generateImport(writer, info);  
                //属性赋值部分  
                for (VariableElement variableElement : cacheElements) {  
                    writeField(writer, variableElement, info);  
                }  
                FileUtils.output("writeField");  
                //尾部  
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
        //获取变量的宿主（类-类型元素），转为TypeElement  
        TypeElement typeElement = (TypeElement)element.getEnclosingElement();  
        String packageName = AnnotationUtils.getPackageName(mProcessingEnv, element);  
        String className = typeElement.getSimpleName().toString();  
        return new InjectInfo(packageName, className);  
    }  
      
    /** 
     * 产生头部 
     * @param writer 
     * @param info 
     */  
    protected abstract void generateImport(Writer writer, InjectInfo info)throws IOException;  
      
    /** 
     * 产生为属性赋值的代码 
     * @param writer 
     * @param element 
     * @param info 
     */  
    protected abstract void writeField(Writer writer, VariableElement element, InjectInfo info)throws IOException;  
      
    /** 
     * 产生结尾部分 
     * @param writer 
     */  
    protected abstract void writeEnd(Writer writer)throws IOException;  
      
    /** 
     * 注入信息 
     * @author Jason 
     * QQ: 1476949583 
     * @date 2016年3月23日 
     * @version 1.0 
     */  
    class InjectInfo{  
        //宿主类的包名  
        public String packageName;  
        //宿主类的类名  
        public String className;  
        //要创建的辅助类的类名  
        public String newClassName;  
          
        public InjectInfo(String packageName, String className) {  
            super();  
            this.packageName = packageName;  
            this.className = className;  
            this.newClassName = className + MyButterKnife.SUFFIX;  
        }  
          
        /** 
         * 获取辅助类的完整类名 
         * @return 
         */  
        public String getClassFullName(){  
            return this.packageName + "." + this.newClassName;  
        }  
          
    }  

}

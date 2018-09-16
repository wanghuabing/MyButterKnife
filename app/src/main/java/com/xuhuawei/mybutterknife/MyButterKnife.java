package com.xuhuawei.mybutterknife;

import java.util.HashMap;
import java.util.Map;

import com.xuhuawei.mybutterknife.adapter.InjectAdapter;
import com.xuhuawei.mybutterknife.adapter.NullAdapter;

import android.app.Activity;

public class MyButterKnife {
	//���棬�������븨�������Ĺ�ϵ  
    static Map<Class<?>, InjectAdapter<?>> mInjectCache = new HashMap<Class<?>, InjectAdapter<?>>();  
    /** 
     * ���������� 
     */  
    public static String SUFFIX = "$InjectAdapter";  
      
    public static void inject(Activity target){  
        //��ȡActivity�е��ڲ��ࣨ�����ࣩ  
        InjectAdapter<Activity> adapter = getViewAdapter(target.getClass());  
        adapter.injects(target);  
    }  
  
    /** 
     * ��ȡһ��ָ�����InjectAdapter�ڲ��� 
     * @param clazz 
     * @return 
     */  
    private static <T> InjectAdapter<T> getViewAdapter(Class<?> clazz) {  
        //�ȴӻ����ȡ  
        InjectAdapter<T> adapter = (InjectAdapter<T>)mInjectCache.get(clazz);  
        if(adapter != null){  
            return adapter;  
        }  
        //ʹ�÷��䣬ͨ���ڲ����Classʵ���������ڲ���Ķ���  
        //com.jason.jasonknife.demo.MainActivity$InjectAdapter  
        String adapterClassName = clazz.getName() + SUFFIX;  
        try {  
            //1.ͨ�����ڲ�����������������س�һ��class  
            Class<?> adapterClass = Class.forName(adapterClassName);  
            //2.ͨ��Classʵ��������  
            adapter = (InjectAdapter<T>)adapterClass.newInstance();  
            //���뻺��  
            mInjectCache.put(clazz, adapter);  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } catch (InstantiationException e) {  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            e.printStackTrace();  
        }  
        //Null Object Pattern �ն������ģʽ  
        return (InjectAdapter<T>) (adapter == null ? new NullAdapter() : adapter);  
    }  
}

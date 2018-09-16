package com.xuhuawei.mybutterknife;

import java.util.HashMap;
import java.util.Map;

import com.xuhuawei.mybutterknife.adapter.InjectAdapter;
import com.xuhuawei.mybutterknife.adapter.NullAdapter;

import android.app.Activity;

public class MyButterKnife {
	//缓存，宿主类与辅助类对象的关系  
    static Map<Class<?>, InjectAdapter<?>> mInjectCache = new HashMap<Class<?>, InjectAdapter<?>>();  
    /** 
     * 辅助类类名 
     */  
    public static String SUFFIX = "$InjectAdapter";  
      
    public static void inject(Activity target){  
        //获取Activity中的内部类（辅助类）  
        InjectAdapter<Activity> adapter = getViewAdapter(target.getClass());  
        adapter.injects(target);  
    }  
  
    /** 
     * 获取一个指定类的InjectAdapter内部类 
     * @param clazz 
     * @return 
     */  
    private static <T> InjectAdapter<T> getViewAdapter(Class<?> clazz) {  
        //先从缓存获取  
        InjectAdapter<T> adapter = (InjectAdapter<T>)mInjectCache.get(clazz);  
        if(adapter != null){  
            return adapter;  
        }  
        //使用反射，通过内部类的Class实例化出该内部类的对象  
        //com.jason.jasonknife.demo.MainActivity$InjectAdapter  
        String adapterClassName = clazz.getName() + SUFFIX;  
        try {  
            //1.通过该内部类的完整类名，加载出一个class  
            Class<?> adapterClass = Class.forName(adapterClassName);  
            //2.通过Class实例化对象  
            adapter = (InjectAdapter<T>)adapterClass.newInstance();  
            //放入缓存  
            mInjectCache.put(clazz, adapter);  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } catch (InstantiationException e) {  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            e.printStackTrace();  
        }  
        //Null Object Pattern 空对象设计模式  
        return (InjectAdapter<T>) (adapter == null ? new NullAdapter() : adapter);  
    }  
}

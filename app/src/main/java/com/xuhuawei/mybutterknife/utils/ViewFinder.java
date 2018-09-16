package com.xuhuawei.mybutterknife.utils;

import android.app.Activity;
import android.view.View;

public class ViewFinder {

	public static <T extends View> T findViewById(Activity activity, int id){  
        return (T)activity.findViewById(id);  
    } 

}

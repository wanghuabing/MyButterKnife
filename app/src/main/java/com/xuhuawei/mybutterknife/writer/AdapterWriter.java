package com.xuhuawei.mybutterknife.writer;

import java.util.List;
import java.util.Map;

import javax.lang.model.element.VariableElement;

public interface AdapterWriter {
	/** 
     * Éú³É¸¨ÖúÀà 
     * @param map 
     */  
    void generate(Map<String, List<VariableElement>> map); 
}

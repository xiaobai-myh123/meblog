package com.myh.utils;

import java.util.ArrayList;
import java.util.List;

/*
**2020年6月13日--上午9:53:48
**@version:
**莫耀华:
**@Description:
*/
public class MyUtils {
	
	public static List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
}

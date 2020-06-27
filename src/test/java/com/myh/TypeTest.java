package com.myh;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myh.pojo.Type;
import com.myh.service.TypeService;
import com.myh.service.impl.TypeServiceImpl;
import com.myh.vo.VoType;

/*
**2020年6月10日--上午10:32:23
**@version:
**莫耀华:
**@Description:
*/
@SpringBootTest
public class TypeTest {
	
	@Autowired
	private TypeServiceImpl typeServiceImpl;
	
	@Test
	void contextLoads() {
//		System.out.println(typeServiceImpl.selectTypeList());
		
//		Type selectTypeByTypeName = typeServiceImpl.selectTypeByTypeName("前端");
//		System.out.println(selectTypeByTypeName);
		
//		Type type = new Type();
//		type.setName("大数据");
//		int saveType = typeServiceImpl.saveType(type);
//		System.out.println(saveType);
		
//		List<Type> selectTypeListLimit = typeServiceImpl.selectTypeListLimit(1, 2);
//		System.out.println(selectTypeListLimit.toString());
//		System.out.println(typeServiceImpl.countType());
		
//		System.out.println(typeServiceImpl.selectVoTypeListLimit(5L));
//		List<Type> list = typeServiceImpl.selectTypeList();
//		System.out.println(list);
		List<VoType> blog = typeServiceImpl.countTypeByBlog();
		System.out.println(blog);
	}
}

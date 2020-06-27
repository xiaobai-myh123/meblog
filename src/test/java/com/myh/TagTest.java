package com.myh;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myh.service.impl.TagServiceImpl;
import com.myh.vo.VoTag;

/*
**2020年6月10日--下午6:17:30
**@version:
**莫耀华:
**@Description:
*/
@SpringBootTest
public class TagTest {

	@Autowired
	private TagServiceImpl tagServiceImpl;

	@Test
	void contextLoads1() {
		System.out.println(tagServiceImpl);
		System.out.println(tagServiceImpl.addTagOne("html"));
	}

	@Test
	void contextLoads2() {
		System.out.println(tagServiceImpl.updateTagOne(1, "java2"));
	}

	@Test
	void contextLoads3() {
		System.out.println(tagServiceImpl.selectTagList());
	}

	@Test
	void contextLoads4() {
		System.out.println(tagServiceImpl.selectTagByTagName("java"));
	}

	@Test
	void contextLoads5() {
		System.out.println(tagServiceImpl.listByIdsTag("1,2,3"));
	}

	@Test
	void selectBlogListLimit() {
		System.out.println(tagServiceImpl.selectTagListLimit(0, 2));
	}
	@Test
	void test6() {
		ArrayList<Long> list = new ArrayList<Long>();
		list.add(1L);
		list.add(2L);
//		System.out.println(tagServiceImpl.insertByIdsTag(1,list));
	}
	
	@Test
	void test7() {
//		tagServiceImpl.selectVoTagListLimit(5L);
		List<VoTag> tags = tagServiceImpl.selectVoTagListLimit(null);
		
	}
}

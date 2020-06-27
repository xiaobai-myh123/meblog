package com.myh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myh.mapper.TagMapper;
import com.myh.pojo.Tag;
import com.myh.service.TagService;
import com.myh.vo.VoTag;

/*
**2020年6月10日--下午6:14:16
**@version:
**莫耀华:
**@Description:
*/
@Service
@Transactional
public class TagServiceImpl implements TagService{
	
	@Autowired
	private TagMapper tagMapper;
	
	@Override
	public int addTagOne(String tageName) {
		return tagMapper.addTagOne(tageName);
	}

	@Override
	public int delTagOne(Long id) {
		return tagMapper.delTagOne(id);
	}

	@Override
	public int updateTagOne(int id, String tageName) {
		return tagMapper.updateTagOne(id, tageName);
	}

	@Override
	public Tag selectTagOne(int id) {
		return tagMapper.selectTagOne(id);
	}

	@Override
	public List<Tag> selectTagList() {
		return tagMapper.selectTagList();
	}

	@Override
	public Tag selectTagByTagName(String tageName) {
		return tagMapper.selectTagByTagName(tageName);
	}

	@Override
	public int saveTag(Tag tag) {
		return tagMapper.saveTag(tag);
	}

	@Override
	public List<Tag> selectTagListLimit(Integer currentPageNo, Integer pageSize) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("currentPageNo", currentPageNo);
		map.put("pageSize", pageSize);
		return tagMapper.selectTagListLimit(map);
	}

	@Override
	public int countTag() {
		return tagMapper.countTag();
	}
    /*将字符串转化为集合*/
	private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

	@Override
	public List<Tag> listByIdsTag(String ids) {
		List<Long> idsList=convertToList(ids);
		return tagMapper.listByIdsTag(idsList);
	}

	@Override
	public int insertByIdsTag(Long blogs_id, List<Long> list) {
		return tagMapper.insertByIdsTag(blogs_id, list);
	}

	@Override
	public List<VoTag> selectVoTagListLimit(Long count) {
		return tagMapper.selectVoTagListLimit(count);
	}

	@Override
	public int countBlogByTagId(int tags_id) {
		return tagMapper.countBlogByTagId(tags_id);
	}

	@Override
	public int delTagByTagId(Long tags_id) {
		return tagMapper.delTagByTagId(tags_id);
	}

}

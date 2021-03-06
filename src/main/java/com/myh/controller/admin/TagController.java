package com.myh.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myh.pojo.Tag;
import com.myh.service.TagService;
import com.myh.service.impl.BlogServiceImpl;
import com.myh.utils.PageSupport;
import com.myh.utils.SystemConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "后台-标签的数据接口")
@Controller
@RequestMapping("/admin")
@Transactional
public class TagController {

	@Autowired
	private TagService tagServiceImpl;
	@Autowired
	private BlogServiceImpl blogService;
	//去标签页面
	
	@ApiOperation(value = "去标签页面",notes = "根据页数查询标签")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "path",name = "currentPage",value = "当前页数",required = false),
	})
    @GetMapping(value = {"/tags","/tags/{currentPage}"})
    public String getTagPage(@PathVariable(value = "currentPage", required = false) String currentPage, Model model) {
    	if (currentPage == null) {
			currentPage = "1";
		}
    	Integer _currentPage=Integer.parseInt(currentPage);
    	List<Tag> tagListLimit = tagServiceImpl.selectTagListLimit((_currentPage-1)*SystemConstant.NUMBER_OF_PAGES, SystemConstant.NUMBER_OF_PAGES);
    	model.addAttribute("tagListLimit", tagListLimit);
    	model.addAttribute("currentPage", _currentPage);//当前页码
    	PageSupport pageSupport=new PageSupport();
		pageSupport.setPageSize(SystemConstant.NUMBER_OF_PAGES);
		pageSupport.setTotalCount(tagServiceImpl.countTag());
		int totalPageCount = pageSupport.getTotalPageCount();
		model.addAttribute("totalPages", totalPageCount);//当前总页数
        return "admin/tags";
    }

    //去新增标签页面
	@ApiOperation(value = "去新增标签页面",notes = "去新增标签页面")
    @GetMapping("/tags/input")
    public String showInput(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }
    
	// 提交表单新增标签
	@ApiOperation(value = "提交表单新增标签",notes = "新增标签")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "Valid",name = "Tag",value = "tag",required = true),
	})
	@PostMapping("/tags")
	public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
		//@Valid 如果有不符合的数据  直接抛出 
		if (tag.getName() == null) {
			attributes.addFlashAttribute("name", "分类名字不能为空");
			return "admin/tags-input";
		}
//		System.out.println(1);
		Tag tag1 = tagServiceImpl.selectTagByTagName(tag.getName());
		if (tag1 != null) {
			result.rejectValue("name", "nameError", "不能添加重复的标签");//手动添加错误
		}
		if (result.hasErrors()) {
			return "admin/tags-input";
		}
		int countSaveTag = tagServiceImpl.saveTag(tag);
		if (countSaveTag > 0) {
			attributes.addFlashAttribute("message", "新增成功");
		} else {
			attributes.addFlashAttribute("message", "新增失败");
		}
		return "redirect:/admin/tags";
	}
	//去增加标签页面
	@ApiOperation(value = "根据id查询标签然后去修改标签",notes = "根据id查询标")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",value = "标签id"),
		})
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable("id") Integer id, Model model){
        model.addAttribute("tag",tagServiceImpl.selectTagOne(id));
        return "admin/tags-input";
    }

	// 提交 表单修改
	@PostMapping("/tags/{id}")
	@ApiOperation(value = "修改标签",notes = "根据id修改标签")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Tag",value = "tag"),
		})
	public String editPost(@Valid Tag tag, BindingResult result, @PathVariable("id") Integer id,
			RedirectAttributes attributes) {
		if (tag.getName() == null) {
			attributes.addFlashAttribute("message", "标签名字不能为空");
			return "admin/tags-input";
		}
		Tag tag1 = tagServiceImpl.selectTagByTagName(tag.getName());
		if (tag1 != null) {
			result.rejectValue("name", "nameError", "不能修改重复的标签");
		}
		if (result.hasErrors()) {
			return "admin/tags-input";
		}
		int countUpdateTagOne = tagServiceImpl.updateTagOne(id, tag.getName());
		if (countUpdateTagOne > 0) {
			attributes.addFlashAttribute("message", "更新成功");
		} else {
			attributes.addFlashAttribute("message", "更新失败");
		}
		return "redirect:/admin/tags";
	}
	
	//删除标签
	@ApiOperation(value = "删除标签",notes = "根据id删除标签")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",value = "id"),
		})
	@GetMapping("/tags/{id}/delete")
	public String deleteById(@PathVariable("id") long id, RedirectAttributes attributes) {
		int countBlogByTagId = tagServiceImpl.countBlogByTagId(Integer.parseInt(id+""));
		if(countBlogByTagId>0) {
			attributes.addFlashAttribute("message", "删除失败，当前标签下存在博客");
		}else {
			int CountdelTagOne = tagServiceImpl.delTagByTagId(id);
			if(CountdelTagOne>0) {
				attributes.addFlashAttribute("message", "删除成功");
			}else {
				attributes.addFlashAttribute("message", "删除失败");
			}
		}
		return "redirect:/admin/tags";
	}
}

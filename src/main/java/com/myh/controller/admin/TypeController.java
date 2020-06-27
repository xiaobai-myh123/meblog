package com.myh.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myh.pojo.Type;
import com.myh.service.TypeService;
import com.myh.service.impl.BlogServiceImpl;
import com.myh.service.impl.TagServiceImpl;
import com.myh.utils.PageSupport;
import com.myh.utils.SystemConstant;

@Controller
@RequestMapping("/admin")
@Transactional
public class TypeController {

	@Autowired
	@Qualifier("typeServiceImpl")
	private TypeService typeService;
	@Autowired
	private BlogServiceImpl blogService;
	// 去分类展示页面
	@GetMapping(value = {"/types","/types/{currentPage}"})
	public String goTypesPage(@PathVariable(value = "currentPage", required = false) String currentPage, Model model) {
		if (currentPage == null) {
			currentPage = "1";
		}
		Integer _currentPage=Integer.parseInt(currentPage);
		List<Type> selectTypeListLimit = typeService.selectTypeListLimit((_currentPage-1)*SystemConstant.NUMBER_OF_PAGES,SystemConstant.NUMBER_OF_PAGES);
		model.addAttribute("typeSelectList", selectTypeListLimit);
		model.addAttribute("currentPage", _currentPage);//当前页码
		PageSupport pageSupport=new PageSupport();
		pageSupport.setPageSize(SystemConstant.NUMBER_OF_PAGES);
		pageSupport.setTotalCount(typeService.countType());
		int totalPageCount = pageSupport.getTotalPageCount();
		model.addAttribute("totalPages", totalPageCount);//当前总页数
		return "admin/types";
	}

	
	
	// 去分类增加页面
	@GetMapping("/types/input")
	public String input(Model model) {
		model.addAttribute("type", new Type());
		return "admin/types-input";
	}

	// 提交分类增加页面
	@PostMapping("/types")
	public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
		if (type.getName() == null) {
			attributes.addFlashAttribute("message", "分类名字不能为空");
			return "redirect:/admin/types";
		}
		// 不能为一样的
		Type typeName = typeService.selectTypeByTypeName(type.getName());
		if(typeName!=null) {
			//name 和type 里面的name保持一样
			attributes.addFlashAttribute("message", "分类不能重复");
			return "redirect:/admin/types";
		}
		// @Valid 校验 返回结果
		// result 接受校验结果
		int savaType = typeService.saveType(type);
		if (savaType > 0) {
			attributes.addFlashAttribute("message", "新增成功");
		} else {
			attributes.addFlashAttribute("message", "新增失败");
		}
		return "redirect:/admin/types";
	}

	// 去修改分类名字页面
	@GetMapping("/types/{id}/input")
	public String editInput(@PathVariable("id") Integer id, Model model) {
		System.out.println(typeService.selectTypeOne(id));
		model.addAttribute("type", typeService.selectTypeOne(id));
		return "admin/types-input";
	}
	
	//提交修改分类名
	@PostMapping("/types/{id}")
	public String editPost(@Valid Type type,BindingResult result,
			@PathVariable("id")Integer id,
			RedirectAttributes attributes
	) {
		if (type.getName() == null) {
			attributes.addFlashAttribute("message", "分类名字不能为空");
			return "redirect:/admin/types";
		}
		// 不能为一样的
		Type typeName = typeService.selectTypeByTypeName(type.getName());
		if(typeName!=null) {
			//name 和type 里面的name保持一样
			attributes.addFlashAttribute("message", "分类不能重复");
			return "redirect:/admin/types";
		}
		int updateTypeOne=typeService.updateTypeOne(id, type.getName());
		if(updateTypeOne>0) {
			attributes.addFlashAttribute("message", "修改成功");
		}else {
			attributes.addFlashAttribute("message", "修改失败");
		}
		return "redirect:/admin/types";
	}
	
	//删除分类
	@GetMapping("/types/{id}/delete")
	public String delete(@PathVariable("id") Integer id,RedirectAttributes attributes) {
		int countBlogByType = blogService.countBlogByType(Long.parseLong(id+""));
		if(countBlogByType>0) {
			attributes.addFlashAttribute("message", "刪除失败，当前分类下存在博客");
		}else {
			typeService.delTypeOne(id);
			attributes.addFlashAttribute("message", "刪除成功");
		}
		return "redirect:/admin/types";
	}
}

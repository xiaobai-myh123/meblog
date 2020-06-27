package com.myh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.myh.service.BlogService;
import com.myh.service.impl.BlogServiceImpl;

/*
**2020年6月24日--下午2:37:48
**@version:
**莫耀华:
**@Description:
*/
/**
 * 归档页面
 * @author 莫耀华
 *
 */
@Controller
public class ArchiveShowController {
    @Autowired
    private BlogServiceImpl blogServiceImpl;


    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", blogServiceImpl.selectArchiesByYearTimeAdvice());
		//最受欢迎
		model.addAttribute("newblogs", blogServiceImpl.selectMostPopularBlog());
        model.addAttribute("blogCount", blogServiceImpl.countBlogBypublicshed());
        return "archives";
    }
}

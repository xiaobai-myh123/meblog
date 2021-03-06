package com.myh.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myh.pojo.Blog;
import com.myh.pojo.Comment;
import com.myh.service.BlogService;
import com.myh.service.TagService;
import com.myh.service.TypeService;
import com.myh.service.UserService;
import com.myh.service.impl.CommentServiceImpl;
import com.myh.service.impl.EsServiceImpl;
import com.myh.utils.MarkdownUtils;
import com.myh.utils.PageSupport;
import com.myh.utils.RedisUtils;
import com.myh.utils.SystemConstant;
import com.myh.vo.VoBlog;
import com.myh.vo.VoTag;
import com.myh.vo.VoType;

/**
 * 前端首页
 * 
 * @author 莫耀华
 *
 */
@Controller
@Transactional
public class IndexController {
	@Autowired
	private UserService userService;
	@Autowired
	private BlogService blogServiceImpl;
	@Autowired
	private TypeService typeServiceImpl;
	@Autowired
	private TagService tagServiceImpl;
	@Autowired
	private CommentServiceImpl commentServiceImpl;
	// 注入es
	@Autowired
	private EsServiceImpl esServiceImpl;
	@Autowired // redis工具类
	private RedisUtils redisUtils;

	/**
	 * 第一次进入首页展示
	 * 
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "index.html", "index" })
	public String index(String currentPage, Model model) {
		if (currentPage == null) {
			currentPage = "1";
		}
		Integer _currentPage = Integer.parseInt(currentPage);
		// 1. 获取分页的博客列表
		List<VoBlog> blogs = blogServiceImpl.selectVoFrontDeskBlog(
				(_currentPage - 1) * SystemConstant.RECOMMEND_OF_PAGES, SystemConstant.RECOMMEND_OF_PAGES, null);
		model.addAttribute("blogs", blogs);
		// 2. 获取分类的内容(显示6条)
		List<VoType> types = typeServiceImpl.selectVoTypeListLimit(6L);
		model.addAttribute("types", types);
		// 3. 获取标签的内容
		List<VoTag> tags = tagServiceImpl.selectVoTagListLimit(6L);
		model.addAttribute("tags", tags);
//		//4. 显示推荐博客列表 
		List<Blog> recommendBlogs = blogServiceImpl.selectRecommend(0, SystemConstant.RECOMMEND_OF_PAGES);
		model.addAttribute("recommendBlogs", recommendBlogs);
		// 5. 博客数量 和分页的数量 以及当前页
		int countBlogs = blogServiceImpl.countBlogFrontDesk();
		// 总博客数
		model.addAttribute("countBlogs", countBlogs);
		PageSupport pageSupport = new PageSupport();
		pageSupport.setPageSize(SystemConstant.RECOMMEND_OF_PAGES);
		pageSupport.setTotalCount(countBlogs);
		// 总页数
		model.addAttribute("totalPages", pageSupport.getTotalPageCount());
		// 当前页数
		model.addAttribute("currentPage", _currentPage);
		// 最受欢迎
		model.addAttribute("newblogs", blogServiceImpl.selectMostPopularBlog());
		return "index";

	}

	// 分页去index 和上面方法一样 可以封装 我懒的弄
	/**
	 * 分页进入首页
	 * 
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/limitIndex" })
	public String indexList(String currentPage, Model model) {
		if (currentPage == null) {
			currentPage = "1";
		}
		Integer _currentPage = Integer.parseInt(currentPage);
		// 1. 获取分页的博客列表
		List<VoBlog> blogs = blogServiceImpl.selectVoFrontDeskBlog(
				(_currentPage - 1) * SystemConstant.RECOMMEND_OF_PAGES, SystemConstant.RECOMMEND_OF_PAGES, null);
		model.addAttribute("blogs", blogs);
		// 2. 获取分类的内容(显示6条)
		List<VoType> types = typeServiceImpl.selectVoTypeListLimit(6L);
		model.addAttribute("types", types);
		// 3. 获取标签的内容
		List<VoTag> tags = tagServiceImpl.selectVoTagListLimit(6L);
		model.addAttribute("tags", tags);
//		//4. 显示推荐博客列表 
		List<Blog> recommendBlogs = blogServiceImpl.selectRecommend(0, SystemConstant.RECOMMEND_OF_PAGES);
		model.addAttribute("recommendBlogs", recommendBlogs);
		// 5. 博客数量 和分页的数量 以及当前页
		int countBlogs = blogServiceImpl.countBlogFrontDesk();
		// 总博客数
		model.addAttribute("countBlogs", countBlogs);
		PageSupport pageSupport = new PageSupport();
		pageSupport.setPageSize(SystemConstant.RECOMMEND_OF_PAGES);
		pageSupport.setTotalCount(countBlogs);
		// 总页数
		model.addAttribute("totalPages", pageSupport.getTotalPageCount());
		// 当前页数
		model.addAttribute("currentPage", _currentPage);
		return "index::indexList";
	}

	// 去博客详情
	@GetMapping("/blog/{id}")
	public String blog(@PathVariable("id") Long id, Model model, HttpServletRequest request) throws Exception {
		// 访问量加一 根据ip判断 是否是同一用户请求多次
		String ip = request.getRemoteAddr();
		String ipBlogIDRedis = (String) redisUtils.get(ip+":"+id);
		if(ipBlogIDRedis==null) {
			//如果是第一次访问  则加一
			blogServiceImpl.addViewById(id); // 判断同一个ip访问 10分钟之后刷新只算一次
			redisUtils.set(ip+":"+id, id+"",60*10);
		}		
		Blog blog = blogServiceImpl.select(id);
		if (blog == null) {
			System.out.println("首页从数据库拿到的博客详情为空");
			throw new Exception("com.myh.controller.IndexController.blog 异常");
		}
		String htmlGetContent = MarkdownUtils.markdownToHtmlExtensions(blog.getContent());
		// md -》html
		blog.setContent(htmlGetContent);
		// 博客
		model.addAttribute("blog", blog);
		// 最受欢迎
		model.addAttribute("newblogs", blogServiceImpl.selectMostPopularBlog());
		// 博客的评论
		List<Comment> comments = commentServiceImpl.getCommentById(id);
		model.addAttribute("comments", comments);
		return "blog";
	}

	// 博客搜索
	@GetMapping("/search")
	public String searchByInfo(@RequestParam("keyword") String keyword,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo, Model model) {
		model.addAttribute("keyword", keyword);
		List<Map<String, Object>> listAll = esServiceImpl.getListAll(keyword, pageNo, 10);
		model.addAttribute("user", userService.seleteNickNameAndAvatar());
		// 查询博客信息 不走数据库 走es
		// 查询的博客
		model.addAttribute("listAll", listAll);
		// 最受欢迎
		model.addAttribute("newblogs", blogServiceImpl.selectMostPopularBlog());
		// 3. 获取标签的内容
		List<VoTag> tags = tagServiceImpl.selectVoTagListLimit(null);
		model.addAttribute("tags", tags);
		return "search";
	}
}

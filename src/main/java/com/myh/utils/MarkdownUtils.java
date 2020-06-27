package com.myh.utils;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.util.*;

public class MarkdownUtils {

	/**
	 * markdown格式转换成HTML格式
	 * 
	 * @param markdown
	 * @return
	 */
	public static String markdownToHtml(String markdown) {
		Parser parser = Parser.builder().build();
		Node document = parser.parse(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		return renderer.render(document);
	}

	/**
	 * 增加扩展[标题锚点，表格生成] Markdown转换成HTML
	 * 
	 * @param markdown
	 * @return
	 */
	public static String markdownToHtmlExtensions(String markdown) {
		// h标题生成id
		Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
		// 转换table的HTML
		List<Extension> tableExtension = Arrays.asList(TablesExtension.create());
		Parser parser = Parser.builder().extensions(tableExtension).build();
		Node document = parser.parse(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder().extensions(headingAnchorExtensions).extensions(tableExtension)
				.attributeProviderFactory(new AttributeProviderFactory() {
					public AttributeProvider create(AttributeProviderContext context) {
						return new CustomAttributeProvider();
					}
				}).build();
		return renderer.render(document);
	}

	/**
	 * 处理标签的属性
	 */
	static class CustomAttributeProvider implements AttributeProvider {
		@Override
		public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
			// 改变a标签的target属性为_blank
			if (node instanceof Link) {
				attributes.put("target", "_blank");
			}
			if (node instanceof TableBlock) {
				attributes.put("class", "ui celled table");
			}
		}
	}
	
	/**
	 * html 转纯文本
	 * @param html
	 * @return
	 */
	public static String convert(String html) {
		if (StringUtils.isEmpty(html)) {
			return "";
		}

		Document document = Jsoup.parse(html);
		Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
		document.outputSettings(outputSettings);
		document.select("br").append("\\n");
		document.select("p").prepend("\\n");
		document.select("p").append("\\n");
		String newHtml = document.html().replaceAll("\\\\n", "\n");
		String plainText = Jsoup.clean(newHtml, "", Whitelist.none(), outputSettings);
		String result = StringEscapeUtils.unescapeHtml(plainText.trim());
		return result;
	}

	public static void main(String[] args) {
		String table = "| hello | hi   | 哈哈哈   |\n" + "| ----- | ---- | -----  |\n" + "| 斯维尔多  | 士大夫  | f啊    |\n"
				+ "| 阿什顿发  | 非固定杆 | 撒阿什顿发 |\n" + "\n#### **啊啊 啊啊**"
						+ "\n"+
						"> 1. 上传文件使用jar包\r\n" + 
						"> commons-fileupload-1.3.3.jar\r\n" + 
						"> commons-io-2.6.jar（依赖包）";
		String a = "[imCoding 爱编程](http://www.lirenmi.cn)";
		System.out.println(markdownToHtmlExtensions(a));
		System.out.println(convert(markdownToHtmlExtensions(table)));
	}
}

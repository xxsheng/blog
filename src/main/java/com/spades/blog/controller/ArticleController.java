package com.spades.blog.controller;

import com.spades.blog.entity.Article;
import com.spades.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.parser.ParseException;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("")
    public String list(Model model){
        List<Article> articles = articleService.list();
        model.addAttribute("articles",articles);
        return "front/index";
    }
    @RequestMapping("/column/{displayname}/{category}")
    public String column(@PathVariable("displayname")String displayname,@PathVariable("category")String category,Model model){
        model.addAttribute("articles",articleService.getArticleByCategoryName(category));
        model.addAttribute("displayname",displayname);
        return "front/columnPage";
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable("id")String id,Model model){
        Article article = articleService.getById(id);
        System.out.println(article.getId());
        Markdown markdown = new Markdown();

        try {
            StringWriter out = new StringWriter();
            markdown.transform(new StringReader(article.getContent()),out);
            out.flush();
            article.setContent(out.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        model.addAttribute("article",article);
        return "front/detail";
    }
    @RequestMapping("/search")
    public String search(String key,Model model){
        List<Article> articles = articleService.search(key);
        model.addAttribute("articles",articles);
        return "front/columnPage";
    }

    @RequestMapping("/get/{id}")
    public String get(Model model, @PathVariable(name = "id")String id){
        return "index";
    }

}

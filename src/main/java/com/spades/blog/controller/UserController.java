package com.spades.blog.controller;

import com.spades.blog.entity.Article;
import com.spades.blog.entity.Category;
import com.spades.blog.entity.User;
import com.spades.blog.service.ArticleService;
import com.spades.blog.service.CategoryService;
import com.spades.blog.service.UserServie;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserServie userServie;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @RequestMapping("")
    public String admin(Model model){
        List<Article> articles = articleService.list();
        model.addAttribute("articles",articles);
        return "admin/index";
    }

    @RequestMapping("/login")
    public String login(){
        return "admin/login";
    }

    @RequestMapping(value = "/dologin",method = RequestMethod.POST)
    public String doLogin(HttpServletResponse response, User user,Model model){
        System.out.println(user);
        
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        // 执行认证登陆
        subject.login(token);
        
        if (userServie.login(user.getUserName(),user.getPassword())){
            model.addAttribute("user", user);
            return "redirect:/admin";
        }else {
            model.addAttribute("error", "用户名或者密码错误");
            return "admin/login";
        }
    }
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id")String id){

        articleService.delete(articleService.getById(id));

        return "redirect:/admin";
    }

    @RequestMapping("/write")
    public String write(Model model){
        List<Category> categories = categoryService.list();
        model.addAttribute("categories",categories);
        model.addAttribute("article",new Article());
        return "admin/write";
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Article article){
        String name = article.getCategory().getName();
        Category category = categoryService.findByName(name);
        article.setCategory(category);
        if (article.getContent().length()>40){
            article.setSummary(article.getContent().substring(0,40));

        }else {
            article.setSummary(article.getContent());
        }
        article.setDate(sdf.format(new Date()));
        articleService.save(article);
        return "redirect:/admin";
    }

    @RequestMapping("/update/{id}")
    public String update(@PathVariable("id")String id,Model model){
        Article article = articleService.getById(id);
        model.addAttribute("target",article);
        List<Category> categories = categoryService.list();
        model.addAttribute("categories",categories);

        return "admin/update";
    }
}

























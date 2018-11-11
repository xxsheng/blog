/**
 * 
 */
package com.spades.blog.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xxq_1
 *
 */

@Controller
public class DefaultController {

	@RequestMapping("/")
	public String index(Model model,HttpServletResponse reponse) {
		
		
		return "redirect:article";	
		
	}
}

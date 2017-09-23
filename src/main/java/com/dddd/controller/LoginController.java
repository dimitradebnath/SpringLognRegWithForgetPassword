package com.dddd.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dddd.model.Login;
import com.dddd.model.User;
import com.dddd.services.UserServices;
import com.dddd.validation.UserValidator;

@Controller
public class LoginController {

	@Autowired
	UserServices userService;
	@Autowired
	UserValidator userValidator;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView showLogin(HttpServletRequest req, HttpServletResponse resp) {

		System.out.println("inside the slash of the login controller");
		ModelAndView mav = new ModelAndView("login");
		Login newLogin = new Login();
		mav.addObject("login", newLogin);
		return mav;
	}

	@RequestMapping(value = "/loginprocess", method = RequestMethod.POST)
	public ModelAndView loginProcess(HttpServletRequest req, HttpServletResponse resp,
			@ModelAttribute("login") Login newLogin, BindingResult result, HttpSession session) throws IOException {
		System.out.println(newLogin);
		ModelAndView mav = null;
		User user = userService.validateUser(newLogin);
		
		
		if (user != null) {
			mav = new ModelAndView("home");
			
			String u = req.getParameter("username");

			session.setAttribute("username", u);
		}

		else {

			mav = new ModelAndView("login");

			mav.addObject("message", "Please LOG IN Again");
		}
		return mav;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		httpSession.invalidate();
		return "redirect:/";
	}

}

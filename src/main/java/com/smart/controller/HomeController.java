package com.smart.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

//import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public UserRepository userRepository;
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title","BASE-smart-contact-manager");
		return"home";
	}
	
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title","ABOUT-smart-contact-manager");
		return"about";
	}
	
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title","Register-smart-contact-manager");
		//idhar User() isliye liya gaya because voh phle kuch nahi bej raha hai
		model.addAttribute("user",new User());
		return"signup";
	}
	
	//handle the registering user
	@RequestMapping(value = "/do_register",method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1, 
			@RequestParam(value="agreement",defaultValue="false") boolean agreement,Model model,HttpSession session) {
		
		try {
			if(!agreement) {
				System.out.println("you have not accepted terms and conditions");
				throw new Exception("you have not accepted terms and conditions");
			}
			
			
			if (result1.hasErrors()) {
				System.out.println("ERROR" + result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}
			 
			 
			user.setRole("ROLE_USER");
			user.setEnable(true);
			user.setImageUrl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			
			System.out.println("agreement:" + agreement);
			System.out.println("USER"+ user);
			
		    User result =this.userRepository.save(user);
			model.addAttribute("user",new User());
		    session.setAttribute("message", new Message("Successfully Registered !!","alert-success"));
			return "signup";
		} catch (Exception e) {
			e.printStackTrace();
			// here we are using because joh bhi data aya hai voh registation form main dekhega
			model.addAttribute("user",user);
		    session.setAttribute("message", new Message("Something Went Wrong !!"+e.getMessage(), "alert-danger"));
			return "signup";
		}
		
		
		
		
	}

	@GetMapping("/signin")
	public String login(Model model) {
		model.addAttribute("titlt","login page");
		return "login";
		
	}

}


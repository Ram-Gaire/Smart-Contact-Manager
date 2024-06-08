package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

import javassist.expr.NewArray;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContactRepository contactRespository;

	// method for adding comman data to response
	@ModelAttribute
	public void addCommonData(Model m, Principal principal) {

		String username = principal.getName();
		System.out.println("username :" + username);

		// get the user using username(email)
		User user = userRepository.getUserByUsername(username);
		System.out.println("USER :" + user);
		m.addAttribute("user", user);

	}

	// dashboard home
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title", "User Dashboard");
		return "normal/user_dashboard";
	}

	// open add form handler
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());

		return "normal/add_contact_form";
	}

	// procesing add form handler
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
			Principal principal, HttpSession session) {
		try {
			String name = principal.getName();
			User user = this.userRepository.getUserByUsername(name);
			// processing and uploading file...
			if (file.isEmpty()) {
				// if the file is empty then try our message
				System.out.println("file is empty");
				contact.setImage("contact.png");

			} else {
				// file the file to folder and update the name to contact
				contact.setImage(file.getOriginalFilename());

				File saveFile = new ClassPathResource("static/image").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Image is uploaded");
			}
			contact.setUser(user);
			user.getContacts().add(contact);
			this.userRepository.save(user);

			System.out.println("data" + contact);

			System.out.println("Added to data base");
			/// message success.....
			session.setAttribute("message", new Message("Your contact is added !! add more", "success"));
		} catch (Exception e) {
			System.out.println("ERROR" + e.getMessage());
			e.printStackTrace();
			// error meesage
			session.setAttribute("Something went wrong !! Try Again", "danger");
		}

		return "normal/add_contact_form";
	}

	@GetMapping("show_contact/{page}")
	public String showContact(@PathVariable("page") Integer page, Model m, Principal principal) {
		m.addAttribute("title", "view-contacts");
		// contacts to bejna hai
		String userName = principal.getName();
		User user = this.userRepository.getUserByUsername(userName);

		Pageable pageable = PageRequest.of(page, 5);

		Page<Contact> contacts = this.contactRespository.findContactsByUser(user.getId(), pageable);

		m.addAttribute("contacts", contacts);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", contacts.getTotalPages());

		return "normal/show_contact";

	}

	// showing particular contact details

	@GetMapping("contact/{cId}")
	public String showContactDetails(@PathVariable("cId") Integer cId, Model m, Principal principal) {

		Optional<Contact> contactOptional = this.contactRespository.findById(cId);
		Contact contact = contactOptional.get();

		// we will get user name from this
		String userName = principal.getName();

		// with the help username we get to which user is current login below method
		User user = this.userRepository.getUserByUsername(userName);// whihc user is loggined

		// user.getid() the person is logined check the contact.getUser().getid() if
		// match its allows
		if (user.getId() == contact.getUser().getId()) {
			m.addAttribute("contact", contact);
			m.addAttribute("title", contact.getName());
		}

		return "normal/contact_detail";

	}

	// delete the contact
	@GetMapping("/delete/{cId}")
	public String deleteContact(@PathVariable("cId") Integer cId, Model model, Principal principal,
			HttpSession session) {
		Optional<Contact> contactOptional = this.contactRespository.findById(cId);
		Contact contact = contactOptional.get();
		// contact.setUser(null);

		// we will get user name from this
		String userName = principal.getName();

		// with the help username we get to which user is current login below method
		User user = this.userRepository.getUserByUsername(userName);// whihc user is loggined

		// check...
		if (user.getId() == contact.getUser().getId()) {
			user.getContacts().remove(contact);

			this.contactRespository.delete(contact);
			session.setAttribute("message", new Message("contact deleted successfully", "success"));
		}
		return "redirect:/user/show_contact/0";

	}

	// open updae form handler

	@PostMapping("/update-contact/{cId}")
	public String updateForm(Model m, @PathVariable("cId") Integer cId) {
		m.addAttribute("titlt", "update Contact");

		Contact contact = this.contactRespository.findById(cId).get();
		m.addAttribute("contact", contact);
		return "normal/update_form";

	}

	// update contact handler

	@PostMapping("/process-update")
	public String updateHandler(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
			Model m, HttpSession session, Principal principal) {
		// old contact details
		Contact oldContactDetail = this.contactRespository.findById(contact.getcId()).get();

		try {
			if (!file.isEmpty()) {
				// delete image
				File deleteFile = new ClassPathResource("static/image").getFile();
				File file2 = new File(deleteFile, oldContactDetail.getImage());
				file2.delete();

				// file work
				// rewrite

				// update image
				File saveFile = new ClassPathResource("static/image").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				contact.setImage(file.getOriginalFilename());
				System.out.println("this try is running");

			} else {
				contact.setImage(oldContactDetail.getImage());
			}

			User user = this.userRepository.getUserByUsername(principal.getName());
			contact.setUser(user);
			this.contactRespository.save(contact);

			session.setAttribute("message", new Message("Your contact is updated", "success"));
		} catch (Exception e) {
			e.printStackTrace();

		}
		return "redirect:/user/"+contact.getcId()+"/contact";

	}
	
	//view profile page handler
	@GetMapping("/profile")
	public String profile(Model m) {
		
		return "normal/profile";
		
	}

}

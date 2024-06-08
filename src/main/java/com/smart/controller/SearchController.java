
 package com.smart.controller;
 /* 
 * import java.security.Principal; import java.util.List;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.smart.dao.ContactRepository; import com.smart.dao.UserRepository;
 * import com.smart.entities.Contact; import com.smart.entities.User;
 * 
 * @RestController public class SearchController {
 * 
 * @Autowired private UserRepository userRepository;
 * 
 * @Autowired private ContactRepository contactRepository;
 * 
 * @GetMapping("/search/{query}") public ResponseEntity<?>
 * search(@PathVariable("query") String query,Principal principal){
 * 
 * System.out.println(query);
 * 
 * User user = this.userRepository.getUserByUsername(principal.getName());
 * 
 * List<Contact> contacts =
 * this.contactRepository.findByNameContainingAndUser(query, user);
 * 
 * return ResponseEntity.ok(contacts);
 * 
 * }
 * 
 * }
 */

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;

@RestController
public class SearchController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal) {
        try {
            System.out.println("Search query: " + query);

            User user = this.userRepository.getUserByUsername(principal.getName());
            List<Contact> contacts = this.contactRepository.findByNameContainingAndUser(query, user);

            String jsonResponse = objectMapper.writeValueAsString(contacts);
            logger.info("Sending JSON response: {}", jsonResponse);

            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
            logger.error("Error during search operation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }
}

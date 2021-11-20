package com.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.exception.ResourceNotFoundException;
import com.app.model.User;
import com.app.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController
{
	
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/users")
	public List<User> getAllUsers()
	{
		
		List<User> list1 = userRepository.findAll();
		
		System.out.println(list1);
				
		return userRepository.findAll();
		
	}
	

	
	
	@PostMapping("/callservice2")
	public User createUser(@Valid @RequestBody User user)
	{
		
		System.out.println("user-firstname : " +user.getFirstName());
		System.out.println("user-lastname : " +user.getLastName());
		System.out.println("user-emailId : " +user.getEmailId());
		
		return userRepository.save(user);
	}
	
	
	
	
	

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException
	{
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		return ResponseEntity.ok().body(user);
	}

	/*
	 * @PostMapping("/users") public User createUser(@Valid @RequestBody User user) { return userRepository.save(user); }
	 */

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails) throws ResourceNotFoundException
	{
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + userId));

		user.setEmailId(userDetails.getEmailId());
		user.setLastName(userDetails.getLastName());
		user.setFirstName(userDetails.getFirstName());
		final User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}

	
	@DeleteMapping("/users/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException
	{
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + userId));
		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
}

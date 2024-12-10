package com.car.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.entity.User;
import com.car.request.AddReturnCarRequest;
import com.car.request.UserRequest;
import com.car.response.DeletedUserResponse;
import com.car.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("addUsers")
	private ResponseEntity<List<User>> addUser(@RequestBody List<UserRequest> request){
		List<User> user = userService.createUser(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);	
	}
	
	@PostMapping("/delete")
	public ResponseEntity<List<DeletedUserResponse>> deleteTheUser(@RequestBody List<AddReturnCarRequest> ids) {
	    List<DeletedUserResponse> deletedResponses = userService.deleteTheUser(ids);
	    
	    boolean anyDeleted = deletedResponses.stream().anyMatch(DeletedUserResponse::getIsDeleted);
	    
	    return anyDeleted ? ResponseEntity.ok(deletedResponses) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(deletedResponses);
	}

}

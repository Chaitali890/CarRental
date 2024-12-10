package com.car.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.entity.User;
import com.car.repository.UserRepository;
import com.car.request.AddReturnCarRequest;
import com.car.request.UserRequest;
import com.car.response.DeletedUserResponse;

@Service
public class UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	
	public List<User> createUser(List<UserRequest> request)
	{
		
		long count = userRepository.count();
		
		List<User> userList = new ArrayList<>();
		User user;
		for(UserRequest req : request)
		{
			if(req.getId()!=null) {
		
			Optional<User> user1 = userRepository.findById(req.getId());
			if(user1.isPresent())
			{
				user = user1.get();
				
			user.setCity(req.getCity());
			user.setCustomerName(req.getCustomerName());
			user.setDistrict(req.getDistrict());
			user.setPincode(req.getPincode());
			user.setState(req.getState());
			user.setContactNumber(req.getContactNumber());
			}
			else
			{
				throw new IllegalArgumentException("user not present with id:"+req.getId());
			}
			}
			else
			{
					user = new User();
					user.setCity(req.getCity());
					user.setCustomerName(req.getCustomerName());
					user.setDistrict(req.getDistrict());
					user.setPincode(req.getPincode());
					user.setState(req.getState());
					user.setContactNumber(req.getContactNumber());
					String userIdentificationNumber = String.format("u%04d", count+1);
					user.setCustIdentificationNumber(userIdentificationNumber);	
			}
			
		
			
			userList.add(user);
			count++;
		}
		
		return userRepository.saveAll(userList);
	}
	
	//Delete the user
	
	public List<DeletedUserResponse> deleteTheUser(List<AddReturnCarRequest> ids) {
	   
		List<DeletedUserResponse> finalResponse = new ArrayList<>();
		
		for(AddReturnCarRequest id : ids) {
			
			DeletedUserResponse response = new DeletedUserResponse();
			if(id!=null && id.getId()!=null)
			{
				Optional<User> user = userRepository.findById(id.getId());
				if(user.isPresent())
				{
					User existingUser = user.get();
					existingUser.setIsDeleted(false);
					userRepository.save(existingUser);
					
					response.setIsDeleted(existingUser.getIsDeleted());
					response.setId(existingUser.getId());
				}
				else
				{
					response.setIsDeleted(true);
				}
						
			}
			else
			{
				response.setIsDeleted(true);
			}
			
			finalResponse.add(response);
		}
		
		return finalResponse;
	}

	
	
}

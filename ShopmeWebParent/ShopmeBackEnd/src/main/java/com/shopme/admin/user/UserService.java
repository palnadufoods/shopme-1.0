package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Service
@Transactional
public class UserService {
	public static final int USERS_PER_PAGE=4;

	@Autowired
	public UserRepository userRepo;

	@Autowired
	public RoleRepository roleRepo;

	@Autowired
	public PasswordEncoder passwordEncoder;

	public List<User> listAll() {

		return (List<User>) userRepo.findAll(Sort.by("firstName").ascending());
	}
	
	public Page<User> listByPage(int pageNumber,String sortField,String sortDir,String keyword)
	{
		Sort sort=Sort.by(sortField);
		sort=sortDir.equals("asc")?sort.ascending():sort.descending();
		Pageable pageable=PageRequest.of(pageNumber-1,USERS_PER_PAGE,sort);	
		
		if(keyword!=null)
			return userRepo.findAll(keyword,pageable);
		 return userRepo.findAll(pageable);
		
	} 

	public List<Role> listRoles() {
		return (List<Role>) roleRepo.findAll();
	}

	public User save(User user) {
		boolean isUpdatinguser = user.getId() != null;
		System.out.println("--------------------> User is updating " + isUpdatinguser);
		if (isUpdatinguser) {
			if (user.getPassword().isEmpty()) {
				User existingUser = userRepo.findById(user.getId()).get();
				user.setPassword(existingUser.getPassword());
			} else
				encodePassword(user);

		} else
			encodePassword(user);
		return userRepo.save(user);
	}
	
	public User updateAccount(User userInForm)
	{
		User userIndDb =userRepo.findById(userInForm.getId()).get();
		if(!userInForm.getPassword().isEmpty())
		{
			userIndDb.setPassword(userInForm.getPassword());
			encodePassword(userIndDb);
			
		}
		if(userInForm.getPhotos()!=null)
		{
			userIndDb.setPhotos(userInForm.getPhotos());
		}
		
		userIndDb.setFirstName(userInForm.getFirstName());
		userIndDb.setLastName(userInForm.getLastName());
		
		return userRepo.save(userIndDb);

	}

	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}

	public boolean isEmailUnique(int id, String email) {
		User userByEmail = userRepo.getUserByEmail(email);
		if (userByEmail == null || userByEmail.getId() == id)
			return true;
		return false;
	}

	public User get(Integer id) throws UserNotFoundException {
		try {
			User user = userRepo.findById(id).get();
			return user;
		} catch (NoSuchElementException e) {
			throw new UserNotFoundException("Could not found any user with id " + id);
		}
	}

	public Long countById(int id) throws UserNotFoundException {
		try {
			Long count = userRepo.countById(id);

			return count;
		} catch (Exception e) {
			throw new UserNotFoundException("Could Not Connect to the server");
		}

	}

	public void deleteUser(int id) throws UserNotFoundException {
			if (countById(id) != 0)
				userRepo.deleteById(id);
			else 
				throw new UserNotFoundException("Could not found any user with id " + id);
		
	}
	public void updateEnabledStatus(Integer id,boolean enabled)
	{
		userRepo.updateEnabledStatus(id, enabled);
	}
	
	
	public User getUserByEmail(String email)
	{
		return userRepo.getUserByEmail(email);
	}
	
	
	
	
}

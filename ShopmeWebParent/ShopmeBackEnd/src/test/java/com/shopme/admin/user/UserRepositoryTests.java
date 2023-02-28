package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private EntityManager entityManager;
	
	
	@Test
	public void testCreatorNewUser()
	{
		Role roleAdmin=entityManager.find(Role.class,1);
		User namUser=new User("nam@codejava.net","nam2020","Nam","Ha Minh");
		namUser.addRole(roleAdmin);
		User savedUser=repo.save(namUser);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreatorNewCreatorWithTwoRoles()
	{
		User userSai=new User("sai@gmail.com","Sai Krishna","Badineedi","Fassak@08");
		Role roleEditor=entityManager.find(Role.class, 3);
		Role roleAssistant=entityManager.find(Role.class,5);
		userSai.addRole(roleEditor);
		userSai.addRole(roleAssistant);
		
		User savedUser=repo.save(userSai);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	
	@Test
	public void testListAllUsers()
	{
		//List<User> usersList= (List<User>) repo.findAll();
		Iterable<User> usersList = repo.findAll();
		usersList.forEach(user->System.out.println(user));
	}
	
	@Test
	public void testGetUserById()
	{
		User userNam=repo.findById(1).get();
		System.out.println(userNam);
		assertThat(userNam).isNotNull();
		
	}
	
	@Test
	public void testUpdateUserDetails()
	{
		User userNam=repo.findById(1).get();
		userNam.setEnabled(true);
		userNam.setEmail("namhamin@gmail.com");
		User userSaved =repo.save(userNam);
		assertThat(userSaved.getId()).isGreaterThan(0);
		
	}
	
	@Test
	
	public void testRemoveUserRole()
	{
		User userRavi=repo.findById(2).get();
		Role roleEditor=entityManager.find(Role.class,3);
		userRavi.getRoles().remove(roleEditor);
		repo.save(userRavi);
	}
	
	@Test
	public void testUpdateUserRoles()
	{
		User userRavi =repo.findById(2).get();
		Role roleEditor=entityManager.find(Role.class, 3);
		userRavi.getRoles().remove(roleEditor);
		
		Role roleSalesman=new Role(2);
		userRavi.addRole(roleSalesman);
		repo.save(userRavi);
		
	}
	
	@Test
	public void testDeleteUser()
	{
		Integer userId=2;
		repo.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail()
	{
		String email="yadav@gmail.com";
		User retrievedUser =repo.getUserByEmail(email);
		assertThat(retrievedUser).isNotNull();
	}
	
	@Test
	public void testcountById()
	{
		Long count=repo.countById(99);
		if(count==null||count==0)
			System.out.println("No User Found with this id "+count);
		else
			System.out.println("number of Users with the given id "+count);
		
		assertThat(count).isNotNull().isGreaterThan(0);
	}
	
	@Test 
	public void testEnabled()
	{
		Integer id=1;
		repo.updateEnabledStatus(id, true);
	}
	
	
	@Test 
	public void testEnabled1()
	{
		Integer id=1;
		repo.updateEnabledStatus1(id, false);
	}
	
	@Test
	public void pageTesting()
	{
		int pageNumber=0;
		int pageSize=4;
		Pageable pageable=PageRequest.of(pageNumber, pageSize);
		Page<User> page=repo.findAll(pageable);
		
		List<User> listOfUsers=page.getContent();
		
		listOfUsers.forEach(user->System.out.println(user));
		//assertThat(listOfUsers.size()).isEqualTo(pageSize);
		
	}
	
	@Test
	public void testSearchUsers()
	{
		String keyword="sai";
		int pageNumber=0;
		int pageSize=4;
		Pageable pageable=PageRequest.of(pageNumber, pageSize);
		Page<User> page=repo.findAll(keyword,pageable);
		
		List<User> listOfUsers=page.getContent();
		
		listOfUsers.forEach(user->System.out.println(user));
		//assertThat(listOfUsers.size()).isGreaterThan(0);
		
	}
	
	
	
	

}

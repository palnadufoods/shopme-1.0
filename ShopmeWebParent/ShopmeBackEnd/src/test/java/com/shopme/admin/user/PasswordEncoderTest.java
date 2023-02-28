package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	
	
	@Test
	public void testEncodePassword()
	{
		BCryptPasswordEncoder bcryptPasswordEncoder=new BCryptPasswordEncoder();
		String rawPassword="Fassak@08";
		String encodedPassword=bcryptPasswordEncoder.encode(rawPassword);
		
		System.out.println(encodedPassword);
		
		boolean matches=bcryptPasswordEncoder.matches(rawPassword, encodedPassword);
		assertThat(matches).isTrue();
		
	}
}

package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService()
	{
		return new ShopmeUserDetailsService();
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider dap=new DaoAuthenticationProvider();
		dap.setUserDetailsService(userDetailsService());
		dap.setPasswordEncoder(passwordEncoder());
		return dap;
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	/*protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.anyRequest().permitAll();
	}*/
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/states/list_by_country/**").hasAnyAuthority("admin", "SalesPersion")
		.antMatchers("/users/**","/settings/**","/countries/**","states/**").hasAuthority("admin")
		.antMatchers("/categories/**").hasAnyAuthority("admin","Editor")
		.antMatchers("/brands/**").hasAnyAuthority("admin","Editor")
		
		.antMatchers("/products/new", "/products/delete/**").hasAnyAuthority("admin", "Editor")
		
		.antMatchers("/products/edit/**", "/products/save", "/products/check_unique")
		.hasAnyAuthority("admin", "Editor", "SalesPersion")
		
		.antMatchers("/products", "/products/", "/products/detail/**", "/products/page/**")
		.hasAnyAuthority("admin", "Editor", "SalesPersion", "Shipper")
		
		.antMatchers("/products/**").hasAnyAuthority("admin","Editor")
		
		
		.antMatchers("/customers/**").hasAnyAuthority("admin","SalesPersion")
		.antMatchers("/shipping/**").hasAnyAuthority("admin","SalesPersion")
		.antMatchers("/orders/**").hasAnyAuthority("admin","SalesPersion","Shipper")
		.antMatchers("/reports/**").hasAnyAuthority("admin","SalesPersion")
		.antMatchers("/articles/**").hasAnyAuthority("admin","Editor")
		.antMatchers("/menus/**").hasAnyAuthority("admin","Editor")
		.anyRequest().authenticated()
		.and()
		.formLogin()
			.loginPage("/login")
			.usernameParameter("email")
			.permitAll()
			.and().logout().permitAll()
			.and().rememberMe()
				.key("abcdfe_12345")
				.tokenValiditySeconds(7*24*60*60)
			;	
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**","/js/**","/webjars/**");
	}
}

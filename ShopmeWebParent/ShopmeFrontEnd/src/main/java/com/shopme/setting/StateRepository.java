package com.shopme.setting;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.State;

public interface StateRepository extends CrudRepository<State, Integer> {
	
	public List<State> findByCountryOrderByNameAsc(Country country);
	
	@Query("SELECT s FROM State s WHERE s.name LIKE %?1%")
	public State findByNameLike(String name);
	
	@Query("SELECT s FROM State s WHERE s.name=?1")
	public State findByName(String name);
}

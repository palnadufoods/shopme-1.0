package com.shopme.admin.setting.city;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.City;
import com.shopme.common.entity.State;

public interface CityRepository extends CrudRepository<City, Integer> {
	
	public List<City> findByStateOrderByNameAsc(State state);
	
}

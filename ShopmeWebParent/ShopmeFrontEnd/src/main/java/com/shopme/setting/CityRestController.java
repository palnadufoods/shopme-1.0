package com.shopme.setting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.common.entity.City;
import com.shopme.common.entity.CityDTO;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.State;
import com.shopme.common.entity.StateDTO;

@RestController
public class CityRestController {

	@Autowired private CityRepository repo;
	
	@GetMapping("/settings/list_cities_by_state/{id}")
	public List<CityDTO> listByState(@PathVariable("id") Integer stateId) {
		
		List<City> listCities = repo.findByStateOrderByNameAsc(new State(stateId));
		List<CityDTO> result = new ArrayList<>();
		
		for (City city : listCities) {
			result.add(new CityDTO(city.getId(), city.getName()));
		}
		
		return result;
	}

}

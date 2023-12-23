package com.shopme.admin.setting.city;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.admin.setting.state.StateRepository;
import com.shopme.common.entity.City;
import com.shopme.common.entity.CityDTO;
import com.shopme.common.entity.State;

@RestController
public class CityRestController {

	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private StateRepository stateRepository;

	@GetMapping("/cities/list_by_state/{id}")
	public List<CityDTO> listByState(@PathVariable("id") Integer stateId) {
		List<City> listCities = cityRepository.findByStateOrderByNameAsc(new State(stateId));

		List<CityDTO> result = new ArrayList<>();

		for (City city : listCities) {
			result.add(new CityDTO(city.getId(), city.getName()));
		}
		return result;

	}

	/*
	 * @GetMapping("/cities/list_by_state/{name}") public List<String>
	 * listByStateName(@PathVariable("name") String stateName) {
	 * 
	 * List<City> listCities =
	 * cityRepository.findByStateOrderByNameAsc(stateRepository.findByName(stateName
	 * ));
	 * 
	 * System.out.println("State Returned Rest :" +
	 * stateRepository.findByName(stateName).toString());
	 * 
	 * List<String> result = new ArrayList<>();
	 * 
	 * for (City city : listCities) { result.add(city.getName()); }
	 * 
	 * System.out.println("Cities : " + result.toString()); return result;
	 * 
	 * }
	 */
	@PostMapping("/cities/save")
	public String save(@RequestBody City city) {

		City savedCity = cityRepository.save(city);
		return String.valueOf(savedCity.getId());

	}

	@DeleteMapping("/cities/delete/{id}")
	public void delete(@PathVariable("id") Integer id) {
		cityRepository.deleteById(id);
	}
}

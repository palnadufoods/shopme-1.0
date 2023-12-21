package com.shopme.address;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Address;
import com.shopme.common.entity.City;
import com.shopme.common.entity.CityDTO;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.State;
import com.shopme.setting.CityRepository;
import com.shopme.setting.StateRepository;

@Service
@Transactional
public class AddressService {

	@Autowired
	private AddressRepository repo;
	
	@Autowired 
	private StateRepository stateRepository;
	
	@Autowired
	private CityRepository cityRepository;

	public List<Address> listAddressBook(Customer customer) {
		return repo.findByCustomer(customer);
	}

	public void save(Address address) {
		repo.save(address);
	}

	public Address get(Integer addressId, Integer customerId) {
		return repo.findByIdAndCustomer(addressId, customerId);
	}

	public void delete(Integer addressId, Integer customerId) {
		repo.deleteByIdAndCustomer(addressId, customerId);
	}

	public void setDefaultAddress(Integer defaultAddressId, Integer customerId) {
		if (defaultAddressId > 0) {
			repo.setDefaultAddress(defaultAddressId);
		}

		repo.setNonDefaultForOthers(defaultAddressId, customerId);
	}

	public Address getDefaultAddress(Customer customer) {
		return repo.findDefaultByCustomer(customer.getId());
	}

	public List<CityDTO> listCitiesByState(String stateStr) {

		State state = stateRepository.findByName(stateStr);
		List<City> cities = cityRepository.findByStateOrderByNameAsc(state);

		List<CityDTO> citiesDto = new ArrayList<>();

		for (City city : cities) {
			citiesDto.add(new CityDTO(city.getId(), city.getName()));
		}
		return citiesDto;
	}
}

package com.vision.springboot.controller;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vision.springboot.data.HouseDTO;
import com.vision.springboot.dao.HousingDAO;

/*
 * REST controller for house collection.
 */
@RestController
@RequestMapping("/api")
public class HousingController {

	// Wire up data-access object which manages the HouseDTO collection.
	@Autowired private HousingDAO housing;
		
	@GetMapping(value="/houses")
	public ResponseEntity<String> getHouses() {
		try {
			// Get and return list of houses.
			List<HouseDTO> result = housing.readAll();
			for (HouseDTO house : result) {
				// Populate location field.
				house.setLocation(buildLocation(house.getId()));
			}
			// Serialize the entire List.
			String json = toJson(result);
			return new ResponseEntity<String>(json, HttpStatus.OK);
		}
		catch(Exception x) {}
		return new ResponseEntity<String>("Server failed retrieving data", HttpStatus.NOT_FOUND);		
	}
	
	@GetMapping(value="/houses/{id}")
	public ResponseEntity<String> getHouse(@PathVariable(value="id") final String id) {
		try {
			if (id != null) {
				HouseDTO house = housing.read(id);
				if (house != null) {
					// Populate location property.
					String location =  ServletUriComponentsBuilder
						.fromCurrentRequest()
						.build()
						.toString();
					house.setLocation(location);
					String json = toJson(house);
					return new ResponseEntity<String>(json, HttpStatus.OK);
				}
			}
		}
		catch(Exception x) {}
		return new ResponseEntity<String>("Server failed retrieving data", HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping(value="/houses/{id}", 
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateHouse(@PathVariable(value="id") final String id,
							  @RequestBody HouseDTO newHouse) {
		try {
			if (id != null) {
				checkInput(newHouse.getOwnerFirstName());
				checkInput(newHouse.getOwnerLastName());
				checkInput(newHouse.getStreet());
				checkInput(newHouse.getCity());
				checkInput(newHouse.getState());
				checkInput(newHouse.getZip());
				checkInput(newHouse.getPropertyType());			
				newHouse.setId(id);
			
				HouseDTO house = housing.update(newHouse);
				if (house != null) {
					// Populate location property.
					String location =  ServletUriComponentsBuilder
						.fromCurrentRequest()
						.build()
						.toString();
					house.setLocation(location);
					String json = toJson(house);
					return new ResponseEntity<String>(json, HttpStatus.OK);
				}			
			}
		}
		catch(Exception x) {}
		return new ResponseEntity<String>("Request input not valid", HttpStatus.NOT_FOUND);			

	}
	
	private void checkInput(String input) throws Exception {
		if (input == null || input == "") {
			throw new Exception("Bad Input");
		}
	}

	// Build a location URI based on request URI + an ID.
	private String buildLocation(String id) {
		String location = 		
 		 	ServletUriComponentsBuilder
 		 	.fromCurrentRequest()
			.pathSegment(id)
 		 	.build()
 		 	.toString();
 		 return location;
	}

	// Build a json string from HouseDTO List, filtering out ID property.
	private String toJson(HouseDTO house) throws Exception {
        SimpleFilterProvider provider = new SimpleFilterProvider();
		provider.addFilter("HouseDTOFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"));	
	    ObjectMapper mapper = new ObjectMapper();
      	mapper.setFilterProvider(provider);
		String result = mapper.writeValueAsString(house);
		return result;	}
	
	// Build a json string from HouseDTO List, filtering out ID property.
	private String toJson(List<HouseDTO> houseList) throws Exception {
        SimpleFilterProvider provider = new SimpleFilterProvider();
		provider.addFilter("HouseDTOFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"));	
	    ObjectMapper mapper = new ObjectMapper();
      	mapper.setFilterProvider(provider);
		String result = mapper.writeValueAsString(houseList);
		return result;
	}


	
}

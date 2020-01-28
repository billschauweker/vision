package com.vision.springboot;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;import com.fasterxml.jackson.databind.ObjectMapper;

import com.vision.springboot.data.HouseDTO;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.CoreMatchers.is; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HousingControllerIT {


	@Autowired
	private MockMvc mvc;
	
	@Test
	public void TestGetAllHouses_HappyPath() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/houses")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andReturn();
		String content = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();				
		List<HouseDTO> houseList = mapper.readValue(content, new TypeReference<List<HouseDTO>>(){});			
	}

	@Test
	public void TestGetAHouse_HappyPath() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/houses/1").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andReturn();
		String content = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();				
		HouseDTO house = mapper.readValue(content, new TypeReference<HouseDTO>(){});			

	}

	@Test
	public void TestPutAHouse_HappyPath() throws Exception {
		// Update house with ID 1, then check the returned payload.
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/houses/1")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andReturn();
		String original = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();				
		HouseDTO originalHouse = mapper.readValue(original, new TypeReference<HouseDTO>(){});			

		HouseDTO testHouse = getTestHouse();
		testHouse.setId(originalHouse.getId());
        SimpleFilterProvider provider = new SimpleFilterProvider();
		provider.addFilter("HouseDTOFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"));	
//	    ObjectMapper mapper = new ObjectMapper();
      	mapper.setFilterProvider(provider);
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(testHouse);
		MvcResult update = mvc.perform(MockMvcRequestBuilders.put("/api/houses/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(json)
			.accept(MediaType.APPLICATION_JSON))
			
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andReturn();
		String updated = update.getResponse().getContentAsString();			
		HouseDTO updatedHouse = mapper.readValue(updated, new TypeReference<HouseDTO>(){});			
		assertThat(updatedHouse, is(testHouse));

	}
	
	private HouseDTO getTestHouse() {
		HouseDTO house = new HouseDTO(
		"99", "Firstname", "Lastname", "Test Street", "Test City", "11111", "TestState", "Test Property Type");
		return house;	
	}
	

}


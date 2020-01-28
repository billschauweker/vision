package com.vision.springboot;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.*; 
import static org.hamcrest.Matchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.vision.springboot.data.HouseDTO;
import com.vision.springboot.dao.HousingDAO;
import com.vision.springboot.controller.HousingController;


@SpringBootTest
@AutoConfigureMockMvc
public class HousingControllerTest {

	@Autowired
	private MockMvc mvc;

	@Mock
	HousingDAO  mockDAO;
	 
	@Test
	public void TestListHouses() {
		System.out.println("TestListHouses - TBD");	
	}
	@Test
	public void TesGetHouse() throws Exception {
			System.out.println("TestGetHouse - TBD");	
	}

	@Test
	public void putHouse() throws Exception {
		System.out.println("TestPutHouse - TBD");	
	}
	

}










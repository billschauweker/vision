package com.vision.springboot.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vision.springboot.data.HouseDTO;

/*
 * HousingDAO - DAO and simulated database manager.
 */

@Component
public class HousingDAOImpl implements HousingDAO {

		Logger logger = LoggerFactory.getLogger(HousingDAOImpl.class);
		
		Map<String, HouseDTO> housing = new HashMap<String, HouseDTO>();
	
	/*
	 * Construct DAO, populating database (hashtable) from CSV resource.
	 */
	public HousingDAOImpl() {

		logger.info("HousingDAO: Begin initialization");
		InputStream in = null;
        try {
        	File file = new File("./src/main/resources/houses.csv");
        	if (file.isFile()) { 
				in = new FileInputStream(file);
        	} else {
				in = getClass().getClassLoader().getResourceAsStream("src/main/resources/houses.csv");
				if (in == null) {
					logger.info("HOUSINGDAO: INPUT STREAM IS NULL");
				} else {
					logger.info("HOUSINGDAO: INPUT STREAM IS OK");
				}
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            
            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreSurroundingSpaces();
            CSVParser csvParser = new CSVParser(reader, csvFileFormat);
            for (CSVRecord csvRecord : csvParser) {
				HouseDTO house = new HouseDTO(
					csvRecord.get("House ID"),
					csvRecord.get("Owner First Name"),
					csvRecord.get("Owner Last Name"),
					csvRecord.get("Street"),
					csvRecord.get("City"),
					csvRecord.get("State"),
					csvRecord.get("Zip"),
					csvRecord.get("Property Type"));
					
				save(house);
            }
		    logger.info("HousingDAO: End initialization");
        }
        
        catch(Exception x) {
		    logger.info("HousingDAO: initialization error" + ExceptionUtils.getStackTrace(x));
        }
	}

    /*
     * Return a list of all house objects.
     */
    public List<HouseDTO> readAll() {
    	ArrayList<HouseDTO> result = new ArrayList<HouseDTO>(housing.values());
    	return result;
    }

	/*
	 * Return the house with the passed-in ID.
	 */
    public HouseDTO read(String id) {
    	if (housing.containsKey(id)) {
    		return housing.get(id);
    	}
	    logger.info("HousingDAO: update: House ID not found:" + id);
    	return null;
    }

	/*
	 * Update the house with the ID and other data passed-in.
	 */
    public HouseDTO update(HouseDTO house) {
    	if (housing.containsKey(house.getId())) {
    		save(house);
    		return house;
    	}
	    logger.info("HousingDAO: update: House ID not found:" + house.getId());
    	return null;
    }
    
     // Save or replace a house
	private void save(HouseDTO house) {
    	housing.put(house.getId(), house);
    }

    

}


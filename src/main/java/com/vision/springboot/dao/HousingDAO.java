package com.vision.springboot.dao;

import java.util.List;
import com.vision.springboot.data.HouseDTO;

/*
 * HousingDAO - DAO and simulated database manager.
 */
public interface HousingDAO {

    /*
     * Return a list of all house objects.
     */
    List<HouseDTO> readAll();

	/*
	 * Return the house with the passed-in ID.
	 */
    HouseDTO read(String id);

	/*
	 * Update the house with the ID and other data passed-in.
	 */
    HouseDTO update(HouseDTO house);

}

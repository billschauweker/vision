package com.vision.springboot.data;

import com.fasterxml.jackson.annotation.JsonFilter;

/*
 * Data Transfer Object for a house.
 * 
 * The filter is to control serialization.
 */
 
@JsonFilter("HouseDTOFilter")
public class HouseDTO {
 	private String id;
 	private String ownerFirstName;
 	private String ownerLastName;
 	private String street;
 	private String city;
 	private String state;
 	private String zip;
 	private String propertyType;
 	private String location;
 	
 	public HouseDTO() {}
 	
 	public HouseDTO(String id, 
 					String ownerFirstName, 
 					String ownerLastName,
 					String street,
 					String city,
 					String state,
 					String zip,
 					String propertyType) {
  		this.id = id;
 		this.ownerFirstName = ownerFirstName;
 		this.ownerLastName = ownerLastName;
 		this.street = street;
 		this.city = city;
 		this.state = state;
 		this.zip = zip;
 		this.propertyType = propertyType;
 	}
 	
 	public void setId(String id) {this.id = id;}
 	public void setOwnerFirstName(String ownerFirstName) { this.ownerFirstName = ownerFirstName;}
 	public void setOwnerLastName(String ownerLastName) { this.ownerLastName = ownerLastName;}
 	public void setStreet(String street) {this.street = street;}
 	public void setCity(String city) {this.city = city;}
 	public void setState(String state) {this.state = state;}
 	public void setZip(String zip) {this.zip = zip;}
 	public void setPropertyType(String propertyType) {this.propertyType = propertyType;}
 	public void setLocation(String location) {this.location = location;}

 	public String getId() {return id;}
 	public String getOwnerFirstName() {return ownerFirstName;}
 	public String getOwnerLastName() {return ownerLastName;}
 	public String getStreet() {return street;}
 	public String getCity() {return city;}
 	public String getState() {return state;}
 	public String getZip() {return zip;}
 	public String getPropertyType() {return propertyType;}
 	public String getLocation() {return location;}
 	
 	/*
 	 * Override equals() to compare two HouseDTOs 
 	 */
    @Override
    public boolean equals(Object o) { 
  
        if (o == this) { 
            return true; 
        } 
  
        if (!(o instanceof HouseDTO)) { 
            return false; 
        } 
          
        HouseDTO other = (HouseDTO)o;
 		return ownerFirstName.equals(other.ownerFirstName)
 		&& ownerLastName.equals(other.ownerLastName)
 		&& street.equals(other.street)
 		&& city.equals(other.city)
 		&& state.equals(other.state)
 		&& zip.equals(other.zip)
 		&& propertyType.equals(other.propertyType);
 	}
 	
 	@Override
 	public String toString() {
		return "\n"
			 + String.format("House ID: %s\n", id)
             + String.format("Owner First Name: %s\n", ownerFirstName)
             + String.format("Owner Last Name:  %s\n", ownerLastName)
             + String.format("Street:           %s\n", street)
             + String.format("City:             %s\n", city)
             + String.format("State:            %s\n", state)
             + String.format("Zip:              %s\n", zip)
             + String.format("propertyType:     %s\n", propertyType)
             + String.format("Location:         %s\n", location);

 	}
 }
 


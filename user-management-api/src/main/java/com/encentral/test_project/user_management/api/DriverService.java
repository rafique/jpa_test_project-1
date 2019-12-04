/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.encentral.test_project.user_management.api;

import java.util.List;

import com.encentral.test_project.commons.exceptions.ResourceNotFound;
import com.encentral.test_project.entities.JpaCar;
import com.encentral.test_project.entities.JpaDriver;

import play.twirl.api.Content;

/**
 *
 * @author James Akinniranye
 */
public interface DriverService 
{
    
    JpaDriver find(String driverId) throws ResourceNotFound;

    JpaDriver create(JpaDriver driverDO) ;

    void delete(String driverId) throws ResourceNotFound;

    /**
     * Assigns a car to a driver.
     * 
     * @param driverId
     * @param carId
     * @return
     * @throws ResourceNotFound 
     * @throws CarAlreadyInUseException 
     */
	JpaDriver assignCar(String driverId, String carId) throws ResourceNotFound, CarAlreadyInUseException;
	
	
	/**
	 * Finds all drivers. Only for test purpose.
	 * 
	 * @return
	 */
	List<JpaDriver> findAll();

	
	/**
	 * Finds driver by matching properties.
	 * 
	 * @param username
	 * @param online_status
	 * @param license_plate
	 * @param rating
	 * @return
	 */
	List<JpaDriver>  findDriver(String username, String online_status, String license_plate, Integer rating);

}

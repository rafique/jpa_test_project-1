/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.encentral.test_project.user_management.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import com.encentral.test_project.commons.exceptions.ResourceNotFound;
import com.encentral.test_project.entities.JpaCar;
import com.encentral.test_project.entities.JpaDriver;
import com.encentral.test_project.user_management.api.CarAlreadyInUseException;
import com.encentral.test_project.user_management.api.CarService;
import com.encentral.test_project.user_management.api.DriverService;

import play.db.jpa.JPAApi;

/**
 *
 * @author James Akinniranye
 */
public class DefaultDriverService implements DriverService 
{

    @Inject
    JPAApi jPAApi;
    
    @Inject 
    CarService carService;
    
    @Override
    public List<JpaDriver> findAll() {
    	return jPAApi.em().createQuery("SELECT d FROM JpaDriver d", JpaDriver.class).getResultList();
    }


    @Override
    public JpaDriver find(String driverId) throws ResourceNotFound 
	{
        JpaDriver driver = jPAApi.em().find(JpaDriver.class, driverId);
        if (driver == null) 
		{
            throw new ResourceNotFound(String.format("Driver with id %s not found", driverId));
        }
        return driver;
    }

    @Override
    public JpaDriver create(JpaDriver driverDO) 
	{
        driverDO.setDriverId(java.util.UUID.randomUUID().toString());
        driverDO.setDateCreated(new Date());
        jPAApi.em().persist(driverDO);
        return driverDO;
    }

    @Override
    public void delete(String driverId) throws ResourceNotFound 
	{
        jPAApi.em().detach(find(driverId));
    }
    
    @Override
    public JpaDriver assignCar(String driverId, String carId) throws ResourceNotFound, CarAlreadyInUseException {
    	
    	JpaDriver driver = find(driverId);
    	JpaCar car = carService.find(carId);
    	
    	if (car.getDriver() != null) {
    		throw new CarAlreadyInUseException("the car "+carId +" is already assigned.");
    	}
    	
    	driver.setCar(car);
    	return driver;
    }

    @Override
    public List<JpaDriver> findDriver(String username, String onlineStatus, String license_plate, Integer rating) {
    	
    	String query = "SELECT d FROM JpaDriver d where 1=1";
    	List<Object> params = new LinkedList<>();
    	
    	if (username != null) {
    		params.add(username);
    		query += " and d.username = ?" + params.size();
    	}
    	if (onlineStatus != null) {
    		params.add(onlineStatus);
    		query += " and d.onlineStatus = ? " + + params.size();
    	}
    	
    	//search on username until license plate is added
    	if (license_plate != null) {
    		params.add(license_plate);
    		query += " and (d.car is not null and d.car.username = ?"+params.size() +")";
    	}
    	
    	//search on password until rating is added
    	if (rating != null) {
    		params.add(rating);
    		query += " and (d.car is not null and d.car.password = ?"+ params.size()+ ")";
    	}
    	
		TypedQuery<JpaDriver> createQuery = jPAApi.em().createQuery(query, JpaDriver.class);
		
		for (int i = 0; i < params.size(); i++) {
			createQuery.setParameter(i+1, params.get(i));
		}
		return createQuery.getResultList();
    	
    }
}

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

import org.apache.commons.lang3.StringUtils;

import com.encentral.test_project.commons.exceptions.ResourceNotFound;
import com.encentral.test_project.entities.JpaCar;
import com.encentral.test_project.entities.JpaDriver;
import com.encentral.test_project.user_management.api.CarAlreadyInUseException;
import com.encentral.test_project.user_management.api.CarNotInUseException;
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
    public JpaDriver unAssignCar(String driverId, String carId) throws ResourceNotFound, CarNotInUseException {
    	
    	JpaDriver driver = find(driverId);
    	JpaCar car = carService.find(carId);
    	
    	if (!car.equals(driver.getCar())) {
    		throw new CarNotInUseException("the car "+ carId +" is not assigned to driver " + driver.getDriverId());
    	}
    	
    	driver.setCar(null);
    	return driver;
    }

    @Override
    public List<JpaDriver> findDriver(String username, String onlineStatus, String licensePlate, Integer rating) {
    	
    	String query = "SELECT d FROM JpaDriver d where 1=1";
    	List<Object> params = new LinkedList<>();
    	
    	if (StringUtils.isNotBlank(username)) {
    		params.add(username);
    		query += " and d.username = ?" + params.size();
    	}
    	if (StringUtils.isNotBlank(onlineStatus)) {
    		params.add(onlineStatus);
    		query += " and d.onlineStatus = ?" + params.size();
    	}
    	
    	if (StringUtils.isNotBlank(licensePlate)) {
    		params.add(licensePlate);
    		query += " and (d.car is not null and d.car.licensePlate = ?" + params.size() + ")";
    	}
    	
    	if (rating != null) {
    		params.add(rating);
    		query += " and (d.car is not null and d.car.rating = ?"+ params.size()+ ")";
    	}
    	
		TypedQuery<JpaDriver> createQuery = jPAApi.em().createQuery(query, JpaDriver.class);
		
		for (int i = 0; i < params.size(); i++) {
			createQuery.setParameter(i+1, params.get(i));
		}
		return createQuery.getResultList();
    	
    }
}

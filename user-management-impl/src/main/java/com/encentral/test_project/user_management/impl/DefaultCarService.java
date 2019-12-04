/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.encentral.test_project.user_management.impl;

import com.encentral.test_project.commons.exceptions.ResourceNotFound;
import com.encentral.test_project.entities.JpaCar;
import com.encentral.test_project.user_management.api.CarService;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import play.db.jpa.JPAApi;

/**
 *
 * @author Itoro Ikon
 */
public class DefaultCarService implements CarService 
{

    @Inject
    JPAApi jPAApi;
    
    @Override
    public List<JpaCar> findAll() {
    	return jPAApi.em().createQuery("SELECT c FROM JpaCar c", JpaCar.class).getResultList();
    }

    @Override
    public JpaCar find(String carId) throws ResourceNotFound 
	{
        JpaCar car = jPAApi.em().find(JpaCar.class, carId);
        if (car == null) 
		{
            throw new ResourceNotFound(String.format("Car with id %s not found", carId));
        }
        return car;
    }

    @Override
    public JpaCar create(JpaCar carDO) 
	{
        carDO.setCarId(java.util.UUID.randomUUID().toString());
        jPAApi.em().persist(carDO);
        return carDO;
    }

    @Override
    public void delete(String carId) throws ResourceNotFound 
	{
		jPAApi.em().remove(find(carId));
    }

	@Override
    public JpaCar update(JpaCar carDO) throws ResourceNotFound 
	{
		JpaCar car = find(carDO.getCarId());
		car.setLicensePlate(carDO.getLicensePlate());
		car.setSeatCount(carDO.getSeatCount());
		car.setConvertible(carDO.getConvertible());
		car.setRating(carDO.getRating());
		car.setManufacturer(carDO.getManufacturer());
		/* car.setEngineType(carDO.getEngineType()); */
		
        return car;
    }
}

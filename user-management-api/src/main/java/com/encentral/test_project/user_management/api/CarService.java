/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.encentral.test_project.user_management.api;

import java.util.List;

import com.encentral.test_project.commons.exceptions.ResourceNotFound;
import com.encentral.test_project.entities.JpaCar;

/**
 *
 * @author Itoro Ikon
 */
public interface CarService 
{
    
	List<JpaCar> findAll();
	
    JpaCar find(String carId) throws ResourceNotFound;

    JpaCar create(JpaCar carDO) ;

    void delete(String carId) throws ResourceNotFound;
	
	JpaCar update(JpaCar carDO) throws ResourceNotFound;
	
}

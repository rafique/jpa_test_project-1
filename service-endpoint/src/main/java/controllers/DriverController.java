/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.encentral.test_project.commons.exceptions.ResourceNotFound;
import com.encentral.test_project.commons.models.CarDTO;
import com.encentral.test_project.commons.models.CarMapper;
import com.encentral.test_project.commons.models.DriverDTO;
import com.encentral.test_project.commons.models.DriverMapper;
import com.encentral.test_project.commons.util.MyObjectMapper;
import com.encentral.test_project.entities.JpaDriver;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.encentral.test_project.user_management.api.CarAlreadyInUseException;
import com.encentral.test_project.user_management.api.DriverService;
import javax.inject.Inject;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.*;
import static play.mvc.Results.badRequest;

/**
 *
 * @author poseidon
 */
@Api(value = "Driver")
@Transactional
public class DriverController extends Controller 
{

    @Inject
    FormFactory formFactory;

    @Inject
    MyObjectMapper objectMapper;

    @Inject
    DriverService driverService;
    
    
    @ApiOperation(value = "Assigns a Car to a Driver", notes = "", httpMethod = "GET")
    @ApiResponses
	(
            value = {
						@ApiResponse(code = 200, message = "Done", response = DriverDTO.class)
					}
    )
    public Result assignCar(String driverId, String carId) 
	{
        try 
		{
            return ok(Json.toJson(DriverMapper.jpaDriverToDriverDTO(driverService.assignCar(driverId, carId))));
            
        } 
		catch (ResourceNotFound ex) 
		{
            return notFound(ex.getMessage());
            
        } catch (CarAlreadyInUseException e) {
        	
			return badRequest(e.getMessage());
		}
    }
    
    @ApiOperation(value = "Find Drivers", notes = "Find Driver endpoint", httpMethod = "GET")
    @ApiResponses
	(
            value = {
						@ApiResponse(code = 200, message = "Done", response = CarDTO.class)
					}
    )
    
    public Result findDriver(@ApiParam(required = false) String username, String online_status, String license_plate, Integer rating) 
	{
           return ok(Json.toJson(driverService.findDriver(username, online_status, license_plate, rating).stream().map(DriverMapper::jpaDriverToDriverDTO)));
    }
    

    @ApiOperation(value = "Get Driver", notes = "", httpMethod = "GET")
    @ApiResponses
	(
            value = {
						@ApiResponse(code = 200, message = "Done", response = DriverDTO.class)
					}
    )
    public Result getDriver(String driverId) 
	{
        try 
		{
            return ok(Json.toJson(DriverMapper.jpaDriverToDriverDTO(driverService.find(driverId))));
        } 
		catch (ResourceNotFound ex) 
		{
            return notFound(ex.getMessage());
        }
    }

    @ApiOperation(value = "Create a Driver", notes = "", httpMethod = "POST")
    @ApiResponses
	(
            value = {
						@ApiResponse(code = 200, message = "Done")
					}
    )
    @ApiImplicitParams
	(
		{
			@ApiImplicitParam
			(
				name = "Application",
				dataType = "com.encentral.test_project.commons.models.DriverDTO",
				required = true,
				paramType = "body",
				value = "Application"
			)
		}
    )
    public Result createDriver() 
	{
        Form<DriverDTO> bindFromRequest = formFactory.form(DriverDTO.class).bindFromRequest();
        if (bindFromRequest.hasErrors()) 
		{
            return badRequest(bindFromRequest.errorsAsJson());

        }
        JpaDriver create = driverService.create(DriverMapper.driverDTotoJpaDriver(bindFromRequest.get()));

        return ok(Json.toJson(DriverMapper.jpaDriverToDriverDTO(create)));
    }

    @ApiOperation(value = "Delete Driver", notes = "", httpMethod = "DELETE")
    @ApiResponses
	(
            value = {
						@ApiResponse(code = 200, message = "Done")
					}
    )
    public Result deleteDriver(String driverId) 
	{
        try 
		{
            driverService.delete(driverId);
            return noContent();
        } 
		catch (ResourceNotFound ex) 
		{
            return notFound(ex.getMessage());
        }
    }
}

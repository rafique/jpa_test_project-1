/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.encentral.test_project.commons.exceptions.ResourceNotFound;
import com.encentral.test_project.commons.models.CarDTO;
import com.encentral.test_project.commons.models.CarMapper;
import com.encentral.test_project.commons.util.MyObjectMapper;
import com.encentral.test_project.entities.JpaCar;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.encentral.test_project.user_management.api.CarService;
import javax.inject.Inject;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.*;
import static play.mvc.Results.badRequest;

/**
 *
 * @author itoroikon
 */
@Api(value = "Car", description = "CRUD operations with car")
@Transactional
public class CarController extends Controller 
{

    @Inject
    FormFactory formFactory;

    @Inject
    MyObjectMapper objectMapper;

    @Inject
    CarService carService;

    @ApiOperation(value = "Find Cars", notes = "Find Car endpoint", httpMethod = "GET")
    @ApiResponses
	(
            value = {
						@ApiResponse(code = 200, message = "Done", response = CarDTO.class)
					}
    )
    
    public Result findCar() 
	{
           return ok(Json.toJson(carService.findAll().stream().map(CarMapper::jpaCarToCarDTO)));
    }
    
    @ApiOperation(value = "Get Car", notes = "Get Car endpoint", httpMethod = "GET")
    @ApiResponses
	(
            value = {
						@ApiResponse(code = 200, message = "Done", response = CarDTO.class)
					}
    )
    public Result getCar(String carId) 
	{
        try 
		{
            return ok(Json.toJson(CarMapper.jpaCarToCarDTO(carService.find(carId))));
        } 
		catch (ResourceNotFound ex) 
		{
            return notFound(ex.getMessage());
        }
    }

    @ApiOperation(value = "Create a Car", notes = "", httpMethod = "POST")
    @ApiResponses
	(
            value = {
						@ApiResponse(code = 200, message = "Car created")
					}
    )
    @ApiImplicitParams
	(
		{
			@ApiImplicitParam
			(
				name = "Application",
				dataType = "com.encentral.test_project.commons.models.CarDTO",
				required = true,
				paramType = "body",
				value = "Application"
			)
		}
    )
    public Result createCar() 
	{
        Form<CarDTO> bindFromRequest = formFactory.form(CarDTO.class).bindFromRequest();
        if (bindFromRequest.hasErrors()) 
		{
            return badRequest(bindFromRequest.errorsAsJson());

        }
        JpaCar create = carService.create(CarMapper.carDTotoJpaCar(bindFromRequest.get()));

        return ok(Json.toJson(CarMapper.jpaCarToCarDTO(create)));
    }

    @ApiOperation(value = "Delete Car", notes = "", httpMethod = "DELETE")
    @ApiResponses
	(
            value = {
						@ApiResponse(code = 200, message = "Car deleted")
					}
    )
    public Result deleteCar(String carId) 
	{
        try 
		{
            carService.delete(carId);
            return noContent();
        } 
		catch (ResourceNotFound ex) 
		{
            return notFound(ex.getMessage());
        }
    }
    
	@ApiOperation(value = "Update Car", notes = "Car Update endpoint", httpMethod = "PUT")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Updated") })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Application", dataType = "com.encentral.test_project.commons.models.CarDTO", required = true, paramType = "body", value = "Application") })
	public Result updateCar() {
		Form<CarDTO> bindFromRequest = formFactory.form(CarDTO.class).bindFromRequest();
		if (bindFromRequest.hasErrors()) {
			return badRequest(bindFromRequest.errorsAsJson());

		}
		try {
			JpaCar update = carService.update(CarMapper.carDTotoJpaCar(bindFromRequest.get()));
			return ok(Json.toJson(CarMapper.jpaCarToCarDTO(update)));
		} catch (ResourceNotFound ex) {
			return notFound(ex.getMessage());
		}
	}


}

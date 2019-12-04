package com.encentral.test_project.commons.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.encentral.test_project.util.EngineType;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO 
{

    private String carId;

    @Size(min = 1, max = 25)
    @NotNull(message = "License plate can not be null!")
    private String licensePlate;

    @NotNull(message = "Seat count can not be null!")
    private int seatCount;

    @NotNull(message = "convertible field can not be null!")
    private boolean convertible;

    private int rating;
	
	private EngineType engineType;
	
	private String manufacturer;

    public String getCarId() 
	{
        return carId;
    }

    public void setCarId(String carId) 
	{
        this.carId = carId;
    }

    public String getLicensePlate() 
	{
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) 
	{
        this.licensePlate = licensePlate;
    }

    public int getSeatCount() 
	{
        return seatCount;
    }

    public void setSeatCount(int seatCount) 
	{
        this.seatCount = seatCount;
    }

    public boolean getConvertible() 
	{
        return convertible;
    }
	
	public void setConvertible(boolean convertible) 
	{
        this.convertible = convertible;
    }

    public int getRating() 
	{
        return rating;
    }

    public void setRating(int rating) 
	{
        this.rating = rating;
    }

    public EngineType getEngineType() 
	{
        return engineType;
    }

    public void setEngineType(EngineType engineType) 
	{
        this.engineType = engineType;
    }
	
	public String getManufacturer() 
	{
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) 
	{
        this.manufacturer = manufacturer;
    }

}

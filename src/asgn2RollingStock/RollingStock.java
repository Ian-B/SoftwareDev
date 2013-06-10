package asgn2RollingStock;

import asgn2Exceptions.TrainException;

/**
 * 
 * 
 * @author Ian
 */

public abstract class RollingStock {
	private Integer weight;
	private Integer invalidWeight = 0;
	
	// Constructor
	public RollingStock(Integer grossWeight) throws TrainException {
		
		if (grossWeight <= invalidWeight) {
			throw new TrainException("Weight is less then or equal to zero");
		}		
		weight = grossWeight;	
	}
	
	public Integer getGrossWeight() {	
		return weight;
	}
	
	// base toString method to be implmented by the 3 cars. 
	public abstract String toString();
}

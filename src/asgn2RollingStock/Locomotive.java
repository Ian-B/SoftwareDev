package asgn2RollingStock;

import asgn2Exceptions.TrainException;

/**
 * 
 * @author Ian
 *
 */
public class Locomotive extends RollingStock {
	
	private String steam = "S";
	private String diesel ="D";
	private String electric ="E";
	private Integer maxPower = 9;
	private Integer minPower = 0;
	
	private Integer power;
	private String engineType;
	private String description;	

	public Locomotive(Integer grossWeight,
            String classification) throws TrainException {
		super (grossWeight);
		
		char tempArray[] = new char[2];
		
		Integer powerLoc = 0;
		Integer engineLoc = 1;
		Integer charEnd = 2;
		Integer tempPower;
		Integer powerFactor = 100;
		String tempString;
		
		// breaking up classification code to individual char
		// then to an integer
		classification.getChars(powerLoc, charEnd, tempArray, 0);
		tempString = Character.toString(tempArray[powerLoc]);
		tempPower = Integer.valueOf(tempString);
		
		// test to ensure power is invalid
		if (tempPower <= minPower || tempPower > maxPower) {
			throw new TrainException("Power is greater then 9 or less then or" +
					" equal to zero");
		}
		
		// power is valid
		else {
			power = tempPower * powerFactor;
			//System.out.print("\n power is " + power);
		}
		
		// string becomes the engine code
		tempString = Character.toString(tempArray[engineLoc]);
		
		// engine code is valid
		if (tempString.equals(steam) || tempString.equals(diesel) || 
				tempString.equals(electric)) {
			engineType = tempString;
		}
		
		// engine code is invalid
		else {
			throw new TrainException("Engine Code is not S, D or E");
		}
		
		description = classification;
	}	
	
	public Integer power() {
		return power;
	}

	@Override
	public String toString() {
		String template = "Locomotive (";
		String output;
		output = template + (description + ")");
		return output;
	}
}

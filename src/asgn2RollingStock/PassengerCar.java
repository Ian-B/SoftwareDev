package asgn2RollingStock;

import asgn2Exceptions.TrainException;

/**
 * 
 * 
 * @author Ian
 */
 
public class PassengerCar extends RollingStock {
	
	private Integer seatCapacity;
	private Integer minSeatNum = 0;
	private Integer passengersOnBoard = 0;
	
	public PassengerCar(Integer grossWeight,
            Integer numberOfSeats) throws TrainException {
		super(grossWeight);		
		
		// Test to see if numberOfSeats is negative
		// Negative seat
		if (numberOfSeats <= minSeatNum) {
			throw new TrainException("Error: Seat Capacity is a negative number");
		}
		//positve seatNumber
		else {
			seatCapacity = numberOfSeats;
		}
	}

	public Integer board(Integer newPassengers) throws TrainException {
		Integer passengersLeft = 0;
		Integer negativeCheck = 0;
		
		// negative newPassengers detected
		if (newPassengers < negativeCheck) {
			throw new TrainException("Error: boarding Passengers is "
					+ "negative");
		}
		
		passengersOnBoard = passengersOnBoard + newPassengers;
		
		// checks if new total is greater then capacity
		// calcs passengers remaining
		if (passengersOnBoard >= seatCapacity) {
			passengersLeft = passengersOnBoard - seatCapacity;
			passengersOnBoard = seatCapacity;
		}
		return passengersLeft;
	}
	
	public void alight(Integer departingPassengers) throws TrainException {
		Integer negativeCheck = 0;
		
		// negative DepartingPassengers detected
		if (departingPassengers < negativeCheck) {
			throw new TrainException("Error: Departing Passengers is "
					+ "negative");
		}
		// departing Passengers greater then num passengers on board
		if (departingPassengers > passengersOnBoard) {
			throw new TrainException("Error: Departing Passengers"
					+ " number is greater then Passenger on board");
		}
		
		passengersOnBoard -= departingPassengers;
	}
	
	public Integer numberOnBoard() {
		return passengersOnBoard;
	}
	
	public Integer numberOfSeats() {
		return seatCapacity;
	}
	
	@Override
	public String toString() {
		String template = "Passenger (";
		String output;
		output = template + (passengersOnBoard + "/");
		output += (seatCapacity + ")");
		// TODO Auto-generated method stub
		return output;
	}
}

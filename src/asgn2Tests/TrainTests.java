/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import asgn2Exceptions.TrainException;
import asgn2RollingStock.FreightCar;
import asgn2RollingStock.Locomotive;
import asgn2RollingStock.PassengerCar;
import asgn2Train.DepartingTrain;

/**
 * @author Ian Bulcock, N6369375
 *
 */
public class TrainTests {
	
	//test data
	Integer locomotiveWeight = 400;
	Integer passengerWeight = 200;
	Integer numberOfSeats = 300;
	Integer newPassengers = 150;
	String classification = "9S"; //1-9 E,D,S
	String freightClassification = "G"; //G,R,D
	Integer freightWeight = 200;

	/**
	 * Test method for {@link asgn2Train.DepartingTrain#DepartingTrain()}.
	 */
	@Test
	public void testDepartingTrainEmptyConstructor() {
		//check if train was created
		DepartingTrain newTrain = new DepartingTrain();
		Assert.assertTrue(newTrain != null);		
	}

	/**
	 * Test method for {@link asgn2Train.DepartingTrain#firstCarriage()}.
	 * @throws TrainException 
	 */
	@Test
	public void testFirstCarriageAndAddCarriage() throws TrainException {
		//first must be a locomotive
		DepartingTrain newTrain = new DepartingTrain();	
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		newTrain.addCarriage(firstCar);	
		assertTrue(newTrain.firstCarriage() instanceof Locomotive);
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#firstCarriage()}.
	 */
	@Test
	public void testFirstCarriageNull() {
		//first must be a locomotive, empty return null
		DepartingTrain newTrain = new DepartingTrain();		
		assertEquals(null,newTrain.firstCarriage());		
	}	

	/**
	 * Test method for {@link asgn2Train.DepartingTrain#nextCarriage()}.
	 * @throws TrainException 
	 */
	@Test
	public void testNextCarriageFirstCall() throws TrainException {
		//first call return first carriage
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		newTrain.addCarriage(firstCar);
		assertTrue(newTrain.nextCarriage() instanceof Locomotive);
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#nextCarriage()}.
	 * @throws TrainException 
	 */
	@Test
	public void testNextCarriageSecondCall() throws TrainException {
		//second call return second carriage
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		PassengerCar secondCar = new PassengerCar(passengerWeight, numberOfSeats);		
		newTrain.addCarriage(firstCar);
		newTrain.addCarriage(secondCar);
		newTrain.nextCarriage(); //first
		assertTrue(newTrain.nextCarriage() instanceof PassengerCar);
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#nextCarriage()}.
	 */
	@Test
	public void testNextCarriageFirstCallNull() {
		//first call no carriage return null
		DepartingTrain newTrain = new DepartingTrain();
		assertEquals(null,newTrain.nextCarriage());
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#nextCarriage()}.
	 * @throws TrainException 
	 */
	@Test
	public void testNextCarriageNullAfterFirst() throws TrainException {
		//after first call no carriage return null
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		newTrain.addCarriage(firstCar);
		newTrain.nextCarriage();		
		assertEquals(null,newTrain.nextCarriage());
	}

	/**
	 * Test method for {@link asgn2Train.DepartingTrain#numberOnBoard()}.
	 * @throws TrainException 
	 */
	@Test
	public void testNumberOnBoardWithOneCar() throws TrainException {
		//return the correct number of passengers on board
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		PassengerCar secondCar = new PassengerCar(passengerWeight, numberOfSeats);		
		newTrain.addCarriage(firstCar);
		newTrain.addCarriage(secondCar);
		newTrain.board(newPassengers);		
		Assert.assertEquals(newPassengers, newTrain.numberOnBoard());		
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#numberOnBoard()}.
	 * @throws TrainException 
	 */
	@Test
	public void testNumberOnBoardWithMultipleCarsMultipleStops() throws TrainException {
		//test multiple adding of passengers to cars
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		PassengerCar secondCar = new PassengerCar(locomotiveWeight, numberOfSeats); //300
		PassengerCar thirdCar = new PassengerCar(passengerWeight, numberOfSeats); //300 = 600
		newTrain.addCarriage(firstCar);
		newTrain.addCarriage(secondCar);
		newTrain.addCarriage(thirdCar);
		newTrain.board(newPassengers); //150
		newTrain.board(newPassengers * 2); //300 = 450 passengers(more than cap of 1 car)
		Integer passengers = (newPassengers * 3);
		assertEquals(passengers, newTrain.numberOnBoard());		
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#numberOnBoard()}.
	 * @throws TrainException 
	 */
	@Test
	public void testNumberOnBoardWithNoPassengers() throws TrainException {
		//check that passenger carriages are empty when first created
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		PassengerCar secondCar = new PassengerCar(passengerWeight, numberOfSeats);		
		newTrain.addCarriage(firstCar);
		newTrain.addCarriage(secondCar);	
		Integer passengers = 0;
		Assert.assertEquals(passengers, newTrain.numberOnBoard());		
	}	
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#numberOfSeats()}.
	 * @throws TrainException 
	 */
	@Test
	public void testNumberOfSeatsWithOneCar() throws TrainException {
		//check number of seats = carriage capacity
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		PassengerCar secondCar = new PassengerCar(passengerWeight, numberOfSeats);		
		newTrain.addCarriage(firstCar);
		newTrain.addCarriage(secondCar);
		Integer seats = newTrain.numberOfSeats();		
		Assert.assertEquals(seats, newTrain.numberOfSeats());		
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#numberOfSeats()}.
	 * @throws TrainException 
	 */
	@Test
	public void testNumberOfSeatsWithMultipleCars() throws TrainException {
		//check total seats/capacity for multiple passenger trains
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		PassengerCar secondCar = new PassengerCar(passengerWeight, numberOfSeats); //300
		PassengerCar thirdCar = new PassengerCar(passengerWeight, numberOfSeats); //300 = 600
		newTrain.addCarriage(firstCar);
		newTrain.addCarriage(secondCar);
		newTrain.addCarriage(thirdCar);
		Integer seats = (numberOfSeats * 2);
		assertEquals(seats, newTrain.numberOfSeats());		
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#numberOfSeats()}.
	 * @throws TrainException 
	 */
	@Test
	public void testNumberOfSeatsWithNoPassengerCar() throws TrainException {
		//check seats if no passenger cars attached
		DepartingTrain newTrain = new DepartingTrain();	
		Integer seats = 0;
		assertEquals(seats, newTrain.numberOfSeats());	
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#board(java.lang.Integer)}.
	 * @throws TrainException 
	 */
	@Test
	public void testBoardNotFull() throws TrainException {
		//check no excess passengers
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		PassengerCar secondCar = new PassengerCar(passengerWeight, numberOfSeats); //300
		newTrain.addCarriage(firstCar);
		newTrain.addCarriage(secondCar);
		Integer returned = 0;
		assertEquals(returned, newTrain.board(newPassengers)); //add 150, return 0
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#board(java.lang.Integer)}.
	 * @throws TrainException 
	 */
	@Test
	public void testBoardAtFull() throws TrainException {
		//check boundary value for adding passenger
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		PassengerCar secondCar = new PassengerCar(passengerWeight, numberOfSeats); //300
		newTrain.addCarriage(firstCar);
		newTrain.addCarriage(secondCar);
		Integer returned = 0;
		assertEquals(returned, newTrain.board(numberOfSeats)); //add 300, return 0
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#board(java.lang.Integer)}.
	 * @throws TrainException 
	 */
	@Test
	public void testBoardOverFull() throws TrainException {
		//check excess passengers
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		PassengerCar secondCar = new PassengerCar(passengerWeight, numberOfSeats); //300
		newTrain.addCarriage(firstCar);
		newTrain.addCarriage(secondCar);
		Integer returned = 1;
		assertEquals(returned, newTrain.board(++numberOfSeats)); //add 301, return 1
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#board(java.lang.Integer)}.
	 * @throws TrainException 
	 */
	@Test (expected = TrainException.class)
	public void testBoardException() throws TrainException {
		//check for exception negative boarding passengers
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		PassengerCar secondCar = new PassengerCar(passengerWeight, numberOfSeats); //300
		newTrain.addCarriage(firstCar);
		newTrain.addCarriage(secondCar);
		newTrain.board(-newPassengers); //negative
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#board(java.lang.Integer)}.
	 * @throws TrainException 
	 */
	@Test (expected = TrainException.class)
	public void testBoardExceptionNoPassengerTrain() throws TrainException {
		//check exception no pasenger train for adding pasengers
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		newTrain.addCarriage(firstCar);
		newTrain.board(newPassengers); //exception no passenger train
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#trainCanMove()}.
	 * @throws TrainException 
	 */
	@Test
	public void testTrainCanMoveNoCarriages() throws TrainException {
		//check if train can move with a locomotive
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		newTrain.addCarriage(firstCar);		
		assertTrue(newTrain.trainCanMove()); //train can pull itself	
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#trainCanMove()}.
	 * @throws TrainException 
	 */
	@Test
	public void testTrainCanMove() throws TrainException {
		//test can move with passenger train
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		PassengerCar secondCar = new PassengerCar(passengerWeight, numberOfSeats);
		newTrain.addCarriage(firstCar);	
		newTrain.addCarriage(secondCar);		
		assertTrue(newTrain.trainCanMove());
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#trainCanMove()}.
	 * @throws TrainException 
	 */
	@Test
	public void testTrainCanMoveToHeavy() throws TrainException {
		//test train cannot move too many carriages
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification); //400
		PassengerCar secondCar = new PassengerCar(passengerWeight, numberOfSeats); //200
		PassengerCar thirdCar = new PassengerCar(passengerWeight, numberOfSeats); //200
		PassengerCar fourthCar = new PassengerCar(passengerWeight, numberOfSeats); //200 = 1000
		newTrain.addCarriage(firstCar);	
		newTrain.addCarriage(secondCar);
		newTrain.addCarriage(thirdCar);
		newTrain.addCarriage(fourthCar);
		assertFalse(newTrain.trainCanMove());
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#trainCanMove()}.
	 * @throws TrainException 
	 */
	@Test
	public void testTrainCanMoveBoundary() throws TrainException {
		//test the train can just pull the max weight
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification); //400
		PassengerCar secondCar = new PassengerCar(passengerWeight, numberOfSeats); //200
		PassengerCar thirdCar = new PassengerCar(passengerWeight, numberOfSeats); //200
		PassengerCar fourthCar = new PassengerCar((passengerWeight / 2), numberOfSeats); //100 = 900
		newTrain.addCarriage(firstCar);	
		newTrain.addCarriage(secondCar);
		newTrain.addCarriage(thirdCar);
		newTrain.addCarriage(fourthCar);
		assertTrue(newTrain.trainCanMove());
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#removeCarriage()}.
	 * @throws TrainException 
	 */
	@Test
	public void testRemoveCarriageLocomotive() throws TrainException {
		//test removing the locomotive from the train
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		newTrain.addCarriage(firstCar);
		newTrain.removeCarriage();		
		assertEquals(null, newTrain.firstCarriage());	
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#removeCarriage()}.
	 * @throws TrainException 
	 */
	@Test
	public void testRemoveCarriageMultipleCars() throws TrainException {
		//test removing more than 1 carriage
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		PassengerCar secondCar = new PassengerCar(passengerWeight, numberOfSeats);
		newTrain.addCarriage(firstCar);
		newTrain.addCarriage(secondCar);
		newTrain.removeCarriage();	
		newTrain.nextCarriage();
		assertEquals(null, newTrain.nextCarriage());
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#removeCarriage()}.
	 * @throws TrainException 
	 */
	@Test (expected = TrainException.class)
	public void testRemoveCarriageExceptionOne() throws TrainException {
		//test exception trying to remove a carriage that doesn't exist
		DepartingTrain newTrain = new DepartingTrain();
		newTrain.removeCarriage(); //throw exception
	}	
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#removeCarriage()}.
	 * @throws TrainException 
	 */
	@Test (expected = TrainException.class)
	public void testRemoveCarriageExceptionTwo() throws TrainException {
		// cannot remove carriage while passengers are aboard (no shunting)
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		PassengerCar secondCar = new PassengerCar(passengerWeight, numberOfSeats);
		newTrain.addCarriage(firstCar);	
		newTrain.addCarriage(secondCar);
		newTrain.board(newPassengers);
		newTrain.removeCarriage(); //throws exception(has passengers)	
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#addCarriage(asgn2RollingStock.RollingStock)}.
	 * @throws TrainException 
	 */
	@Test (expected = TrainException.class)
	public void testAddCarriageExceptionPassengers() throws TrainException {
		//cannot add a carriage with passengers on board (no shunting)
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		PassengerCar secondCar = new PassengerCar(passengerWeight, numberOfSeats);		
		newTrain.addCarriage(firstCar);
		newTrain.addCarriage(secondCar);
		newTrain.board(newPassengers);
		newTrain.removeCarriage(); //exception passengers on board
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#addCarriage(asgn2RollingStock.RollingStock)}.
	 * @throws TrainException 
	 */
	@Test (expected = TrainException.class)
	public void testAddCarriageExceptionFreightNotLast() throws TrainException {
		//check bad configuration freight train before passenger
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		FreightCar secondCar = new FreightCar(freightWeight, freightClassification);
		PassengerCar thirdCar = new PassengerCar(passengerWeight, numberOfSeats);		
		newTrain.addCarriage(firstCar);
		newTrain.addCarriage(secondCar);
		newTrain.addCarriage(thirdCar); //exception bad configuration
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#addCarriage(asgn2RollingStock.RollingStock)}.
	 * @throws TrainException 
	 */
	@Test (expected = TrainException.class)
	public void testAddCarriageExceptionLocoNotFirst() throws TrainException {
		//exception locomotive not first
		DepartingTrain newTrain = new DepartingTrain();
		FreightCar firstCar = new FreightCar(freightWeight, freightClassification);		
		newTrain.addCarriage(firstCar); //exception bad configuration
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#addCarriage(asgn2RollingStock.RollingStock)}.
	 * @throws TrainException 
	 */
	@Test (expected = TrainException.class)
	public void testAddCarriageExceptionTwoLoco() throws TrainException {
		//exception cannot have to locomotives
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		Locomotive secondCar = new Locomotive(locomotiveWeight, classification);
		newTrain.addCarriage(firstCar);
		newTrain.addCarriage(secondCar); //exception bad configuration
	}
	
	//---- Strings

	/**
	 * Test method for {@link asgn2Train.DepartingTrain#toString()}.
	 */
	@Test
	public void testToStringNoCarriages() {
		DepartingTrain newTrain = new DepartingTrain();		
		assertEquals("", newTrain.toString()); //return empty string
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#toString()}.
	 * @throws TrainException 
	 */
	@Test
	public void testToStringOneCar() throws TrainException {
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		newTrain.addCarriage(firstCar);
		assertEquals(firstCar.toString(), newTrain.toString());		
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#toString()}.
	 * @throws TrainException 
	 */
	@Test
	public void testToStringPassengerTrain() throws TrainException {
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		PassengerCar secondCar = new PassengerCar(passengerWeight, numberOfSeats);
		newTrain.addCarriage(firstCar);
		newTrain.addCarriage(secondCar);
		String loco = firstCar.toString();
		String pass = secondCar.toString();
		assertEquals(loco + "-" + pass, newTrain.toString());	
	}
	
	/**
	 * Test method for {@link asgn2Train.DepartingTrain#toString()}.
	 * @throws TrainException 
	 */
	@Test
	public void testToStringTrain() throws TrainException {
		//fully configured train
		DepartingTrain newTrain = new DepartingTrain();
		Locomotive firstCar = new Locomotive(locomotiveWeight, classification);
		PassengerCar secondCar = new PassengerCar(passengerWeight, numberOfSeats);
		FreightCar thirdCar = new FreightCar(freightWeight, freightClassification);
		newTrain.addCarriage(firstCar);
		newTrain.addCarriage(secondCar);
		newTrain.addCarriage(thirdCar);
		String loco = firstCar.toString();
		String pass = secondCar.toString();
		String freight = thirdCar.toString();
		assertEquals(loco + "-" + pass + "-" + freight, newTrain.toString());
	}
}

package asgn2RollingStock;

import asgn2Exceptions.TrainException;

// Excepted Good Types - G, R, D
/**
 * @author Ian
 */
public class FreightCar extends RollingStock {	

	private String generalCode = "G";
	private String refrigeratedCode = "R";
	private String dangerousCode = "D";
	private Integer inValidWeight = 0;
	
	//private Integer weight;
	private String goods;
		

public FreightCar(Integer grossWeight, String goodsType) throws TrainException {
		super(grossWeight);
		// TODO Auto-generated constructor stub
		/*
		// testing for negative weight
		if (grossWeight <= inValidWeight) {
			throw new TrainException("Freight Weight is a negative or zero value");
		}
		// valid weight
		else {
			weight = grossWeight;
		}
		*/
		// testing for invalid goods
		if (goodsType.equals(generalCode) || goodsType.equals(refrigeratedCode) ||
				goodsType.equals(dangerousCode)){
			goods = goodsType;
		}
		
		else {
			throw new TrainException("Freight Goods Type is not Valid, G,R,D" +
			" Only");
		}
	}

public String goodsType() {

	return goods;
}

	@Override
	public String toString() {
		String template = "Freight (";
		String output;
		
		output = template + (goods + ")");
		return output;
		// TODO Auto-generated method stub
	}
}

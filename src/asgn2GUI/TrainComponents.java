package asgn2GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import asgn2Exceptions.TrainException;
import asgn2RollingStock.FreightCar;
import asgn2RollingStock.Locomotive;
import asgn2RollingStock.PassengerCar;
import asgn2RollingStock.RollingStock;
import asgn2Train.DepartingTrain;

public class TrainComponents extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 900;
	public static final int HEIGHT = 400;

	private Integer trainPower = 0;
	private Integer trainWeight = 0;

	
	// Buttons
	private JButton createTrain;
	private JButton addCar;
	private JButton removeCar;
	private JButton boardCar;
	private JButton viewTrain;
	private JButton departTrain;
	private JButton viewRemainingSeats;
	
	private JButton viewFirstCarriage;
	private JButton viewNextCarriage;

	// ComboBox
	private JComboBox trainType; //type of train
	private JComboBox locoClass; //classification
	private JComboBox freightGoods; //goods type

	// Label
	private JLabel addCarLbl;
	private JLabel carWeightLbl;
	private JLabel secondLbl;
	private JLabel thirdLbl;
	private JLabel addPassengerLbl; 

	// TextBox
	private JTextField carWeight; //carriage weight
	private JTextField passengerSeats; //seat capacity
	private JTextField passengerBoard; //boarding amount

	// Spinner
	private JSpinner locoPower; //loco power factor

	//panels for components
	private JPanel middleScreen;
	private JPanel driverComponents;
	private JPanel conductorComponents;

	// Display for simulation messages
	private JTextArea display;           
	private JScrollPane textScrollPane;

	// How big a margin to allow for the main frame
	final Integer mainMargin = 30; // pixels

	//Train data
	DepartingTrain emptyTrain;
	int freightTrain = 0; //has freight train
	int passengerCar = 0;
	int locomotiveCar = 0;


	public TrainComponents(String arg0) throws HeadlessException {
		super(arg0);
		createGUI();
	}

	// create GUI Components
	/**
	 * @author Ian
	 */
	private void createGUI() {

		setSize(WIDTH, HEIGHT);
		setLocation(120, 160);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Main frame Layout
		setLayout(new BorderLayout());

		middleScreen = new JPanel();
		middleScreen.setPreferredSize(new Dimension(200, this.getHeight()));
		middleScreen.setBorder(new TitledBorder("Main"));

		conductorComponents = new JPanel();
		conductorComponents.setPreferredSize(new Dimension(350, this.getHeight()));
		conductorComponents.setBorder(new TitledBorder("Conductor"));

		driverComponents = new JPanel();		
		driverComponents.setPreferredSize(new Dimension(350, this.getHeight()));
		driverComponents.setBorder(new TitledBorder("Driver"));

		//create buttons 1
		createTrain = createButton("New Train");
		addCar = createButton("Add Carriage");		
		removeCar = createButton("Remove Carriage");		
		viewTrain = createButton("Display Train");		
		departTrain = createButton("Depart Train");	
		//create buttons 2
		boardCar = createButton("Board Passengers");
		viewRemainingSeats = createButton("Remaining Seats");
		viewFirstCarriage = createButton("View First Carriage");
		viewNextCarriage = createButton("View Next Carriage");

		//selection box
		trainType = new JComboBox(new String[] {"Locomotive", "Passenger", "Freight"});
		trainType.addActionListener(this);
		locoClass = new JComboBox(new String[] {"Electric", "Diesel", "Steam"});
		freightGoods = new JComboBox(new String[] {"General", "Refridgerated", "Dangerous"});

		//text field
		carWeight = new JTextField();
		carWeight.setText("40");
		passengerSeats = new JTextField();
		passengerSeats.setText("100");
		passengerBoard = new JTextField();
		passengerBoard.setText("30");

		//Labels
		addCarLbl = new JLabel("Select Carriage to Add: ");
		carWeightLbl = new JLabel("Enter Carriage Weight: ");
		secondLbl = new JLabel("Select Power Factor: ");
		thirdLbl = new JLabel("Select Train Type: ");
		addPassengerLbl = new JLabel("Enter Boarding Amount.");

		SpinnerNumberModel spinModel = new SpinnerNumberModel(5, 0, 9, 1); //default, min, max, step
		locoPower = new JSpinner(spinModel);

		// initial visibility
		locoPower.setVisible(true);
		locoClass.setVisible(true);
		thirdLbl.setVisible(true);
		passengerSeats.setVisible(false);
		freightGoods.setVisible(false);			

		layoutConductor();
		layoutDriver();
		layoutMain();

		//Create Main Output Screen
		display = new JTextArea(); 
		display.setEditable(false);
		display.setLineWrap(true);
		display.setFont(new Font("Arial",Font.PLAIN,14));
		display.setRows(6);
		display.setBorder(BorderFactory.createLoweredBevelBorder());

		textScrollPane = new JScrollPane(display);	

		this.getContentPane().add(textScrollPane,BorderLayout.NORTH);
		this.getContentPane().add(middleScreen,BorderLayout.CENTER);
		this.getContentPane().add(conductorComponents,BorderLayout.EAST);
		this.getContentPane().add(driverComponents,BorderLayout.WEST);
		repaint();
	}

	// Create Button Helper
	/**
	 * @author Tim
	 */
	private JButton createButton(String str) {
		JButton jb = new JButton(str);
		jb.addActionListener(this);
		return jb; 
	}	
	
	/**
	 * @author Ian
	 */
	private void layoutDriver() {
		GridBagLayout layout = new GridBagLayout();
		driverComponents.setLayout(layout);

		//add components to grid
		GridBagConstraints constraints = new GridBagConstraints(); 

		//Defaults
		constraints.fill = GridBagConstraints.BOTH; //resize
		constraints.anchor = GridBagConstraints.LINE_END;
		//constraints.weightx = 0;
		//constraints.weighty = 0;

		addToPanel(driverComponents, createTrain,constraints,0,0,2,1); //create	    

		addToPanel(driverComponents, addCarLbl,constraints,0,1,1,1); //add car lbl
		addToPanel(driverComponents, trainType,constraints,1,1,1,1); //select type

		addToPanel(driverComponents, carWeightLbl,constraints,0,2,1,1); //weight lbl	    
		addToPanel(driverComponents, carWeight,constraints,1,2,1,1); //weight txt 

		addToPanel(driverComponents, secondLbl,constraints,0,3,1,1); //second level lbl	    
		addToPanel(driverComponents, locoPower,constraints,1,3,1,1); //loco spinner

		addToPanel(driverComponents, thirdLbl,constraints,0,4,1,1); //third lbl
		addToPanel(driverComponents, locoClass,constraints,1,4,1,1); //loco spec

		addToPanel(driverComponents, passengerSeats,constraints,1,3,1,1);	

		addToPanel(driverComponents, freightGoods,constraints,1,3,1,1);

		addToPanel(driverComponents, addCar,constraints,0,5,2,1); //add car
		addToPanel(driverComponents, removeCar,constraints,0,6,2,1); //remove car
		addToPanel(driverComponents, departTrain,constraints,1,8,1,1);
	}

	/**
	 * @author Ian
	 */
	private void layoutMain() {
		GridBagLayout layout = new GridBagLayout();
		middleScreen.setLayout(layout);

		//add components to grid
		GridBagConstraints constraints = new GridBagConstraints(); 

		//Defaults
		constraints.fill = GridBagConstraints.REMAINDER; //resize
		constraints.anchor = GridBagConstraints.SOUTH;
		//constraints.weightx = 0;
		//constraints.weighty = 0;

		addToPanel(middleScreen, viewTrain,constraints,0,0,1,1);
	}

	/**
	 * @author Ian
	 */
	private void layoutConductor() {
		GridBagLayout layout = new GridBagLayout();
		conductorComponents.setLayout(layout);

		//add components to grid
		GridBagConstraints constraints = new GridBagConstraints(); 

		//Defaults
		constraints.fill = GridBagConstraints.BOTH; //resize
		constraints.anchor = GridBagConstraints.LINE_END;
		//constraints.weightx = 0;
		//constraints.weighty = 0;

		//addToPanel(conductorComponents, viewRemainingSeats,constraints,0,0,1,1);
		addToPanel(conductorComponents, addPassengerLbl,constraints,0,0,1,1);
		addToPanel(conductorComponents, passengerBoard,constraints,0,1,1,1);
		addToPanel(conductorComponents, boardCar,constraints,1,1,1,1);
		addToPanel(conductorComponents, viewRemainingSeats, constraints, 0,2,1,1);
		addToPanel(conductorComponents, viewFirstCarriage, constraints, 0,3,1,1);
		addToPanel(conductorComponents, viewNextCarriage, constraints, 0,4,1,1);
	}

	/**
	 * 
	 * A convenience method to add a component to given grid bag
	 * layout locations. Code due to Cay Horstmann 
	 *
	 * @param c the component to add
	 * @param constraints the grid bag constraints to use
	 * @param x the x grid position
	 * @param y the y grid position
	 * @param w the grid width
	 * @param h the grid height
	 */
	
	private void addToPanel(JPanel jp,Component c, GridBagConstraints constraints, int x, int y, int w, int h) {  
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		jp.add(c, constraints);
	}

	/*
	 * Convenience method for resetting the text in the display area
	 */
	private void resetDisplay(String initialText) {
		display.setText("\n\t" + initialText);
	}

	/*
	 * Convenience method for adding text to the display area without
	 * overwriting what's already there
	 */
	private void appendDisplay(String newText) {
		display.setText(display.getText() + newText);
	}

	/*
	 * Actions
	 */
	/**
	 * @author Ian, Tim
	 */
	public void actionPerformed(ActionEvent event) {

		// Get event's source 
		Object source = event.getSource(); 

		// Change train type
		if (source == trainType) {
			switchVisibility();
		}		
		// New Train
		else if (source == createTrain) {
			if (emptyTrain != null) {
				emptyTrain = null;
			}
			emptyTrain = new DepartingTrain();
			resetDisplay("New Train Ready!\n");
		}
		// Add new carriage
		else if (source == addCar) {
			if (emptyTrain != null) {
				if (this.trainType.getSelectedItem() == "Locomotive") {
					if (emptyTrain.firstCarriage() == null || locomotiveCar == 0) {
						addLocomotive();
					} else {
						appendDisplay("\tTrain already contains a Locomotive\n");
					}
				}
				if (emptyTrain.firstCarriage() != null) {
					if (emptyTrain.numberOnBoard() > 0) { //check for passengers
						appendDisplay("\tCannot Preform Shunts with passengers on Carriages\n");
					} else {
						//try add carriages
						if (this.trainType.getSelectedItem() == "Passenger") {
							if (freightTrain == 0) {
								addPassenger();
							} else {
								appendDisplay("\tCannot Add Passenger Carriages after Freigths\n");
							}													
						}
						else if (this.trainType.getSelectedItem() == "Freight") {						
							addFreight();	
							freightTrain++; //count freight trains											
						}
						//check for weight
						if (!emptyTrain.trainCanMove()) {
							appendDisplay("\tTrain too Heavy, Remove a Carriage.\n");
						}	
					}
				} else {
					appendDisplay("\tFirst Carriage must be a Locomotive\n");
				}
			} else {
				resetDisplay("No Trains Currently in Station.");
			}			
		}


		// Let passengers board
		else if (source == boardCar) {
			if (emptyTrain != null) {
				Integer seatingCap = emptyTrain.numberOfSeats();
				if (seatingCap > 0) {
					Integer addPassengers = Integer.parseInt(this.passengerBoard.getText().trim());				
					try {
						Integer remainder = emptyTrain.board(addPassengers);
						if (remainder > 0) {
							appendDisplay("\tAll "+ addPassengers +" passengers Boarded\n");							
						} else {
							appendDisplay("\t" + remainder +" Passengers where unable to Board\n");
						}						
					} catch (TrainException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}											
				} else {
					appendDisplay("\tSeating Capacity Reached or No Passenger Carriages\n");
				}											
			} else {
				resetDisplay("No Trains Currently in Station.");
			}			
		}

		// Remove a carriage
		else if (source == removeCar) {
			if (emptyTrain != null) {
				if (emptyTrain.firstCarriage() != null) {
					if (emptyTrain.numberOnBoard() > 0) { //check for passengers
						appendDisplay("\tCannot Preform Shunts with passengers on Carriages\n");
					} else {
						try {
							emptyTrain.removeCarriage();
							Integer tempWeight = 0;
							RollingStock tempCar;
							Boolean end = false;
							do {
								tempCar = emptyTrain.nextCarriage();
								if (tempCar instanceof Locomotive) {
									locomotiveCar --;
									trainPower = 0;
									trainWeight = 0;
								}
								else if (tempCar instanceof PassengerCar) {
									passengerCar --;
								}
								
								if (tempCar == null) {
									end = true;
								}
								else {
									tempWeight = tempCar.getGrossWeight();
									
								}
							} while (!end);
							trainWeight -= tempWeight;
							if (freightTrain > 0) { //removing freight train
								freightTrain--;							
							}
						} catch (TrainException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						appendDisplay("\tA Carriage has been Removed\n");	
						powerWeightDisplay();
					}					
				} else {
					appendDisplay("\tNo Carriages to Remove\n");
				}				
			} else {
				resetDisplay("No Trains Currently in Station.");
			}						
		}		
		// View train layout
		else if (source == viewTrain) {			
			if (emptyTrain != null) {
				appendDisplay("\tTrain Configuration: ");
				appendDisplay(emptyTrain.toString() + "\n");	
				powerWeightDisplay();
			} else {
				resetDisplay("No Trains Currently in Station.");
			}
		}
		// view seats
		else if (source == viewRemainingSeats) {
			if (emptyTrain != null) {
				displaySeatingCapacityOcupancy();
			}	
		}
		
		//view First Carriage
		else if (source == viewFirstCarriage) {
			if (emptyTrain != null) {
				RollingStock temp = emptyTrain.firstCarriage();
				appendDisplay("\t First Carriage" + temp.toString() + "\n");
			}
		}
		
		//view Next Carriage
		else if (source == viewNextCarriage) {
			if (emptyTrain != null) {
				displayCarriage();
			}
		}
		
		// Train Finished
		else if (source == departTrain) {
			if (emptyTrain != null) {
				if (emptyTrain.firstCarriage() == null) {
					appendDisplay("\tNo Locomotive Present\n");
				}
				else if (!emptyTrain.trainCanMove()) {
					appendDisplay("\tTrain to Heavy!\n");
				}
				else if (emptyTrain.trainCanMove()) {
					emptyTrain = null;
					resetDisplay("Train has left the Station!");
				};
			} else {
				resetDisplay("No Trains Currently in Station.");				
			}
		};
	}

	/**
	 * @author Ian
	 */
	// Helper switch component visibility
	private void switchVisibility() {
		if (trainType.getSelectedItem() == "Locomotive") {
			locoPower.setVisible(true);
			locoClass.setVisible(true);
			passengerSeats.setVisible(false);
			freightGoods.setVisible(false);
			secondLbl.setText("Select Power Factor: ");
			thirdLbl.setVisible(true);
		}
		else if (trainType.getSelectedItem() == "Passenger") {
			locoPower.setVisible(false);
			locoClass.setVisible(false);
			passengerSeats.setVisible(true);
			freightGoods.setVisible(false);	
			secondLbl.setText("Passenger Capacity: ");
			thirdLbl.setVisible(false);
		}
		else if (trainType.getSelectedItem() == "Freight") {
			locoPower.setVisible(false);
			locoClass.setVisible(false);
			passengerSeats.setVisible(false);
			freightGoods.setVisible(true);	
			secondLbl.setText("Goods Type: ");
			thirdLbl.setVisible(false);
		}
	}

	/**
	 * @author Ian
	 */
	// Helper add locomotive
	private void addLocomotive() {
		Integer modValue = 100;
		Integer weight = Integer.parseInt(this.carWeight.getText().trim());
		Integer powerFactor = (Integer) this.locoPower.getValue();
		
		trainPower = powerFactor * modValue;
		trainWeight = weight;
		
		powerFactor.toString();

		String classification = "";

		if (this.locoClass.getSelectedItem() == "Electric") {
			classification = powerFactor + "E";
		}
		else if (this.locoClass.getSelectedItem() == "Diesel") {
			classification = powerFactor + "D";					
		}
		else if (this.locoClass.getSelectedItem() == "Steam") {
			classification = powerFactor + "S";					
		}				
		try {
			emptyTrain.addCarriage(new Locomotive(weight, classification));
			trainWeight += weight;
			appendDisplay("\tLocomotive Added\n");
			powerWeightDisplay();
		} catch (TrainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author Ian
	 */
	// Helper add Passenger car
	private void addPassenger() {
		Integer weight = Integer.parseInt(this.carWeight.getText().trim());
		Integer capacity = Integer.parseInt(this.passengerSeats.getText().trim());				
		try {
			emptyTrain.addCarriage(new PassengerCar(weight, capacity));
			trainWeight += weight;
			appendDisplay("\tPassenger Carriage Added\n");
			powerWeightDisplay();
		} catch (TrainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @author Tim
	 */
	// Helper add freight car
	private void addFreight() {
		Integer weight = Integer.parseInt(carWeight.getText().trim());			
		String goods = "";
		if (this.freightGoods.getSelectedItem() == "General") {
			goods = "G";				
		}
		else if (this.freightGoods.getSelectedItem() == "Refridgerated") {
			goods = "R";				
		}
		else if (this.freightGoods.getSelectedItem() == "Dangerous") {
			goods = "D";				
		}
		try {
			emptyTrain.addCarriage(new FreightCar(weight, goods));
			trainWeight += weight;
			appendDisplay("\tFreight Carriage Added\n");
			powerWeightDisplay();
		} catch (TrainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @author Tim
	 */
	private void powerWeightDisplay() {
		appendDisplay("\t  Train Power:" +  trainPower + 
				"\t Train Weight:" + trainWeight +	" \n ");
	}
	/**
	 * @author Tim
	 */
	private void displaySeatingCapacityOcupancy() {
		Integer remainingSeats = (emptyTrain.numberOfSeats() -
				emptyTrain.numberOnBoard());
		appendDisplay("\t Train Seating Capacity:" + emptyTrain.numberOfSeats() + 
				"\t Passengers on Board: " + emptyTrain.numberOnBoard() +"\n" );
		//appendDisplay("\t Passengers on Board: " + emptyTrain.numberOnBoard() + "\n");
		appendDisplay("\t Seats Remaining: " + remainingSeats + "\n");
	}
	/**
	 * @author Tim
	 */
	private void displayCarriage() {
		RollingStock tempCar = emptyTrain.nextCarriage();
		if (tempCar != null) {
			appendDisplay("\t Carriage: " + tempCar.toString() + "\n");
		}
		else {
			appendDisplay("\t No more Carriages to display \n");
		}
	}

	public static void main(String[] args) {
		TrainComponents gui = new TrainComponents("Train Organizer");
		gui.setVisible(true);
	}
}

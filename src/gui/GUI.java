package gui;

/**
 * marina, panagiotis
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core.taxisController;
import core.taxisModel;
import core.taxisView;

@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener, taxisView {

	taxisController controller;
	public GUI(taxisModel model) {
		model.addModelListener(this);
	}
	// Declaration of instance variables of panels
	JPanel menuPanel = new JPanel();
	JPanel northPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel southPanel = new JPanel();

	// Declaration of instance variables of Menu Panel
	JLabel workersCountL;
	JSpinner workersCountS;
	JLabel timerCountL;
	JSpinner timerCountS;
	JButton LoadJourneysB; //todo add, use loadfile call controller.loadJourneyFile
	// Declaration of instance variables of North Panel
	Vector<WorkerStand> stands = new Vector<WorkerStand>();


	// Declaration of instance variables of Center Panel
	JLabel passengersLabel;
	JLabel taxisLabel;
	JLabel passengersCounter;
	JLabel taxisCounter;
	JTextArea taxisQueue;
	JTextArea passengersQueue;

	// Declaration of instance variables of South Panel
	JButton startButton;
	JButton stopButton;
	JLabel taxisLogo;

	public void initializations()

	{
		// set the title of the window
		setTitle("Display available Taxis");

		
		/*
		 * When the close button on the upper right corner of thewindow is
		 * pressed the window closes
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		// Call methods for initialization
		setUpMenuPanel();
		setUpNorthPanel();
		setUpCenterPanel();
		setUpSouthPanel();

		
		/*
		 * pack the window to a fixed size ,set the location of the windowto the
		 * center of the screen and doesn't allow user to alter thedefault size
		 * has given on the window
		 */
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);

	}


	public void addWorkerStand(String name) {
		WorkerStand stand = new WorkerStand(name);
		stands.add(stand);
		northPanel.add(stand);
	}
	
	public void setUpMenuPanel() {
		workersCountL = new JLabel("Workers count:");
		menuPanel.add(workersCountL);
		workersCountS = new JSpinner(new SpinnerNumberModel(controller.getWorkersCount(), 0, 100, 1));

		workersCountS.addChangeListener( new ChangeListener() {
			//@Override
			public void stateChanged(ChangeEvent arg0) {
				int diff = ((Integer)workersCountS.getValue()) - controller.getWorkersCount();
				if(diff>0) {// add stuff
					controller.setWorkersCount((Integer)workersCountS.getValue());
					for(int i=0; i<diff; i++)
						addWorkerStand("WORKER " + (controller.getWorkersCount() + 1));
				} else if(diff<0) {//remove stuff
					controller.setWorkersCount((Integer)workersCountS.getValue());
					for(int i=0; i<-diff; i++) {
						northPanel.remove(stands.get(stands.size()-1));
						stands.remove(stands.size()-1);
					}
				}
				//refresh
				northPanel.revalidate();
				revalidate();
				//else do nothing
			}
			
		});
		menuPanel.add(workersCountS);
		timerCountL = new JLabel("TImer ticks(ms):");
		menuPanel.add(timerCountL);
		timerCountS = new JSpinner(new SpinnerNumberModel(1000, 1000, 10000, 500));
		timerCountS.addChangeListener(new ChangeListener() {
			
			//@Override
			public void stateChanged(ChangeEvent e) {
				controller.setTimerCount((Integer) timerCountS.getValue());
			}
		});
		menuPanel.add(timerCountS);
		northPanel.add(menuPanel);
	}
	public void setUpNorthPanel() {

		/*
		 * All components of northPanel initialized and the layout of the panel
		 * is set to GridBagLayout.Also setBorder has been used and declared as
		 * an EmptyBorder to define spaces between the NORTH and the CENTER
		 * panel
		 */
		northPanel.setBorder(new EmptyBorder(10, 15, 50, 15));
		northPanel.setLayout(new GridBagLayout());
		
		for(int i=0; i<controller.getWorkersCount(); i++)
			this.addWorkerStand("WORKER " + (i + 1));

		// Adds northPanel to the NORTH layout of the window
		add(northPanel, BorderLayout.NORTH);
	}

	
	public void setUpCenterPanel() {

		/*
		 * All components of centerPanel initialized and different attributes
		 * defined for each element
		 */
		centerPanel.setLayout(new GridBagLayout());

		passengersLabel = new JLabel("Passengers waiting");
		taxisLabel = new JLabel("Taxis available");

		passengersCounter = new JLabel("3");
		taxisCounter = new JLabel("5");

		taxisQueue = new JTextArea(18, 25);
		taxisQueue.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		taxisQueue.setEditable(false);
		taxisQueue.setBorder(BorderFactory.createLineBorder(Color.black));

		passengersQueue = new JTextArea(18, 25);
		passengersQueue.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		passengersQueue.setEditable(false);
		passengersQueue.setBorder(BorderFactory.createLineBorder(Color.black));

		
		// Creates a GridBagConstraints object
		GridBagConstraints c = new GridBagConstraints();

		
		/*
		 * Sets the values (3,5,,5,3) on the GridBagConstraints object created
		 * abovewhich they define the distance(top,left,right,bottom) which will
		 * havethe elements of the panel between them.
		 */
		c.insets = new Insets(1, 10, 10, 1);
		c.gridx = 0;
		c.gridy = 0;
		centerPanel.add(passengersLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		centerPanel.add(passengersCounter, c);
		c.gridx = 0;
		c.gridy = 2;
		centerPanel.add(passengersQueue, c);

		c.gridx = 1;
		c.gridy = 0;
		centerPanel.add(taxisLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		centerPanel.add(taxisCounter, c);
		c.gridx = 1;
		c.gridy = 2;
		centerPanel.add(taxisQueue, c);

		
		// Adds centerPanel to the CENTER layout of the window
		add(centerPanel, BorderLayout.CENTER);

	}

	
	public void setUpSouthPanel() {

		/*
		 * All components of southPanel initialized and layout set to FlowLayout
		 */
		southPanel.setLayout(new FlowLayout());
		startButton = new JButton("Start");
		stopButton = new JButton("Stop");
		taxisLogo = new JLabel(new ImageIcon(
				"/Users/Panos/Documents/workspace/GUI/Icons/taxis.png"));

		
		/*
		 * Add actionListeners to the buttons and every time the user presses
		 * the button an action is performed regarding the command has been
		 * assessed in the actionPerformed method
		 */
		startButton.addActionListener(this);
		stopButton.addActionListener(this);

		
		// All elements has been added to the southPanel
		southPanel.add(taxisLogo);
		southPanel.add(startButton);
		southPanel.add(stopButton);
		//southPanel.add(pauseButton);

		
		// Used to validate the image added to taxisLogo label
		validate();

		
		// Adds southPanel to the SOUTH layer of the window
		add(southPanel, BorderLayout.SOUTH);

	}

	
	/*
	 * Depending which button is pressed every time an action takes place
	 */

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == startButton)

		{
			controller.pairAll();
			
		}

		if (e.getSource() == stopButton)

		{
			controller.terminateExecution();

		}

	}


	//@Override
	public void modelChanged(taxisModel model) {
		String taxis = "", journeys = "";
		int i;
		for(i=0; i<model.taxisCount(); i++)
			taxis += model.getTaxi(i).getID() + "\n";
		if(i != 0)
			taxis = taxis.substring(0, taxis.length()-1);
		taxisQueue.setText(taxis);
		taxisCounter.setText("" + model.taxisCount());
		for(i=0; i<model.journeyCount(); i++)
			journeys += model.getJourney(i).getDestination() + ", " + model.getJourney(i).getPassengers() + "\n";
		if(i != 0)
			journeys = journeys.substring(0, journeys.length()-1);
		passengersQueue.setText(journeys);
		passengersCounter.setText("" + model.journeyCount());
	}
	
	//@Override
	public void setWorkerCurrentItem(int workerIndex, String text) {
		stands.get(workerIndex).setText(text);
	}


	public void setController(taxisController controller) {
		this.controller = controller;		
	}
}
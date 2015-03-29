package gui;

/**
 * @authors: Filiotis Panagiotis - H00203394 (graphical part)
 *           Goupiou Marina - H00199564 (functionality, connections, etc.)
 *           
 *           
 *           Paparisteidis Georgios - H00202663 (relevant to extension: more passenger groups can be added during simulation) 
 *           Fytrakis Emmanouil - H00202650 (the same extension)
 *           
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core.FileFormatException;
import core.taxisController;
import core.taxisModel;
import core.taxisView;

@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener, taxisView {
	
	public static String Destination;
	public static int NoPassengers;
	public static boolean NewEntry;
    taxisController controller;
	
	
	// Declaration of instance variables of panels
	JPanel menuPanel = new JPanel();
	JPanel northPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel southPanel = new JPanel();
	JPanel eastPanel = new JPanel();
	JPanel westPanel = new JPanel();

	
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
	JScrollPane taxisScroll;
	JScrollPane passengersScroll;

	
	// Declaration of instance variables of South Panel
	JButton startButton;
	JButton stopButton;
	JLabel taxisLogo;
	
	
	// Declaration of instance variables of West Panel
    JButton submit;
    JLabel entryHeader;
    JLabel passengers;
	JLabel destinationsLabel;
	JTextField passengersText;
	JTextField destinationsText;
	JButton browseButton;
	JLabel browseLabel;
	
	
	// Declaration of instance variables of East Panel
	JLabel speedLabel;
	JSpinner speedHandler;
	JLabel extraWorkersLabel;
	JSpinner extraWorkers;
	
	
	public GUI(taxisModel model) {
		
	     model.addModelListener(this);
		}
	
	
	public void initializations()

	{
		// set the title of the window
		setTitle("Display available Taxis");

		
		/*
		 * When the close button on the upper right corner of the window is
		 * pressed the window closes
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		// Call methods for initialization
		setUpNorthPanel();
		setUpCenterPanel();
		setUpSouthPanel();
		setUpWestPanel();
		setUpEastPanel();

		
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
	
	
	
	
	
	public void setUpNorthPanel() {

		/*
		 * All components of northPanel initialized and the layout of the panel
		 * is set to GridBagLayout.Also setBorder has been used and declared as
		 * an EmptyBorder to define spaces between the NORTH and the CENTER
		 * panel
		 */
		northPanel.setBorder(new EmptyBorder(10, 15, 1, 1));
		northPanel.setLayout(new GridLayout());
		
		for(int i=0; i<controller.getWorkersCount(); i++)
						
			this.addWorkerStand("WORKER " + (i+1));	
	
		
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

		passengersCounter = new JLabel("");
		taxisCounter = new JLabel("");
		

		taxisQueue = new JTextArea(15,13);
		taxisQueue.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		taxisQueue.setEditable(false);
		taxisQueue.setBorder(BorderFactory.createLineBorder(Color.black));
		
		taxisScroll = new JScrollPane(taxisQueue);

		
		passengersQueue = new JTextArea(18, 25);
		passengersQueue.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		passengersQueue.setEditable(false);
		passengersQueue.setBorder(BorderFactory.createLineBorder(Color.black));
		
		passengersScroll = new JScrollPane(passengersQueue);


		
		// Creates a GridBagConstraints object
		GridBagConstraints c = new GridBagConstraints();
         
		
		/*
		 * Sets the values (3,5,,5,3) on the GridBagConstraints object created
		 * above which they define the distance(top,left,right,bottom) which
		 * will have the elements of the panel between them.
		 */
		c.insets = new Insets(1, 10, 10, 1);

		c.gridx = 0;
		c.gridy = 0;
		centerPanel.add(passengersLabel, c);

		c.gridx = 1;
		c.gridy = 0;
		centerPanel.add(taxisLabel, c);

		c.gridx = 0;
		c.gridy = 1;
		centerPanel.add(taxisCounter, c);

		c.gridx = 1;
		c.gridy = 1;
		centerPanel.add(passengersCounter, c);
		// c.fill=GridBagConstraints.HORIZONTAL;

		c.fill = GridBagConstraints.VERTICAL;
		c.ipady = 400;
		c.ipadx = 200;
		c.gridx = 0;
		c.gridy = 2;
		centerPanel.add(passengersScroll, c);

		c.fill = GridBagConstraints.VERTICAL;
		c.ipady = 400;
		c.ipadx = 180;
		c.gridx = 1;
		c.gridy = 2;
		centerPanel.add(taxisScroll, c);
		
	
		// Adds centerPanel to the CENTER layout of the window
		add(centerPanel, BorderLayout.CENTER);

	}

	
	
	public void setUpSouthPanel() {

		/*
		 * All components of southPanel initialized and layout set to FlowLayout
		 */
		southPanel.setLayout(new FlowLayout());
		startButton = new JButton("Start/Resume");
		stopButton = new JButton("Stop/Pause");

		
		/*
		 * Add actionListeners to the buttons and every time the user presses
		 * the button an action is performed regarding the command has been
		 * assessed in the actionPerformed method
		 */
		startButton.addActionListener(this);
		stopButton.addActionListener(this);

		
		// All elements has been added to the southPanel
		southPanel.add(startButton);
		southPanel.add(stopButton);
		//southPanel.add(pauseButton);

		
		// Used to validate the image added to taxisLogo label
		validate();

		
		// Adds southPanel to the SOUTH layer of the window
		add(southPanel, BorderLayout.SOUTH);

	}
	
	
	
    public void setUpWestPanel() {
		

    	
    	
    	
		/*
		 * All components of westPanel initialized and some different attributes
		 * for each element have been given
		 */
		JPanel topPanel = new JPanel(new FlowLayout());
		JPanel bottomPanel = new JPanel(new FlowLayout());
		JPanel centerPanel = new JPanel(new GridLayout(0,2,5,6));
		JPanel groupPanel  =new JPanel(new GridBagLayout());
		JPanel filePanel = new JPanel((new FlowLayout()));
		
	
		westPanel.setLayout(new GridBagLayout());
		westPanel.setBorder(new TitledBorder(new LineBorder(Color.black, 2),
		"New Entry Manually"));
		
		
		filePanel.setBorder(new TitledBorder(new LineBorder(Color.black, 2),
				"New Entry From File"));
		browseButton= new JButton("Browse");
		browseLabel = new JLabel("Please select a File:");
		

		entryHeader = new JLabel("Please Enter New Details");

		passengers = new JLabel("No of Passengers");
		passengersText = new JTextField();
		passengersText.setPreferredSize(new Dimension(10,2));

		destinationsLabel = new JLabel("Destination");
		destinationsText = new JTextField();

		submit = new JButton("Submit Details");
		submit.addActionListener(this);

		
		//All the sub-panels added to the main panel
	
		topPanel.add(entryHeader);
		centerPanel.add(passengers);
		centerPanel.add(passengersText);
		centerPanel.add(destinationsLabel);
		centerPanel.add(destinationsText);	
		bottomPanel.add(submit);

		GridBagConstraints c = new GridBagConstraints();
        GridBagConstraints d = new GridBagConstraints();
		
		/*
		 * Sets the values (1,10,10,1) on the GridBagConstraints object created
		 * above.The values define the distance(top,left,right,bottom) which
		 * will have the elements of the panel between them and the position on the panel.
		 */
		c.insets = new Insets(1, 10, 10, 1);
		c.gridx = 0;
		c.gridy = 1;
		westPanel.add(topPanel, c);
		c.gridx = 0;
		c.gridy = 2;
		westPanel.add(centerPanel, c);
		c.gridx = 0;
		c.gridy = 3;
		westPanel.add(bottomPanel, c);
		
		
		
		filePanel.add(browseLabel);
		filePanel.add(browseButton);
		browseButton.addActionListener(this);
		
		
		d.insets = new Insets(1, 25, 25, 1);	
		d.gridx = 0;
		d.gridy = 1;
		groupPanel.add(westPanel,d);
		d.gridx = 0;
		d.gridy = 2;
		groupPanel.add(filePanel,d);
		
		
		// Adds groupPanel to the WEST layout of the window
		this.add(groupPanel, BorderLayout.WEST);	
		
		
		
	
	}
	
    


	public void setUpEastPanel() {
	
		/*
		 * All elements of eastPanel initialized 
		 */
		
		eastPanel.setLayout(new GridLayout(2,10,25,30));
		JPanel backPanel = new JPanel(new GridBagLayout());
		eastPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
	   
		
		extraWorkersLabel = new JLabel("Extra Workers");
		extraWorkers = new JSpinner(new SpinnerNumberModel(controller.getWorkersCount(), 1, 7, 1));
		
		extraWorkers.addChangeListener( new ChangeListener() {
		
			
			//@Override
			public void stateChanged(ChangeEvent arg0) {
				int diff = ((Integer)extraWorkers.getValue()) - controller.getWorkersCount();
				if(diff>0) {// add stuff
					controller.setWorkersCount((Integer)extraWorkers.getValue());
					for(int i=0; i<diff; i++)
						addWorkerStand("WORKER " + (controller.getWorkersCount() + 1));
				} else if(diff<0) {//remove stuff
					controller.setWorkersCount((Integer)extraWorkers.getValue());
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
		
		eastPanel.add(extraWorkersLabel);
		eastPanel.add(extraWorkers);
		
		JLabel speedLabel= new JLabel("Speed Modification");
		eastPanel.add(speedLabel);
		final JSpinner speedHandler=new JSpinner(new SpinnerNumberModel(1000, 1000, 10000, 1000));
		speedHandler.addChangeListener(new ChangeListener() {
			
			//@Override
			public void stateChanged(ChangeEvent e) {
				controller.setTimerCount((Integer) speedHandler.getValue());
			}
		});
		
		eastPanel.add(speedHandler);


		GridBagConstraints b = new GridBagConstraints();
	
		/*
		 * Sets the values (1,10,10,1) on the GridBagConstraints object created
		 * above.The values define the distance(top,left,right,bottom) which
		 * will have the elements of the panel between them.Then the gridx and gridy
		 * puts the element on a specific position on the panel.
		 */
		b.insets = new Insets(1, 10, 10, 1);
		b.gridx = 0;
		b.gridy = 0;
		backPanel.add(eastPanel,b);
		
	
		// Adds groupPanel to the EAST layout of the window
		add(backPanel ,BorderLayout.EAST);
		
		
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
		
		
		//Actions relevant to submit button
		if (e.getSource() == submit) {

			/*
			 * Checks after submitting the form if both fields are empty.if this is the case 
			 * user gets a warning message saying that he has two provide proper values to
			 * both text fields.Also a red border in each field is displayed to show the empty boxes.
			 */
			if ((passengersText.getText().trim().equals(""))
					&& destinationsText.getText().trim().equals(""))

			{

				JOptionPane.showMessageDialog(centerPanel,
						"Please provide a destination and the number of passengers",
						"WARNING!!!!",
						JOptionPane.ERROR_MESSAGE);	
				
				passengersText.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				destinationsText.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				
				
			}

			/*
			 * if the text field responsible for passengers values is empty
			 * user gets a warning message saying that he has to provide a proper value to
			 * that text field.Also a red border around the field is displayed
			 */
			
			else if (passengersText.getText().trim().equals("")) {

				JOptionPane.showMessageDialog(centerPanel,
						"Please provide a number of passengers",
						"WARNING!!!!",
						JOptionPane.ERROR_MESSAGE);

				destinationsText.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
				passengersText.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				
			}

			
			
			/*
			 * if the text field responsible for the destination values is empty
			 * user gets a warning message saying that he has to provide a proper value to
			 * the specific text field.Also a red border around the field is displayed
			 */	
			else if (destinationsText.getText().trim().equals(""))

			{
				JOptionPane.showMessageDialog(centerPanel,
						"Please provide a destination",
						"WARNING!!!!",
						JOptionPane.ERROR_MESSAGE);
				
				    passengersText.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
					destinationsText.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

			}

			/*
			 * if both fields have values, a new entry is inserted into the list of passengesQueue
			 * and the user gets an information message saying that has registered the form properly.
			 */
			
			else

			{
				
				NoPassengers = Integer.parseInt(passengersText.getText());
				Destination = destinationsText.getText();
				
				
				
				if (NoPassengers > 0 && NoPassengers <=5 )
				{
				NewEntry = true;
				JOptionPane.showMessageDialog(centerPanel,
						"Registration completed.Thank you.A taxi will be with you soon!!");
				passengersText.setText("");
				destinationsText.setText("");
				passengersText.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
				destinationsText.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
				}
				
				else 
				{
					
					JOptionPane.showMessageDialog(centerPanel,
							"Please choose for passengers a value between 1-5",
							"WARNING!!!!",
							JOptionPane.WARNING_MESSAGE);
					passengersText.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					destinationsText.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
					
					
				}
				


			}

		}
		if (e.getSource() == browseButton) {
			//create the open file dialog
			JFileChooser fchooser = new JFileChooser();
			int ret = fchooser.showOpenDialog(this);
			if(ret == JFileChooser.APPROVE_OPTION) {
				//the user has selected a file, ask the controller to load it
				try {
					controller.loadJourneyFile(fchooser.getSelectedFile().getPath());
				} catch (FileFormatException e1) {
					JOptionPane.showMessageDialog(centerPanel,
							"Error attempting to load the specified file",
							"WARNING!!!!",
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
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
		passengersCounter.setText("" + model.taxisCount());
		for(i=0; i<model.journeyCount(); i++)
			journeys += model.getJourney(i).getDestination() + ", " + model.getJourney(i).getPassengers() + "\n";
		if(i != 0)
			journeys = journeys.substring(0, journeys.length()-1);
		passengersQueue.setText(journeys);
		taxisCounter.setText("" + model.journeyCount());
	}
	
	//@Override
	public void setWorkerCurrentItem(int workerIndex, String text) {
		stands.get(workerIndex).setText(text);
	}


	public void setController(taxisController controller) {
		this.controller = controller;		
	}
}
/**
 * 
 * @author Goupiou Marina
 *
 */

package core;

import gui.GUI;

import java.io.File;


public class taxisDemo {
	public static void main(String args[]) {
		//clear the log file
		File f = new File("log.txt");
		if(f.exists())
			f.delete();
		//setup logger
		Logger.getInstance().setLogFile("log.txt");
		//create model, view and controller
		taxisModel model = new taxisModel();
        GUI view = new GUI(model);
		taxisController controller = new taxisController(model, view);
		controller.setWorkersCount(3);
		controller.setTimerCount(1000);
		view.setController(controller);
		try {
			//create the gui
			view.initializations();
		    view.setVisible(true);
		    //load the data
			controller.loadJourneyFile("journeys.txt");
			controller.loadTaxiFile("taxis.txt");
			//ready, the gui thread takes over now
		} catch (FileFormatException e) {
			System.out.println(e.getMessage());
		}
	}
}
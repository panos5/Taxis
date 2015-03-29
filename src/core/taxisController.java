/**
 * 
 * @author marina
 *
 */

package core;
import gui.GUI;

import java.awt.Frame;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//--> why Runnable???
//==> instances of this class (taxisController) can be run non threads 
//I also need the class RUN for this purpose (RUN is at the end)
public class taxisController implements Runnable, ModelListener {
	private taxisModel model;
	private taxisView view;
	private boolean terminateThread;
	private Vector<Thread> workers;
	private int timerCount;
	
	public taxisController(taxisModel model, taxisView view) {
		this.model = model;
		model.addModelListener(this);
		this.view = view;
		workers = new Vector<Thread>();
		timerCount = 1000;
		terminateThread = true;
	}
	
	public void setWorkersCount(int count) {
		//keep the threads state (working/idle)
		boolean tmp = this.terminateThread;
		//ensure all threads are going to terminate, without losing any data
		this.terminateThread = true;
		//wait for the threads to finish, before we destroy the objects...
		for(int i=0; i<workers.size(); i++)
			try {
				workers.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//destroy the threads
		workers.clear();
		//add the new threads
		for(int i=0; i<count; i++)
			workers.add(new Thread(this));
		//return all remaining threads to their previous state
		this.terminateThread = tmp;
		if(!this.terminateThread)
			pairAll(); //the threads were working originally, so restart them.
	}
	public int getWorkersCount() {
		return workers.size();
	}

	
	public int getTimerCount() {
		return timerCount;
	}

	public void setTimerCount(int timerCount) {
		this.timerCount = timerCount;
	}

	public void loadTaxiFile(String filename) throws FileFormatException {
		// load the file's lines
		Vector<String> lines = new Parser(filename).getLinesList();
		String[] segments;
		//now we parse them, one line at a time
		for(int i=0; i<lines.size(); i++) {
			segments = lines.get(i).split(",");
			if(segments.length != 1) //one record per line
				throw new FileFormatException(filename);
			model.addTaxi(new Taxi(i + 1, segments[0]));
		}	
	}
	public void loadJourneyFile(String filename) throws FileFormatException {
		// load the file's lines
		Vector<String> lines = new Parser(filename).getLinesList();
		String[] segments;
		//now we parse them, one line at a time
		for(int i=0; i<lines.size(); i++) {
			segments = lines.get(i).split(",");
			if(segments.length != 2) //two record per line
				throw new FileFormatException(filename);
			model.addJourney(new Journey(i + 1, segments[0], Integer.parseInt(segments[1])));
		}	
	}
	
	public void pairAll() {
		//ensure the threads will run
		this.terminateThread = false;
		//create the threads
		for(int i=0; i<workers.size(); i++)
			if(workers.get(i).getState() != Thread.State.NEW) //if a thread has been started, calling start again, will fail
				workers.set(i, new Thread(this)); //so we create a newer thread object!
		//run the threads
		for(int i=0; i<workers.size(); i++)
			workers.get(i).start(); //now we can safely start the thread.
	}
	public void terminateExecution() {
		//ask threads kindly to terminate
		this.terminateThread = true;
		//wait for all the threads to termimate, they will surely terminate
		for(int i=0; i<workers.size(); i++)
			try {
				workers.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	//code for workers
	//@Override
	public void run() { 
		Journey j;
		Taxi t;
		String s;
		//workerID
		int workerID = (int)(Thread.currentThread().getId() % this.workers.size());
		while(model.hasMoreTaxis()  && model.hasMoreJourneys()) {
			//terminateThread is used to request the termination of this thread
			if(terminateThread)
				break;
			//make simulation time variable
			try {
				Thread.sleep(timerCount);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			///get the data, due to locks there should not be any race conditions
			j = model.pollJourney();
			t = model.pollTaxi();
			//use the data and prepare the result
			if(j != null && t != null) {
				j.setTaxi(t);
				model.addPairedJourney(j);
				//Using character '\1' where a space or a '\n' should  will enable us to parse the string easily afterwards
				s = "W" + (workerID+1) + ":\1DESTINATION: " + j.getDestination() + "\1PASSENGERS:\t" + j.getPassengers() + "\1Taxi:\t" + t.getID();
			} else if(j == null && t == null) {
				s = "W" + (workerID+1) + ": Closing!";
			} else if(j == null) {
				s = "W" + (workerID+1) + ": No more passengers";
			} else {//t == null
				s = "W" + (workerID+1) + ": No more taxis";
			}
			
			
			//show and log the results
			Logger.getInstance().log(s.replace('\1', ' ')); // make the log one line
			view.setWorkerCurrentItem(workerID, s.replace('\1', '\n')); //make the log 4 lines

			
			if ( GUI.NoPassengers !=0 && !(GUI.Destination.isEmpty()) && GUI.NewEntry == true){
				model.addJourney(new Journey(100,GUI.Destination,GUI.NoPassengers));
				GUI.NewEntry = false;
			
			}
			 	
			
		}
	}

	//@Override
	public void modelChanged(taxisModel model) {
		//unneeded
	}
}

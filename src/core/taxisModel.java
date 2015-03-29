/**
 * 
 * @author marina
 *
 */

package core;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class taxisModel {
	private Vector<ModelListener> listeners;
	
	// I only want to store them, that's why I used Vector 
	private Vector<Taxi> taxis;
	private Vector<Journey> journeys;
	private Vector<Journey> pairedJourneys;

	// usage of lock-unlock: 
	// a specific record is loaded and read, assuring that there is no conflict with other
	private Lock taxiLock;
	private Lock journeyLock;
	private Lock pairedJourneysLock;

	
	public taxisModel() {
		listeners = new Vector<ModelListener>();
		
		this.taxis = new Vector<Taxi>();
		this.journeys = new Vector<Journey>();
		this.pairedJourneys = new Vector<Journey>();
		//ReentrantLock(true) ensures every thread gets the resources in a first in first served fashion
		this.taxiLock = new ReentrantLock(true);
		this.journeyLock = new ReentrantLock(true);
		this.pairedJourneysLock = new ReentrantLock(true);
	}
	public void addModelListener(ModelListener listener) {
		listeners.add(listener);
	}
	//used to call model to change it's values
	private void broadcastChange() {
		for(int i=0; i<listeners.size(); i++)
			if(listeners.get(i) != null)
				listeners.get(i).modelChanged(this);
	}
	
	public void addTaxi(Taxi taxi) {
		taxiLock.lock(); //ensure that no other thread writes while we add the taxi
		try {
			taxis.add(taxi);
		} finally {
			taxiLock.unlock();
		}
		broadcastChange();
	}
	public Taxi getTaxi(int index) {
		if(index < taxis.size())
			return taxis.get(index);
		return null;
	}
	//used only to get sequentially all the taxis stored in the model, and remove them
	public Taxi pollTaxi() {
		taxiLock.lock();
		Taxi t = null;
		try {
			if(taxis.size() != 0) {
				t = taxis.get(0);
				taxis.remove(0);
			}
		} finally {
			taxiLock.unlock();
		}
		broadcastChange();
		return t;
	}
	public int taxisCount() {
		return taxis.size();
	}
	public boolean hasMoreTaxis() {
		return taxis.size() != 0;
	}
	public void addJourney(Journey journey) {
		journeyLock.lock();
		try {
			journeys.add(journey);
		} finally {
			journeyLock.unlock();
		}
		broadcastChange();
	}
	public Journey getJourney(int index) {
		if(index < journeys.size())
			return journeys.get(index);
		return null;
	}
	public Journey pollJourney()  {
		journeyLock.lock();
		Journey j = null;
		try {
			if(journeys.size() != 0) {
				j = journeys.get(0);
				journeys.remove(0);
			}	
		} finally {
			journeyLock.unlock();
		}
		broadcastChange();
		return j;
	}
	public int journeyCount() {
		return journeys.size();
	}
	public boolean hasMoreJourneys() {
		return journeys.size() != 0;
	}
	public void addPairedJourney(Journey j) {
		pairedJourneysLock.lock();
		try {
			pairedJourneys.add(j);
		} finally {
			pairedJourneysLock.unlock();
		}
		broadcastChange();
	}
	public Journey getPairedJourney(int index) {
		if(index < pairedJourneys.size())
			return pairedJourneys.get(index);
		return null;
	}
	public Journey pollPairedJourney() {
		pairedJourneysLock.lock();
		Journey j = null;
		try {
			if(pairedJourneys.size() != 0) {
				j = pairedJourneys.get(0);
				pairedJourneys.remove(0);
			}
		} finally {
			pairedJourneysLock.unlock();
		}	
		return j;
	}
}



/**
 * 
 * @author Goupiou Marina
 *
 */
package core;

public class Journey {
	
	private int sequence_id;
	private String destination;
	private int passengers;
	private Taxi taxi;
	
	public Journey(int sequence_id, String destination, int passengers) {
		this.sequence_id = sequence_id;
		this.destination = destination;
		this.passengers = passengers;
		this.taxi = null;
	}
	public void setTaxi(Taxi t) {
		this.taxi = t;
	}
	public int getSequenceID() {
		return sequence_id;	
	}
	public String getDestination() {
		return destination;	
	}
	public int getPassengers() {
		return passengers;	
	}
	public Taxi getTaxi() {
		return taxi;	
	}

}

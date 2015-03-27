/**
 * 
 * @author marina
 *
 */
package core;

public class Taxi {
	private String id;
	private int sequence_id;
	
	public Taxi(int sequence_id, String id) {
		this.sequence_id = sequence_id;
		this.id = id;
	}
	public String getID() {
		return id;
	}
	public int getSequenceID() {
		return sequence_id;	
	}
}

/**
 * 
 * @authors: marina, panos
 *
 */
package core;

// Used to receive any broadcast of changes in the model data
public interface ModelListener {
	//called by the model when a paired Journey occurs
	public void modelChanged(taxisModel model);
	
}

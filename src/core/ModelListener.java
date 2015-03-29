/**
 * 
 * @authors: Goupiou Marina - H00199564
 *           Filiotis Panagiotis - H00203394
 *
 */
package core;

// Used to receive any broadcast of changes in the model data
public interface ModelListener {
	//called by the model when a paired Journey occurs
	public void modelChanged(taxisModel model);
	
}

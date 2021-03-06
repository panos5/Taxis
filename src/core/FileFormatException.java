/**
 * 
 * @author: Goupiou Marina - H00199564
 *
 */
package core;

// used when a file was not formated correctly
public class FileFormatException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public FileFormatException(String filename) {
		super(filename + " had wrong format!");
	}
	
}

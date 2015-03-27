package core;
/*
 * ASE: STAGE 1ST - TEAM 11 
 * @author: Goupiou Marina – H00199564 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 * Parses a file and returns an object of type LinesList to read the lines of the file.
 */
public class Parser {

    /**
     * Parses a file and returns an object of type LinesList to read the lines of the file.
     */
    public Parser() {
    }

    /**
     * Contains an object of class Vector to hold the lines of the parsed file.
     */
    private Vector<String> exportedlines = new Vector<String>();

    /**
     * Reads the contents of the file and stores each line as a String in the List lines.
     * @param filename 
     * @return
     * @throws FileNotFoundException 
     */
    public void loadFile(String filename) {
        try {
	        BufferedReader br = new BufferedReader(new FileReader(filename));
        	String s = "";
        	while((s = br.readLine()) != null) { //read the file line by line.
        		this.exportedlines.add(s); //then add each line to the list
        	}
        	br.close();
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
    }

    /**
     * Returns the internal LinesList object
     * @return
     */
    public Vector<String> getLinesList() {
        return this.exportedlines;
    }

    /**
     * Calls loadFile(filename)
     * @param filename
     */
    public Parser(String filename) {
    	this.loadFile(filename); //just call the loadFile function.
    }

}
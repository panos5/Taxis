package core;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author marinaki
 *
 */
public class Logger {
	private String logFile;
	private Lock filelock;
	
	/** start singleton **/
	private static Logger instance = null;
	public static Logger getInstance() {
		if(instance == null) { //first call of getInstance is initialization 
			instance = new Logger();
			instance.filelock = new ReentrantLock(true);
			instance.logFile = "log.txt";
		}
		return instance;
	}
	/** prevents instantiation **/
	protected Logger() {
		
	}
	/** end singleton **/
	// open the log file, append the log, close the file
	public void log(String text) {
		filelock.lock(); //make sure only one thread can write at any time
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(logFile, true));
			br.write(text + "\n");
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			filelock.unlock(); //let the next thread write the log
		}
	}
	public void setLogFile(String filename) {
		this.logFile = filename;
	}
}

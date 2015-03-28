/**
 * @author: marina
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class WorkerStand extends JPanel {

	private JLabel label;
	private JTextArea textarea;
	private JScrollPane scroll;
	
	
	public WorkerStand(String name) {
		
		super(true);
		this.setLayout(new BorderLayout());
		label = new JLabel(name);
		label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		this.add(label, BorderLayout.NORTH);
		textarea = new JTextArea(5, 19);
		textarea.setText("");
		scroll = new JScrollPane(textarea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setSize(200, 100);
		this.add(scroll, BorderLayout.CENTER);
	}
	
	
	public void addText(String text) {
		textarea.setText(textarea.getText() + text);
	}
	
	
	public void setText(String text) {
		textarea.setText(text);
	}
}

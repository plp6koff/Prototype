package com.consultancygrid.trz.actionListener;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;

import com.consultancygrid.trz.ui.frame.FileChooserDemo;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;

public class OpenFileAL extends BaseActionListener {
	
	static private final String newline = "\n";
	private JFileChooser fc;
	private JTextArea log;
	private File file;
	
	public OpenFileAL(PrototypeMainFrame mainFrame, JFileChooser fc,
			JTextArea log) {

		super(mainFrame);
		this.fc = fc;
		this.log = log;
	}

	public void actionPerformed(ActionEvent e) {

		int returnVal = fc.showOpenDialog(mainFrame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			
			file = fc.getSelectedFile();
			// This is where a real application would open the file.
			log.append("Opening: " + file.getName() + "." + newline);
		} else {
			log.append("Open command cancelled by user." + newline);
		}
		log.setCaretPosition(log.getDocument().getLength());
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	
}

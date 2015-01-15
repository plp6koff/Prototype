package com.consultancygrid.trz.actionListener;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;

import com.consultancygrid.trz.ui.frame.FileChooserDemo;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;

public class LoadFileAL extends BaseActionListener {
	
	static private final String newline = "\n";
	private JFileChooser fc;
	private JTextArea log;
	private File file;
	
	public LoadFileAL(PrototypeMainFrame mainFrame, JFileChooser fc,
			JTextArea log) {

		super(mainFrame);
		this.fc = fc;
		this.log = log;
	}

	public void actionPerformed(ActionEvent e) {

//		 int returnVal = fc.showSaveDialog(mainFrame);
//         if (returnVal == JFileChooser.APPROVE_OPTION) {
//             File file = fc.getSelectedFile();
//             //This is where a real application would save the file.
//             log.append("Saving: " + file.getName() + "." + newline);
//         } else {
//             log.append("Save command cancelled by user." + newline);
//         }
//         log.setCaretPosition(log.getDocument().getLength());
	}

	
}

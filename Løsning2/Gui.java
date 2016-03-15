import java.io.IOException;
import java.io.File;

import java.net.URI;

import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JButton;

public class Gui extends JFrame {
	private JButton chooseFile, wiki;
	private JTextArea outputArea, readArea;
	final JPanel panel;
	
	/*
	 *	Simple GUI design set to reality.
	 *	output() and readput() prints out information and result
	 *	fileChooser opens a new window, which lets the user choose
	 *	file for the occation by browsing through directories.
	*/
	public Gui(){
		//Buttons
		chooseFile = new JButton("Choose file");
		wiki = new JButton("Wiki anagram");

		//TextArea
		outputArea = new JTextArea(20, 25);
		outputArea.setEditable(false);
		readArea = new JTextArea(20, 25);
		readArea.setEditable(false);
		
		//create JTabbedPane
		JTabbedPane t = new JTabbedPane();

		//Create and add to panel
		panel = new JPanel();
		//Interface purposes
		String tab = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t";
		panel.add(new JLabel("File content" + tab + "Anagram result"));

		panel.add(new JScrollPane(readArea));
		panel.add(new JScrollPane(outputArea));

		t.addTab("Anagram", null, panel, "Anagram");		
		
		add(t);

		//Action to buttons
		Buttons b = new Buttons();
		chooseFile.addActionListener(b);
		wiki.addActionListener(b);
		panel.add(chooseFile);
		panel.add(wiki);

		setVisible(true);	
	}
	/*
	 *	output() prints out the anagram result in a JTextArea
	 *	readput() prints out the content of the file, that has been read
	*/
	public void output(String text){
		outputArea.setText("");
		outputArea.append(text);
	}
	public void readput(String text){
		readArea.setText("");
		readArea.append(text);
	}

	public void fileChooser(){
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Choose a '.txt' file");
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			try {
				Test t = new Test();
				String content = t.readFile(f);
				if(content == null){
					showMsg("ERROR!!!\n\t-> COULD NOT READ FILE!");
				}else{
					readput(content);
				}
				output(t.grouping());

			} catch(Exception e) {}
		}
	}

	//Pop up message
	private void showMsg (String melding){
		JOptionPane.showMessageDialog(this, melding);
	}
	/*
	 *	Buttons are given life.
	 *	wiki opens the given link in users web-browser for anagram definition
	 */
	private class Buttons implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == chooseFile){
				fileChooser();
			}else if(e.getSource() == wiki) {
				try {
					String htmlFilePath = "https://no.wikipedia.org/wiki/Anagram";
					Desktop.getDesktop().browse(URI.create(htmlFilePath));
				} catch(IOException ioe) {
					showMsg("ERROR!!!\n\t-> FAILED OPENING LINK!");
				}
			}
		}
	}
}
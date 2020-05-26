package src;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class FileBrowser extends JPanel implements ActionListener{
	JLabel label = new JLabel("File List: ");
	JButton newFile = new JButton("NewFile ");
	JButton open = new JButton("Open ");
	JTextField tf = new JTextField(10);
	ButtonGroup bg;
	File directory;
	FileBrowser(String dir ){
		directory = new File(dir);
		directory.mkdir();
		JPanel fileList = new JPanel(new GridLayout(directory.listFiles().length+3 , 1));
		fileList.add(label);
		bg = new ButtonGroup();
		for(File file: directory.listFiles()) {
			JRadioButton radio = new JRadioButton(file.getName());
			radio.setActionCommand(file.getName());
			bg.add(radio);
			fileList.add(radio);
		}
		JPanel newP = new JPanel();
		newP.add(tf);
		newP.add(newFile);
		fileList.add(open);
		fileList.add(newP);
		open.addActionListener(this);
		newFile.addActionListener(this);
		add(fileList);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Login login = (Login) getParent();
		if(e.getSource() == open) {
			login.add(new Editor(directory.getName() + "\\" + bg.getSelection().getActionCommand()), "editor");
			login.cl.show(login, "editor");
			
		}
		if(e.getSource() == newFile) {
			String file = directory.getName()+"\\"+tf.getText()+".txt";
			if(tf.getText().length() > 0 && !(new File(file).exists())) {
				login.add(new Editor(file), "editor");
				login.cl.show(login, "editor");
			}
		}
		
	}
}

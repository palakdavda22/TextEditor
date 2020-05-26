package src;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.MessageDigest;
import java.util.StringTokenizer;

import javax.swing.JLabel;
import javax.swing.*;

public class Login extends JPanel implements ActionListener{
	JLabel userL = new JLabel("Username: ");
	JTextField userTF = new JTextField();
	JLabel passL = new JLabel("Password ");
	JPasswordField passTF = new JPasswordField();
	JPanel loginP = new JPanel(new GridLayout(3,2));
	JPanel panel = new JPanel();
	JButton login = new JButton("Login");
	JButton register = new JButton("register");
	CardLayout cl;
	Login(){
		setLayout(new CardLayout());
		loginP.add(userL);
		loginP.add(userTF);
		loginP.add(passL);
		loginP.add(passTF);
		login.addActionListener(this);
		register.addActionListener(this);
		loginP.add(login);
		loginP.add(register);
		panel.add(loginP);
		add(panel, "login");
		cl = (CardLayout) getLayout();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == register) {
			add(new Register() , "register");
			cl.show(this, "register");
		}
		if(e.getSource() == login) {
			try {
				BufferedReader input = new BufferedReader(new FileReader("passwords.txt"));
				String pass = null;
				String line = input.readLine();
				while(line != null) {
					StringTokenizer st = new StringTokenizer(line);
					if(userTF.getText().contentEquals(st.nextToken())) {
						pass = st.nextToken();
						
					}
					line = input.readLine();
				}
				input.close();
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(new String(passTF.getPassword()).getBytes());
				byte byteData[] = md.digest();
				StringBuffer sb = new StringBuffer();
				for(int i = 0 ;i <byteData.length; i++)
					sb.append(Integer.toString(byteData[i] & 0xFF));
//					sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100, 16).substring(1));
				
				if(pass.contentEquals(sb.toString())) {
					System.out.println("Logged in successfully");
					add(new FileBrowser(userTF.getText()), "fb");
					cl.show(this, "fb");
				}
				else
					System.out.println("Problem logging in");
			}
			catch(Exception e1) {
				e1.printStackTrace(); 
			}
		}
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("Text Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		Login login = new Login();
		frame.add(login);
		frame.setVisible(true);
	}
}

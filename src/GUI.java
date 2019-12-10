/*
.AUTHOR 			Dennis Bergmann
.CREATED 			03.12.2019
.LAST UPDATED 		10.12.2019
.DESCRIPTION		Genereates random password
*/

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class GUI {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		// Erstellung des GUIs
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JCheckBox chckbxBuchstaben = new JCheckBox("Buchstaben");
		chckbxBuchstaben.setSelected(true);
		chckbxBuchstaben.setBounds(24, 53, 120, 23);
		frame.getContentPane().add(chckbxBuchstaben);
		
		JCheckBox chckbxZahlen = new JCheckBox("Zahlen");
		chckbxZahlen.setSelected(true);
		chckbxZahlen.setBounds(24, 79, 97, 23);
		frame.getContentPane().add(chckbxZahlen);
		
		JCheckBox chckbxSonderzeichen = new JCheckBox("Sonderzeichen");
		chckbxSonderzeichen.setSelected(true);
		chckbxSonderzeichen.setBounds(24, 105, 142, 23);
		frame.getContentPane().add(chckbxSonderzeichen);
		
		textField = new JTextField();
		textField.setBounds(24, 135, 30, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
				
		JLabel lblLnge = new JLabel("L\u00E4nge");
		lblLnge.setBounds(64, 138, 46, 14);
		frame.getContentPane().add(lblLnge);
		
		JLabel lblRandomPasswordGenerator = new JLabel("Random Password Generator");
		lblRandomPasswordGenerator.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblRandomPasswordGenerator.setBounds(24, 11, 355, 35);
		frame.getContentPane().add(lblRandomPasswordGenerator);
		
		JLabel label = new JLabel("");
		label.setBounds(24, 166, 378, 23);
		frame.getContentPane().add(label);
		
		JButton btnGenerieren = new JButton("Generieren");
		btnGenerieren.setEnabled(false); // Button default disabled
		
		JSlider slider = new JSlider();
		slider.setValue(8);
		slider.setMinimum(8);
		slider.setBounds(120, 133, 200, 26);
		frame.getContentPane().add(slider);
		
		// Listener zum Aktivieren des Buttons
		textField.getDocument().addDocumentListener(new DocumentListener() {
			
			public void changedUpdate(DocumentEvent e)            {
				     enableButton();
	}
			public     void removeUpdate(DocumentEvent e)       {
			        enableButton();
}
			public void insertUpdate(DocumentEvent e) {
			    enableButton();
			}
			
			public void enableButton() {
				// aktivieren, wenn nicht leer, eine Zahl und kein Buchstabe
				if (!textField.getText().equals("") && textField.getText().matches(".*\\d.*") && !textField.getText().matches(".*\\D.*")) {
					btnGenerieren.setEnabled(true);
				} else {
					btnGenerieren.setEnabled(false);
				}
			}
		});
		
		// Listener f�r den Button
		btnGenerieren.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				// Benutzerauswahl Zusammensetzung Passwort
				boolean boolBuchstaben = chckbxBuchstaben.isSelected();
				boolean boolZahlen = chckbxZahlen.isSelected();
				boolean boolSonder = chckbxSonderzeichen.isSelected();
				int pwLength = 0;
				
				pwLength = Integer.parseInt(textField.getText()); // Aus GUI Textfeld
				
				int minPwLength = 8;
				
				// Vorgabe f�r den Pool aus Zeichen, aus dem das Passwort generiert wird
				String alpha = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
				String numbers = "1234567890";
				String sonder = "+#-.,!?";
				
				String pool = "";
				String randomPw = "";
			
				// min minPwLength Chars und eine Art
				if (pwLength >= minPwLength && (boolBuchstaben || boolZahlen || boolSonder)) {
					
					// Ausgabe leeren
					label.setText("");
					label.setForeground(Color.black);
					
					// Pool f�r Random String generieren
					if (boolBuchstaben) { pool += alpha; }
					if (boolZahlen) { pool += numbers; }
					if (boolSonder) { pool += sonder; }
					
					// Schleife, die das Passwort zuf�llig zusammensetzt
					for (int i=0; i < pwLength; i++) {
						
						Random rand = new Random();
						int ranNum = rand.nextInt(pool.length());
						
						randomPw += pool.charAt(ranNum);
	
					}
					
					// Ausgabe Passwort
					label.setText(randomPw);
					
					
				} else {
					// Wenn Passwort kleiner als minPwLength
					label.setText("Das Passwort muss mindestens aus " + minPwLength + " Zeichen bestehen.");
					label.setForeground(Color.red);
				}
				
			}
		});
		
		btnGenerieren.setBounds(24, 200, 100, 35);
		frame.getContentPane().add(btnGenerieren);
		
		JButton btnCopy = new JButton("Copy");
		
		// Passwort in Zwischenablage kopieren
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StringSelection stringSelection = new StringSelection(label.getText());
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
			}
		});
		
		btnCopy.setBounds(134, 206, 68, 23);
		frame.getContentPane().add(btnCopy);
		
		// Eventlistener f�r Slider
		 slider.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent event) {
		    	if (slider.hasFocus()) {
		    		String value = Integer.toString(slider.getValue());
				    textField.setText(value);
		    	}
		       
		      }
		    });
	}
}

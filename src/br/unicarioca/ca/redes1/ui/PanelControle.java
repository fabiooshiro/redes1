package br.unicarioca.ca.redes1.ui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelControle extends JPanel{
	MainFrame mainFrame;
	JButton enviarPacote;
	JTextField txtTempo;
	JLabel lblTempo;
	public PanelControle(MainFrame mainFrame) {
		this.setLayout(null);
		this.mainFrame=mainFrame;
		enviarPacote=new JButton("Enviar");
		txtTempo = new JTextField("60");
		lblTempo = new JLabel("Tempo:");
		enviarPacote.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				enviar();	
			}			
		});
		lblTempo.setBounds(0, 0,60,26);
		txtTempo.setBounds(65, 0,30,26);
		enviarPacote.setBounds(60, 30,90,26);
		this.add(enviarPacote);
		this.add(lblTempo);
		
		this.add(txtTempo);
		this.setPreferredSize(new Dimension(800,150));
	}
	private void enviar(){
		try{
			int tempo = Integer.valueOf(txtTempo.getText());
			this.mainFrame.enviarPacote(1,tempo);
		}catch(Exception e){
			
		}
		
		
	}
}

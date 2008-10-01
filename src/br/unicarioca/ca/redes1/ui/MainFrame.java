package br.unicarioca.ca.redes1.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import br.unicarioca.ca.redes1.bo.Animador;

/**
 * Só por trocadilho
 * @author Fabio Issamu Oshiro
 *
 */
public class MainFrame extends JFrame{
	private PanelAnimacao panelAnimacao;
	private Animador animador;
	public MainFrame() throws Exception{
		super("Transmissão");
		panelAnimacao = new PanelAnimacao();
		add(panelAnimacao,BorderLayout.CENTER);
		animador = new Animador(panelAnimacao);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	public static void main(String args[]){
		try{
			new MainFrame();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

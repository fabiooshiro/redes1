package br.unicarioca.ca.redes1.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;


import br.unicarioca.ca.redes1.bo.Animador;
import br.unicarioca.ca.redes1.protocolo.CamadaFisica;
import br.unicarioca.ca.redes1.protocolo.InterfaceTransmissor;
import br.unicarioca.ca.redes1.protocolo.Receptor;
import br.unicarioca.ca.redes1.protocolo.Transmissor;
import br.unicarioca.ca.redes1.vo.Ack;
import br.unicarioca.ca.redes1.vo.Animavel;
import br.unicarioca.ca.redes1.vo.Quadro;
import br.unicarioca.ca.redes1.vo.TimeOut;

/**
 * Só por trocadilho
 * @author Fabio Issamu Oshiro
 *
 */
public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4293189701804295061L;
	public static final int X_TRANSMISSOR=365;
	public static final int Y_TRANSMISSOR=55;
	public static final int Y_RECEPTOR=155;
	public static final int MIN_INTERVALOR = 7;
	private JSplitPane splitPane;
	private PanelAnimacao panelAnimacao;
	private PanelControle panelControle;
	private static Animador animador;
	private Transmissor transmissor;
	private Receptor receptor;
	public MainFrame() throws Exception{
		
		super("Redes 1");
		{//iniciar parte lógica
			panelAnimacao = new PanelAnimacao();
			animador = new Animador(panelAnimacao);
			CamadaFisica.setAnimador(animador);
			transmissor = new Transmissor();
			receptor = new Receptor();
			CamadaFisica camadaFisica = CamadaFisica.getInstance();
			camadaFisica.setReceptor(receptor);
			camadaFisica.setTransmissor(transmissor);
		}
		{//Iniciar parte gráfica
			
			JScrollPane sp = new JScrollPane(panelAnimacao); 
			sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			panelControle = new PanelControle(this);
			splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,sp,panelControle);
			splitPane.setDividerLocation(300);
			splitPane.setOneTouchExpandable(true);
			add(splitPane,BorderLayout.CENTER);	
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setPreferredSize(new Dimension(800,600));
			this.pack();
			this.setVisible(true);
		}
		this.mostrarHistorico(true);
	}
	public void enviarQuadros(int qtd){
		try{
			transmissor.enviarQuadros(qtd);
		}catch(Exception e){
			
		}
	}
	
	public void enviarOnda()throws Exception{
		//int tempo=50;
		try{
			/*
				long cframe = animador.getCurrentFrame();
				Onda onda = new Onda();
				onda.setImagemPath("images/onda.gif");
				onda.setOrigemX(-300);
				onda.setOrigemY(Y_TRANSMISSOR+30);
				onda.setDestinoX(900);
				onda.setFrameInicio(cframe);
				onda.setDestinoY(Y_TRANSMISSOR+30);
				onda.setFrameFinal(cframe+tempo);
				animador.animar(onda);*/
		}catch(Exception e){
			JOptionPane.showMessageDialog(this,e.getMessage());
		}
		
	}
	
	
	
	public static void main(String args[]){
		try{
			
			new MainFrame();
			//new Thread(new ColidListenerThread()).start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void mostrarHistorico(boolean selected) {
		animador.mostrarHistorico(selected);
		
	}
	public static Animador getAnimador() {
		return animador;
	}
	public void enviarMensagem(String mensagem) {
		try{
			transmissor.enviarMensagem(mensagem);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public Transmissor getTransmissor() {
		return transmissor;		
	}
	public Receptor getReceptor() {
		return receptor;
	}
}

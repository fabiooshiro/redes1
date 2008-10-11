package br.unicarioca.ca.redes1.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import br.unicarioca.ca.redes1.bo.Animador;
import br.unicarioca.ca.redes1.vo.Ack;
import br.unicarioca.ca.redes1.vo.Quadro;

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
	private PanelAnimacao panelAnimacao;
	private Animador animador;
	public MainFrame() throws Exception{
		super("Transmissão");
		panelAnimacao = new PanelAnimacao();
		add(panelAnimacao,BorderLayout.CENTER);
		animador = new Animador(panelAnimacao);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800,600));
		this.pack();
		this.setVisible(true);
		enviarPacote();
	}
	public void enviarPacote(){
		try{
			for(int i=0;i<3;i++){
				Quadro quadro = new Quadro();
				quadro.setDestinoX(365);
				quadro.setDestinoY(155);
				quadro.setOrigemX(365);
				quadro.setOrigemY(54);
				quadro.setFrameInicio(0+i*30);
				quadro.setFrameFinal(60+i*30);
				animador.animar(quadro);
				Ack ack = new Ack();
				ack.setOrigemX(365);
				ack.setOrigemY(155);
				ack.setDestinoX(365);
				ack.setDestinoY(54);
				ack.setFrameInicio(65+i*30);
				ack.setFrameFinal(125+i*30);
				animador.animar(ack);
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(this,e.getMessage());
		}
	}
	public static void main(String args[]){
		try{
			new MainFrame();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

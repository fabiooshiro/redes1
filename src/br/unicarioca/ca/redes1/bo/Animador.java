package br.unicarioca.ca.redes1.bo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import br.unicarioca.ca.redes1.vo.Animavel;

/**
 * Poderíamos chamar de engine
 * @author Fabio Issamu Oshiro
 *
 */
public class Animador {
	private Timer timer;
	private long frameCounter;
	private ArrayList<Animavel> listAnimavel;
	private BufferedImage bufferedImage;
	private BufferedImage pacote;
	private BufferedImage historico;
	private Tela tela;
	public Animador(Tela tela) throws Exception {
		pacote = ImageIO.read(new File("images/pacote.jpg"));
		listAnimavel = new ArrayList<Animavel>();
		bufferedImage = new BufferedImage(550,420,BufferedImage.TYPE_INT_RGB);
		historico = new BufferedImage(550,1680,BufferedImage.TYPE_INT_RGB);
		this.tela = tela;
		timer = new Timer(this);
		timer.start();
		
	}
	
	/**
	 * Qualquer semelhança com o Flash é mera coincidência
	 */
	private void onEnterFrame() throws Exception{
		//teste de animação
		bufferedImage.getGraphics().drawImage(historico, 0,(int)frameCounter-840 ,null);
		historico.getGraphics().drawImage(pacote, (int)frameCounter*2,840-(int)frameCounter, null);
	}
	
	/**
	 * Chamado pelo timer
	 */
	void timeOut(){
		try{
			frameCounter++;
			onEnterFrame();
			tela.setBufferedImage(bufferedImage);
		}catch(Exception e){
			
		}
	}
}

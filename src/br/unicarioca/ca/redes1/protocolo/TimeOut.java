package br.unicarioca.ca.redes1.protocolo;

import br.unicarioca.ca.redes1.bo.Animador;
import br.unicarioca.ca.redes1.ui.MainFrame;

public class TimeOut extends br.unicarioca.ca.redes1.vo.TimeOut{
	private static Animador animador;
	private int numero;
	private Transmissor transmissor;
	private long tempoMs;
	private TimeOut timeOut;
	public TimeOut(Transmissor transmissor,final long tempoMs) throws Exception{
		this.transmissor = transmissor;
		this.tempoMs = tempoMs;
		timeOut = this;
		this.setImagemPath("images/clockp.png");
		this.setDestinoY(MainFrame.Y_TRANSMISSOR);
		this.setDestinoX(MainFrame.X_TRANSMISSOR);
		int tQ = (int)tempoMs/(1000/animador.getFps());
		int tX = tQ*animador.getVelocidadeHistorico();
		this.setOrigemX(MainFrame.X_TRANSMISSOR+tX);
		this.setOrigemY(MainFrame.Y_TRANSMISSOR);
		this.setFrameInicio(animador.getCurrentFrame());
		this.setFrameFinal(animador.getCurrentFrame()+tQ);
		animador.animar(this);
		initTimeOut();
	}
	private void initTimeOut(){
		Thread t = new Thread(new Runnable(){
			public void run() {
				try{
					Thread.sleep(tempoMs);
				}catch(Exception e){
					
				}
				transmissor.receberTimeOut(timeOut);
			}
		});
		t.start();
	}
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	public static void setAnimador(Animador animador) {
		TimeOut.animador = animador;		
	}
	
}

package br.unicarioca.ca.redes1.protocolo;

import br.unicarioca.ca.redes1.bo.Animador;
import br.unicarioca.ca.redes1.bo.FrameAction;
import br.unicarioca.ca.redes1.ui.MainFrame;

public class TimeOut extends br.unicarioca.ca.redes1.vo.TimeOut{
	private static Animador animador;
	private int numero;
	private Transmissor transmissor;
	private TimeOut timeOut;
	private boolean valido;
	public TimeOut(Transmissor transmissor,final long tempoMs,int numero) throws Exception{
		valido = true;
		this.transmissor = transmissor;
		timeOut = this;
		this.setNumero(numero);
		this.setDestinoY(MainFrame.Y_TRANSMISSOR - 20);
		int tQ = (int)tempoMs/(1000/animador.getFps());
		int tX = tQ*animador.getVelocidadeHistorico();
		this.setOrigemX(MainFrame.X_TRANSMISSOR+tX);
		this.setDestinoX(MainFrame.X_TRANSMISSOR+tX-animador.getVelocidadeHistorico());
		this.setOrigemY(MainFrame.Y_TRANSMISSOR - 20);
		this.setFrameInicio(animador.getCurrentFrame());
		this.setFrameFinal(animador.getCurrentFrame()+1);
		animador.animar(this);
		//Cadastra uma acao para executar no futuro
		animador.addFrameAction(new FrameAction(animador.getCurrentFrame()+tQ){
			public void executar() {
				timeOut.transmissor.receberTimeOut(timeOut);
			}			
		});
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.setImagemPath("images/clockp"+numero+".png");
		this.numero = numero;
	}
	public static void setAnimador(Animador animador) {
		TimeOut.animador = animador;		
	}
	public boolean isValido() {
		return valido;
	}
	public void setValido(boolean valido) {
		this.valido = valido;
	}
	
}
